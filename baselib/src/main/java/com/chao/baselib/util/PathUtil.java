package com.chao.baselib.util;

import android.os.Environment;

import com.chao.baselib.variable.GeneralVar;

import java.io.File;

/**
 * 内存路径工具类
 * Created by Chao on 2017/8/3.
 */

public class PathUtil {

    public static String getRootPath() {
        return Environment.getExternalStorageDirectory()
                + File.separator;
    }

    /**
     * 包名路径
     *
     * @return
     */
    public static String getPackagePath() {
        String path = Environment.getExternalStorageDirectory()
                + File.separator + GeneralVar.getApplication().getPackageName()
                + File.separator;
        create(path);
        return path;
    }

    /**
     * 包名路径
     *
     * @return
     */
    public static File getPackageFilePath() {
        return new File(getPackagePath());
    }

    /**
     * 创建路径
     *
     * @param path
     */
    public static File create(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
