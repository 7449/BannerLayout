package com.bannersimple;

import com.bannerlayout.bannerenum.BannerAnimationType;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/12/15
 */

public class AnimationList {


    public static List<BannerAnimationType> getAnimationList() {
        List<BannerAnimationType> list = new ArrayList<>();
        list.add(BannerAnimationType.ACCORDION);
        list.add(BannerAnimationType.BACKGROUND);
        list.add(BannerAnimationType.CUBE_IN);
        list.add(BannerAnimationType.CUBE_OUT);
        list.add(BannerAnimationType.DEFAULT);
        list.add(BannerAnimationType.DEPTH_PAGE);
        list.add(BannerAnimationType.FLIPHORIZONTAL);
        list.add(BannerAnimationType.FLIPVERTICAL);
        list.add(BannerAnimationType.FOREGROUND);
        list.add(BannerAnimationType.ROTATEDOWN);
        list.add(BannerAnimationType.ROTATEUP);
        list.add(BannerAnimationType.SCALEINOUT);
        list.add(BannerAnimationType.STACK);
        list.add(BannerAnimationType.TABLET);
        list.add(BannerAnimationType.ZOOMIN);
        list.add(BannerAnimationType.ZOOMOUTPAGE);
        list.add(BannerAnimationType.ZOOMOUTSLIDE);
        list.add(BannerAnimationType.ZOOMOUT);
        return list;
    }

}
