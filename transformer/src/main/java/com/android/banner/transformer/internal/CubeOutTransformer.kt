package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer

class CubeOutTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = if (position < 0f) page.width.toFloat() else 0f
        page.pivotY = page.height * 0.5f
        page.rotationY = 90f * position
    }

    public override fun isPagingEnabled(): Boolean = true
}