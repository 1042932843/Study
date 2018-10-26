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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.AnsRecAdapter;
import com.dy.ustudyonline.Adapter.InformationRecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Design.keyEditText.KeyEditText;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RelistActivity extends BaseActivity {
    PlayDataTab3Item item;
    String courseTerraceId;
    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    @BindView(R.id.titlev)
    TextView titlev;
    @BindView(R.id.com)
    TextView com;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.count)
    TextView count;

    @BindView(R.id.value)
    KeyEditText value;
    @BindView(R.id.send)
    ImageView send;
    @OnClick(R.id.send)
    public void ans(){
        if(TextUtils.isEmpty(value.getText().toString())){
            ToastUtil.ShortToast("什么都没写哦");
            return;
        }
        answer(value.getText().toString());
    }

    List<PlayDataTab3Item> datas=new ArrayList<>();
    AnsRecAdapter adapter;

    @BindView(R.id.recyclerview)RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_relist;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        item=(PlayDataTab3Item)getIntent().getSerializableExtra("item");
        courseTerraceId=getIntent().getStringExtra("courseTerraceId");
        titlev.setText(item.getTitle());
        com.setText(item.getComment());
        username.setText(item.getUserName());
        time.setText(item.getTs());
        initWidget();
        initRecyclerView();
        loadData();

    }



    @SuppressLint("CheckResult")
    public void loadData() {
        RetrofitHelper.getPlayAPI()
                .qesCommentList(item.getQesId())
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    //{"message":"获取评论列表成功","count":0,"state":"0000","ansMapList":[]}
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONObject object= JSON.parseObject(a);
                            String co=object.getString("count");
                            count.setText("全部回复 "+co);
                            JSONArray array=object.getJSONArray("ansMapList");
                            int size=array.size();
                            if(size>0){

                                for(int i=0;i<size;i++){
                                    PlayDataTab3Item item=JSON.parseObject(array.get(i).toString(),PlayDataTab3Item.class);
                                    datas.add(item);
                                }
                                adapter.notifyDataSetChanged();

                            }else{

                            }

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

    protected ProgressDialog pdialog;
    @SuppressLint("CheckResult")
    public void answer(String com){
        String s= PreferenceUtil.getStringPRIVATE("id", "");
        pdialog = new ProgressDialog(RelistActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        pdialog.setMessage("提交中...");
        pdialog.show();
        RetrofitHelper.getPlayAPI()
                .qesComment(com,s,item.getQesId())
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
                            value.setText("");
                            value.clearFocus();
                            datas.clear();
                            loadData();
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

    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new AnsRecAdapter(datas,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initWidget() {
        apptitle.setText("回复");
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }


}


