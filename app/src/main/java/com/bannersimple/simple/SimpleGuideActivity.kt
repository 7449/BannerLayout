package com.bannersimple.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.bannerlayout.widget.BannerLayout

import com.bannersimple.R
import com.bannersimple.SimpleOnBannerChangeListener
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
                .initTips()
                .setGuide(true)
                .setTipsDotsSelector(R.drawable.banner)
                .setDotsSite(BannerLayout.CENTER)
                .setTipsWidthAndHeight(BannerLayout.MATCH_PARENT, 300)
                .setDotsWidthAndHeight(30, 30)
                .initListResources(SimpleData.update())

        guideBanner.addOnPageChangeListener(object : SimpleOnBannerChangeListener() {
            override fun onPageSelected(position: Int) {
                guideButton.visibility = if (position == guideBanner.getImageList().size - 1) View.VISIBLE else View.GONE
            }
        })

        guideButton.setOnClickListener { v -> Toast.makeText(v.context, "开启", Toast.LENGTH_SHORT).show() }
    }
}
