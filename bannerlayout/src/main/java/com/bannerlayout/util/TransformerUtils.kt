package com.bannerlayout.util

import com.bannerlayout.animation.*
import com.bannerlayout.annotation.AnimationMode
import com.bannerlayout.widget.BannerLayout

/**
 * by y on 2016/11/11
 */

object TransformerUtils {

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
            BannerLayout.ANIMATION_ZOOMOUT -> return ZoomOutTranformer()
            BannerLayout.ANIMATION_DRAWER -> return DrawerTransformer()
            else -> return AccordionTransformer()
        }
    }

}
