package com.bannersimple;

import android.content.Context;
import android.widget.ImageView;

import com.bannerlayout.util.ImageLoaderManage;
import com.squareup.picasso.Picasso;

/**
 * by y on 2016/10/27
 */

public class ImageLoader implements ImageLoaderManage {
    @Override
    public void display(Context context, ImageView imageView, Object url) {
        Picasso.with(context).load((Integer) url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
    }
}
