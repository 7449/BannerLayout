package androidx.banner.page

import android.graphics.Color
import android.text.TextUtils
import androidx.banner.BannerLayout
import androidx.banner.listener.OnBannerPageChangeListener.Companion.doOnPageSelected

fun BannerLayout.marginPageView(
    margin: Int,
    pagePaddingTop: Int = 5,
    pagePaddingLeft: Int = 20,
    pagePaddingBottom: Int = 5,
    pagePaddingRight: Int = 20,
    pageRadius: Float = 25f,
    pageMark: String = " / ",
    pageTextSize: Float = 10f,
    pageTextColor: Int = Color.WHITE,
    pageBackgroundColor: Int = Color.DKGRAY,
    pageSite: Int = BannerPageView.PAGE_NUM_VIEW_TOP_RIGHT
) = addPageView(
    margin,
    margin,
    margin,
    margin,
    pagePaddingTop,
    pagePaddingLeft,
    pagePaddingBottom,
    pagePaddingRight,
    pageRadius,
    pageMark,
    pageTextSize,
    pageTextColor,
    pageBackgroundColor,
    pageSite
)

fun BannerLayout.paddingPageView(
    padding: Int,
    pageTopMargin: Int = 0,
    pageRightMargin: Int = 0,
    pageBottomMargin: Int = 0,
    pageLeftMargin: Int = 0,
    pageRadius: Float = 25f,
    pageMark: String = " / ",
    pageTextSize: Float = 10f,
    pageTextColor: Int = Color.WHITE,
    pageBackgroundColor: Int = Color.DKGRAY,
    pageSite: Int = BannerPageView.PAGE_NUM_VIEW_TOP_RIGHT
) = addPageView(
    pageTopMargin,
    pageRightMargin,
    pageBottomMargin,
    pageLeftMargin,
    padding,
    padding,
    padding,
    padding,
    pageRadius,
    pageMark,
    pageTextSize,
    pageTextColor,
    pageBackgroundColor,
    pageSite
)

fun BannerLayout.addPageView(
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
    pageSite: Int = BannerPageView.PAGE_NUM_VIEW_TOP_RIGHT
) = also {
    require(checkViewPager) { "must add ViewPage first;" }
    val bannerPageView = BannerPageView(context)
    val params = bannerPageView.run {
        addPageView(
            itemCount,
            pageTopMargin,
            pageRightMargin,
            pageBottomMargin,
            pageLeftMargin,
            pagePaddingTop,
            pagePaddingLeft,
            pagePaddingBottom,
            pagePaddingRight,
            pageRadius,
            pageMark,
            pageTextSize,
            pageTextColor,
            pageBackgroundColor,
            pageSite
        )
    }
    doOnPageSelected {
        bannerPageView.text = TextUtils.concat((it + 1).toString(), pageMark, itemCount.toString())
    }
    removePageView()
    addView(bannerPageView, params)
}

fun BannerLayout.removePageView() {
    for (index in 0 until childCount) {
        getChildAt(index)?.let {
            if (it is BannerPageView) {
                removeView(it)
            }
        }
    }
}
