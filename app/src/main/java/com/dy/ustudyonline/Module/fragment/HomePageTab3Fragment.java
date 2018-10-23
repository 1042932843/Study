package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.JsonReader;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.HomeRecAdapter1;
import com.dy.ustudyonline.Adapter.HomeRecAdapter2;
import com.dy.ustudyonline.Adapter.Tab3RecAdapter;
import com.dy.ustudyonline.Adapter.helper.EndlessRecyclerOnScrollListener;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.IntroductionActivity;
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.activities.PlayActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab1Item;
import com.dy.ustudyonline.Module.entity.DataTab3Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //学习
 * Date: 2018-08-29 11:02
 */
public class HomePageTab3Fragment extends BaseFragment {
    private int pageNum = 1;
    private int pageSize = 10;
    boolean isFirst=true;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private boolean mIsRefreshing = false;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.tip)RelativeLayout tip;
    @BindView(R.id.textRight)TextView textRight;
    @OnClick(R.id.textRight)
    public void bianji(){
        if("编辑".equals(textRight.getText())){
            int size=fldatas.size();
            for (int i=0;i<size;i++){
                fldatas.get(i).setShow(true);
            }
            homeRecAdapter1.notifyDataSetChanged();
            textRight.setText("取消");
        }else{
            int size=fldatas.size();
            for (int i=0;i<size;i++){
                fldatas.get(i).setShow(false);
            }
            homeRecAdapter1.notifyDataSetChanged();
            textRight.setText("编辑");
        }

    }
    @OnClick(R.id.tip)
    public void jump(){
        EventBus.getDefault().post(MainActivity.Page2);
    }
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    Tab3RecAdapter homeRecAdapter1;
    List<DataTab3Item> fldatas=new ArrayList<>();
    @BindView(R.id.title)
    TextView title;
    public HomePageTab3Fragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab3;
    }

    @Override
    public void finishCreateView(Bundle state) {
        title.setText("学习");
        title.setTextColor(Color.WHITE);
        textRight.setText("编辑");
        textRight.setVisibility(View.GONE);
        textRight.setTextColor(Color.WHITE);
        initRecyclerView();


    }

    @Override
    public void lazyLoad(){
        pageNum = 1;
        mIsRefreshing = true;
        mEndlessRecyclerOnScrollListener.refresh();
        if(isFirst){
            mSwipeRefreshLayout.post(() -> {
                mSwipeRefreshLayout.setRefreshing(true);
                fldatas.clear();
                loadData();
            });
            isFirst=false;
        }else{
            if(fldatas.size()<=0){
                textRight.setText("编辑");
                loadData();
            }
        }
    }

    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            int size=fldatas.size();
            for (int i=0;i<size;i++){
                fldatas.get(i).setShow(false);
            }
            textRight.setText("编辑");
            homeRecAdapter1.notifyDataSetChanged();
            pageNum = 1;
            mIsRefreshing = true;
            fldatas.clear();
            mEndlessRecyclerOnScrollListener.refresh();
            loadData();
        });
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        RetrofitHelper.gethomePageTab3API()
                .unLearnedCourses(PreferenceUtil.getStringPRIVATE("id",""),pageNum)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mIsRefreshing = false;
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
                                    DataTab3Item item=JSON.parseObject(array.get(i).toString(),DataTab3Item.class);
                                    if("取消".equals(textRight.getText())){
                                        item.setShow(true);
                                    }else{
                                        item.setShow(false);
                                    }
                                    fldatas.add(item);
                                }
                                homeRecAdapter1.notifyDataSetChanged();
                                //fldatas.addAll();
                                pageNum++;
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
                    mIsRefreshing = false;
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }

    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        homeRecAdapter1=new Tab3RecAdapter(fldatas,getContext());
        homeRecAdapter1.setOnItemCheckedListener(new Tab3RecAdapter.OnItemCheckedListener() {
            @Override
            public void Checked(int position, boolean checked) {
                fldatas.get(position).setSelect(checked);
            }
        });
        homeRecAdapter1.setOnItemClickListener(new Tab3RecAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity(), PlayActivity.class);
                intent.putExtra("courseTerraceId",fldatas.get(position).getCourseTerraceId());
                intent.putExtra("title",fldatas.get(position).getCourseName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeRecAdapter1);

        mEndlessRecyclerOnScrollListener =new EndlessRecyclerOnScrollListener(layoutManager) {
            @SuppressLint("CheckResult")
            @Override
            public void onLoadMore(int currentPage) {
                loadData();
            }
        };
       // recyclerView.addOnScrollListener(mEndlessRecyclerOnScrollListener);
        //setRecycleNoScroll();
        initRefreshLayout();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private void setRecycleNoScroll() {
        recyclerView.setOnTouchListener((View v, MotionEvent event) -> {
            return mIsRefreshing;
        });
    }
}
