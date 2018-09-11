package com.bannerlayout.annotation

import android.support.annotation.IntDef
import com.bannerlayout.widget.BannerLayout

/**
 * by y on 2017/1/19.
 */

@IntDef(BannerLayout.BOTTOM, BannerLayout.TOP, BannerLayout.CENTER)
@Retention(AnnotationRetention.SOURCE)
annotation class TipsSiteMode
