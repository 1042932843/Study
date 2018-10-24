package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.entity.PlayItem;
import com.dy.ustudyonline.Utils.ToastUtil;
import com.jiang.android.pbutton.CProgressButton;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/4/17
 * @DESCRIPTION:
 */
public class PlayTab1RecAdapter extends RecyclerView.Adapter<PlayTab1RecAdapter.ViewHolder>{

    private List<PlayItem> home;
    private Context context;

    public PlayTab1RecAdapter(List<PlayItem> home, Context context) {
        this.home = home;
        this.context=context;

    }

    public void updateData(List<PlayItem> home) {
        this.home.addAll(home);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playtab1_reclist_item, parent, false);
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
        final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator+ "UStudy"+File.separator+home.get(position).getChapter_name()+".mp4";
        if(fileIsExists(PATH)){
            holder.cProgressButton.normal(2);
        }
        holder.title.setText(home.get(position).getChapter_name());
        holder.cProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建文件夹 UStudy，在存储卡下
                FileDownloader.getImpl().create(home.get(position).getChapter_url())
                        .setPath(PATH)
                        .setListener(new FileDownloadListener() {
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                            }

                            @Override
                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                holder.cProgressButton.startDownLoad();
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                int progress=(int)((double)soFarBytes/totalBytes*100);
                                holder.cProgressButton.download(progress);
                            }

                            @Override
                            protected void blockComplete(BaseDownloadTask task) {

                            }

                            @Override
                            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {

                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                holder.cProgressButton.normal(2);
                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                holder.cProgressButton.normal(1);
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                                    holder.cProgressButton.normal(3);
                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {

                            }
                        }).start();



            }
        });
    }

    @Override
    public int getItemCount() {
        return home == null ? 0 : home.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CProgressButton cProgressButton;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            cProgressButton=(CProgressButton)itemView.findViewById(R.id.cProgressButton);
        }
    }
    OnExpAllClickListener onExpAllClickListener;
    public interface OnExpAllClickListener{
        void onClick(String type, String name);
    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(String id, String title);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }

    public void setOnExpAllClickListener(OnExpAllClickListener onExpAllClickListener ){
        this.onExpAllClickListener=onExpAllClickListener;
    }

    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }
}

