package com.android.banner.widget

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
                             private val loaderManager: ImageLoaderManager<BannerInfo>,
                             private val listener: OnBannerClickListener<BannerInfo>?,
                             private val guide: Boolean) : PagerAdapter() {

    override fun getCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img = loaderManager.display(container, imageList[getPosition(position)], position)
        listener?.let { img.setOnClickListener { v -> it.onBannerClick(v, getPosition(position), imageList[getPosition(position)]) } }
        container.addView(img)
        return img
    }

    private fun getPosition(position: Int): Int = position % imageList.size
}
