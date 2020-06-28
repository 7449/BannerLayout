package com.example.display

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.OnBannerImageLoader
import com.example.NetBannerInfo
import com.example.R
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * by y on 2017/5/28.
 */

class ImageLoaderSimpleManager : OnBannerImageLoader<NetBannerInfo> {

    override fun display(container: ViewGroup, info: NetBannerInfo, position: Int): View {
        val inflate = View.inflate(container.context, R.layout.banner_item, null)
        val imageLoader = ImageLoader.getInstance()
        imageLoader.displayImage(info.bannerUrl, inflate.findViewById<ImageView>(R.id.iv))
        return inflate
    }
}
