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
    
    banner.resource(list,boolean)
    
    banner.start()
    
    banner.stop()
    
    banner.play(boolean)
    
    banner.addOnBannerChangeListener(OnBannerChangeListener)
    
    banner.addOnBannerResourceChangedListener(OnBannerResourceChangedListener)
    
    banner.addOnBannerClickListener(OnBannerClickListener)
    
    banner.setOnBannerImageLoader(OnBannerImageLoader)
    
    banner.delayTime(Long)
    
    banner.touchMode(boolean)
    
    banner.duration(Int)
    
    banner.setTransformer(BannerTransformer)

    banner.setOffscreenPageLimit(Int)

    banner.viewPagerLayoutParams()

    banner.release()

    banner.getItem(position)

    banner.itemCount

    banner.status

    banner.checkViewPager()
    
## kotlin expand

    banner.setOnBannerImageLoader()

    banner.addOnItemClickListener()

    banner.addOnBannerResourceChangedListener()

    banner.doOnPageScrolled()

    banner.doOnPageSelected()

    banner.doOnPageScrollStateChanged()

    banner.addOnBannerChangeListener()

## ImageLoaderManager

    class ImageLoader : OnBannerImageLoader<BannerInfo> {
        override fun instantiateItem(container: ViewGroup, info: BannerInfo, position: Int): View {
            return ImageView(container.context).apply {
                Glide.with(container.context)
                        .applyDefaultRequestOptions(RequestOptions().centerCrop())
                        .load(info.bannerUrl)
                        .into(this)
            }
        }
    }