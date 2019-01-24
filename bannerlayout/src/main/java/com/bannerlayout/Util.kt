package com.bannerlayout

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Handler
import android.os.Message
import com.bannerlayout.widget.BannerLayout

fun getTransformer(type: Int): BannerTransformer {
    when (type) {
        BannerTransformer.ANIMATION_ACCORDION -> return AccordionTransformer()
        BannerTransformer.ANIMATION_BACKGROUND -> return BackgroundToForegroundTransformer()
        BannerTransformer.ANIMATION_CUBE_IN -> return CubeInTransformer()
        BannerTransformer.ANIMATION_CUBE_OUT -> return CubeOutTransformer()
        BannerTransformer.ANIMATION_DEFAULT -> return DefaultTransformer()
        BannerTransformer.ANIMATION_DEPTH_PAGE -> return DepthPageTransformer()
        BannerTransformer.ANIMATION_FLIPHORIZONTAL -> return FlipHorizontalTransformer()
        BannerTransformer.ANIMATION_FLIPVERTICAL -> return FlipVerticalTransformer()
        BannerTransformer.ANIMATION_FOREGROUND -> return ForegroundToBackgroundTransformer()
        BannerTransformer.ANIMATION_ROTATEDOWN -> return RotateDownTransformer()
        BannerTransformer.ANIMATION_ROTATEUP -> return RotateUpTransformer()
        BannerTransformer.ANIMATION_SCALEINOUT -> return ScaleInOutTransformer()
        BannerTransformer.ANIMATION_STACK -> return StackTransformer()
        BannerTransformer.ANIMATION_TABLET -> return TabletTransformer()
        BannerTransformer.ANIMATION_ZOOMIN -> return ZoomInTransformer()
        BannerTransformer.ANIMATION_ZOOMOUTPAGE -> return ZoomOutPageTransformer()
        BannerTransformer.ANIMATION_ZOOMOUTSLIDE -> return ZoomOutSlideTransformer()
        BannerTransformer.ANIMATION_ZOOMOUT -> return ZoomOutTransformer()
        BannerTransformer.ANIMATION_DRAWER -> return DrawerTransformer()
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