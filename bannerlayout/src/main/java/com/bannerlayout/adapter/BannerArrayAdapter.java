package com.bannerlayout.adapter;

import android.widget.ImageView;

/**
 * by y on 2016/10/25
 */

public class BannerArrayAdapter extends BannerBaseAdapter {
    private int[] imageArray = null;

    public BannerArrayAdapter(int[] imageArray) {
        this.imageArray = imageArray;
    }


    @Override
    protected void imageBannerLoader(ImageView view, int position) {
        if (imageLoaderManage == null) {
            imageLoader(view.getContext(), imageArray[position % imageArray.length], view);
        } else {
            imageLoaderManage.display(view.getContext(), view, imageArray[position % imageArray.length]);
        }
    }

    @Override
    public int getPosition(int position) {
        return position % imageArray.length;
    }

}