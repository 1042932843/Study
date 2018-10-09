package com.dy.ustudyonline.Adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.Banner;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.P;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/8/31
 * @DESCRIPTION:
 */
public class UltraPagerTwoCenterTypeAdapter extends PagerAdapter {
    List<Banner> banners =new ArrayList<>();
    private UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener onBannerItemClickListener;
    Context context;
    // 监听事件
    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }
    public UltraPagerTwoCenterTypeAdapter(List<Banner> banners, Context context) {
        this.banners.addAll(banners);
        this.context=context;

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    public void setOnBannerItemClickListener(UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.two_center_banner_item, null);
        LinearLayout l1=(LinearLayout) linearLayout.findViewById(R.id.l1);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick((2*position+1)-1);
                }
            }
        });
        ImageView imageView1 = (ImageView) linearLayout.findViewById(R.id.img1);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(R.drawable.banner3x).into(imageView1);
        TextView cname1=(TextView)  linearLayout.findViewById(R.id.cname);
        TextView score1=(TextView)  linearLayout.findViewById(R.id.score);
        TextView time1=(TextView)  linearLayout.findViewById(R.id.time);
        TextView students1=(TextView)  linearLayout.findViewById(R.id.students);

        LinearLayout l2=(LinearLayout) linearLayout.findViewById(R.id.l2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(2*(position+1)-1);
                }
            }
        });
        ImageView imageView2 = (ImageView) linearLayout.findViewById(R.id.img2);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(R.drawable.banner33x).into(imageView2);
        TextView cname2=(TextView)  linearLayout.findViewById(R.id.cname2);
        TextView score2=(TextView)  linearLayout.findViewById(R.id.score2);
        TextView time2=(TextView)  linearLayout.findViewById(R.id.time2);
        TextView students2=(TextView)  linearLayout.findViewById(R.id.students2);


        container.addView(linearLayout);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout;
        //new LinearLayout(container.getContext());

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
