package com.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.banner.page.addPageView
import com.android.banner.shadow.BannerTip
import com.android.banner.shadow.addTipLayout
import com.android.banner.transformer.ABaseTransformer
import com.android.banner.transformer.getTransformer
import com.example.banner.TransformerActivity
import com.example.databinding.ActivityMainBinding
import com.example.list.ListActivity

class MainActivity : AppCompatActivity() {

    private val viewBind: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBind.banner
                .setOnBannerImageLoader(GlideImageLoader())
                .setTransformer(getTransformer(ABaseTransformer.ANIMATION_ACCORDION))
                .resource(newModel())
                .addPageView(pageTopMargin = 10, pageRightMargin = 10)
                .addTipLayout(BannerTip(visibleTitle = true))

        viewBind.btnRecyclerView.setOnClickListener { v ->
            val intent = Intent(v.context, ListActivity::class.java)
            startActivity(intent)
        }

        viewBind.btnTransformer.setOnClickListener { v ->
            val intent = Intent(v.context, TransformerActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewBind.banner.release()
    }

}