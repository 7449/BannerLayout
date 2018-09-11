package com.bannersimple.imagemanager

import android.view.ViewGroup
import android.widget.ImageView

import com.bannerlayout.listener.ImageLoaderManager
import com.bannersimple.bean.SimpleBannerModel
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * by y on 2017/5/28.
 */

class ImageLoaderSimpleManager : ImageLoaderManager<SimpleBannerModel> {

    override fun display(container: ViewGroup, model: SimpleBannerModel): ImageView {
        val imageView = ImageView(container.context)
        val imageLoader = ImageLoader.getInstance()
        imageLoader.displayImage(model.bannerUrl, imageView)
        return imageView
    }
}
