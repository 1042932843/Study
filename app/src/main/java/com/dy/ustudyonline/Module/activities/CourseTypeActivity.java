package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.Tab2RecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Base.DuskyApp;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab2;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CourseTypeActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;
    Tab2RecAdapter adapter;
    List<DataTab2> datas=new ArrayList<>();
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.tip)RelativeLayout tip;
    @OnClick(R.id.tip)
    public void jump(){
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            datas.clear();
            loadData();
        });
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_tab2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            datas.clear();
            loadData();
        });
    }

    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            datas.clear();
            loadData();
        });
    }
    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new Tab2RecAdapter(datas,this);
        adapter.setOnItemClickListener(new Tab2RecAdapter.OnItemClickListener() {
            @Override
            public void onClick(String id) {
                Intent intent=new Intent(CourseTypeActivity.this, IntroductionActivity.class);
                intent.putExtra("courseTerraceId",id);
                startActivity(intent);
            }
        });
        adapter.setOnExpAllClickListener(new Tab2RecAdapter.OnExpAllClickListener() {
            @Override
            public void onClick(String type,String name) {
                Intent intent=new Intent(CourseTypeActivity.this, CourseType2Activity.class);
                intent.putExtra("courseTypeId",type);
                intent.putExtra("courseTypeName",name);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initRefreshLayout();
    }
    @SuppressLint("CheckResult")
    public void loadData() {
        RetrofitHelper.gethomePageTab2API()
                .choseCourseSecondPage(PreferenceUtil.getStringPRIVATE("id",""),getIntent().getStringExtra("courseTypeId"))
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
                                    DataTab2 item=JSON.parseObject(array.get(i).toString(),DataTab2.class);
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

    private void initWidget() {
        apptitle.setText(getIntent().getStringExtra("courseTypeName"));
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
        initRecyclerView();
    }


}


