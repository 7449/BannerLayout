package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer

class AccordionTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position < 0) 0 else page.width).toFloat()
        page.scaleX = if (position < 0) 1f + position else 1f - position
    }
}