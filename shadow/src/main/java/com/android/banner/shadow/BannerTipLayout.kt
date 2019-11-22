package com.android.banner.shadow

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class BannerTipLayout(context: Context) : RelativeLayout(context) {

    companion object {
        const val LEFT = 9
        const val TOP = 10
        const val RIGHT = 11
        const val BOTTOM = 12
        const val CENTER = 13
    }

    private val textView: TextView = TextView(context)
    private val linearLayout: LinearLayout = LinearLayout(context)

    internal fun initTip(
            tipSite: Int,
            tipWidth: Int,
            tipHeight: Int,
            tipLayoutBackgroundColor: Int
    ): FrameLayout.LayoutParams {
        val tipParams = FrameLayout.LayoutParams(tipWidth, tipHeight)
        when (tipSite) {
            BOTTOM -> tipParams.gravity = Gravity.BOTTOM
            TOP -> tipParams.gravity = Gravity.TOP
            CENTER -> tipParams.gravity = Gravity.CENTER
        }
        setBackgroundColor(tipLayoutBackgroundColor)
        return tipParams
    }

    @Suppress("DEPRECATION")
    internal fun initDot(
            dotCount: Int,
            dotHeight: Int,
            dotWidth: Int,
            dotLeftMargin: Int,
            dotRightMargin: Int,
            dotSite: Int,
            bannerTip: BannerTip) {
        linearLayout.removeAllViews()
        for (i in 0 until dotCount) {
            val view = View(context)
            view.setBackgroundDrawable(dotsSelector(bannerTip))
            view.isEnabled = i == 0
            val params = LinearLayout.LayoutParams(dotWidth, dotHeight)
            view.layoutParams = params
            params.leftMargin = dotLeftMargin
            params.rightMargin = dotRightMargin
            linearLayout.addView(view)
        }
        val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_VERTICAL)
        params.addRule(dotSite)
        addView(linearLayout, params)
    }

    internal fun changeDotsPosition(position: Int) {
        linearLayout.getChildAt(if (position == 0) linearLayout.childCount - 1 else position - 1)?.isEnabled = false
        linearLayout.getChildAt(position)?.isEnabled = true
    }

    internal fun initTitle(
            titleColor: Int,
            titleSize: Float,
            titleLeftMargin: Int,
            titleRightMargin: Int,
            titleWidth: Int,
            titleHeight: Int,
            titleSite: Int) {
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.setTextColor(titleColor)
        textView.textSize = titleSize
        textView.isSingleLine = true
        textView.ellipsize = TextUtils.TruncateAt.END
        val params = LayoutParams(titleWidth, titleHeight)
        params.addRule(CENTER_VERTICAL)
        params.leftMargin = titleLeftMargin
        params.rightMargin = titleRightMargin
        params.addRule(titleSite)
        addView(textView, params)
    }

    internal fun setTitle(title: String?) {
        clearText()
        if (!TextUtils.isEmpty(title)) {
            textView.text = title
        }
    }

    internal fun clearText() {
        textView.text = ""
    }
}
