package com.android.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.android.banner.viewpager.BannerAdapter
import com.android.banner.viewpager.BannerViewPager

class BannerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    companion object {
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
    }

    private class BannerHandler(private val action: () -> Unit) : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (hasMessages(MSG_UPDATE)) {
                removeMessages(MSG_UPDATE)
            }
            when (msg.what) {
                MSG_UPDATE -> {
                    action.invoke()
                    sendUpdateMessage(msg.arg1)
                }
            }
        }

        fun release() {
            removeCallbacksAndMessages(null)
        }

        fun sendKeepMessage() {
            sendEmptyMessage(MSG_KEEP)
        }

        fun sendUpdateMessage(delayTime: Int) {
            sendMessageDelayed(Message.obtain(this, MSG_UPDATE, delayTime, 0), delayTime.toLong())
        }

    }

    private val bannerHandler: BannerHandler = BannerHandler { viewPager.currentItem = ++viewPager.currentItem }
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

    fun resource(imageList: MutableList<out BannerInfo>, isPlay: Boolean = true) = apply {
        if (imageList.isEmpty()) {
            return@apply
        }
        this.imageList.clear()
        this.imageList.addAll(imageList)
        removeView(viewPager)
        viewPager.viewTouchMode = touchMode
        viewPager.setDuration(duration)
        viewPager.adapter = BannerAdapter(imageList, onBannerImageLoader
                ?: throw KotlinNullPointerException("onBannerImageLoader == null"), onBannerClickListener, isGuide)
        viewPager.currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % itemCount
        addView(viewPager, 0)
        onBannerResourceChangedListener.forEach { it.onBannerDataChanged() }
        if (isGuide) {
            return@apply
        }
        play(isPlay)
    }

    fun start() {
        play(true)
    }

    fun stop() {
        play(false)
    }

    fun play(isPlay: Boolean) {
        release()
        this.isPlay = isPlay
        if (isPlay) {
            bannerHandler.sendUpdateMessage(delayTime)
        } else {
            bannerHandler.sendKeepMessage()
            release()
        }
    }

    fun addOnBannerChangeListener(onBannerChangeListener: OnBannerChangeListener) {
        this.onBannerChangeListener.add(onBannerChangeListener)
    }

    fun addOnBannerResourceChangedListener(onBannerResourceChangedListener: OnBannerResourceChangedListener) {
        this.onBannerResourceChangedListener.add(onBannerResourceChangedListener)
    }

    fun addOnBannerClickListener(onBannerClickListener: OnBannerClickListener<BannerInfo>) {
        this.onBannerClickListener.add(onBannerClickListener)
    }

    /** [resource]之前调用 */
    @Suppress("UNCHECKED_CAST")
    fun setOnBannerImageLoader(onBannerImageLoader: OnBannerImageLoader<out BannerInfo>) = apply {
        this.onBannerImageLoader = onBannerImageLoader as OnBannerImageLoader<BannerInfo>
    }

    /** [resource]之前调用 */
    fun delayTime(delayTime: Int) = apply {
        this.delayTime = delayTime
    }

    /** [resource]之前调用 */
    fun touchMode(touchMode: Boolean) = apply {
        this.touchMode = touchMode
    }

    /** [resource]之前调用 */
    fun duration(duration: Int) = apply {
        this.duration = duration
    }

    fun setTransformer(bannerTransformer: BannerTransformer) = apply {
        viewPager.setPageTransformer(true, bannerTransformer)
    }

    fun isVertical(isVertical: Boolean) {
        viewPager.isVertical = isVertical
    }

    fun beginFakeDrag() {
        viewPager.beginFakeDrag()
    }

    fun endFakeDrag() {
        viewPager.endFakeDrag()
    }

    fun fakeDragBy(xOffset: Float) {
        viewPager.fakeDragBy(xOffset)
    }

    fun setOffscreenPageLimit(offscreenPageLimit: Int) {
        viewPager.offscreenPageLimit = offscreenPageLimit
    }

    fun viewPagerLayoutParams(): LayoutParams? = viewPager.layoutParams as LayoutParams?

    @Suppress("UNCHECKED_CAST")
    fun <T : BannerInfo> getItem(position: Int): T = imageList[position] as T

    fun release() {
        bannerHandler.release()
    }

    val itemCount: Int
        get() = imageList.size

    fun checkViewPager(): Boolean {
        if (getChildAt(0) is BannerViewPager) {
            return true
        }
        return false
    }

}