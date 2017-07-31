package com.chao.baselib.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.baselib.R;

/**
 * Created by Chao on 2017/7/31.
 */

public class CustomToolbar extends Toolbar {
    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_center;
    private TextView tv_right;


    public CustomToolbar(Context context) {
        this(context, null);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_toolbar, null, false);
        iv_left = contentView.findViewById(R.id.toolbar_ivb_left);
        tv_center = contentView.findViewById(R.id.toolbar_tv_title);
        tv_right = contentView.findViewById(R.id.toolbar_tvb_right);
        iv_right = contentView.findViewById(R.id.toolbar_ivb_right);
        addView(contentView);
    }

    public void setLeftImg(@DrawableRes int resId) {
        iv_left.setImageResource(resId);
    }

    public void setLeftImg(Drawable drawable) {
        iv_left.setImageDrawable(drawable);
    }

    public void setRightImg(@DrawableRes int resId) {
        iv_right.setImageResource(resId);
    }

    public void setRightImg(Drawable drawable) {
        iv_right.setImageDrawable(drawable);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        tv_center.setText(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        tv_center.setText(title);
    }

    public void setRightText(@StringRes int resId) {
        tv_right.setText(resId);
    }

    public void setRightText(CharSequence text) {
        tv_right.setText(text);
    }

    public void setTitleColor(ColorStateList color) {
        tv_center.setTextColor(color);
    }

    public void setTitleColor(int color) {
        tv_center.setTextColor(color);
    }

    public void setRightColor(ColorStateList color) {
        tv_right.setTextColor(color);
    }

    public void setRightColor(@ColorInt int color) {
        tv_right.setTextColor(color);
    }

    public void setLeftImgOnClickListener(View.OnClickListener l) {
        iv_left.setOnClickListener(l);
    }
}
