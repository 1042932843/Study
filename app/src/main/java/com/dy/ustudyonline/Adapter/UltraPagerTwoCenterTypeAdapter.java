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
import com.dy.ustudyonline.Module.entity.DataTab1Item;
import com.dy.ustudyonline.Net.ApiConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.P;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/8/31
 * @DESCRIPTION:
 */
public class UltraPagerTwoCenterTypeAdapter extends PagerAdapter {
    List<DataTab1Item> banners =new ArrayList<>();
    private UltraPagerTwoCenterTypeAdapter.OnBannerItemClickListener onBannerItemClickListener;
    Context context;
    // 监听事件
    public interface OnBannerItemClickListener {
        void onItemClick(int position);
    }
    public UltraPagerTwoCenterTypeAdapter(List<DataTab1Item> banners, Context context) {
        this.banners.addAll(banners);
        this.context=context;

    }

    public void notifyData(List<DataTab1Item> banners){
        this.banners.clear();
        this.banners.addAll(banners);
    }

    @Override
    public int getCount() {
        return banners.size() / 2;
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
        Glide.with(context).load(banners.get((2*position+1)-1).getImageUrl()).into(imageView1);
        TextView cname1=(TextView)  linearLayout.findViewById(R.id.cname);
        cname1.setText(banners.get((2*position+1)-1).getCourseName());
        TextView score1=(TextView)  linearLayout.findViewById(R.id.score);
        String sc=banners.get((2*position+1)-1).getCoursePoint()+"学分";
        score1.setText(sc);
        TextView time1=(TextView)  linearLayout.findViewById(R.id.time);
        float a = Float.parseFloat(banners.get((2*position+1)-1).getLength());
        BigDecimal   b   =   new BigDecimal(a/60);
        a=b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();
        String le="约"+a +"小时";
        time1.setText(le);
        TextView students1=(TextView)  linearLayout.findViewById(R.id.students);
        String am=banners.get((2*position+1)-1).getAmount()+"人学习";
        students1.setText(am);

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
        Glide.with(context).load(banners.get(2*(position+1)-1).getImageUrl()).into(imageView2);
        TextView cname2=(TextView)  linearLayout.findViewById(R.id.cname2);
        cname2.setText(banners.get(2*(position+1)-1).getCourseName());
        TextView score2=(TextView)  linearLayout.findViewById(R.id.score2);
        String sc2=banners.get(2*(position+1)-1).getCoursePoint()+"学分";
        score2.setText(sc2);
        TextView time2=(TextView)  linearLayout.findViewById(R.id.time2);
        float aa = Float.parseFloat(banners.get(2*(position+1)-1).getLength());
        BigDecimal   bb   =   new BigDecimal(aa/60);
        aa=bb.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();
        String le2="约"+aa+"小时";
        time2.setText(le2);
        TextView students2=(TextView)  linearLayout.findViewById(R.id.students2);
        String am2=banners.get(2*(position+1)-1).getAmount()+"人学习";
        students2.setText(am2);


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
