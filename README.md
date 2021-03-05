# BannerLayout

## core

    implementation 'com.ydevelop:bannerlayout:1.2.1'
    
## page

    implementation 'com.ydevelop:bannerlayout:1.2.1'
    implementation 'com.ydevelop:bannerlayout.page:0.0.3'

## shadow

    implementation 'com.ydevelop:bannerlayout:1.2.1'
    implementation 'com.ydevelop:bannerlayout.shadow:0.0.3'

## transformer

    implementation 'com.ydevelop:bannerlayout:1.2.1'
    implementation 'com.ydevelop:bannerlayout.transformer:0.0.3'
    
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