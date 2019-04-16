package com.bannersimple.simple.issues

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bannerlayout.removeCallbacksAndMessages
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleData


/**
 * by y on 25/07/2017.
 *
 *
 * Issues sample : https://github.com/7449/BannerLayout/issues/12
 */

class Issues12Activity : AppCompatActivity() {

    private lateinit var bannerLayout: BannerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues_12)
        bannerLayout = findViewById(R.id.issues_12_banner)

        bannerLayout
                .apply {
                    dotsSelector = R.drawable.banner
                    pageNumViewBottomMargin = 12
                    pageNumViewLeftMargin = 12
                    pageNumViewRightMargin = 12
                    pageNumViewTopMargin = 12
                }
                .initTips()
                .initPageNumView()
                .resource(SimpleData.initModel())
                .switchBanner(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerLayout.removeCallbacksAndMessages()
    }
}
