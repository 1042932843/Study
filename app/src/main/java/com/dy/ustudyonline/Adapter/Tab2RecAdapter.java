package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.DataTab2;
import com.dy.ustudyonline.Module.entity.DataTab2Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class Tab2RecAdapter extends RecyclerView.Adapter<Tab2RecAdapter.ViewHolder>{

    private List<DataTab2> home;
    private Context context;

    public Tab2RecAdapter(List<DataTab2> home, Context context) {
        this.home = home;
        this.context=context;

    }

    public void updateData(List<DataTab2> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab2_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {
        if(home==null||home.get(position)==null){
            return;
        }
        // 绑定数据
        holder.totaltitle.setText(home.get(position).getCourseTypeName());
        holder.expall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpAllClickListener.onClick(home.get(position).getCourseTypeId(),home.get(position).getCourseTypeName());
            }
        });
        int size=home.get(position).getCourseList().size();

        for(int i=0;i<size;i++){
            DataTab2Item item=home.get(position).getCourseList().get(i);
            if(item==null){
                return;
            }
            View.OnClickListener onClickListener= new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(item.getCourseTerraceId());
                    }
                }
            };

            float a = Float.parseFloat(item.getLength());
            BigDecimal   b   =   new BigDecimal(a/60);
            a=b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();
            String le=" 约"+a +"小时";
            switch (i){
                 case 0:
                     if("0".equals(item.getIsSelect())){
                         holder.btn.setBackgroundColor(context.getResources().getColor((R.color.colorPrimary)));
                         holder.btn.setText("已选课");
                     }else{
                         holder.btn.setBackgroundColor(context.getResources().getColor((R.color.possible_result_points)));
                         holder.btn.setText("+选课");
                     }


                     Glide.with(context).load(item.getCourseImg()).into(holder.img1);

                     holder.cname.setText(item.getCourseName());
                     holder.score.setText(item.getCoursePoint()+"学分");
                     holder.time.setText(le);
                     holder.students.setText(item.getAmount()+"人学习");
                     holder.l1.setOnClickListener(onClickListener);
                     holder.l1.setVisibility(View.VISIBLE);
                     break;
                case 1:
                    if("0".equals(item.getIsSelect())){
                        holder.btn2.setBackgroundColor(context.getResources().getColor((R.color.colorPrimary)));
                        holder.btn2.setText("已选课");
                    }else{
                        holder.btn2.setBackgroundColor(context.getResources().getColor((R.color.possible_result_points)));
                        holder.btn2.setText("+选课");
                    }


                    Glide.with(context).load(item.getCourseImg()).into(holder.img2);

                    holder.cname2.setText(item.getCourseName());
                    holder.score2.setText(item.getCoursePoint()+"学分");
                    holder.time2.setText(le);
                    holder.students2.setText(item.getAmount()+"人学习");
                    holder.l2.setOnClickListener(onClickListener);
                    holder.l2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    if("0".equals(item.getIsSelect())){
                        holder.btn3.setText("已选课");
                        holder.btn3.setBackgroundColor(context.getResources().getColor((R.color.colorPrimary)));

                    }else{
                        holder.btn3.setBackgroundColor(context.getResources().getColor((R.color.possible_result_points)));
                        holder.btn3.setText("+选课");
                    }

                    Glide.with(context).load(item.getCourseImg()).into(holder.img3);

                    holder.cname3.setText(item.getCourseName());
                    holder.score3.setText(item.getCoursePoint()+"学分");
                    holder.time3.setText(le);
                    holder.students3.setText(item.getAmount()+"人学习");
                    holder.l3.setOnClickListener(onClickListener);
                    holder.l3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    if("0".equals(item.getIsSelect())){
                        holder.btn4.setText("已选课");
                        holder.btn4.setBackgroundColor(context.getResources().getColor((R.color.colorPrimary)));

                    }else{
                        holder.btn4.setBackgroundColor(context.getResources().getColor((R.color.possible_result_points)));
                        holder.btn4.setText("+选课");
                    }

                    Glide.with(context).load(item.getCourseImg()).into(holder.img4);

                    holder.cname4.setText(item.getCourseName());
                    holder.score4.setText(item.getCoursePoint()+"学分");
                    holder.time4.setText(le);
                    holder.students4.setText(item.getAmount()+"人学习");
                    holder.l4.setOnClickListener(onClickListener);
                    holder.l4.setVisibility(View.VISIBLE);
                    break;
            }
            if(i>=3){
                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return home == null ? 0 : home.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView totaltitle,btn,btn2,btn3,btn4,cname,cname2,cname3,cname4,score,score2,score3,score4,time,time2,time3,time4,students,students2,students3,students4;
        ImageView img1,img2,img3,img4;
        LinearLayout l1,l2,l3,l4;
        TextView expall;

        public ViewHolder(View itemView) {
            super(itemView);
            expall=(TextView)itemView.findViewById(R.id.expall);
            l1=(LinearLayout)itemView.findViewById(R.id.l1) ;
            l2=(LinearLayout)itemView.findViewById(R.id.l2) ;
            l3=(LinearLayout)itemView.findViewById(R.id.l3) ;
            l4=(LinearLayout)itemView.findViewById(R.id.l4) ;
            totaltitle=(TextView)itemView.findViewById(R.id.totaltitle);
            btn = (TextView) itemView.findViewById(R.id.btn);
            btn2=(TextView) itemView.findViewById(R.id.btn2);
            btn3=(TextView) itemView.findViewById(R.id.btn3);
            btn4=(TextView) itemView.findViewById(R.id.btn4);
            cname=(TextView) itemView.findViewById(R.id.cname);
            cname2=(TextView) itemView.findViewById(R.id.cname2);
            cname3=(TextView) itemView.findViewById(R.id.cname3);
            cname4=(TextView) itemView.findViewById(R.id.cname4);
            score=(TextView) itemView.findViewById(R.id.score);
            score2=(TextView) itemView.findViewById(R.id.score2);
            score3=(TextView) itemView.findViewById(R.id.score3);
            score4=(TextView) itemView.findViewById(R.id.score4);
            time=(TextView) itemView.findViewById(R.id.time);
            time2=(TextView) itemView.findViewById(R.id.time2);
            time3=(TextView) itemView.findViewById(R.id.time3);
            time4=(TextView) itemView.findViewById(R.id.time4);
            students=(TextView) itemView.findViewById(R.id.students);
            students2=(TextView) itemView.findViewById(R.id.students2);
            students3=(TextView) itemView.findViewById(R.id.students3);
            students4=(TextView) itemView.findViewById(R.id.students4);

            img1=(ImageView) itemView.findViewById(R.id.img1);
            img2=(ImageView) itemView.findViewById(R.id.img2);
            img3=(ImageView) itemView.findViewById(R.id.img3);
            img4=(ImageView) itemView.findViewById(R.id.img4);

        }
    }
    OnExpAllClickListener onExpAllClickListener;
    public interface OnExpAllClickListener{
        void onClick(String type,String name);
    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(String id);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }

    public void setOnExpAllClickListener(OnExpAllClickListener onExpAllClickListener ){
        this.onExpAllClickListener=onExpAllClickListener;
    }


}

