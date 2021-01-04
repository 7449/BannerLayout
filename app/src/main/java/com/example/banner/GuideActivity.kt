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
import com.android.banner.shadow.BannerTip
import com.android.banner.shadow.BannerTipLayout
import com.android.banner.shadow.addTipLayout
import com.example.GlideImageLoader
import com.example.R
import com.example.databinding.ActivityGuideBinding
import com.example.newModel
import com.example.viewBinding

class GuideActivity : AppCompatActivity() {

    private val viewBind by viewBinding(ActivityGuideBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, flag)
        viewBind.buttonGuide.visibility = View.GONE
        viewBind.bannerGuide
                .setOnBannerImageLoader(GlideImageLoader())
                .resource(newModel())
                .addTipLayout(BannerTip(
                        dotSelector = R.drawable.selector_banner_dots,
                        dotSite = BannerTipLayout.CENTER,
                        tipWidth = BannerLayout.MATCH_PARENT,
                        dotWidth = 30,
                        dotHeight = 30,
                        tipHeight = 300))
                .addOnItemClickListener { view, position, _ ->
                    Toast.makeText(view.context, position.toString(), Toast.LENGTH_SHORT).show()
                }
                .doOnPageSelected {
                    viewBind.buttonGuide.visibility = if (it == viewBind.bannerGuide.itemCount - 1) View.VISIBLE else View.GONE
                }
        viewBind.buttonGuide.setOnClickListener { v -> Toast.makeText(v.context, "开启", Toast.LENGTH_SHORT).show() }
    }
}
