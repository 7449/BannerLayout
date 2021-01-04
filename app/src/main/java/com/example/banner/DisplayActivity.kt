package com.example.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.*
import com.example.databinding.ActivityDisplayBinding

/**
 * by y on 2017/5/28.
 */
class DisplayActivity : AppCompatActivity() {

    private val viewBind by viewBinding(ActivityDisplayBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "ImageLoader Example"
        viewBind.frescoBanner
                .setOnBannerImageLoader(FrescoImageLoader())
                .resource(newModel())

        viewBind.imageloaderBanner
                .setOnBannerImageLoader(ImageLoader())
                .resource(newModel())

        viewBind.picassoBanner
                .setOnBannerImageLoader(PicassoImageLoader())
                .resource(newModel())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBind.frescoBanner.release()
        viewBind.imageloaderBanner.release()
        viewBind.picassoBanner.release()
    }
}
