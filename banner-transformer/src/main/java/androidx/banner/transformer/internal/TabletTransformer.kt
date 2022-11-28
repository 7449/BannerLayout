package androidx.banner.transformer.internal

import android.graphics.Camera
import android.graphics.Matrix
import android.view.View
import androidx.banner.transformer.ABaseTransformer
import kotlin.math.abs

class TabletTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val rotation = (if (position < 0) 30f else -30f) * abs(position)
        page.translationX =
            androidx.banner.transformer.internal.TabletTransformer.Companion.getOffsetXForRotation(
                rotation,
                page.width,
                page.height
            )
        page.pivotX = page.width * 0.5f
        page.pivotY = 0f
        page.rotationY = rotation
    }

    companion object {
        private val OFFSET_MATRIX = Matrix()
        private val OFFSET_CAMERA = Camera()
        private val OFFSET_TEMP_FLOAT = FloatArray(2)
        private fun getOffsetXForRotation(degrees: Float, width: Int, height: Int): Float {
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_MATRIX.reset()
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_CAMERA.save()
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_CAMERA.rotateY(
                abs(degrees)
            )
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_CAMERA.getMatrix(
                androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_MATRIX
            )
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_CAMERA.restore()
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_MATRIX.preTranslate(
                -width * 0.5f,
                -height * 0.5f
            )
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_MATRIX.postTranslate(
                width * 0.5f,
                height * 0.5f
            )
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_TEMP_FLOAT[0] =
                width.toFloat()
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_TEMP_FLOAT[1] =
                height.toFloat()
            androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_MATRIX.mapPoints(
                androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_TEMP_FLOAT
            )
            return (width - androidx.banner.transformer.internal.TabletTransformer.Companion.OFFSET_TEMP_FLOAT[0]) * if (degrees > 0.0f) 1.0f else -1.0f
        }
    }
}