package com.bannerlayout.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Message
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bannerlayout.R
import com.bannerlayout.animation.BannerTransformer
import com.bannerlayout.annotation.AnimationMode
import com.bannerlayout.annotation.DotsAndTitleSiteMode
import com.bannerlayout.annotation.PageNumViewSiteMode
import com.bannerlayout.annotation.TipsSiteMode
import com.bannerlayout.listener.*
import com.bannerlayout.util.BannerHandlerUtils
import com.bannerlayout.util.BannerSelectorUtils
import com.bannerlayout.util.TransformerUtils
import com.bannerlayout.widget.BannerTipsLayout.DotsInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * by y on 2016/10/24
 */
class BannerLayout : FrameLayout,
        ViewPagerCurrent,
        ViewPager.OnPageChangeListener,
        OnBannerImageClickListener<Any>,
        DotsInterface,
        BannerTipsLayout.TitleInterface,
        BannerTipsLayout.TipsInterface,
        BannerPageView.PageNumViewInterface {

    private lateinit var adapter: BannerAdapter
    private var viewPager: BannerViewPager = BannerViewPager(context)
    private var handler: BannerHandlerUtils = BannerHandlerUtils(this)
    private var imageLoaderManage: ImageLoaderManager<Any> = initImageLoaderManager()
    private var imageList: List<BannerModelCallBack<Any>> = ArrayList()
    private var transformerList: List<BannerTransformer> = ArrayList()
    private var bannerTransformer: BannerTransformer? = null
    private var onBannerChangeListener: OnBannerChangeListener? = null
    private var onBannerClickListener: OnBannerClickListener<Any>? = null

    private var pageView: BannerPageView? = null
    private var tipLayout: BannerTipsLayout? = null

    private var enabledRadius: Float = 0.toFloat()
    private var normalRadius: Float = 0.toFloat()
    private var enabledColor: Int = 0
    private var normalColor: Int = 0
    private var preEnablePosition = 0
    private var isGuide: Boolean = false
    private var isStartRotation: Boolean = false
    private var viePagerTouchMode: Boolean = false
    private var errorImageView: Int = 0
    private var placeImageView: Int = 0
    private var mDuration: Int = 0
    private var isVertical: Boolean = false

    private var isVisibleDots: Boolean = false
    private var dotsWidth: Int = 0
    private var dotsHeight: Int = 0
    private var dotsSelector: Int = 0
    private var delayTime: Long = 0
    private var dotsLeftMargin: Int = 0
    private var dotsRightMargin: Int = 0
    private var dotsSite: Int = 0


    override var tipsHeight: Int = 0
    override var tipsWidth: Int = 0
    override var tipsLayoutBackgroundColor: Int = 0
    override var tipsSite: Int = 0
    override var showBackgroundColor: Boolean = false

    private var isVisibleTitle: Boolean = false
    override var titleSize: Float = 0.toFloat()
    override var titleColor: Int = 0
    override var titleLeftMargin: Int = 0
    override var titleRightMargin: Int = 0
    override var titleWidth: Int = 0
    override var titleHeight: Int = 0
    override var titleSite: Int = 0

    override var pageNumViewRadius: Float = 0.toFloat()
    override var pageNumViewPaddingTop: Int = 0
    override var pageNumViewPaddingLeft: Int = 0
    override var pageNumViewPaddingBottom: Int = 0
    override var pageNumViewPaddingRight: Int = 0
    override var pageNumViewTopMargin: Int = 0
    override var pageNumViewRightMargin: Int = 0
    override var pageNumViewBottomMargin: Int = 0
    override var pageNumViewLeftMargin: Int = 0
    override var pageNumViewSite: Int = 0
    override var pageNumViewTextColor: Int = 0
    override var pageNumViewBackgroundColor: Int = 0
    override var pageNumViewTextSize: Float = 0.toFloat()
    private lateinit var pageNumViewMark: String

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, BannerDefaults.IS_GUIDE)
        showBackgroundColor = typedArray.getBoolean(R.styleable.BannerLayout_banner_is_tips_background, BannerDefaults.IS_TIPS_LAYOUT_BACKGROUND)
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
        viePagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, BannerDefaults.VIEW_PAGER_TOUCH_MODE)
        errorImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_error_image, BannerDefaults.GLIDE_ERROR_IMAGE)
        placeImageView = typedArray.getResourceId(R.styleable.BannerLayout_banner_glide_place_image, BannerDefaults.GLIDE_PIACE_IMAGE)
        mDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, BannerDefaults.BANNER_DURATION)
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
        onBannerChangeListener?.onPageScrolled(position % dotsSize, positionOffset, positionOffsetPixels)
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
        onBannerChangeListener?.onPageSelected(newPosition)
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (isStartRotation) {
            handler.removeCallbacksAndMessages(null)
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> handler.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> handler.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime)
            }
        }
        onBannerChangeListener?.onPageScrollStateChanged(state)
    }

    override fun onBannerClick(view: View, position: Int, model: Any) {
        onBannerClickListener?.onBannerClick(view, position, model)
    }

    @Suppress("UNCHECKED_CAST")
    fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener<*>) = apply { this.onBannerClickListener = onBannerClickListener as? OnBannerClickListener<Any> }

    fun addOnPageChangeListener(onBannerChangeListener: OnBannerChangeListener) = apply { this.onBannerChangeListener = onBannerChangeListener }
    fun setGuide(guide: Boolean) = apply { this.isGuide = guide }
    fun initPageNumView() = apply { pageView = BannerPageView(context) }
    fun initTips() = apply { initTips(showBackgroundColor, isVisibleDots, isVisibleTitle) }
    fun initTips(isBackgroundColor: Boolean, isVisibleDots: Boolean, isVisibleTitle: Boolean) = apply {
        this.showBackgroundColor = isBackgroundColor
        this.isVisibleDots = isVisibleDots
        this.isVisibleTitle = isVisibleTitle
        tipLayout = BannerTipsLayout(context)
    }

    fun initListResources(imageList: MutableList<out BannerModelCallBack<Any>>) = apply {
        this.imageList = imageList
        initBannerMethod()
    }

    fun setDelayTime(time: Int) = apply { this.delayTime = time.toLong() }
    fun switchBanner(isStartRotation: Boolean) = apply {
        this.isStartRotation = isStartRotation
        handler.removeCallbacksAndMessages(null)
        if (isStartRotation) {
            handler.setDelayTime(delayTime)
            handler.sendEmptyMessageDelayed(BannerHandlerUtils.MSG_UPDATE, delayTime)
        } else {
            handler.sendEmptyMessage(BannerHandlerUtils.MSG_KEEP)
            handler.removeCallbacksAndMessages(null)
        }
    }

    fun setErrorImageView(@DrawableRes errorImageView: Int) = apply { this.errorImageView = errorImageView }
    fun setPlaceImageView(@DrawableRes placeImageView: Int) = apply { this.placeImageView = placeImageView }
    fun setDuration(pace: Int) = apply { this.mDuration = pace }
    fun setViewPagerTouchMode(b: Boolean) = apply { this.viePagerTouchMode = b }
    fun setVertical(vertical: Boolean) = apply { this.isVertical = vertical }
    fun setTipsBackgroundColor(@ColorInt colorId: Int) = apply { this.tipsLayoutBackgroundColor = colorId }
    fun setTipsDotsSelector(@DrawableRes dotsSelector: Int) = apply { this.dotsSelector = dotsSelector }
    fun setTipsWidthAndHeight(width: Int, height: Int) = apply {
        this.tipsHeight = height
        this.tipsWidth = width
    }

    fun setTipsSite(@TipsSiteMode tipsSite: Int) = apply { this.tipsSite = tipsSite }
    fun setTitleTextColor(@ColorInt titleColor: Int) = apply { this.titleColor = titleColor }
    fun setTitleTextSize(titleSize: Float) = apply { this.titleSize = titleSize }
    fun setTitleMargin(leftMargin: Int, rightMargin: Int) = apply {
        this.titleLeftMargin = leftMargin
        this.titleRightMargin = rightMargin
    }

    fun setTitleMargin(margin: Int) = apply {
        this.titleLeftMargin = margin
        this.titleRightMargin = margin
    }

    fun setTitleSite(@DotsAndTitleSiteMode titleSite: Int) = apply { this.titleSite = titleSite }
    fun setDotsWidthAndHeight(width: Int, height: Int) = apply {
        this.dotsWidth = width
        this.dotsHeight = height
    }

    fun setDotsSite(@DotsAndTitleSiteMode dotsSite: Int) = apply { this.dotsSite = dotsSite }
    fun setDotsMargin(leftMargin: Int, rightMargin: Int) = apply {
        this.dotsLeftMargin = leftMargin
        this.dotsRightMargin = rightMargin
    }

    fun setDotsMargin(margin: Int) = apply {
        this.dotsLeftMargin = margin
        this.dotsRightMargin = margin
    }

    fun setNormalRadius(normalRadius: Float) = apply { this.normalRadius = normalRadius }
    fun setNormalColor(@ColorInt normalColor: Int) = apply { this.normalColor = normalColor }
    fun setEnabledColor(@ColorInt enabledColor: Int) = apply { this.enabledColor = enabledColor }
    fun setEnabledRadius(enabledRadius: Float) = apply { this.enabledRadius = enabledRadius }
    fun setBannerTransformer(@AnimationMode type: Int) = apply { setBannerTransformer(TransformerUtils.getTransformer(type)) }
    fun setBannerTransformer(bannerTransformer: BannerTransformer) = apply {
        this.bannerTransformer = bannerTransformer
        viewPager.setPageTransformer(true, bannerTransformer)
    }

    fun setBannerSystemTransformerList(list: MutableList<Int>) = apply {
        val bannerTransformers = ArrayList<BannerTransformer>()
        val size = list.size
        for (i in 0 until size) {
            bannerTransformers.add(TransformerUtils.getTransformer(list[i]))
        }
        setBannerTransformerList(bannerTransformers)
    }

    fun setBannerTransformerList(list: MutableList<BannerTransformer>) = apply { this.transformerList = list }
    fun setPageNumViewRadius(pageNumViewRadius: Float) = apply { this.pageNumViewRadius = pageNumViewRadius }
    fun setPageNumViewPadding(top: Int, bottom: Int, left: Int, right: Int) = apply {
        this.pageNumViewPaddingTop = top
        this.pageNumViewPaddingBottom = bottom
        this.pageNumViewPaddingLeft = left
        this.pageNumViewPaddingRight = right
    }

    fun setPageNumViewPadding(padding: Int) = apply {
        this.pageNumViewPaddingTop = padding
        this.pageNumViewPaddingBottom = padding
        this.pageNumViewPaddingLeft = padding
        this.pageNumViewPaddingRight = padding
    }

    fun setPageNumViewMargin(top: Int, bottom: Int, left: Int, right: Int) = apply {
        this.pageNumViewTopMargin = top
        this.pageNumViewBottomMargin = bottom
        this.pageNumViewLeftMargin = left
        this.pageNumViewRightMargin = right
    }

    fun setPageNumViewMargin(margin: Int) = apply {
        this.pageNumViewTopMargin = margin
        this.pageNumViewBottomMargin = margin
        this.pageNumViewLeftMargin = margin
        this.pageNumViewRightMargin = margin
    }

    fun setPageNumViewTextColor(@ColorInt pageNumViewTextColor: Int) = apply { this.pageNumViewTextColor = pageNumViewTextColor }
    fun setPageNumViewBackgroundColor(@ColorInt pageNumViewBackgroundColor: Int) = apply { this.pageNumViewBackgroundColor = pageNumViewBackgroundColor }
    fun setPageNumViewTextSize(pageNumViewTextSize: Float) = apply { this.pageNumViewTextSize = pageNumViewTextSize }
    fun setPageNumViewSite(@PageNumViewSiteMode pageNumViewSite: Int) = apply { this.pageNumViewSite = pageNumViewSite }
    fun setPageNumViewMark(pageNumViewMark: String) = apply { this.pageNumViewMark = pageNumViewMark }
    fun setPageNumViewMark(@StringRes pageNumViewMark: Int) = apply { this.pageNumViewMark = context.getString(pageNumViewMark) }
    @Suppress("UNCHECKED_CAST")
    fun setImageLoaderManager(loaderManage: ImageLoaderManager<*>) = apply { this.imageLoaderManage = loaderManage as ImageLoaderManager<Any> }

    private fun initBannerMethod() = apply {
        removeAllViews()
        preEnablePosition = 0
        adapter = BannerAdapter(imageList, imageLoaderManage, isGuide)
        adapter.setImageClickListener(this)
        viewPager.setDuration(mDuration).setViewTouchMode(viePagerTouchMode).addOnPageChangeListener(this)
        viewPager.adapter = adapter
        if (isVertical) {
            viewPager.setVertical(isVertical)
        } else {
            viewPager.setPageTransformer(true, bannerTransformer)
        }
        addView(viewPager)
        val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dotsSize
        viewPager.currentItem = currentItem
        handler.setPage(currentItem)
        handler.setDelayTime(delayTime)
        switchBanner(isStartRotation)
        if (pageView != null) {
            pageView?.text = TextUtils.concat(1.toString(), pageNumViewMark, dotsSize.toString())
            addView(pageView, pageView?.initPageView(this))
        }
        if (tipLayout != null) {
            tipLayout?.removeAllViews()
            if (isVisibleDots) {
                tipLayout?.setDots(this)
            }
            if (isVisibleTitle) {
                tipLayout?.setTitle(this)
                tipLayout?.setTitle(imageList[0].bannerTitle)
            }
            addView(tipLayout, tipLayout!!.setBannerTips(this))
        }
    }

    override fun dotsCount(): Int {
        return dotsSize
    }

    override fun dotsSelector(): Drawable {
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

    override fun dotsHeight(): Int {
        return dotsHeight
    }

    override fun dotsWidth(): Int {
        return dotsWidth
    }

    override fun dotsLeftMargin(): Int {
        return dotsLeftMargin
    }

    override fun dotsRightMargin(): Int {
        return dotsRightMargin
    }

    override fun dotsSite(): Int {
        return dotsSite
    }

    fun removeHandler() = apply {
        handler.removeCallbacksAndMessages(null)
    }

    fun getImageList(): List<BannerModelCallBack<Any>> {
        return imageList
    }

    private fun initImageLoaderManager(): ImageLoaderManager<Any> {
        val requestOptions = RequestOptions()
                .placeholder(placeImageView)
                .error(errorImageView)
                .centerCrop()
        return object : ImageLoaderManager<Any> {
            override fun display(container: ViewGroup, model: Any): ImageView {
                val bannerModelCallBack = model as BannerModelCallBack<*>
                val imageView = ImageView(container.context)
                Glide.with(imageView.context).load(bannerModelCallBack.bannerUrl).apply(requestOptions).into(imageView)
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
