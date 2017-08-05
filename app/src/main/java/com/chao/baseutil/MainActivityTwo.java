package com.chao.baseutil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chao.baselib.base.activity.SkinActivity;
import com.chao.baselib.injection.Id;
import com.chao.baselib.log.LogUtils;
import com.chao.baselib.variable.GeneralVar;

public class MainActivityTwo extends SkinActivity {
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
        toolbar.setTitle("新页面标题");
        toolbar.setTitleColor(ContextCompat.getColor(this, R.color.colorAccent));
        toolbar.setRightText("更多");
        toolbar.setRightColor(ContextCompat.getColor(this, R.color.colorAccent));
        LogUtils.showTagE("-----" + GeneralVar.getScreenWidth());
        LogUtils.showTagE("-----" + GeneralVar.getScreenHeight());
        LogUtils.showTagE("-----" + GeneralVar.getStatusHeight());
        tv_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityTwo.this, MainActivityTwo.class));
            }
        });
        statusBar.setBackgroundColor(Color.GREEN);
    }
}
