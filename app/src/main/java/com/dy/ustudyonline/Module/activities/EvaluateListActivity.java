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
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.EvaluateListRecAdapter;
import com.dy.ustudyonline.Adapter.InformationRecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
import com.dy.ustudyonline.Module.entity.EvaluateItem;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EvaluateListActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    List<EvaluateItem> datas=new ArrayList<>();
    EvaluateListRecAdapter adapter;
    @OnClick(R.id.tip)
    public void jump(){
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            datas.clear();
            loadData();
        });
    }
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.tip)RelativeLayout tip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
        initRecyclerView();
    }



    @SuppressLint("CheckResult")
    public void loadData() {
        RetrofitHelper.getPlayAPI()
                .peCommentList(getIntent().getStringExtra("courseTerraceId"))
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    String a=bean.string();
                    //{"message":"获取全部评价成功","count":0,"state":"0000","commentList":[]}
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONObject object= JSON.parseObject(a);
                            JSONArray array=object.getJSONArray("commentList");
                            int size=array.size();
                            if(size>0){
                                tip.setVisibility(View.GONE);
                                for(int i=0;i<size;i++){
                                    EvaluateItem item=JSON.parseObject(array.get(i).toString(),EvaluateItem.class);
                                    datas.add(item);
                                }
                                adapter.notifyDataSetChanged();
                            }else{
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
        adapter=new EvaluateListRecAdapter(datas,this);
        adapter.setOnItemClickListener(new EvaluateListRecAdapter.OnItemClickListener() {
            @Override
            public void onClick(String id,String title) {

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
        apptitle.setText("全部评价");
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }


}


