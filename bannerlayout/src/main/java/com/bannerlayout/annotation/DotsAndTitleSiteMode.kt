package com.bannerlayout.annotation

import androidx.annotation.IntDef
import com.bannerlayout.widget.BannerLayout

/**
 * by y on 2017/1/19.
 */

@IntDef(BannerLayout.CENTER, BannerLayout.LEFT, BannerLayout.RIGHT)
@Retention(AnnotationRetention.SOURCE)
annotation class DotsAndTitleSiteMode

