package com.bannersimple.simple


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bannerlayout.ImageLoader
import com.bannerlayout.OnBannerChangeListener
import com.bannerlayout.removeCallbacksAndMessages
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class SimpleActivity : AppCompatActivity() {

    private lateinit var defaultBanner: BannerLayout
    private lateinit var customizeBanner: BannerLayout
    private lateinit var verticalBanner: BannerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        defaultBanner = findViewById(R.id.default_banner)
        customizeBanner = findViewById(R.id.customize_banner)
        verticalBanner = findViewById(R.id.vertical_banner)

        defaultBanner
                .apply {
                    delayTime = 2000
                    dotsSelector = R.drawable.banner
                    pageNumViewBottomMargin = 12
                    pageNumViewLeftMargin = 12
                    pageNumViewRightMargin = 12
                    pageNumViewTopMargin = 12
                }
                .initPageNumView()
                .initTips()
                .resource(SimpleData.initModel())
                .switchBanner(true)
        defaultBanner.ImageLoader<SimpleBannerModel> { container, info, position ->
            val imageView = ImageView(container.context)
            Glide.with(imageView.context)
                    .load(info.bannerUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .fallback(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            imageView
        }

        customizeBanner
                .apply {
                    pageNumViewBottomMargin = 12
                    pageNumViewLeftMargin = 12
                    pageNumViewRightMargin = 12
                    pageNumViewTopMargin = 12
                    pageNumViewMark = " & "
                    pageNumViewSite = BannerLayout.PAGE_NUM_VIEW_BOTTOM_RIGHT
                    pageNumViewTextColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
                    dotsSite = BannerLayout.BANNER_TIPS_CENTER
                }
                .OnBannerChangeListener {
                    onPageScrollStateChanged { }
                    onPageScrolled { position, positionOffset, positionOffsetPixels -> }
                    onPageSelected { position -> }
                }
                .initPageNumView()
                .resource(SimpleData.initModel())
                .switchBanner(true)

        verticalBanner
                .apply {
                    isVertical = true
                    showTipsBackgroundColor = true
                    visibleDots = true
                    visibleTitle = true
                }
                .resource(SimpleData.initModel())
                .switchBanner(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        defaultBanner.removeCallbacksAndMessages()
        customizeBanner.removeCallbacksAndMessages()
        verticalBanner.removeCallbacksAndMessages()
    }
}
