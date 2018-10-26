package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.InformationRecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
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

public class QuestionActivity extends BaseActivity {
    String courseTerraceId;
    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    @BindView(R.id.num)
    TextView num;

    @OnClick(R.id.ok)
    public void Ok(){
        String com=editText.getText().toString();
        String title=editText0.getText().toString();
        if(TextUtils.isEmpty(title)){
            ToastUtil.ShortToast("请填写标题");
            return;
        }
        if(TextUtils.isEmpty(com)){
            ToastUtil.ShortToast("请作描述");
            return;
        }
        submit(title,com);
    }
    ProgressDialog pdialog;
    @SuppressLint("CheckResult")
    private void submit(String title, String com) {
        pdialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        pdialog.setMessage("提交中...");
        pdialog.show();
        RetrofitHelper.getPlayAPI()
                .courseQuiz(courseTerraceId,PreferenceUtil.getStringPRIVATE("id",""),title,com)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            EventBus.getDefault().post("refreshTab3");
                            finish();
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

    @BindView(R.id.edittext)
    EditText editText;
    @BindView(R.id.edittext0)
    EditText editText0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_play_tab3_question;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
        courseTerraceId=getIntent().getStringExtra("courseTerraceId");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                num.setText(editText.getText().length()+"/"+"200");
            }
        });
    }






    private void initWidget() {
        apptitle.setText("提问");
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }


}


