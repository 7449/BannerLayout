package com.bannersimple.simple

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bannerlayout.OnBannerClickListener
import com.bannerlayout.SimpleOnBannerChangeListener
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData

class SimpleGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, flag)
        setContentView(R.layout.activity_guide)
        val guideBanner = findViewById<BannerLayout>(R.id.banner_guide)
        val guideButton = findViewById<AppCompatButton>(R.id.button_guide)
        guideButton.visibility = View.GONE
        guideBanner
                .apply {
                    isGuide = true
                    dotsSelector = R.drawable.banner
                    dotsSite = BannerLayout.CENTER
                    tipsWidth = BannerLayout.MATCH_PARENT
                    tipsHeight = 300
                    dotsWidth = 30
                    dotsHeight = 30
                    onBannerClickListener = object : OnBannerClickListener<SimpleBannerModel> {
                        override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {
                            Toast.makeText(view.context, position.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .initTips()
                .resource(SimpleData.update())

        guideBanner.onBannerChangeListener = (object : SimpleOnBannerChangeListener() {
            override fun onPageSelected(position: Int) {
                guideButton.visibility = if (position == guideBanner.getImageList().size - 1) View.VISIBLE else View.GONE
            }
        })

        guideButton.setOnClickListener { v -> Toast.makeText(v.context, "开启", Toast.LENGTH_SHORT).show() }
    }
}
