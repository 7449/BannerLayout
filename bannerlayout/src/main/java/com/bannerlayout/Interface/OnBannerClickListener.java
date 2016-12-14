package com.bannerlayout.Interface;

/**
 * by y on 2016/11/11
 * <p>
 * Banner Click event, object for the return of data
 */

public interface OnBannerClickListener<T> {
    void onBannerClick(int position, T model);
}
