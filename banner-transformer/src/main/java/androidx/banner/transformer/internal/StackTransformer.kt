package androidx.banner.transformer.internal

import android.view.View
import androidx.banner.transformer.ABaseTransformer

class StackTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.translationX = if (position < 0) 0f else -page.width * position
    }
}
