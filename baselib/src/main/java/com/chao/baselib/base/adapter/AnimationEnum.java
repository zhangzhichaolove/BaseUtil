package com.chao.baselib.base.adapter;


import com.chao.baselib.base.adapter.animation.AlphaInAnimation;
import com.chao.baselib.base.adapter.animation.BaseAnimation;
import com.chao.baselib.base.adapter.animation.ScaleInAnimation;
import com.chao.baselib.base.adapter.animation.SlideInBottomAnimation;
import com.chao.baselib.base.adapter.animation.SlideInLeftAnimation;
import com.chao.baselib.base.adapter.animation.SlideInRightAnimation;

/**
 * Created by Chao on 2017/3/29.
 */

public enum AnimationEnum {
    ALPHA(new AlphaInAnimation()), SCALE(new ScaleInAnimation()), BOTTOM(new SlideInBottomAnimation()), LEFT(new SlideInLeftAnimation()), RIGHT(new SlideInRightAnimation());

    public BaseAnimation animation;

    AnimationEnum(BaseAnimation animation) {
        this.animation = animation;
    }


}


