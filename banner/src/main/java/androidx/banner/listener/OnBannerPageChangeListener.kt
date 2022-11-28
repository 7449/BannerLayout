package androidx.banner.listener

import androidx.banner.BannerLayout

interface OnBannerPageChangeListener {

    fun onBannerPageScrolled(position: Int, offset: Float, pixels: Int) {}

    fun onBannerPageSelected(position: Int) {}

    fun onBannerPageScrollStateChanged(state: Int) {}

    companion object {

        fun BannerLayout.doOnPageScrolled(action: (position: Int, offset: Float, pixels: Int) -> Unit) =
            bannerChangeListener(onPageScrolled = action)

        fun BannerLayout.doOnPageSelected(action: (position: Int) -> Unit) =
            bannerChangeListener(onPageSelected = action)

        fun BannerLayout.doOnPageScrollStateChanged(action: (state: Int) -> Unit) =
            bannerChangeListener(onPageScrollStateChanged = action)

        fun BannerLayout.bannerChangeListener(
            onPageScrolled: (position: Int, offset: Float, pixels: Int) -> Unit = { _: Int, _: Float, _: Int -> },
            onPageSelected: (position: Int) -> Unit = { _: Int -> },
            onPageScrollStateChanged: (state: Int) -> Unit = { _: Int -> }
        ) = also {
            val listener = object : OnBannerPageChangeListener {
                override fun onBannerPageScrolled(
                    position: Int,
                    offset: Float,
                    pixels: Int
                ) = onPageScrolled(position, offset, pixels)

                override fun onBannerPageSelected(position: Int) = onPageSelected(position)
                override fun onBannerPageScrollStateChanged(state: Int) =
                    onPageScrollStateChanged(state)
            }
            addOnBannerChangeListener(listener)
        }

    }

}