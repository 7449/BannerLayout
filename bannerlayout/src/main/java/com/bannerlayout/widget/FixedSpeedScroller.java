package com.bannerlayout.widget;

import android.content.Context;
import android.widget.Scroller;

/**
 * by y on 2016/11/11
 * <p>
 * Control viewpager sliding speed, the default value of 1500, the greater the slower
 */
class FixedSpeedScroller extends Scroller {
    private int mDuration;

    FixedSpeedScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    void setDuration(int time) {
        mDuration = time;
    }

    int getmDuration() {
        return mDuration;
    }
}