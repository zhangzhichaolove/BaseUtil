package com.chao.baselib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chao.baselib.R;
import com.chao.baselib.config.CustomConfig;
import com.chao.baselib.side.SwipeBackHelper;

/**
 * 侧滑返回Activity基类
 * Created by Chao on 2017/8/3.
 */

public abstract class SwipeBackActivity extends ViewControlActivity implements SwipeBackHelper.Delegate {
    protected SwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeBackFinish();
    }

    protected void initSwipeBackFinish() {
        if (CustomConfig.backFinish) {
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
        if (CustomConfig.backFinish) {
            // 正在滑动返回的时候取消返回按钮事件
            if (mSwipeBackHelper.isSliding()) {
                return;
            }
            mSwipeBackHelper.backward();
        } else {
            super.onBackPressed();
        }
    }
}
