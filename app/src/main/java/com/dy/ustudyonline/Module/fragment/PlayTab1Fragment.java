package com.dy.ustudyonline.Module.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Adapter.PlayTab1RecAdapter;
import com.dy.ustudyonline.Base.BaseFragment;
import com.dy.ustudyonline.Module.entity.PlayItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

import static com.dy.ustudyonline.Module.activities.MainActivity.refreshData;

/**
 * Name: PlayTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //课件
 * Date: 2018-10-15 11:02
 */
public class PlayTab1Fragment extends BaseFragment {

    private  List<PlayItem> playItems ;
    PlayTab1RecAdapter adapter;
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    public static PlayTab1Fragment newInstance(List<PlayItem> playItems) {
        PlayTab1Fragment fragment=  new PlayTab1Fragment();
        fragment.playItems=playItems;
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
        adapter=new PlayTab1RecAdapter(playItems,getContext());
        adapter.setOnItemClickListener(new PlayTab1RecAdapter.OnItemClickListener() {
            @Override
            public void onClick(String id, String title) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_tab1;
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
