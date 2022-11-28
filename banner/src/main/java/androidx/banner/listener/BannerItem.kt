package androidx.banner.listener

interface BannerItem {
    val bannerUrl: Any
    val bannerTitle: String
        get() = ""
}