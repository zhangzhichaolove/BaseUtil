package com.chao.baseutil;

import android.app.Application;

import com.chao.baselib.config.BaseConfig;
import com.chao.baselib.init.MainInit;

/**
 * Created by Chao on 2017/7/30.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MainInit.getInstance().init(this);
        BaseConfig.setDeBug(true);
        BaseConfig.setBackFinish(true);

    }
}
