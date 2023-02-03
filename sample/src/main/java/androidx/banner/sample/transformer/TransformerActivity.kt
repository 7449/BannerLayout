package androidx.banner.sample.transformer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.banner.listener.OnBannerPageChangeListener.Companion.doOnPageSelected
import androidx.banner.sample.GlideImageLoader
import androidx.banner.sample.R
import androidx.banner.sample.databinding.ActivityTransformerBinding
import androidx.banner.sample.newModel
import androidx.banner.sample.viewBinding
import androidx.banner.transformer.ABaseTransformer
import androidx.banner.transformer.accordionTransformer
import androidx.banner.transformer.backgroundTransformer
import androidx.banner.transformer.cubeInTransformer
import androidx.banner.transformer.cubeOutTransformer
import androidx.banner.transformer.defaultTransformer
import androidx.banner.transformer.depthPageTransformer
import androidx.banner.transformer.drawerTransformer
import androidx.banner.transformer.flipHorizontalTransformer
import androidx.banner.transformer.flipVerticalTransformer
import androidx.banner.transformer.foregroundTransformer
import androidx.banner.transformer.getTransformer
import androidx.banner.transformer.rotateDownTransformer
import androidx.banner.transformer.rotateUpTransformer
import androidx.banner.transformer.scaleInOutTransformer
import androidx.banner.transformer.stackTransformer
import androidx.banner.transformer.tabletTransformer
import androidx.banner.transformer.verticalTransformer
import androidx.banner.transformer.zoomInTransformer
import androidx.banner.transformer.zoomOutPageTransformer
import androidx.banner.transformer.zoomOutSlideTransformer
import androidx.banner.transformer.zoomOutTransformer

class TransformerActivity : AppCompatActivity() {

    private val viewBind by viewBinding(ActivityTransformerBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind.bannerPosition.text = "select position:" + 0
        viewBind.transformerBanner
            .delayTime(600)
            .setOnBannerImageLoader(GlideImageLoader())
            .setTransformer(getTransformer(ABaseTransformer.ANIMATION_ACCORDION))
            .doOnPageSelected { viewBind.bannerPosition.text = "select position:$it" }
            .resource(newModel())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.transformer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.transformer_accordion -> viewBind.transformerBanner.accordionTransformer()
            R.id.transformer_background -> viewBind.transformerBanner.backgroundTransformer()
            R.id.transformer_cube_in -> viewBind.transformerBanner.cubeInTransformer()
            R.id.transformer_cube_out -> viewBind.transformerBanner.cubeOutTransformer()
            R.id.transformer_default -> viewBind.transformerBanner.defaultTransformer()
            R.id.transformer_depth_page -> viewBind.transformerBanner.depthPageTransformer()
            R.id.transformer_flip_horizontal -> viewBind.transformerBanner.flipHorizontalTransformer()
            R.id.transformer_flip_vertical -> viewBind.transformerBanner.flipVerticalTransformer()
            R.id.transformer_foreground -> viewBind.transformerBanner.foregroundTransformer()
            R.id.transformer_rotate_down -> viewBind.transformerBanner.rotateDownTransformer()
            R.id.transformer_rotate_up -> viewBind.transformerBanner.rotateUpTransformer()
            R.id.transformer_stack -> viewBind.transformerBanner.stackTransformer()
            R.id.transformer_scale_in_out -> viewBind.transformerBanner.scaleInOutTransformer()
            R.id.transformer_tablet -> viewBind.transformerBanner.tabletTransformer()
            R.id.transformer_zoom_in -> viewBind.transformerBanner.zoomInTransformer()
            R.id.transformer_zoom_out_page -> viewBind.transformerBanner.zoomOutPageTransformer()
            R.id.transformer_zoom_out_slide -> viewBind.transformerBanner.zoomOutSlideTransformer()
            R.id.transformer_zoom_out -> viewBind.transformerBanner.zoomOutTransformer()
            R.id.transformer_drawer -> viewBind.transformerBanner.drawerTransformer()
            R.id.transformer_vertical -> viewBind.transformerBanner.verticalTransformer()
        }
        return super.onOptionsItemSelected(item)
    }

}
