package com.bannerlayout.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.bannerlayout.animation.VerticalTransformer

/**
 * by y on 2016/10/25
 */
class BannerViewPager : ViewPager {

    private var mViewTouchMode: Boolean = false
    private var isVertical: Boolean = false
    private lateinit var scroller: FixedSpeedScroller
    val duration: Int get() = scroller.fixDuration

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setViewTouchMode(b: Boolean) = apply {
        if (b && !isFakeDragging) {
            beginFakeDrag()
        } else if (!b && isFakeDragging) {
            endFakeDrag()
        }
        mViewTouchMode = b
        return this
    }

    fun setVertical(vertical: Boolean) = apply {
        isVertical = vertical
        if (isVertical) {
            setPageTransformer(true, VerticalTransformer())
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            val mFirstLayout = ViewPager::class.java.getDeclaredField("mFirstLayout")
            mFirstLayout.isAccessible = true
            mFirstLayout.set(this, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDetachedFromWindow() {
        if ((context as Activity).isFinishing) {
            super.onDetachedFromWindow()
        }
    }

    /**
     * When mViewTouchMode is true, ViewPager does not intercept the click event, and the click event will be handled by the childView
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isVertical) {
            !mViewTouchMode && super.onInterceptTouchEvent(swapEvent(event))
        } else {
            !mViewTouchMode && super.onInterceptTouchEvent(event)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isVertical) {
            super.onTouchEvent(swapEvent(ev))
        } else {
            super.onTouchEvent(ev)
        }
    }

    /**
     * In the mViewTouchMode true or sliding direction is not about time, ViewPager will give up control of click events,
     * This is conducive to the ListView in the ListView can be added, such as sliding control, or sliding between the two will have a conflict
     */
    override fun arrowScroll(direction: Int): Boolean {
        return !mViewTouchMode && !(direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT) && super.arrowScroll(direction)
    }


    /**
     * Set the switching speed
     */
    fun setDuration(duration: Int) = apply {
        try {
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            scroller = FixedSpeedScroller(context)
            mScroller.set(this, scroller)
            scroller.duration = duration
        } catch (e: Exception) {
            Log.i(javaClass.simpleName, e.message)
        }
        return this
    }

    private fun swapEvent(event: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val swappedX = event.y / height * width
        val swappedY = event.x / width * height
        event.setLocation(swappedX, swappedY)
        return event
    }
}
