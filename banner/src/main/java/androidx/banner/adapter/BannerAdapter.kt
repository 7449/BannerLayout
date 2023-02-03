package androidx.banner.adapter

import android.view.View
import android.view.ViewGroup
import androidx.banner.listener.BannerItem
import androidx.banner.listener.OnBannerClickListener
import androidx.banner.listener.OnBannerImageLoader
import androidx.viewpager.widget.PagerAdapter

internal class BannerAdapter(
    private val items: List<BannerItem>,
    private val imageLoader: OnBannerImageLoader<BannerItem>,
    private val clickListeners: List<OnBannerClickListener<BannerItem>>,
) : PagerAdapter() {

    private val Int.pos get() = this % items.size

    override fun getCount() = Integer.MAX_VALUE
    override fun isViewFromObject(view: View, any: Any) = view == any

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as? View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = imageLoader.instantiateItem(container, items[position.pos])
        if (clickListeners.isNotEmpty()) {
            view.setOnClickListener {
                clickListeners.forEach { listener ->
                    listener.onBannerClick(it, position.pos, items[position.pos])
                }
            }
        }
        container.addView(view)
        return view
    }

}
