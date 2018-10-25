package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class PlayTab3RecAdapter extends RecyclerView.Adapter<PlayTab3RecAdapter.ViewHolder>{

    private List<PlayDataTab3Item> home;
    private Context context;

    public PlayTab3RecAdapter(List<PlayDataTab3Item> home, Context context) {
        this.home = home;
        this.context=context;
    }

    public void updateData(List<PlayDataTab3Item> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.infor_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(home==null||home.get(position)==null){
            return;
        }
        // 绑定数据

        //单独对应类型的设置事件
        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return home == null ? 2 : home.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title1,time1;

        public ViewHolder(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.title1);
            time1=(TextView) itemView.findViewById(R.id.time1);

        }
    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }
}

