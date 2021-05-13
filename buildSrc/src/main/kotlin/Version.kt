import org.gradle.api.JavaVersion

object Plugin {
    const val application = "com.android.application"
    const val library = "com.android.library"
    const val kotlin_android = "android"
    const val maven = "com.github.dcendents.android-maven"
}

object Version {
    const val compileSdk = 30
    const val minSdk = 14
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0"
    val java = JavaVersion.VERSION_1_8
}

object ClassPath {
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32"
    const val gradle = "com.android.tools.build:gradle:4.2.0"
    const val maven = "com.github.dcendents:android-maven-gradle-plugin:2.1"
}

object Dep {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.32"
    const val viewPager = "androidx.viewpager:viewpager:1.0.0"

    const val banner = "com.ydevelop:bannerlayout:1.2.1"
    const val transformer = "com.ydevelop:bannerlayout.transformer:0.0.3"
    const val shadow = "com.ydevelop:bannerlayout.shadow:0.0.3"
    const val page = "com.ydevelop:bannerlayout.page:0.0.3"
}