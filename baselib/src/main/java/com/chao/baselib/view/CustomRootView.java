package com.chao.baselib.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 多布局根View
 * Created by Chao on 2017/8/3.
 */

public class CustomRootView extends FrameLayout {
    private int emptyRes = -1;
    private int loadingRes = -1;
    private View contentView;
    private View emptyView;
    private View loadingView;


    public CustomRootView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRootView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEmptyRes(int res) {
        emptyRes = res;
    }

    public void setLoadingRes(int res) {
        loadingRes = res;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }

    /**
     * Empty view
     */
    public void showEmpty() {
        if (emptyRes != -1 && emptyView == null) {
            emptyView = LayoutInflater.from(getContext()).inflate(emptyRes, null);
            addView(emptyView);
        }
        if (contentView.isShown()) {
            contentView.setVisibility(GONE);
        }
        if (loadingView != null && loadingView.isShown()) {
            loadingView.setVisibility(GONE);
        }
        emptyView.setVisibility(VISIBLE);
    }


    /**
     * Loading view
     */
    public void showLoading() {
        if (loadingRes != -1 && loadingView == null) {
            loadingView = LayoutInflater.from(getContext()).inflate(loadingRes, null);
            addView(loadingView);
        }
        if (contentView.isShown()) {
            contentView.setVisibility(GONE);
        }
        if (emptyView != null && emptyView.isShown()) {
            emptyView.setVisibility(GONE);
        }
        loadingView.setVisibility(VISIBLE);
    }

    /**
     * Content view
     */
    public void showContent() {
        if (emptyView != null && emptyView.isShown()) {
            emptyView.setVisibility(GONE);
        }
        if (loadingView != null && loadingView.isShown()) {
            loadingView.setVisibility(GONE);
        }
        if (!contentView.isShown()) {
            contentView.setVisibility(VISIBLE);
        }
    }
}
