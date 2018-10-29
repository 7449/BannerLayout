package com.bannerlayout

import androidx.annotation.IntDef
import com.bannerlayout.widget.BannerLayout

@IntDef(BannerLayout.ANIMATION_DRAWER, BannerLayout.ANIMATION_ACCORDION, BannerLayout.ANIMATION_BACKGROUND, BannerLayout.ANIMATION_CUBE_IN, BannerLayout.ANIMATION_CUBE_OUT, BannerLayout.ANIMATION_DEFAULT, BannerLayout.ANIMATION_DEPTH_PAGE, BannerLayout.ANIMATION_FLIPHORIZONTAL, BannerLayout.ANIMATION_FLIPVERTICAL, BannerLayout.ANIMATION_FOREGROUND, BannerLayout.ANIMATION_ROTATEDOWN, BannerLayout.ANIMATION_ROTATEUP, BannerLayout.ANIMATION_SCALEINOUT, BannerLayout.ANIMATION_STACK, BannerLayout.ANIMATION_TABLET, BannerLayout.ANIMATION_ZOOMIN, BannerLayout.ANIMATION_ZOOMOUTPAGE, BannerLayout.ANIMATION_ZOOMOUTSLIDE, BannerLayout.ANIMATION_ZOOMOUT)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimationMode

@IntDef(BannerLayout.CENTER, BannerLayout.LEFT, BannerLayout.RIGHT)
@Retention(AnnotationRetention.SOURCE)
annotation class DotsAndTitleSiteMode

@IntDef(BannerLayout.PAGE_NUM_VIEW_TOP_LEFT, BannerLayout.PAGE_NUM_VIEW_TOP_RIGHT, BannerLayout.PAGE_NUM_VIEW_BOTTOM_LEFT, BannerLayout.PAGE_NUM_VIEW_BOTTOM_RIGHT, BannerLayout.PAGE_NUM_VIEW_CENTER_LEFT, BannerLayout.PAGE_NUM_VIEW_CENTER_RIGHT, BannerLayout.PAGE_NUM_VIEW_TOP_CENTER, BannerLayout.PAGE_NUM_VIEW_BOTTOM_CENTER)
@Retention(AnnotationRetention.SOURCE)
annotation class PageNumViewSiteMode

@IntDef(BannerLayout.BOTTOM, BannerLayout.TOP, BannerLayout.CENTER)
@Retention(AnnotationRetention.SOURCE)
annotation class TipsSiteMode
