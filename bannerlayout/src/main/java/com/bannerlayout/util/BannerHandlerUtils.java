package com.bannerlayout.util;

import android.os.Handler;
import android.os.Message;

import com.bannerlayout.listener.ViewPagerCurrent;

/**
 * by y on 2016/9/15.
 */
public class BannerHandlerUtils extends Handler {

    private int mStatus;

    public static final int MSG_UPDATE = 1;
    public static final int MSG_KEEP = 2;
    public static final int MSG_PAGE = 3;

    private long delayTime = 2000;
    private ViewPagerCurrent mCurrent;
    private int page;

    public BannerHandlerUtils(ViewPagerCurrent viewPager, int currentItem) {
        this.page = currentItem;
        this.mCurrent = viewPager;
    }

    public void setDelayTime(long time) {
        this.delayTime = time;
    }

    public int getStatus() {
        return mStatus;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (null == mCurrent || page == -1) {
            return;
        }
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE);
        }
        int what = msg.what;
        switch (what) {
            case MSG_UPDATE:
                mCurrent.setCurrentItem(++page);
                sendEmptyMessageDelayed(MSG_UPDATE, delayTime);
                break;
            case MSG_PAGE:
                page = msg.arg1;
                break;
            case MSG_KEEP:
                break;
        }
        mStatus = what;
    }
}