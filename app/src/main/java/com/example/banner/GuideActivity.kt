package com.example.banner

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.BannerLayout
import com.android.banner.addOnItemClickListener
import com.android.banner.doOnPageSelected
import com.android.banner.imageLoader
import com.android.banner.shadow.BannerTip
import com.android.banner.shadow.BannerTipLayout
import com.android.banner.shadow.addTipLayout
import com.example.NetBannerInfo
import com.example.R
import com.example.display.GlideAppSimpleImageManager
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
        buttonGuide.visibility = View.GONE
        bannerGuide
                .imageLoader { GlideAppSimpleImageManager() }
                .resource(newModel())
                .addTipLayout(BannerTip(
                        dotSelector = R.drawable.selector_banner_dots,
                        dotSite = BannerTipLayout.CENTER,
                        tipWidth = BannerLayout.MATCH_PARENT,
                        dotWidth = 30,
                        dotHeight = 30,
                        tipHeight = 300))

        bannerGuide
                .addOnItemClickListener<NetBannerInfo> { view, position, _ ->
                    Toast.makeText(view.context, position.toString(), Toast.LENGTH_SHORT).show()
                }
                .doOnPageSelected {
                    buttonGuide.visibility = if (it == bannerGuide.itemCount - 1) View.VISIBLE else View.GONE
                }

        buttonGuide.setOnClickListener { v -> Toast.makeText(v.context, "开启", Toast.LENGTH_SHORT).show() }
    }
}
