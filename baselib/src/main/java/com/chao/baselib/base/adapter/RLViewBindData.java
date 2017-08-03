package com.chao.baselib.base.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public interface RLViewBindData<T, VH> {


    VH onCreate(@Nullable View convertView, ViewGroup parent, int viewType);


    void onBind(VH holder, int viewType, int position, T item);
}