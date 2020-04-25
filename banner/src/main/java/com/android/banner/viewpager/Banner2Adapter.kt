package com.android.banner.viewpager

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.banner.BannerInfo
import com.android.banner.ImageLoaderManager
import com.android.banner.OnBannerClickListener

class Banner2Adapter(
        private val imageList: List<BannerInfo>,
        private val loaderManager: ImageLoaderManager<out BannerInfo>?,
        private val listener: OnBannerClickListener<out BannerInfo>?,
        private val guide: Boolean
) : RecyclerView.Adapter<Banner2Adapter.Banner2Holder>() {

    init {
        require(loaderManager != null) { "ImageLoaderManager == null" }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Banner2Holder {
        val holder = Banner2Holder.create(parent)
        listener?.let {
            holder.getImageView().setOnClickListener { v ->
                @Suppress("UNCHECKED_CAST")
                (it as OnBannerClickListener<BannerInfo>)
                        .onBannerClick(
                                v,
                                getPosition(holder.adapterPosition),
                                imageList[getPosition(holder.adapterPosition)]
                        )
            }
        }
        return holder
    }

    override fun getItemCount(): Int = if (guide) imageList.size else Integer.MAX_VALUE

    private fun getPosition(position: Int): Int = position % imageList.size

    override fun onBindViewHolder(holder: Banner2Holder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        val imageLoader = loaderManager as ImageLoaderManager<BannerInfo>
        imageLoader.display(holder.getImageView(), imageList[getPosition(position)], getPosition(position))
    }

    class Banner2Holder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun create(parent: ViewGroup): Banner2Holder {
                val imageView = ImageView(parent.context)
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                imageView.id = ViewCompat.generateViewId()
                return Banner2Holder(imageView)
            }
        }

        fun getImageView(): ImageView {
            return itemView as ImageView
        }
    }
}