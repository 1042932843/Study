package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.DataTab1Item;
import com.dy.ustudyonline.Module.entity.DataTab3Item;
import com.dy.ustudyonline.Net.ApiConstants;

import java.math.BigDecimal;
import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class Tab3RecAdapter extends RecyclerView.Adapter<Tab3RecAdapter.ViewHolder>{

    private List<DataTab3Item> home;
    private Context context;

    public Tab3RecAdapter(List<DataTab3Item> home, Context context) {
        this.home = home;
        this.context=context;
    }

    public void updateData(List<DataTab3Item> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reclist_item1, parent, false);
        // 实例化viewholder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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
        holder.name.setText(home.get(position).getCourseName());
        holder.content.setText(home.get(position).getCourseTeacher());
        float a2 = Float.parseFloat(home.get(position).getStudyLenth());
        BigDecimal b2   =   new BigDecimal(a2/60);
        a2=b2.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();
        String le2=" 已学习"+a2 +"小时";
        holder.time2.setText(le2);
        float a = Float.parseFloat(home.get(position).getVideoLenth());
        BigDecimal b   =   new BigDecimal(a/60);
        a=b.setScale(1,   BigDecimal.ROUND_HALF_UP).floatValue();
        String le="总共约"+a +"小时";
        holder.students2.setText(le);
        Glide.with(context).load(home.get(position).getImgUrl()).into(holder.img);
        //单独对应类型的设置事件
        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });
            holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return home == null ? 0 : home.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,content,time2,students2;
        ImageView img;
        CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkbox=(CheckBox)itemView.findViewById(R.id.checkbox);
            name = (TextView) itemView.findViewById(R.id.name);
            content=(TextView) itemView.findViewById(R.id.content);
            time2 = (TextView) itemView.findViewById(R.id.time2);
            students2 = (TextView) itemView.findViewById(R.id.students2);
            img=(ImageView) itemView.findViewById(R.id.img);

        }
    }


    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
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

