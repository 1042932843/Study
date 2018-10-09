package com.dy.ustudyonline.Design.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.studyonline.R;


/**
 * Name: DialogTheme
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 自定义dialog
 * Date: 2017-04-12 00:44
 */

public class DialogLoading extends DialogFragment {
    public static final String TAG = DialogLoading.class.getSimpleName();
    private TextView messageTv;//消息提示文本
    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageTv = (TextView) view.findViewById(R.id.message);
        messageTv.setText(message);
        messageTv.setTextColor(Color.WHITE);
    }
    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message=message;
    }

}
