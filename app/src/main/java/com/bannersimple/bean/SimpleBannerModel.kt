package com.bannersimple.bean

import com.bannerlayout.BannerInfo


/**
 * by y on 2016/10/24
 */

class SimpleBannerModel(image: Any, var title: String) : BannerInfo {
    override val bannerUrl: String = image.toString()
    override val bannerTitle: String = title
}
