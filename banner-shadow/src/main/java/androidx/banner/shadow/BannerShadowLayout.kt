package androidx.banner.shadow

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class BannerShadowLayout(context: Context) : RelativeLayout(context) {

    companion object {
        const val LEFT = 9
        const val TOP = 10
        const val RIGHT = 11
        const val BOTTOM = 12
        const val CENTER = 13
    }

    private val textView = TextView(context)
    private val linearLayout = LinearLayout(context)

    internal fun shadow(
        shadowSite: Int,
        shadowWidth: Int,
        shadowHeight: Int,
        shadowBackgroundColor: Int
    ): FrameLayout.LayoutParams {
        val shadowParams = FrameLayout.LayoutParams(shadowWidth, shadowHeight)
        when (shadowSite) {
            BOTTOM -> shadowParams.gravity = Gravity.BOTTOM
            TOP -> shadowParams.gravity = Gravity.TOP
            CENTER -> shadowParams.gravity = Gravity.CENTER
        }
        setBackgroundColor(shadowBackgroundColor)
        return shadowParams
    }

    internal fun initDot(
        dotCount: Int,
        dotHeight: Int,
        dotWidth: Int,
        dotLeftMargin: Int,
        dotRightMargin: Int,
        dotSite: Int,
        config: BannerShadowConfig
    ) {
        linearLayout.removeAllViews()
        for (i in 0 until dotCount) {
            val view = View(context)
            view.background = dotsSelector(config)
            view.isEnabled = i == 0
            val params = LinearLayout.LayoutParams(dotWidth, dotHeight)
            view.layoutParams = params
            params.leftMargin = dotLeftMargin
            params.rightMargin = dotRightMargin
            linearLayout.addView(view)
        }
        val params =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_VERTICAL)
        params.addRule(dotSite)
        addView(linearLayout, params)
    }

    internal fun changeDotsPosition(position: Int) {
        for (index in 0 until linearLayout.childCount) {
            linearLayout.getChildAt(index)?.let {
                if (it.isEnabled) {
                    it.isEnabled = false
                }
            }
        }
        linearLayout.getChildAt(position)?.isEnabled = true
    }

    internal fun initTitle(
        titleColor: Int,
        titleSize: Float,
        titleLeftMargin: Int,
        titleRightMargin: Int,
        titleWidth: Int,
        titleHeight: Int,
        titleSite: Int
    ) {
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
        textView.text = title
    }

    private fun clearText() {
        textView.text = ""
    }
}
