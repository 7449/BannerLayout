package com.example.display

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.OnBannerImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.NetBannerInfo
import com.example.R

class GlideAppSimpleImageManager : OnBannerImageLoader<NetBannerInfo> {

    private val requestOptions: RequestOptions = RequestOptions().centerCrop()

    override fun display(container: ViewGroup, info: NetBannerInfo, position: Int): View {
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
