package com.bannerlayout.annotation;

import android.support.annotation.IntDef;

import com.bannerlayout.widget.BannerLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/1/19.
 */

@IntDef({BannerLayout.ALIGN_PARENT_BOTTOM,
        BannerLayout.ALIGN_PARENT_TOP,
        BannerLayout.CENTER_IN_PARENT})
@Retention(RetentionPolicy.SOURCE)
public @interface TipsSiteMode {
}
