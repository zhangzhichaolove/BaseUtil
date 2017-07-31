package com.chao.baselib.config;

import com.chao.baselib.backlayout.SwipeBackManager;
import com.chao.baselib.variable.GeneralVar;

/**
 * 自定义配置
 * Created by Chao on 2017/7/30.
 */

public class BaseConfig {
    public static boolean deBug;
    public static boolean backFinish;


    public static void setDeBug(boolean deBug) {
        BaseConfig.deBug = deBug;
    }

    public static void setBackFinish(boolean backFinish) {
        BaseConfig.backFinish = backFinish;
        if (backFinish) {
            SwipeBackManager.getInstance().init(GeneralVar.getApplication());
        }
    }
}
