package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dy.studyonline.R;

import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Base.BasePlayerActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.Introduction;
import com.dy.ustudyonline.Module.entity.User;
import com.dy.ustudyonline.Module.entity.myVideo;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class IntroductionActivity extends BasePlayerActivity {
    @BindView(R.id.video_player)
    NormalGSYVideoPlayer detailPlayer;
    private String url ="";
            Introduction introduction;
    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        this.finish();
    }

    @BindView(R.id.cho)
    Button cho;
    @OnClick(R.id.cho)
    public void chc(){
        if("进入学习".equals(cho.getText().toString())){
            go();
        }else {
            xuanke();
        }
    }
    protected ProgressDialog pdialog;
    @SuppressLint("CheckResult")
    private void xuanke() {
        pdialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        pdialog.setMessage("正在拼命帮你选课...");
        pdialog.show();
        RetrofitHelper.getIntroductionAPI()
                .choseCoursePage(PreferenceUtil.getStringPRIVATE("id",""),getIntent().getStringExtra("courseTerraceId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            cho.setText("进入学习");
                            but.setVisibility(View.GONE);
                            pic.setVisibility(View.VISIBLE);
                            if (isPlay) {
                                detailPlayer.getCurrentPlayer().onVideoPause();
                                detailPlayer.getCurrentPlayer().release();
                            }
                            if (orientationUtils != null)
                                orientationUtils.releaseListener();
                            break;
                        case "-1":
                        case "-2":
                        default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }
                  pdialog.dismiss();
                }, throwable -> {
                    pdialog.dismiss();
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }

    @BindView(R.id.but)
    Button but;

    @BindView(R.id.pic)
    ImageView pic;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.introtitle)
    TextView introtitle;
    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.teacher)
    TextView teacher;

    @BindView(R.id.progressT)
    TextView progressT;

    @BindView(R.id.point)
    TextView point;

    @BindView(R.id.studytime)
    TextView studytime;

    @OnClick(R.id.but)
    public void go(){
        detailPlayer.startPlayLogic();
    }

    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("课程简介");
        title.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });

    }

    @SuppressLint("CheckResult")
    public void loadData(){
        RetrofitHelper.getIntroductionAPI()
                .courseAbout(PreferenceUtil.getStringPRIVATE("id",""),getIntent().getStringExtra("courseTerraceId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            //ToastUtil.ShortToast(apiMsg.getMessage());
                            introduction=JSON.parseObject(apiMsg.getResultInfo(),Introduction.class);
                            Glide.with(IntroductionActivity.this).load(introduction.getImageUrl()).into(pic);
                            introtitle.setText(introduction.getCourseName());
                            if("0".equals(introduction.getIsSelected())){
                                detailPlayer.setVisibility(View.GONE);
                                but.setVisibility(View.GONE);
                                cho.setText("进入学习");
                                pic.setVisibility(View.VISIBLE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }else {
                                but.setVisibility(View.VISIBLE);
                                cho.setText("立即选课");
                                detailPlayer.setVisibility(View.VISIBLE);
                                pic.setVisibility(View.GONE);
                                getTestPlayUrl();
                            }
                            String cont;
                            if(TextUtils.isEmpty(introduction.getRemark())){
                                cont="没有简介";
                            }else{
                                cont=introduction.getRemark();
                            }
                            content.setText(cont);
                            teacher.setText(introduction.getCourseTeacher());
                            String prog;
                            if(TextUtils.isEmpty(introduction.getStudyPercent())){
                                prog="未开始";
                            }else{
                                prog=introduction.getStudyPercent();
                            }
                            progressT.setText(prog);
                            point.setText(introduction.getCoursePoint()+"学分");
                            String playtime;
                            if(TextUtils.isEmpty(introduction.getLastPlayTime())){
                                playtime="未学习";
                            }else{
                                playtime=introduction.getLastPlayTime();
                            }
                            studytime.setText(playtime);
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

    @SuppressLint("CheckResult")
    private void getTestPlayUrl() {
        RetrofitHelper.getIntroductionAPI()
                .playTest(getIntent().getStringExtra("courseTerraceId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONObject object=JSON.parseObject(apiMsg.getResultInfo());
                            JSONArray array=object.getJSONArray("chapterlList");
                            myVideo v= JSON.parseObject(array.get(0).toString(),myVideo.class);
                            url=v.getChapter_url();
                            initVideoBuilderMode();
                            mSwipeRefreshLayout.setRefreshing(false);
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


    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                )
                .load(url)
                .into(imageView);
    }

    @Override
    public void clickForFullScreen() {

    }

    /**
     * 是否启动旋转横屏，true表示启动
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        loadCover(imageView, introduction.getImageUrl());
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setVideoTitle("")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }


}
