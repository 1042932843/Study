package com.dy.ustudyonline.Module.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;


import com.dy.studyonline.R;

import com.dy.ustudyonline.Base.BaseActivity;


import butterknife.BindView;
import butterknife.OnClick;


public class IntroductionActivity extends BaseActivity {
    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        this.finish();
    }

    @BindView(R.id.title)
    TextView title;

    @OnClick(R.id.but)
    public void go(){
        Intent it=new Intent(IntroductionActivity.this,PlayActivity.class);
        startActivity(it);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("课程简介");
        title.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }





}
