package com.chao.baselib.init;

import android.app.Application;

import com.chao.baselib.backlayout.SwipeBackManager;
import com.chao.baselib.config.BaseConfig;
import com.chao.baselib.util.ScreenUtils;
import com.chao.baselib.variable.GeneralVar;

/**
 * Created by Chao on 2017/7/30.
 */

public class MainInit {
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
        GeneralVar.setApplication(application);
        GeneralVar.setScreenWidth(ScreenUtils.getScreenWidth(application));
        GeneralVar.setScreenHeight(ScreenUtils.getScreenHeight(application));
        GeneralVar.setStatusHeight(ScreenUtils.getStatusHeight(application));
    }

}
