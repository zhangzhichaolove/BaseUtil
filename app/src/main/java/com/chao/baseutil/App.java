package com.chao.baseutil;

import android.app.Application;

import com.chao.baselib.config.CustomConfig;
import com.chao.baselib.init.MainInit;

/**
 * Created by Chao on 2017/7/30.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MainInit.getInstance().init(this);
        CustomConfig.setDeBug(true);
        CustomConfig.setBackFinish(true);
        CustomConfig.setLoadingView(R.layout.base_loading);
        CustomConfig.setEmptyView(R.layout.base_empty);

    }
}
