@file:Suppress("UNCHECKED_CAST")

package com.android.banner.viewpager

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.banner.BannerInfo
import com.android.banner.ImageLoaderManager
import com.android.banner.OnBannerClickListener

/**
 * by y on 2016/10/24.
 */
internal class BannerAdapter(private val imageList: List<BannerInfo>,
                             private val loaderManager: ImageLoaderManager<out BannerInfo>?,
                             private val listener: OnBannerClickListener<out BannerInfo>?,
                             private val guide: Boolean) : PagerAdapter() {

    init {
        require(loaderManager != null) { "ImageLoaderManager == null" }
    }

    override fun getCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img = (loaderManager as ImageLoaderManager<BannerInfo>).display(container, imageList[getPosition(position)], position)
        listener?.let { img.setOnClickListener { v -> (it as OnBannerClickListener<BannerInfo>).onBannerClick(v, getPosition(position), imageList[getPosition(position)]) } }
        container.addView(img)
        return img
    }

    private fun getPosition(position: Int): Int = position % imageList.size
}
