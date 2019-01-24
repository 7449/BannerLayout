package com.bannersimple.simple


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bannerlayout.OnBannerChangeListener
import com.bannerlayout.OnBannerClickListener
import com.bannerlayout.widget.BannerLayout
import com.bannerlayout.widget.BannerLayout.Companion.BANNER_TIPS_CENTER
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData
import com.bannersimple.imagemanager.GlideAppSimpleImageManager
import com.bannersimple.refresh.ArrayUtils


class SimpleActivity : AppCompatActivity(), OnBannerClickListener<SimpleBannerModel> {

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
                    imageLoaderManager = GlideAppSimpleImageManager()
                    onBannerClickListener = this@SimpleActivity
                }
                .initPageNumView()
                .initTips()
                .resource(SimpleData.initModel())
                .switchBanner(true)

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
                    onBannerClickListener = this@SimpleActivity
                    onBannerChangeListener = object : OnBannerChangeListener {
                        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                        }

                        override fun onPageSelected(position: Int) {

                        }

                        override fun onPageScrollStateChanged(state: Int) {

                        }
                    }
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
                    onBannerClickListener = this@SimpleActivity
                }
                .resource(SimpleData.initModel())
                .switchBanner(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        defaultBanner.removeHandler()
        customizeBanner.removeHandler()
        verticalBanner.removeHandler()
    }

    override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {
        Toast.makeText(view.context, position.toString(), Toast.LENGTH_SHORT).show()
    }
}
