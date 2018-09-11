package com.bannersimple.imagemanager

import android.view.ViewGroup
import android.widget.ImageView

import com.bannerlayout.listener.ImageLoaderManager
import com.bannersimple.bean.SimpleBannerModel
import com.facebook.drawee.view.SimpleDraweeView

/**
 * by y on 2017/5/28.
 */

class FrescoSimpleImageManager : ImageLoaderManager<SimpleBannerModel> {

    override fun display(container: ViewGroup, model: SimpleBannerModel): ImageView {
        val draweeView = SimpleDraweeView(container.context)
        draweeView.setImageURI(model.bannerUrl)
        return draweeView
    }
}
