package com.bannersimple;

import android.widget.ImageView;

import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannersimple.bean.SimpleBannerModel;
import com.squareup.picasso.Picasso;

/**
 * by y on 2016/12/2
 */

public class ImageManager implements ImageLoaderManager<SimpleBannerModel> {


    @Override
    public void display(ImageView imageView, SimpleBannerModel model) {
        Picasso.with(imageView.getContext())
                .load((String) model.getBannerUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
