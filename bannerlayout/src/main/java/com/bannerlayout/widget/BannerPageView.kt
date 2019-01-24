package com.bannerlayout.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.bannerlayout.getShape

internal class BannerPageView(context: Context) : AppCompatTextView(context) {

    var viewTopMargin: Int = 0
    var viewRightMargin: Int = 0
    var viewBottomMargin: Int = 0
    var viewLeftMargin: Int = 0
    var viewPaddingTop: Int = 0
    var viewPaddingLeft: Int = 0
    var viewPaddingBottom: Int = 0
    var viewPaddingRight: Int = 0
    var viewRadius: Float = 0F
    var viewBackgroundColor: Int = 0
    var viewSite: Int = 0

    fun initPageView(): FrameLayout.LayoutParams {
        val pageParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        pageParams.setMargins(viewLeftMargin, viewTopMargin, viewRightMargin, viewBottomMargin)
        when (viewSite) {
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
        setPadding(viewPaddingLeft, viewPaddingTop, viewPaddingRight, viewPaddingBottom)
        setBackgroundDrawable(getShape(viewRadius, viewBackgroundColor))
        return pageParams
    }
}
