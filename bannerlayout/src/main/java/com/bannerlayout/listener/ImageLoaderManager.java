package com.bannerlayout.listener;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * by y on 2016/10/27
 */

public interface ImageLoaderManager<T> {
    @NonNull
    ImageView display(@NonNull ViewGroup container, T model);
}
