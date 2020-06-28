package com.example.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.imageLoaderManager
import com.android.banner.setImageLoaderManager
import com.example.NetBannerInfo
import com.example.R
import com.example.display.ImageLoaderSimpleManager
import com.example.display.PicassoSimpleImageManager
import com.example.newModel
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.activity_display.*

/**
 * by y on 2017/5/28.
 */
class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        title = "ImageManager Example"
        fresco_banner
                .setImageLoaderManager<NetBannerInfo> { container, info, _ ->
                    val draweeView = SimpleDraweeView(container.context)
                    draweeView.setImageURI(info.bannerUrl)
                    draweeView
                }
                .resource(newModel())

        imageloader_banner
                .imageLoaderManager { ImageLoaderSimpleManager() }
                .resource(newModel(), isPlay = false)

        picasso_banner
                .imageLoaderManager { PicassoSimpleImageManager() }
                .resource(newModel())
    }

    override fun onDestroy() {
        super.onDestroy()
        fresco_banner.release()
        imageloader_banner.release()
        picasso_banner.release()
    }
}
