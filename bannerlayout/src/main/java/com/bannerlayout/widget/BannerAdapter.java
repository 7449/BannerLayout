package com.bannerlayout.widget;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.Interface.BannerModelCallBack;
import com.bannerlayout.Interface.ImageLoaderManager;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * by y on 2016/10/24.
 */

class BannerAdapter extends PagerAdapter {
    private List<? extends BannerModelCallBack> imageList = null;
    private int error_image;
    private int place_image;
    private ImageLoaderManager imageLoaderManage = null;
    private OnBannerImageClickListener imageClickListener = null;

    BannerAdapter() {
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View img;
        if (imageLoaderManage == null) {
            img = new ImageView(container.getContext());
            Glide
                    .with(img.getContext())
                    .load(imageList.get(getPosition(position)).getBannerUrl())
                    .placeholder(place_image)
                    .error(error_image)
                    .centerCrop()
                    .into((ImageView) img);
        } else {
            img = imageLoaderManage.display(container, imageList.get(getPosition(position)));
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    imageClickListener.onBannerClick(v, getPosition(position), imageList.get(getPosition(position)));
                }
            }
        });
        container.addView(img);
        return img;
    }


    void addAll(List<? extends BannerModelCallBack> list) {
        imageList = list;
    }

    private int getPosition(int position) {
        return position % imageList.size();
    }

    interface OnBannerImageClickListener {
        void onBannerClick(View view, int position, Object model);
    }

    void setErrorImage(int error_image) {
        this.error_image = error_image;
    }

    void setPlaceImage(int place_image) {
        this.place_image = place_image;
    }

    void setImageClickListener(OnBannerImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }


    void setImageLoaderManage(ImageLoaderManager imageLoaderManage) {
        this.imageLoaderManage = imageLoaderManage;
    }
}
