package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.DataTab4;
import com.dy.ustudyonline.Module.entity.DataTab4Item;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class Tab4RecAdapter extends RecyclerView.Adapter<Tab4RecAdapter.ViewHolder>{

    private List<DataTab4> home;
    private Context context;

    public Tab4RecAdapter(List<DataTab4> home, Context context) {
        this.home = home;
        this.context=context;

    }

    public void updateData(List<DataTab4> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab4_item, parent, false);
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
        holder.totaltitle.setText(home.get(position).getTypeName());
        holder.expall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpAllClickListener.onClick(home.get(position).getTypeId(),home.get(position).getTypeName());
            }
        });
        int size=home.get(position).getNewsList().size();

        for(int i=0;i<size;i++){
            DataTab4Item item=home.get(position).getNewsList().get(i);
            if(item==null){
                return;
            }
            View.OnClickListener onClickListener= new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(item.getNewsId(),item.getNewsTitle());
                    }
                }
            };

            switch (i){
                 case 0:
                     holder.l1.setOnClickListener(onClickListener);
                     holder.l1.setVisibility(View.VISIBLE);
                     holder.title1.setText(item.getNewsTitle());
                     holder.time1.setText(item.getCreateTime());
                     break;
                case 1:
                    holder.l2.setOnClickListener(onClickListener);
                    holder.l2.setVisibility(View.VISIBLE);
                    holder.title2.setText(item.getNewsTitle());
                    holder.time2.setText(item.getCreateTime());
                    break;
                case 2:
                    holder.l3.setOnClickListener(onClickListener);
                    holder.l3.setVisibility(View.VISIBLE);
                    holder.title3.setText(item.getNewsTitle());
                    holder.time3.setText(item.getCreateTime());
                    break;
                case 3:
                    holder.l4.setOnClickListener(onClickListener);
                    holder.l4.setVisibility(View.VISIBLE);
                    holder.title4.setText(item.getNewsTitle());
                    holder.time4.setText(item.getCreateTime());
                    break;
                case 4:
                    holder.l5.setOnClickListener(onClickListener);
                    holder.l5.setVisibility(View.VISIBLE);
                    holder.title5.setText(item.getNewsTitle());
                    holder.time5.setText(item.getCreateTime());
                    break;
            }
            if(i>=4){
                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return home == null ? 0 : home.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView totaltitle,title1,title2,title3,title4,title5,time1,time2,time3,time4,time5;
        RelativeLayout l1,l2,l3,l4,l5;
        TextView expall;

        public ViewHolder(View itemView) {
            super(itemView);
            expall=(TextView)itemView.findViewById(R.id.expall);
            l1=(RelativeLayout)itemView.findViewById(R.id.l1);
            l2=(RelativeLayout)itemView.findViewById(R.id.l2);
            l3=(RelativeLayout)itemView.findViewById(R.id.l3);
            l4=(RelativeLayout)itemView.findViewById(R.id.l4);
            l5=(RelativeLayout)itemView.findViewById(R.id.l5);
            totaltitle=(TextView)itemView.findViewById(R.id.totaltitle);
            title1=(TextView)itemView.findViewById(R.id.title1);
            title2=(TextView)itemView.findViewById(R.id.title2);
            title3=(TextView)itemView.findViewById(R.id.title3);
            title4=(TextView)itemView.findViewById(R.id.title4);
            title5=(TextView)itemView.findViewById(R.id.title5);
            time1=(TextView)itemView.findViewById(R.id.time1);
            time2=(TextView)itemView.findViewById(R.id.time2);
            time3=(TextView)itemView.findViewById(R.id.time3);
            time4=(TextView)itemView.findViewById(R.id.time4);
            time5=(TextView)itemView.findViewById(R.id.time5);


        }
    }
    OnExpAllClickListener onExpAllClickListener;
    public interface OnExpAllClickListener{
        void onClick(String type, String name);
    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(String id,String title);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }

    public void setOnExpAllClickListener(OnExpAllClickListener onExpAllClickListener ){
        this.onExpAllClickListener=onExpAllClickListener;
    }


}

