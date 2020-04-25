package com.android.banner

import android.view.View
import android.widget.ImageView

interface BannerInfo {
    val bannerUrl: Any
    val bannerTitle: String?
}

interface ImageLoaderManager<T : BannerInfo> {
    fun display(imageView: ImageView, info: T, position: Int)
}

interface OnBannerClickListener<T : BannerInfo> {
    fun onBannerClick(view: View, position: Int, info: T)
}

interface OnBannerChangeListener {
    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    fun onPageSelected(position: Int)
    fun onPageScrollStateChanged(state: Int)
}