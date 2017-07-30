package com.chao.baseutil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chao.baselib.base.BaseActivity;
import com.chao.baselib.log.LogUtils;
import com.chao.baselib.variable.ScreenVar;

public class MainActivityTwo extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.showTagE("-----" + ScreenVar.getScreenWidth());
        LogUtils.showTagE("-----" + ScreenVar.getScreenHeight());
        LogUtils.showTagE("-----" + ScreenVar.getStatusHeight());
        findViewById(R.id.tv_startActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityTwo.this, MainActivityTwo.class));
            }
        });
    }
}
