package com.chao.baselib.base.adapter;

import android.support.annotation.LayoutRes;

public interface RLItemViewType<T> {


    int getItemViewType(int position, T t);


    @LayoutRes
    int getLayoutId(int viewType);

    int getViewTypeCount();
}