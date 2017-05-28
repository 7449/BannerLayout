package com.bannerlayout.util;

import com.bannerlayout.animation.AccordionTransformer;
import com.bannerlayout.animation.BackgroundToForegroundTransformer;
import com.bannerlayout.animation.BannerTransformer;
import com.bannerlayout.animation.CubeInTransformer;
import com.bannerlayout.animation.CubeOutTransformer;
import com.bannerlayout.animation.DefaultTransformer;
import com.bannerlayout.animation.DepthPageTransformer;
import com.bannerlayout.animation.FlipHorizontalTransformer;
import com.bannerlayout.animation.FlipVerticalTransformer;
import com.bannerlayout.animation.ForegroundToBackgroundTransformer;
import com.bannerlayout.animation.RotateDownTransformer;
import com.bannerlayout.animation.RotateUpTransformer;
import com.bannerlayout.animation.ScaleInOutTransformer;
import com.bannerlayout.animation.StackTransformer;
import com.bannerlayout.animation.TabletTransformer;
import com.bannerlayout.animation.ZoomInTransformer;
import com.bannerlayout.animation.ZoomOutPageTransformer;
import com.bannerlayout.animation.ZoomOutSlideTransformer;
import com.bannerlayout.animation.ZoomOutTranformer;
import com.bannerlayout.annotation.AnimationMode;
import com.bannerlayout.widget.BannerLayout;

/**
 * by y on 2016/11/11
 */

public class TransformerUtils {


    public static BannerTransformer getTransformer(@AnimationMode int type) {
        BannerTransformer bannerTransformer = null;
        switch (type) {
            case BannerLayout.ANIMATION_ACCORDION:
                bannerTransformer = new AccordionTransformer();
                break;
            case BannerLayout.ANIMATION_BACKGROUND:
                bannerTransformer = new BackgroundToForegroundTransformer();
                break;
            case BannerLayout.ANIMATION_CUBE_IN:
                bannerTransformer = new CubeInTransformer();
                break;
            case BannerLayout.ANIMATION_CUBE_OUT:
                bannerTransformer = new CubeOutTransformer();
                break;
            case BannerLayout.ANIMATION_DEFAULT:
                bannerTransformer = new DefaultTransformer();
                break;
            case BannerLayout.ANIMATION_DEPTH_PAGE:
                bannerTransformer = new DepthPageTransformer();
                break;
            case BannerLayout.ANIMATION_FLIPHORIZONTAL:
                bannerTransformer = new FlipHorizontalTransformer();
                break;
            case BannerLayout.ANIMATION_FLIPVERTICAL:
                bannerTransformer = new FlipVerticalTransformer();
                break;
            case BannerLayout.ANIMATION_FOREGROUND:
                bannerTransformer = new ForegroundToBackgroundTransformer();
                break;
            case BannerLayout.ANIMATION_ROTATEDOWN:
                bannerTransformer = new RotateDownTransformer();
                break;
            case BannerLayout.ANIMATION_ROTATEUP:
                bannerTransformer = new RotateUpTransformer();
                break;
            case BannerLayout.ANIMATION_SCALEINOUT:
                bannerTransformer = new ScaleInOutTransformer();
                break;
            case BannerLayout.ANIMATION_STACK:
                bannerTransformer = new StackTransformer();
                break;
            case BannerLayout.ANIMATION_TABLET:
                bannerTransformer = new TabletTransformer();
                break;
            case BannerLayout.ANIMATION_ZOOMIN:
                bannerTransformer = new ZoomInTransformer();
                break;
            case BannerLayout.ANIMATION_ZOOMOUTPAGE:
                bannerTransformer = new ZoomOutPageTransformer();
                break;
            case BannerLayout.ANIMATION_ZOOMOUTSLIDE:
                bannerTransformer = new ZoomOutSlideTransformer();
                break;
            case BannerLayout.ANIMATION_ZOOMOUT:
                bannerTransformer = new ZoomOutTranformer();
                break;
        }

        return bannerTransformer;
    }

}
