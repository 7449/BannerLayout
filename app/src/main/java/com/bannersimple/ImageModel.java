package com.bannersimple;

import com.bannerlayout.model.BannerModel;

/**
 * by y on 2016/11/1.
 */

public class ImageModel extends BannerModel {

    public ImageModel(Object image, String title, String testText) {
        super(image, title);
        this.testText = testText;
    }

    public String getTestText() {
        return testText;
    }

    public void setTestText(String testText) {
        this.testText = testText;
    }

    private String testText;
}
