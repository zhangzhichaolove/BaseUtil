package com.chao.baselib.base.adapter;

import android.support.v7.widget.RecyclerView;

interface ILayoutManager {
    boolean hasLayoutManager();

    RecyclerView.LayoutManager getLayoutManager();
}