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
import com.bannerlayout.animation.StackTransformer;
import com.bannerlayout.animation.TabletTransformer;
import com.bannerlayout.animation.ZoomInTransformer;
import com.bannerlayout.animation.ZoomOutPageTransformer;
import com.bannerlayout.animation.ZoomOutSlideTransformer;
import com.bannerlayout.animation.ZoomOutTranformer;
import com.bannerlayout.bannerenum.BANNER_ANIMATION;

/**
 * by y on 2016/11/11
 */

public class TransformerUtils {


    public static BannerTransformer getTransformer(BANNER_ANIMATION bannerAnimation) {
        BannerTransformer bannerTransformer = null;
        switch (bannerAnimation) {
            case ACCORDION:
                bannerTransformer = new AccordionTransformer();
                break;
            case BACKGROUND:
                bannerTransformer = new BackgroundToForegroundTransformer();
                break;
            case CUBE_IN:
                bannerTransformer = new CubeInTransformer();
                break;
            case CUBE_OUT:
                bannerTransformer = new CubeOutTransformer();
                break;
            case DEFAULT:
                bannerTransformer = new DefaultTransformer();
                break;
            case DEPTH_PAGE:
                bannerTransformer = new DepthPageTransformer();
                break;
            case FLIPHORIZONTAL:
                bannerTransformer = new FlipHorizontalTransformer();
                break;
            case FLIPVERTICAL:
                bannerTransformer = new FlipVerticalTransformer();
                break;
            case FOREGROUND:
                bannerTransformer = new ForegroundToBackgroundTransformer();
                break;
            case ROTATEDOWN:
                bannerTransformer = new RotateDownTransformer();
                break;
            case ROTATEUP:
                bannerTransformer = new RotateUpTransformer();
                break;
            case STACK:
                bannerTransformer = new StackTransformer();
                break;
            case TABLET:
                bannerTransformer = new TabletTransformer();
                break;
            case ZOOMIN:
                bannerTransformer = new ZoomInTransformer();
                break;
            case ZOOMOUTPAGE:
                bannerTransformer = new ZoomOutPageTransformer();
                break;
            case ZOOMOUTSLIDE:
                bannerTransformer = new ZoomOutSlideTransformer();
                break;
            case ZOOMOUT:
                bannerTransformer = new ZoomOutTranformer();
                break;
        }

        return bannerTransformer;
    }

}
