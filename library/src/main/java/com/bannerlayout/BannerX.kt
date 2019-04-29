@file:Suppress("FunctionName", "UNCHECKED_CAST")

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
