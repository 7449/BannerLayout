package com.bannersimple.bean

import com.bannerlayout.BannerModelCallBack


/**
 * by y on 2016/10/24
 */

class SimpleBannerModel(var image: Any, var title: String) : BannerModelCallBack {
    override val bannerUrl: String = image.toString()
    override val bannerTitle: String = title
}
