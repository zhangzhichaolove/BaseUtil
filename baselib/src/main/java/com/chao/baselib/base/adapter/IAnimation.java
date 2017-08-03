package com.chao.baselib.base.adapter;

import android.support.v7.widget.RecyclerView;

import com.chao.baselib.base.adapter.animation.BaseAnimation;


interface IAnimation {

    void enableLoadAnimation();

    void enableLoadAnimation(long duration, BaseAnimation animation);

    void cancelLoadAnimation();

    void setOnlyOnce(boolean onlyOnce);

    void addLoadAnimation(RecyclerView.ViewHolder holder, int position);

}
