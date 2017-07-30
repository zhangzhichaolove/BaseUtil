package com.chao.baselib.variable;

/**
 * Created by Chao on 2017/7/30.
 */

public class ScreenVar {
    private static int screenWidth;
    private static int screenHeight;
    private static int statusHeight;


    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        ScreenVar.screenWidth = screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(int screenHeight) {
        ScreenVar.screenHeight = screenHeight;
    }

    public static int getStatusHeight() {
        return statusHeight;
    }

    public static void setStatusHeight(int statusHeight) {
        ScreenVar.statusHeight = statusHeight;
    }
}
