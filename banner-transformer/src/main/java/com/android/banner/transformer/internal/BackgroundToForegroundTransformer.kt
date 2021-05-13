package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer
import kotlin.math.abs

class BackgroundToForegroundTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val height = page.height.toFloat()
        val width = page.width.toFloat()
        val scale = min(if (position < 0) 1f else abs(1f - position), 0.5f)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = width * 0.5f
        page.pivotY = height * 0.5f
        page.translationX = if (position < 0) width * position else -width * position * 0.25f
    }
}