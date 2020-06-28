package com.example.display

import android.view.View
import android.view.ViewGroup
import com.android.banner.ImageLoaderManager
import com.example.NetBannerInfo
import com.facebook.drawee.view.SimpleDraweeView

/**
 * by y on 2017/5/28.
 */

class FrescoSimpleImageManager : ImageLoaderManager<NetBannerInfo> {

    override fun display(container: ViewGroup, info: NetBannerInfo, position: Int): View {
        val draweeView = SimpleDraweeView(container.context)
        draweeView.setImageURI(info.bannerUrl)
        return draweeView
    }
}
