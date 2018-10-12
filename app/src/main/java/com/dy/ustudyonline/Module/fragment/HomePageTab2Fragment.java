package com.dy.ustudyonline.Module.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Base.BaseFragment;

import butterknife.BindView;

/**
 * Name: HomePageTab1Fragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //选课
 * Date: 2018-08-29 11:02
 */
public class HomePageTab2Fragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    public HomePageTab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_tab2;
    }

    @Override
    public void finishCreateView(Bundle state) {
        title.setText("选课中心");
        title.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
