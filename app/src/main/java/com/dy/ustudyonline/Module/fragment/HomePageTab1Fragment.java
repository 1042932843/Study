package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.HomeRecAdapter1;
import com.dy.ustudyonline.Adapter.HomeRecAdapter2;
import com.dy.ustudyonline.Adapter.UltraPagerAdapter;
import com.dy.ustudyonline.Adapter.UltraPagerTwoCenterTypeAdapter;
import com.dy.ustudyonline.Adapter.helper.FullyLinearLayoutManager;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.CourseType2Activity;
import com.dy.ustudyonline.Module.activities.CourseTypeActivity;
import com.dy.ustudyonline.Module.activities.IntroductionActivity;
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.activities.Tab1CourseType2Activity;
import com.dy.ustudyonline.Module.entity.Ad;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.Banner;
import com.dy.ustudyonline.Module.entity.DataTab1;
import com.dy.ustudyonline.Module.entity.DataTab1Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.tmall.ultraviewpager.UltraViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.dy.ustudyonline.Module.activities.MainActivity.refreshData;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //首页
 * Date: 2018-08-29 11:02
 */
public class HomePageTab1Fragment extends BaseFragment {
    DataTab1 dataTab1;
    String terraceName,haveLearn,coursePoints,realName;

    @OnClick(R.id.imgLeft)
    public void open(){
        EventBus.getDefault().post(MainActivity.openDrawer);
    }
    @OnClick(R.id.imgRight)
    public void msg(){
       ToastUtil.ShortToast("功能开发中");
    }

    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRefreshing = false;

    @BindView(R.id.hotlayout)
    LinearLayout hotlayout;
    @BindView(R.id.rlayout)
    LinearLayout rlayout;
    @BindView(R.id.newlayout)
    LinearLayout newlayout;
    @BindView(R.id.bannerlayout)
    LinearLayout bannerlayout;
    @BindView(R.id.fllayout)
    LinearLayout xianxiu;

    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.terraceName)
    TextView terraceNameTv;
    @BindView(R.id.haveLearn)
    TextView haveLearnTv;
    @BindView(R.id.coursePoints)
    TextView coursePointsTv;
    @BindView(R.id.realName)
    TextView realNameTv;


    @BindView(R.id.ultra_viewpager)
    UltraViewPager ultraViewPager;
    UltraPagerAdapter ultraPagerAdapter;
    List<Ad> banners=new ArrayList<>();

    /**
     * 最新课程模块
     */
    @BindView(R.id.ultra_viewpagernew)
    UltraViewPager ultraViewPager1;
    UltraPagerTwoCenterTypeAdapter ultraPagerTwoCenterTypeAdapter;
    List<DataTab1Item> ultraViewPager1datas=new ArrayList<>();
    @OnClick(R.id.newsmore)
    public void newsmore(){
        Intent intent=new Intent(getActivity(), Tab1CourseType2Activity.class);
        intent.putExtra("type","ts");
        intent.putExtra("courseTypeName","最新课程");
        startActivity(intent);
    }

    @OnClick(R.id.hotmore)
    public void hotmore(){
        Intent intent=new Intent(getActivity(), Tab1CourseType2Activity.class);
        intent.putExtra("type","amout");
        intent.putExtra("courseTypeName","热门课程");
        startActivity(intent);
    }

    @OnClick(R.id.lovemore)
    public void lovemore(){
        Intent intent=new Intent(getActivity(), Tab1CourseType2Activity.class);
        intent.putExtra("type","");
        intent.putExtra("courseTypeName","猜你喜欢");
        startActivity(intent);
    }

    @OnClick(R.id.edu_promore)
    public void edu_promore(){
        Intent intent=new Intent(getActivity(), Tab1CourseType2Activity.class);
        intent.putExtra("type","edu_pro");
        intent.putExtra("courseTypeName","学历提升");
        startActivity(intent);
    }

    @OnClick(R.id.first_learnmore)
    public void first_learnmore(){
        Intent intent=new Intent(getActivity(), Tab1CourseType2Activity.class);
        intent.putExtra("type","first_learn");
        intent.putExtra("courseTypeName","平台先修课");
        startActivity(intent);
    }

    /**
     * 最热课程模块
     */
    @BindView(R.id.ultra_viewpagernewhot)
    UltraViewPager ultraViewPager2;
    UltraPagerTwoCenterTypeAdapter ultraPagerTwoCenterTypeAdapterhot;
    List<DataTab1Item> ultraViewPager2datas=new ArrayList<>();

    /**
     * 喜欢课程模块
     */
    @BindView(R.id.ultra_viewpagernewlove)
    UltraViewPager ultraViewPager3;
    UltraPagerTwoCenterTypeAdapter ultraPagerTwoCenterTypeAdapterlove;
    List<DataTab1Item> ultraViewPager3datas=new ArrayList<>();


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
    List<DataTab1Item> fldatas;


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
        //imageRight.setImageResource(R.drawable.msg);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mIsRefreshing = true;
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });

        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setMargin(0,0,0,16)
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(getResources().getColor(R.color.colorPrimary))
                .setNormalColor(getResources().getColor(R.color.point))
                .setRadius((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()))
                .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM).build();
        ultraViewPager.setInfiniteLoop(true);


        ultraViewPager1.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager1.setMultiScreen(0.9f);
        ultraViewPager1.setInfiniteLoop(true);
        ultraViewPager2.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager2.setMultiScreen(0.9f);
        ultraViewPager2.setInfiniteLoop(true);
        ultraViewPager3.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager3.setMultiScreen(0.9f);
        ultraViewPager3.setInfiniteLoop(true);

        initRecyclerView();
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        RetrofitHelper.gethomePageTab1API()
                .indexall(PreferenceUtil.getStringPRIVATE("id",""),"2")
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    int total=0;
                    switch (state){
                        case "0000":
                            //ToastUtil.ShortToast(apiMsg.getMessage());
                            //{"message":"跳转首页成功","state":"0000","resultInfo":{"hCourseList":[{"amount":"6","count":"2","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201421716950671.JPG","courseTerraceId":"297edff860781d760160e7e383cf02fa","length":"38.0","coursePoint":"0.8","courseName":"跨文化管理之澶渊之盟"},{"amount":"4","count":"11","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CPFWSPG0706144430001.jpg","courseTerraceId":"297edff860781d760160e7e3841d02fc","length":"104.0","coursePoint":"2.1","courseName":"舞动人生"},{"amount":"4","count":"128","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20150427101935120.jpg","courseTerraceId":"297edff862b7d5790162cc2c87f20427","length":"1589.9","coursePoint":"32","courseName":"Photoshop CS6一对一教程"},{"amount":"4","count":"3","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201341614111484.jpg","courseTerraceId":"297edff863010065016324c891dc2816","length":"60.4","coursePoint":"1.2","courseName":"强迫型人格障碍"},{"amount":"3","count":"7","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CPFWSPG0706114140030.jpg","courseTerraceId":"297edff860781d760160e7e383fe02fb","length":"82.3","coursePoint":"1.6","courseName":"职场与成熟女性妆"},{"amount":"3","count":"4","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201322895918848.jpg","courseTerraceId":"297edff860781d760160e7e3846b02fe","length":"114.3","coursePoint":"2.3","courseName":"成就卓越领导力"},{"amount":"2","count":"7","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CPFWSPG0520133005001.jpg","courseTerraceId":"297edff860781d760160e7e37cdd02d2","length":"48.1","coursePoint":"1.0","courseName":"中关村创业大街后传"},{"amount":"2","count":"4","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201334173846561.jpg","courseTerraceId":"297edff860781d760160e7e3844c02fd","length":"114.2","coursePoint":"2.3","courseName":"组织智慧"}],"muCount":0,"nCourseList":[{"amount":"1","count":"6","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CNFWSPB0331202137083.jpg","courseTerraceId":"297edff85f3380d0015f3381c1840001","length":"5.1","coursePoint":"0.1","courseName":"民企形势分析"},{"amount":"0","count":"3","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20161009024727262.jpg","courseTerraceId":"297edff85f3380d0015f3381c25e0002","length":"70.8","coursePoint":"1.4","courseName":"霸道面试秘籍"},{"amount":"0","count":"7","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CNFWSPB0331203646379.jpg","courseTerraceId":"297edff85f3380d0015f3381c28d0003","length":"25.6","coursePoint":"0.5","courseName":"处理男女关系的智慧"},{"amount":"0","count":"5","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20161012091820971.jpg","courseTerraceId":"297edff85f3380d0015f3381c2bc0004","length":"51.1","coursePoint":"1.0","courseName":"如何用SEO思维写简历"},{"amount":"0","count":"7","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CNFWSPB0528164537315.jpg","courseTerraceId":"297edff85f3380d0015f3381c2fa0005","length":"21.0","coursePoint":"0.4","courseName":"企业处理违纪职工败诉的原因"},{"amount":"0","count":"8","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20160303105007969.jpg","courseTerraceId":"297edff85f3380d0015f3381c3290006","length":"170.0","coursePoint":"3.4","courseName":"保教知识与能力之游戏活动的指导"},{"amount":"0","count":"26","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20160812161823128.jpg","courseTerraceId":"297edff85f3380d0015f3381c3480007","length":"552.2","coursePoint":"11","courseName":"2016公务员《申论》"},{"amount":"0","count":"8","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20160303133436489.jpg","courseTerraceId":"297edff85f3380d0015f3381c3770008","length":"145.1","coursePoint":"2.9","courseName":"保教知识与能力之教育评价"}],"rCourseList":[{"amount":"4","count":"3","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201341614111484.jpg","courseTerraceId":"297edff863010065016324c891dc2816","length":"60.4","coursePoint":"1.2","courseName":"强迫型人格障碍"},{"amount":"2","count":"8","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CNSFHIB0925105651030.jpg","courseTerraceId":"297edff863010065016324c892492817","length":"199.7","coursePoint":"4.0","courseName":"阳光心态"},{"amount":"2","count":"4","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/20133279381291.jpg","courseTerraceId":"297edff863010065016324c892c62818","length":"56.2","coursePoint":"1.1","courseName":"道之为物恍兮惚兮"},{"amount":"1","count":"1","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/CPFHOPG0325104101002.jpg","courseTerraceId":"297edff863010065016324c893422819","length":"10.3","coursePoint":"1.0","courseName":"组合25式太极拳"},{"amount":"0","count":"1","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/2013523103820921.jpg","courseTerraceId":"297edff863010065016324c893bf281a","length":"27.9","coursePoint":"0.6","courseName":"广告行销的运用"},{"amount":"1","count":"2","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/COFAGHG0206152151014.jpg","courseTerraceId":"297edff863010065016324c8943c281b","length":"72.9","coursePoint":"1.5","courseName":"消毒大作战"},{"amount":"1","count":"1","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201341514637280.jpg","courseTerraceId":"297edff863010065016324c894b9281c","length":"15.0","coursePoint":"0.3","courseName":"我仰视精神病人"},{"amount":"2","count":"4","imageUrl":"http://61.143.38.10:9088/subpfv32/common/images/courseImage/001001/201351412634145.jpg","courseTerraceId":"297edff863010065016324c883f727fa","length":"55.9","coursePoint":"1.1","courseName":"小穴位大作用"}],"terraceName":"珠海市民终身学习网","haveLearn":"374.18","coursePoints":"6.70","realName":"王丹","adList":[{"dr":"0","id":"297edff859719ec1015971bb86060002","imageUrl":"/commons/images/advertisement/20170106110338.jpg","linkUrl":"#","rank":"","status":"0","terraceCode":"001001","ts":"2017-01-06 11:03:52"},{"dr":"0","id":"297edff85b83bc15015b856fecab0028","imageUrl":"/commons/images/advertisement/20170419165904.jpg","linkUrl":"#","rank":"","status":"0","terraceCode":"001001","ts":"2017-04-19 16:59:16"},{"dr":"0","id":"297edff86071824d0160730721650044","imageUrl":"/commons/images/advertisement/20171220162520.jpg","linkUrl":"#","rank":"","status":"0","terraceCode":"001001","ts":"2017-12-20 16:25:35"}]}}
                            dataTab1= JSON.parseObject(apiMsg.getResultInfo(),DataTab1.class);
                            JSONObject object=JSON.parseObject(apiMsg.getResultInfo());
                            terraceName=object.getString("terraceName");
                            haveLearn=object.getString("haveLearn");
                            coursePoints=object.getString("coursePoints");
                            realName=object.getString("realName");
                            terraceName="欢迎使用"+terraceName;
                            realName=realName+"学员";
                            coursePoints="学分银行："+coursePoints;
                            haveLearn="已获学时："+haveLearn;
                            terraceNameTv.setText(terraceName);
                            realNameTv.setText(PreferenceUtil.getStringPRIVATE("realName","未登录")+"学员");
                            haveLearnTv.setText(haveLearn);
                            coursePointsTv.setText(coursePoints);
                            //ultraPagerAdapter.notifyData(dataTab1.getAdList());
                            //ultraViewPager.refresh();
                            //ultraPagerTwoCenterTypeAdapter.notifyData(dataTab1.getnCourseList());
                            //ultraViewPager1.refresh();
                            //ultraPagerTwoCenterTypeAdapterhot.notifyData(dataTab1.gethCourseList());
                            //ultraViewPager2.refresh();
                            //ultraPagerTwoCenterTypeAdapterlove.notifyData(dataTab1.getrCourseList());
                            //ultraViewPager3.refresh();
                            banners.clear();
                            banners.addAll(dataTab1.getAdList());
                            if(banners.size()<=0){
                                bannerlayout.setVisibility(View.GONE);
                                total++;
                            }else {
                                bannerlayout.setVisibility(View.VISIBLE);
                                initBanner();
                            }

                            ultraViewPager1datas.clear();
                            ultraViewPager1datas.addAll(dataTab1.getnCourseList());
                            ultraViewPager2datas.clear();
                            ultraViewPager2datas.addAll(dataTab1.gethCourseList());
                            ultraViewPager3datas.clear();
                            ultraViewPager3datas.addAll(dataTab1.getrCourseList());
                            if(ultraViewPager1datas.size()<=0){
                                newlayout.setVisibility(View.GONE);
                            }else{
                                newlayout.setVisibility(View.VISIBLE);
                                ultraPagerTwoCenterTypeAdapter = new UltraPagerTwoCenterTypeAdapter(ultraViewPager1datas,getContext());
                                ultraPagerTwoCenterTypeAdapter.setOnBannerItemClickListener(new UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                                        intent.putExtra("courseTerraceId",ultraViewPager1datas.get(position).getCourseTerraceId());
                                        startActivity(intent);
                                    }
                                });
                                ultraViewPager1.setAdapter(ultraPagerTwoCenterTypeAdapter);
                            }
                            if(ultraViewPager2datas.size()<=0){
                                hotlayout.setVisibility(View.GONE);
                                total++;
                            }else{
                                hotlayout.setVisibility(View.VISIBLE);
                                ultraPagerTwoCenterTypeAdapterhot = new UltraPagerTwoCenterTypeAdapter(ultraViewPager2datas,getContext());
                                ultraPagerTwoCenterTypeAdapterhot.setOnBannerItemClickListener(new UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                                        intent.putExtra("courseTerraceId",ultraViewPager2datas.get(position).getCourseTerraceId());
                                        startActivity(intent);
                                    }
                                });
                                ultraViewPager2.setAdapter(ultraPagerTwoCenterTypeAdapterhot);
                            }
                            if(ultraViewPager3datas.size()<=0){
                                rlayout.setVisibility(View.GONE);
                                total++;
                            }else {
                                rlayout.setVisibility(View.VISIBLE);
                                ultraPagerTwoCenterTypeAdapterlove = new UltraPagerTwoCenterTypeAdapter(ultraViewPager3datas,getContext());
                                ultraPagerTwoCenterTypeAdapterlove.setOnBannerItemClickListener(new UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                                        intent.putExtra("courseTerraceId",ultraViewPager3datas.get(position).getCourseTerraceId());
                                        startActivity(intent);
                                    }
                                });
                                ultraViewPager3.setAdapter(ultraPagerTwoCenterTypeAdapterlove);
                            }

                            fldatas.clear();
                            fldatas.addAll(dataTab1.getFlCourseList());
                            if(fldatas.size()<=0){
                                xianxiu.setVisibility(View.GONE);
                                total++;
                            }else {
                                xianxiu.setVisibility(View.VISIBLE);
                                adapter2.notifyDataSetChanged();
                            }
                            if(total>=4){
                                tip.setVisibility(View.VISIBLE);
                            }else{
                                tip.setVisibility(View.GONE);
                            }

                            //ToastUtil.ShortToast("加载成功");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String cmd) {
        switch (cmd) {
            case refreshData:
                mSwipeRefreshLayout.post(() -> {
                    mSwipeRefreshLayout.setRefreshing(true);
                    loadData();
                });
                break;

        }
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
                //intent.putExtra("courseTerraceId",fldatas.get(position).getCourseTerraceId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        FullyLinearLayoutManager linearLayoutManager2= new FullyLinearLayoutManager(getActivity());
        linearLayoutManager2.setScrollEnabled(false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        fldatas=new ArrayList<>();
        adapter2=new HomeRecAdapter2(fldatas);
        recyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new HomeRecAdapter2.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                intent.putExtra("courseTerraceId",fldatas.get(position).getCourseTerraceId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    private void initBanner() {
        ultraViewPager.setAutoScroll(10000);
        ultraPagerAdapter = new UltraPagerAdapter(banners,getContext());
        ultraPagerAdapter.setOnBannerItemClickListener(new UltraPagerAdapter.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
               // Intent intent=new Intent(getActivity(), IntroductionActivity.class);
               // startActivity(intent);
            }
        });
        ultraViewPager.setAdapter(ultraPagerAdapter);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
