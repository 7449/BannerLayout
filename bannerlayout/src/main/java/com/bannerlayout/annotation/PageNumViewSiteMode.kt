package com.bannerlayout.annotation

import android.support.annotation.IntDef
import com.bannerlayout.widget.BannerLayout

/**
 * by y on 2017/1/19.
 */
@IntDef(BannerLayout.PAGE_NUM_VIEW_TOP_LEFT, BannerLayout.PAGE_NUM_VIEW_TOP_RIGHT, BannerLayout.PAGE_NUM_VIEW_BOTTOM_LEFT, BannerLayout.PAGE_NUM_VIEW_BOTTOM_RIGHT, BannerLayout.PAGE_NUM_VIEW_CENTER_LEFT, BannerLayout.PAGE_NUM_VIEW_CENTER_RIGHT, BannerLayout.PAGE_NUM_VIEW_TOP_CENTER, BannerLayout.PAGE_NUM_VIEW_BOTTOM_CENTER)
@Retention(AnnotationRetention.SOURCE)
annotation class PageNumViewSiteMode

