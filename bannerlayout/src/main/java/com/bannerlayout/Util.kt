package com.bannerlayout

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Handler
import android.os.Message
import com.bannerlayout.widget.BannerLayout

fun getTransformer(type: Int): BannerTransformer {
    when (type) {
        ANIMATION_ACCORDION -> return AccordionTransformer()
        ANIMATION_BACKGROUND -> return BackgroundToForegroundTransformer()
        ANIMATION_CUBE_IN -> return CubeInTransformer()
        ANIMATION_CUBE_OUT -> return CubeOutTransformer()
        ANIMATION_DEFAULT -> return DefaultTransformer()
        ANIMATION_DEPTH_PAGE -> return DepthPageTransformer()
        ANIMATION_FLIPHORIZONTAL -> return FlipHorizontalTransformer()
        ANIMATION_FLIPVERTICAL -> return FlipVerticalTransformer()
        ANIMATION_FOREGROUND -> return ForegroundToBackgroundTransformer()
        ANIMATION_ROTATEDOWN -> return RotateDownTransformer()
        ANIMATION_ROTATEUP -> return RotateUpTransformer()
        ANIMATION_SCALEINOUT -> return ScaleInOutTransformer()
        ANIMATION_STACK -> return StackTransformer()
        ANIMATION_TABLET -> return TabletTransformer()
        ANIMATION_ZOOMIN -> return ZoomInTransformer()
        ANIMATION_ZOOMOUTPAGE -> return ZoomOutPageTransformer()
        ANIMATION_ZOOMOUTSLIDE -> return ZoomOutSlideTransformer()
        ANIMATION_ZOOMOUT -> return ZoomOutTransformer()
        ANIMATION_DRAWER -> return DrawerTransformer()
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

class BannerHandlerUtils(private val mCurrent: BannerLayout) : Handler() {

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
                mCurrent.currentItem(++handlerPage)
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