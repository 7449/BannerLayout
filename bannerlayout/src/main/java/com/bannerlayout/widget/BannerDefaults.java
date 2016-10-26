package com.bannerlayout.widget;

import android.graphics.Color;
import android.view.ViewGroup;

import com.bannerlayout.R;

/**
 * by y on 2016/10/25
 */

public class BannerDefaults {

    /**
     * 默认小圆点状态选择器
     */
    public static final int ROUND_SELECTOR = R.drawable.point_background;
    /**
     * 默认小圆点背景色
     */
    public static final int ROUND_CONTAINER_BACKGROUND = R.color.colorBackground;

    /**
     * 默认小圆点控件宽度
     */
    public static final int ROUND_CONTAINER_WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
    /**
     * 默认小圆点控件宽度
     */
    public static final int ROUND_CONTAINER_HEIGHT = 50;

    /**
     * 默认轮播时间
     */
    public static final int DELAY_TIME = 2000;

    /**
     * 默认显示小圆点
     */
    public static final boolean IS_VISIBLE_ROUND = true;

    /**
     * 默认不开启自动轮播
     */
    public static final boolean IS_START_ROTATION = false;
    /**
     * 默认不显示小圆点背景
     */
    public static final boolean ROUND_CONTAINER_BACKGROUND_SWITCH = false;

    /**
     * 默认小圆点的marginLeft
     */
    public static final int ROUND_LEFT_MARGIN = 10;
    /**
     * 默认小圆点的marginRight
     */
    public static final int ROUND_RIGHT_MARGIN = 10;

    /**
     * 默认小圆点的width
     */
    public static final int ROUND_WIDth = 15;

    /**
     * 默认小圆点的height
     */
    public static final int ROUND_HEIGHT = 15;

    /**
     * 默认viewpager可以手动滑动
     */
    public static final boolean VIEW_PAGER_TOUCH_MODE = false;

    /**
     * Glide默认出错占位符
     */
    public static final int GLIDE_ERROR_IMAGE = R.drawable.ic_launcher;

    /**
     * Glide默认加载时占位符
     */
    public static final int GLIDE_PIACE_IMAGE = R.drawable.ic_launcher;

    /**
     * 默认不显示title
     */
    public static final boolean BANNER_TITLE_VISIBLE = false;

    /**
     * 默认title字体大小
     */
    public static final float BANNER_TITLE_SIZE = 12;
    /**
     * 默认title字体颜色
     */
    public static final int BANNER_TITLE_COLOR = Color.YELLOW;
    /**
     * 默认title的marginLeft
     */
    public static final int BANNER_TITLE_LEFT_MARGIN = 10;
    /**
     * 默认title的marginRight
     */
    public static final int BANNER_TITLE_RIGHT_MARGIN = 10;

}
