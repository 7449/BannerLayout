package com.bannerlayout.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bannerlayout.model.BannerModel;

import java.util.List;

/**
 * by y on 2016/10/25
 */

public class BannerListAdapter extends BannerBaseAdapter {
    private List<? extends BannerModel> imageList = null;

    public BannerListAdapter(List<? extends BannerModel> imageList) {
        this.imageList = imageList;
    }

    @Override
    protected void imageBannerLoader(ImageView view, final int position) {
        if (imageLoaderManage == null) {
            imageLoader(view.getContext(), imageList.get(getPosition(position)).getImage(), view);
        } else {
            imageLoaderManage.display(view.getContext(), view, imageList.get(getPosition(position)).getImage(), imageList.get(getPosition(position)));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    imageClickListener.onBannerClick(getPosition(position), imageList.get(getPosition(position)));
                }
            }
        });
    }

    @Override
    public int getPosition(int position) {
        return position % imageList.size();
    }

}
