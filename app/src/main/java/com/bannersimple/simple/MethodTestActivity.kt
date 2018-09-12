package com.bannersimple.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bannerlayout.listener.OnBannerChangeListener
import com.bannerlayout.listener.OnBannerClickListener
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData

/**
 * by y on 2017/5/28.
 */

class MethodTestActivity : AppCompatActivity(), OnBannerClickListener<SimpleBannerModel>, OnBannerChangeListener {

    private lateinit var xmlBannerLayout: BannerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_method_test)
        xmlBannerLayout = findViewById(R.id.method_banner)
        xmlBannerLayout.resource(SimpleData.initModel())

//        val bannerLayout = BannerLayout(applicationContext)
//        bannerLayout
//                .apply {
//                    delayTime = 3000
//                    errorImageView = R.mipmap.ic_launcher
//                    placeImageView = R.mipmap.ic_launcher
//                    bannerDuration = 3000
//                    viewPagerTouchMode = true
//                    isVertical = true
//                    titleColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)
//                    titleSize = 23F
//                    tipsLayoutBackgroundColor = ContextCompat.getColor(applicationContext, R.color.colorAccent)
//                    dotsSelector = R.drawable.banner
//                    tipsWidth = BannerLayout.MATCH_PARENT
//                    tipsHeight = 300
//                    tipsSite = BannerLayout.TOP
//                    ...
//                }
//                .initPageNumView()
//                .initTips()
//                .resource(SimpleData.initModel())
//                .switchBanner(true)

    }

    override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
