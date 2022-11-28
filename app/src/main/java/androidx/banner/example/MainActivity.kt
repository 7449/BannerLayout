package androidx.banner.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.banner.example.banner.TransformerActivity
import androidx.banner.example.databinding.ActivityMainBinding
import androidx.banner.example.list.ListActivity
import androidx.banner.page.BannerPageView
import androidx.banner.page.marginPageView
import androidx.banner.shadow.BannerShadowConfig
import androidx.banner.shadow.addShadowLayout
import androidx.banner.transformer.ABaseTransformer
import androidx.banner.transformer.getTransformer

class MainActivity : AppCompatActivity() {

    private val viewBind: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBind.banner
            .setOnBannerImageLoader(GlideImageLoader())
            .setTransformer(getTransformer(ABaseTransformer.ANIMATION_ACCORDION))
            .resource(newModel())
            .marginPageView(
                margin = 10, pageMark = " & ",
                pageSite = BannerPageView.PAGE_NUM_VIEW_TOP_CENTER
            )
//            .paddingPageView(
//                padding = 10, pageMark = " - ",
//                pageSite = BannerPageView.PAGE_NUM_VIEW_TOP_LEFT
//            )
//            .addPageView(pageTopMargin = 10, pageRightMargin = 10)
            .addShadowLayout(BannerShadowConfig(visibleTitle = true))

        viewBind.btnRecyclerView.setOnClickListener { v ->
            val intent = Intent(v.context, ListActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnTransformer.setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
            startActivity(intent)
        }

    }

}