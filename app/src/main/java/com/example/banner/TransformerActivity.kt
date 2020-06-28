package com.example.banner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.doOnPageSelected
import com.android.banner.imageLoaderManager
import com.android.banner.transformer.*
import com.example.R
import com.example.display.GlideAppSimpleImageManager
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
                .delayTime(300)
                .imageLoaderManager { GlideAppSimpleImageManager() }
                .setTransformer(getTransformer(ABaseTransformer.ANIMATION_ACCORDION))
                .doOnPageSelected { banner_position.text = "select position:$it" }
                .resource(newModel())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.transformer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.transformer_accordion -> transformer_banner.accordionTransformer()
            R.id.transformer_background -> transformer_banner.backgroundTransformer()
            R.id.transformer_cube_in -> transformer_banner.cubeInTransformer()
            R.id.transformer_cube_out -> transformer_banner.cubeOutTransformer()
            R.id.transformer_default -> transformer_banner.defaultTransformer()
            R.id.transformer_depth_page -> transformer_banner.depthPageTransformer()
            R.id.transformer_flip_horizontal -> transformer_banner.flipHorizontalTransformer()
            R.id.transformer_flip_vertical -> transformer_banner.flipVerticalTransformer()
            R.id.transformer_foreground -> transformer_banner.foregroundTransformer()
            R.id.transformer_rotate_down -> transformer_banner.rotateDownTransformer()
            R.id.transformer_rotate_up -> transformer_banner.rotateUpTransformer()
            R.id.transformer_stack -> transformer_banner.stackTransformer()
            R.id.transformer_scale_in_out -> transformer_banner.scaleInOutTransformer()
            R.id.transformer_tablet -> transformer_banner.tabletTransformer()
            R.id.transformer_zoom_in -> transformer_banner.zoomInTransformer()
            R.id.transformer_zoom_out_page -> transformer_banner.zoomOutPageTransformer()
            R.id.transformer_zoom_out_slide -> transformer_banner.zoomOutSlideTransformer()
            R.id.transformer_zoom_out -> transformer_banner.zoomOutTransformer()
            R.id.transformer_drawer -> transformer_banner.drawerTransformer()
            R.id.transformer_vertical -> transformer_banner.verticalTransformer()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        transformer_banner.release()
    }
}
