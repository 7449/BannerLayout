package com.example.issues

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.page.addPageView
import com.example.GlideImageLoader
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
        issues12Banner
                .setOnBannerImageLoader(GlideImageLoader())
                .resource(newModel())
                .addPageView(pageBottomMargin = 12, pageLeftMargin = 12, pageRightMargin = 12, pageTopMargin = 12)
    }

    override fun onDestroy() {
        super.onDestroy()
        issues12Banner.release()
    }
}
