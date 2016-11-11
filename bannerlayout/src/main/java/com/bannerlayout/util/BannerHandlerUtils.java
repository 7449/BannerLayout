package com.bannerlayout.util;

import android.os.Handler;
import android.os.Message;

import com.bannerlayout.Interface.ViewPagerCurrent;

/**
 * by y on 2016/9/15.
 */
public class BannerHandlerUtils extends Handler {

    public static final int MSG_START = 0; //开始轮播
    public static final int MSG_UPDATE = 1; //更新
    public static final int MSG_KEEP = 2; //暂停
    public static final int MSG_BREAK = 3; // 恢复
    public static final int MSG_PAGE = 4; //最新的page
    private long delayTime = 2000; //time
    private ViewPagerCurrent mCurrent = null;
    private int page = 0;

    public BannerHandlerUtils(ViewPagerCurrent viewPager, int currentItem) {
        this.page = currentItem;
        this.mCurrent = viewPager;
    }

    public void setDelayTime(long time) {
        this.delayTime = time;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE);
        }
        if (null == mCurrent) {
            return;
        }
        switch (msg.what) {
            case MSG_START:
                sendEmptyMessageDelayed(MSG_UPDATE, delayTime);
                break;
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