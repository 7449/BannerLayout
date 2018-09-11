package com.bannerlayout.annotation

import android.support.annotation.IntDef
import com.bannerlayout.widget.BannerLayout

/**
 * by y on 2017/5/27.
 */
@IntDef(BannerLayout.ANIMATION_DRAWER, BannerLayout.ANIMATION_ACCORDION, BannerLayout.ANIMATION_BACKGROUND, BannerLayout.ANIMATION_CUBE_IN, BannerLayout.ANIMATION_CUBE_OUT, BannerLayout.ANIMATION_DEFAULT, BannerLayout.ANIMATION_DEPTH_PAGE, BannerLayout.ANIMATION_FLIPHORIZONTAL, BannerLayout.ANIMATION_FLIPVERTICAL, BannerLayout.ANIMATION_FOREGROUND, BannerLayout.ANIMATION_ROTATEDOWN, BannerLayout.ANIMATION_ROTATEUP, BannerLayout.ANIMATION_SCALEINOUT, BannerLayout.ANIMATION_STACK, BannerLayout.ANIMATION_TABLET, BannerLayout.ANIMATION_ZOOMIN, BannerLayout.ANIMATION_ZOOMOUTPAGE, BannerLayout.ANIMATION_ZOOMOUTSLIDE, BannerLayout.ANIMATION_ZOOMOUT)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimationMode
