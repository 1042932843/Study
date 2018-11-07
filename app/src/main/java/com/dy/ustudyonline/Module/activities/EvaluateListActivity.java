package com.dy.ustudyonline.Module.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.EvaluateListRecAdapter;
import com.dy.ustudyonline.Adapter.FragmentAdapter;
import com.dy.ustudyonline.Adapter.InformationRecAdapter;
import com.dy.ustudyonline.Base.BaseActivity;
import com.dy.ustudyonline.Design.MyRadioBt.ChoiceGroup;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
import com.dy.ustudyonline.Module.entity.EvaluateItem;
import com.dy.ustudyonline.Module.fragment.PlayEvaluteFragment;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EvaluateListActivity extends BaseActivity {

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @OnClick(R.id.imgLeft)
    public void back(){
        finish();
    }
    @BindView(R.id.title)
    TextView apptitle;

    @BindView(R.id.Rate)
    TextView Rate;

    List<String> val=new ArrayList<>();

    @BindView(R.id.choiceGroup)
    ChoiceGroup choiceGroup;

    @OnClick(R.id.tip)
    public void jump(){
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tip)RelativeLayout tip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWidget();
        initRefreshLayout();
        choiceGroup.setColumn(4);
        choiceGroup.setValues(val);
        choiceGroup.setOnItemClickListener(new ChoiceGroup.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }



    @SuppressLint("CheckResult")
    public void loadData() {
        RetrofitHelper.getPlayAPI()
                .peCommentList(getIntent().getStringExtra("courseTerraceId"))
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    String a=bean.string();
                    //{"gcRate":"92.3076923076923%","message":"获取全部评价成功","count":13,"state":"0000","dcount":0,"mcount":1,"gcount":12}
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            if("获取全部评价成功".equals(apiMsg.getMessage())){
                                val.clear();
                                JSONObject object=JSONObject.parseObject(a);
                                String rate=object.getString("gcRate");
                                String count=object.getString("count");
                                String dcount=object.getString("dcount");
                                String mcount=object.getString("mcount");
                                String gcount=object.getString("gcount");
                                Rate.setText("好评率"+rate);
                                val.add("全部("+count+")");
                                val.add("好评("+gcount+")");
                                val.add("中评("+mcount+")");
                                val.add("差评("+dcount+")");
                                choiceGroup.setView(this);
                                choiceGroup.setInitChecked(0);
                                initViewPager();
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





    protected void initViewPager() {

            List<String> titles = new ArrayList<>();
            List<Fragment> mFragments = new ArrayList<>();
            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, titles);

            mFragments.add(PlayEvaluteFragment.newInstance(1));
            mFragments.add(PlayEvaluteFragment.newInstance(2));
            mFragments.add(PlayEvaluteFragment.newInstance(3));
            mFragments.add(PlayEvaluteFragment.newInstance(4));
            viewPager.setAdapter(adapter);

            viewPager.setOffscreenPageLimit(mFragments.size());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    choiceGroup.setInitChecked(i);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });


    }

    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);

            loadData();
        });
    }
    private void initWidget() {
        apptitle.setText("全部评价");
        apptitle.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.back);
    }


}


