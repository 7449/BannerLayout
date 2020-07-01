# BannerLayout

## core

    implementation 'com.ydevelop:bannerlayout:1.2.0'
    
## page

    implementation 'com.ydevelop:bannerlayout:1.2.0'
    implementation 'com.ydevelop:bannerlayout.page:0.0.2'

## shadow

    implementation 'com.ydevelop:bannerlayout:1.2.0'
    implementation 'com.ydevelop:bannerlayout.shadow:0.0.2'

## transformer

    implementation 'com.ydevelop:bannerlayout:1.2.0'
    implementation 'com.ydevelop:bannerlayout.transformer:0.0.2'
    
## api

    banner.viewPager
    
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