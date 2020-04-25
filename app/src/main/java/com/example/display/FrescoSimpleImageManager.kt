package com.example.display

import android.widget.ImageView
import com.android.banner.ImageLoaderManager
import com.example.NetBannerInfo

/**
 * by y on 2017/5/28.
 */
@Deprecated("")
class FrescoSimpleImageManager : ImageLoaderManager<NetBannerInfo> {

    override fun display(imageView: ImageView, info: NetBannerInfo, position: Int) {
    }
}
