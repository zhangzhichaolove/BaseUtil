package com.chao.baselib.base.adapter;

/**
 * Created by Chao on 2017/3/24.
 */

public abstract class SimpleItemType<T> implements RLItemViewType<T> {

    /**
     * 空实现，此方法仅ListView需要实现
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 0;
    }
}
