package com.android.banner

import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.banner.widget.BannerAdapter
import com.android.banner.widget.BannerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author y
 * @create 2019/4/16
 */
internal fun BannerLayout.defaultImageLoaderManager(): ImageLoaderManager<BannerInfo> {
    val requestOptions = RequestOptions().placeholder(placeImageView).error(errorImageView).centerCrop()
    return object : ImageLoaderManager<BannerInfo> {
        override fun display(container: ViewGroup, info: BannerInfo, position: Int): View {
            val imageView = ImageView(container.context)
            Glide.with(imageView.context).load(info.bannerUrl).apply(requestOptions).into(imageView)
            return imageView
        }
    }
}

internal fun BannerLayout.initBanner(startRotation: Boolean = true) = apply {
    removeAllViews()
    val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dotsSize()
    initViewPager(currentItem)
    handler.handlerPage = currentItem
    handler.handlerDelayTime = delayTime
    if (isGuide) {
        switchBanner(false)
    } else {
        switchBanner(startRotation)
    }
}

internal fun BannerLayout.initViewPager(currentItem: Int) {
    viewPager.viewTouchMode = viewPagerTouchMode
    viewPager.addOnPageChangeListener(this)
    viewPager.setDuration(bannerDuration)
    viewPager.adapter = BannerAdapter(imageList, imageLoaderManager as ImageLoaderManager<BannerInfo>, onBannerClickListener as OnBannerClickListener<BannerInfo>?, isGuide)
    viewPager.setPageTransformer(true, bannerTransformer)
    viewPager.currentItem = currentItem
    addView(viewPager)
}

internal fun BannerLayout.bannerTypedArrayImpl(attrs: AttributeSet?) {
    val context = context
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
    isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, false)
    delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000).toLong()
    isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_banner_start_rotation, false)
    viewPagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, false)
    errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_error_image, R.drawable.ic_default_loading)
    placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_place_image, R.drawable.ic_default_loading)
    bannerDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, 800)
    typedArray.recycle()
}