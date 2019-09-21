package com.example.display

import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.ImageLoaderManager

import com.example.NetBannerInfo
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * by y on 2017/5/28.
 */

class ImageLoaderSimpleManager : ImageLoaderManager<NetBannerInfo> {

    override fun display(container: ViewGroup, info: NetBannerInfo, position: Int): ImageView {
        val imageView = ImageView(container.context)
        val imageLoader = ImageLoader.getInstance()
        imageLoader.displayImage(info.bannerUrl, imageView)
        return imageView
    }
}
