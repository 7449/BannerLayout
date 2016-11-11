package com.bannerlayout.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * by y on 2016/11/11
 * <p>
 * 控制viewpager滑动速度，默认值1500，越大越慢
 */

public class FixedSpeedScroller extends Scroller {
    private int mDuration;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int time) {
        mDuration = time;
    }

    public int getmDuration() {
        return mDuration;
    }
}