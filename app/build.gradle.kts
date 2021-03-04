plugins {
    id(Plugin.application)
    kotlin(Plugin.kotlin_android)
    kotlin(Plugin.kotlin_ext)
}
android {
    compileSdkVersion(Version.compileSdk)
    defaultConfig {
        applicationId = "com.example"
        minSdkVersion(16)
        targetSdkVersion(Version.targetSdk)
        versionCode = Version.versionCode
        versionName = Version.versionName
    }
    compileOptions {
        sourceCompatibility = Version.java
        targetCompatibility = Version.java
    }
    buildFeatures.viewBinding = true
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    implementation("cn.jzvd:jiaozivideoplayer:7.4.1")

    implementation("com.github.bumptech.glide:glide:4.11.0")

    implementation("com.ydevelop:rxNetWork:0.2.1")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")

    implementation(Dep.viewPager)
    implementation(Dep.kotlin)
    implementation(project(":banner"))
    implementation(project(":transformer"))
    implementation(project(":shadow"))
    implementation(project(":page"))
//    implementation(Dep.banner)
//    implementation(Dep.transformer)
//    implementation(Dep.shadow)
//    implementation(Dep.page)
}