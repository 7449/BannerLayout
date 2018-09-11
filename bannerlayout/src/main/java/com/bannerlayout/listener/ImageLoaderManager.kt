package com.bannerlayout.listener

import android.view.ViewGroup
import android.widget.ImageView

/**
 * by y on 2016/10/27
 */

interface ImageLoaderManager<in T> {
    fun display(container: ViewGroup, model: T): ImageView
}
