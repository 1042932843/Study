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
import com.dy.ustudyonline.Module.activities.MainActivity;
import com.dy.ustudyonline.Module.entity.DataTab4Item;
import com.dy.ustudyonline.Module.entity.EvaluateItem;

import java.util.List;

import static com.dy.ustudyonline.Base.DuskyApp.optionsRoundedCircle;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class EvaluateListRecAdapter extends RecyclerView.Adapter<EvaluateListRecAdapter.ViewHolder>{

    private List<EvaluateItem> home;
    private Context context;

    public EvaluateListRecAdapter(List<EvaluateItem> home, Context context) {
        this.home = home;
        this.context=context;
    }

    public void updateData(List<EvaluateItem> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluate_item, parent, false);
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
        holder.title1.setText(home.get(position).getUserName());
        String rank=home.get(position).getRank();
        switch (rank){
            case "0":
            rank="评价等级：优";
                break;
            case "1":
                rank="评价等级：良";
                break;
            case "2":
                rank="评价等级：中";
                break;
            case "3":
                rank="评价等级：差";
                break;
        }
        holder.rank.setText(rank);
        holder.ts.setText(home.get(position).getTs());
        holder.time1.setText(home.get(position).getComment());
        Glide.with(context).load(home.get(position).getImgUrl()).apply(optionsRoundedCircle).into(holder.userhead);
        //单独对应类型的设置事件
        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onItemClickListener.onClick(home.get(position).getNewsId(),home.get(position).getNewsTitle());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return home == null ? 2 : home.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title1,time1,rank,ts;
        ImageView userhead;

        public ViewHolder(View itemView) {
            super(itemView);
            userhead=(ImageView)itemView.findViewById(R.id.user_head) ;
            title1 = (TextView) itemView.findViewById(R.id.title1);
            rank = (TextView) itemView.findViewById(R.id.rank);
            ts = (TextView) itemView.findViewById(R.id.ts);
            time1=(TextView) itemView.findViewById(R.id.time1);

        }
    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(String id, String title);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }
}

