plugins {
    id(Plugin.library)
    kotlin(Plugin.kotlin_android)
}
apply("../maven.gradle")
android {
    namespace = "androidx.banner.shadow"
    compileSdk = Version.compileSdk
    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
    }
    buildFeatures {
        buildConfig = false
    }
    compileOptions {
        kotlinOptions.freeCompilerArgs += listOf(
            "-module-name",
            "com.github.7449.banner.shadow"
        )
    }
}
dependencies {
    compileOnly(project(":banner"))
    compileOnly(Dep.viewPager)
    compileOnly(Dep.kotlin)
}