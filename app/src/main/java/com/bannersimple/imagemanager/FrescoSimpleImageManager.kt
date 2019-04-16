package com.bannersimple.imagemanager

import android.view.ViewGroup
import android.widget.ImageView
import com.bannerlayout.ImageLoaderManager
import com.bannersimple.bean.SimpleBannerModel
import com.facebook.drawee.view.SimpleDraweeView

/**
 * by y on 2017/5/28.
 */

class FrescoSimpleImageManager : ImageLoaderManager<SimpleBannerModel> {

    override fun display(container: ViewGroup, info: SimpleBannerModel, position: Int): ImageView {
        val draweeView = SimpleDraweeView(container.context)
        draweeView.setImageURI(info.bannerUrl)
        return draweeView
    }
}
