package com.dy.ustudyonline.Module.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Utils.LogUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.dy.ustudyonline.zxing.android.CaptureActivity;
import com.dy.ustudyonline.zxing.common.Constant;

import butterknife.BindView;
import butterknife.OnClick;

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                LogUtil.d("扫描结果为：" + content);

            }
        }
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
