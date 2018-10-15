package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    protected ProgressDialog pdialog;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.idcard)
    EditText username;
    @BindView(R.id.pwd)
    EditText password;
    String[] sitedef;
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
                            JSONArray array=JSON.parseArray(apiMsg.getResultInfo());
                            User user= JSON.parseObject(array.get(0).toString(),User.class);//以前的接口像个SB一样套了个array
                            //Object obj1 = JSON.parseObject(apiMsg.getResult(),Object.class);
                            //String ob= obj1.toString();
                            if(user!=null){
                                PreferenceUtil.put("username",user.getUserName());
                                PreferenceUtil.putStringPRIVATE("id",user.getId());
                                PreferenceUtil.putStringPRIVATE("userName",user.getUserName());
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
                        case "0003":
                            JSONArray jsonArray=JSON.parseArray(apiMsg.getResultInfo()) ;
                            sitedef=new String[jsonArray.size()];
                            int size=jsonArray.size();
                            for (int i=0;i<size;i++){
                                JSONObject ti= (JSONObject) jsonArray.get(i);
                                sitedef[i]= ti.getString("tName");
                            }
                            if(sitedef==null||sitedef.length==0){
                                ToastUtil.ShortToast("未获取到站点，如非网络问题，请尝试刷新或者重启app");
                                return;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setIcon(R.drawable.ic_launcher);
                            builder.setTitle("请选择一个默认站点");
                            //    指定下拉列表的显示数据
                            //    设置一个下拉的列表选择项
                            builder.setItems(sitedef, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    doSet(which,userName);
                                }
                            });
                            builder.show();
                            break;
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

    @SuppressLint("CheckResult")
    private void doSet(int which,String userName) {
        pdialog = new ProgressDialog(LoginActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        pdialog.setMessage("设置中...");
        pdialog.show();
        RetrofitHelper.getMainAPI()
                .setDef(userName,which)
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
                            doLogin();
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
