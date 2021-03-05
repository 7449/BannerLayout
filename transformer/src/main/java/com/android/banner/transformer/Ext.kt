package com.android.banner.transformer

import com.android.banner.BannerLayout
import com.android.banner.BannerTransformer
import com.android.banner.transformer.internal.*

fun BannerLayout.accordionTransformer() = also { setTransformer(AccordionTransformer()) }

fun BannerLayout.backgroundTransformer() = also { setTransformer(BackgroundToForegroundTransformer()) }

fun BannerLayout.cubeInTransformer() = also { setTransformer(CubeInTransformer()) }

fun BannerLayout.cubeOutTransformer() = also { setTransformer(CubeOutTransformer()) }

fun BannerLayout.defaultTransformer() = also { setTransformer(DefaultTransformer()) }

fun BannerLayout.depthPageTransformer() = also { setTransformer(DepthPageTransformer()) }

fun BannerLayout.flipHorizontalTransformer() = also { setTransformer(FlipHorizontalTransformer()) }

fun BannerLayout.flipVerticalTransformer() = also { setTransformer(FlipVerticalTransformer()) }

fun BannerLayout.foregroundTransformer() = also { setTransformer(ForegroundToBackgroundTransformer()) }

fun BannerLayout.rotateDownTransformer() = also { setTransformer(RotateDownTransformer()) }

fun BannerLayout.rotateUpTransformer() = also { setTransformer(RotateUpTransformer()) }

fun BannerLayout.scaleInOutTransformer() = also { setTransformer(ScaleInOutTransformer()) }

fun BannerLayout.stackTransformer() = also { setTransformer(StackTransformer()) }

fun BannerLayout.tabletTransformer() = also { setTransformer(TabletTransformer()) }

fun BannerLayout.zoomInTransformer() = also { setTransformer(ZoomInTransformer()) }

fun BannerLayout.zoomOutPageTransformer() = also { setTransformer(ZoomOutPageTransformer()) }

fun BannerLayout.zoomOutSlideTransformer() = also { setTransformer(ZoomOutSlideTransformer()) }

fun BannerLayout.zoomOutTransformer() = also { setTransformer(ZoomOutTransformer()) }

fun BannerLayout.drawerTransformer() = also { setTransformer(DrawerTransformer()) }

fun BannerLayout.verticalTransformer() = also {
    isVertical(true)
    setTransformer(VerticalTransformer())
}

fun getTransformer(type: Int): BannerTransformer {
    when (type) {
        ABaseTransformer.ANIMATION_ACCORDION -> return AccordionTransformer()
        ABaseTransformer.ANIMATION_BACKGROUND -> return BackgroundToForegroundTransformer()
        ABaseTransformer.ANIMATION_CUBE_IN -> return CubeInTransformer()
        ABaseTransformer.ANIMATION_CUBE_OUT -> return CubeOutTransformer()
        ABaseTransformer.ANIMATION_DEFAULT -> return DefaultTransformer()
        ABaseTransformer.ANIMATION_DEPTH_PAGE -> return DepthPageTransformer()
        ABaseTransformer.ANIMATION_FLIP_HORIZONTAL -> return FlipHorizontalTransformer()
        ABaseTransformer.ANIMATION_FLIP_VERTICAL -> return FlipVerticalTransformer()
        ABaseTransformer.ANIMATION_FOREGROUND -> return ForegroundToBackgroundTransformer()
        ABaseTransformer.ANIMATION_ROTATE_DOWN -> return RotateDownTransformer()
        ABaseTransformer.ANIMATION_ROTATE_UP -> return RotateUpTransformer()
        ABaseTransformer.ANIMATION_SCALE_IN_OUT -> return ScaleInOutTransformer()
        ABaseTransformer.ANIMATION_STACK -> return StackTransformer()
        ABaseTransformer.ANIMATION_TABLET -> return TabletTransformer()
        ABaseTransformer.ANIMATION_ZOOM_IN -> return ZoomInTransformer()
        ABaseTransformer.ANIMATION_ZOOM_OUT_PAGE -> return ZoomOutPageTransformer()
        ABaseTransformer.ANIMATION_ZOOM_OUT_SLIDE -> return ZoomOutSlideTransformer()
        ABaseTransformer.ANIMATION_ZOOM_OUT -> return ZoomOutTransformer()
        ABaseTransformer.ANIMATION_DRAWER -> return DrawerTransformer()
        else -> return AccordionTransformer()
    }
}