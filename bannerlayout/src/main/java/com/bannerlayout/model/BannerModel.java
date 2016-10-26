package com.bannerlayout.model;

/**
 * by y on 2016/10/24
 */

public class BannerModel {
    private Object image;
    private String text;

    public BannerModel(Object image) {
        this.image = image;
    }

    public BannerModel(String text) {
        this.text = text;
    }

    public BannerModel(Object image, String text) {
        this.image = image;
        this.text = text;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
