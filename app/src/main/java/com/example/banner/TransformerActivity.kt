package com.example.banner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bannerlayout.BannerTransformer
import com.bannerlayout.doOnPageSelected
import com.bannerlayout.removeCallbacksAndMessages
import com.example.R
import com.example.newModel
import kotlinx.android.synthetic.main.activity_transformer.*

/**
 * by y on 2017/5/28.
 */

class TransformerActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Transformer Example"
        setContentView(R.layout.activity_transformer)
        banner_position.text = "select position:" + 0
        transformer_banner
                .apply {
                    delayTime = 300
                    bannerTransformerType = BannerTransformer.ANIMATION_ACCORDION
                }
                .doOnPageSelected { banner_position.text = "select position:$it" }
                .resource(newModel())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.transformer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.transformer_accordion -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ACCORDION
            R.id.transformer_background -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_BACKGROUND
            R.id.transformer_cube_in -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_CUBE_IN
            R.id.transformer_cube_out -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_CUBE_OUT
            R.id.transformer_default -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_DEFAULT
            R.id.transformer_depth_page -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_DEPTH_PAGE
            R.id.transformer_flip_horizontal -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_FLIP_HORIZONTAL
            R.id.transformer_flip_vertical -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_FLIP_VERTICAL
            R.id.transformer_foreground -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_FOREGROUND
            R.id.transformer_rotate_down -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ROTATE_DOWN
            R.id.transformer_rotate_up -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ROTATE_UP
            R.id.transformer_stack -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_STACK
            R.id.transformer_scale_in_out -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_SCALE_IN_OUT
            R.id.transformer_tablet -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_TABLET
            R.id.transformer_zoom_in -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ZOOM_IN
            R.id.transformer_zoom_out_page -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ZOOM_OUT_PAGE
            R.id.transformer_zoom_out_slide -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ZOOM_OUT_SLIDE
            R.id.transformer_zoom_out -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_ZOOM_OUT
            R.id.transformer_drawer -> transformer_banner.bannerTransformerType = BannerTransformer.ANIMATION_DRAWER
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        transformer_banner.removeCallbacksAndMessages()
    }
}
