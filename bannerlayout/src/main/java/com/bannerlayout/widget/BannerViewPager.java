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

    //true Viewpager Prevents swipe
    private boolean mViewTouchMode = false;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
            // Full control of sliding events
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
            // Terminate the control slide event
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    /**
     * When mViewTouchMode is true, ViewPager does not intercept the click event, and the click event will be handled by the childView
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
     * In the mViewTouchMode true or sliding direction is not about time, ViewPager will give up control of click events,
     * This is conducive to the ListView in the ListView can be added, such as sliding control, or sliding between the two will have a conflict
     */
    @Override
    public boolean arrowScroll(int direction) {
        return !mViewTouchMode && !(direction != FOCUS_LEFT && direction != FOCUS_RIGHT) && super.arrowScroll(direction);
    }

    /**
     * Set the switching speed
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
