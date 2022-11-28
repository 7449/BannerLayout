plugins {
    id(Plugin.application) version Plugin.androidVersion apply false
    id(Plugin.library) version Plugin.androidVersion apply false
    kotlin(Plugin.kotlin_android) version Plugin.kotlinVersion apply false
}