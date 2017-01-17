package com.bannerlayout.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerlayout.Interface.ImageLoaderManager;
import com.bannerlayout.R;
import com.bannerlayout.exception.BannerException;
import com.bannerlayout.model.BannerModel;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * by y on 2016/10/24.
 */

class BannerAdapter extends PagerAdapter {
    private List<? extends BannerModel> imageList = null;
    private int error_image = -1;
    private int place_image = -1;
    private ImageLoaderManager imageLoaderManage = null;
    private OnBannerImageClickListener imageClickListener = null;

    BannerAdapter(List<? extends BannerModel> imageList) {
        this.imageList = imageList;
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
        ImageView img = new ImageView(container.getContext());
        if (imageLoaderManage == null) {
            imageLoader(img.getContext(), imageList.get(getPosition(position)).getImage(), img);
        } else {
            //noinspection unchecked
            imageLoaderManage.display(img, imageList.get(getPosition(position)));
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClickListener != null) {
                    imageClickListener.onBannerClick(v,getPosition(position), imageList.get(getPosition(position)));
                }
            }
        });
        container.addView(img);
        return img;
    }

    private void imageLoader(Context context, Object url, ImageView imageView) {
        if (place_image == -1 || error_image == -1) {
            throw new BannerException(context.getString(R.string.glide_excpetion));
        }
        Glide.with(context).load(url).placeholder(place_image)
                .error(error_image).centerCrop().into(imageView);
    }


    private int getPosition(int position) {
        return position % imageList.size();
    }

    interface OnBannerImageClickListener {
        void onBannerClick(View view,int position, Object model);
    }
}
