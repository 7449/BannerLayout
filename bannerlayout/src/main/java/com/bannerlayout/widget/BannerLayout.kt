package com.bannerlayout.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.bannerlayout.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@Suppress("FunctionName")
fun BannerTypedArrayImpl(bannerLayout: BannerLayout, attrs: AttributeSet?) {
    val context = bannerLayout.context
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
    bannerLayout.isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, false)
    bannerLayout.showTipsBackgroundColor = typedArray.getBoolean(R.styleable.BannerLayout_banner_show_tips_background, false)
    bannerLayout.tipsLayoutBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_tips_background, ContextCompat.getColor(context, R.color.colorBackground))
    bannerLayout.tipsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_width, BannerLayout.MATCH_PARENT)
    bannerLayout.tipsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_height, 50)
    bannerLayout.visibleDots = typedArray.getBoolean(R.styleable.BannerLayout_banner_dots_visible, true)
    bannerLayout.dotsLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_left_margin, 10)
    bannerLayout.dotsRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_right_margin, 10)
    bannerLayout.dotsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_width, 15)
    bannerLayout.dotsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_height, 15)
    bannerLayout.dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_banner_dots_selector, 0x0)
    bannerLayout.enabledRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_enabled_radius, 20f)
    bannerLayout.enabledColor = typedArray.getColor(R.styleable.BannerLayout_banner_enabled_color, ContextCompat.getColor(context, R.color.colorPrimary))
    bannerLayout.normalRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_norma_radius, 20f)
    bannerLayout.normalColor = typedArray.getColor(R.styleable.BannerLayout_banner_normal_color, ContextCompat.getColor(context, R.color.colorWhite))
    bannerLayout.delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000).toLong()
    bannerLayout.isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_banner_start_rotation, false)
    bannerLayout.viewPagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, false)
    bannerLayout.errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_error_image, R.drawable.ic_launcher)
    bannerLayout.placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_place_image, R.drawable.ic_launcher)
    bannerLayout.bannerDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, 800)
    bannerLayout.isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_vertical, false)
    bannerLayout.visibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_banner_title_visible, false)
    bannerLayout.titleColor = typedArray.getColor(R.styleable.BannerLayout_banner_title_color, ContextCompat.getColor(context, R.color.colorYellow))
    bannerLayout.titleSize = typedArray.getDimension(R.styleable.BannerLayout_banner_title_size, 12f)
    bannerLayout.titleRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_right_margin, 10)
    bannerLayout.titleLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_left_margin, 10)
    bannerLayout.titleWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_title_width, BannerLayout.WRAP_CONTENT)
    bannerLayout.titleHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_title_height, BannerLayout.WRAP_CONTENT)
    bannerLayout.tipsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_site, BannerLayout.BANNER_TIPS_BOTTOM)
    bannerLayout.dotsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_site, BannerLayout.BANNER_TIPS_RIGHT)
    bannerLayout.titleSite = typedArray.getInteger(R.styleable.BannerLayout_banner_title_site, BannerLayout.BANNER_TIPS_LEFT)
    bannerLayout.pageNumViewSite = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_site, BannerLayout.PAGE_NUM_VIEW_TOP_RIGHT)
    bannerLayout.pageNumViewRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_page_num_radius, 25f)
    bannerLayout.pageNumViewPaddingTop = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_top, 5)
    bannerLayout.pageNumViewPaddingLeft = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_left, 20)
    bannerLayout.pageNumViewPaddingBottom = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_bottom, 5)
    bannerLayout.pageNumViewPaddingRight = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_padding_right, 20)
    bannerLayout.pageNumViewTopMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_top, 0)
    bannerLayout.pageNumViewRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_right, 0)
    bannerLayout.pageNumViewBottomMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_bottom, 0)
    bannerLayout.pageNumViewLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_margin_left, 0)
    bannerLayout.pageNumViewTextColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_text_color, ContextCompat.getColor(context, R.color.colorWhite))
    bannerLayout.pageNumViewBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_background_color, ContextCompat.getColor(context, R.color.colorBackground))
    bannerLayout.pageNumViewTextSize = typedArray.getDimension(R.styleable.BannerLayout_banner_page_num_text_size, 10f)
    bannerLayout.pageNumViewMark = typedArray.getString(R.styleable.BannerLayout_banner_page_num_mark)
            ?: " / "
    typedArray.recycle()
}

class BannerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr),
        ViewPager.OnPageChangeListener {

    companion object {

        const val MATCH_PARENT = FrameLayout.LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = FrameLayout.LayoutParams.WRAP_CONTENT

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

    private var preEnablePosition = 0
    private var handler: BannerHandlerUtils = BannerHandlerUtils(this)
    private var imageList: List<BannerModelCallBack> = ArrayList()

    var viewPager: BannerViewPager = BannerViewPager(context)
    private var pageView: BannerPageView? = null
    private var tipLayout: BannerTipsLayout? = null

    var isStartRotation: Boolean = false

    var imageLoaderManager: ImageLoaderManager<out BannerModelCallBack> = defaultImageLoaderManager()

    var onBannerClickListener: OnBannerClickListener<out BannerModelCallBack>? = null

    var onBannerChangeListener: OnBannerChangeListener? = null

    var transformerList: List<BannerTransformer> = ArrayList()

    var bannerTransformer: BannerTransformer? = null
        set(value) {
            field = value
            viewPager.setPageTransformer(true, value)
        }

    var bannerTransformerType = 0
        set(value) {
            field = value
            bannerTransformer = getTransformer(value)
        }

    var offscreenPageLimit = 0
        set(value) {
            field = value
            viewPager.offscreenPageLimit = offscreenPageLimit
        }

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
        BannerTypedArrayImpl(this, attrs)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onBannerChangeListener?.onPageScrolled(position % dotsSize, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        val newPosition = position % dotsSize
        pageView?.text = TextUtils.concat((newPosition + 1).toString(), pageNumViewMark, dotsSize.toString())
        if (visibleDots) {
            tipLayout?.changeDotsPosition(preEnablePosition, newPosition)
        }
        if (visibleTitle) {
            tipLayout?.setTitle(imageList[newPosition].bannerTitle)
        }
        preEnablePosition = newPosition
        if (!transformerList.isEmpty() && !isVertical) {
            viewPager.setPageTransformer(true, transformerList[(Math.random() * transformerList.size).toInt()])
        }
        handler.sendMessage(Message.obtain(handler, BannerHandlerUtils.MSG_PAGE, viewPager.currentItem, 0))
        onBannerChangeListener?.onPageSelected(newPosition)
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (isStartRotation) {
            removeHandler()
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> handler.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> handler.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime)
            }
        }
        onBannerChangeListener?.onPageScrollStateChanged(state)
    }

    fun initPageNumView() = apply { pageView = BannerPageView(context) }

    fun initTips() = apply { tipLayout = BannerTipsLayout(context) }

    fun resource(imageList: MutableList<out BannerModelCallBack>) = apply {
        this.imageList = imageList
        initBanner()
    }

    fun switchBanner(isStartRotation: Boolean) = apply {
        removeHandler()
        this.isStartRotation = isStartRotation
        if (isStartRotation) {
            handler.handlerDelayTime = delayTime
            handler.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime)
        } else {
            handler.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP)
            removeHandler()
        }
    }

    fun currentItem(page: Int) {
        viewPager.currentItem = page
    }

    fun dotsSelector(): Drawable {
        return if (dotsSelector == 0x0)
            getDrawableSelector(
                    enabledRadius,
                    enabledColor,
                    normalRadius,
                    normalColor)
        else
            ContextCompat.getDrawable(context, dotsSelector)
                    ?: getDrawableSelector(
                            enabledRadius,
                            enabledColor,
                            normalRadius,
                            normalColor)
    }

    private fun initBanner() = apply {
        removeAllViews()
        preEnablePosition = 0
        val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dotsSize
        initViewPager(currentItem)
        initPageView()
        initTipsView()
        handler.apply {
            handlerPage = currentItem
            handlerDelayTime = delayTime
        }
        switchBanner(isStartRotation)
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewPager(currentItem: Int) {
        viewPager.viewTouchMode = viewPagerTouchMode
        viewPager.addOnPageChangeListener(this)
        viewPager.setDuration(bannerDuration)
        viewPager.adapter = BannerAdapter(imageList, imageLoaderManager as ImageLoaderManager<BannerModelCallBack>, onBannerClickListener as OnBannerClickListener<BannerModelCallBack>?, isGuide)
        if (isVertical) {
            viewPager.isVertical = true
            viewPager.setPageTransformer(true, VerticalTransformer())
        } else {
            viewPager.setPageTransformer(true, bannerTransformer)
        }
        viewPager.currentItem = currentItem
        addView(viewPager)
    }

    private fun initPageView() {
        if (pageView == null) {
            return
        }
        pageView?.text = TextUtils.concat(1.toString(), pageNumViewMark, dotsSize.toString())
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

    private fun initTipsView() {
        if (tipLayout == null) {
            return
        }
        val params = tipLayout?.run {
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

            viewDotsCount = dotsSize
            viewDotsHeight = dotsHeight
            viewDotsWidth = dotsWidth
            viewDotsLeftMargin = dotsLeftMargin
            viewDotsRightMargin = dotsRightMargin
            viewDotsSite = dotsSite

            if (visibleDots) {
                initDots(this@BannerLayout)
            }
            if (visibleTitle) {
                initTitle()
                setTitle(imageList[0].bannerTitle)
            }
            initTips()
        }
        addView(tipLayout, params)
    }

    private fun defaultImageLoaderManager(): ImageLoaderManager<BannerModelCallBack> {
        val requestOptions = RequestOptions().placeholder(placeImageView).error(errorImageView).centerCrop()
        return object : ImageLoaderManager<BannerModelCallBack> {
            override fun display(container: ViewGroup, model: BannerModelCallBack): View {
                val imageView = ImageView(container.context)
                Glide.with(imageView.context).load(model.bannerUrl).apply(requestOptions).into(imageView)
                return imageView
            }
        }
    }

    private val dotsSize: Int get() = imageList.size

    val duration: Int get() = viewPager.duration

    val bannerStatus: Int get() = handler.status

    fun viewPagerLayoutParams(): FrameLayout.LayoutParams? = viewPager.layoutParams as LayoutParams?

    fun removeHandler() = handler.removeCallbacksAndMessages(null)

    fun imageList(): List<BannerModelCallBack> = imageList
}
