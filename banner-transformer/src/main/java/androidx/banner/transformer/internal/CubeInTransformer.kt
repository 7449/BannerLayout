package androidx.banner.transformer.internal

import android.view.View
import androidx.banner.transformer.ABaseTransformer

class CubeInTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position > 0) 0 else page.width).toFloat()
        page.pivotY = 0f
        page.rotationY = -90f * position
    }

    public override fun isPagingEnabled(): Boolean = true
}