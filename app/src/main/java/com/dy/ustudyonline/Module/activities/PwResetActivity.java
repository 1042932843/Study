package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class PwResetActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    protected ProgressDialog dialog;
    @BindView(R.id.title)
    TextView apptitle;
    @BindView(R.id.textRight)
    TextView txt_right;

    @BindView(R.id.tip)
    TextView tip;

    @OnClick(R.id.textRight)
    public void reset(){
        tip.setText("");
        String old=pwo.getText().toString();
        String newpw=pw.getText().toString();
        String newpws=pws.getText().toString();
        if(TextUtils.isEmpty(old)){
            tip.setText("请输入旧密码");
            return;
        }
        if(TextUtils.isEmpty(newpw)){
            tip.setText("请输入新密码");
            return;
        }

        if(newpw.length()<6){
            tip.setText("密码太短，请至少输入6位密码");
            return;
        }
        if(newpw.length()>20){
            tip.setText("密码太长，请输入20位以内密码");
            return;
        }
        if(TextUtils.isEmpty(newpws)){
            tip.setText("请重复密码加以确认");
            return;
        }
        if(!newpw.equals(newpws)){
            tip.setText("新密码两次输入不一致");
            return;
        }
        doReset(old,newpw);

    }

    public void goLogin(){
        Intent it=new Intent(this,LoginActivity.class);
        startActivity(it);
        finish();
    }

    @SuppressLint("CheckResult")
    private void doReset(String po, String pn) {
        String s= PreferenceUtil.getStringPRIVATE("id", "");
        if(TextUtils.isEmpty(s)){
            ToastUtil.ShortToast("登录状态错误请重新登录");
            goLogin();
            return;
        }
        dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setMessage("请求中...");
        dialog.show();
        RetrofitHelper.getLoginRegisterAPI()
                .reset(s,po,pn)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            ToastUtil.ShortToast("修改成功");
                            DuskyApp.getInstance().logout();
                            goLogin();
                            break;
                        case "-1":
                        case "-2":
                            default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }

                    dialog.dismiss();
                }, throwable -> {
                    dialog.dismiss();
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }


    @BindView(R.id.pwo)
    EditText pwo;
    @BindView(R.id.pw)
    EditText pw;
    @BindView(R.id.pws)
    EditText pws;


    @Override
    public int getLayoutId() {
        return R.layout.activity_resetpw;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
    }



    @SuppressLint("CheckResult")
    public void loadData() {

    }

    private void initWidget() {
        apptitle.setText("密码修改");
        apptitle.setTextColor(Color.WHITE);
        txt_right.setText("完成");
        txt_right.setTextColor(Color.WHITE);
        txt_right.setVisibility(View.VISIBLE);
        imgLeft.setImageResource(R.drawable.back);
    }


}


