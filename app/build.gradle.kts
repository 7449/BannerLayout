plugins {
    id(Plugin.application)
    kotlin(Plugin.kotlin_android)
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
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    implementation("com.github.bumptech.glide:glide:4.11.0")

    implementation("com.ydevelop:rxNetWork:0.2.1")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")

    implementation(Dep.viewPager)
    implementation(Dep.kotlin)
    implementation(Dep.banner)
    implementation(Dep.page)
    implementation(Dep.shadow)
    implementation(Dep.transformer)
//    implementation(project(":banner"))
//    implementation(project(":banner-transformer"))
//    implementation(project(":banner-shadow"))
//    implementation(project(":banner-page"))
}