package com.bannersimple.simple.issues

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
                .initPageNumView()
                .initTips()
                .setTipsDotsSelector(R.drawable.banner)
                .setPageNumViewMargin(12, 12, 12, 12)
                .initListResources(SimpleData.initModel())
                .switchBanner(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerLayout.removeHandler()
    }
}
