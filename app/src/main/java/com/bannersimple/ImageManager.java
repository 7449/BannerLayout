package com.bannersimple;

import android.content.Context;
import android.widget.ImageView;

import com.bannerlayout.Interface.ImageLoaderManage;
import com.bannersimple.bean.BannerBean;
import com.squareup.picasso.Picasso;

/**
 * by y on 2016/12/2
 */

public class ImageManager implements ImageLoaderManage {
    @Override
    public void display(Context context, ImageView imageView, Object url, Object model) {
        BannerBean bannerBean = (BannerBean) model;
        Picasso.with(context)
                .load(bannerBean.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
