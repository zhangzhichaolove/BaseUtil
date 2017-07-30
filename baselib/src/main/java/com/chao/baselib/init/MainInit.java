package com.chao.baselib.init;

import android.app.Application;

import com.chao.baselib.util.ScreenUtils;
import com.chao.baselib.variable.ScreenVar;

/**
 * Created by Chao on 2017/7/30.
 */

public class MainInit {
    private Application application;

    private static MainInit instance;

    private MainInit() {
    }

    public static MainInit getInstance() {
        if (instance == null) {
            synchronized (MainInit.class) {
                if (instance == null) {
                    instance = new MainInit();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        this.application = application;
        ScreenVar.setScreenWidth(ScreenUtils.getScreenWidth(application));
        ScreenVar.setScreenHeight(ScreenUtils.getScreenHeight(application));
        ScreenVar.setStatusHeight(ScreenUtils.getStatusHeight(application));
    }

}
