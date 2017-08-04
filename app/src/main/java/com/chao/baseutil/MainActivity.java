package com.chao.baseutil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chao.baselib.base.activity.ViewControlActivity;
import com.chao.baselib.injection.Id;
import com.chao.baselib.log.LogUtils;
import com.chao.baselib.variable.GeneralVar;

public class MainActivity extends ViewControlActivity {
    @Id(R.id.tv_startActivity)
    TextView tv_startActivity;
    @Id(R.id.tv_content)
    TextView tv_content;
    @Id(R.id.iv_showLoad)
    ImageView iv_showLoad;

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
        LogUtils.showTagE("-----" + GeneralVar.getNavigationBarHeight());
        tv_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivityTwo.class));
            }
        });
        iv_showLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                iv_showLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showContent();
                    }
                }, 2000);
            }
        });
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmpty();
                tv_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showContent();
                    }
                }, 2000);
            }
        });
    }
}
