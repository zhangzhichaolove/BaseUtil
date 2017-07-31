package com.chao.baseutil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chao.baselib.base.BaseActivity;
import com.chao.baselib.injection.Id;
import com.chao.baselib.log.LogUtils;
import com.chao.baselib.variable.GeneralVar;

public class MainActivity extends BaseActivity {
    @Id(R.id.tv_startActivity)
    TextView tv_startActivity;

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
        LogUtils.showTagE("-----" + GeneralVar.getScreenWidth());
        LogUtils.showTagE("-----" + GeneralVar.getScreenHeight());
        LogUtils.showTagE("-----" + GeneralVar.getStatusHeight());
        tv_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivityTwo.class));
            }
        });
    }
}
