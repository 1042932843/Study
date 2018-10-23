package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    @BindView(R.id.newstitle)
    TextView newstitle;

    @BindView(R.id.news)
    TextView news;
    @BindView(R.id.img)
    ImageView imageView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
    }



    @SuppressLint("CheckResult")
    public void loadData() {

    }

    private void initWidget() {
        apptitle.setText("详情");
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
        newstitle.setText(getIntent().getStringExtra("NewsTitle"));
        com.alibaba.fastjson.JSONObject object=JSON.parseObject(getIntent().getStringExtra("NewsResult"));
        String detail=object.getString("newsDetail");
        news.setText(detail);
        String img=object.getString("newsImg");
        if(TextUtils.isEmpty(img)){
            imageView.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(img).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

    }


}


