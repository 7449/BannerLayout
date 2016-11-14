package com.bannerlayout.adapter;

import android.view.View;
import android.widget.ImageView;

/**
 * by y on 2016/10/25
 */

public class BannerArrayAdapter extends BannerBaseAdapter {
    private Object[] imageArray = null;

    public BannerArrayAdapter(Object[] imageArray) {
        this.imageArray = imageArray;
    }


    @Override
    protected void imageBannerLoader(ImageView view, final int position) {
        if (imageLoaderManage == null) {
            imageLoader(view.getContext(), imageArray[position % imageArray.length], view);
        } else {
            imageLoaderManage.display(view.getContext(), view, imageArray[position % imageArray.length], imageArray);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    imageClickListener.onBannerClick(getPosition(position), imageArray);
                }
            }
        });
    }

    @Override
    public int getPosition(int position) {
        return position % imageArray.length;
    }

}