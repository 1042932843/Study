package com.dy.ustudyonline.Module.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.PlayTab3RecAdapter;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Name: PlayTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //课件
 * Date: 2018-10-15 11:02
 */
public class PlayTab3Fragment extends BaseFragment {

    private  List<PlayDataTab3Item> playDataTab3Items ;
    PlayTab3RecAdapter adapter;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    public static PlayTab3Fragment newInstance() {
        PlayTab3Fragment fragment=  new PlayTab3Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String cmd) {
        switch (cmd) {
            case "refreshTab1":
                adapter.notifyDataSetChanged();
                break;

        }
    }
    protected void initRecyclerView() {
        //去掉recyclerView动画处理闪屏
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter=new PlayTab3RecAdapter(playDataTab3Items,getContext());
        adapter.setOnItemClickListener(new PlayTab3RecAdapter.OnItemClickListener() {
            @Override
            public void onClick() {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_tab3;
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
