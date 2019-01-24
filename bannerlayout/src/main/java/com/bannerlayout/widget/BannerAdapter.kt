package com.bannerlayout.widget

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bannerlayout.BannerModelCallBack
import com.bannerlayout.ImageLoaderManager
import com.bannerlayout.OnBannerClickListener


/**
 * by y on 2016/10/24.
 */
internal class BannerAdapter(private val imageList: List<BannerModelCallBack>,
                             private val loaderManager: ImageLoaderManager<BannerModelCallBack>,
                             private val listener: OnBannerClickListener<BannerModelCallBack>?,
                             private val guide: Boolean) : PagerAdapter() {

    override fun getCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img = loaderManager.display(container, imageList[getPosition(position)])
        img.setOnClickListener { v -> listener?.onBannerClick(v, getPosition(position), imageList[getPosition(position)]) }
        container.addView(img)
        return img
    }

    private fun getPosition(position: Int): Int = position % imageList.size
}
