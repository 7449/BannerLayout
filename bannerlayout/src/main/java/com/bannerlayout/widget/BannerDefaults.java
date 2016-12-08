package com.bannerlayout.widget;

import android.graphics.Color;
import android.view.ViewGroup;

import com.bannerlayout.R;

/**
 * by y on 2016/10/25
 */

class BannerDefaults {

    /**
     * Default Small Dot State Selector
     */
    static final int ROUND_SELECTOR = R.drawable.point_background;
    /**
     * Default small dot background color
     */
    static final int ROUND_CONTAINER_BACKGROUND = R.color.colorBackground;

    /**
     * The default dots control width
     */
    static final int ROUND_CONTAINER_WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
    /**
     * The default dots control height
     */
    static final int ROUND_CONTAINER_HEIGHT = 50;

    /**
     * Default rotation time
     */
    static final int DELAY_TIME = 2000;

    /**
     * The dots are displayed by default
     */
    static final boolean IS_VISIBLE_ROUND = true;

    /**
     * Auto rotation is not turned on by default
     */
    static final boolean IS_START_ROTATION = false;
    /**
     * By default, small dot backgrounds are not displayed
     */
    static final boolean ROUND_CONTAINER_BACKGROUND_SWITCH = false;

    /**
     * The default dots are marginLeft
     */
    static final int ROUND_LEFT_MARGIN = 10;
    /**
     * The default dots are marginRight
     */
    static final int ROUND_RIGHT_MARGIN = 10;

    /**
     * The default dot width
     */
    static final int ROUND_WIDth = 15;

    /**
     * The default dot height
     */
    static final int ROUND_HEIGHT = 15;

    /**
     * The default viewpager can be manually swiped
     */
    static final boolean VIEW_PAGER_TOUCH_MODE = false;

    /**
     * Glide default error placeholder
     */
    static final int GLIDE_ERROR_IMAGE = R.drawable.ic_launcher;

    /**
     * Glide The default placeholder for the load
     */
    static final int GLIDE_PIACE_IMAGE = R.drawable.ic_launcher;

    /**
     * The title is not displayed by default
     */
    static final boolean BANNER_TITLE_VISIBLE = false;

    /**
     * The default title font size
     */
    static final float BANNER_TITLE_SIZE = 12;
    /**
     * The default title font color
     */
    static final int BANNER_TITLE_COLOR = Color.YELLOW;
    /**
     * The default title of marginLeft
     */
    static final int BANNER_TITLE_LEFT_MARGIN = 10;
    /**
     * The default title of marginRight
     */
    static final int BANNER_TITLE_RIGHT_MARGIN = 10;
    /**
     * The width of the default title
     */
    static final int BANNER_TITLE_WIDTH = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * The height of the default title
     */
    static final int BANNER_TITLE_HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * Default viewpager switching speed
     */
    static final int BANNER_DURATION = 800;
    /**
     * Whether the vertical sliding ,The default is not
     */
    static final boolean BANNER_ISVERTICAL = false;
}
