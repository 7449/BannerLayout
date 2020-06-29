package com.example

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.OnBannerImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.drawee.view.SimpleDraweeView
import com.nostra13.universalimageloader.core.ImageLoader
import com.squareup.picasso.Picasso

class PicassoImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun display(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return ImageView(container.context).apply {
            Picasso.get()
                    .load(info.bannerUrl)
                    .into(this)
        }
    }
}

class ImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun display(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return View.inflate(container.context, R.layout.banner_item, null).apply {
            ImageLoader.getInstance().displayImage(info.bannerUrl, findViewById<ImageView>(R.id.iv))
        }
    }
}

class GlideImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun display(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return ImageView(container.context).apply {
            Glide.with(container.context)
                    .applyDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(info.bannerUrl)
                    .into(this)
        }
    }
}

class FrescoImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun display(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return SimpleDraweeView(container.context).apply {
            setImageURI(info.bannerUrl)
        }
    }
}