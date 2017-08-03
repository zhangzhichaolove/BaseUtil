package com.chao.baselib.base.adapter.holder;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 扩展ViewHolder
 */
public class ExtendViewHolder {
    static ExtendViewHolder instance;
    View view;

    public static ExtendViewHolder getInstance(View textView) {
        instance = new ExtendViewHolder(textView);
        return instance;
    }

//    public ExtendViewHolder view(View textView) {
//        return new ExtendViewHolder(textView);
//    }

    private ExtendViewHolder(View view) {
        this.view = view;
    }


    public ExtendViewHolder setTag(Object tag) {
        view.setTag(tag);
        return this;
    }

    public ExtendViewHolder setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
        return this;
    }


    public ExtendViewHolder setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        }
        return this;
    }

    public ExtendViewHolder setBackgroundColor(int color) {
        view.setBackgroundColor(color);
        return this;
    }

    public ExtendViewHolder setBackgroundResource(@DrawableRes int resid) {
        view.setBackgroundResource(resid);
        return this;
    }

    public ExtendViewHolder setScaleType(ImageView.ScaleType scaleType) {
        if (view instanceof ImageView) {
            ((ImageView) view).setScaleType(scaleType);
        }
        return this;
    }

    public ExtendViewHolder setText(String text) {
        if (view instanceof TextView) {//Button、EditText等控件继承自TextView
            ((TextView) view).setText(text);
        }
        return this;
    }

    public ExtendViewHolder setTextColor(int color) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
        return this;
    }

    public ExtendViewHolder setTextSize(float size) {
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(size);
        }
        return this;
    }
}