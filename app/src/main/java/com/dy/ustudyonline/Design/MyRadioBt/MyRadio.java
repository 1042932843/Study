package com.dy.ustudyonline.Design.MyRadioBt;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/25
 * @DESCRIPTION:
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dy.studyonline.R;


public class MyRadio extends android.support.v7.widget.AppCompatTextView implements View.OnTouchListener{

    private boolean isTouched = false;//是否被按下

    private int touch = 1;//按钮被按下的次数

    public MyRadio(Context context){
        super(context);
        init();
    }

    public MyRadio(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }

    public MyRadio(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
        init();
    }

    protected void init(){
        this.setTextSize(12);
        this.setMaxLines(1);
        this.setEllipsize(TextUtils.TruncateAt.END);
        this.setPadding(16,24,16,24);
        this.setGravity(Gravity.CENTER);
        setOnTouchListener(this);
    }

    public void setTouch(int touch){
        this.touch = touch;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(0 == touch%2){
            this.setBackgroundResource(R.drawable.btn_theme_solid_bg);//bg_round_corner_line_bg
            this.setTextColor(Color.WHITE);
        }else {
            this.setBackgroundResource(R.drawable.bg_round_corner_line_bg_theme);
            this.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        invalidate();
    }

    public void setTouched(boolean isTouched){
        this.isTouched = isTouched;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onValueChangedListner.OnValueChanged(this.getText().toString());
                isTouched = true;
                touch ++;
                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                break;
        }
        return true;
    }

    public interface OnValueChangedListner{
        void OnValueChanged(String value);
    }

    //实现接口，方便将当前按钮的值回调
    OnValueChangedListner onValueChangedListner;

    public void setOnValueChangedListner(OnValueChangedListner onValueChangedListner){
        this.onValueChangedListner = onValueChangedListner;
    }
}

