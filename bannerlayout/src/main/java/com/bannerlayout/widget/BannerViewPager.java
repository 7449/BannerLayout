package com.bannerlayout.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import java.lang.reflect.Field;

/**
 * by y on 2016/10/25
 */

public class BannerViewPager extends ViewPager {

    //true viewpager禁止滑动
    private boolean mViewTouchMode = false;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
            // 全权控制滑动事件
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
            // 终止控制滑动事件
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    /**
     * 在 mViewTouchMode 为 true 的时候，ViewPager 不拦截点击事件，点击事件将由子 View 处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !mViewTouchMode && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 在 mViewTouchMode 为 true 或者滑动方向不是左右的时候，ViewPager 将放弃控制点击事件，
     * 这样做有利于在 ViewPager 中加入 ListView 等可以滑动的控件，否则两者之间的滑动将会有冲突
     */
    @Override
    public boolean arrowScroll(int direction) {
        return !mViewTouchMode && !(direction != FOCUS_LEFT && direction != FOCUS_RIGHT) && super.arrowScroll(direction);
    }

    /**
     * 设置切换速度
     */
    public void setDuration(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
            field.set(this, scroller);
            scroller.setDuration(duration);
        } catch (Exception e) {
            Log.i(getClass().getSimpleName(), e.getMessage());
        }
    }
}
