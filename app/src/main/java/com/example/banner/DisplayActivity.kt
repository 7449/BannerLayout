package com.example.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.*
import kotlinx.android.synthetic.main.activity_display.*

/**
 * by y on 2017/5/28.
 */
class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        title = "ImageLoader Example"
        frescoBanner
                .setOnBannerImageLoader(FrescoImageLoader())
                .resource(newModel())

        imageloaderBanner
                .setOnBannerImageLoader(ImageLoader())
                .resource(newModel())

        picassoBanner
                .setOnBannerImageLoader(PicassoImageLoader())
                .resource(newModel())
    }

    override fun onDestroy() {
        super.onDestroy()
        frescoBanner.release()
        imageloaderBanner.release()
        picassoBanner.release()
    }
}
