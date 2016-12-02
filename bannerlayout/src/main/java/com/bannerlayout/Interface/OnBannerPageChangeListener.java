package com.bannerlayout.Interface;

/**
 * by y on 2016/11/11
 * <p>
 * Take over the viewPager's OnPageChangeListener method
 */

public interface OnBannerPageChangeListener {

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);
}
