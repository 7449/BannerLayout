package com.bannerlayout.Interface;

/**
 * by y on 2016/11/11
 * <p>
 * 接管viewpager的OnPageChangeListener方法
 */

public interface OnBannerPageChangeListener {

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);
}
