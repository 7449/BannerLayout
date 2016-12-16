package com.bannerlayout.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import com.bannerlayout.animation.VerticalTransformer;

import java.lang.reflect.Field;

/**
 * by y on 2016/10/25
 */
class BannerViewPager extends ViewPager {

    //true Viewpager Prevents swipe
    private boolean mViewTouchMode = false;

    //Whether the vertical sliding ,The default is not
    private boolean isVertical;
    private FixedSpeedScroller scroller;

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

    public void setVertical(boolean vertical) {
        isVertical = vertical;
        if (isVertical) {
            setPageTransformer(true, new VerticalTransformer());
        }
    }

    /**
     * When mViewTouchMode is true, ViewPager does not intercept the click event, and the click event will be handled by the childView
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isVertical) {
            return !mViewTouchMode && super.onInterceptTouchEvent(swapEvent(event));
        } else {
            return !mViewTouchMode && super.onInterceptTouchEvent(event);
        }
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (isVertical) {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        } else {
//            return super.dispatchTouchEvent(ev);
//        }
//        return false;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isVertical) {
            return super.onTouchEvent(swapEvent(ev));
        } else {
            return super.onTouchEvent(ev);
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
            scroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
            field.set(this, scroller);
            scroller.setDuration(duration);
        } catch (Exception e) {
            Log.i(getClass().getSimpleName(), e.getMessage());
        }
    }

    public int getDuration() {
        if (scroller != null) {
          return  scroller.getmDuration();
        }
        return 0;
    }

    private MotionEvent swapEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;
        event.setLocation(swappedX, swappedY);
        return event;
    }
}
