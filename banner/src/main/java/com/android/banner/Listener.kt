package com.android.banner

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

interface BannerInfo {
    val bannerUrl: Any
    val bannerTitle: String?
        get() = ""
}

interface OnBannerImageLoader<T : BannerInfo> {

    fun instantiateItem(container: ViewGroup, info: T, position: Int): View

    fun destroyItem(container: ViewGroup, position: Int, bannerPosition: Int, any: Any, info: T) {
        container.removeView(any as? View)
    }

    fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    fun setPrimaryItem(container: ViewGroup, position: Int, bannerPosition: Int, any: Any, info: T) {
    }

}

interface OnBannerClickListener<T : BannerInfo> {
    fun onBannerClick(view: View, position: Int, info: T)
}

interface OnBannerResourceChangedListener {
    fun onBannerDataChanged()
}

/**
 * @see [ViewPager.OnPageChangeListener]
 */
interface OnBannerChangeListener {
    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    fun onPageSelected(position: Int)
    fun onPageScrollStateChanged(state: Int)
}