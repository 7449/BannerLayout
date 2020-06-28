package com.example.display

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.OnBannerImageLoader
import com.example.NetBannerInfo
import com.example.R
import com.squareup.picasso.Picasso

/**
 * by y on 2016/12/2
 */

class PicassoSimpleImageManager : OnBannerImageLoader<NetBannerInfo> {

    override fun display(container: ViewGroup, info: NetBannerInfo, position: Int): View {
        val imageView = ImageView(container.context)
        Picasso.get()
                .load(info.bannerUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        return imageView
    }
}
