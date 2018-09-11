package com.bannerlayout.widget

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout

import com.bannerlayout.util.BannerSelectorUtils

/**
 * by y on 2017/1/6
 */
class BannerPageView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun initPageView(pageNumViewInterface: PageNumViewInterface): FrameLayout.LayoutParams {
        val pageParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        pageParams.setMargins(
                pageNumViewInterface.pageNumViewLeftMargin,
                pageNumViewInterface.pageNumViewTopMargin,
                pageNumViewInterface.pageNumViewRightMargin,
                pageNumViewInterface.pageNumViewBottomMargin
        )
        when (pageNumViewInterface.pageNumViewSite) {
            BannerLayout.PAGE_NUM_VIEW_TOP_LEFT -> {
            }
            BannerLayout.PAGE_NUM_VIEW_TOP_RIGHT -> pageParams.gravity = Gravity.END or Gravity.TOP
            BannerLayout.PAGE_NUM_VIEW_BOTTOM_LEFT -> pageParams.gravity = Gravity.START or Gravity.BOTTOM
            BannerLayout.PAGE_NUM_VIEW_BOTTOM_RIGHT -> pageParams.gravity = Gravity.END or Gravity.BOTTOM
            BannerLayout.PAGE_NUM_VIEW_CENTER_LEFT -> pageParams.gravity = Gravity.START or Gravity.CENTER
            BannerLayout.PAGE_NUM_VIEW_CENTER_RIGHT -> pageParams.gravity = Gravity.END or Gravity.CENTER
            BannerLayout.PAGE_NUM_VIEW_TOP_CENTER -> pageParams.gravity = Gravity.TOP or Gravity.CENTER
            BannerLayout.PAGE_NUM_VIEW_BOTTOM_CENTER -> pageParams.gravity = Gravity.BOTTOM or Gravity.CENTER
        }
        setTextColor(pageNumViewInterface.pageNumViewTextColor)
        textSize = pageNumViewInterface.pageNumViewTextSize
        setPadding(pageNumViewInterface.pageNumViewPaddingLeft,
                pageNumViewInterface.pageNumViewPaddingTop,
                pageNumViewInterface.pageNumViewPaddingRight,
                pageNumViewInterface.pageNumViewPaddingBottom)
        setBackgroundDrawable(BannerSelectorUtils.getShape(pageNumViewInterface.pageNumViewRadius,
                pageNumViewInterface.pageNumViewBackgroundColor))
        return pageParams
    }

    interface PageNumViewInterface {
        val pageNumViewTopMargin: Int
        val pageNumViewRightMargin: Int
        val pageNumViewBottomMargin: Int
        val pageNumViewLeftMargin: Int
        val pageNumViewTextColor: Int
        val pageNumViewTextSize: Float
        val pageNumViewPaddingTop: Int
        val pageNumViewPaddingLeft: Int
        val pageNumViewPaddingBottom: Int
        val pageNumViewPaddingRight: Int
        val pageNumViewRadius: Float
        val pageNumViewBackgroundColor: Int
        val pageNumViewSite: Int
    }

}
