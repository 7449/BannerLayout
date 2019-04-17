@file:Suppress("UNCHECKED_CAST")

package com.bannerlayout

import android.view.View
import android.view.ViewGroup

interface BannerInfo {
    val bannerUrl: Any
    val bannerTitle: String?
}

interface ImageLoaderManager<T : BannerInfo> {
    fun display(container: ViewGroup, info: T, position: Int): View
}

interface OnBannerClickListener<T : BannerInfo> {
    fun onBannerClick(view: View, position: Int, info: T)
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

class SimpleOnBannerChangeListenerKt {

    private var onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null
    private var onPageSelected: ((position: Int) -> Unit)? = null
    private var onPageScrollStateChanged: ((state: Int) -> Unit)? = null

    fun onPageScrolled(onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit) {
        this.onPageScrolled = onPageScrolled
    }

    fun onPageSelected(onPageSelected: (position: Int) -> Unit) {
        this.onPageSelected = onPageSelected
    }

    fun onPageScrollStateChanged(onPageScrollStateChanged: (state: Int) -> Unit) {
        this.onPageScrollStateChanged = onPageScrollStateChanged
    }

    internal fun build(): OnBannerChangeListener {
        return object : OnBannerChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                onPageSelected?.invoke(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                onPageScrollStateChanged?.invoke(state)
            }
        }
    }
}
