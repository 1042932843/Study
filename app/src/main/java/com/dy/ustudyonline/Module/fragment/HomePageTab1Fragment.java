package com.dy.ustudyonline.Module.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.HomeRecAdapter1;
import com.dy.ustudyonline.Adapter.HomeRecAdapter2;
import com.dy.ustudyonline.Adapter.UltraPagerAdapter;
import com.dy.ustudyonline.Adapter.UltraPagerTwoCenterTypeAdapter;
import com.dy.ustudyonline.Adapter.helper.FullyLinearLayoutManager;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.IntroductionActivity;
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.entity.Banner;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.tmall.ultraviewpager.UltraViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dy.ustudyonline.Module.activities.MainActivity.refreshData;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //首页
 * Date: 2018-08-29 11:02
 */
public class HomePageTab1Fragment extends BaseFragment {


    @OnClick(R.id.imgLeft)
    public void open(){
        EventBus.getDefault().post(MainActivity.openDrawer);
    }

    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRefreshing = false;

    @BindView(R.id.ultra_viewpager)
    UltraViewPager ultraViewPager;
    UltraPagerAdapter ultraPagerAdapter;
    List<Banner> banners;

    /**
     * 最新课程模块
     */
    @BindView(R.id.ultra_viewpagernew)
    UltraViewPager ultraViewPager1;
    UltraPagerTwoCenterTypeAdapter ultraPagerTwoCenterTypeAdapter;
    List<Banner> ultraViewPager1datas;

    /**
     * 最热课程模块
     */
    @BindView(R.id.ultra_viewpagernewhot)
    UltraViewPager ultraViewPager2;
    UltraPagerTwoCenterTypeAdapter ultraPagerTwoCenterTypeAdapterhot;
    List<Banner> ultraViewPager2datas;

    /**
     * 喜欢课程模块
     */
    @BindView(R.id.ultra_viewpagernewlove)
    UltraViewPager ultraViewPager3;
    UltraPagerTwoCenterTypeAdapter ultraPagerTwoCenterTypeAdapterlove;
    List<Banner> ultraViewPager3datas;


    /**
     * 学历提升
     */
    @BindView(R.id.recyclerview1)
    RecyclerView recyclerView1;
    HomeRecAdapter1 adapter1;

    /**
     * 平台先修模块
     */
    @BindView(R.id.recyclerview2)
    RecyclerView recyclerView2;
    HomeRecAdapter2 adapter2;

    @BindView(R.id.imgLeft)
    ImageView imgLeft;
    @BindView(R.id.imgRight)
    ImageView imageRight;

    @BindView(R.id.title)
    TextView title;

    public HomePageTab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab1;
    }

    @Override
    public void finishCreateView(Bundle state) {
        title.setText("首页");
        title.setTextColor(Color.WHITE);
        imgLeft.setImageResource(R.drawable.mine);
        imageRight.setImageResource(R.drawable.msg);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mIsRefreshing = true;
            mSwipeRefreshLayout.setRefreshing(false);
        });
        initBanner();
        initModule();
        initRecyclerView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String cmd) {
        switch (cmd) {
            case refreshData:

                break;
        }
    }

    private void initModule() {
        ultraViewPager1.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager1.setMultiScreen(0.9f);
        ultraViewPager1.setInfiniteLoop(true);
        ultraViewPager1datas=new ArrayList<>();
        ultraPagerTwoCenterTypeAdapter = new UltraPagerTwoCenterTypeAdapter(ultraViewPager1datas,getContext());
        ultraPagerTwoCenterTypeAdapter.setOnBannerItemClickListener(new UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.ShortToast("点击了"+position);
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }
        });
        ultraViewPager1.setAdapter(ultraPagerTwoCenterTypeAdapter);

        ultraViewPager2.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager2.setMultiScreen(0.9f);
        ultraViewPager2.setInfiniteLoop(true);
        ultraViewPager2datas=new ArrayList<>();
        ultraPagerTwoCenterTypeAdapterhot = new UltraPagerTwoCenterTypeAdapter(ultraViewPager2datas,getContext());
        ultraPagerTwoCenterTypeAdapterhot.setOnBannerItemClickListener(new UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.ShortToast("点击了"+position);
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }
        });
        ultraViewPager2.setAdapter(ultraPagerTwoCenterTypeAdapterhot);

        ultraViewPager3.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager3.setMultiScreen(0.9f);
        ultraViewPager3.setInfiniteLoop(true);
        ultraViewPager3datas=new ArrayList<>();
        ultraPagerTwoCenterTypeAdapterlove = new UltraPagerTwoCenterTypeAdapter(ultraViewPager3datas,getContext());
        ultraPagerTwoCenterTypeAdapterlove.setOnBannerItemClickListener(new UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.ShortToast("点击了"+position);
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }
        });
        ultraViewPager3.setAdapter(ultraPagerTwoCenterTypeAdapterlove);
    }

    private void initRecyclerView(){
        FullyLinearLayoutManager linearLayoutManager= new FullyLinearLayoutManager(getActivity());
        linearLayoutManager.setScrollEnabled(false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        adapter1=new HomeRecAdapter1(null,getActivity());
        recyclerView1.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new HomeRecAdapter1.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        FullyLinearLayoutManager linearLayoutManager2= new FullyLinearLayoutManager(getActivity());
        linearLayoutManager2.setScrollEnabled(false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        adapter2=new HomeRecAdapter2(null,getActivity());
        recyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new HomeRecAdapter2.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    private void initBanner() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setMargin(0,0,0,16)
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getResources().getColor(R.color.colorPrimary))
                .setNormalColor(getResources().getColor(R.color.point))
                .setRadius((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
//set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();

        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setAutoScroll(10000);
        banners=new ArrayList<>();
        ultraPagerAdapter = new UltraPagerAdapter(banners,getContext());
        ultraPagerAdapter.setOnBannerItemClickListener(new UltraPagerAdapter.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                startActivity(intent);
            }
        });
        ultraViewPager.setAdapter(ultraPagerAdapter);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
