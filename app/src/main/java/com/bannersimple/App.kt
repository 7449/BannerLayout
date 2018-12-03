package com.bannersimple

import android.app.Application
import com.bannersimple.refresh.Api
import com.facebook.drawee.backends.pipeline.Fresco
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import io.reactivex.network.RxNetWork
import io.reactivex.network.SimpleRxNetOptionFactory


/**
 * by y on 2017/5/16
 */

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this))
        RxNetWork.initialization(object : SimpleRxNetOptionFactory(Api.ZL_BASE_API, null, null) {})
    }

}
