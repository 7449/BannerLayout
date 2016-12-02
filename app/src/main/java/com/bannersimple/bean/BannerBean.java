package com.bannersimple.bean;

import com.bannerlayout.model.BannerModel;

/**
 * by y on 2016/12/2
 */

public class BannerBean extends BannerModel {

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThisTitle() {
        return thisTitle;
    }

    public void setThisTitle(String thisTitle) {
        this.thisTitle = thisTitle;
    }

    private String imageUrl;
    private String thisTitle;

    public BannerBean(String imageUrl, String thisTitle) {
        this.imageUrl = imageUrl;
        this.thisTitle = thisTitle;
    }



}
