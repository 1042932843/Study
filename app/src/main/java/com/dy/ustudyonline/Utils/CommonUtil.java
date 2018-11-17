package com.dy.ustudyonline.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;


import com.dy.studyonline.R;
import com.dy.ustudyonline.Module.activities.LoginActivity;

import java.io.File;

/**
 * Created by dsy on 16/8/4 21:18
 * <p/>
 * 通用工具类
 */
public class CommonUtil {

  /**
   * 检查是否有网络
   */
  public static boolean isNetworkAvailable(Context context) {

    NetworkInfo info = getNetworkInfo(context);
    return info != null && info.isAvailable();
  }

  public static void goLogin(Activity activity) {
    Intent it=new Intent(activity,LoginActivity.class);
    activity.startActivity(it);
  }

  public static void logOut(Activity activity){
    PreferenceUtil.resetPrivate();
    ToastUtil.ShortToast("已退出，请重新登录");
    goLogin(activity);
  }

  /**
   * 当判断当前手机没有网络时选择是否打开网络设置
   * @param context
   */
  public static void showNoNetWorkDlg(final Context context) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setIcon(R.drawable.ic_launcher)         //
            .setTitle(R.string.app_name)            //
            .setMessage("当前无网络,请前往设置").setPositiveButton("设置", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        // 跳转到系统的网络设置界面
        Intent intent = null;
        // 先判断当前系统版本
        if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
          intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        }else{
          intent = new Intent();
          intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);

      }
    }).setNegativeButton("知道了", null);
    builder.show();
  }

  /**
   * 检查是否是WIFI
   */
   public static boolean isWifi(Context context) {

    NetworkInfo info = getNetworkInfo(context);
    if (info != null) {
      if (info.getType() == ConnectivityManager.TYPE_WIFI) {
        return true;
      }
    }
    return false;
  }


  /**
   * 检查是否是移动网络
   */
  public static boolean isMobile(Context context) {

    NetworkInfo info = getNetworkInfo(context);
    if (info != null) {
      if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
        return true;
      }
    }
    return false;
  }


  private static NetworkInfo getNetworkInfo(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE);
    return cm.getActiveNetworkInfo();
  }


  /**
   * 检查SD卡是否存在
   */
  private static boolean checkSdCard() {

    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }


  /**
   * 获取手机SD卡总空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  private static long getSDcardTotalSize() {

    if (checkSdCard()) {
      File path = Environment.getExternalStorageDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = mStatFs.getBlockSizeLong();
      long blockCountLong = mStatFs.getBlockCountLong();

      return blockSizeLong * blockCountLong;
    } else {
      return 0;
    }
  }


  /**
   * 获取SDka可用空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  private static long getSDcardAvailableSize() {

    if (checkSdCard()) {
      File path = Environment.getExternalStorageDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = mStatFs.getBlockSizeLong();
      long availableBlocksLong = mStatFs.getAvailableBlocksLong();
      return blockSizeLong * availableBlocksLong;
    } else {
      return 0;
    }
  }


  /**
   * 获取手机内部存储总空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  public static long getPhoneTotalSize() {

    if (!checkSdCard()) {
      File path = Environment.getDataDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = mStatFs.getBlockSizeLong();
      long blockCountLong = mStatFs.getBlockCountLong();
      return blockSizeLong * blockCountLong;
    } else {
      return getSDcardTotalSize();
    }
  }


  /**
   * 获取手机内存存储可用空间
   */
  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
  public static long getPhoneAvailableSize() {

    if (!checkSdCard()) {
      File path = Environment.getDataDirectory();
      StatFs mStatFs = new StatFs(path.getPath());
      long blockSizeLong = mStatFs.getBlockSizeLong();
      long availableBlocksLong = mStatFs.getAvailableBlocksLong();
      return blockSizeLong * availableBlocksLong;
    } else
      return getSDcardAvailableSize();
  }

  public static boolean isMobileNO(String mobileNums) {
    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     * @param str
     * @return 待检测的字符串
     */
    String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
    if (TextUtils.isEmpty(mobileNums))
      return false;
    else
      return mobileNums.matches(telRegex);
  }

  public static boolean personIdValidation(String text) {
    String regx = "[0-9]{17}x";
    String reg1 = "[0-9]{15}";
    String regex = "[0-9]{18}";
    return text.matches(regx) || text.matches(reg1) || text.matches(regex);
  }
}
