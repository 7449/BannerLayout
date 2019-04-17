@file:Suppress("FunctionName", "UNCHECKED_CAST")

package com.bannerlayout

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

fun BannerLayout.OnBannerChangeListener(onBannerChangeListenerKt: SimpleOnBannerChangeListenerKt.() -> Unit) = apply {
    this.onBannerChangeListener = SimpleOnBannerChangeListenerKt().also(onBannerChangeListenerKt).build()
}

fun <T : BannerInfo> BannerLayout.ImageLoader(imageLoaderManager: (container: ViewGroup, info: T, position: Int) -> View) = apply {
    this.imageLoaderManager = object : ImageLoaderManager<T> {
        override fun display(container: ViewGroup, info: T, position: Int): View {
            return imageLoaderManager.invoke(container, info, position)
        }
    }
}

fun <T : BannerInfo> BannerLayout.ImageLoaderManager(imageLoaderManager: () -> ImageLoaderManager<T>) = apply {
    this.imageLoaderManager = imageLoaderManager.invoke()
}

fun <T : BannerInfo> BannerLayout.OnItemClick(onBannerClickListener: (view: View, position: Int, info: T) -> Unit) = apply {
    this.onBannerClickListener = object : OnBannerClickListener<T> {
        override fun onBannerClick(view: View, position: Int, info: T) {
            onBannerClickListener.invoke(view, position, info)
        }
    }
}

fun <T : BannerInfo> BannerLayout.OnItemClickListener(onBannerClickListener: () -> OnBannerClickListener<T>) = apply {
    this.onBannerClickListener = onBannerClickListener.invoke()
}

//fun BannerLayout.setAttributes(bannerAttributesKt: BannerAttributesKt.() -> Unit) = apply {
//    BannerAttributesKt().also(bannerAttributesKt).build(this)
//}
//
//class BannerAttributesKt {
//
//    private var bannerTransformer: (() -> BannerTransformer)? = null
//    private var bannerTransformerType: (() -> Int)? = null
//    private var offscreenPageLimit: (() -> Int)? = null
//    private var isStartRotation: (() -> Boolean)? = null
//    private var enabledRadius: (() -> Float)? = null
//    private var normalRadius: (() -> Float)? = null
//    private var enabledColor: (() -> Int)? = null
//    private var normalColor: (() -> Int)? = null
//    private var isGuide: (() -> Boolean)? = null
//    private var viewPagerTouchMode: (() -> Boolean)? = null
//    private var errorImageView: (() -> Int)? = null
//    private var placeImageView: (() -> Int)? = null
//    private var bannerDuration: (() -> Int)? = null
//    private var isVertical: (() -> Boolean)? = null
//    private var delayTime: (() -> Long)? = null
//    private var visibleDots: (() -> Boolean)? = null
//    private var dotsWidth: (() -> Int)? = null
//    private var dotsHeight: (() -> Int)? = null
//    private var dotsSelector: (() -> Int)? = null
//    private var dotsLeftMargin: (() -> Int)? = null
//    private var dotsRightMargin: (() -> Int)? = null
//    private var dotsSite: (() -> Int)? = null
//    private var showTipsBackgroundColor: (() -> Boolean)? = null
//    private var tipsHeight: (() -> Int)? = null
//    private var tipsWidth: (() -> Int)? = null
//    private var tipsLayoutBackgroundColor: (() -> Int)? = null
//    private var tipsSite: (() -> Int)? = null
//    private var visibleTitle: (() -> Boolean)? = null
//    private var titleSize: (() -> Float)? = null
//    private var titleColor: (() -> Int)? = null
//    private var titleLeftMargin: (() -> Int)? = null
//    private var titleRightMargin: (() -> Int)? = null
//    private var titleWidth: (() -> Int)? = null
//    private var titleHeight: (() -> Int)? = null
//    private var titleSite: (() -> Int)? = null
//    private var pageNumViewRadius: (() -> Float)? = null
//    private var pageNumViewPaddingTop: (() -> Int)? = null
//    private var pageNumViewPaddingLeft: (() -> Int)? = null
//    private var pageNumViewPaddingBottom: (() -> Int)? = null
//    private var pageNumViewPaddingRight: (() -> Int)? = null
//    private var pageNumViewTopMargin: (() -> Int)? = null
//    private var pageNumViewRightMargin: (() -> Int)? = null
//    private var pageNumViewBottomMargin: (() -> Int)? = null
//    private var pageNumViewLeftMargin: (() -> Int)? = null
//    private var pageNumViewSite: (() -> Int)? = null
//    private var pageNumViewTextColor: (() -> Int)? = null
//    private var pageNumViewBackgroundColor: (() -> Int)? = null
//    private var pageNumViewTextSize: (() -> Float)? = null
//    private var pageNumViewMark: (() -> String)? = null
//
//    internal fun build(bannerLayout: BannerLayout) {
//        bannerTransformer?.let { bannerLayout.bannerTransformer = it.invoke() }
//        bannerTransformerType?.let { bannerLayout.bannerTransformerType = it.invoke() }
//        offscreenPageLimit?.let { bannerLayout.offscreenPageLimit = it.invoke() }
//        isStartRotation?.let { bannerLayout.isStartRotation = it.invoke() }
//        enabledRadius?.let { bannerLayout.enabledRadius = it.invoke() }
//        normalRadius?.let { bannerLayout.normalRadius = it.invoke() }
//        enabledColor?.let { bannerLayout.enabledColor = it.invoke() }
//        normalColor?.let { bannerLayout.normalColor = it.invoke() }
//        isGuide?.let { bannerLayout.isGuide = it.invoke() }
//        normalColor?.let { bannerLayout.normalColor = it.invoke() }
//        viewPagerTouchMode?.let { bannerLayout.viewPagerTouchMode = it.invoke() }
//        errorImageView?.let { bannerLayout.errorImageView = it.invoke() }
//        placeImageView?.let { bannerLayout.placeImageView = it.invoke() }
//        bannerDuration?.let { bannerLayout.bannerDuration = it.invoke() }
//        isVertical?.let { bannerLayout.isVertical = it.invoke() }
//        delayTime?.let { bannerLayout.delayTime = it.invoke() }
//        visibleDots?.let { bannerLayout.visibleDots = it.invoke() }
//        dotsWidth?.let { bannerLayout.dotsWidth = it.invoke() }
//        dotsHeight?.let { bannerLayout.dotsHeight = it.invoke() }
//        dotsHeight?.let { bannerLayout.dotsHeight = it.invoke() }
//        dotsSelector?.let { bannerLayout.dotsSelector = it.invoke() }
//        dotsLeftMargin?.let { bannerLayout.dotsLeftMargin = it.invoke() }
//        dotsRightMargin?.let { bannerLayout.dotsRightMargin = it.invoke() }
//        dotsSite?.let { bannerLayout.dotsSite = it.invoke() }
//        showTipsBackgroundColor?.let { bannerLayout.showTipsBackgroundColor = it.invoke() }
//        tipsHeight?.let { bannerLayout.tipsHeight = it.invoke() }
//        tipsWidth?.let { bannerLayout.tipsWidth = it.invoke() }
//        tipsLayoutBackgroundColor?.let { bannerLayout.tipsLayoutBackgroundColor = it.invoke() }
//        tipsSite?.let { bannerLayout.tipsSite = it.invoke() }
//        visibleTitle?.let { bannerLayout.visibleTitle = it.invoke() }
//        titleSize?.let { bannerLayout.titleSize = it.invoke() }
//        titleColor?.let { bannerLayout.titleColor = it.invoke() }
//        titleLeftMargin?.let { bannerLayout.titleLeftMargin = it.invoke() }
//        titleRightMargin?.let { bannerLayout.titleRightMargin = it.invoke() }
//        titleWidth?.let { bannerLayout.titleWidth = it.invoke() }
//        titleHeight?.let { bannerLayout.titleHeight = it.invoke() }
//        titleSite?.let { bannerLayout.titleSite = it.invoke() }
//        pageNumViewRadius?.let { bannerLayout.pageNumViewRadius = it.invoke() }
//        pageNumViewPaddingTop?.let { bannerLayout.pageNumViewPaddingTop = it.invoke() }
//        pageNumViewPaddingLeft?.let { bannerLayout.pageNumViewPaddingLeft = it.invoke() }
//        pageNumViewPaddingBottom?.let { bannerLayout.pageNumViewPaddingBottom = it.invoke() }
//        pageNumViewPaddingRight?.let { bannerLayout.pageNumViewPaddingRight = it.invoke() }
//        pageNumViewTopMargin?.let { bannerLayout.pageNumViewTopMargin = it.invoke() }
//        pageNumViewRightMargin?.let { bannerLayout.pageNumViewRightMargin = it.invoke() }
//        pageNumViewBottomMargin?.let { bannerLayout.pageNumViewBottomMargin = it.invoke() }
//        pageNumViewLeftMargin?.let { bannerLayout.pageNumViewLeftMargin = it.invoke() }
//        pageNumViewSite?.let { bannerLayout.pageNumViewSite = it.invoke() }
//        pageNumViewTextColor?.let { bannerLayout.pageNumViewTextColor = it.invoke() }
//        pageNumViewBackgroundColor?.let { bannerLayout.pageNumViewBackgroundColor = it.invoke() }
//        pageNumViewTextSize?.let { bannerLayout.pageNumViewTextSize = it.invoke() }
//        pageNumViewMark?.let { bannerLayout.pageNumViewMark = it.invoke() }
//    }
//
//}
