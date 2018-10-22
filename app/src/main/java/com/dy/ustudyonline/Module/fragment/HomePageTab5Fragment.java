package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.LogUtil;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.dy.ustudyonline.zxing.android.CaptureActivity;
import com.dy.ustudyonline.zxing.common.Constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //更多
 * Date: 2018-08-29 11:02
 */
public class HomePageTab5Fragment extends BaseFragment {

    @BindView(R.id.item1)
    RelativeLayout item1;
    @OnClick(R.id.item1)
    public void shequ(){
        //ToastUtil.ShortToast("");
    }

    @BindView(R.id.item2)
    RelativeLayout item2;
    @OnClick(R.id.item2)
    public void pingjia(){

    }

    @BindView(R.id.item3)
    RelativeLayout item3;
    @OnClick(R.id.item3)
    public void jilu(){

    }
    private int REQUEST_CODE_SCAN = 111;
    @BindView(R.id.item4)
    RelativeLayout item4;
    @OnClick(R.id.item4)
    public void qiandao(){
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                LogUtil.d("扫描结果为：" + content);
                String uuid = PreferenceUtil.getStringPRIVATE("id","");
                String encryptingCode = null;
                if(!TextUtils.isEmpty(content)){
                    try {
                    encryptingCode=encrypt("12345678",uuid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RetrofitHelper.getMainAPI()
                            .urlPost(content+encryptingCode)
                            .compose(this.bindToLifecycle())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bean -> {
                                String a=bean.string();
                                JSONObject jsonObject=JSON.parseObject(a);
                               ;
                                if(!TextUtils.isEmpty( jsonObject.getString("info"))){
                                    ToastUtil.ShortToast(jsonObject.getString("info"));
                                }

                            }, throwable -> {
                                ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                            });
                }
            }
        }
    }

    public static String encrypt(String seed, String cleartext) throws Exception {
        String rawKey = Md5(seed);
        byte[] result = encrypt(rawKey.getBytes(), cleartext.getBytes());
        return Base64.encodeToString(result,Base64.DEFAULT);
    }
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    //md5加密
    public static String Md5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
//         System.out.println("result: " + result);//32位的加密
            System.out.println("result: " + buf.toString().substring(8, 24));//16位的加密
        } catch (NoSuchAlgorithmException e) {

        }
        return result;
    }

    @BindView(R.id.title)
    TextView title;

    public HomePageTab5Fragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab5;
    }

    @Override
    public void finishCreateView(Bundle state) {
        title.setText("更多");
        title.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
