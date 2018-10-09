package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.User;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseActivity {
    protected ProgressDialog dialog;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.idcard)
    EditText username;
    @BindView(R.id.pwd)
    EditText password;

    @SuppressLint("CheckResult")
    @OnClick(R.id.login)
    public void doLogin(){
        String userName=username.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.ShortToast("请输入用户名");
            return;
        }

        String passwordText = password.getText().toString();
        if (TextUtils.isEmpty(passwordText)) {
            ToastUtil.ShortToast("请输入密码");
            return;
        }

        dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setMessage("请求中...");
        dialog.show();
        RetrofitHelper.getLoginRegisterAPI()
                .login(userName,passwordText)
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
                            /*
                            [{"telePhone":"13232277702","userType":"1","remark":"","realName":"王丹","imageUrl":"http://119.146.222.170:9081/subpfv31/common/images/userImage/119448964420180926095018.jpg","major":"信计","id":"d6c42c8bde524acdb3f0f59092774e4b","idCard":"500225199504177720","education":"大专","loginCount":1182,"userName":"1194489644","address":"广东珠海市香洲区","lastLoginTime":"2018-10-08 16:18:25","optTime":"2017-09-13 15:56:20","deviceType":"2","school":"重庆大学","email":"1194489644@qq.com","politicalStatus":"群众","regiestTime":"2017-09-13 15:56:20","userPwd":"E10ADC3949BA59ABBE56E057F20F883E","flag":"0","operator":"admin","hostUrl":"","availablyTag":114123,"sex":"女"}]
                             */
                            JSONArray array=JSON.parseArray(apiMsg.getResultInfo());
                            User user= JSON.parseObject(array.get(0).toString(),User.class);//以前的接口像个SB一样套了个array
                            //Object obj1 = JSON.parseObject(apiMsg.getResult(),Object.class);
                            //String ob= obj1.toString();
                            if(user!=null){
                                PreferenceUtil.put("username",user.getUserName());
                                PreferenceUtil.putStringPRIVATE("id",user.getId());
                                PreferenceUtil.putStringPRIVATE("realName",user.getRealName());
                                PreferenceUtil.putStringPRIVATE("imageUrl",user.getImageUrl());
                                if("0".equals(user.getFlag())){
                                    ToastUtil.showShort(LoginActivity.this,"登录成功");
                                    Intent it=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(it);
                                    finish();
                                }else{
                                    ToastUtil.showShort(LoginActivity.this,"您的帐号没有相关权限，请尝试重新登录或者联系管理员");
                                }

                            }
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("登录");
        title.setTextColor(Color.WHITE);
        username.setText(PreferenceUtil.getString("username",""));
        if(!TextUtils.isEmpty(PreferenceUtil.getStringPRIVATE("id",""))){
            Intent it=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(it);
            finish();
        }
    }



}
