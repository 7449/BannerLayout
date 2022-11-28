package androidx.banner.transformer.internal

import android.view.View
import androidx.banner.transformer.ABaseTransformer

class RotateUpTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val rotation =
            androidx.banner.transformer.internal.RotateUpTransformer.Companion.ROT_MOD * position
        page.pivotX = width * 0.5f
        page.pivotY = 0f
        page.translationX = 0f
        page.rotation = rotation
    }

    override fun isPagingEnabled(): Boolean = true

    companion object {
        private const val ROT_MOD = -15f
    }
}