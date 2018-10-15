package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.JsonReader;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.HomeRecAdapter1;
import com.dy.ustudyonline.Adapter.HomeRecAdapter2;
import com.dy.ustudyonline.Adapter.Tab3RecAdapter;
import com.dy.ustudyonline.Adapter.helper.EndlessRecyclerOnScrollListener;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.activities.IntroductionActivity;
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.DataTab1Item;
import com.dy.ustudyonline.Module.entity.DataTab3Item;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.PreferenceUtil;
import com.dy.ustudyonline.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //学习
 * Date: 2018-08-29 11:02
 */
public class HomePageTab3Fragment extends BaseFragment {
    private int pageNum = 0;
    private int pageSize = 10;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private boolean mIsRefreshing = false;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.tip)RelativeLayout tip;
    @BindView(R.id.textRight)TextView textRight;
    @OnClick(R.id.textRight)
    public void bianji(){
        ToastUtil.ShortToast("功能开发中");
    }
    @OnClick(R.id.tip)
    public void jump(){
        EventBus.getDefault().post(MainActivity.Page2);
    }
    @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
    Tab3RecAdapter homeRecAdapter1;
    List<DataTab3Item> fldatas=new ArrayList<>();
    @BindView(R.id.title)
    TextView title;
    public HomePageTab3Fragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab3;
    }

    @Override
    public void finishCreateView(Bundle state) {
        title.setText("学习");
        title.setTextColor(Color.WHITE);
        textRight.setText("编辑");
        textRight.setTextColor(Color.WHITE);
        initRefreshLayout();
        initRecyclerView();
    }

    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            pageNum = 1;
            mIsRefreshing = true;
            fldatas.clear();
            mEndlessRecyclerOnScrollListener.refresh();
            loadData();
        });
    }

    @SuppressLint("CheckResult")
    private void loadData() {
        RetrofitHelper.gethomePageTab3API()
                .unLearnedCourses(PreferenceUtil.getStringPRIVATE("id",""),pageNum)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mIsRefreshing = false;
                    String a=bean.string();
                    //{"pageSize":10,"totalCount":30,"unLearnedCourses":[{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"跨文化管理之澶渊之盟","coursePoint":"0.8","shareFlag":"","imageUrl":"/common/images/courseImage/001001/201421716950671.JPG","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"职业素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016035551","terraceCode":"000Z01001","CourseType":{"typeName":"职业技能","dr":"0","remark":" ","isExtend":"0","typeCode":"20171016035104","terraceCode":"000Z01001","flag":"","ts":"2017-51-16 15:51:10 ","id":"49d913daafc84815bc0ad8ff77b016a7","orderby":"6","createTime":"2017-10-16 15:51","sort":0},"flag":"","ts":"2017-10-16 15:55","id":"5dec7861dd6241bd8ca79f791523f4d6","orderby":"5","createTime":"2017-10-16 15:55","sort":0},"flag":"","ts":"2017-12-29 10:00:23","amount":10,"sendOut":"1","id":"f0100e85c16c4f9685a0d84c8307da37","courseCode":"20171229100023","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"阎雨"},"studyLength":"165.0","terraceCode":"001001","flag":"1","ts":"2018-09-28 16:35:03","lastPlayTime":"2018-09-28 16:35:03","status":"1","id":"297e6d9c65c13e8d01660eae0dc3036b","videoLength":"2279.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"印度密宗瑜伽","coursePoint":"1.2","shareFlag":"","imageUrl":"/common/images/courseImage/001001/CNSFHIB0923110528010.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"生活百科","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034503","terraceCode":"000Z01001","CourseType":{"typeName":"生活休闲","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034430","terraceCode":"000Z01001","flag":"","ts":"2017-10-16 15:44","id":"bee9a38dd81142688936bf940c2cdd21","orderby":"3","createTime":"2017-10-16 15:44","sort":0},"flag":"","ts":"2017-10-16 15:45","id":"b7401924272b4a68ac17d74bb0d0bca2","orderby":"1","createTime":"2017-10-16 15:45","sort":0},"flag":"","ts":"2018-03-30 09:34:59","amount":1,"sendOut":"1","id":"d8affa11bac0480b82660d1935a9e974","courseCode":"20180330093459","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"杨洋"},"studyLength":"3682.59","terraceCode":"001001","flag":"1","ts":"2018-09-26 09:46:24","lastPlayTime":"2018-09-26 09:46:24","status":"1","id":"297edff862b7d5790162d17a39bc07bf","videoLength":"3667.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"舞动人生","coursePoint":"2.1","shareFlag":"","imageUrl":"/common/images/courseImage/001001/CPFWSPG0706144430001.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"兴趣爱好","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034603","terraceCode":"000Z01001","CourseType":{"typeName":"生活休闲","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034430","terraceCode":"000Z01001","flag":"","ts":"2017-10-16 15:44","id":"bee9a38dd81142688936bf940c2cdd21","orderby":"3","createTime":"2017-10-16 15:44","sort":0},"flag":"","ts":"2017-10-16 15:46","id":"9921a77e4fac421daadb228e2addff27","orderby":"4","createTime":"2017-10-16 15:46","sort":0},"flag":"","ts":"2017-11-21 10:19:58","amount":4,"sendOut":"1","id":"f1cfe7e813fd4ea991978e3f5ccee0c6","courseCode":"20171121101958","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"王广成"},"studyLength":"37.0","terraceCode":"001001","flag":"1","ts":"2018-09-25 09:15:05","lastPlayTime":"2018-09-25 09:15:05","status":"1","id":"297e6d9c65c13e8d0165f9e84c9f035c","videoLength":"6238.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"道之为物恍兮惚兮","coursePoint":"1.1","shareFlag":"","imageUrl":"/common/images/courseImage/001001/20133279381291.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"道德修养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034334","terraceCode":"000Z01001","CourseType":{"typeName":"文化素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034304","terraceCode":"000Z01001","flag":"","ts":"2017-10-16 15:43","id":"dc47ea78fe0a43ad96b4b1c79e24d8e4","orderby":"2","createTime":"2017-10-16 15:43","sort":0},"flag":"","ts":"2017-10-16 15:43","id":"0cd458a9d43c4af6a5f3a76f2189ca51","orderby":"1","createTime":"2017-10-16 15:43","sort":0},"flag":"","ts":"2018-04-28 20:09:23","amount":3,"sendOut":"1","id":"ff8c01387367496bab09f145c1522506","courseCode":"20180428200923","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"李安纲"},"studyLength":"20.0","terraceCode":"001001","flag":"1","ts":"2018-09-20 17:15:38","lastPlayTime":"2018-09-20 17:15:38","status":"1","id":"297e6d9c65c13e8d0165f5d995bf0357","videoLength":"3374.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"销售面对面","coursePoint":"1.7","shareFlag":"","imageUrl":"/common/images/courseImage/001001/Vod_59751.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"职业素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016035551","terraceCode":"000Z01001","CourseType":{"typeName":"职业技能","dr":"0","remark":" ","isExtend":"0","typeCode":"20171016035104","terraceCode":"000Z01001","flag":"","ts":"2017-51-16 15:51:10 ","id":"49d913daafc84815bc0ad8ff77b016a7","orderby":"6","createTime":"2017-10-16 15:51","sort":0},"flag":"","ts":"2017-10-16 15:55","id":"5dec7861dd6241bd8ca79f791523f4d6","orderby":"5","createTime":"2017-10-16 15:55","sort":0},"flag":"","ts":"2018-04-28 20:12:02","amount":1,"sendOut":"1","id":"f17f7be11c6b48478ac7d93b4634a881","courseCode":"20180428201202","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"刘锦全"},"studyLength":"5245.01","terraceCode":"001001","flag":"1","ts":"2018-08-27 17:05:31","lastPlayTime":"2018-09-17 17:01:51","status":"0","id":"297e6d9c6578f54701657aa188970021","videoLength":"5248.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"强迫型人格障碍","coursePoint":"1.2","shareFlag":"","imageUrl":"/common/images/courseImage/001001/201341614111484.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"道德修养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034334","terraceCode":"000Z01001","CourseType":{"typeName":"文化素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034304","terraceCode":"000Z01001","flag":"","ts":"2017-10-16 15:43","id":"dc47ea78fe0a43ad96b4b1c79e24d8e4","orderby":"2","createTime":"2017-10-16 15:43","sort":0},"flag":"","ts":"2017-10-16 15:43","id":"0cd458a9d43c4af6a5f3a76f2189ca51","orderby":"1","createTime":"2017-10-16 15:43","sort":0},"flag":"","ts":"2018-04-28 20:09:52","amount":4,"sendOut":"1","id":"ff523fbe189841b7b023395005103329","courseCode":"20180428200952","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"姬雪松"},"studyLength":"11.0","terraceCode":"001001","flag":"1","ts":"2018-09-12 10:06:20","lastPlayTime":"2018-09-12 10:06:20","status":"1","id":"297e6d9c658f6eef0165a29a356d0135","videoLength":"3626.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"成就卓越领导力","coursePoint":"2.3","shareFlag":"","imageUrl":"/common/images/courseImage/001001/201322895918848.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"职业素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016035551","terraceCode":"000Z01001","CourseType":{"typeName":"职业技能","dr":"0","remark":" ","isExtend":"0","typeCode":"20171016035104","terraceCode":"000Z01001","flag":"","ts":"2017-51-16 15:51:10 ","id":"49d913daafc84815bc0ad8ff77b016a7","orderby":"6","createTime":"2017-10-16 15:51","sort":0},"flag":"","ts":"2017-10-16 15:55","id":"5dec7861dd6241bd8ca79f791523f4d6","orderby":"5","createTime":"2017-10-16 15:55","sort":0},"flag":"","ts":"2017-12-29 10:00:21","amount":3,"sendOut":"1","id":"f3ae163d5fa44300b0bba762fda04c16","courseCode":"20171229100021","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"孙树宏"},"studyLength":"16.0","terraceCode":"001001","flag":"1","ts":"2018-09-12 10:05:50","lastPlayTime":"2018-09-12 10:05:49","status":"1","id":"297e6d9c65c13e8d0165cb86b49c0010","videoLength":"6858.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"1","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"民企形势分析","coursePoint":"0.1","shareFlag":"","imageUrl":"/common/images/courseImage/001001/CNFWSPB0331202137083.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"职业素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016035551","terraceCode":"000Z01001","CourseType":{"typeName":"职业技能","dr":"0","remark":" ","isExtend":"0","typeCode":"20171016035104","terraceCode":"000Z01001","flag":"","ts":"2017-51-16 15:51:10 ","id":"49d913daafc84815bc0ad8ff77b016a7","orderby":"6","createTime":"2017-10-16 15:51","sort":0},"flag":"","ts":"2017-10-16 15:55","id":"5dec7861dd6241bd8ca79f791523f4d6","orderby":"5","createTime":"2017-10-16 15:55","sort":0},"flag":"","ts":"2017-10-16 16:47:34","amount":8,"sendOut":"1","id":"006f9ab837094f0dbb892088a3cec480","courseCode":"20171016164734","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"王兴权"},"studyLength":"157.89","terraceCode":"001001","flag":"1","ts":"2018-08-30 11:23:57","lastPlayTime":"2018-08-30 11:23:57","status":"1","id":"297e6d9c657b1c34016584fd427a00a7","videoLength":"304.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"阳光心态","coursePoint":"4.0","shareFlag":"","imageUrl":"/common/images/courseImage/001001/CNSFHIB0925105651030.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"道德修养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034334","terraceCode":"000Z01001","CourseType":{"typeName":"文化素养","dr":"0","remark":"","isExtend":"0","typeCode":"20171016034304","terraceCode":"000Z01001","flag":"","ts":"2017-10-16 15:43","id":"dc47ea78fe0a43ad96b4b1c79e24d8e4","orderby":"2","createTime":"2017-10-16 15:43","sort":0},"flag":"","ts":"2017-10-16 15:43","id":"0cd458a9d43c4af6a5f3a76f2189ca51","orderby":"1","createTime":"2017-10-16 15:43","sort":0},"flag":"","ts":"2018-04-28 20:11:01","amount":2,"sendOut":"1","id":"ff6d0f4689074b76b30b60a045f41a7f","courseCode":"20180428201101","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"孙健升"},"studyLength":"10338.35","terraceCode":"001001","flag":"1","ts":"2018-08-27 17:16:59","lastPlayTime":"2018-08-27 17:20:17","status":"0","id":"297e6d9c6578f54701657aac0a43002b","videoLength":"11980.0","userName":"1194489644"},{"dr":"0","remark":"","course":{"eduPro":"","firstLearn":"","dr":"0","stopShare":"","remark":"","isChecked":0,"courseName":"消毒大作战","coursePoint":"1.5","shareFlag":"","imageUrl":"/common/images/courseImage/001001/COFAGHG0206152151014.jpg","courseLevel":"","createUser":"adminZ01001","terraceCode":"000Z01001","CourseType":{"typeName":"养生保健","dr":"0","remark":"","isExtend":"0","typeCode":"20171016035239","terraceCode":"000Z01001","CourseType":{"typeName":"老年学堂","dr":"0","remark":"","isExtend":"0","typeCode":"20171016035044","terraceCode":"000Z01001","flag":"","ts":"2017-10-16 15:50","id":"63a623a6f12a41af9205a7bca220da93","orderby":"5","createTime":"2017-10-16 15:50","sort":0},"flag":"","ts":"2017-10-16 15:52","id":"cce1a813432e4984b1d77d3169918f78","orderby":"1","createTime":"2017-10-16 15:52","sort":0},"flag":"","ts":"2018-04-28 20:07:46","amount":1,"sendOut":"1","id":"ffdb0bf554374ef28a18af9f43d2ff47","courseCode":"20180428200746","isShare":"1","sendOutFlag":"","recovery":"0","courseTeacher":"叶俏"},"studyLength":"2189.78","terraceCode":"001001","flag":"1","ts":"2018-08-30 14:20:25","lastPlayTime":"","status":"1","id":"297e6d9c657b1c340165897d769c00c4","videoLength":"4376.0","userName":"1194489644"}]}
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONObject object=JSON.parseObject(apiMsg.getResultInfo());
                            JSONArray array=object.getJSONArray("unLearnedCourses");
                            int size=array.size();
                            if(size>0){
                                tip.setVisibility(View.GONE);
                                for(int i=0;i<size;i++){
                                    DataTab3Item item=JSON.parseObject(array.get(i).toString(),DataTab3Item.class);
                                    fldatas.add(item);
                                }
                                homeRecAdapter1.notifyDataSetChanged();
                                //fldatas.addAll();
                                pageNum++;
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
                    mIsRefreshing = false;
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
    }

    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        homeRecAdapter1=new Tab3RecAdapter(fldatas,getContext());
        homeRecAdapter1.setOnItemClickListener(new Tab3RecAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(getActivity(), IntroductionActivity.class);
                intent.putExtra("courseTerraceId",fldatas.get(position).getTerraceCode());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeRecAdapter1);

        mEndlessRecyclerOnScrollListener =new EndlessRecyclerOnScrollListener(layoutManager) {
            @SuppressLint("CheckResult")
            @Override
            public void onLoadMore(int currentPage) {
                loadData();
            }
        };
        recyclerView.addOnScrollListener(mEndlessRecyclerOnScrollListener);
        setRecycleNoScroll();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private void setRecycleNoScroll() {
        recyclerView.setOnTouchListener((View v, MotionEvent event) -> {
            return mIsRefreshing;
        });
    }
}
