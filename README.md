# BannerLayout

	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}

[![](https://jitpack.io/v/7449/BannerLayout.svg)](https://jitpack.io/#7449/BannerLayout)

    implementation 'com.github.7449.BannerLayout:banner:release.version'
    implementation 'com.github.7449.BannerLayout:banner-page:release.version'
    implementation 'com.github.7449.BannerLayout:banner-shadow:release.version'
    implementation 'com.github.7449.BannerLayout:banner-transformer:release.version'

## api

    banner.resource(items,boolean)
    
    banner.start()
    banner.stop()
    banner.release()

    banner.delayTime(Long)
    banner.touchMode(boolean)
    banner.duration(Int)
    banner.setTransformer(PageTransformer)

    banner.getItem(position)
    banner.itemCount
    banner.checkViewPager

    banner.setOnBannerImageLoader()

    banner.addOnItemClickListener()
    banner.removeOnBannerClickListener()

    banner.addOnBannerResourceChangedListener()
    banner.removeOnBannerResourceChangedListener()

    banner.doOnPageScrolled()
    banner.doOnPageSelected()
    banner.doOnPageScrollStateChanged()

    banner.addOnBannerChangeListener()
    banner.removeOnBannerChangeListener()

## ImageLoaderManager

    class GlideImageLoader : OnBannerImageLoader<SimpleBannerItem> {
        override fun instantiateItem(
            container: ViewGroup,
            item: SimpleBannerItem,
        ): View {
            return ImageView(container.context).apply {
                Glide.with(container.context)
                    .applyDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(item.bannerUrl)
                    .into(this)
            }
        }
    }