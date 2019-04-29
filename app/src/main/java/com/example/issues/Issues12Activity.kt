package com.example.issues

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bannerlayout.removeCallbacksAndMessages
import com.bannerlayout.valueTipsBackgroundColor
import com.bannerlayout.valueTipsLayoutBackgroundColor
import com.example.R
import com.example.newModel
import kotlinx.android.synthetic.main.activity_issues_12.*


/**
 * by y on 25/07/2017.
 *
 *
 * Issues sample : https://github.com/7449/BannerLayout/issues/12
 */

class Issues12Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues_12)
        issues_12_banner
                .apply {
                    dotsSelector = R.drawable.selector_banner_dots
                    pageNumViewBottomMargin = 12
                    pageNumViewLeftMargin = 12
                    pageNumViewRightMargin = 12
                    pageNumViewTopMargin = 12
                }
                .valueTipsLayoutBackgroundColor(R.color.colorBackground)
                .valueTipsBackgroundColor(true)
                .resource(newModel(), showTipsLayout = true, showPageView = true)
    }

    override fun onDestroy() {
        super.onDestroy()
        issues_12_banner.removeCallbacksAndMessages()
    }
}
