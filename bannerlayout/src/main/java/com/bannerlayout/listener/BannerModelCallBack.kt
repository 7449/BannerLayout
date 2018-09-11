package com.bannerlayout.listener

/**
 * by y on 2017/4/11
 */

interface BannerModelCallBack<out T> {

    val bannerUrl: T

    val bannerTitle: String
}
