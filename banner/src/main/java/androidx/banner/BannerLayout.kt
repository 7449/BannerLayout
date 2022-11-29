@file:Suppress("UNCHECKED_CAST")

package androidx.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.banner.adapter.BannerAdapter
import androidx.banner.adapter.BannerViewPager
import androidx.banner.listener.*
import androidx.viewpager.widget.ViewPager

class BannerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

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

    private val handler = BannerHandler { viewPager.currentItem = ++viewPager.currentItem }
    private val viewPager = BannerViewPager(context)
    private val pageChangeListeners = arrayListOf<OnBannerPageChangeListener>()
    private val clickListeners = arrayListOf<OnBannerClickListener<BannerItem>>()
    private val resourceChangedListener = arrayListOf<OnBannerResourceChangedListener>()
    private val bannerItems = mutableListOf<BannerItem>()
    private var bannerImageLoader: OnBannerImageLoader<BannerItem>? = null

    private var isPlay: Boolean = false

    private var touchMode: Boolean = false
    private var duration: Int = 0
    private var delayTime: Int = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000)
        touchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_touch_mode, false)
        duration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, 800)
        typedArray.recycle()
        viewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrolled(position: Int, offset: Float, pixels: Int) {
        pageChangeListeners.forEach { it.onBannerPageScrolled(position.pos, offset, pixels) }
    }

    override fun onPageSelected(position: Int) {
        pageChangeListeners.forEach { it.onBannerPageSelected(position.pos) }
    }

    override fun onPageScrollStateChanged(state: Int) {
        pageChangeListeners.forEach { it.onBannerPageScrollStateChanged(state) }
        if (!isPlay) return
        release()
        when (state) {
            ViewPager.SCROLL_STATE_DRAGGING -> handler.sendKeepMessage()
            ViewPager.SCROLL_STATE_IDLE -> handler.sendUpdateMessage(delayTime)
        }
    }

    fun resource(items: List<BannerItem>, isPlay: Boolean = true) = apply {
        if (items.isEmpty()) return@apply
        bannerItems.clear()
        bannerItems.addAll(items)
        removeView(viewPager)
        viewPager.viewTouchMode = touchMode
        viewPager.setDuration(duration)
        viewPager.adapter = BannerAdapter(items, requireNotNull(bannerImageLoader), clickListeners)
        viewPager.currentItem = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % bannerItems.size
        addView(viewPager, 0)
        play(isPlay)
        resourceChangedListener.forEach { it.onBannerDataChanged() }
    }

    private fun play(isPlay: Boolean) = apply {
        this.isPlay = isPlay
        release()
        when (isPlay) {
            true -> handler.sendUpdateMessage(delayTime)
            false -> handler.sendKeepMessage()
        }
    }

    fun addOnBannerChangeListener(listener: OnBannerPageChangeListener) = apply {
        if (!pageChangeListeners.contains(listener)) {
            pageChangeListeners.add(listener)
        }
    }

    fun removeOnBannerChangeListener(listener: OnBannerPageChangeListener) = apply {
        pageChangeListeners.remove(listener)
    }

    fun clearOnBannerChangeListener() = apply {
        pageChangeListeners.clear()
    }

    fun addOnBannerResourceChangedListener(listener: OnBannerResourceChangedListener) = apply {
        if (!resourceChangedListener.contains(listener)) {
            resourceChangedListener.add(listener)
        }
    }

    fun removeOnBannerResourceChangedListener(listener: OnBannerResourceChangedListener) = apply {
        resourceChangedListener.remove(listener)
    }

    fun clearOnBannerResourceChangedListener() = apply {
        resourceChangedListener.clear()
    }

    fun addOnBannerClickListener(listener: OnBannerClickListener<BannerItem>) = apply {
        if (!clickListeners.contains(listener)) {
            clickListeners.add(listener)
        }
    }

    fun removeOnBannerClickListener(listener: OnBannerClickListener<BannerItem>) = apply {
        clickListeners.remove(listener)
    }

    fun clearOnBannerClickListener() = apply {
        clickListeners.clear()
    }

    fun setOnBannerImageLoader(imageLoader: OnBannerImageLoader<out BannerItem>) = apply {
        bannerImageLoader = imageLoader as OnBannerImageLoader<BannerItem>
    }

    fun delayTime(delayTime: Int) = apply {
        this.delayTime = delayTime
    }

    fun touchMode(touchMode: Boolean) = apply {
        this.touchMode = touchMode
    }

    fun duration(duration: Int) = apply {
        this.duration = duration
    }

    fun clearAllListener() = apply {
        clearOnBannerClickListener()
        clearOnBannerChangeListener()
        clearOnBannerResourceChangedListener()
    }

    fun setTransformer(bannerTransformer: ViewPager.PageTransformer) = apply {
        viewPager.setPageTransformer(true, bannerTransformer)
    }

    fun isVertical(isVertical: Boolean) = apply {
        viewPager.isVertical = isVertical
    }

    fun start() {
        play(true)
    }

    fun stop() {
        play(false)
    }

    fun release() {
        handler.release()
    }

    fun <T : BannerItem> getItem(position: Int): T = bannerItems[position] as T

    fun <T : BannerItem> getItemOrNull(position: Int): T? = bannerItems.getOrNull(position) as? T

    val itemCount: Int
        get() = bannerItems.size

    val checkViewPager: Boolean
        get() = getChildAt(0) is BannerViewPager

    private val Int.pos get() = this % itemCount

}