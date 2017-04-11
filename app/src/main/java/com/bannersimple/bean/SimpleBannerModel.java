package com.bannersimple.bean;

import com.bannerlayout.Interface.BannerModelCallBack;

/**
 * by y on 2016/10/24
 */

public class SimpleBannerModel implements BannerModelCallBack {
    private Object image;
    private String title;

    public SimpleBannerModel() {
    }

    public SimpleBannerModel(Object image) {
        this.image = image;
    }

    public SimpleBannerModel(Object image, String title) {
        this.image = image;
        this.title = title;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Object getBannerUrl() {
        return getImage();
    }

    @Override
    public String getBannerTitle() {
        return getTitle();
    }
}
