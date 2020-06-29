package com.example.issues

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.page.addPageView
import com.android.banner.transformer.BannerTransformer
import com.example.GlideImageLoader
import com.example.R
import com.example.newModel
import kotlinx.android.synthetic.main.activity_issues_13.*
import kotlin.math.abs
import kotlin.math.max

/**
 * Issues sample : https://github.com/7449/BannerLayout/issues/13
 */
class Issues13Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues_13)
        issues13Banner.clipChildren = false
        issues13Banner
                .setOnBannerImageLoader(GlideImageLoader())
                .setTransformer(MeizuBannerTransformer())
                .resource(newModel())
                .addPageView(pageBottomMargin = 12, pageLeftMargin = 12, pageRightMargin = 12, pageTopMargin = 12)
        val layoutParams = issues13Banner.viewPagerLayoutParams()
        layoutParams?.leftMargin = 50
        layoutParams?.rightMargin = 50
        val handler = Handler()
        val r = Runnable {
            issues13Banner.viewPager.beginFakeDrag()
            issues13Banner.viewPager.fakeDragBy(1.0f)
            issues13Banner.viewPager.endFakeDrag()
        }
        handler.postDelayed(r, 10)
    }

    override fun onDestroy() {
        issues13Banner.release()
        super.onDestroy()
    }
}

class MeizuBannerTransformer : BannerTransformer() {
    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 -> page.scaleY = 0.8f
            position <= 1 -> page.scaleY = max(0.8f, 1 - abs(position))
            else -> page.scaleY = 0.8f
        }
    }
}