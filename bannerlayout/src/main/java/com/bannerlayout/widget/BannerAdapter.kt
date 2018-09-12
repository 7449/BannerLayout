package com.bannerlayout.widget

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bannerlayout.listener.BannerModelCallBack
import com.bannerlayout.listener.ImageLoaderManager
import com.bannerlayout.listener.OnBannerImageClickListener


/**
 * by y on 2016/10/24.
 */
internal class BannerAdapter(private val imageList: List<BannerModelCallBack>,
                             private val imageLoaderManage: ImageLoaderManager<BannerModelCallBack>,
                             private val isGuide: Boolean) : PagerAdapter() {

    var imageClickListener: OnBannerImageClickListener<BannerModelCallBack>? = null

    override fun getCount(): Int = if (isGuide) imageList.size else Integer.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val img = imageLoaderManage.display(container, imageList[getPosition(position)])
        img.setOnClickListener { v ->
            imageClickListener?.onBannerClick(v, getPosition(position), imageList[getPosition(position)])
        }
        container.addView(img)
        return img
    }

    private fun getPosition(position: Int): Int {
        return position % imageList.size
    }
}
