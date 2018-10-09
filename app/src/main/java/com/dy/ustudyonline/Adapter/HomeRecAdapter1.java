package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.HomeRecItem1;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class HomeRecAdapter1 extends RecyclerView.Adapter<HomeRecAdapter1.ViewHolder>{

    private List<HomeRecItem1> home;
    private Context context;

    public HomeRecAdapter1(List<HomeRecItem1> home, Context context) {
        this.home = home;
        this.context=context;
    }

    public void updateData(List<HomeRecItem1> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reclist_item1, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /*if(home==null||home.get(position)==null){
            return;
        }*/
        // 绑定数据
        holder.name.setText("测试数据");
        holder.content.setText("每天五分钟，写出不一样的代码。高效的啊数据库的撒娇的三大");
        Glide.with(context).load(R.drawable.banner43x).into(holder.img);
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
        return home == null ? 2 : home.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,content;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            content=(TextView) itemView.findViewById(R.id.content);
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
}

