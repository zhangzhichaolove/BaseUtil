package com.chao.baseutil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chao.baselib.log.LogUtils;

/**
 * Created by Chao on 2017/8/1.
 */

public class BaseViewGroup extends ViewGroup implements View.OnClickListener {
    protected static final float FLIP_DISTANCE = 50;
    private final int SIZE = 80;//大小
    private final int CLEARANCE = 20;//间隙
    private boolean isOpen = false;
    private int mTouchSlop;
    private int width;
    private GestureDetector gestureDetector;


    public BaseViewGroup(Context context) {
        this(context, null);
    }

    public BaseViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
        for (int i = 0; i < 6; i++) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(R.mipmap.s11);
            iv.setOnClickListener(this);
            addView(iv);
        }
        width = getChildCount() * SIZE + getChildCount() * CLEARANCE - CLEARANCE;
        setOnClickListener(this);
        setListener(context);
    }

    private void setListener(Context context) {
//        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent motionEvent) {
//                return true;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent motionEvent) {
//                LogUtils.showTagE(motionEvent);
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent motionEvent) {
//                //LogUtils.showTagE(motionEvent);
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {
//                scrollBy((int) v, 0);
//                return true;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent motionEvent) {
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
//                if (e1 != null && e2 != null) {
//                    if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
//                        LogUtils.showTagE("向左滑...");
//                        return true;
//                    }
//                    if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
//                        LogUtils.showTagE("向右滑...");
//                        return true;
//                    }
//                    if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
//                        LogUtils.showTagE("向上滑...");
//                        return true;
//                    }
//                    if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
//                        LogUtils.showTagE("向下滑...");
//                        return true;
//                    }
//                }
//                return false;
//            }
//
//
//        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (!isOpen) {
            for (int j = 0; j < getChildCount(); j++) {
                View childAt = getChildAt(j);
                if (getChildCount() == 1) {
                    childAt.layout(0, 0, SIZE, SIZE);
                }
                if (getChildCount() > 1) {
                    int offsetX = SIZE + j * SIZE / 2;
                    int closeWidth = getChildCount() * SIZE - SIZE / 2 * (getChildCount() - 1);
                    childAt.layout(closeWidth - offsetX, 0, closeWidth - offsetX + SIZE, SIZE);
                }
            }
        } else {
            for (int j = 0; j < getChildCount(); j++) {
                View childAt = getChildAt(j);
                childAt.layout(j * SIZE + (j * CLEARANCE), 0, j * SIZE + SIZE + (j * CLEARANCE), SIZE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof ImageView) {
            isOpen = !isOpen;
            scrollTo(0, 0);
            requestLayout();
        }
    }

    float x = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isOpen) {
                    float moveX = x - event.getX();
                    scrollBy((int) moveX, 0);
                    if (getScrollX() < 0) {
                        scrollTo(0, 0);
                    }
                    if (getScrollX() > width - getWidth()) {
                        scrollTo(width - getWidth(), 0);
                    }
                    LogUtils.showTagE("getScrollX=" + getScrollX() + "  getWidth=" + getWidth() + "   width=" + width);
                    x = event.getX();
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
//                if (getScrollX() < 0) {
//                    scrollTo(0, 0);
//                }
//                if (getScrollX() > getChildCount() * SIZE) {
//                    scrollTo(0, 0);
//                }
                break;
        }

        return super.onTouchEvent(event);
//        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX() - x;
                if (Math.abs(moveX) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), SIZE);
    }
}
