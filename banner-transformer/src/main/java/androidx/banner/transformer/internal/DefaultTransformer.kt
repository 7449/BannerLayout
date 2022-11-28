package androidx.banner.transformer.internal

import android.view.View
import androidx.banner.transformer.ABaseTransformer

class DefaultTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {}
    public override fun isPagingEnabled(): Boolean = true
}