package com.example.issues

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bannerlayout.BannerTransformer
import com.bannerlayout.ViewPager
import com.bannerlayout.ViewPagerLayoutParams
import com.bannerlayout.removeCallbacksAndMessages
import com.example.newModel
import kotlinx.android.synthetic.main.activity_issues_13.*


/**
 * Issues sample : https://github.com/7449/BannerLayout/issues/13
 */

class Issues13Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.R.layout.activity_issues_13)
        issues_13_banner.clipChildren = false
        issues_13_banner
                .apply {
                    dotsSelector = com.example.R.drawable.selector_banner_dots
                    pageNumViewBottomMargin = 12
                    pageNumViewLeftMargin = 12
                    pageNumViewRightMargin = 12
                    pageNumViewTopMargin = 12
                    offscreenPageLimit = 3
                    bannerTransformer = MeizuBannerTransformer()
                }
                .initTips()
                .initPageNumView()
                .resource(newModel())
        val layoutParams = issues_13_banner.ViewPagerLayoutParams()
        layoutParams?.leftMargin = 50
        layoutParams?.rightMargin = 50
        val handler = Handler()
        val r = Runnable {
            issues_13_banner.ViewPager().beginFakeDrag()
            issues_13_banner.ViewPager().fakeDragBy(1.0f)
            issues_13_banner.ViewPager().endFakeDrag()
        }
        handler.postDelayed(r, 10)
    }

    override fun onDestroy() {
        issues_13_banner.removeCallbacksAndMessages()
        super.onDestroy()
    }
}

class MeizuBannerTransformer : BannerTransformer() {
    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 -> page.scaleY = 0.8f
            position <= 1 -> page.scaleY = Math.max(0.8f, 1 - Math.abs(position))
            else -> page.scaleY = 0.8f
        }
    }
}