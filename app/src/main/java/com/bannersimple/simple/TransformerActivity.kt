package com.bannersimple.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

import com.bannerlayout.listener.OnBannerChangeListener
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
                .setBannerTransformer(BannerLayout.ANIMATION_ACCORDION)
                .setDelayTime(300)
                .initListResources(SimpleData.initModel())
                .switchBanner(true)
                .addOnPageChangeListener(
                        object : OnBannerChangeListener {
                            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                            override fun onPageSelected(position: Int) {
                                positionTv.text = "select position:$position"
                            }

                            override fun onPageScrollStateChanged(state: Int) {

                            }
                        })


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                transformerBanner.setBannerTransformer(position)
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
