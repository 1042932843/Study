package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.dy.ustudyonline.Module.activities.MainActivity.refreshData;


public class AccountSetActivity extends BaseActivity {
    protected ProgressDialog dialog;
    User user;
    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        this.finish();
    }
    @BindView(R.id.textRight)
    TextView textRight;
    @SuppressLint("CheckResult")
    @OnClick(R.id.textRight)
    public void save(){
        String USERNAME=username.getText().toString();
        if(TextUtils.isEmpty(USERNAME)){
            ToastUtil.ShortToast("用户昵称不能为空");
            return;
        }
        username.clearFocus();
        String REALNAME=name.getText().toString();
        if(TextUtils.isEmpty(REALNAME)){
            ToastUtil.ShortToast("姓名不能为空");
            return;
        }
        name.clearFocus();
        String IDCARD=idcard.getText().toString();
        if(TextUtils.isEmpty(IDCARD)){
            ToastUtil.ShortToast("证件号码不能为空");
            return;
        }
        idcard.clearFocus();
        String PHONE=phone.getText().toString();
        if(TextUtils.isEmpty(PHONE)){
            ToastUtil.ShortToast("手机号码不能为空");
            return;
        }
        phone.clearFocus();
        String SEX=sex.getText().toString();
        if(TextUtils.isEmpty(SEX)){
            ToastUtil.ShortToast("性别不能为空");
            return;
        }
        sex.clearFocus();
        String ADDRESS=address.getText().toString();
        if(TextUtils.isEmpty(ADDRESS)){
            ToastUtil.ShortToast("地址不能为空");
            return;
        }
        address.clearFocus();
        String LEVEL=level.getText().toString();
        if(TextUtils.isEmpty(LEVEL)){
            ToastUtil.ShortToast("政治面貌不能为空");
            return;
        }
        level.clearFocus();
        String SCHOOL=school.getText().toString();
        if(TextUtils.isEmpty(SCHOOL)){
            ToastUtil.ShortToast("毕业院校不能为空");
            return;
        }
        school.clearFocus();
        String MAJOR=major.getText().toString();
        if(TextUtils.isEmpty(MAJOR)){
            ToastUtil.ShortToast("专业不能为空");
            return;
        }
        major.clearFocus();
        String EDUCATION=education.getText().toString();
        if(TextUtils.isEmpty(EDUCATION)){
            ToastUtil.ShortToast("最高学历不能为空");
            return;
        }
        education.clearFocus();
        if(user!=null){
            if(!REALNAME.equals(user.getRealName())){
                RetrofitHelper.getAccountSetAPI()
                        .updateRealName(PreferenceUtil.getStringPRIVATE("id",""),REALNAME)
                        .compose(this.bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bean -> {
                            String a=bean.string();
                            ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                            String state = apiMsg.getState();
                            switch (state){
                                case "0000":
                                    PreferenceUtil.putStringPRIVATE("realName",REALNAME);
                                    EventBus.getDefault().post(refreshData);
                                    ToastUtil.ShortToast(apiMsg.getMessage());
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

            if(!IDCARD.equals(user.getIdCard())){
                RetrofitHelper.getAccountSetAPI()
                        .updateIdCard(PreferenceUtil.getStringPRIVATE("id",""),IDCARD)
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
            if(!PHONE.equals(user.getTelePhone())){
                RetrofitHelper.getAccountSetAPI()
                        .updateTelePhone(PreferenceUtil.getStringPRIVATE("id",""),PHONE)
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

            if(!ADDRESS.equals(user.getAddress())){
                RetrofitHelper.getAccountSetAPI()
                        .updateAddress(PreferenceUtil.getStringPRIVATE("id",""),ADDRESS)
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
            if(!SCHOOL.equals(user.getSchool())){
                RetrofitHelper.getAccountSetAPI()
                        .updateSchool(PreferenceUtil.getStringPRIVATE("id",""),SCHOOL)
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
            if(!MAJOR.equals(user.getMajor())){
                RetrofitHelper.getAccountSetAPI()
                        .updateMajor(PreferenceUtil.getStringPRIVATE("id",""),MAJOR)
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

        }

    }


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.idcard)
    EditText idcard;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.level)
    EditText level;
    @BindView(R.id.school)
    EditText school;
    @BindView(R.id.major)
    EditText major;
    @BindView(R.id.education)
    EditText education;

    @BindView(R.id.title)
    TextView title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_set;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        title.setText("帐号设置");
        title.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
        textRight.setText("保存");
        textRight.setTextColor(Color.WHITE);
        getUserData();
    }

    @SuppressLint("CheckResult")
    private void getUserData() {
        dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setMessage("数据拉取中...");
        dialog.show();
        RetrofitHelper.getAccountSetAPI()
                .toUpdateUserInfo(PreferenceUtil.getStringPRIVATE("id",""))
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            ToastUtil.ShortToast("获取成功");
                            JSONObject json=JSON.parseObject(apiMsg.getResultInfo()) ;
                            user= JSON.parseObject(json.getString("user"),User.class);
                            if(user!=null){
                                username.setText(user.getUserName());
                                name.setText(user.getRealName());
                                idcard.setText(user.getIdCard());
                                phone.setText(user.getTelePhone());
                                sex.setText(user.getSex());
                                address.setText(user.getAddress());
                                level.setText(user.getPoliticalStatus());
                                school.setText(user.getSchool());
                                major.setText(user.getMajor());
                                education.setText(user.getEducation());

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


}
