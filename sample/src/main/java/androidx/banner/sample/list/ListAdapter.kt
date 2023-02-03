package androidx.banner.sample.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.banner.BannerLayout
import androidx.banner.listener.OnBannerClickListener.Companion.itemClickListener
import androidx.banner.listener.OnBannerResourceChangedListener.Companion.resourceChangedListener
import androidx.banner.sample.GlideImageLoader
import androidx.banner.sample.R
import androidx.banner.sample.SimpleBannerItem
import androidx.banner.sample.newModel
import androidx.banner.shadow.BannerShadowConfig
import androidx.banner.shadow.addShadowLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_ITEM = 1
    }

    private var info: ArrayList<SimpleBannerItem> = ArrayList()
    private val listModels: ArrayList<DataModel> = ArrayList()
    private val bannerImageLoader = GlideImageLoader()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BANNER -> BannerViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
            )

            else -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_BANNER -> {
                val viewHolder = holder as BannerViewHolder
                val bannerLayout = viewHolder.bannerLayout
                bannerLayout
                    .clearAllListener()
                    .delayTime(1000)
                    .setOnBannerImageLoader(bannerImageLoader)
                    .itemClickListener { view, _, info ->
                        Toast.makeText(view.context, info.bannerTitle, Toast.LENGTH_SHORT).show()
                    }
                    .resourceChangedListener {
                        bannerLayout.addShadowLayout(BannerShadowConfig(visibleTitle = true))
                    }
                    .resource(info)
                viewHolder.start.setOnClickListener { viewHolder.bannerLayout.start() }
                viewHolder.stop.setOnClickListener { viewHolder.bannerLayout.stop() }
                viewHolder.update.setOnClickListener {
                    info = newModel()
                    bannerLayout.resource(info)
                }
            }

            TYPE_ITEM -> {
                val itemViewHolder = holder as ItemViewHolder
                Glide
                    .with(itemViewHolder.imageView.context)
                    .load(listModels[position - 1].image)
                    .into(itemViewHolder.imageView)
                itemViewHolder.textView.text = listModels[position - 1].title
            }
        }
    }

    override fun getItemCount(): Int = listModels.size + 1

    override fun getItemViewType(position: Int): Int = if (position == 0) TYPE_BANNER else TYPE_ITEM

    fun addAll(data: List<DataModel>) {
        listModels.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        listModels.clear()
        notifyDataSetChanged()
    }

    fun addBanner(info: ArrayList<SimpleBannerItem>) {
        this.info = info
    }

    private class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerLayout: BannerLayout = itemView.findViewById(R.id.banner)
        val start: Button = itemView.findViewById(R.id.start)
        val stop: Button = itemView.findViewById(R.id.stop)
        val update: Button = itemView.findViewById(R.id.update)
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.listImage)
        val textView: AppCompatTextView = itemView.findViewById(R.id.listTv)
    }

}
