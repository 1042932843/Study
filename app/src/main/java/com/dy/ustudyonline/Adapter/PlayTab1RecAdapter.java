package com.dy.ustudyonline.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(home.get(position));
            }
        });
        // 绑定数据
        final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator+ "UStudy"+File.separator+home.get(position).getChapter_name()+".mp4";
        File file = new File(PATH);
        if(file.exists()){
            holder.cProgressButton.normal(2);
            home.get(position).setLocalUrl(PATH);
        }else{
            holder.cProgressButton.normal(0);
        }
            holder.cProgressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.cProgressButton.getResultString().equals("")){
                        FileDownloader.getImpl().pause(home.get(position).getDownloaderId());
                    }

                    if(holder.cProgressButton.getResultString().equals("完成")) {

                        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        //    设置Title的图标
                        builder.setIcon(R.drawable.ic_launcher);
                        //    设置Title的内容
                        builder.setTitle("U学");
                        //    设置Content来显示一个信息
                        builder.setMessage("需要删除此章节的本地视频文件吗？");
                        //    设置一个PositiveButton
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(delete(PATH)){
                                    holder.cProgressButton.normal(0);
                                    home.get(position).setLocalUrl("");
                                }
                            }
                        });
                        //    设置一个NegativeButton
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        });
                        //    显示出该对话框
                        builder.show();
                    }
                    if(holder.cProgressButton.getResultString().equals("暂停")||holder.cProgressButton.getResultString().equals("下载")){
                        //创建文件夹 UStudy，在存储卡下
                      int id = FileDownloader.getImpl().create(home.get(position).getChapter_url())
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
                                        if(progress>98){
                                            progress=100;
                                        }
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
                        home.get(position).setDownloaderId(id);
                    }

                }
            });
        holder.title.setText(home.get(position).getChapter_name());

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

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(PlayItem item);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }


    private boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            Toast.makeText(context, "删除文件失败:" + delFile + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
                return deleteSingleFile(delFile);
        }
    }

    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {

                return false;
            }
        } else {

            return false;
        }
    }


}

