package com.example.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.android.banner.BannerLayout
import com.android.banner.addOnItemClickListener
import com.android.banner.imageLoaderManager
import com.android.banner.shadow.BannerTip
import com.android.banner.shadow.addTipLayout
import com.bumptech.glide.Glide
import com.example.NetBannerInfo
import com.example.R
import com.example.display.GlideAppSimpleImageManager
import com.example.newModel

/**
 * by y on 2017/3/8.
 */

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_ITEM = 1
    }

    private var info: ArrayList<NetBannerInfo> = ArrayList()
    private var listModels: ArrayList<DataModel> = ArrayList()
    private lateinit var bannerLayout: BannerLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BANNER -> BannerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false))
            else -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_BANNER -> {
                val viewHolder = holder as BannerViewHolder
                bannerLayout = viewHolder.bannerLayout
                bannerLayout
                        .delayTime(1000)
                        .imageLoaderManager { GlideAppSimpleImageManager() }
                        .addOnItemClickListener<NetBannerInfo> { view, _, info ->
                            Toast.makeText(view.context, info.title, Toast.LENGTH_LONG).show()
                        }
                        .resource(info)
                if (info.isNotEmpty()) {
                    bannerLayout.addTipLayout(BannerTip(visibleTitle = true))
                }
                viewHolder.start.setOnClickListener { viewHolder.bannerLayout.startBanner() }
                viewHolder.stop.setOnClickListener { viewHolder.bannerLayout.stopBanner() }
                viewHolder.update.setOnClickListener {
                    val update = newModel()
                    info = update
                    bannerLayout.resource(update)
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

    fun addBanner(info: ArrayList<NetBannerInfo>) {
        this.info = info
    }

    private class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerLayout: BannerLayout = itemView.findViewById(R.id.banner)
        val start: Button = itemView.findViewById(R.id.start)
        val stop: Button = itemView.findViewById(R.id.stop)
        val update: Button = itemView.findViewById(R.id.update)
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.list_image)
        val textView: AppCompatTextView = itemView.findViewById(R.id.list_tv)
    }

}
