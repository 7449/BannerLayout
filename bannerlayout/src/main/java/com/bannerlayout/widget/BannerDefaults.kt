package com.bannerlayout.widget

import com.bannerlayout.R

/**
 * by y on 2016/10/25
 */

object BannerDefaults {
    /**
     * By default,  isGuide
     */
    const val IS_GUIDE = false
    /**
     * customize  dots State Selector
     */
    const val DOTS_SELECTOR = 0x0
    /**
     * Default BannerTipsLayout background color
     */
    val TIPS_LAYOUT_BACKGROUND = R.color.colorBackground

    /**
     * The default dots control width
     */
    const val TIPS_LAYOUT_WIDTH = BannerLayout.MATCH_PARENT
    /**
     * The default dots control height
     */
    const val TIPS_LAYOUT_HEIGHT = 50

    /**
     * Default rotation time
     */
    const val DELAY_TIME = 2000

    /**
     * The dots are displayed by default
     */
    const val IS_VISIBLE_DOTS = true

    /**
     * Auto rotation is not turned on by default
     */
    const val IS_START_ROTATION = false
    /**
     * By default,  dots backgrounds are not displayed
     */
    const val IS_TIPS_LAYOUT_BACKGROUND = false

    /**
     * The default dots are marginLeft
     */
    const val DOTS_LEFT_MARGIN = 10
    /**
     * The default dots are marginRight
     */
    const val DOTS_RIGHT_MARGIN = 10

    /**
     * The default dotS width
     */
    const val DOTS_WIDth = 15

    /**
     * The default dotS height
     */
    const val DOTS_HEIGHT = 15

    /**
     * The default viewpager can be manually swiped
     */
    const val VIEW_PAGER_TOUCH_MODE = false

    /**
     * Glide default error placeholder
     */
    val GLIDE_ERROR_IMAGE = R.drawable.ic_launcher

    /**
     * Glide The default placeholder for the load
     */
    val GLIDE_PIACE_IMAGE = R.drawable.ic_launcher

    /**
     * The title is not displayed by default
     */
    const val TITLE_VISIBLE = false

    /**
     * The default title font size
     */
    const val TITLE_SIZE = 12f
    /**
     * The default title font color
     */
    val TITLE_COLOR = R.color.colorYellow
    /**
     * The default title of marginLeft
     */
    const val TITLE_LEFT_MARGIN = 10
    /**
     * The default title of marginRight
     */
    const val TITLE_RIGHT_MARGIN = 10
    /**
     * The width of the default title
     */
    const val TITLE_WIDTH = BannerLayout.WRAP_CONTENT
    /**
     * The height of the default title
     */
    const val TITLE_HEIGHT = BannerLayout.WRAP_CONTENT
    /**
     * Default viewpager switching speed
     */
    const val BANNER_DURATION = 800
    /**
     * Whether the vertical sliding ,The default is not
     */
    const val IS_VERTICAL = false

    const val ENABLED_RADIUS = 20f
    const val NORMAL_RADIUS = 20f
    val ENABLED_COLOR = R.color.colorPrimary
    val NORMAL_COLOR = R.color.colorWhite


    /**
     * this is pageNumberTextView setting
     */
    const val PAGE_NUM_VIEW_RADIUS = 25f
    const val PAGE_NUM_VIEW_RIGHT_MARGIN = 0
    const val PAGE_NUM_VIEW_TOP_MARGIN = 0
    const val PAGE_NUM_VIEW_LEFT_MARGIN = 0
    const val PAGE_NUM_VIEW_BOTTOM_MARGIN = 0
    const val PAGE_NUM_VIEW_SIZE = 10f
    const val PAGE_NUM_VIEW_PADDING_LEFT = 20
    const val PAGE_NUM_VIEW_PADDING_TOP = 5
    const val PAGE_NUM_VIEW_PADDING_RIGHT = 20
    const val PAGE_NUM_VIEW_PADDING_BOTTOM = 5
    val PAGE_NUM_VIEW_BACKGROUND = R.color.colorBackground
    val PAGE_NUL_VIEW_TEXT_COLOR = R.color.colorWhite
    const val PAGE_NUM_VIEW_MARK = " / "

}
