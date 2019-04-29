package com.example.banner

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bannerlayout.addOnItemClickListener
import com.bannerlayout.doOnPageSelected
import com.bannerlayout.imageList
import com.bannerlayout.widget.BannerLayout
import com.example.NetBannerInfo
import com.example.R
import com.example.newModel
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, flag)
        setContentView(R.layout.activity_guide)
        button_guide.visibility = View.GONE
        banner_guide
                .apply {
                    isGuide = true
                    dotsSelector = R.drawable.selector_banner_dots
                    dotsSite = BannerLayout.BANNER_TIPS_CENTER
                    tipsWidth = BannerLayout.MATCH_PARENT
                    tipsHeight = 300
                    dotsWidth = 30
                    dotsHeight = 30
                }
                .initTips()
                .resource(newModel())

        banner_guide
                .addOnItemClickListener<NetBannerInfo> { view, position, _ ->
                    Toast.makeText(view.context, position.toString(), Toast.LENGTH_SHORT).show()
                }
                .doOnPageSelected {
                    button_guide.visibility = if (it == banner_guide.imageList<NetBannerInfo>().size - 1) View.VISIBLE else View.GONE
                }

        button_guide.setOnClickListener { v -> Toast.makeText(v.context, "开启", Toast.LENGTH_SHORT).show() }
    }
}
