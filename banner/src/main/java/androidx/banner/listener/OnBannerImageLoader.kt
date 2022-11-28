package androidx.banner.listener

import android.view.View
import android.view.ViewGroup

interface OnBannerImageLoader<T : BannerItem> {

    fun instantiateItem(container: ViewGroup, item: T): View

}