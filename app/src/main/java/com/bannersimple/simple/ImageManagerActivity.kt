package com.bannersimple.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleData
import com.bannersimple.imagemanager.FrescoSimpleImageManager
import com.bannersimple.imagemanager.ImageLoaderSimpleManager
import com.bannersimple.imagemanager.PicassoSimpleImageManager

/**
 * by y on 2017/5/28.
 */

class ImageManagerActivity : AppCompatActivity() {

    private lateinit var frescoBanner: BannerLayout
    private lateinit var imageLoaderBanner: BannerLayout
    private lateinit var picassoBanner: BannerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagemanager)
        frescoBanner = findViewById(R.id.fresco_banner)
        imageLoaderBanner = findViewById(R.id.imageloader_banner)
        picassoBanner = findViewById(R.id.picasso_banner)


        frescoBanner
                .apply {
                    imageLoaderManager = FrescoSimpleImageManager()
                }
                .resource(SimpleData.initModel())
                .switchBanner(true)

        imageLoaderBanner
                .apply {
                    imageLoaderManager = ImageLoaderSimpleManager()
                }
                .resource(SimpleData.initModel())
                .switchBanner(false)

        picassoBanner
                .apply {
                    imageLoaderManager = PicassoSimpleImageManager()
                }
                .resource(SimpleData.initModel())
                .switchBanner(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        frescoBanner.removeHandler()
        imageLoaderBanner.removeHandler()
        picassoBanner.removeHandler()
    }
}
