package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;
import com.dy.ustudyonline.Utils.ToastUtil;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_tab3_item, parent, false);
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
        holder.title1.setText(home.get(position).getTitle());
        holder.commet.setText(home.get(position).getComment());
        holder.ts.setText(home.get(position).getTs());
        holder.username.setText(home.get(position).getUserName());
        //单独对应类型的设置事件
        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onReClick(home.get(position));
                }
            });

        }
        holder.re.setText("回复"+"(" +home.get(position).getAnsCount()+")");
        holder.re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onReClick(home.get(position));
            }
        });
        int count=Integer.parseInt(home.get(position).getClickCount());
        holder.zan.setVisibility(View.VISIBLE);
        boolean iszn=home.get(position).isZaned();
        if(iszn){
            holder.zan.setText("顶"+"(" +count+")");
            holder.zan.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.zan.setText("顶"+"(" +count+")");

        }

        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onZanClick(home.get(position),position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return home == null ? 2 : home.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title1,commet,username,ts,re,zan;

        public ViewHolder(View itemView) {
            super(itemView);
            title1 = (TextView) itemView.findViewById(R.id.title1);
            commet = (TextView) itemView.findViewById(R.id.commet);
            username = (TextView) itemView.findViewById(R.id.username);
            ts=(TextView) itemView.findViewById(R.id.ts);
            re=(TextView) itemView.findViewById(R.id.re);
            zan=(TextView) itemView.findViewById(R.id.zan);

        }
    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onReClick(PlayDataTab3Item item);
        void onZanClick(PlayDataTab3Item item,int p);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }
}

