plugins {
    id(Plugin.library)
    id(Plugin.maven)
    kotlin(Plugin.kotlin_android)
}
android {
    compileSdkVersion(Version.compileSdk)
    defaultConfig {
        minSdkVersion(Version.minSdk)
        targetSdkVersion(Version.targetSdk)
        versionCode = Version.versionCode
        versionName = Version.versionName
    }
    compileOptions {
        kotlinOptions.freeCompilerArgs += listOf(
            "-module-name",
            "com.github.7449.banner.shadow"
        )
    }
}
dependencies {
//    compileOnly(project(":banner"))
    compileOnly(Dep.banner)
    compileOnly(Dep.viewPager)
    compileOnly(Dep.kotlin)
}