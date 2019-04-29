package com.example.display

import android.view.ViewGroup
import android.widget.ImageView
import com.bannerlayout.ImageLoaderManager

import com.example.R
import com.example.NetBannerInfo
import com.squareup.picasso.Picasso

/**
 * by y on 2016/12/2
 */

class PicassoSimpleImageManager : ImageLoaderManager<NetBannerInfo> {

    override fun display(container: ViewGroup, info: NetBannerInfo, position: Int): ImageView {
        val imageView = ImageView(container.context)
        Picasso.get()
                .load(info.bannerUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        return imageView
    }
}
