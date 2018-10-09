package com.dy.ustudyonline.Utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

/**
 * Name: BitmapUtils
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-07-12 15:48
 */

public  class BitmapUtils {
    public static AssetManager assets;
    /**
     * 传入图片名和是否需要透明处理
     * @param s
     * @param b
     * @return
     */
    public static Bitmap loadBitmap(String s, boolean b) {
        InputStream inputStream=null;
        try {
            inputStream= assets.open(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options op=new BitmapFactory.Options();
        if(b){
            op.inPreferredConfig= Bitmap.Config.ARGB_8888;
        }else {
            op.inPreferredConfig= Bitmap.Config.RGB_565;
        }
        Bitmap bitmap= BitmapFactory.decodeStream(inputStream,null,op);
        return bitmap;
    }


    public static Bitmap convertBmp(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight()/3;

        Bitmap convertBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(convertBmp);
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1); //镜像垂直翻转
//  matrix.postScale(-1, 1); //镜像水平翻转
//  matrix.postRotate(-90); //旋转-90度

        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
        cv.drawBitmap(newBmp, new Rect(0, 0,newBmp.getWidth(), newBmp.getHeight()),new Rect(0, 0, w, h), null);
        return convertBmp;
    }



    public static Bitmap createReflectedBitmap(Bitmap resourceBitmap) {
       int ReflectionGap = 0;//原图片于倒影之间的距离
            //源图片
            int width = resourceBitmap.getWidth();
            int height = resourceBitmap.getHeight();
            //生成倒影图片
            //Bitmap source 源图片
            //x,y 生成倒影图片的起始位置  左上角
            //width,height 图片的宽高
            // Matrix m 用来 设置图片的样式 （倒影）
            Matrix matrix = new Matrix();
            // x水平翻转     y垂直翻转    1 正常     -1翻转
            matrix.setScale(1, -1);
            Bitmap refrectionBitmap = Bitmap.createBitmap(resourceBitmap, 0, height/2, width, height/2, matrix, false);
            //带有倒影的图片
            Bitmap bitmap = Bitmap.createBitmap(width, height + height/2, Bitmap.Config.ARGB_8888);
            //创建画布
            Canvas canvas = new Canvas(bitmap);
            //绘制源图片
            canvas.drawBitmap(resourceBitmap, 0, 0, null);
            //绘制   原图片于倒影之间的距离
            Paint defaultPaint = new Paint();
            canvas.drawRect(0, height, width, height + ReflectionGap, defaultPaint);
            //绘制倒影图片
            canvas.drawBitmap(refrectionBitmap, 0, height + ReflectionGap, null);


        return bitmap;
	}

}
