plugins {
    id(Plugin.library)
    kotlin(Plugin.kotlin_android)
}
apply("../maven.gradle")
android {
    namespace = "androidx.banner"
    compileSdk = Version.compileSdk
    defaultConfig {
        minSdk = Version.minSdk
    }
    compileOptions {
        kotlinOptions.freeCompilerArgs += listOf("-module-name", "com.github.7449.banner")
    }
}
dependencies {
    compileOnly(Dep.viewPager)
    compileOnly(Dep.kotlin)
}