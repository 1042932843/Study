package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.PlayTab3RecAdapter;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.QuestionActivity;
import com.dy.ustudyonline.Module.activities.RelistActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab4;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Name: PlayTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //课件
 * Date: 2018-10-15 11:02
 */
public class PlayTab3Fragment extends BaseFragment {

    @BindView(R.id.tip)RelativeLayout tip;
    @OnClick(R.id.tip)
    public void jump(){
        playDataTab3Items.clear();
        loadData();
    }

    @OnClick(R.id.dowall)
    public void tiwen(){
        Intent it=new Intent(getActivity(),QuestionActivity.class);
        it.putExtra("courseTerraceId",courseTerraceId);
        startActivity(it);
    }

    String courseTerraceId;
    private  List<PlayDataTab3Item> playDataTab3Items =new ArrayList<>();
    PlayTab3RecAdapter adapter;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    public static PlayTab3Fragment newInstance(String courseTerraceId) {
        PlayTab3Fragment fragment=  new PlayTab3Fragment();
        fragment.courseTerraceId=courseTerraceId;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String cmd) {
        switch (cmd) {
            case "refreshTab3":
                playDataTab3Items.clear();
                loadData();
                break;

        }
    }

    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new PlayTab3RecAdapter(playDataTab3Items,getContext());
        adapter.setOnItemClickListener(new PlayTab3RecAdapter.OnItemClickListener() {

            @Override
            public void onReClick(PlayDataTab3Item item) {
                Intent it=new Intent(getActivity(),RelistActivity.class);
                it.putExtra("courseTerraceId",courseTerraceId);
                it.putExtra("item",item);
                startActivity(it);
            }

            @Override
            public void onZanClick(PlayDataTab3Item item,int p) {
                zan(item,p);
            }

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    int click=1;
    @SuppressLint("CheckResult")
    public void zan(PlayDataTab3Item item,int p){
        RetrofitHelper.getPlayAPI()
                .qesClick(item.getQesId(),PreferenceUtil.getStringPRIVATE("id",""),click)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":

                            if(apiMsg.getMessage().equals("点赞成功")){
                               int count= Integer.parseInt(item.getClickCount())+1;
                                item.setClickCount(count+"");
                                adapter.notifyItemChanged(p);
                                click=0;
                                ToastUtil.ShortToast(apiMsg.getMessage());
                            }
                            if(apiMsg.getMessage().equals("只能点赞一次")){
                                click=0;
                            }
                            if(apiMsg.getMessage().equals("你已取消点赞")){
                                click=1;
                            }
                            if(apiMsg.getMessage().equals("取消点赞成功")){
                                int count= Integer.parseInt(item.getClickCount())-1;
                                item.setClickCount(count+"");
                                adapter.notifyItemChanged(p);
                                click=1;
                                ToastUtil.ShortToast(apiMsg.getMessage());
                            }

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

    @Override
    public void lazyLoad(){
        playDataTab3Items.clear();
        loadData();

    }

    @SuppressLint("CheckResult")
    private void loadData() {
        RetrofitHelper.getPlayAPI()
                .courseQuizList(courseTerraceId)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONObject object= JSON.parseObject(a);
                            JSONArray array=object.getJSONArray("questionList");
                            int size=array.size();
                            if(size>0){
                                tip.setVisibility(View.GONE);
                                for(int i=0;i<size;i++){
                                    PlayDataTab3Item item=JSON.parseObject(array.get(i).toString(),PlayDataTab3Item.class);
                                    playDataTab3Items.add(item);
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
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_tab3;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int ANIMATION_DURATION = 600;//动画执行时长
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).
                setInterpolator(INTERPOLATOR).setDuration(ANIMATION_DURATION);
        animator.start();
    }

    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).
                setInterpolator(INTERPOLATOR).
                setDuration(ANIMATION_DURATION);
        animator.start();

    }
}
