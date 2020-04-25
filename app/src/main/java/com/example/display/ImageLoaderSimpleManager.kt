package com.example.display

import android.widget.ImageView
import com.android.banner.ImageLoaderManager
import com.example.NetBannerInfo
import com.nostra13.universalimageloader.core.ImageLoader

/**
 * by y on 2017/5/28.
 */

class ImageLoaderSimpleManager : ImageLoaderManager<NetBannerInfo> {

    override fun display(imageView: ImageView, info: NetBannerInfo, position: Int) {
        ImageLoader.getInstance().displayImage(info.bannerUrl, imageView)
    }
}
