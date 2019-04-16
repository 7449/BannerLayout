package com.bannersimple.imagemanager

import android.view.ViewGroup
import android.widget.ImageView
import com.bannerlayout.ImageLoaderManager

import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideAppSimpleImageManager : ImageLoaderManager<SimpleBannerModel> {

    private val requestOptions: RequestOptions = RequestOptions().centerCrop()

    override fun display(container: ViewGroup, info: SimpleBannerModel, position: Int): ImageView {
        val imageView = ImageView(container.context)
        Glide.with(imageView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(info.bannerUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        return imageView
    }


}
