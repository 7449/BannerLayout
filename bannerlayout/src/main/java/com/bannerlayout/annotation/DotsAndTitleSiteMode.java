package com.bannerlayout.annotation;

import android.support.annotation.IntDef;

import com.bannerlayout.widget.BannerLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/1/19.
 */

@IntDef({BannerLayout.CENTER_IN_PARENT,
        BannerLayout.ALIGN_PARENT_LEFT,
        BannerLayout.ALIGN_PARENT_RIGHT})
@Retention(RetentionPolicy.SOURCE)
public @interface DotsAndTitleSiteMode {
}

