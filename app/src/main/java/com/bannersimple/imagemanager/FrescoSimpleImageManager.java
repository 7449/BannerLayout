package com.bannersimple.imagemanager;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.listener.ImageLoaderManager;
import com.bannersimple.bean.SimpleBannerModel;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * by y on 2017/5/28.
 */

public class FrescoSimpleImageManager implements ImageLoaderManager<SimpleBannerModel> {

    @NonNull
    @Override
    public ImageView display(@NonNull ViewGroup container, SimpleBannerModel model) {
        SimpleDraweeView draweeView = new SimpleDraweeView(container.getContext());
        draweeView.setImageURI(String.valueOf(model.getBannerUrl()));
        return draweeView;
    }
}
