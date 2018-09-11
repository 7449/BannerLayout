package com.bannersimple.simple


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.bannerlayout.listener.OnBannerChangeListener
import com.bannerlayout.listener.OnBannerClickListener
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData
import com.bannersimple.imagemanager.GlideAppSimpleImageManager
import com.bannersimple.refresh.ArrayUtils


class SimpleActivity : AppCompatActivity(), OnBannerClickListener<SimpleBannerModel> {

    private lateinit var defaultBanner: BannerLayout
    private lateinit var customizeBanner: BannerLayout
    private lateinit var verticalBanner: BannerLayout

    private val image = arrayOf<Any>(
            "http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491588490192&di=c7c9dfd2fc4b1eeb5a4a874ec9a30d1d&imgtype=0&src=http%3A%2F%2Fmvimg2.meitudata.com%2F55713dd0165c89055.jpg",
            "http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg",
            "http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg")
    private val title = arrayOf(
            "At that time just love, this time to break up",
            "Shame it ~",
            "The legs are not long but thin",
            "Late at night")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        defaultBanner = findViewById(R.id.default_banner)
        customizeBanner = findViewById(R.id.customize_banner)
        verticalBanner = findViewById(R.id.vertical_banner)

        defaultBanner
                .initPageNumView()
                .setDuration(2000)
                .setImageLoaderManager(GlideAppSimpleImageManager())
                .setTipsDotsSelector(R.drawable.banner)
                .setPageNumViewMargin(12, 12, 12, 12)
                .initListResources(SimpleData.initModel())
                .switchBanner(true)
                .setOnBannerClickListener(this)

        customizeBanner
                .initPageNumView()
                .setPageNumViewMargin(12, 12, 12, 12)
                .setPageNumViewMark(" & ")
                .setPageNumViewSite(BannerLayout.PAGE_NUM_VIEW_BOTTOM_RIGHT)
                .setDotsSite(BannerLayout.CENTER)
                .setPageNumViewTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
                .initListResources(ArrayUtils.initArrayResources(image, title))
                .switchBanner(true)
                .setOnBannerClickListener(this)
                .addOnPageChangeListener(
                        object : OnBannerChangeListener {
                            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                            }

                            override fun onPageSelected(position: Int) {

                            }

                            override fun onPageScrollStateChanged(state: Int) {

                            }
                        })

        verticalBanner
                .setVertical(true)
                .initTips(true, true, true)
                .setOnBannerClickListener(this)
                .initListResources(SimpleData.initModel())
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
