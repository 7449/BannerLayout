 # BannerLayout

## core

    implementation 'com.ydevelop:bannerlayout:1.1.9'
    
## page

    implementation 'com.ydevelop:bannerlayout:1.1.9'
    implementation 'com.ydevelop:bannerlayout.page:0.0.1'

## shadow

    implementation 'com.ydevelop:bannerlayout:1.1.9'
    implementation 'com.ydevelop:bannerlayout.shadow:0.0.1'

## transformer

    implementation 'com.ydevelop:bannerlayout:1.1.9'
    implementation 'com.ydevelop:bannerlayout.transformer:0.0.1'
    
## api

    banner.bannerStatus()
    
    banner.removeCallbacksAndMessages()
    
    banner.transformer()
    
    banner.offscreenPageLimit()
    
    banner.viewPagerTouchMode()
    
    banner.bannerDuration()
    
    banner.guide()
    
    banner.delayTime()
    
    banner.play()
    
    banner.resource()
    
    banner.playBanner()
    
    banner.stopBanner()
    
## ImageLoaderManager

    class ImageLoaderSimpleManager : ImageLoaderManager<NetBannerInfo> {
    
        override fun display(container: ViewGroup, model: NetBannerInfo): View {
            val imageView = ImageView(container.context)
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage(model.bannerUrl, imageView)
            return imageView
        }
    }
    
    banner.imageLoaderManager{ ImageLoaderSimpleManager() }
    
    banner.setImageLoaderManager()
    
## Vertical scrolling

    implementation 'com.ydevelop:bannerlayout:1.1.9'
    implementation 'com.ydevelop:bannerlayout.transformer:0.0.1'

    banner.verticalTransformer()
    
## click

    banner.addOnItemClickListener()
    
## pageChanged

    banner.doOnpageScrolled()
    
    banner.doOnPageSelected()
    
    banner.doOnPageScrollStateChanged()