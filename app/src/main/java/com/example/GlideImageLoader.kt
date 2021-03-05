package com.example

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.OnBannerImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun instantiateItem(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return ImageView(container.context).apply {
            Glide.with(container.context)
                    .applyDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(info.bannerUrl)
                    .into(this)
        }
    }
}