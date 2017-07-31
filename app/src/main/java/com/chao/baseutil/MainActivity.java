package com.chao.baseutil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chao.baselib.base.BaseActivity;
import com.chao.baselib.log.LogUtils;
import com.chao.baselib.variable.ScreenVar;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        toolbar.setTitle("首页");
        toolbar.setRightImg(R.mipmap.s11);
        LogUtils.showTagE("-----" + ScreenVar.getScreenWidth());
        LogUtils.showTagE("-----" + ScreenVar.getScreenHeight());
        LogUtils.showTagE("-----" + ScreenVar.getStatusHeight());
        findViewById(R.id.tv_startActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivityTwo.class));
            }
        });
    }
}
