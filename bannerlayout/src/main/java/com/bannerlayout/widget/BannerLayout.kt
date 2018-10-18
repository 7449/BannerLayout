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
import com.bannerlayout.R
import com.bannerlayout.animation.BannerTransformer
import com.bannerlayout.animation.VerticalTransformer
import com.bannerlayout.listener.*
import com.bannerlayout.util.BannerHandlerUtils
import com.bannerlayout.util.BannerSelectorUtils
import com.bannerlayout.util.TransformerUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * by y on 2016/10/24
 */
class BannerLayout : FrameLayout, ViewPagerCurrent, ViewPager.OnPageChangeListener, OnBannerImageClickListener<BannerModelCallBack> {

    private var preEnablePosition = 0
    private var viewPager: BannerViewPager = BannerViewPager(context)
    private var handler: BannerHandlerUtils = BannerHandlerUtils(this)
    private var imageList: List<BannerModelCallBack> = ArrayList()

    private var pageView: BannerPageView? = null
    private var tipLayout: BannerTipsLayout? = null

    var isStartRotation: Boolean = false
        private set

    var imageLoaderManager: ImageLoaderManager<*> = initImageLoaderManager()

    var onBannerChangeListener: OnBannerChangeListener = SimpleOnBannerChangeListener()

    var onBannerClickListener: OnBannerClickListener<*>? = null

    var transformerList: List<BannerTransformer> = ArrayList()

    var bannerTransformer: BannerTransformer? = null
        set(value) {
            field = value
            viewPager.setPageTransformer(true, value)
        }

    var bannerTransformerType = 0
        set(value) {
            field = value
            bannerTransformer = TransformerUtils.getTransformer(value)
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

    /****  [BannerTipsLayout.initDots] ***/
    var isVisibleDots: Boolean = false
    var dotsWidth: Int = 0
    var dotsHeight: Int = 0
    var dotsSelector: Int = 0
    var dotsLeftMargin: Int = 0
    var dotsRightMargin: Int = 0
    var dotsSite: Int = 0

    /****  [BannerTipsLayout.initTips] ***/
    var showTipsBackgroundColor: Boolean = false
    var tipsHeight: Int = 0
    var tipsWidth: Int = 0
    var tipsLayoutBackgroundColor: Int = 0
    var tipsSite: Int = 0

    /****  [BannerTipsLayout.initTitle] ***/
    var isVisibleTitle: Boolean = false
    var titleSize: Float = 0.toFloat()
    var titleColor: Int = 0
    var titleLeftMargin: Int = 0
    var titleRightMargin: Int = 0
    var titleWidth: Int = 0
    var titleHeight: Int = 0
    var titleSite: Int = 0

    /****  [BannerPageView] ***/
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

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, BannerDefaults.IS_GUIDE)
        showTipsBackgroundColor = typedArray.getBoolean(R.styleable.BannerLayout_banner_is_tips_background, BannerDefaults.IS_TIPS_LAYOUT_BACKGROUND)
        tipsLayoutBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_tips_background, ContextCompat.getColor(context, BannerDefaults.TIPS_LAYOUT_BACKGROUND))
        tipsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_width, BannerDefaults.TIPS_LAYOUT_WIDTH)
        tipsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_height, BannerDefaults.TIPS_LAYOUT_HEIGHT)
        isVisibleDots = typedArray.getBoolean(R.styleable.BannerLayout_banner_dots_visible, BannerDefaults.IS_VISIBLE_DOTS)
        dotsLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_left_margin, BannerDefaults.DOTS_LEFT_MARGIN)
        dotsRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_right_margin, BannerDefaults.DOTS_RIGHT_MARGIN)
        dotsWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_width, BannerDefaults.DOTS_WIDth)
        dotsHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_height, BannerDefaults.DOTS_HEIGHT)
        dotsSelector = typedArray.getResourceId(R.styleable.BannerLayout_banner_dots_selector, BannerDefaults.DOTS_SELECTOR)
        enabledRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_enabledRadius, BannerDefaults.ENABLED_RADIUS)
        enabledColor = typedArray.getColor(R.styleable.BannerLayout_banner_enabledColor, ContextCompat.getColor(context, BannerDefaults.ENABLED_COLOR))
        normalRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_normalRadius, BannerDefaults.NORMAL_RADIUS)
        normalColor = typedArray.getColor(R.styleable.BannerLayout_banner_normalColor, ContextCompat.getColor(context, BannerDefaults.NORMAL_COLOR))
        delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, BannerDefaults.DELAY_TIME).toLong()
        isStartRotation = typedArray.getBoolean(R.styleable.BannerLayout_banner_start_rotation, BannerDefaults.IS_START_ROTATION)
        viewPagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, BannerDefaults.VIEW_PAGER_TOUCH_MODE)
        errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_error_image, BannerDefaults.GLIDE_ERROR_IMAGE)
        placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_place_image, BannerDefaults.GLIDE_PIACE_IMAGE)
        bannerDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, BannerDefaults.BANNER_DURATION)
        isVertical = typedArray.getBoolean(R.styleable.BannerLayout_banner_isVertical, BannerDefaults.IS_VERTICAL)
        isVisibleTitle = typedArray.getBoolean(R.styleable.BannerLayout_banner_title_visible, BannerDefaults.TITLE_VISIBLE)
        titleColor = typedArray.getColor(R.styleable.BannerLayout_banner_title_color, ContextCompat.getColor(context, BannerDefaults.TITLE_COLOR))
        titleSize = typedArray.getDimension(R.styleable.BannerLayout_banner_title_size, BannerDefaults.TITLE_SIZE)
        titleRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_right_margin, BannerDefaults.TITLE_RIGHT_MARGIN)
        titleLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_title_left_margin, BannerDefaults.TITLE_LEFT_MARGIN)
        titleWidth = typedArray.getInteger(R.styleable.BannerLayout_banner_title_width, BannerDefaults.TITLE_WIDTH)
        titleHeight = typedArray.getInteger(R.styleable.BannerLayout_banner_title_height, BannerDefaults.TITLE_HEIGHT)
        tipsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_tips_site, BOTTOM)
        dotsSite = typedArray.getInteger(R.styleable.BannerLayout_banner_dots_site, RIGHT)
        titleSite = typedArray.getInteger(R.styleable.BannerLayout_banner_title_site, LEFT)
        pageNumViewSite = typedArray.getInteger(R.styleable.BannerLayout_banner_pageNum_site, PAGE_NUM_VIEW_TOP_RIGHT)
        pageNumViewRadius = typedArray.getFloat(R.styleable.BannerLayout_banner_page_num_radius, BannerDefaults.PAGE_NUM_VIEW_RADIUS)
        pageNumViewPaddingTop = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingTop, BannerDefaults.PAGE_NUM_VIEW_PADDING_TOP)
        pageNumViewPaddingLeft = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingLeft, BannerDefaults.PAGE_NUM_VIEW_PADDING_LEFT)
        pageNumViewPaddingBottom = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingBottom, BannerDefaults.PAGE_NUM_VIEW_PADDING_BOTTOM)
        pageNumViewPaddingRight = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_paddingRight, BannerDefaults.PAGE_NUM_VIEW_PADDING_RIGHT)
        pageNumViewTopMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginTop, BannerDefaults.PAGE_NUM_VIEW_TOP_MARGIN)
        pageNumViewRightMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginRight, BannerDefaults.PAGE_NUM_VIEW_RIGHT_MARGIN)
        pageNumViewBottomMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginBottom, BannerDefaults.PAGE_NUM_VIEW_BOTTOM_MARGIN)
        pageNumViewLeftMargin = typedArray.getInteger(R.styleable.BannerLayout_banner_page_num_marginLeft, BannerDefaults.PAGE_NUM_VIEW_LEFT_MARGIN)
        pageNumViewTextColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_textColor, ContextCompat.getColor(context, BannerDefaults.PAGE_NUL_VIEW_TEXT_COLOR))
        pageNumViewBackgroundColor = typedArray.getColor(R.styleable.BannerLayout_banner_page_num_BackgroundColor, ContextCompat.getColor(context, BannerDefaults.PAGE_NUM_VIEW_BACKGROUND))
        pageNumViewTextSize = typedArray.getDimension(R.styleable.BannerLayout_banner_page_num_textSize, BannerDefaults.PAGE_NUM_VIEW_SIZE)
        pageNumViewMark = typedArray.getString(R.styleable.BannerLayout_banner_page_num_mark) ?: BannerDefaults.PAGE_NUM_VIEW_MARK
        typedArray.recycle()
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    override fun setCurrentItem(page: Int) {
        viewPager.currentItem = page
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onBannerChangeListener.onPageScrolled(position % dotsSize, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        val newPosition = position % dotsSize
        pageView?.text = TextUtils.concat((newPosition + 1).toString(), pageNumViewMark, dotsSize.toString())
        if (isVisibleDots) {
            tipLayout?.changeDotsPosition(preEnablePosition, newPosition)
        }
        if (isVisibleTitle) {
            tipLayout?.setTitle(imageList[newPosition].bannerTitle)
        }
        preEnablePosition = newPosition
        if (!transformerList.isEmpty() && !isVertical) {
            viewPager.setPageTransformer(true, transformerList[(Math.random() * transformerList.size).toInt()])
        }
        handler.sendMessage(Message.obtain(handler, BannerHandlerUtils.MSG_PAGE, viewPager.currentItem, 0))
        onBannerChangeListener.onPageSelected(newPosition)
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (isStartRotation) {
            removeHandler()
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> handler.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> handler.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime)
            }
        }
        onBannerChangeListener.onPageScrollStateChanged(state)
    }

    override fun onBannerClick(view: View, position: Int, model: BannerModelCallBack) {
        if (onBannerClickListener == null) return
        @Suppress("UNCHECKED_CAST")
        (onBannerClickListener as OnBannerClickListener<BannerModelCallBack>).onBannerClick(view, position, model)
    }

    fun initPageNumView() = apply { pageView = BannerPageView(context) }

    fun initTips() = apply { tipLayout = BannerTipsLayout(context) }

    fun resource(imageList: MutableList<out BannerModelCallBack>) = apply {
        this.imageList = imageList
        initBannerMethod()
    }

    fun switchBanner(isStartRotation: Boolean) = apply {
        this.isStartRotation = isStartRotation
        removeHandler()
        if (isStartRotation) {
            handler.handlerDelayTime = delayTime
            handler.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime)
        } else {
            handler.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP)
            removeHandler()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initBannerMethod() = apply {
        removeAllViews()
        preEnablePosition = 0
        val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dotsSize
        viewPager.apply {
            viewTouchMode = viewPagerTouchMode
            addOnPageChangeListener(this@BannerLayout)
            setDuration(bannerDuration)
            adapter = BannerAdapter(imageList, imageLoaderManager as ImageLoaderManager<BannerModelCallBack>, isGuide).apply { imageClickListener = this@BannerLayout }
        }.currentItem = currentItem
        if (isVertical) {
            viewPager.isVertical = true
            viewPager.setPageTransformer(true, VerticalTransformer())
        } else {
            viewPager.setPageTransformer(true, bannerTransformer)
        }
        addView(viewPager)
        handler.apply {
            handlerPage = currentItem
            handlerDelayTime = delayTime
        }
        switchBanner(isStartRotation)
        pageView?.text = TextUtils.concat(1.toString(), pageNumViewMark, dotsSize.toString())
        if (pageView != null) {
            addView(pageView, pageView?.run {
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
            })
        }
        if (tipLayout != null) {
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

                viewDotsCount = dotsSize
                viewDotsHeight = dotsHeight
                viewDotsWidth = dotsWidth
                viewDotsLeftMargin = dotsLeftMargin
                viewDotsRightMargin = dotsRightMargin
                viewDotsSite = dotsSite

                if (isVisibleDots) {
                    initDots(this@BannerLayout)
                }
                if (isVisibleTitle) {
                    initTitle()
                    setTitle(imageList[0].bannerTitle)
                }
                initTips()
            })
        }
    }

    fun dotsSelector(): Drawable {
        return if (dotsSelector == BannerDefaults.DOTS_SELECTOR)
            BannerSelectorUtils.getDrawableSelector(
                    enabledRadius,
                    enabledColor,
                    normalRadius,
                    normalColor)
        else
            ContextCompat.getDrawable(context, dotsSelector)
                    ?: BannerSelectorUtils.getDrawableSelector(
                            enabledRadius,
                            enabledColor,
                            normalRadius,
                            normalColor)
    }

    fun removeHandler() = handler.removeCallbacksAndMessages(null)

    fun getImageList(): List<BannerModelCallBack> = imageList

    private fun initImageLoaderManager(): ImageLoaderManager<BannerModelCallBack> {
        val requestOptions = RequestOptions()
                .placeholder(placeImageView)
                .error(errorImageView)
                .centerCrop()
        return object : ImageLoaderManager<BannerModelCallBack> {
            override fun display(container: ViewGroup, model: BannerModelCallBack): ImageView {
                val imageView = ImageView(container.context)
                Glide.with(imageView.context).load(model.bannerUrl).apply(requestOptions).into(imageView)
                return imageView
            }
        }
    }

    companion object {

        const val MATCH_PARENT = FrameLayout.LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = FrameLayout.LayoutParams.WRAP_CONTENT

        /**
         * animation type
         */
        const val ANIMATION_ACCORDION = 0
        const val ANIMATION_BACKGROUND = 1
        const val ANIMATION_CUBE_IN = 2
        const val ANIMATION_CUBE_OUT = 3
        const val ANIMATION_DEFAULT = 4
        const val ANIMATION_DEPTH_PAGE = 5
        const val ANIMATION_FLIPHORIZONTAL = 6
        const val ANIMATION_FLIPVERTICAL = 7
        const val ANIMATION_FOREGROUND = 8
        const val ANIMATION_ROTATEDOWN = 9
        const val ANIMATION_ROTATEUP = 10
        const val ANIMATION_STACK = 11
        const val ANIMATION_SCALEINOUT = 12
        const val ANIMATION_TABLET = 13
        const val ANIMATION_ZOOMIN = 14
        const val ANIMATION_ZOOMOUTPAGE = 15
        const val ANIMATION_ZOOMOUTSLIDE = 16
        const val ANIMATION_ZOOMOUT = 17
        const val ANIMATION_DRAWER = 18
        /**
         * pageNumberView site type
         */
        const val PAGE_NUM_VIEW_TOP_LEFT = 0
        const val PAGE_NUM_VIEW_TOP_RIGHT = 1
        const val PAGE_NUM_VIEW_BOTTOM_LEFT = 2
        const val PAGE_NUM_VIEW_BOTTOM_RIGHT = 3
        const val PAGE_NUM_VIEW_CENTER_LEFT = 4
        const val PAGE_NUM_VIEW_CENTER_RIGHT = 5
        const val PAGE_NUM_VIEW_TOP_CENTER = 6
        const val PAGE_NUM_VIEW_BOTTOM_CENTER = 7
        /**
         * tipsLayout location marker
         */
        const val LEFT = 9
        const val TOP = 10
        const val RIGHT = 11
        const val BOTTOM = 12
        const val CENTER = 13
    }

    private val dotsSize: Int get() = imageList.size
    val duration: Int get() = viewPager.duration
    val bannerStatus: Int get() = handler.status
}
