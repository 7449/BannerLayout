package com.bannersimple.bean

import com.bannerlayout.BannerModelCallBack


/**
 * by y on 2016/10/24
 */

class SimpleBannerModel : BannerModelCallBack {
    var image: Any? = null
    var title: String? = null

    override val bannerUrl: String
        get() = image.toString()

    override val bannerTitle: String
        get() = title ?: ""

    constructor(image: Any) {
        this.image = image
    }

    constructor(image: Any, title: String) {
        this.image = image
        this.title = title
    }

    constructor()
}
