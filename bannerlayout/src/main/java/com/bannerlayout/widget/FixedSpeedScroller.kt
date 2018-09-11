package com.bannerlayout.widget

import android.content.Context
import android.widget.Scroller

/**
 * by y on 2016/11/11
 *
 *
 * Control viewpager sliding speed, the default value of 1500, the greater the slower
 */
internal class FixedSpeedScroller(context: Context) : Scroller(context) {

    var fixDuration: Int = 0
        private set

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, fixDuration)
    }

    fun setDuration(time: Int) {
        fixDuration = time
    }
}