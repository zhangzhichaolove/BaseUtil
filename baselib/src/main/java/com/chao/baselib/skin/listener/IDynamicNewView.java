package com.chao.baselib.skin.listener;

import android.view.View;

import com.chao.baselib.skin.entity.DynamicAttr;

import java.util.List;


public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
