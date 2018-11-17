package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.CourseType2RecAdapter;
import com.dy.ustudyonline.Adapter.InformationRecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab2Item;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.lzy.imagepicker.util.Utils;
import com.lzy.imagepicker.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CourseType2Activity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    List<DataTab2Item> datas=new ArrayList<>();
    List<DataTab2Item> Checked=new ArrayList<>();
    CourseType2RecAdapter adapter;

    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.tip)RelativeLayout tip;
    @BindView(R.id.textRight)TextView textRight;
    @BindView(R.id.ok)TextView ok;
    @BindView(R.id.okl)RelativeLayout okl;
    @OnClick(R.id.ok)
    public void ok(){
        StringBuilder courseTerrace_ids= new StringBuilder();
        if(Checked.size()<=0){
            ToastUtil.ShortToast("您没有选择任何课程.");
            return;
        }
        for(int i=0;i<Checked.size();i++){
            courseTerrace_ids.append(Checked.get(i).getCourseTerraceId()).append(",");
        }
        choseCourse(courseTerrace_ids.toString());
    }

    @OnClick(R.id.textRight)
    public void bianji(){
        int size=datas.size();
        if("批量选课".equals(textRight.getText())){
            for (int i=0;i<size;i++){
                datas.get(i).setShow(true);
            }
            adapter.notifyDataSetChanged();
            textRight.setText("取消");
            show(okl);
        }else{
            for (int i=0;i<size;i++){
                datas.get(i).setShow(false);
            }
            adapter.notifyDataSetChanged();
            textRight.setText("批量选课");
            hide(okl);
        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_coursetype2;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
        initRecyclerView();
    }



    @SuppressLint("CheckResult")
    public void loadData() {
        String courseTypeId=getIntent().getStringExtra("courseTypeId");
        RetrofitHelper.gethomePageTab2API()
                .choseCourseThirdPage(PreferenceUtil.getStringPRIVATE("id",""),courseTypeId)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONArray array=JSON.parseArray(apiMsg.getResultInfo());
                            int size=array.size();
                            if(size>0){
                                Checked.clear();
                                datas.clear();
                                tip.setVisibility(View.GONE);
                                for(int i=0;i<size;i++){
                                    DataTab2Item item=JSON.parseObject(array.get(i).toString(),DataTab2Item.class);
                                    datas.add(item);
                                }
                                adapter.notifyDataSetChanged();

                            }else{
                                tip.setVisibility(View.VISIBLE);
                            }

                            break;
                        case "-1":
                        case "-2":
                        default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }

                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }


    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new CourseType2RecAdapter(datas,this);
        adapter.setOnItemClickListener(new CourseType2RecAdapter.OnItemClickListener() {
            @Override
            public void onClick(String id) {
                Intent intent=new Intent(CourseType2Activity.this, IntroductionActivity.class);
                intent.putExtra("courseTerraceId",id);
                startActivity(intent);
            }
        });
        adapter.setOnItemCheckedListener(new CourseType2RecAdapter.OnItemCheckedListener() {
            @Override
            public void Checked(int position, boolean checked) {
                datas.get(position).setSelect(checked);
                if(checked){
                    Checked.add(datas.get(position));
                }else{
                    Checked.remove(datas.get(position));
                }

            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initRefreshLayout();
    }

    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            textRight.setText("批量选课");
            hide(okl);
            loadData();
        });
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            textRight.setText("批量选课");
            hide(okl);
            loadData();
        });
    }
    private void initWidget() {
        apptitle.setText(getIntent().getStringExtra("courseTypeName"));
        apptitle.setTextColor(Color.WHITE);
        textRight.setText("批量选课");
        hide(okl);
        textRight.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }

    protected ProgressDialog pdialog;
    @SuppressLint("CheckResult")
    public void choseCourse(String courseTerrace_ids){
        pdialog = new ProgressDialog(CourseType2Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
        pdialog.setMessage("正在帮你选课...");
        pdialog.show();
        RetrofitHelper.gethomePageTab2API()
                .choseCourse(PreferenceUtil.getStringPRIVATE("id",""),courseTerrace_ids)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            ToastUtil.ShortToast("选课成功，您可以前往 学习 页面查看相关课程");
                            mSwipeRefreshLayout.post(() -> {
                                mSwipeRefreshLayout.setRefreshing(true);
                                textRight.setText("批量选课");
                                hide(okl);
                                loadData();
                            });
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
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int ANIMATION_DURATION = 600;//动画执行时长
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).
                setInterpolator(INTERPOLATOR).setDuration(ANIMATION_DURATION);
        animator.start();
    }

    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).
                setInterpolator(INTERPOLATOR).
                setDuration(ANIMATION_DURATION);
        animator.start();

    }
}


