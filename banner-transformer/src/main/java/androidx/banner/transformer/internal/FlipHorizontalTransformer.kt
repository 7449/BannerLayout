package androidx.banner.transformer.internal

import android.view.View
import androidx.banner.transformer.ABaseTransformer

class FlipHorizontalTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val rotation = 180f * position
        page.alpha = (if (rotation > 90f || rotation < -90f) 0 else 1).toFloat()
        page.pivotX = page.width * 0.5f
        page.pivotY = page.height * 0.5f
        page.rotationY = rotation
    }

    override fun onPostTransform(page: View, position: Float) {
        super.onPostTransform(page, position)
        if (position > -0.5f && position < 0.5f) {
            page.visibility = View.VISIBLE
        } else {
            page.visibility = View.INVISIBLE
        }
    }
}