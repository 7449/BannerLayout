package com.bannersimple.refresh

import io.reactivex.Observable
import retrofit2.http.GET

object Api {

    const val ZL_BASE_API = "https://news-at.zhihu.com/api/4/"

    interface ZLService {
        @GET("news/latest")
        fun getList(): Observable<ListModel>
    }

}
