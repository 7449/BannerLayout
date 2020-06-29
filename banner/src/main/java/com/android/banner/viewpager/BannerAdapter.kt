@file:Suppress("UNCHECKED_CAST")

package com.android.banner.viewpager

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.banner.BannerInfo
import com.android.banner.OnBannerClickListener
import com.android.banner.OnBannerImageLoader


/**
 * by y on 2016/10/24.
 */
internal class BannerAdapter(private val imageList: List<BannerInfo>,
                             private val loaderManager: OnBannerImageLoader<*>,
                             private val listener: ArrayList<OnBannerClickListener<*>>,
                             private val guide: Boolean) : PagerAdapter() {

    private val imageLoader: OnBannerImageLoader<BannerInfo>
        get() = loaderManager as OnBannerImageLoader<BannerInfo>

    override fun getCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, any: Any): Boolean = view === any

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        imageLoader.destroyItem(container, getPosition(position), any, imageList[getPosition(position)])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = imageLoader.instantiateItem(container, imageList[getPosition(position)], position)
        listener.forEach {
            view.setOnClickListener { v -> (it as OnBannerClickListener<BannerInfo>).onBannerClick(v, getPosition(position), imageList[getPosition(position)]) }
        }
        container.addView(view)
        return view
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, any: Any) {
        imageLoader.setPrimaryItem(container, getPosition(position), any, imageList[getPosition(position)])
    }

    private fun getPosition(position: Int): Int = position % imageList.size
}
