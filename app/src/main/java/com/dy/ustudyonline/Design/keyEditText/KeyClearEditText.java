/*
 * Copyright (C) 2016 Bilibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dy.ustudyonline.Design.keyEditText;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Name: KeyEditText
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2018-07-30 16:28
 */
public class KeyClearEditText extends android.support.v7.widget.AppCompatEditText {
    private KeyPreImeListener mKeyPreImeListener;
    Drawable imgAble;
    public KeyClearEditText(Context context) {
        super(context);
    }

    public KeyClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO:
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:
                if (count < 1) {
                    imgAble=null;
                    setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
                } else {
                    imgAble = context.getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
                    setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO:
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    public KeyClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (mKeyPreImeListener != null) {
                    mKeyPreImeListener.onKeyPreImeUp(keyCode, event);
                }
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setKeyPreImeListener(KeyPreImeListener listener) {
        mKeyPreImeListener = listener;
    }

    public interface KeyPreImeListener {
        void onKeyPreImeUp(int keyCode, KeyEvent event);
    }



    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 130;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
}
}