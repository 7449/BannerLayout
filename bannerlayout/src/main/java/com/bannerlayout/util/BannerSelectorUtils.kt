package com.bannerlayout.util

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable

/**
 * by y on 2016/12/28
 */

object BannerSelectorUtils {


    fun getDrawableSelector(enabledRadius: Float,
                            enabledColor: Int,
                            normalRadius: Float,
                            normalColor: Int): StateListDrawable {
        val drawable = StateListDrawable()
        drawable.addState(intArrayOf(android.R.attr.state_enabled), getShape(enabledRadius, enabledColor))
        drawable.addState(intArrayOf(-android.R.attr.state_enabled), getShape(normalRadius, normalColor))
        return drawable
    }

    fun getShape(radius: Float, color: Int): GradientDrawable {
        val gd = GradientDrawable()
        gd.cornerRadius = radius
        gd.setColor(color)
        return gd
    }
}
