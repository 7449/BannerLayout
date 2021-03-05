package com.android.banner.transformer

import android.view.View
import com.android.banner.BannerTransformer

abstract class ABaseTransformer : BannerTransformer() {

    companion object {
        fun min(`val`: Float, min: Float) = if (`val` < min) min else `val`

        const val ANIMATION_ACCORDION = 0
        const val ANIMATION_BACKGROUND = 1
        const val ANIMATION_CUBE_IN = 2
        const val ANIMATION_CUBE_OUT = 3
        const val ANIMATION_DEFAULT = 4
        const val ANIMATION_DEPTH_PAGE = 5
        const val ANIMATION_FLIP_HORIZONTAL = 6
        const val ANIMATION_FLIP_VERTICAL = 7
        const val ANIMATION_FOREGROUND = 8
        const val ANIMATION_ROTATE_DOWN = 9
        const val ANIMATION_ROTATE_UP = 10
        const val ANIMATION_STACK = 11
        const val ANIMATION_SCALE_IN_OUT = 12
        const val ANIMATION_TABLET = 13
        const val ANIMATION_ZOOM_IN = 14
        const val ANIMATION_ZOOM_OUT_PAGE = 15
        const val ANIMATION_ZOOM_OUT_SLIDE = 16
        const val ANIMATION_ZOOM_OUT = 17
        const val ANIMATION_DRAWER = 18
    }

    protected open fun isPagingEnabled(): Boolean = false

    protected abstract fun onTransform(page: View, position: Float)

    override fun transformPage(page: View, position: Float) {
        val clampedPosition = clampPosition(position)
        onPreTransform(page, clampedPosition)
        onTransform(page, clampedPosition)
        onPostTransform(page, clampedPosition)
    }

    private fun clampPosition(position: Float): Float {
        if (position < -1f) {
            return -1f
        } else if (position > 1f) {
            return 1f
        }
        return position
    }

    protected open fun hideOffscreenPages(): Boolean = true

    protected open fun onPreTransform(page: View, position: Float) {
        val width = page.width.toFloat()

        page.rotationX = 0f
        page.rotationY = 0f
        page.rotation = 0f
        page.scaleX = 1f
        page.scaleY = 1f
        page.pivotX = 0f
        page.pivotY = 0f
        page.translationY = 0f
        page.translationX = if (isPagingEnabled()) 0f else -width * position

        if (hideOffscreenPages()) {
            page.alpha = if (position <= -1f || position >= 1f) 0f else 1f
        } else {
            page.alpha = 1f
        }
    }

    protected open fun onPostTransform(page: View, position: Float) {}

}