package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer

class RotateDownTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val height = page.height.toFloat()
        val rotation = ROT_MOD * position * -1.25f
        page.pivotX = width * 0.5f
        page.pivotY = height
        page.rotation = rotation
    }

    override fun isPagingEnabled(): Boolean = true

    companion object {
        private const val ROT_MOD = -15f
    }
}