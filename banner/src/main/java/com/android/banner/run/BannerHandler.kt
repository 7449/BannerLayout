package com.android.banner.run

import android.os.Handler
import android.os.Message
import com.android.banner.BannerLayout

/**
 * @author y
 * @create 2019-04-29
 */
internal class BannerHandler(
        private val mCurrent: BannerLayout
) : Handler() {

    companion object {
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
        const val MSG_PAGE = 3
    }

    var status: Int = 0
        private set

    var handlerDelayTime: Int = 2000
    var handlerPage: Int = 0

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (handlerPage == -1) {
            return
        }
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE)
        }
        val what = msg.what
        when (what) {
            MSG_UPDATE -> {
                mCurrent.viewPager.currentItem = ++handlerPage
                sendEmptyMessageDelayed(MSG_UPDATE, handlerDelayTime.toLong())
            }
            MSG_PAGE -> handlerPage = msg.arg1
            MSG_KEEP -> {
            }
        }
        status = what
    }

    fun release() {
        removeCallbacksAndMessages(null)
    }

    fun sendUpdateMessage() {
        sendEmptyMessageDelayed(MSG_UPDATE, handlerDelayTime.toLong())
    }

    fun sendKeepMessage() {
        sendEmptyMessage(MSG_KEEP)
    }

    fun sendMessageSelected() {
        sendMessage(Message.obtain(this, MSG_PAGE, mCurrent.viewPager.currentItem, 0))
    }

}