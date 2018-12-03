package com.bannerlayout

import android.annotation.SuppressLint
import android.graphics.Camera
import android.graphics.Matrix
import android.view.View
import androidx.viewpager.widget.ViewPager

const val ANIMATION_ACCORDION = 0
const val ANIMATION_BACKGROUND = 1
const val ANIMATION_CUBE_IN = 2
const val ANIMATION_CUBE_OUT = 3
const val ANIMATION_DEFAULT = 4
const val ANIMATION_DEPTH_PAGE = 5
const val ANIMATION_FLIPHORIZONTAL = 6
const val ANIMATION_FLIPVERTICAL = 7
const val ANIMATION_FOREGROUND = 8
const val ANIMATION_ROTATEDOWN = 9
const val ANIMATION_ROTATEUP = 10
const val ANIMATION_STACK = 11
const val ANIMATION_SCALEINOUT = 12
const val ANIMATION_TABLET = 13
const val ANIMATION_ZOOMIN = 14
const val ANIMATION_ZOOMOUTPAGE = 15
const val ANIMATION_ZOOMOUTSLIDE = 16
const val ANIMATION_ZOOMOUT = 17
const val ANIMATION_DRAWER = 18

abstract class BannerTransformer : ViewPager.PageTransformer

abstract class ABaseTransformer : BannerTransformer() {
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

    companion object {
        fun min(`val`: Float, min: Float): Float {
            return if (`val` < min) min else `val`
        }
    }

}

class AccordionTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position < 0) 0 else page.width).toFloat()
        page.scaleX = if (position < 0) 1f + position else 1f - position
    }
}

class BackgroundToForegroundTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val height = page.height.toFloat()
        val width = page.width.toFloat()
        val scale = ABaseTransformer.min(if (position < 0) 1f else Math.abs(1f - position), 0.5f)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = width * 0.5f
        page.pivotY = height * 0.5f
        page.translationX = if (position < 0) width * position else -width * position * 0.25f
    }
}

class CubeInTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position > 0) 0 else page.width).toFloat()
        page.pivotY = 0f
        page.rotationY = -90f * position
    }

    public override fun isPagingEnabled(): Boolean = true
}

class CubeOutTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = if (position < 0f) page.width.toFloat() else 0f
        page.pivotY = page.height * 0.5f
        page.rotationY = 90f * position
    }

    public override fun isPagingEnabled(): Boolean = true
}

class DefaultTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {}
    public override fun isPagingEnabled(): Boolean = true
}


class DepthPageTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        if (position <= 0f) {
            page.translationX = 0f
            page.scaleX = 1f
            page.scaleY = 1f
        } else if (position <= 1f) {
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            page.alpha = 1 - position
            page.pivotY = 0.5f * page.height
            page.translationX = page.width * -position
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
        }
    }

    override fun isPagingEnabled(): Boolean = true

    companion object {
        private const val MIN_SCALE = 0.75f
    }
}

class DrawerTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        if (position <= 0) {
            page.translationX = 0f
        } else if (position > 0 && position <= 1) {
            page.translationX = -page.width / 2 * position
        }
    }
}

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

class FlipVerticalTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val rotation = -180f * position
        page.alpha = if (rotation > 90f || rotation < -90f) 0f else 1f
        page.pivotX = page.width * 0.5f
        page.pivotY = page.height * 0.5f
        page.rotationX = rotation
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

class ForegroundToBackgroundTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val height = page.height.toFloat()
        val width = page.width.toFloat()
        val scale = ABaseTransformer.min(if (position > 0) 1f else Math.abs(1f + position), 0.5f)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = width * 0.5f
        page.pivotY = height * 0.5f
        page.translationX = if (position > 0) width * position else -width * position * 0.25f
    }
}

class RotateDownTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val height = page.height.toFloat()
        val rotation = ROT_MOD * position * -1.25f
        page.pivotX = width * 0.5f
        page.pivotY = height
        page.rotation = rotation
    }

    override fun isPagingEnabled(): Boolean = true

    companion object {
        private const val ROT_MOD = -15f
    }
}

class RotateUpTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val width = page.width.toFloat()
        val rotation = ROT_MOD * position
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

class ScaleInOutTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position < 0) 0 else page.width).toFloat()
        page.pivotY = page.height / 2f
        val scale = if (position < 0) 1f + position else 1f - position
        page.scaleX = scale
        page.scaleY = scale
    }
}

class StackTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        page.translationX = if (position < 0) 0f else -page.width * position
    }
}

class TabletTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val rotation = (if (position < 0) 30f else -30f) * Math.abs(position)
        page.translationX = getOffsetXForRotation(rotation, page.width, page.height)
        page.pivotX = page.width * 0.5f
        page.pivotY = 0f
        page.rotationY = rotation
    }

    companion object {
        private val OFFSET_MATRIX = Matrix()
        private val OFFSET_CAMERA = Camera()
        private val OFFSET_TEMP_FLOAT = FloatArray(2)
        private fun getOffsetXForRotation(degrees: Float, width: Int, height: Int): Float {
            OFFSET_MATRIX.reset()
            OFFSET_CAMERA.save()
            OFFSET_CAMERA.rotateY(Math.abs(degrees))
            OFFSET_CAMERA.getMatrix(OFFSET_MATRIX)
            OFFSET_CAMERA.restore()
            OFFSET_MATRIX.preTranslate(-width * 0.5f, -height * 0.5f)
            OFFSET_MATRIX.postTranslate(width * 0.5f, height * 0.5f)
            OFFSET_TEMP_FLOAT[0] = width.toFloat()
            OFFSET_TEMP_FLOAT[1] = height.toFloat()
            OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT)
            return (width - OFFSET_TEMP_FLOAT[0]) * if (degrees > 0.0f) 1.0f else -1.0f
        }
    }
}

class VerticalTransformer : BannerTransformer() {
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

class ZoomInTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        val scale = if (position < 0) position + 1f else Math.abs(1f - position)
        page.scaleX = scale
        page.scaleY = scale
        page.pivotX = page.width * 0.5f
        page.pivotY = page.height * 0.5f
        page.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
    }
}

class ZoomOutPageTransformer : BannerTransformer() {
    @SuppressLint("NewApi")
    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        when {
            position < -1 -> view.alpha = 0f
            position <= 1 -> {
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
            }
            else -> view.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}

class ZoomOutSlideTransformer : ABaseTransformer() {
    override fun onTransform(page: View, position: Float) {
        if (position >= -1 || position <= 1) {
            val height = page.height.toFloat()
            val width = page.width.toFloat()
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = height * (1 - scaleFactor) / 2
            val horzMargin = width * (1 - scaleFactor) / 2
            page.pivotY = 0.5f * height
            page.pivotX = 0.5f * width
            if (position < 0) {
                page.translationX = horzMargin - vertMargin / 2
            } else {
                page.translationX = -horzMargin + vertMargin / 2
            }
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        }
    }

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}

class ZoomOutTransformer : ABaseTransformer() {
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
