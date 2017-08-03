package com.chao.baselib.base.adapter;

import android.view.View;

interface RLHeaderFooter {
    void addHeader(View header);

    void addFooter(View footer);

    void removeHeader(View header);

    void removeFooter(View footer);

    int getHeaderSize();

    int getFootSize();

    boolean isHeaderView(int position);

    boolean isFooterView(int position);
}
