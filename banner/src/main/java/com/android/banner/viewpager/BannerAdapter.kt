@file:Suppress("UNCHECKED_CAST")

package com.android.banner.viewpager

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.banner.BannerInfo
import com.android.banner.OnBannerImageLoader
import com.android.banner.OnBannerClickListener

/**
 * by y on 2016/10/24.
 */
internal class BannerAdapter(private val imageList: List<BannerInfo>,
                             private val loaderManager: OnBannerImageLoader<*>,
                             private val listener: ArrayList<OnBannerClickListener<*>>,
                             private val guide: Boolean) : PagerAdapter() {

    override fun getCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, any: Any): Boolean = view === any

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as? View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img = (loaderManager as OnBannerImageLoader<BannerInfo>).display(container, imageList[getPosition(position)], position)
        listener.forEach {
            img.setOnClickListener { v -> (it as OnBannerClickListener<BannerInfo>).onBannerClick(v, getPosition(position), imageList[getPosition(position)]) }
        }
        container.addView(img)
        return img
    }

    private fun getPosition(position: Int): Int = position % imageList.size
}
