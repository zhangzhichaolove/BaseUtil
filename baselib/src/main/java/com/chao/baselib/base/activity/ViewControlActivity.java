package com.chao.baselib.base.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chao.baselib.R;
import com.chao.baselib.injection.FindView;
import com.chao.baselib.util.BarUtils;
import com.chao.baselib.variable.GeneralVar;
import com.chao.baselib.view.CustomRootView;
import com.chao.baselib.view.CustomToolbar;


/**
 * 视图控制Activity基类
 * Created by Chao on 2017/7/30.
 */

public abstract class ViewControlActivity extends AppCompatActivity implements ActivityInterface, View.OnClickListener {
    public final static float DESIGN_WIDTH = 720; //绘制页面时参照的设计图宽度
    protected CustomToolbar toolbar;
    protected LinearLayout ll_rootView;
    protected CustomRootView base_content;
    protected View statusBar;
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetDensity();
        setContentView(R.layout.base_layout);
        mContext = this;
        BarUtils.setWindow(this);
        base_content = (CustomRootView) findViewById(R.id.fl_base_content);
        ll_rootView = (LinearLayout) findViewById(R.id.ll_rootView);
        if (showStatusBar()) {
            statusBar = new View(mContext);
            LinearLayout.LayoutParams statusBarLp =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, GeneralVar.getStatusHeight());
            statusBar.setBackgroundColor(Color.RED);
            ll_rootView.addView(statusBar, 0, statusBarLp);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                statusBar.setVisibility(View.GONE);
            }
        }
        if (showTitle()) {
            toolbar = (CustomToolbar) getLayoutInflater().inflate(R.layout.whole_toolbar, null);
            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
            LinearLayout.LayoutParams toolbarLp =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, actionBarHeight);
            toolbar.setLeftImgOnClickListener(this);
            ll_rootView.addView(toolbar, showStatusBar() ? 1 : 0, toolbarLp);
        }

        //setSupportActionBar(toolbar);
        //LogUtils.showTagE(Math.sqrt(Math.pow(1280, 2) + Math.pow(720, 2)) / 72 + "");
        if (getLayout() != 0) {
            base_content.addView(getLayoutInflater().inflate(getLayout(), null));
        }
        init();
    }

    private void init() {
        FindView.bind(this);
        initView();
    }

    //将pt转换为px值
    public float pt2px(int value) {
        //TypedValue.applyDimension时注意传入的DisplayMetrics是改过之后的。或者不用这个方法自己来计算。
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, value, getResources().getDisplayMetrics());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }

    public void resetDensity() {
        //Point size = new Point();
        //getWindowManager().getDefaultDisplay().getSize(size);
        //getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
        Point size = new Point();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbar_ivb_left) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mContext = null;
        super.onDestroy();
    }

    /**
     * 是否显示标题栏，子类可以重写
     */
    public boolean showTitle() {
        return true;
    }

    /**
     * 是否显示状态栏，子类可以重写
     */
    public boolean showStatusBar() {
        return true;
    }
}
