package com.dy.ustudyonline.Module.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseFragment;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //学习
 * Date: 2018-08-29 11:02
 */
public class HomePageTab3Fragment extends BaseFragment {

    public HomePageTab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab3;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
