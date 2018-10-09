package com.dy.ustudyonline.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Name: BaseActivity
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-06-25 21:22
 */
public abstract class BaseFragment extends RxFragment implements View.OnTouchListener{

  public View parentView;

  //标志位 fragment是否可见
  protected boolean isVisible;

  private Unbinder bind;


  public abstract
  @LayoutRes
  int getLayoutResId();


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
    parentView = inflater.inflate(getLayoutResId(), container, false);

    return parentView;
  }


  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {

    super.onViewCreated(view, savedInstanceState);
    bind = ButterKnife.bind(this, view);
    view.setOnTouchListener(this);
    finishCreateView(savedInstanceState);
  }


  public abstract void finishCreateView(Bundle state);


  @Override
  public void onResume() {
    super.onResume();
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    bind.unbind();
  }


  @Override
  public void onAttach(Activity activity) {

    super.onAttach(activity);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  public FragmentActivity getSupportActivity() {

    return super.getActivity();
  }


  /**
   * Fragment数据的懒加载
   */
  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      isVisible = true;
      onVisible();
    } else {
      isVisible = false;
      onInvisible();
    }
  }
  protected void onVisible() {
    lazyLoad();
  }

  protected void lazyLoad() {}

  protected void onInvisible() {}

}
