package com.android.banner

import android.view.View
import android.view.ViewGroup

fun BannerLayout.addOnItemClickListener(action: (view: View, position: Int, info: BannerInfo) -> Unit) = also {
    val listener = object : OnBannerClickListener<BannerInfo> {
        override fun onBannerClick(view: View, position: Int, info: BannerInfo) = action(view, position, info)
    }
    addOnBannerClickListener(listener)
}

fun BannerLayout.addOnBannerResourceChangedListener(action: () -> Unit) = also {
    val listener = object : OnBannerResourceChangedListener {
        override fun onBannerDataChanged() {
            action.invoke()
        }
    }
    addOnBannerResourceChangedListener(listener)
}

fun BannerLayout.doOnPageScrolled(action: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit) = addOnBannerChangeListener(onPageScrolled = action)

fun BannerLayout.doOnPageSelected(action: (position: Int) -> Unit) = addOnBannerChangeListener(onPageSelected = action)

fun BannerLayout.doOnPageScrollStateChanged(action: (state: Int) -> Unit) = addOnBannerChangeListener(onPageScrollStateChanged = action)

fun BannerLayout.addOnBannerChangeListener(
        onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _: Int, _: Float, _: Int -> },
        onPageSelected: (position: Int) -> Unit = { _: Int -> },
        onPageScrollStateChanged: (state: Int) -> Unit = { _: Int -> }
) = also {
    val listener = object : OnBannerChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = onPageScrolled(position, positionOffset, positionOffsetPixels)
        override fun onPageSelected(position: Int) = onPageSelected(position)
        override fun onPageScrollStateChanged(state: Int) = onPageScrollStateChanged(state)
    }
    addOnBannerChangeListener(listener)
}