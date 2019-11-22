package com.android.banner

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.android.banner.transformer.BannerTransformer
import com.android.banner.widget.BannerLayout
import com.android.banner.widget.BannerViewPager

/**
 * @author y
 * @create 2019/4/16
 */

fun BannerLayout.checkViewPager(): Boolean {
    for (index in 0 until childCount) {
        if (getChildAt(index) is BannerViewPager) {
            return true
        }
    }
    return false
}

fun BannerLayout.viewPager(): BannerViewPager = viewPager

fun BannerLayout.viewPagerLayoutParams(): FrameLayout.LayoutParams? = viewPager().layoutParams as FrameLayout.LayoutParams?

fun BannerLayout.dotsSize(): Int = imageList.size

fun BannerLayout.duration(): Int = viewPager.duration

fun BannerLayout.bannerStatus(): Int = handler.status

fun <T : BannerInfo> BannerLayout.imageList(): List<T> = imageList as List<T>

fun BannerLayout.removeCallbacksAndMessages() = handler.removeCallbacksAndMessages(null)

fun BannerLayout.valueTransformer(bannerTransformer: BannerTransformer) = also { this.bannerTransformer = bannerTransformer }

fun BannerLayout.valueOffscreenPageLimit(offscreenPageLimit: Int) = also { this.offscreenPageLimit = offscreenPageLimit }

fun BannerLayout.valueStartRotation(isStartRotation: Boolean) = also { this.isStartRotation = isStartRotation }

fun BannerLayout.valueEnabledRadius(enabledRadius: Float) = also { this.enabledRadius = enabledRadius }

fun BannerLayout.valueNormalRadius(normalRadius: Float) = also { this.normalRadius = normalRadius }

fun BannerLayout.valueEnabledColor(enabledColor: Int) = also { this.enabledColor = enabledColor }

fun BannerLayout.valueNormalColor(normalColor: Int) = also { this.normalColor = normalColor }

fun BannerLayout.valueGuide(isGuide: Boolean) = also { this.isGuide = isGuide }

fun BannerLayout.valueViewPagerTouchMode(viewPagerTouchMode: Boolean) = also { this.viewPagerTouchMode = viewPagerTouchMode }

fun BannerLayout.valueErrorImageView(errorImageView: Int) = also { this.errorImageView = errorImageView }

fun BannerLayout.valuePlaceImageView(placeImageView: Int) = also { this.placeImageView = placeImageView }

fun BannerLayout.valueBannerDuration(bannerDuration: Int) = also { this.bannerDuration = bannerDuration }

fun BannerLayout.valueDelayTime(delayTime: Long) = also { this.delayTime = delayTime }

fun BannerLayout.valueVisibleDots(visibleDots: Boolean) = also { this.visibleDots = visibleDots }

fun BannerLayout.valueDotsWidth(dotsWidth: Int) = also { this.dotsWidth = dotsWidth }

fun BannerLayout.valueDotsHeight(dotsHeight: Int) = also { this.dotsHeight = dotsHeight }

fun BannerLayout.valueDotsSelector(dotsSelector: Int) = also { this.dotsSelector = dotsSelector }

fun BannerLayout.valueDotsLeftMargin(dotsLeftMargin: Int) = also { this.dotsLeftMargin = dotsLeftMargin }

fun BannerLayout.valueDotsRightMargin(dotsRightMargin: Int) = also { this.dotsRightMargin = dotsRightMargin }

fun BannerLayout.valueDotsSite(dotsSite: Int) = also { this.dotsSite = dotsSite }

fun BannerLayout.valueTipsBackgroundColor(showTipsBackgroundColor: Boolean) = also { this.showTipsBackgroundColor = showTipsBackgroundColor }

fun BannerLayout.valueTipsHeight(tipsHeight: Int) = also { this.tipsHeight = tipsHeight }

fun BannerLayout.valueTipsWidth(tipsWidth: Int) = also { this.tipsWidth = tipsWidth }

fun BannerLayout.valueTipsLayoutBackgroundColor(tipsLayoutBackgroundColor: Int) = also { this.tipsLayoutBackgroundColor = tipsLayoutBackgroundColor }

fun BannerLayout.valueTipsSite(tipsSite: Int) = also { this.tipsSite = tipsSite }

fun BannerLayout.valueVisibleTitle(visibleTitle: Boolean) = also { this.visibleTitle = visibleTitle }

fun BannerLayout.valueTitleSize(titleSize: Float) = also { this.titleSize = titleSize }

fun BannerLayout.valueTitleColor(titleColor: Int) = also { this.titleColor = titleColor }

fun BannerLayout.valueTitleLeftMargin(titleLeftMargin: Int) = also { this.titleLeftMargin = titleLeftMargin }

fun BannerLayout.valueTitleRightMargin(titleRightMargin: Int) = also { this.titleRightMargin = titleRightMargin }

fun BannerLayout.valueTitleWidth(titleWidth: Int) = also { this.titleWidth = titleWidth }

fun BannerLayout.valueTitleHeight(titleHeight: Int) = also { this.titleHeight = titleHeight }

fun BannerLayout.valueTitleSite(titleSite: Int) = also { this.titleSite = titleSite }

fun <T : BannerInfo> BannerLayout.imageLoaderManager(imageLoaderManager: () -> ImageLoaderManager<T>) = apply { this.imageLoaderManager = imageLoaderManager.invoke() }

fun <T : BannerInfo> BannerLayout.setImageLoaderManager(action: (container: ViewGroup, info: T, position: Int) -> View): BannerLayout {
    val imageManager = object : ImageLoaderManager<T> {
        override fun display(container: ViewGroup, info: T, position: Int): View = action(container, info, position)
    }
    imageLoaderManager = imageManager
    return this
}

fun <T : BannerInfo> BannerLayout.addOnItemClickListener(action: (view: View, position: Int, info: T) -> Unit): BannerLayout {
    val listener = object : OnBannerClickListener<T> {
        override fun onBannerClick(view: View, position: Int, info: T) = action(view, position, info)
    }
    onBannerClickListener = listener
    return this
}

fun BannerLayout.doOnPageScrolled(action: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit) = addOnBannerChangeListener(onPageScrolled = action)

fun BannerLayout.doOnPageSelected(action: (position: Int) -> Unit) = addOnBannerChangeListener(onPageSelected = action)

fun BannerLayout.doOnPageScrollStateChanged(action: (state: Int) -> Unit) = addOnBannerChangeListener(onPageScrollStateChanged = action)

fun BannerLayout.addOnBannerChangeListener(
        onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _: Int, _: Float, _: Int -> },
        onPageSelected: (position: Int) -> Unit = { _: Int -> },
        onPageScrollStateChanged: (state: Int) -> Unit = { _: Int -> }
): BannerLayout {
    val listener = object : OnBannerChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = onPageScrolled(position, positionOffset, positionOffsetPixels)
        override fun onPageSelected(position: Int) = onPageSelected(position)
        override fun onPageScrollStateChanged(state: Int) = onPageScrollStateChanged(state)
    }
    onBannerChangeListener.add(listener)
    return this
}

fun GradientDrawable.cornerRadius(radius: Float) = also { cornerRadius = radius }

fun GradientDrawable.color(color: Int) = also { setColor(color) }

fun StateListDrawable.addEnabledState(radius: Float, color: Int) = also { addState(intArrayOf(android.R.attr.state_enabled), GradientDrawable().cornerRadius(radius).color(color)) }

fun StateListDrawable.addNormalState(radius: Float, color: Int) = also { addState(intArrayOf(-android.R.attr.state_enabled), GradientDrawable().cornerRadius(radius).color(color)) }