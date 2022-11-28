package androidx.banner.shadow

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import androidx.banner.BannerLayout
import androidx.banner.addEnabledState
import androidx.banner.addNormalState
import androidx.banner.listener.BannerItem
import androidx.banner.listener.OnBannerPageChangeListener.Companion.doOnPageSelected
import androidx.core.content.ContextCompat

internal fun BannerShadowLayout.dotsSelector(bannerTip: BannerShadowConfig): Drawable {
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

fun BannerLayout.addShadowLayout(config: BannerShadowConfig = BannerShadowConfig()) = also {
    require(checkViewPager) { "must add ViewPage first;" }
    val tipLayout = BannerShadowLayout(context)
    val params = tipLayout.run {
        removeAllViews()
        initDot(
            itemCount,
            config.dotHeight,
            config.dotWidth,
            config.dotLeftMargin,
            config.dotRightMargin,
            config.dotSite,
            config
        )
        if (config.visibleTitle) {
            initTitle(
                config.titleColor,
                config.titleSize,
                config.titleLeftMargin,
                config.titleRightMargin,
                config.titleWidth,
                config.titleHeight,
                config.titleSite
            )
            setTitle(getItem<BannerItem>(0).bannerTitle)
        }
        shadow(
            config.shadowSite,
            config.shadowWidth,
            config.shadowHeight,
            config.shadowBackgroundColor
        )
    }
    doOnPageSelected {
        tipLayout.changeDotsPosition(it)
        if (config.visibleTitle) {
            tipLayout.setTitle(getItem<BannerItem>(it).bannerTitle)
        }
    }
    removeTipLayout()
    addView(tipLayout, params)
}

fun BannerLayout.removeTipLayout() {
    for (index in 0 until childCount) {
        getChildAt(index)?.let {
            if (it is BannerShadowLayout) {
                removeView(it)
            }
        }
    }
}