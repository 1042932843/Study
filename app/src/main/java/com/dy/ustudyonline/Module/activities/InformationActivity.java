package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.InformationRecAdapter;
import com.dy.ustudyonline.Adapter.Tab2RecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab2;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InformationActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    List<DataTab4Item> datas=new ArrayList<>();
    InformationRecAdapter adapter;

    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.tip)RelativeLayout tip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_infromation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
        initRecyclerView();
    }



    @SuppressLint("CheckResult")
    public void loadData() {
        RetrofitHelper.gethomePageTab4API()
                .moreNews(getIntent().getStringExtra("typeId"))
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONArray array=JSON.parseArray(apiMsg.getResultInfo());
                            int size=array.size();
                            if(size>0){
                                tip.setVisibility(View.GONE);
                                for(int i=0;i<size;i++){
                                    DataTab4Item item=JSON.parseObject(array.get(i).toString(),DataTab4Item.class);
                                    datas.add(item);
                                }
                                adapter.notifyDataSetChanged();

                            }else{
                                ToastUtil.ShortToast(a);
                                tip.setVisibility(View.VISIBLE);
                            }

                            break;
                        case "-1":
                        case "-2":
                        default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }

                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }


    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new InformationRecAdapter(datas,this);
        adapter.setOnItemClickListener(new InformationRecAdapter.OnItemClickListener() {
            @Override
            public void onClick(String id,String title) {
                getNewsData(id,title);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initRefreshLayout();
    }

    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            datas.clear();
            loadData();
        });
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            datas.clear();
            loadData();
        });
    }
    private void initWidget() {
        apptitle.setText(getIntent().getStringExtra("typeName"));
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }

    @SuppressLint("CheckResult")
    public void getNewsData(String id,String title){
        RetrofitHelper.gethomePageTab4API()
                .readNews(id)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            Intent intent=new Intent(InformationActivity.this, NewsDetailActivity.class);
                            intent.putExtra("NewsResult",apiMsg.getResultInfo());
                            intent.putExtra("NewsTitle",title);
                            startActivity(intent);
                            break;
                        case "-1":
                        case "-2":
                        default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }

                }, throwable -> {

                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }
}


