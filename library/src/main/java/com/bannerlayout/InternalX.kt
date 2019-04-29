@file:Suppress("FunctionName", "UNCHECKED_CAST", "NOTHING_TO_INLINE")

package com.bannerlayout

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bannerlayout.widget.BannerAdapter
import com.bannerlayout.widget.BannerLayout
import com.bannerlayout.widget.BannerLayout.Companion.BANNER_TIPS_BOTTOM
import com.bannerlayout.widget.BannerLayout.Companion.BANNER_TIPS_LEFT
import com.bannerlayout.widget.BannerLayout.Companion.BANNER_TIPS_RIGHT
import com.bannerlayout.widget.BannerLayout.Companion.MATCH_PARENT
import com.bannerlayout.widget.BannerLayout.Companion.PAGE_NUM_VIEW_TOP_RIGHT
import com.bannerlayout.widget.BannerLayout.Companion.WRAP_CONTENT
import com.bannerlayout.widget.BannerPageView
import com.bannerlayout.widget.BannerTipsLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author y
 * @create 2019/4/16
 */

internal inline fun BannerLayout.defaultImageLoaderManager(): ImageLoaderManager<BannerInfo> {
    val requestOptions = RequestOptions().placeholder(placeImageView).error(errorImageView).centerCrop()
    return object : ImageLoaderManager<BannerInfo> {
        override fun display(container: ViewGroup, info: BannerInfo, position: Int): View {
            val imageView = ImageView(container.context)
            Glide.with(imageView.context).load(info.bannerUrl).apply(requestOptions).into(imageView)
            return imageView
        }
    }
}

internal inline fun BannerLayout.dotsSelector(): Drawable {
    return if (dotsSelector == 0x0)
        StateListDrawable()
                .addEnabledState(enabledRadius, enabledColor)
                .addNormalState(normalRadius, normalColor)
    else
        ContextCompat.getDrawable(context, dotsSelector)
                ?: StateListDrawable()
                        .addEnabledState(enabledRadius, enabledColor)
                        .addNormalState(normalRadius, normalColor)
}

internal inline fun BannerLayout.initBanner(showTipsLayout: Boolean = false, showPageView: Boolean = false, startRotation: Boolean = true) = apply {
    removeAllViews()
    preEnablePosition = 0
    val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dotsSize()
    initViewPager(currentItem)
    if (showTipsLayout) {
        initTipsLayout()
    }
    if (showPageView) {
        initPageView()
    }
    handler.handlerPage = currentItem
    handler.handlerDelayTime = delayTime
    if (isGuide) {
        switchBanner(false)
    } else {
        switchBanner(startRotation)
    }
}

internal inline fun BannerLayout.initViewPager(currentItem: Int) {
    viewPager.viewTouchMode = viewPagerTouchMode
    viewPager.addOnPageChangeListener(this)
    viewPager.setDuration(bannerDuration)
    viewPager.adapter = BannerAdapter(imageList, imageLoaderManager as ImageLoaderManager<BannerInfo>, onBannerClickListener as OnBannerClickListener<BannerInfo>?, isGuide)
    if (isVertical) {
        viewPager.isVertical = true
        viewPager.setPageTransformer(true, VerticalTransformer())
    } else {
        viewPager.setPageTransformer(true, bannerTransformer)
    }
    viewPager.currentItem = currentItem
    addView(viewPager)
}

internal inline fun BannerLayout.initPageView() {
    pageView = BannerPageView(context)
    pageView?.text = TextUtils.concat(1.toString(), pageNumViewMark, dotsSize().toString())
    val params = pageView?.run {
        setTextColor(pageNumViewTextColor)
        textSize = pageNumViewTextSize
        viewTopMargin = pageNumViewTopMargin
        viewRightMargin = pageNumViewRightMargin
        viewBottomMargin = pageNumViewBottomMargin
        viewLeftMargin = pageNumViewLeftMargin
        viewPaddingTop = pageNumViewPaddingTop
        viewPaddingBottom = pageNumViewPaddingBottom
        viewPaddingLeft = pageNumViewPaddingLeft
        viewPaddingRight = pageNumViewPaddingRight
        viewRadius = pageNumViewRadius
        viewBackgroundColor = pageNumViewBackgroundColor
        viewSite = pageNumViewSite
        initPageView()
    }
    addView(pageView, params)
}

internal inline fun BannerLayout.initTipsLayout() {
    tipLayout = BannerTipsLayout(context)
    addView(tipLayout, tipLayout?.run {
        removeAllViews()
        viewTitleColor = titleColor
        viewTitleSize = titleSize
        viewTitleLeftMargin = titleLeftMargin
        viewTitleRightMargin = titleRightMargin
        viewTitleWidth = titleWidth
        viewTitleHeight = titleHeight
        viewTitleSite = titleSite

        showViewTipsBackgroundColor = showTipsBackgroundColor
        viewTipsSite = tipsSite
        viewTipsWidth = tipsWidth
        viewTipsHeight = tipsHeight
        viewTipsLayoutBackgroundColor = tipsLayoutBackgroundColor

        viewDotsCount = dotsSize()
        viewDotsHeight = dotsHeight
        viewDotsWidth = dotsWidth
        viewDotsLeftMargin = dotsLeftMargin
        viewDotsRightMargin = dotsRightMargin
        viewDotsSite = dotsSite

        if (visibleDots) {
            initDots(this@initTipsLayout)
        }
        if (visibleTitle) {
            initTitle()
            setTitle(imageList[0].bannerTitle)
        }
        initTips()
    })
}

internal inline fun BannerLayout.BannerTypedArrayImpl(attrs: AttributeSet?) {
    val context = context
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
    isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, false)
    showTipsBackgroundColor = typedArray.getBoolean(R.styleable.BannerLayout_banner_show_tips_background, false)
    tipsLayoutBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_tips_background, ContextCompat.getColor(context, R.color.colorBackground))
    tipsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_width, MATCH_PARENT)
    tipsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_height, 50)
    visibleDots = typedArray.getBoolean(R.styleable.BannerLayout_banner_dots_visible, true)
    dotsLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_left_margin, 10)
    dotsRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_right_margin, 10)
    dotsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_width, 15)
    dotsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_height, 15)
    dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_banner_dots_selector, 0x0)
    enabledRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_enabled_radius, 20f)
    enabledColor = typedArray.getColor(R.styleable.BannerLayout_banner_enabled_color, ContextCompat.getColor(context, R.color.colorPrimary))
    normalRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_norma_radius, 20f)
    normalColor = typedArray.getColor(R.styleable.BannerLayout_banner_normal_color, ContextCompat.getColor(context, R.color.colorWhite))
    delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000).toLong()
    isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_banner_start_rotation, false)
    viewPagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, false)
    errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_error_image, R.drawable.ic_default_loading)
    placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_place_image, R.drawable.ic_default_loading)
    bannerDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, 800)
    isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_vertical, false)
    visibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_banner_title_visible, false)
    titleColor = typedArray.getColor(R.styleable.BannerLayout_banner_title_color, ContextCompat.getColor(context, R.color.colorYellow))
    titleSize = typedArray.getDimension(R.styleable.BannerLayout_banner_title_size, 12f)
    titleRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_right_margin, 10)
    titleLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_left_margin, 10)
    titleWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_title_width, WRAP_CONTENT)
    titleHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_title_height, WRAP_CONTENT)
    tipsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_site, BANNER_TIPS_BOTTOM)
    dotsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_site, BANNER_TIPS_RIGHT)
    titleSite = typedArray.getInteger(R.styleable.BannerLayout_banner_title_site, BANNER_TIPS_LEFT)
    pageNumViewSite = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_site, PAGE_NUM_VIEW_TOP_RIGHT)
    pageNumViewRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_page_num_radius, 25f)
    pageNumViewPaddingTop = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_top, 5)
    pageNumViewPaddingLeft = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_left, 20)
    pageNumViewPaddingBottom = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_bottom, 5)
    pageNumViewPaddingRight = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_right, 20)
    pageNumViewTopMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_top, 0)
    pageNumViewRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_right, 0)
    pageNumViewBottomMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_bottom, 0)
    pageNumViewLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_left, 0)
    pageNumViewTextColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_text_color, ContextCompat.getColor(context, R.color.colorWhite))
    pageNumViewBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_background_color, ContextCompat.getColor(context, R.color.colorBackground))
    pageNumViewTextSize = typedArray.getDimension(R.styleable.BannerLayout_banner_page_num_text_size, 10f)
    pageNumViewMark = typedArray.getString(R.styleable.BannerLayout_banner_page_num_mark)
            ?: " / "
    typedArray.recycle()
}