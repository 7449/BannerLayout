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
    public ImageView getView(ImageView view, int position) {
        imageLoader(view.getContext(), (imageArray[position % imageArray.length]), view);
        return view;
    }

    @Override
    public int getPosition(int position) {
        return position % imageArray.length;
    }

}