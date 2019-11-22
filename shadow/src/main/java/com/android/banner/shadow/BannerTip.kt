package com.android.banner.shadow

import android.graphics.Color
import com.android.banner.widget.BannerLayout

class BannerTip(
        val visibleDot: Boolean = true,
        val dotWidth: Int = 15,
        val dotHeight: Int = 15,
        val dotSelector: Int = 0x0,
        val dotLeftMargin: Int = 10,
        val dotRightMargin: Int = 10,
        val dotSite: Int = BannerTipLayout.RIGHT,
        val enabledRadius: Float = 20f,
        val normalRadius: Float = 20f,
        val enabledColor: Int = Color.BLUE,
        val normalColor: Int = Color.WHITE,
        val tipHeight: Int = 50,
        val tipWidth: Int = BannerLayout.MATCH_PARENT,
        val tipLayoutBackgroundColor: Int = -0x0000000,
        val tipSite: Int = BannerTipLayout.BOTTOM,
        val visibleTitle: Boolean = false,
        val titleSize: Float = 12f,
        val titleColor: Int = Color.YELLOW,
        val titleLeftMargin: Int = 10,
        val titleRightMargin: Int = 10,
        val titleWidth: Int = BannerLayout.WRAP_CONTENT,
        val titleHeight: Int = BannerLayout.WRAP_CONTENT,
        val titleSite: Int = BannerTipLayout.LEFT
)