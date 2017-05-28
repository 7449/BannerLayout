package com.bannersimple.imagemanager;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleBannerModel;
import com.squareup.picasso.Picasso;

/**
 * by y on 2016/12/2
 */

public class PicassoSimpleImageManager implements ImageLoaderManager<SimpleBannerModel> {


    @NonNull
    @Override
    public ImageView display(@NonNull ViewGroup container, SimpleBannerModel model) {
        ImageView imageView = new ImageView(container.getContext());
        Picasso.with(imageView.getContext())
                .load(model.getBannerUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        return imageView;
    }
}
