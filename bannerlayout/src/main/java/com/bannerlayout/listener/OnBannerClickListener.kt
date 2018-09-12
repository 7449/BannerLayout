package com.bannerlayout.listener

import android.view.View

/**
 * by y on 2016/11/11
 *
 *
 * Banner Click event, object for the return of data
 */

interface OnBannerClickListener<in T : BannerModelCallBack> {
    fun onBannerClick(view: View, position: Int, model: T)
}
