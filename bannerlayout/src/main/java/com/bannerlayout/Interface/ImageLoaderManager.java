package com.bannerlayout.Interface;

import android.content.Context;
import android.widget.ImageView;

/**
 * by y on 2016/10/27
 */

public interface ImageLoaderManager<T> {
    void display(Context context, ImageView imageView, T model);
}
