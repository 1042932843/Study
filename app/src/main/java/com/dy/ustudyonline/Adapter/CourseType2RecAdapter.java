package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.DataTab2Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/24
 * @DESCRIPTION:
 */
public class CourseType2RecAdapter extends RecyclerView.Adapter<CourseType2RecAdapter.ViewHolder>{

    private List<DataTab2Item> home;
    private Context context;

    public CourseType2RecAdapter(List<DataTab2Item> home, Context context) {
        this.home = home;
        this.context=context;

    }

    public void updateData(List<DataTab2Item> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coursetype_reclist_item, parent, false);
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
        holder.checkbox.setVisibility(home.get(position).isShow()?View.VISIBLE:View.GONE);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onItemCheckedListener.Checked(position,isChecked);
            }
        });
            DataTab2Item item=home.get(position);
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

        if("0".equals(item.getIsSelect())){
            holder.btn.setBackgroundColor(context.getResources().getColor((R.color.colorPrimary)));
            holder.btn.setText("已选课");
        }else{
            holder.btn.setBackgroundColor(context.getResources().getColor((R.color.possible_result_points)));
            holder.btn.setText("+选课");
        }


        if(TextUtils.isEmpty(item.getCourseImg())){
            holder.btn.setVisibility(View.GONE);
            Glide.with(context).load(item.getImageUrl()).into(holder.img1);
        }else{
            Glide.with(context).load(item.getCourseImg()).into(holder.img1);
        }


        holder.cname.setText(item.getCourseName());
        holder.score.setText(item.getCoursePoint()+"学分");
        holder.time.setText(le);
        holder.students.setText(item.getAmount()+"人学习");
        holder.l1.setOnClickListener(onClickListener);
        holder.l1.setVisibility(View.VISIBLE);



    }

    @Override
    public int getItemCount() {
        return home == null ? 0 : home.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView btn,cname,score,time,students;
        ImageView img1;
        LinearLayout l1;
        CheckBox checkbox;


        public ViewHolder(View itemView) {
            super(itemView);
            checkbox=(CheckBox)itemView.findViewById(R.id.checkbox);

            l1=(LinearLayout)itemView.findViewById(R.id.l1) ;

            btn = (TextView) itemView.findViewById(R.id.btn);

            cname=(TextView) itemView.findViewById(R.id.cname);

            score=(TextView) itemView.findViewById(R.id.score);

            time=(TextView) itemView.findViewById(R.id.time);

            students=(TextView) itemView.findViewById(R.id.students);

            img1=(ImageView) itemView.findViewById(R.id.img1);


        }
    }


    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(String id);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }
    OnItemCheckedListener onItemCheckedListener;
    public interface OnItemCheckedListener{
        void Checked(int position,boolean checked);
    }
    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener ){
        this.onItemCheckedListener=onItemCheckedListener;
    }


}

