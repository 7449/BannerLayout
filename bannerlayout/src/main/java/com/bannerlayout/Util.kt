package com.bannerlayout

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Handler
import android.os.Message
import com.bannerlayout.widget.BannerLayout

fun getTransformer(@AnimationMode type: Int): BannerTransformer {
    when (type) {
        BannerLayout.ANIMATION_ACCORDION -> return AccordionTransformer()
        BannerLayout.ANIMATION_BACKGROUND -> return BackgroundToForegroundTransformer()
        BannerLayout.ANIMATION_CUBE_IN -> return CubeInTransformer()
        BannerLayout.ANIMATION_CUBE_OUT -> return CubeOutTransformer()
        BannerLayout.ANIMATION_DEFAULT -> return DefaultTransformer()
        BannerLayout.ANIMATION_DEPTH_PAGE -> return DepthPageTransformer()
        BannerLayout.ANIMATION_FLIPHORIZONTAL -> return FlipHorizontalTransformer()
        BannerLayout.ANIMATION_FLIPVERTICAL -> return FlipVerticalTransformer()
        BannerLayout.ANIMATION_FOREGROUND -> return ForegroundToBackgroundTransformer()
        BannerLayout.ANIMATION_ROTATEDOWN -> return RotateDownTransformer()
        BannerLayout.ANIMATION_ROTATEUP -> return RotateUpTransformer()
        BannerLayout.ANIMATION_SCALEINOUT -> return ScaleInOutTransformer()
        BannerLayout.ANIMATION_STACK -> return StackTransformer()
        BannerLayout.ANIMATION_TABLET -> return TabletTransformer()
        BannerLayout.ANIMATION_ZOOMIN -> return ZoomInTransformer()
        BannerLayout.ANIMATION_ZOOMOUTPAGE -> return ZoomOutPageTransformer()
        BannerLayout.ANIMATION_ZOOMOUTSLIDE -> return ZoomOutSlideTransformer()
        BannerLayout.ANIMATION_ZOOMOUT -> return ZoomOutTransformer()
        BannerLayout.ANIMATION_DRAWER -> return DrawerTransformer()
        else -> return AccordionTransformer()
    }
}

fun getDrawableSelector(enabledRadius: Float,
                        enabledColor: Int,
                        normalRadius: Float,
                        normalColor: Int): StateListDrawable {
    val drawable = StateListDrawable()
    drawable.addState(intArrayOf(android.R.attr.state_enabled), getShape(enabledRadius, enabledColor))
    drawable.addState(intArrayOf(-android.R.attr.state_enabled), getShape(normalRadius, normalColor))
    return drawable
}

fun getShape(radius: Float, color: Int): GradientDrawable {
    val gd = GradientDrawable()
    gd.cornerRadius = radius
    gd.setColor(color)
    return gd
}

interface ViewPagerCurrent {
    fun setCurrentItem(page: Int)
}

class BannerHandlerUtils(private val mCurrent: ViewPagerCurrent) : Handler() {

    var status: Int = 0
        private set

    var handlerDelayTime: Long = 2000
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
                mCurrent.setCurrentItem(++handlerPage)
                sendEmptyMessageDelayed(MSG_UPDATE, handlerDelayTime)
            }
            MSG_PAGE -> handlerPage = msg.arg1
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