package com.bannerlayout.listener

import android.view.View

interface OnBannerImageClickListener<T : BannerModelCallBack> {
    fun onBannerClick(view: View, position: Int, model: T)
}