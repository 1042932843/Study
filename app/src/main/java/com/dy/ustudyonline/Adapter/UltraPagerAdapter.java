package com.dy.ustudyonline.Adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.Ad;
import com.dy.ustudyonline.Module.entity.Banner;
import com.dy.ustudyonline.Net.ApiConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/8/31
 * @DESCRIPTION:
 */
public class UltraPagerAdapter extends PagerAdapter {
    List<Ad> banners =new ArrayList<>();
    private UltraPagerAdapter.OnBannerItemClickListener onBannerItemClickListener;
    Context context;
    // 监听事件
    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }
    public UltraPagerAdapter(List<Ad> banners, Context context) {
        this.banners.addAll(banners);
        this.context=context;
    }

    public void notifyData(List<Ad> banners){
        this.banners.clear();
        this.banners.addAll(banners);
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    public void setOnBannerItemClickListener(UltraPagerAdapter.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Glide.with(context).load(banners.get(position).getImageUrl()).into(imageView);
        Glide.with(context).load(ApiConstants.Base_URL+banners.get(position).getImageUrl()).into(imageView);
        container.addView(imageView);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return imageView;
        //new LinearLayout(container.getContext());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
    }
}
