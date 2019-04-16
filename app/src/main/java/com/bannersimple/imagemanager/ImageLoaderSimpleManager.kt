package com.bannersimple.imagemanager

import android.view.ViewGroup
import android.widget.ImageView
import com.bannerlayout.ImageLoaderManager

import com.bannersimple.bean.SimpleBannerModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * by y on 2017/5/28.
 */

class ImageLoaderSimpleManager : ImageLoaderManager<SimpleBannerModel> {

    override fun display(container: ViewGroup, info: SimpleBannerModel, position: Int): ImageView {
        val imageView = ImageView(container.context)
        val imageLoader = ImageLoader.getInstance()
        imageLoader.displayImage(info.bannerUrl, imageView)
        return imageView
    }
}
