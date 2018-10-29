package com.bannersimple.refresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bannerlayout.OnBannerClickListener
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData
import com.bumptech.glide.Glide

/**
 * by y on 2017/3/8.
 */

class RefreshAdapter(private val listModels: MutableList<ListModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_ITEM = 1
    }

    private var bannerModels: MutableList<SimpleBannerModel>? = null
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
                if (bannerModels == null) return
                val viewHolder = holder as BannerViewHolder
                bannerLayout = viewHolder.bannerLayout
                bannerLayout
                        .apply {
                            delayTime = 1000
                            showTipsBackgroundColor = true
                            isVisibleDots = true
                            isVisibleTitle = true
                            onBannerClickListener = object : OnBannerClickListener<SimpleBannerModel> {
                                override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {
                                    Toast.makeText(view.context, model.title, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        .initTips()
                        .resource(bannerModels!!)
                        .switchBanner(true)

                viewHolder.start.setOnClickListener { viewHolder.bannerLayout.switchBanner(true) }
                viewHolder.stop.setOnClickListener { viewHolder.bannerLayout.switchBanner(false) }
                viewHolder.update.setOnClickListener {
                    val update = SimpleData.update()
                    bannerModels = update
                    bannerLayout.resource(update)
                }
            }
            TYPE_ITEM -> {
                if (listModels == null) {
                    return
                }
                val itemViewHolder = holder as ItemViewHolder
                Glide
                        .with(itemViewHolder.imageView.context)
                        .load(listModels[position - 1].titleImage)
                        .into(itemViewHolder.imageView)
                itemViewHolder.textView.text = listModels[position - 1].title
            }
        }


    }

    override fun getItemCount(): Int {
        return if (listModels == null) 0 else listModels.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_BANNER else TYPE_ITEM
    }

    fun addAll(data: List<ListModel>) {
        if (listModels != null) {
            listModels.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        if (listModels != null) {
            listModels.clear()
            notifyDataSetChanged()
        }
    }


    fun addBanner(bannerModels: MutableList<SimpleBannerModel>) {
        this.bannerModels = bannerModels
    }

    private inner class BannerViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerLayout: BannerLayout = itemView.findViewById(R.id.banner)
        val start: Button = itemView.findViewById(R.id.start)
        val stop: Button = itemView.findViewById(R.id.stop)
        val update: Button = itemView.findViewById(R.id.update)
    }

    private inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.list_image)
        val textView: AppCompatTextView = itemView.findViewById(R.id.list_tv)
    }


}
