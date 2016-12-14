package com.bannerlayout.model;

/**
 * by y on 2016/10/24
 */

public class BannerModel {
    private Object image;
    private String title;

    public BannerModel() {
    }

    public BannerModel(Object image) {
        this.image = image;
    }

    public BannerModel(Object image, String title) {
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

}
