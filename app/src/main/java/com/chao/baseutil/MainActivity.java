package com.chao.baseutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chao.baselib.log.LogUtils;
import com.chao.baselib.variable.ScreenVar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.showTagE("-----" + ScreenVar.getScreenWidth());
        LogUtils.showTagE("-----" + ScreenVar.getScreenHeight());
        LogUtils.showTagE("-----" + ScreenVar.getStatusHeight());
    }
}
