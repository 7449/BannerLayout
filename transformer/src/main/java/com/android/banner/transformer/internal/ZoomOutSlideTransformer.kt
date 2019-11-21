package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer
import kotlin.math.abs

class ZoomOutSlideTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        if (position >= -1 || position <= 1) {
            val height = page.height.toFloat()
            val width = page.width.toFloat()
            val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
            val vertMargin = height * (1 - scaleFactor) / 2
            val horzMargin = width * (1 - scaleFactor) / 2
            page.pivotY = 0.5f * height
            page.pivotX = 0.5f * width
            if (position < 0) {
                page.translationX = horzMargin - vertMargin / 2
            } else {
                page.translationX = -horzMargin + vertMargin / 2
            }
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        }
    }

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}