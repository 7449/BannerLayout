package androidx.banner.listener

import androidx.banner.BannerLayout

interface OnBannerResourceChangedListener {

    fun onBannerDataChanged()

    companion object {

        fun BannerLayout.resourceChangedListener(action: () -> Unit) = also {
            val listener = object : OnBannerResourceChangedListener {
                override fun onBannerDataChanged() {
                    action.invoke()
                }
            }
            addOnBannerResourceChangedListener(listener)
        }

    }

}