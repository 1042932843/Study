package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Base.DuskyApp;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

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


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_tab2;
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


