package androidx.banner.listener

import android.view.View
import androidx.banner.BannerLayout

interface OnBannerClickListener<T : BannerItem> {

    fun onBannerClick(view: View, position: Int, item: T)

    companion object {

        fun BannerLayout.itemClickListener(action: (view: View, position: Int, info: BannerItem) -> Unit) =
            also {
                val listener = object : OnBannerClickListener<BannerItem> {
                    override fun onBannerClick(view: View, position: Int, item: BannerItem) =
                        action(view, position, item)
                }
                addOnBannerClickListener(listener)
            }

    }

}