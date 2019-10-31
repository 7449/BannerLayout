package com.android.banner.run

import android.os.Handler
import android.os.Message
import com.android.banner.widget.BannerLayout

/**
 * @author y
 * @create 2019-04-29
 */
class BannerHandler(private val mCurrent: BannerLayout) : Handler() {

    var status: Int = 0
        private set

    var handlerDelayTime: Long = 2000
    var handlerPage: Int = 0

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (handlerPage == -1) {
            return
        }
        if (hasMessages(BannerLayout.MSG_UPDATE)) {
            removeMessages(BannerLayout.MSG_UPDATE)
        }
        val what = msg.what
        when (what) {
            BannerLayout.MSG_UPDATE -> {
                mCurrent.viewPager.currentItem = ++handlerPage
                sendEmptyMessageDelayed(BannerLayout.MSG_UPDATE, handlerDelayTime)
            }
            BannerLayout.MSG_PAGE -> handlerPage = msg.arg1
            BannerLayout.MSG_KEEP -> {
            }
        }
        status = what
    }
}