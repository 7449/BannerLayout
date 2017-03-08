package com.bannerlayout.util;

import android.os.Handler;
import android.os.Message;

import com.bannerlayout.Interface.ViewPagerCurrent;

/**
 * by y on 2016/9/15.
 */
public class BannerHandlerUtils extends Handler {

    private int MSG_STATUS = -1;

    public static final int MSG_UPDATE = 1;
    public static final int MSG_KEEP = 2;
    public static final int MSG_BREAK = 3;
    public static final int MSG_PAGE = 4;
    private long delayTime = 2000;
    private ViewPagerCurrent mCurrent = null;
    private int page = 0;

    public BannerHandlerUtils(ViewPagerCurrent viewPager, int currentItem) {
        this.page = currentItem;
        this.mCurrent = viewPager;
    }

    public void setDelayTime(long time) {
        this.delayTime = time;
    }

    public int getBannerStatus() {
        return MSG_STATUS;
    }

    public void setBannerStatus(int status) {
        this.MSG_STATUS = status;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (null == mCurrent) {
            return;
        }
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE);
        }
        int what = msg.what;
        if (MSG_STATUS != what) {
            MSG_STATUS = what;
        }
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
            case MSG_BREAK:
                sendEmptyMessageDelayed(MSG_UPDATE, delayTime);
                break;
        }
    }
}