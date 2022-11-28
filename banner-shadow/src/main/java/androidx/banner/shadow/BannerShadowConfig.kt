package androidx.banner.shadow

import android.graphics.Color
import androidx.banner.BannerLayout

data class BannerShadowConfig(
    val dotWidth: Int = 15,
    val dotHeight: Int = 15,
    val dotSelector: Int = 0x0,
    val dotLeftMargin: Int = 10,
    val dotRightMargin: Int = 10,
    val dotSite: Int = BannerShadowLayout.RIGHT,
    val enabledRadius: Float = 20f,
    val normalRadius: Float = 20f,
    val enabledColor: Int = Color.BLUE,
    val normalColor: Int = Color.WHITE,
    val shadowHeight: Int = 50,
    val shadowWidth: Int = BannerLayout.MATCH_PARENT,
    val shadowBackgroundColor: Int = -0x0000000,
    val shadowSite: Int = BannerShadowLayout.BOTTOM,
    val visibleTitle: Boolean = false,
    val titleSize: Float = 12f,
    val titleColor: Int = Color.YELLOW,
    val titleLeftMargin: Int = 10,
    val titleRightMargin: Int = 10,
    val titleWidth: Int = BannerLayout.WRAP_CONTENT,
    val titleHeight: Int = BannerLayout.WRAP_CONTENT,
    val titleSite: Int = BannerShadowLayout.LEFT
)