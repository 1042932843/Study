package com.dy.ustudyonline.Module.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BasePlayerActivity;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.OnClick;


public class PlayActivity extends BasePlayerActivity {
    private String url = "http://cntv.vod.cdn.myqcloud.com/flash/mp4video62/TMS/2018/04/11/d23b5cec668b1e59715b944c38b7848b_2000_h264_1872_aac_128-2.mp4";

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
        title.setText("");
        title.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
        initVideoBuilderMode();
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
        loadCover(imageView, "");
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url)
                .setCacheWithPlay(true)
                .setVideoTitle("测试视频")
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
