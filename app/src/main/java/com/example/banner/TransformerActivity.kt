package com.example.banner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.doOnPageSelected
import com.android.banner.transformer.*
import com.example.GlideImageLoader
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
        bannerPosition.text = "select position:" + 0
        transformerBanner
                .delayTime(300)
                .setOnBannerImageLoader(GlideImageLoader())
                .setTransformer(getTransformer(ABaseTransformer.ANIMATION_ACCORDION))
                .doOnPageSelected { bannerPosition.text = "select position:$it" }
                .resource(newModel())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.transformer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.transformer_accordion -> transformerBanner.accordionTransformer()
            R.id.transformer_background -> transformerBanner.backgroundTransformer()
            R.id.transformer_cube_in -> transformerBanner.cubeInTransformer()
            R.id.transformer_cube_out -> transformerBanner.cubeOutTransformer()
            R.id.transformer_default -> transformerBanner.defaultTransformer()
            R.id.transformer_depth_page -> transformerBanner.depthPageTransformer()
            R.id.transformer_flip_horizontal -> transformerBanner.flipHorizontalTransformer()
            R.id.transformer_flip_vertical -> transformerBanner.flipVerticalTransformer()
            R.id.transformer_foreground -> transformerBanner.foregroundTransformer()
            R.id.transformer_rotate_down -> transformerBanner.rotateDownTransformer()
            R.id.transformer_rotate_up -> transformerBanner.rotateUpTransformer()
            R.id.transformer_stack -> transformerBanner.stackTransformer()
            R.id.transformer_scale_in_out -> transformerBanner.scaleInOutTransformer()
            R.id.transformer_tablet -> transformerBanner.tabletTransformer()
            R.id.transformer_zoom_in -> transformerBanner.zoomInTransformer()
            R.id.transformer_zoom_out_page -> transformerBanner.zoomOutPageTransformer()
            R.id.transformer_zoom_out_slide -> transformerBanner.zoomOutSlideTransformer()
            R.id.transformer_zoom_out -> transformerBanner.zoomOutTransformer()
            R.id.transformer_drawer -> transformerBanner.drawerTransformer()
            R.id.transformer_vertical -> transformerBanner.verticalTransformer()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        transformerBanner.release()
    }
}
