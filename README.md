# BannerLayout

	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}

## core

    implementation 'com.github.7449.BannerLayout:banner:v1.0.0'

## page

    implementation 'com.github.7449.BannerLayout:banner:v1.0.0'
    implementation 'com.github.7449.BannerLayout:banner-page:v1.0.0'

## shadow

    implementation 'com.github.7449.BannerLayout:banner:v1.0.0'
    implementation 'com.github.7449.BannerLayout:banner-shadow:v1.0.0'

## transformer

    implementation 'com.github.7449.BannerLayout:banner:v1.0.0'
    implementation 'com.github.7449.BannerLayout:banner-transformer:v1.0.0'

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