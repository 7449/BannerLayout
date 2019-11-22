package com.android.banner.widget

import android.content.Context
import android.os.Message
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
    internal var tipLayout: BannerTipsLayout? = null

    var imageLoaderManager: ImageLoaderManager<out BannerInfo> = defaultImageLoaderManager()
    var onBannerClickListener: OnBannerClickListener<out BannerInfo>? = null
    val onBannerChangeListener: ArrayList<OnBannerChangeListener> = ArrayList()

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

    init {
        bannerTypedArrayImpl(attrs)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onBannerChangeListener.forEach { it.onPageScrolled(position % dotsSize(), positionOffset, positionOffsetPixels) }
    }

    override fun onPageSelected(position: Int) {
        val newPosition = position % dotsSize()
        if (visibleDots) {
            tipLayout?.changeDotsPosition(preEnablePosition, newPosition)
        }
        if (visibleTitle) {
            tipLayout?.setTitle(imageList[newPosition].bannerTitle)
        }
        preEnablePosition = newPosition
        handler.sendMessage(Message.obtain(handler, MSG_PAGE, viewPager.currentItem, 0))
        onBannerChangeListener.forEach { it.onPageSelected(newPosition) }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (isStartRotation) {
            removeCallbacksAndMessages()
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> handler.sendEmptyMessage(MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> handler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
            }
        }
        onBannerChangeListener.forEach { it.onPageScrollStateChanged(state) }
    }

    fun resource(imageList: ArrayList<out BannerInfo>, showTipsLayout: Boolean = false, isStartRotation: Boolean = true) = apply {
        if (imageList.isNotEmpty()) {
            this.imageList = imageList
            initBanner(showTipsLayout, isStartRotation)
        }
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
