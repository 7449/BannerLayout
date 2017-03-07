package com.bannerlayout.util;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * by y on 2016/12/28
 */

public class BannerSelectorUtils {


    public static StateListDrawable getDrawableSelector(float enabledRadius,
                                                        int enabledColor,
                                                        float normalRadius,
                                                        int normalColor) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_enabled}, getShape(enabledRadius, enabledColor));
        drawable.addState(new int[]{-android.R.attr.state_enabled}, getShape(normalRadius, normalColor));
        return drawable;
    }

    public static GradientDrawable getShape(float radius, int color) {
        GradientDrawable gd = new GradientDrawable();
//        gd.setShape(GradientDrawable.OVAL); The larger the radius, the more round the radius
        gd.setCornerRadius(radius);
        gd.setColor(color);
        return gd;
    }
}
