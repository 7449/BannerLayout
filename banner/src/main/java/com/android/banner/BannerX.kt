package com.android.banner

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.android.banner.transformer.Banner2Transformer

/**
 * @author y
 * @create 2019/4/16
 */

fun BannerLayout.checkViewPager2(): Boolean {
    for (index in 0 until childCount) {
        if (getChildAt(index) is ViewPager2) {
            return true
        }
    }
    return false
}

//fun BannerLayout.viewPager(): BannerViewPager = viewPager

fun BannerLayout.viewPager2(): ViewPager2 = viewPager2

//fun BannerLayout.viewPagerLayoutParams(): FrameLayout.LayoutParams? = viewPager().layoutParams as FrameLayout.LayoutParams?

fun BannerLayout.viewPager2LayoutParams(): FrameLayout.LayoutParams? = viewPager2().layoutParams as FrameLayout.LayoutParams?

fun BannerLayout.dotsSize(): Int = imageList.size

//fun BannerLayout.duration(): Int = viewPager.duration

fun BannerLayout.bannerStatus(): Int = bannerHandler.status

fun <T : BannerInfo> BannerLayout.imageList(): List<T> = imageList as List<T>

fun BannerLayout.removeCallbacksAndMessages() = bannerHandler.removeCallbacksAndMessages(null)

fun BannerLayout.transformer(bannerTransformer: Banner2Transformer) = also { this.bannerTransformer = bannerTransformer }

fun BannerLayout.offscreenPageLimit(offscreenPageLimit: Int) = also { this.offscreenPageLimit = offscreenPageLimit }

fun BannerLayout.guide() = also { this.isGuide = true }

fun BannerLayout.viewPagerTouchMode(viewPagerTouchMode: Boolean) = also { this.viewPagerTouchMode = viewPagerTouchMode }

fun BannerLayout.bannerDuration(bannerDuration: Int) = also { this.bannerDuration = bannerDuration }

fun BannerLayout.delayTime(delayTime: Long) = also { this.delayTime = delayTime }

fun BannerLayout.play(isStartRotation: Boolean) = also { this.isPlay = isStartRotation }

fun BannerLayout.resource(imageList: ArrayList<out BannerInfo>) = also { resource(imageList, false) }

fun BannerLayout.playBanner() = also { playBanner(true) }

fun BannerLayout.stopBanner() = also { playBanner(false) }

fun <T : BannerInfo> BannerLayout.imageLoaderManager(imageLoaderManager: () -> ImageLoaderManager<T>) = apply { this.imageLoaderManager = imageLoaderManager.invoke() }

fun <T : BannerInfo> BannerLayout.setImageLoaderManager(action: (imageView: ImageView, info: T, position: Int) -> Unit): BannerLayout {
    val imageManager = object : ImageLoaderManager<T> {
        override fun display(imageView: ImageView, info: T, position: Int) {
            action(imageView, info, position)
        }
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