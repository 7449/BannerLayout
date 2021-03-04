package com.android.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.android.banner.run.BannerHandler
import com.android.banner.transformer.BannerTransformer
import com.android.banner.viewpager.BannerAdapter
import com.android.banner.viewpager.BannerViewPager

class BannerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    companion object {
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    }

    private val bannerHandler: BannerHandler = BannerHandler {
        viewPager.currentItem = it
    }
    private val viewPager: BannerViewPager = BannerViewPager(context)
    private val onBannerChangeListener: ArrayList<OnBannerChangeListener> = arrayListOf()
    private val onBannerClickListener: ArrayList<OnBannerClickListener<BannerInfo>> = arrayListOf()
    private val onBannerResourceChangedListener: ArrayList<OnBannerResourceChangedListener> = arrayListOf()
    private val imageList: MutableList<BannerInfo> = mutableListOf()
    private var onBannerImageLoader: OnBannerImageLoader<BannerInfo>? = null

    private var isGuide: Boolean = false
    private var isPlay: Boolean = false
    private var touchMode: Boolean = false
    private var duration: Int = 0
    private var delayTime: Int = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, false)
        delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000)
        touchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, false)
        duration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, 800)
        typedArray.recycle()
        viewPager.addOnPageChangeListener(this)
        addView(viewPager, 0)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onBannerChangeListener.forEach { it.onPageScrolled(position % itemCount, positionOffset, positionOffsetPixels) }
    }

    override fun onPageSelected(position: Int) {
        onBannerChangeListener.forEach { it.onPageSelected(position % itemCount) }
        bannerHandler.sendMessageSelected(viewPager.currentItem)
    }

    override fun onPageScrollStateChanged(state: Int) {
        onBannerChangeListener.forEach { it.onPageScrollStateChanged(state) }
        if (!isPlay) {
            return
        }
        release()
        when (state) {
            ViewPager.SCROLL_STATE_DRAGGING -> bannerHandler.sendKeepMessage()
            ViewPager.SCROLL_STATE_IDLE -> bannerHandler.sendUpdateMessage(delayTime)
        }
    }

    fun resource(imageList: MutableList<out BannerInfo>, isPlay: Boolean = true) = also {
        if (imageList.isEmpty()) {
            return@also
        }
        this.imageList.clear()
        this.imageList.addAll(imageList)
        removeView(viewPager)
        val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % itemCount
        viewPager.viewTouchMode = touchMode
        viewPager.setDuration(duration)
        viewPager.adapter = BannerAdapter(imageList, onBannerImageLoader
                ?: throw KotlinNullPointerException("onBannerImageLoader == null"), onBannerClickListener, isGuide)
        viewPager.currentItem = currentItem
        bannerHandler.updateCurrentPage(currentItem)
        addView(viewPager, 0)
        onBannerResourceChangedListener.forEach { it.onBannerDataChanged() }
        if (isGuide) {
            return@also
        }
        play(isPlay)
    }

    fun start() = also { play(true) }

    fun stop() = also { play(false) }

    fun play(isPlay: Boolean) = also {
        release()
        this.isPlay = isPlay
        if (isPlay) {
            bannerHandler.sendUpdateMessage(delayTime)
        } else {
            bannerHandler.sendKeepMessage()
            release()
        }
    }

    fun addOnBannerChangeListener(onBannerChangeListener: OnBannerChangeListener) = also {
        this.onBannerChangeListener.add(onBannerChangeListener)
    }

    fun addOnBannerResourceChangedListener(onBannerResourceChangedListener: OnBannerResourceChangedListener) = also {
        this.onBannerResourceChangedListener.add(onBannerResourceChangedListener)
    }

    fun addOnBannerClickListener(onBannerClickListener: OnBannerClickListener<BannerInfo>) = also {
        this.onBannerClickListener.add(onBannerClickListener)
    }

    /** [resource]之前调用 */
    @Suppress("UNCHECKED_CAST")
    fun setOnBannerImageLoader(onBannerImageLoader: OnBannerImageLoader<out BannerInfo>) = also {
        this.onBannerImageLoader = onBannerImageLoader as OnBannerImageLoader<BannerInfo>
    }

    /** [resource]之前调用 */
    fun delayTime(delayTime: Int) = also {
        this.delayTime = delayTime
    }

    /** [resource]之前调用 */
    fun touchMode(touchMode: Boolean) = also {
        this.touchMode = touchMode
    }

    /** [resource]之前调用 */
    fun duration(duration: Int) = also {
        this.duration = duration
    }

    fun setTransformer(bannerTransformer: BannerTransformer) = also {
        viewPager.setPageTransformer(true, bannerTransformer)
    }

    fun isVertical(isVertical: Boolean) = also {
        viewPager.isVertical = isVertical
    }

    fun beginFakeDrag() = also {
        viewPager.beginFakeDrag()
    }

    fun endFakeDrag() = also {
        viewPager.endFakeDrag()
    }

    fun fakeDragBy(xOffset: Float) {
        viewPager.fakeDragBy(xOffset)
    }

    fun setOffscreenPageLimit(offscreenPageLimit: Int) = also {
        viewPager.offscreenPageLimit = offscreenPageLimit
    }

    fun viewPagerLayoutParams(): LayoutParams? {
        return viewPager.layoutParams as LayoutParams?
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BannerInfo> getItem(position: Int): T {
        return imageList[position] as T
    }

    fun release() {
        bannerHandler.release()
    }

    val itemCount: Int
        get() = imageList.size

    fun checkViewPager(): Boolean {
        for (index in 0 until childCount) {
            if (getChildAt(index) is BannerViewPager) {
                return true
            }
        }
        return false
    }

}
