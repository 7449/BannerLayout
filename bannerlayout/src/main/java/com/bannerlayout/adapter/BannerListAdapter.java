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
    protected void imageBannerLoader(ImageView view, int position) {
        if (imageLoaderManage == null) {
            imageLoader(view.getContext(), imageList.get(position % imageList.size()).getImage(), view);
        } else {
            imageLoaderManage.display(view.getContext(), view, imageList.get(position % imageList.size()).getImage());
        }
    }

    @Override
    public int getPosition(int position) {
        return position % imageList.size();
    }

}
