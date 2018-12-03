package com.bannerlayout.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.bannerlayout.getShape

const val PAGE_NUM_VIEW_TOP_LEFT = 0
const val PAGE_NUM_VIEW_TOP_RIGHT = 1
const val PAGE_NUM_VIEW_BOTTOM_LEFT = 2
const val PAGE_NUM_VIEW_BOTTOM_RIGHT = 3
const val PAGE_NUM_VIEW_CENTER_LEFT = 4
const val PAGE_NUM_VIEW_CENTER_RIGHT = 5
const val PAGE_NUM_VIEW_TOP_CENTER = 6
const val PAGE_NUM_VIEW_BOTTOM_CENTER = 7

class BannerPageView(context: Context) : AppCompatTextView(context) {

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
            PAGE_NUM_VIEW_TOP_LEFT -> {
            }
            PAGE_NUM_VIEW_TOP_RIGHT -> pageParams.gravity = Gravity.END or Gravity.TOP
            PAGE_NUM_VIEW_BOTTOM_LEFT -> pageParams.gravity = Gravity.START or Gravity.BOTTOM
            PAGE_NUM_VIEW_BOTTOM_RIGHT -> pageParams.gravity = Gravity.END or Gravity.BOTTOM
            PAGE_NUM_VIEW_CENTER_LEFT -> pageParams.gravity = Gravity.START or Gravity.CENTER
            PAGE_NUM_VIEW_CENTER_RIGHT -> pageParams.gravity = Gravity.END or Gravity.CENTER
            PAGE_NUM_VIEW_TOP_CENTER -> pageParams.gravity = Gravity.TOP or Gravity.CENTER
            PAGE_NUM_VIEW_BOTTOM_CENTER -> pageParams.gravity = Gravity.BOTTOM or Gravity.CENTER
        }
        setPadding(viewPaddingLeft, viewPaddingTop, viewPaddingRight, viewPaddingBottom)
        setBackgroundDrawable(getShape(viewRadius, viewBackgroundColor))
        return pageParams
    }
}
