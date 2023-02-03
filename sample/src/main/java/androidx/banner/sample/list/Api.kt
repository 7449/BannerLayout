package androidx.banner.sample.list

import io.reactivex.Observable
import retrofit2.http.GET

object Api {

    const val ZL_BASE_API = "https://news-at.zhihu.com/api/4/"

    interface ZLService {
        @GET("https://news-at.zhihu.com/api/4/news/latest")
        fun getList(): Observable<ListModel>
    }

}
