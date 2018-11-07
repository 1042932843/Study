package com.dy.ustudyonline.Module.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.EvaluateListRecAdapter;
import com.dy.ustudyonline.Adapter.PlayTab1RecAdapter;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.entity.ApiMsg;
import com.dy.ustudyonline.Module.entity.EvaluateItem;
import com.dy.ustudyonline.Module.entity.PlayItem;
import com.dy.ustudyonline.Net.RetrofitHelper;
import com.dy.ustudyonline.Utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Name: PlayTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //课件
 * Date: 2018-10-15 11:02
 */
public class PlayEvaluteFragment extends BaseFragment {

    private  List<EvaluateItem> playItems =new ArrayList<>();
    private  int Rank;
    EvaluateListRecAdapter adapter;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    public static PlayEvaluteFragment newInstance(int Rank) {
        PlayEvaluteFragment fragment=  new PlayEvaluteFragment();
        fragment.Rank=Rank;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @SuppressLint("CheckResult")
    public void loadData(){
        RetrofitHelper.getPlayAPI()
                .rkCommentList(getActivity().getIntent().getStringExtra("courseTerraceId"),Rank)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    //{"message":"获取全部好评成功","state":"0000","gCommetList":[{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 16:06:42","userName":"1194489644","comment":"云区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。主要包括如下模块：大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的顺利流转。叫号评价模块：主要处理叫号、评价、结果录入等面对窗口人员的业务交互模块，同时需与后台凄凄切切群"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 15:49:54","userName":"1194489644","comment":"云区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。主要包括如下模块：大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的顺利流转。叫号评价模块：主要处理叫号、评价、结果录入等面对窗口人员的业务交互模块，同时需与后台凄凄切切群"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 15:49:18","userName":"1194489644","comment":"区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。主要包括如下模块：大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的云凄凄切切"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 15:46:29","userName":"1194489644","comment":"云区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。主要包括如下模块：大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的我问问"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 15:46:07","userName":"1194489644","comment":"主要包括如下模块：大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的云区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。\r\n主要包括如下模块：\r\n"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 15:43:34","userName":"1194489644","comment":"云区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。\r\n主要包括如下模块：\r\n大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的11"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-31 15:43:22","userName":"1194489644","comment":"云区政务服务管理办公室政务审批平台升级改造项目主要是对接北京市政务审批系统，形成大厅相关引导叫号、运营管理、人员分流、运营统计等全生命流程管理、引导平台，为提高大厅办事效率、办事用户满意度而建设的。\r\n主要包括如下模块：\r\n大厅智能引导系统：主要处理引导、办事指南查询、大厅信息展示、进度查询等面对办事用户的前端交互模块。同时需与后台运营系统、叫号评价系统以及相关展示模块进行数据交互，保证各系统间业务流程的"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-30 16:25:00","userName":"1194489644","comment":"henhao "},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"0","ts":"2018-10-29 10:22:38","userName":"1194489644","comment":"好"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"1","ts":"2018-10-27 12:13:23","userName":"1194489644","comment":"If re "},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"0","ts":"2018-10-25 17:07:52","userName":"1194489644","comment":"哈哈哈"},{"imgUrl":"http://119.146.222.170:9082/subpfv32/common/images/userImage/119448964420181011150107.jpg","rank":"0","ts":"2018-10-25 16:55:56","userName":"1194489644","comment":"good!"}]}
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            JSONObject object=JSONObject.parseObject(a);
                            JSONArray array=new JSONArray();
                            if(object.getJSONArray("gCommetList")!=null){
                                array=object.getJSONArray("gCommetList");
                            }
                            if(object.getJSONArray("peCommetList")!=null){
                                array=object.getJSONArray("peCommetList");
                            }
                            if(object.getJSONArray("mdCommetList")!=null){
                                array=object.getJSONArray("mdCommetList");
                            }
                            if(array.size()>0){
                                for (int i=0;i<array.size();i++){
                                    EvaluateItem item=JSONObject.parseObject(array.get(i).toString(),EvaluateItem.class);
                                    playItems.add(item);
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

    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new EvaluateListRecAdapter(playItems,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_evaluate;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
