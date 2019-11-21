package com.android.banner.widget

import android.content.Context
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.android.banner.*
import com.android.banner.run.BannerHandler
import com.android.banner.transformer.BannerTransformer

class BannerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    companion object {
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
        const val MSG_PAGE = 3
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
        const val PAGE_NUM_VIEW_TOP_LEFT = 0
        const val PAGE_NUM_VIEW_TOP_RIGHT = 1
        const val PAGE_NUM_VIEW_BOTTOM_LEFT = 2
        const val PAGE_NUM_VIEW_BOTTOM_RIGHT = 3
        const val PAGE_NUM_VIEW_CENTER_LEFT = 4
        const val PAGE_NUM_VIEW_CENTER_RIGHT = 5
        const val PAGE_NUM_VIEW_TOP_CENTER = 6
        const val PAGE_NUM_VIEW_BOTTOM_CENTER = 7
        const val BANNER_TIPS_LEFT = 9
        const val BANNER_TIPS_TOP = 10
        const val BANNER_TIPS_RIGHT = 11
        const val BANNER_TIPS_BOTTOM = 12
        const val BANNER_TIPS_CENTER = 13
    }

    internal var preEnablePosition = 0
    internal var handler: BannerHandler = BannerHandler(this)
    internal var imageList: List<BannerInfo> = ArrayList()
    internal var viewPager: BannerViewPager = BannerViewPager(context)
    internal var pageView: BannerPageView? = null
    internal var tipLayout: BannerTipsLayout? = null

    var imageLoaderManager: ImageLoaderManager<out BannerInfo> = defaultImageLoaderManager()
    var onBannerClickListener: OnBannerClickListener<out BannerInfo>? = null
    var onBannerChangeListener: OnBannerChangeListener? = null

    var bannerTransformer: BannerTransformer? = null
        set(value) {
            field = value
            viewPager.setPageTransformer(true, value)
        }

    var offscreenPageLimit = 0
        set(value) {
            field = value
            viewPager.offscreenPageLimit = offscreenPageLimit
        }

    var isStartRotation: Boolean = false
    var enabledRadius: Float = 0F
    var normalRadius: Float = 0F
    var enabledColor: Int = 0
    var normalColor: Int = 0
    var isGuide: Boolean = false
    var viewPagerTouchMode: Boolean = false
    var errorImageView: Int = 0
    var placeImageView: Int = 0
    var bannerDuration: Int = 0
    var isVertical: Boolean = false
    var delayTime: Long = 0

    var visibleDots: Boolean = false
    var dotsWidth: Int = 0
    var dotsHeight: Int = 0
    var dotsSelector: Int = 0
    var dotsLeftMargin: Int = 0
    var dotsRightMargin: Int = 0
    var dotsSite: Int = 0

    var showTipsBackgroundColor: Boolean = false
    var tipsHeight: Int = 0
    var tipsWidth: Int = 0
    var tipsLayoutBackgroundColor: Int = 0
    var tipsSite: Int = 0

    var visibleTitle: Boolean = false
    var titleSize: Float = 0.toFloat()
    var titleColor: Int = 0
    var titleLeftMargin: Int = 0
    var titleRightMargin: Int = 0
    var titleWidth: Int = 0
    var titleHeight: Int = 0
    var titleSite: Int = 0

    var pageNumViewRadius: Float = 0F
    var pageNumViewPaddingTop: Int = 0
    var pageNumViewPaddingLeft: Int = 0
    var pageNumViewPaddingBottom: Int = 0
    var pageNumViewPaddingRight: Int = 0
    var pageNumViewTopMargin: Int = 0
    var pageNumViewRightMargin: Int = 0
    var pageNumViewBottomMargin: Int = 0
    var pageNumViewLeftMargin: Int = 0
    var pageNumViewSite: Int = 0
    var pageNumViewTextColor: Int = 0
    var pageNumViewBackgroundColor: Int = 0
    var pageNumViewTextSize: Float = 0F
    var pageNumViewMark: String = ""

    init {
        bannerTypedArrayImpl(attrs)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onBannerChangeListener?.onPageScrolled(position % dotsSize(), positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        val newPosition = position % dotsSize()
        pageView?.text = TextUtils.concat((newPosition + 1).toString(), pageNumViewMark, dotsSize().toString())
        if (visibleDots) {
            tipLayout?.changeDotsPosition(preEnablePosition, newPosition)
        }
        if (visibleTitle) {
            tipLayout?.setTitle(imageList[newPosition].bannerTitle)
        }
        preEnablePosition = newPosition
        handler.sendMessage(Message.obtain(handler, MSG_PAGE, viewPager.currentItem, 0))
        onBannerChangeListener?.onPageSelected(newPosition)
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (isStartRotation) {
            removeCallbacksAndMessages()
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> handler.sendEmptyMessage(MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> handler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
            }
        }
        onBannerChangeListener?.onPageScrollStateChanged(state)
    }

    fun resource(imageList: ArrayList<out BannerInfo>, showTipsLayout: Boolean = false, showPageView: Boolean = false, isStartRotation: Boolean = true) {
        if (imageList.isNullOrEmpty()) {
            return
        }
        this.imageList = imageList
        initBanner(showTipsLayout, showPageView, isStartRotation)
    }

    fun switchBanner(isStartRotation: Boolean) = apply {
        removeCallbacksAndMessages()
        this.isStartRotation = isStartRotation
        if (isStartRotation) {
            handler.handlerDelayTime = delayTime
            handler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
        } else {
            handler.sendEmptyMessage(MSG_KEEP)
            removeCallbacksAndMessages()
        }
    }
}
