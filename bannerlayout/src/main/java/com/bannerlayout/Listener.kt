package com.bannerlayout

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

interface BannerModelCallBack {
    val bannerUrl: Any
    val bannerTitle: String
}

interface ImageLoaderManager<in T : BannerModelCallBack> {
    fun display(container: ViewGroup, model: T): ImageView
}

interface OnBannerChangeListener {
    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    fun onPageSelected(position: Int)
    fun onPageScrollStateChanged(state: Int)
}

open class SimpleOnBannerChangeListener : OnBannerChangeListener {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
}

interface OnBannerClickListener<in T : BannerModelCallBack> {
    fun onBannerClick(view: View, position: Int, model: T)
}

interface OnBannerImageClickListener<T : BannerModelCallBack> {
    fun onBannerClick(view: View, position: Int, model: T)
}