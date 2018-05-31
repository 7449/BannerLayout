package com.bannersimple.imagemanager;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.listener.ImageLoaderManager;
import com.bannersimple.R;
import com.bannersimple.bean.SimpleBannerModel;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GlideAppSimpleImageManager implements ImageLoaderManager<SimpleBannerModel> {

    private final RequestOptions requestOptions;

    public GlideAppSimpleImageManager() {
        requestOptions = new RequestOptions().centerCrop();
    }

    @NonNull
    @Override
    public ImageView display(@NonNull ViewGroup container, SimpleBannerModel model) {
        ImageView imageView = new ImageView(container.getContext());
        GlideApp.with(imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(model.getBannerUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        return imageView;
    }


}
