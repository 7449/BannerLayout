package com.example

import android.app.Application
import com.example.list.Api
import io.reactivex.network.RxNetWork
import io.reactivex.network.SimpleRxNetOptionFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * by y on 2017/5/16
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RxNetWork.initialization(SimpleRxNetOptionFactory(Api.ZL_BASE_API, GsonConverterFactory.create()))
    }
}
