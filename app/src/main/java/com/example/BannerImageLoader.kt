package com.example

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.jzvd.Jzvd
import com.android.banner.OnBannerImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.drawee.view.SimpleDraweeView
import com.nostra13.universalimageloader.core.ImageLoader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.banner_video_item.view.*

class VideoImageLoader : OnBannerImageLoader<SimpleBannerInfo> {

    override fun destroyItem(container: ViewGroup, correctPosition: Int, bannerPosition: Int, any: Any, info: SimpleBannerInfo) {
        super.destroyItem(container, correctPosition, bannerPosition, any, info)
        Jzvd.releaseAllVideos()
    }

    override fun setPrimaryItem(container: ViewGroup, correctPosition: Int, bannerPosition: Int, any: Any, info: SimpleBannerInfo) {
        if (!info.isVideo) {
            return
        }
        any as View
        if (any.video.state == Jzvd.STATE_NORMAL) {
            any.video.startButton.performClick()
        }
    }

    override fun instantiateItem(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return View.inflate(container.context, R.layout.banner_video_item, null).apply {
            if (info.isVideo) {
                video.setUp(info.bannerUrl, info.title)
                Glide.with(container.context)
                        .applyDefaultRequestOptions(RequestOptions().centerCrop())
                        .load(info.bannerUrl)
                        .into(video.posterImageView)
            } else {
                Glide.with(container.context)
                        .applyDefaultRequestOptions(RequestOptions().centerCrop())
                        .load(info.bannerUrl)
                        .into(iv)
            }
            iv.visibility = if (info.isVideo) View.GONE else View.VISIBLE
            video.visibility = if (info.isVideo) View.VISIBLE else View.GONE
        }
    }
}

class PicassoImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun instantiateItem(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return ImageView(container.context).apply {
            Picasso.get()
                    .load(info.bannerUrl)
                    .into(this)
        }
    }
}

class ImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun instantiateItem(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return View.inflate(container.context, R.layout.banner_item, null).apply {
            ImageLoader.getInstance().displayImage(info.bannerUrl, findViewById<ImageView>(R.id.iv))
        }
    }
}

class GlideImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun instantiateItem(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return ImageView(container.context).apply {
            Glide.with(container.context)
                    .applyDefaultRequestOptions(RequestOptions().centerCrop())
                    .load(info.bannerUrl)
                    .into(this)
        }
    }
}

class FrescoImageLoader : OnBannerImageLoader<SimpleBannerInfo> {
    override fun instantiateItem(container: ViewGroup, info: SimpleBannerInfo, position: Int): View {
        return SimpleDraweeView(container.context).apply {
            setImageURI(info.bannerUrl)
        }
    }
}