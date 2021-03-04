package com.android.banner.run

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * @author y
 * @create 2019-04-29
 */
internal class BannerHandler(
        private val action: (page: Int) -> Unit
) : Handler(Looper.getMainLooper()) {

    companion object {
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
        const val MSG_PAGE = 3
    }

    private var handlerPage: Int = -1

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (handlerPage == -1) {
            return
        }
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE)
        }
        when (msg.what) {
            MSG_UPDATE -> {
                action.invoke(++handlerPage)
                sendUpdateMessage(msg.arg1)
            }
            MSG_PAGE -> handlerPage = msg.arg1
        }
    }

    fun release() {
        removeCallbacksAndMessages(null)
    }

    fun sendKeepMessage() {
        sendEmptyMessage(MSG_KEEP)
    }

    fun sendUpdateMessage(delayTime: Int) {
        sendMessageDelayed(Message.obtain(this, MSG_UPDATE, delayTime, 0), delayTime.toLong())
    }

    fun sendMessageSelected(currentItem: Int) {
        sendMessage(Message.obtain(this, MSG_PAGE, currentItem, 0))
    }

    fun updateCurrentPage(item: Int) {
        handlerPage = item
    }

}