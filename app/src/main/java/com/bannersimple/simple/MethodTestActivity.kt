package com.bannersimple.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.bannerlayout.listener.OnBannerChangeListener
import com.bannerlayout.listener.OnBannerClickListener
import com.bannerlayout.widget.BannerLayout
import com.bannersimple.R
import com.bannersimple.bean.SimpleBannerModel
import com.bannersimple.bean.SimpleData

/**
 * by y on 2017/5/28.
 */

class MethodTestActivity : AppCompatActivity(), OnBannerClickListener<SimpleBannerModel>, OnBannerChangeListener {

    private lateinit var xmlBannerLayout: BannerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_method_test)
        xmlBannerLayout = findViewById(R.id.method_banner)
        xmlBannerLayout.initListResources(SimpleData.initModel())

        //        BannerLayout newBannerLayout = new BannerLayout(getApplicationContext());
        //        bannerLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
        //        setContentView(bannerLayout);
        //        newBannerLayout
        //                .setDelayTime(3000)
        //                .setErrorImageView(R.mipmap.ic_launcher)
        //                .setPlaceImageView(R.mipmap.ic_launcher)
        //                .setDuration(3000)
        //                .setViewPagerTouchMode(true)
        //                .setVertical(true)
        //                .setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark))
        //                .setTitleTextSize(23)
        //                .setTipsBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
        //                .setTipsDotsSelector(R.drawable.banner)
        //                .setTipsWidthAndHeight(BannerLayout.MATCH_PARENT, 300)
        //                .setTipsSite(BannerLayout.TOP)
        //                .setTitleMargin(60, 20)
        //                .setTitleSite(BannerLayout.LEFT)
        //                .setDotsWidthAndHeight(30, 30)
        //                .setDotsSite(BannerLayout.RIGHT)
        //                .setNormalRadius(2)
        //                .setNormalColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYellow))
        //                .setEnabledRadius(2)
        //                .setEnabledColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYellow))
        //                .setDotsMargin(60, 60)
        //                .setBannerTransformer(BannerLayout.ANIMATION_ZOOMOUT)
        //                .setBannerTransformer(new ZoomOutSlideTransformer())
        //                .setPageNumViewRadius(1)
        //                .setPageNumViewMargin(10, 10, 10, 10)
        //                .setPageNumViewPadding(10, 10, 10, 10)
        //                .setPageNumViewMargin(10)
        //                .setPageNumViewPadding(10)
        //                .setPageNumViewTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorYellow))
        //                .setPageNumViewBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
        //                .setPageNumViewSite(BannerLayout.PAGE_NUM_VIEW_BOTTOM_CENTER)
        //                .setPageNumViewTextSize(22)
        //                .setPageNumViewMark(" & ")
        //                .initPageNumView()
        //                .initTips(true, true, true)
        //                .setOnBannerClickListener(this)
        //                .addOnPageChangeListener(this)
        //                .initListResources(SimpleData.initModel())
        //                .switchBanner(true);

    }

    override fun onBannerClick(view: View, position: Int, model: SimpleBannerModel) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
