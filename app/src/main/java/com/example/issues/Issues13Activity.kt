package com.example.issues

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.page.addPageView
import com.android.banner.transformer.BannerTransformer
import com.example.GlideImageLoader
import com.example.databinding.ActivityIssues13Binding
import com.example.newModel
import com.example.viewBinding
import kotlin.math.abs
import kotlin.math.max

/**
 * Issues sample : https://github.com/7449/BannerLayout/issues/13
 */
class Issues13Activity : AppCompatActivity() {

    private val viewBind by viewBinding(ActivityIssues13Binding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind.issues13Banner.clipChildren = false
        viewBind.issues13Banner
                .setOnBannerImageLoader(GlideImageLoader())
                .setTransformer(MeizuBannerTransformer())
                .resource(newModel())
                .addPageView(pageBottomMargin = 12, pageLeftMargin = 12, pageRightMargin = 12, pageTopMargin = 12)
        val layoutParams = viewBind.issues13Banner.viewPagerLayoutParams()
        layoutParams?.leftMargin = 50
        layoutParams?.rightMargin = 50
        val handler = Handler(Looper.getMainLooper())
        val r = Runnable {
            viewBind.issues13Banner.beginFakeDrag()
            viewBind.issues13Banner.fakeDragBy(1.0f)
            viewBind.issues13Banner.endFakeDrag()
        }
        handler.postDelayed(r, 10)
    }

    override fun onDestroy() {
        viewBind.issues13Banner.release()
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