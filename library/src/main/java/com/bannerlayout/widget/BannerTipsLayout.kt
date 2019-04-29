package com.bannerlayout.widget

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bannerlayout.dotsSelector

internal class BannerTipsLayout(context: Context) : RelativeLayout(context) {

    private var textView: TextView = TextView(context)
    private var linearLayout: LinearLayout = LinearLayout(context)

    var viewTitleColor: Int = 0
    var viewTitleSize: Float = 0F
    var viewTitleLeftMargin: Int = 0
    var viewTitleRightMargin: Int = 0
    var viewTitleWidth: Int = 0
    var viewTitleHeight: Int = 0
    var viewTitleSite: Int = 0

    var showViewTipsBackgroundColor: Boolean = false
    var viewTipsSite: Int = 0
    var viewTipsWidth: Int = 0
    var viewTipsHeight: Int = 0
    var viewTipsLayoutBackgroundColor: Int = 0

    var viewDotsCount: Int = 0
    var viewDotsHeight: Int = 0
    var viewDotsWidth: Int = 0
    var viewDotsLeftMargin: Int = 0
    var viewDotsRightMargin: Int = 0
    var viewDotsSite: Int = 0

    fun initDots(bannerLayout: BannerLayout) {
        linearLayout.removeAllViews()
        for (i in 0 until viewDotsCount) {
            val view = View(context)
            view.setBackgroundDrawable(bannerLayout.dotsSelector())
            view.isEnabled = i == 0
            val params = LinearLayout.LayoutParams(viewDotsWidth, viewDotsHeight)
            view.layoutParams = params
            params.leftMargin = viewDotsLeftMargin
            params.rightMargin = viewDotsRightMargin
            linearLayout.addView(view)
        }
        val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_VERTICAL)
        params.addRule(viewDotsSite)
        addView(linearLayout, params)
    }

    fun initTips(): FrameLayout.LayoutParams {
        val tipsParams = FrameLayout.LayoutParams(viewTipsWidth, viewTipsHeight)
        when (viewTipsSite) {
            BannerLayout.BANNER_TIPS_BOTTOM -> tipsParams.gravity = Gravity.BOTTOM
            BannerLayout.BANNER_TIPS_TOP -> tipsParams.gravity = Gravity.TOP
            BannerLayout.BANNER_TIPS_CENTER -> tipsParams.gravity = Gravity.CENTER
        }
        if (showViewTipsBackgroundColor) {
            setBackgroundColor(viewTipsLayoutBackgroundColor)
        }
        return tipsParams
    }

    fun changeDotsPosition(position: Int, newPosition: Int) {
        val childAt = linearLayout.getChildAt(position)
        val newChildAt = linearLayout.getChildAt(newPosition)
        if (childAt != null) {
            childAt.isEnabled = false
        }
        if (newChildAt != null) {
            newChildAt.isEnabled = true
        }
    }

    fun initTitle() {
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.setTextColor(viewTitleColor)
        textView.textSize = viewTitleSize
        textView.setSingleLine(true)
        textView.ellipsize = TextUtils.TruncateAt.END
        val params = LayoutParams(viewTitleWidth, viewTitleHeight)
        params.addRule(CENTER_VERTICAL)
        params.leftMargin = viewTitleLeftMargin
        params.rightMargin = viewTitleRightMargin
        params.addRule(viewTitleSite)
        addView(textView, params)
    }

    fun setTitle(title: String?) {
        clearText()
        if (!TextUtils.isEmpty(title)) {
            textView.text = title
        }
    }

    private fun clearText() {
        textView.text = ""
    }
}
