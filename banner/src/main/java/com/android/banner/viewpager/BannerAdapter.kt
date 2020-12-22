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
class BannerAdapter(
        private val imageList: List<BannerInfo>,
        private val loaderManager: OnBannerImageLoader<BannerInfo>,
        private val listener: ArrayList<OnBannerClickListener<BannerInfo>>,
        private val guide: Boolean
) : PagerAdapter() {

    override fun getCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return loaderManager.isViewFromObject(view, any)
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        loaderManager.destroyItem(container, position, getPosition(position), any, imageList[getPosition(position)])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = loaderManager.instantiateItem(container, imageList[getPosition(position)], position)
        if (listener.isNotEmpty()) {
            view.setOnClickListener {
                listener.forEach { listener ->
                    listener.onBannerClick(it, getPosition(position), imageList[getPosition(position)])
                }
            }
        }
        container.addView(view)
        return view
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, any: Any) {
        loaderManager.setPrimaryItem(container, position, getPosition(position), any, imageList[getPosition(position)])
    }

    private fun getPosition(position: Int): Int = position % imageList.size

}
