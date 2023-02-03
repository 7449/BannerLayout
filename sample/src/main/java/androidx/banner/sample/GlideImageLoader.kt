package androidx.banner.sample

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.banner.listener.OnBannerImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : OnBannerImageLoader<SimpleBannerItem> {
    override fun instantiateItem(container: ViewGroup, item: SimpleBannerItem): View {
        return ImageView(container.context).apply {
            Glide.with(container.context)
                .applyDefaultRequestOptions(RequestOptions().centerCrop())
                .load(item.bannerUrl)
                .into(this)
        }
    }
}