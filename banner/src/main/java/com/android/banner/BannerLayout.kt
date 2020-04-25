package com.android.banner

import android.content.Context
import android.os.Message
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.android.banner.run.BannerHandler
import com.android.banner.transformer.Banner2Transformer
import com.android.banner.viewpager.Banner2Adapter

class BannerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    companion object {
        const val MSG_UPDATE = 1
        const val MSG_KEEP = 2
        const val MSG_PAGE = 3
        const val MATCH_PARENT = LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    }

    internal val bannerHandler: BannerHandler = BannerHandler(this)

    //    @Deprecated("==")
//    internal val viewPager: BannerViewPager = BannerViewPager(context)
    internal val viewPager2: ViewPager2 = ViewPager2(context)
    internal var imageList: List<BannerInfo> = ArrayList()

    var imageLoaderManager: ImageLoaderManager<out BannerInfo>? = null
    var onBannerClickListener: OnBannerClickListener<out BannerInfo>? = null
    val onBannerChangeListener: ArrayList<OnBannerChangeListener> = ArrayList()

    var bannerTransformer: Banner2Transformer? = null
        set(value) {
            field = value
//            viewPager.setPageTransformer(true) { page, position -> bannerTransformer?.transformPage(page, position) }
            viewPager2.setPageTransformer(value)
        }

    var offscreenPageLimit = 0
        set(value) {
            field = value
//            viewPager.offscreenPageLimit = offscreenPageLimit
            viewPager2.offscreenPageLimit = offscreenPageLimit
        }

    var isPlay: Boolean = false
    var isGuide: Boolean = false
    var viewPagerTouchMode: Boolean = false
    var bannerDuration: Int = 0
    var delayTime: Long = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout)
        isGuide = typedArray.getBoolean(R.styleable.BannerLayout_banner_guide, false)
        delayTime = typedArray.getInteger(R.styleable.BannerLayout_banner_delay_time, 2000).toLong()
        isPlay = typedArray.getBoolean(R.styleable.BannerLayout_banner_play, false)
        viewPagerTouchMode = typedArray.getBoolean(R.styleable.BannerLayout_banner_view_pager_touch_mode, false)
        bannerDuration = typedArray.getInteger(R.styleable.BannerLayout_banner_duration, 800)
        typedArray.recycle()
//        viewPager.addOnPageChangeListener(this)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                this@BannerLayout.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                this@BannerLayout.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                this@BannerLayout.onPageSelected(position)
            }
        })
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onBannerChangeListener.forEach { it.onPageScrolled(position % dotsSize(), positionOffset, positionOffsetPixels) }
    }

    override fun onPageSelected(position: Int) {
//        bannerHandler.sendMessage(Message.obtain(bannerHandler, MSG_PAGE, viewPager.currentItem, 0))
        bannerHandler.sendMessage(Message.obtain(bannerHandler, MSG_PAGE, viewPager2.currentItem, 0))
        onBannerChangeListener.forEach { it.onPageSelected(position % dotsSize()) }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (isPlay) {
            removeCallbacksAndMessages()
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> bannerHandler.sendEmptyMessage(MSG_KEEP)
//                ViewPager2.SCROLL_STATE_DRAGGING -> bannerHandler.sendEmptyMessage(MSG_KEEP)
                ViewPager.SCROLL_STATE_IDLE -> bannerHandler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
//                ViewPager2.SCROLL_STATE_IDLE -> bannerHandler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
            }
        }
        onBannerChangeListener.forEach { it.onPageScrollStateChanged(state) }
    }

    fun getItem(position: Int): BannerInfo = imageList[position]

    fun resource(imageList: ArrayList<out BannerInfo>, isPlay: Boolean = true) = also {
        if (imageList.isEmpty()) {
            return@also
        }
        removeAllViews()
        this.imageList = imageList
        val currentItem = if (isGuide) 0 else Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % dotsSize()

//        viewPager.viewTouchMode = viewPagerTouchMode
//        viewPager.setDuration(bannerDuration)
//        viewPager.adapter = BannerAdapter(imageList, imageLoaderManager, onBannerClickListener, isGuide)
//        viewPager.setPageTransformer(true) { page, position -> bannerTransformer?.transformPage(page, position) }
//        viewPager.currentItem = currentItem

        viewPager2.isUserInputEnabled = viewPagerTouchMode
        viewPager2.adapter = Banner2Adapter(imageList, imageLoaderManager, onBannerClickListener, isGuide)
        viewPager2.setPageTransformer(bannerTransformer)
        viewPager2.setCurrentItem(currentItem, false)

//        addView(viewPager)
        addView(viewPager2)
        bannerHandler.handlerPage = currentItem
        bannerHandler.handlerDelayTime = delayTime
        playBanner(if (isGuide) false else isPlay)
    }

    fun playBanner(isPlay: Boolean) = also {
        removeCallbacksAndMessages()
        this.isPlay = isPlay
        if (isPlay) {
            bannerHandler.handlerDelayTime = delayTime
            bannerHandler.sendEmptyMessageDelayed(MSG_UPDATE, delayTime)
        } else {
            bannerHandler.sendEmptyMessage(MSG_KEEP)
            removeCallbacksAndMessages()
        }
    }
}
