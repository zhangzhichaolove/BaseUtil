package com.chao.baselib.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chao.baselib.R;
import com.chao.baselib.backlayout.SwipeBackHelper;
import com.chao.baselib.config.BaseConfig;
import com.chao.baselib.injection.FindView;
import com.chao.baselib.util.WindowUtils;
import com.chao.baselib.variable.GeneralVar;
import com.chao.baselib.view.CustomToolbar;

/**
 * Created by Chao on 2017/7/30.
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityInterface, SwipeBackHelper.Delegate, View.OnClickListener {
    protected SwipeBackHelper mSwipeBackHelper;
    protected CustomToolbar toolbar;
    private FrameLayout base_content;
    protected View statusBar;
    private LinearLayout ll_rootView;
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        mContext = this;
        WindowUtils.setWindow(this);
        base_content = (FrameLayout) findViewById(R.id.fl_base_content);
        ll_rootView = (LinearLayout) findViewById(R.id.ll_rootView);
        toolbar = (CustomToolbar) findViewById(R.id.tl_base_toolbar);
        toolbar.setLeftImgOnClickListener(this);
        statusBar = new View(mContext);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralVar.getStatusHeight());
        statusBar.setBackgroundColor(Color.RED);
        ll_rootView.addView(statusBar, 0, lp);
        //setSupportActionBar(toolbar);
        if (getLayout() != 0) {
            base_content.addView(getLayoutInflater().inflate(getLayout(), null));
        }
        init();
    }

    private void init() {
        FindView.bind(this);
        initSwipeBackFinish();
        initView();
    }


    protected void initSwipeBackFinish() {
        if (BaseConfig.backFinish) {
            mSwipeBackHelper = new SwipeBackHelper(this, this);
            // 设置滑动返回是否可用。默认值为 true
            mSwipeBackHelper.setSwipeBackEnable(true);
            // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
            mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
            // 设置是否是微信滑动返回样式。默认值为 true
            mSwipeBackHelper.setIsWeChatStyle(true);
            // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
            mSwipeBackHelper.setShadowResId(R.drawable.sbl_shadow);
            // 设置是否显示滑动返回的阴影效果。默认值为 true
            mSwipeBackHelper.setIsNeedShowShadow(true);
            // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
            mSwipeBackHelper.setIsShadowAlphaGradient(true);
            // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
            mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        }
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        if (BaseConfig.backFinish) {
            // 正在滑动返回的时候取消返回按钮事件
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            mSwipeBackHelper.backward();
        } else {
            super.onBackPressed();
        }
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
}
