package com.bannerlayout.adapter;

import android.widget.ImageView;

import com.bannerlayout.model.BannerModel;

import java.util.List;

/**
 * by y on 2016/10/25
 */

public class BannerListAdapter extends BannerBaseAdapter {
    private List<BannerModel> imageList = null;

    public BannerListAdapter(List<BannerModel> imageList) {
        this.imageList = imageList;
    }

    @Override
    public ImageView getView(ImageView view, int position) {
        imageLoader(view.getContext(), imageList.get(position % imageList.size()).getImage(), view);
        return view;
    }

    @Override
    public int getPosition(int position) {
        return position % imageList.size();
    }

}
