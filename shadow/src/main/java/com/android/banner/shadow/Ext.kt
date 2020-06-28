package com.android.banner.shadow

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import androidx.core.content.ContextCompat
import com.android.banner.BannerLayout
import com.android.banner.addEnabledState
import com.android.banner.addNormalState
import com.android.banner.doOnPageSelected

internal fun BannerTipLayout.dotsSelector(bannerTip: BannerTip): Drawable {
    return if (bannerTip.dotSelector == 0x0)
        StateListDrawable()
                .addEnabledState(bannerTip.enabledRadius, bannerTip.enabledColor)
                .addNormalState(bannerTip.normalRadius, bannerTip.normalColor)
    else
        ContextCompat.getDrawable(context, bannerTip.dotSelector)
                ?: StateListDrawable()
                        .addEnabledState(bannerTip.enabledRadius, bannerTip.enabledColor)
                        .addNormalState(bannerTip.normalRadius, bannerTip.normalColor)
}

fun BannerLayout.addTipLayout(bannerTip: BannerTip = BannerTip()) {
    require(checkViewPager()) { "must add ViewPage first;" }
    val tipLayout = BannerTipLayout(context)
    val params = tipLayout.run {
        removeAllViews()
        if (bannerTip.visibleDot) {
            initDot(dotsSize, bannerTip.dotHeight, bannerTip.dotWidth, bannerTip.dotLeftMargin, bannerTip.dotRightMargin, bannerTip.dotSite, bannerTip)
        }
        if (bannerTip.visibleTitle) {
            initTitle(bannerTip.titleColor, bannerTip.titleSize, bannerTip.titleLeftMargin, bannerTip.titleRightMargin, bannerTip.titleWidth, bannerTip.titleHeight, bannerTip.titleSite)
            setTitle(getItem(0).bannerTitle)
        }
        initTip(bannerTip.tipSite, bannerTip.tipWidth, bannerTip.tipHeight, bannerTip.tipLayoutBackgroundColor)
    }
    doOnPageSelected {
        if (bannerTip.visibleDot) {
            tipLayout.changeDotsPosition(it)
        }
        if (bannerTip.visibleTitle) {
            tipLayout.setTitle(getItem(it).bannerTitle)
        }
    }
    removeTipLayout()
    addView(tipLayout, params)
}

fun BannerLayout.removeTipLayout() {
    for (index in 0 until childCount) {
        getChildAt(index)?.let {
            if (it is BannerTipLayout) {
                removeView(it)
            }
        }
    }
}