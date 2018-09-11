package com.bannersimple.imagemanager

import android.view.ViewGroup
import android.widget.ImageView

import com.bannerlayout.listener.ImageLoaderManager
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.squareup.picasso.Picasso

/**
 * by y on 2016/12/2
 */

class PicassoSimpleImageManager : ImageLoaderManager<SimpleBannerModel> {

    override fun display(container: ViewGroup, model: SimpleBannerModel): ImageView {
        val imageView = ImageView(container.context)
        Picasso.get()
                .load(model.bannerUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        return imageView
    }
}
