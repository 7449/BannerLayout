package androidx.banner.transformer.internal

import android.view.View
import androidx.viewpager.widget.ViewPager

class VerticalTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        var alpha = 0f
        if (position in 0.0..1.0) {
            alpha = 1 - position
        } else if (-1 < position && position < 0) {
            alpha = position + 1
        }
        view.alpha = alpha
        val transX = view.width * -position
        view.translationX = transX
        val transY = position * view.height
        view.translationY = transY
    }
}