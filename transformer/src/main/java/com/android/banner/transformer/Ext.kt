package com.android.banner.transformer

import com.android.banner.BannerLayout
import com.android.banner.transformer.internal.*
import com.android.banner.viewPager

fun BannerLayout.accordionTransformer() = also { bannerTransformer = AccordionTransformer() }

fun BannerLayout.backgroundTransformer() = also { bannerTransformer = BackgroundToForegroundTransformer() }

fun BannerLayout.cubeInTransformer() = also { bannerTransformer = CubeInTransformer() }

fun BannerLayout.cubeOutTransformer() = also { bannerTransformer = CubeOutTransformer() }

fun BannerLayout.defaultTransformer() = also { bannerTransformer = DefaultTransformer() }

fun BannerLayout.depthPageTransformer() = also { bannerTransformer = DepthPageTransformer() }

fun BannerLayout.flipHorizontalTransformer() = also { bannerTransformer = FlipHorizontalTransformer() }

fun BannerLayout.flipVerticalTransformer() = also { bannerTransformer = FlipVerticalTransformer() }

fun BannerLayout.foregroundTransformer() = also { bannerTransformer = ForegroundToBackgroundTransformer() }

fun BannerLayout.rotateDownTransformer() = also { bannerTransformer = RotateDownTransformer() }

fun BannerLayout.rotateUpTransformer() = also { bannerTransformer = RotateUpTransformer() }

fun BannerLayout.scaleInOutTransformer() = also { bannerTransformer = ScaleInOutTransformer() }

fun BannerLayout.stackTransformer() = also { bannerTransformer = StackTransformer() }

fun BannerLayout.tabletTransformer() = also { bannerTransformer = TabletTransformer() }

fun BannerLayout.zoomInTransformer() = also { bannerTransformer = ZoomInTransformer() }

fun BannerLayout.zoomOutPageTransformer() = also { bannerTransformer = ZoomOutPageTransformer() }

fun BannerLayout.zoomOutSlideTransformer() = also { bannerTransformer = ZoomOutSlideTransformer() }

fun BannerLayout.zoomOutTransformer() = also { bannerTransformer = ZoomOutTransformer() }

fun BannerLayout.drawerTransformer() = also { bannerTransformer = DrawerTransformer() }

fun BannerLayout.verticalTransformer() = also {
    viewPager().isVertical = true
    viewPager().setPageTransformer(true, VerticalTransformer())
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