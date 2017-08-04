package com.chao.baselib.skin.loader;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by Chao on 2017/6/11.
 */
public class BaseLayoutInflater extends LayoutInflater {


    public BaseLayoutInflater(Context context) {
        super(context);
//        LayoutInflaterCompat.setFactory(this, new V4SkinInflaterFactory(this));
        setFactory(new SkinInflaterFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new BaseLayoutInflater(newContext);
    }
}