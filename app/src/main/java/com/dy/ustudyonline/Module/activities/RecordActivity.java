package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;


    @Override
    public int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
    }



    @SuppressLint("CheckResult")
    public void loadData() {

    }

    private void initWidget() {
        apptitle.setText(getIntent().getStringExtra("courseTypeName"));
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }


}


