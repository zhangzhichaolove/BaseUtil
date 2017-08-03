package com.chao.baselib.variable;

import android.app.Application;

/**
 * 通用变量
 * Created by Chao on 2017/7/30.
 */

public class GeneralVar {
    private static Application application;
    private static int screenWidth;
    private static int screenHeight;
    private static int statusHeight;
    private static int navigationBarHeight;


    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        GeneralVar.screenWidth = screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(int screenHeight) {
        GeneralVar.screenHeight = screenHeight;
    }

    public static int getStatusHeight() {
        return statusHeight;
    }

    public static void setStatusHeight(int statusHeight) {
        GeneralVar.statusHeight = statusHeight;
    }

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        GeneralVar.application = application;
    }

    public static int getNavigationBarHeight() {
        return navigationBarHeight;
    }

    public static void setNavigationBarHeight(int navigationBarHeight) {
        GeneralVar.navigationBarHeight = navigationBarHeight;
    }
}
