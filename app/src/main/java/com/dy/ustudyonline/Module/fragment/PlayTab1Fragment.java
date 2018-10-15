package com.dy.ustudyonline.Module.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseFragment;

import butterknife.BindView;

/**
 * Name: PlayTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //课件
 * Date: 2018-10-15 11:02
 */
public class PlayTab1Fragment extends BaseFragment {

    public PlayTab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_tab1;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
