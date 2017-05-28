package com.bannersimple.imagemanager;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannersimple.bean.SimpleBannerModel;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * by y on 2017/5/28.
 */

public class ImageLoaderSimpleManager implements ImageLoaderManager<SimpleBannerModel> {

    @NonNull
    @Override
    public ImageView display(@NonNull ViewGroup container, SimpleBannerModel model) {
        ImageView imageView = new ImageView(container.getContext());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(String.valueOf(model.getBannerUrl()), imageView);
        return imageView;
    }
}
