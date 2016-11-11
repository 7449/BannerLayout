package com.bannerlayout.animation;

/**
 * by y on 2016/11/11
 */

public class TransformerItem {

    private final String title;
    private final Class<? extends BannerTransformer> clazz;

    public TransformerItem(Class<? extends BannerTransformer> clazz) {
        this.clazz = clazz;
        title = clazz.getSimpleName();
    }

    @Override
    public String toString() {
        return title;
    }

}
