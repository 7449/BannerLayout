package com.bannerlayout.util

import android.os.Handler
import android.os.Message

import com.bannerlayout.listener.ViewPagerCurrent

/**
 * by y on 2016/9/15.
 */
class BannerHandlerUtils(private val mCurrent: ViewPagerCurrent) : Handler() {

    var status: Int = 0
        private set

    private var delayTime: Long = 2000
    private var page: Int = 0

    fun setDelayTime(time: Long) = apply { this.delayTime = time }
    fun setPage(page: Int) = apply { this.page = page }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (page == -1) {
            return
        }
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE)
        }
        val what = msg.what
        when (what) {
            MSG_UPDATE -> {
                mCurrent.setCurrentItem(++page)
                sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
            }
            MSG_PAGE -> page = msg.arg1
            MSG_KEEP -> {
            }
        }
        status = what
    }

    companion object {
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
        const val MSG_PAGE = 3
    }
}