package com.bannerlayout.animation

import android.view.View

class ZoomOutTranformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val scale = 1f + Math.abs(position)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = page.width * 0.5f
        page.pivotY = page.height * 0.5f
        page.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
        if (position == -1f) {
            page.translationX = (page.width * -1).toFloat()
        }
    }

}
