package com.bannerlayout.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * by y on 2016/10/25
 */
class BannerTipsLayout : RelativeLayout {

    private var textView: TextView = TextView(context)
    private var linearLayout: LinearLayout = LinearLayout(context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    /**
     * Initialize the dots
     */
    fun setDots(dotsInterface: DotsInterface) {
        linearLayout.removeAllViews()
        for (i in 0 until dotsInterface.dotsCount()) {
            val view = View(context)
            view.setBackgroundDrawable(dotsInterface.dotsSelector())
            view.isEnabled = i == 0
            val params = LinearLayout.LayoutParams(dotsInterface.dotsWidth(), dotsInterface.dotsHeight())
            view.layoutParams = params
            params.leftMargin = dotsInterface.dotsLeftMargin()
            params.rightMargin = dotsInterface.dotsRightMargin()
            linearLayout.addView(view)
        }
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.CENTER_VERTICAL)
        params.addRule(dotsInterface.dotsSite())
        addView(linearLayout, params)
    }

    fun setBannerTips(tipsInterface: TipsInterface): FrameLayout.LayoutParams {
        val tipsParams = FrameLayout.LayoutParams(tipsInterface.tipsWidth, tipsInterface.tipsHeight)
        when (tipsInterface.tipsSite) {
            BannerLayout.BOTTOM -> tipsParams.gravity = Gravity.BOTTOM
            BannerLayout.TOP -> tipsParams.gravity = Gravity.TOP
            BannerLayout.CENTER -> tipsParams.gravity = Gravity.CENTER
        }
        if (tipsInterface.showBackgroundColor) {
            setBackgroundColor(tipsInterface.tipsLayoutBackgroundColor)
        }
        return tipsParams
    }

    /**
     * Update the dot position
     */
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


    /**
     * Update title, the default on the left
     */
    fun setTitle(titleInterface: TitleInterface) {
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.setTextColor(titleInterface.titleColor)
        textView.textSize = titleInterface.titleSize
        textView.setSingleLine(true)
        textView.ellipsize = TextUtils.TruncateAt.END
        val params = RelativeLayout.LayoutParams(titleInterface.titleWidth, titleInterface.titleHeight)
        params.addRule(RelativeLayout.CENTER_VERTICAL)
        params.leftMargin = titleInterface.titleLeftMargin
        params.rightMargin = titleInterface.titleRightMargin
        params.addRule(titleInterface.titleSite)
        addView(textView, params)
    }

    fun setTitle(title: String) {
        clearText()
        if (!TextUtils.isEmpty(title)) {
            textView.text = title
        }
    }

    private fun clearText() {
        textView.text = ""
    }

    interface TipsInterface {
        val showBackgroundColor: Boolean
        val tipsSite: Int
        val tipsWidth: Int
        val tipsHeight: Int
        val tipsLayoutBackgroundColor: Int
    }

    interface TitleInterface {
        val titleColor: Int
        val titleSize: Float
        val titleLeftMargin: Int
        val titleRightMargin: Int
        val titleWidth: Int
        val titleHeight: Int
        val titleSite: Int
    }

    interface DotsInterface {
        fun dotsCount(): Int

        fun dotsSelector(): Drawable

        fun dotsHeight(): Int

        fun dotsWidth(): Int

        fun dotsLeftMargin(): Int

        fun dotsRightMargin(): Int

        fun dotsSite(): Int
    }
}
