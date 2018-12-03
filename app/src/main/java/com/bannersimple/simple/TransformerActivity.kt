package com.bannersimple.simple

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.bannerlayout.ANIMATION_ACCORDION
import com.bannerlayout.OnBannerChangeListener
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleData

/**
 * by y on 2017/5/28.
 */

class TransformerActivity : AppCompatActivity() {

    private lateinit var transformerBanner: BannerLayout
    private lateinit var positionTv: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformer)
        transformerBanner = findViewById(R.id.transformer_banner)
        val spinner = findViewById<Spinner>(R.id.transformer_spinner)
        positionTv = findViewById(R.id.banner_position)
        positionTv.text = "select position:" + 0
        transformerBanner
                .apply {
                    delayTime = 300
                    bannerTransformerType = ANIMATION_ACCORDION
                    onBannerChangeListener = object : OnBannerChangeListener {
                        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                        override fun onPageSelected(position: Int) {
                            positionTv.text = "select position:$position"
                        }

                        override fun onPageScrollStateChanged(state: Int) {

                        }
                    }
                }
                .resource(SimpleData.initModel())
                .switchBanner(true)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                transformerBanner.bannerTransformerType = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        transformerBanner.removeHandler()
    }
}
