package com.dy.ustudyonline.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.dy.ustudyonline.Base.DuskyApp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * SP缓存工具类
 */
public final class PreferenceUtil {

  public static void reset(final Context context) {

    Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
    edit.clear();
    edit.apply();
  }

  public static void resetPrivate(){
    SharedPreferences sharedPreferences = DuskyApp.getInstance().getSharedPreferences("preference_mu", MODE_PRIVATE);
    Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();
  }


  public static String getString(String key, String defValue) {

    return PreferenceManager.getDefaultSharedPreferences(DuskyApp.getInstance())
        .getString(key, defValue);
  }


  public static long getLong(String key, long defValue) {

    return PreferenceManager.getDefaultSharedPreferences(DuskyApp.getInstance())
        .getLong(key, defValue);
  }


  public static float getFloat(String key, float defValue) {

    return PreferenceManager.getDefaultSharedPreferences(DuskyApp.getInstance())
        .getFloat(key, defValue);
  }


  public static void put(String key, String value) {

    putString(key, value);
  }


  public static void put(String key, int value) {

    putInt(key, value);
  }


  public static void put(String key, float value) {

    putFloat(key, value);
  }


  public static void put(String key, boolean value) {

    putBoolean(key, value);
  }


  private static void putFloat(String key, float value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            DuskyApp.getInstance());
    Editor editor = sharedPreferences.edit();
    editor.putFloat(key, value);
    editor.apply();
  }


  public static SharedPreferences getPreferences() {

    return PreferenceManager.getDefaultSharedPreferences(DuskyApp.getInstance());
  }


  public static int getInt(String key, int defValue) {

    return PreferenceManager.getDefaultSharedPreferences(DuskyApp.getInstance())
        .getInt(key, defValue);
  }


  public static boolean getBoolean(String key, boolean defValue) {

    return PreferenceManager.getDefaultSharedPreferences(DuskyApp.getInstance())
        .getBoolean(key, defValue);
  }


  public static void putStringPRIVATE(String key, String value) {

    SharedPreferences sharedPreferences = DuskyApp.getInstance().getSharedPreferences("preference_mu", MODE_PRIVATE);
    Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }


  public static String getStringPRIVATE(String key, String defValue) {

    SharedPreferences sharedPreferences = DuskyApp.getInstance()
        .getSharedPreferences("preference_mu", MODE_PRIVATE);
    return sharedPreferences.getString(key, defValue);
  }


  public static boolean hasString(String key) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            DuskyApp.getInstance());
    return sharedPreferences.contains(key);
  }


  private static void putString(String key, String value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            DuskyApp.getInstance());
    Editor editor = sharedPreferences.edit();
    editor.putString(key, value);
    editor.apply();
  }


  public static void putLong(String key, long value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            DuskyApp.getInstance());
    Editor editor = sharedPreferences.edit();
    editor.putLong(key, value);
    editor.apply();
  }


  public static void putBoolean(String key, boolean value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            DuskyApp.getInstance());
    Editor editor = sharedPreferences.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }


  private static void putInt(String key, int value) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            DuskyApp.getInstance());
    Editor editor = sharedPreferences.edit();
    editor.putInt(key, value);
    editor.apply();
  }


  public static void remove(String... keys) {

    if (keys != null) {
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
              DuskyApp.getInstance());
      Editor editor = sharedPreferences.edit();
      for (String key : keys) {
        editor.remove(key);
      }
      editor.apply();
    }
  }
}
