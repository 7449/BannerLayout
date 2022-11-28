import org.gradle.api.JavaVersion

object Plugin {
    const val androidVersion = "7.3.1"
    const val kotlinVersion = "1.7.20"
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlin_android = "android"
}

object Version {
    const val compileSdk = 33
    const val minSdk = 19
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0"
    val java = JavaVersion.VERSION_1_8
}

object Dep {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Plugin.kotlinVersion}"
    const val viewPager = "androidx.viewpager:viewpager:1.1.0-alpha01"

    const val banner = "com.github.7449.BannerLayout:banner:v1.0.0"
    const val transformer = "com.github.7449.BannerLayout:banner-transformer:v1.0.0"
    const val shadow = "com.github.7449.BannerLayout:banner-shadow:v1.0.0"
    const val page = "com.github.7449.BannerLayout:banner-page:v1.0.0"
}