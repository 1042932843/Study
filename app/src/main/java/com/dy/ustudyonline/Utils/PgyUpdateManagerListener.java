
package com.dy.ustudyonline.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.dy.studyonline.BuildConfig;
import com.dy.studyonline.R;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.UpdateManagerListener;

public class PgyUpdateManagerListener extends UpdateManagerListener {
    private static final int PERMISSION_REQUEST_CODE = 1; //权限请求码
    final Activity activity;

    public PgyUpdateManagerListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onNoUpdateAvailable() {

    }

    @Override
    public void onUpdateAvailable(String s) {
        final AppBean bean = getAppBeanFromString(s);
        if ((!StringUtil.matchesNumber(bean.getVersionCode()) ||
                Integer.valueOf(bean.getVersionCode()) > BuildConfig.VERSION_CODE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("发现新版本：" + bean.getVersionName());
            builder.setMessage(bean.getReleaseNote());
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            //获取权限后的操作。读取文件
                            startDownloadTask(activity, bean.getDownloadURL());
                        } else {
                            //请求权限
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_CODE);
                        }
                    }else {
                        startDownloadTask(activity, bean.getDownloadURL());
                    }
                }
            });
            builder.setNegativeButton("取消", null);
            builder.setCancelable(false);
            builder.show();
        }


    }

    /**
     * 检测权限是否授权
     *
     * @return
     */
    private boolean checkPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }

}


