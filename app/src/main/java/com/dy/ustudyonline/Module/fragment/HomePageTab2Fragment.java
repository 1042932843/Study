package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.Tab2RecAdapter;
import com.dy.ustudyonline.Adapter.Tab3RecAdapter;
import com.dy.ustudyonline.Adapter.helper.EndlessRecyclerOnScrollListener;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.CourseTypeActivity;
import com.dy.ustudyonline.Module.activities.IntroductionActivity;
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.activities.PlayActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab2;
import com.dy.ustudyonline.Module.entity.DataTab3Item;
import com.dy.ustudyonline.Module.entity.Introduction;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //选课
 * Date: 2018-08-29 11:02
 */
public class HomePageTab2Fragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    boolean isFirst=true;
    List<DataTab2> datas=new ArrayList<>();
    Tab2RecAdapter adapter;
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
    public HomePageTab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab2;
    }

    @Override
    public void finishCreateView(Bundle state) {
        title.setText("选课中心");
        title.setTextColor(Color.WHITE);
        initRecyclerView();
    }

    @Override
    public void lazyLoad(){
        if(isFirst){
            mSwipeRefreshLayout.post(() -> {
                mSwipeRefreshLayout.setRefreshing(true);
                datas.clear();
                loadData();
            });
            isFirst=false;
        }else{
          if(datas.size()<=0){
              loadData();
          }

        }

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
        adapter=new Tab2RecAdapter(datas,getContext());
        adapter.setOnItemClickListener(new Tab2RecAdapter.OnItemClickListener() {
            @Override
            public void onClick(String id) {
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                intent.putExtra("courseTerraceId",id);
                startActivity(intent);
            }
        });
        adapter.setOnExpAllClickListener(new Tab2RecAdapter.OnExpAllClickListener() {
            @Override
            public void onClick(String type,String name) {
                Intent intent=new Intent(getActivity(), CourseTypeActivity.class);
                intent.putExtra("courseTypeId",type);
                intent.putExtra("courseTypeName",name);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initRefreshLayout();
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        RetrofitHelper.gethomePageTab2API()
                .choseCourseFirstPage(PreferenceUtil.getStringPRIVATE("id",""))
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
