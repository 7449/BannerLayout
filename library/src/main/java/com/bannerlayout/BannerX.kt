@file:Suppress("FunctionName", "UNCHECKED_CAST", "NOTHING_TO_INLINE")

package com.bannerlayout

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bannerlayout.widget.BannerLayout
import com.bannerlayout.widget.BannerViewPager

/**
 * @author y
 * @create 2019/4/16
 */

fun BannerLayout.ViewPager(): BannerViewPager = viewPager

fun BannerLayout.ViewPagerLayoutParams(): FrameLayout.LayoutParams? = ViewPager().layoutParams as FrameLayout.LayoutParams?

fun BannerLayout.dotsSize(): Int = imageList.size

fun BannerLayout.duration(): Int = viewPager.duration

fun BannerLayout.bannerStatus(): Int = handler.status

fun <T : BannerInfo> BannerLayout.imageList(): List<T> = imageList as List<T>

fun BannerLayout.removeCallbacksAndMessages() = handler.removeCallbacksAndMessages(null)

inline fun BannerLayout.valueTransformer(bannerTransformer: BannerTransformer) = also { this.bannerTransformer = bannerTransformer }

inline fun BannerLayout.valueTransformerType(bannerTransformerType: Int) = also { this.bannerTransformerType = bannerTransformerType }

inline fun BannerLayout.valueOffscreenPageLimit(offscreenPageLimit: Int) = also { this.offscreenPageLimit = offscreenPageLimit }

inline fun BannerLayout.valueStartRotation(isStartRotation: Boolean) = also { this.isStartRotation = isStartRotation }

inline fun BannerLayout.valueEnabledRadius(enabledRadius: Float) = also { this.enabledRadius = enabledRadius }

inline fun BannerLayout.valueNormalRadius(normalRadius: Float) = also { this.normalRadius = normalRadius }

inline fun BannerLayout.valueEnabledColor(enabledColor: Int) = also { this.enabledColor = enabledColor }

inline fun BannerLayout.valueNormalColor(normalColor: Int) = also { this.normalColor = normalColor }

inline fun BannerLayout.valueGuide(isGuide: Boolean) = also { this.isGuide = isGuide }

inline fun BannerLayout.valueViewPagerTouchMode(viewPagerTouchMode: Boolean) = also { this.viewPagerTouchMode = viewPagerTouchMode }

inline fun BannerLayout.valueErrorImageView(errorImageView: Int) = also { this.errorImageView = errorImageView }

inline fun BannerLayout.valuePlaceImageView(placeImageView: Int) = also { this.placeImageView = placeImageView }

inline fun BannerLayout.valueBannerDuration(bannerDuration: Int) = also { this.bannerDuration = bannerDuration }

inline fun BannerLayout.valueVertical(isVertical: Boolean) = also { this.isVertical = isVertical }

inline fun BannerLayout.valueDelayTime(delayTime: Long) = also { this.delayTime = delayTime }

inline fun BannerLayout.valueVisibleDots(visibleDots: Boolean) = also { this.visibleDots = visibleDots }

inline fun BannerLayout.valueDotsWidth(dotsWidth: Int) = also { this.dotsWidth = dotsWidth }

inline fun BannerLayout.valueDotsHeight(dotsHeight: Int) = also { this.dotsHeight = dotsHeight }

inline fun BannerLayout.valueDotsSelector(dotsSelector: Int) = also { this.dotsSelector = dotsSelector }

inline fun BannerLayout.valueDotsLeftMargin(dotsLeftMargin: Int) = also { this.dotsLeftMargin = dotsLeftMargin }

inline fun BannerLayout.valueDotsRightMargin(dotsRightMargin: Int) = also { this.dotsRightMargin = dotsRightMargin }

inline fun BannerLayout.valueDotsSite(dotsSite: Int) = also { this.dotsSite = dotsSite }

inline fun BannerLayout.valueTipsBackgroundColor(showTipsBackgroundColor: Boolean) = also { this.showTipsBackgroundColor = showTipsBackgroundColor }

inline fun BannerLayout.valueTipsHeight(tipsHeight: Int) = also { this.tipsHeight = tipsHeight }

inline fun BannerLayout.valueTipsWidth(tipsWidth: Int) = also { this.tipsWidth = tipsWidth }

inline fun BannerLayout.valueTipsLayoutBackgroundColor(tipsLayoutBackgroundColor: Int) = also { this.tipsLayoutBackgroundColor = tipsLayoutBackgroundColor }

inline fun BannerLayout.valueTipsSite(tipsSite: Int) = also { this.tipsSite = tipsSite }

inline fun BannerLayout.valueVisibleTitle(visibleTitle: Boolean) = also { this.visibleTitle = visibleTitle }

inline fun BannerLayout.valueTitleSize(titleSize: Float) = also { this.titleSize = titleSize }

inline fun BannerLayout.valueTitleColor(titleColor: Int) = also { this.titleColor = titleColor }

inline fun BannerLayout.valueTitleLeftMargin(titleLeftMargin: Int) = also { this.titleLeftMargin = titleLeftMargin }

inline fun BannerLayout.valueTitleRightMargin(titleRightMargin: Int) = also { this.titleRightMargin = titleRightMargin }

inline fun BannerLayout.valueTitleWidth(titleWidth: Int) = also { this.titleWidth = titleWidth }

inline fun BannerLayout.valueTitleHeight(titleHeight: Int) = also { this.titleHeight = titleHeight }

inline fun BannerLayout.valueTitleSite(titleSite: Int) = also { this.titleSite = titleSite }

inline fun BannerLayout.valuePageNumViewRadius(pageNumViewRadius: Float) = also { this.pageNumViewRadius = pageNumViewRadius }

inline fun BannerLayout.valuePageNumViewPaddingTop(pageNumViewPaddingTop: Int) = also { this.pageNumViewPaddingTop = pageNumViewPaddingTop }

inline fun BannerLayout.valuePageNumViewPaddingLeft(pageNumViewPaddingLeft: Int) = also { this.pageNumViewPaddingLeft = pageNumViewPaddingLeft }

inline fun BannerLayout.valuePageNumViewPaddingBottom(pageNumViewPaddingBottom: Int) = also { this.pageNumViewPaddingBottom = pageNumViewPaddingBottom }

inline fun BannerLayout.valuePageNumViewPaddingRight(pageNumViewPaddingRight: Int) = also { this.pageNumViewPaddingRight = pageNumViewPaddingRight }

inline fun BannerLayout.valuePageNumViewTopMargin(pageNumViewTopMargin: Int) = also { this.pageNumViewTopMargin = pageNumViewTopMargin }

inline fun BannerLayout.valuePageNumViewRightMargin(pageNumViewRightMargin: Int) = also { this.pageNumViewRightMargin = pageNumViewRightMargin }

inline fun BannerLayout.valuePageNumViewBottomMargin(pageNumViewBottomMargin: Int) = also { this.pageNumViewBottomMargin = pageNumViewBottomMargin }

inline fun BannerLayout.valuePageNumViewLeftMargin(pageNumViewLeftMargin: Int) = also { this.pageNumViewLeftMargin = pageNumViewLeftMargin }

inline fun BannerLayout.valuePageNumViewSite(pageNumViewSite: Int) = also { this.pageNumViewSite = pageNumViewSite }

inline fun BannerLayout.valuePageNumViewTextColor(pageNumViewTextColor: Int) = also { this.pageNumViewTextColor = pageNumViewTextColor }

inline fun BannerLayout.valuePageNumViewBackgroundColor(pageNumViewBackgroundColor: Int) = also { this.pageNumViewBackgroundColor = pageNumViewBackgroundColor }

inline fun BannerLayout.valuePageNumViewTextSize(pageNumViewTextSize: Float) = also { this.pageNumViewTextSize = pageNumViewTextSize }

inline fun BannerLayout.valuePageNumViewMark(pageNumViewMark: String) = also { this.pageNumViewMark = pageNumViewMark }

inline fun <T : BannerInfo> BannerLayout.ImageLoaderManager(imageLoaderManager: () -> ImageLoaderManager<T>) = apply {
    this.imageLoaderManager = imageLoaderManager.invoke()
}

inline fun <T : BannerInfo> BannerLayout.addImageLoaderManager(
        crossinline action: (container: ViewGroup, info: T, position: Int) -> View
): BannerLayout {
    val imageManager = object : ImageLoaderManager<T> {
        override fun display(container: ViewGroup, info: T, position: Int): View = action(container, info, position)
    }
    imageLoaderManager = imageManager
    return this
}

inline fun <T : BannerInfo> BannerLayout.addOnItemClickListener(
        crossinline action: (view: View, position: Int, info: T) -> Unit
): BannerLayout {
    val listener = object : OnBannerClickListener<T> {
        override fun onBannerClick(view: View, position: Int, info: T) = action(view, position, info)
    }
    onBannerClickListener = listener
    return this
}

inline fun BannerLayout.doOnPageScrolled(crossinline action: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit) =
        addOnBannerChangeListener(onPageScrolled = action)

inline fun BannerLayout.doOnPageSelected(crossinline action: (position: Int) -> Unit) =
        addOnBannerChangeListener(onPageSelected = action)

inline fun BannerLayout.doOnPageScrollStateChanged(crossinline action: (state: Int) -> Unit) =
        addOnBannerChangeListener(onPageScrollStateChanged = action)

inline fun BannerLayout.addOnBannerChangeListener(
        crossinline onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _: Int, _: Float, _: Int -> },
        crossinline onPageSelected: (position: Int) -> Unit = { _: Int -> },
        crossinline onPageScrollStateChanged: (state: Int) -> Unit = { _: Int -> }
): BannerLayout {
    val listener = object : OnBannerChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = onPageScrolled(position, positionOffset, positionOffsetPixels)
        override fun onPageSelected(position: Int) = onPageSelected(position)
        override fun onPageScrollStateChanged(state: Int) = onPageScrollStateChanged(state)
    }
    onBannerChangeListener = listener
    return this
}

fun GradientDrawable.cornerRadius(radius: Float) = also { cornerRadius = radius }

fun GradientDrawable.color(color: Int) = also { setColor(color) }

fun StateListDrawable.addEnabledState(radius: Float, color: Int) = also { addState(intArrayOf(android.R.attr.state_enabled), GradientDrawable().cornerRadius(radius).color(color)) }

fun StateListDrawable.addNormalState(radius: Float, color: Int) = also { addState(intArrayOf(-android.R.attr.state_enabled), GradientDrawable().cornerRadius(radius).color(color)) }
