package androidx.banner.page

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.banner.color
import androidx.banner.cornerRadius

class BannerPageView(context: Context) : TextView(context) {

    companion object {
        const val PAGE_NUM_VIEW_TOP_LEFT = 0
        const val PAGE_NUM_VIEW_TOP_RIGHT = 1
        const val PAGE_NUM_VIEW_BOTTOM_LEFT = 2
        const val PAGE_NUM_VIEW_BOTTOM_RIGHT = 3
        const val PAGE_NUM_VIEW_CENTER_LEFT = 4
        const val PAGE_NUM_VIEW_CENTER_RIGHT = 5
        const val PAGE_NUM_VIEW_TOP_CENTER = 6
        const val PAGE_NUM_VIEW_BOTTOM_CENTER = 7
    }

    internal fun addPageView(
        pageCount: Int = 0,
        pageTopMargin: Int = 0,
        pageRightMargin: Int = 0,
        pageBottomMargin: Int = 0,
        pageLeftMargin: Int = 0,
        pagePaddingTop: Int = 5,
        pagePaddingLeft: Int = 20,
        pagePaddingBottom: Int = 5,
        pagePaddingRight: Int = 20,
        pageRadius: Float = 25f,
        pageMark: String = " / ",
        pageTextSize: Float = 10f,
        pageTextColor: Int = Color.WHITE,
        pageBackgroundColor: Int = Color.DKGRAY,
        pageSite: Int = PAGE_NUM_VIEW_TOP_RIGHT
    ): FrameLayout.LayoutParams {
        val pageParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        pageParams.setMargins(pageLeftMargin, pageTopMargin, pageRightMargin, pageBottomMargin)
        when (pageSite) {
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
        setPadding(pagePaddingLeft, pagePaddingTop, pagePaddingRight, pagePaddingBottom)
        background = GradientDrawable().cornerRadius(pageRadius).color(pageBackgroundColor)
        setTextColor(pageTextColor)
        textSize = pageTextSize
        text = TextUtils.concat(1.toString(), pageMark, pageCount.toString())
        return pageParams
    }
}
