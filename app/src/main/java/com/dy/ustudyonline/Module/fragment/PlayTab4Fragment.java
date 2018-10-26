package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.PlayTab3RecAdapter;
import com.dy.ustudyonline.Adapter.PlayTab4RecAdapter;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.EvaluateListActivity;
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;
import com.dy.ustudyonline.Module.entity.PlayDataTab4Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Name: PlayTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //课件
 * Date: 2018-10-15 11:02
 */
public class PlayTab4Fragment extends BaseFragment {

    @OnClick(R.id.dowall)
    public void goAll(){
        Intent it=new Intent(getActivity(),EvaluateListActivity.class);
        it.putExtra("courseTerraceId",courseTerraceId);
        startActivity(it);
    }

    @BindView(R.id.num)
    TextView num;

    @BindView(R.id.edittext)
    EditText editText;
    @OnClick(R.id.ok)
    public void Ok(){
        String com=editText.getText().toString();
        if(TextUtils.isEmpty(com)){
            ToastUtil.ShortToast("什么都没写哦");
            return;
        }
        submit(com);
    }
    ProgressDialog pdialog;
    int rank=0;
    String courseTerraceId;
    @SuppressLint("CheckResult")
    private void submit(String c) {
        pdialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
        pdialog.setMessage("提交中...");
        pdialog.show();
        RetrofitHelper.getPlayAPI()
                .peComment(PreferenceUtil.getStringPRIVATE("id",""),rank,courseTerraceId,c)
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
                            editText.setText("");
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

    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroup;


    private  List<PlayDataTab4Item> playDataTab4Item ;
    PlayTab4RecAdapter adapter;
   // @BindView(R.id.recyclerview)RecyclerView recyclerView;
    public static PlayTab4Fragment newInstance(String courseTerraceId) {
        PlayTab4Fragment fragment=  new PlayTab4Fragment();
        fragment.courseTerraceId=courseTerraceId;
        return fragment;
    }


    /*protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new PlayTab4RecAdapter(playDataTab4Item,getContext());


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
    */

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_tab4_kejian;
    }

    @Override
    public void finishCreateView(Bundle state) {
        //initRecyclerView();
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.you:
                        rank=0;
                        break;
                    case R.id.liang:
                        rank=1;
                        break;
                    case R.id.zhong:
                        rank=2;
                        break;
                    case R.id.cha:
                        rank=3;
                        break;
                }

            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
