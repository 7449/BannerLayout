package com.example.issues

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bannerlayout.doOnPageSelected
import com.bannerlayout.removeCallbacksAndMessages
import com.bannerlayout.widget.BannerLayout
import com.example.R
import com.example.newModel
import kotlinx.android.synthetic.main.activity_issues_10.*


/**
 * by y on 25/07/2017.
 *
 *
 * Issues sample : https://github.com/7449/BannerLayout/issues/10
 *
 *
 * test:
 * onePlus 3T  1080;
 * virtual machine  480;
 */

/**
 * Just a simple example, so only two resolutions are tested
 */
class Issues10Activity : AppCompatActivity() {

    companion object {
        private val TAG = Issues10Activity::class.java.simpleName
    }

    private var isShowTips = false
    private var preEnablePosition = 0
    private var sizelnstagram: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues_10)
        test()
        testlnstagram()
    }

    override fun onDestroy() {
        super.onDestroy()
        banner.removeCallbacksAndMessages()
        banner_lnstagram.removeCallbacksAndMessages()
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun testlnstagram() {
        val data = newModel()
        banner_lnstagram
                .apply {
                    pageNumViewBottomMargin = 10
                    pageNumViewLeftMargin = 10
                    pageNumViewRightMargin = 10
                    pageNumViewTopMargin = 10
                    delayTime = 1000
                }
                .resource(data, showPageView = true)


        //  dots
        sizelnstagram = data.size
        ll_view.removeAllViews()
        for (i in 0 until sizelnstagram) {
            val view = View(this)
            view.background = ContextCompat.getDrawable(this, R.drawable.selector_banner_dots)
            view.isEnabled = i == 0
            val params = LinearLayout.LayoutParams(15, 15)
            view.layoutParams = params
            params.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER
            params.leftMargin = 5
            params.rightMargin = 5
            ll_view.addView(view)
        }

        banner_lnstagram.doOnPageSelected { position ->
            ll_view.getChildAt(position).isEnabled = true
            ll_view.getChildAt(preEnablePosition).isEnabled = false
            preEnablePosition = position
            val startView = ll_view.getChildAt(0)
            val endView = ll_view.getChildAt(sizelnstagram - 1)
            if (position == sizelnstagram - 1) {
                startView.scaleX = 0.6f
                startView.scaleY = 0.6f
                endView.scaleX = 1f
                endView.scaleY = 1f
            } else if (position == 0) {
                startView.scaleX = 1f
                startView.scaleY = 1f
                endView.scaleX = 0.6f
                endView.scaleY = 0.6f
            }
        }
    }


    private fun test() {
        val display = window.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        Log.i(TAG, dm.widthPixels.toString() + "   " + dm.heightPixels)
        val dotWidthAndHeight: Int
        val dotMargin: Int
        if (dm.widthPixels >= 1080) {
            dotWidthAndHeight = 15
            dotMargin = 6
        } else {
            dotWidthAndHeight = 6
            dotMargin = 3
        }
        banner
                .apply {
                    pageNumViewBottomMargin = 10
                    pageNumViewLeftMargin = 10
                    pageNumViewRightMargin = 10
                    pageNumViewTopMargin = 10
                    dotsSite = BannerLayout.BANNER_TIPS_CENTER
                    dotsWidth = dotWidthAndHeight
                    dotsHeight = dotWidthAndHeight
                    dotsRightMargin = dotMargin
                    dotsLeftMargin = dotMargin
                }
                .resource(newModel(), showTipsLayout = true, showPageView = true)
        findViewById<View>(R.id.btn_alter_count)
                .setOnClickListener { alterBannerCount() }
    }

    private fun alterBannerCount() {
        val alterData: ArrayList<com.example.NetBannerInfo> = if (!isShowTips) {
            newModel()
        } else {
            newModel()
        }
        isShowTips = !isShowTips
        val size = alterData.size
        if (size <= 1) {
            banner
                    .apply {
                        showTipsBackgroundColor = false
                        visibleDots = false
                        visibleTitle = false
                    }
                    .resource(alterData, isStartRotation = false)
            Toast.makeText(this, "size <=1 , stopBanner , not show tipsLayout", Toast.LENGTH_SHORT).show()
        } else {
            banner
                    .apply {
                        showTipsBackgroundColor = true
                        visibleDots = true
                        visibleTitle = true
                    }
                    .resource(alterData, isStartRotation = true)
            Toast.makeText(this, "size >1 , startBanner , show tipsLayout", Toast.LENGTH_SHORT).show()
        }
    }


}
