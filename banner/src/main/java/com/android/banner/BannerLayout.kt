@file:Suppress("MemberVisibilityCanBePrivate")

package com.android.banner

import android.content.Context
import android.os.Message
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.android.banner.run.BannerHandler
import com.android.banner.transformer.BannerTransformer
import com.android.banner.viewpager.BannerAdapter
import com.android.banner.viewpager.BannerViewPager

class BannerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    companion object {
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
        const val MSG_PAGE = 3
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    }

    private val bannerHandler: BannerHandler = BannerHandler(this)
    private val onBannerChangeListener: ArrayList<OnBannerChangeListener> = arrayListOf()
    private val onBannerClickListener: ArrayList<OnBannerClickListener<*>> = arrayListOf()
    private val onBannerResourceChangedListener: ArrayList<OnBannerResourceChangedListener> = arrayListOf()
    private val imageList: MutableList<BannerInfo> = mutableListOf()
    private var onBannerImageLoader: OnBannerImageLoader<*> = DEFAULT_IMAGE_LOADER
    private var isGuide: Boolean = false
    private var isPlay: Boolean = false
    private var touchMode: Boolean = false
    private var duration: Int = 0
    private var delayTime: Long = 0

    val viewPager: BannerViewPager = BannerViewPager(context)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, false)
        delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000).toLong()
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
        bannerHandler.sendMessage(Message.obtain(bannerHandler, MSG_PAGE, viewPager.currentItem, 0))
    }

    override fun onPageScrollStateChanged(state: Int) {
        onBannerChangeListener.forEach { it.onPageScrollStateChanged(state) }
        if (isPlay) {
            release()
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> bannerHandler.sendEmptyMessage(MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> bannerHandler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
            }
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
        viewPager.adapter = BannerAdapter(imageList, onBannerImageLoader, onBannerClickListener, isGuide)
        viewPager.currentItem = currentItem
        bannerHandler.handlerPage = currentItem
        bannerHandler.handlerDelayTime = delayTime
        addView(viewPager, 0)
        onBannerResourceChangedListener.forEach { it.onBannerDataChanged() }
        if (isGuide) {
            return@also
        }
        play(isPlay)
    }

    /** 启动轮播 */
    fun start() = also { play(true) }

    /** 停止轮播 */
    fun stop() = also { play(false) }

    fun play(isPlay: Boolean) = also {
        release()
        this.isPlay = isPlay
        if (isPlay) {
            bannerHandler.handlerDelayTime = delayTime
            bannerHandler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
        } else {
            bannerHandler.sendEmptyMessage(MSG_KEEP)
            release()
        }
    }

    fun addOnBannerChangeListener(onBannerChangeListener: OnBannerChangeListener) = also {
        this.onBannerChangeListener.add(onBannerChangeListener)
    }

    fun addOnBannerResourceChangedListener(onBannerResourceChangedListener: OnBannerResourceChangedListener) = also {
        this.onBannerResourceChangedListener.add(onBannerResourceChangedListener)
    }

    fun <T : BannerInfo> addOnBannerClickListener(onBannerClickListener: OnBannerClickListener<T>) = also {
        this.onBannerClickListener.add(onBannerClickListener)
    }

    fun <T : BannerInfo> setOnBannerImageLoader(onBannerImageLoader: OnBannerImageLoader<T>) = also {
        this.onBannerImageLoader = onBannerImageLoader
    }

    /** [resource]之前调用 */
    fun delayTime(delayTime: Long) = also {
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

    fun setOffscreenPageLimit(offscreenPageLimit: Int) = also {
        viewPager.offscreenPageLimit = offscreenPageLimit
    }

    fun viewPagerLayoutParams(): LayoutParams? {
        return viewPager.layoutParams as LayoutParams?
    }

    fun release() = bannerHandler.removeCallbacksAndMessages(null)

    /** [BannerInfo] */
    @Suppress("UNCHECKED_CAST")
    fun <T : BannerInfo> getItem(position: Int): T = imageList[position] as T

    /** data count */
    val itemCount: Int
        get() = imageList.size

    /** [MSG_KEEP],[MSG_PAGE],[MSG_UPDATE] */
    val status: Int
        get() = bannerHandler.status

    fun checkViewPager(): Boolean {
        for (index in 0 until childCount) {
            if (getChildAt(index) is BannerViewPager) {
                return true
            }
        }
        return false
    }
}
