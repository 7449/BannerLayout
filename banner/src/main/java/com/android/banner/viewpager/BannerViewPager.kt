package com.android.banner.viewpager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

/**
 * by y on 2016/10/25
 */
class BannerViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    var viewTouchMode: Boolean = false
        set(value) {
            field = value
            if (value && !isFakeDragging) {
                beginFakeDrag()
            } else if (!value && isFakeDragging) {
                endFakeDrag()
            }
        }

    var isVertical: Boolean = false

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        runCatching {
            val mFirstLayout = ViewPager::class.java.getDeclaredField("mFirstLayout")
            mFirstLayout.isAccessible = true
            mFirstLayout.set(this, false)
        }
    }

    override fun onDetachedFromWindow() {
        if ((context as? Activity)?.isFinishing == true) {
            super.onDetachedFromWindow()
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean = if (isVertical) {
        !viewTouchMode && super.onInterceptTouchEvent(swapEvent(event))
    } else {
        !viewTouchMode && super.onInterceptTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean = if (isVertical) {
        super.onTouchEvent(swapEvent(ev))
    } else {
        super.onTouchEvent(ev)
    }

    override fun arrowScroll(direction: Int): Boolean = !viewTouchMode && !(direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT) && super.arrowScroll(direction)

    fun setDuration(duration: Int) {
        runCatching {
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            mScroller.set(this, FixedSpeedScroller(context, duration))
        }.onFailure { Log.i(javaClass.simpleName, it.message ?: "") }
    }

    private fun swapEvent(event: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val swappedX = event.y / height * width
        val swappedY = event.x / width * height
        event.setLocation(swappedX, swappedY)
        return event
    }

    private class FixedSpeedScroller(context: Context, val fixDuration: Int) : Scroller(context) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, fixDuration)
        }
    }
}
