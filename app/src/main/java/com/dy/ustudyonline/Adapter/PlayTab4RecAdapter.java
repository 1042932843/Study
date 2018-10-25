package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Design.MyRadioBt.ChoiceGroup;
import com.dy.ustudyonline.Module.entity.PlayDataTab3Item;
import com.dy.ustudyonline.Module.entity.PlayDataTab4Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class PlayTab4RecAdapter extends RecyclerView.Adapter<PlayTab4RecAdapter.ViewHolder>{

    private List<PlayDataTab4Item> home;
    private Context context;

    public PlayTab4RecAdapter(List<PlayDataTab4Item> home, Context context) {
        this.home = home;
        this.context=context;
    }

    public void updateData(List<PlayDataTab4Item> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playtab4_reclist_item, parent, false);
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
        List<String> list = new ArrayList<String>();
        list.add("非常满意");
        list.add("基本满意");
        list.add("不满意");
        holder.title.setText(position+1+home.get(position).getComment());
        holder.choiceGroup.setColumn(3);//设置列数
        holder.choiceGroup.setValues(list);//设置记录列表
        holder.choiceGroup.setView(context);//设置视图
        holder.choiceGroup.setInitChecked(0);//设置最初默认被选按钮

        holder.choiceGroup.getCurrentValue();//获取当前被选择的按钮值

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
        ChoiceGroup choiceGroup;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            choiceGroup = (ChoiceGroup) itemView.findViewById(R.id.choiceGroup);
            title=(TextView) itemView.findViewById(R.id.title);

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

