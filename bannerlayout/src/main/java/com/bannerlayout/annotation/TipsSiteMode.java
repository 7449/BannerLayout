package com.bannerlayout.annotation;

import android.support.annotation.IntDef;

import com.bannerlayout.widget.BannerLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/1/19.
 */

@IntDef({BannerLayout.BOTTOM,
        BannerLayout.TOP,
        BannerLayout.CENTER})
@Retention(RetentionPolicy.SOURCE)
public @interface TipsSiteMode {
}
