package com.android.banner.transformer.internal

import android.view.View
import com.android.banner.transformer.ABaseTransformer

class DefaultTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {}
    public override fun isPagingEnabled(): Boolean = true
}