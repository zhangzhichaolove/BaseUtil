package com.chao.baselib.config;

import com.chao.baselib.side.SwipeBackManager;
import com.chao.baselib.variable.GeneralVar;

/**
 * 自定义配置
 * Created by Chao on 2017/7/30.
 */

public class CustomConfig {
    public static boolean deBug;
    public static boolean backFinish;
    public static int emptyView = -1;
    public static int loadingView = -1;


    public static void setDeBug(boolean deBug) {
        CustomConfig.deBug = deBug;
    }

    public static void setBackFinish(boolean backFinish) {
        CustomConfig.backFinish = backFinish;
        if (backFinish) {
            SwipeBackManager.getInstance().init(GeneralVar.getApplication());
        }
    }

    public static void setLoadingView(int loadingView) {
        CustomConfig.loadingView = loadingView;
    }

    public static void setEmptyView(int emptyView) {
        CustomConfig.emptyView = emptyView;
    }

}
