package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer

class DrawerTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        if (position <= 0) {
            page.translationX = 0f
        } else if (position > 0 && position <= 1) {
            page.translationX = -page.width / 2 * position
        }
    }
}