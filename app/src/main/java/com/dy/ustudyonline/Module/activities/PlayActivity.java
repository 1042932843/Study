package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.FragmentAdapter;
import com.dy.ustudyonline.Base.BasePlayerActivity;
import com.dy.ustudyonline.Design.ViewPager.NoAnimationViewPager;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.PlayItem;
import com.dy.ustudyonline.Module.fragment.PlayTab1Fragment;
import com.dy.ustudyonline.Module.fragment.PlayTab3Fragment;
import com.dy.ustudyonline.Module.fragment.PlayTab4Fragment;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PlayActivity extends BasePlayerActivity {
    private String url = "";
    private String titleT = "";
    int pageNum=1;
    @BindView(R.id.toolbar_tab)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    NoAnimationViewPager view_pager;

    boolean isOk=false;
    List<PlayItem> playItems=new ArrayList<>();
    PlayItem current;

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.video_player)
    NormalGSYVideoPlayer detailPlayer;


    @Override
    public int getLayoutId() {
        return R.layout.activity_play;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getUrl();
        title.setText(getIntent().getStringExtra("title"));
        title.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
        initViewPager();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void initViewPager(){
        List<String> titles;
        String[] PLANETS = {"课件","答疑","评价"};//"作业","答疑",,"资料"
        titles= Arrays.asList(PLANETS);
        List<Fragment> mFragments=new ArrayList<>();
        PlayTab1Fragment playTab1Fragment= PlayTab1Fragment.newInstance(playItems);

        PlayTab3Fragment playTab3Fragment=PlayTab3Fragment.newInstance(getIntent().getStringExtra("courseTerraceId"));
        PlayTab4Fragment playTab4Fragment=PlayTab4Fragment.newInstance(getIntent().getStringExtra("courseTerraceId"));
        //PlayTab1Fragment playTab1Fragment5=new PlayTab1Fragment();
        mFragments.add(playTab1Fragment);

        mFragments.add(playTab3Fragment);
        mFragments.add(playTab4Fragment);
        //mFragments.add(playTab1Fragment5);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(titles.size());
        tabs.setupWithViewPager(view_pager);
        //tab可滚动
        //tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    @SuppressLint("CheckResult")
    public void getUrl(){
        String id=PreferenceUtil.getStringPRIVATE("id","");
        String courseTerraceId=getIntent().getStringExtra("courseTerraceId");
        RetrofitHelper.getIntroductionAPI()
                .toPersonVideo(id,courseTerraceId,pageNum,"person")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    //{"pageSize":10,"commentTotalCount":0,"chapterlList":[{"playRate":"0%","chapter_url":"http://119.146.222.170:9980/001001/gmzy/zyk/jycy//CPSHWXG1008164559001.mp4","id":"e3ab77871da6425d814a3dcbddfb6f2c","chapter_name":"如何撰写一流的个人简历","chapter_length":"24.2"},{"playRate":"0%","chapter_url":"http://119.146.222.170:9980/001001/gmzy/zyk/jycy//CPSHWXG1008164559002.mp4","id":"09190ece728f4f6bb7ef79b7252ac07c","chapter_name":"什么才是最佳的形象礼仪","chapter_length":"22.2"},{"playRate":"0%","chapter_url":"http://119.146.222.170:9980/001001/gmzy/zyk/jycy//CPSHWXG1008164559003.mp4","id":"f626cd441e574d4897a0924e4b1c129b","chapter_name":"面试破冰的三板斧","chapter_length":"24.3"}],"chapter_count":3,"courseImageUrl":"http://119.146.222.170:9082/subpfv32/common/images/courseImage/001001/20161009024727262.jpg","peCommentList":[],"flag":"person"}
                    switch (state){
                        case "0000":
                            JSONObject object=JSON.parseObject(apiMsg.getResultInfo());
                            JSONArray array=object.getJSONArray("chapterlList");
                            int size=array.size();
                            if(size>0){
                                for(int i=0;i<size;i++){
                                    PlayItem item=JSON.parseObject(array.get(i).toString(),PlayItem.class);
                                    playItems.add(item);
                                }
                                //fldatas.addAll();

                                pageNum++;
                            }else{

                            }
                            if(!isOk&&playItems.size()>0){
                                current=playItems.get(0);
                                if(!TextUtils.isEmpty(current.getLocalUrl())){
                                    url=current.getLocalUrl();
                                }else{
                                    url=current.getChapter_url();
                                }

                                titleT=current.getChapter_name();
                                initVideoBuilderMode();
                                isOk=true;
                                EventBus.getDefault().post("refreshTab1");
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
    protected void onStop() {
        super.onStop();
        save();

    }

    public void save(){
        if(detailPlayer!=null&&playItems.size()>0){
            int nowp=detailPlayer.getCurrentPositionWhenPlaying();
            if(nowp>2000){
            int duration=detailPlayer.getDuration();
            float length=Float.parseFloat(current.getChapter_length());
            float time=length*nowp/duration;
            String id=PreferenceUtil.getStringPRIVATE("id","");
            String courseTerraceId=  getIntent().getStringExtra("courseTerraceId");
            saveRecord(id,current.getId(),time,current.getChapter_length(),courseTerraceId);
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayItem p) {
        save();
        current=p;
        if(!TextUtils.isEmpty(current.getLocalUrl())){
            url=current.getLocalUrl();
        }else{
            url=current.getChapter_url();
        }
        titleT=current.getChapter_name();
        initVideoBuilderMode();
        //detailPlayer.seekTo();
    }

    @SuppressLint("CheckResult")
    public void saveRecord(String uid, String chapterId,float time,String maxTime,String courseTerraceId){
        RetrofitHelper.getIntroductionAPI()
                .saveRecord(uid,chapterId,time,maxTime,courseTerraceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                           // ToastUtil.ShortToast(apiMsg.getMessage());

                            break;
                        case "-1":
                        case "-2":
                        default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }
                }, throwable -> {
                    //ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
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
        loadCover(imageView, "");
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setVideoTitle(titleT)
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
