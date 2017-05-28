package com.bannersimple.refresh;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Api {

   public static final String ZL_BASE_API = "https://zhuanlan.zhihu.com/api/";

    public interface ZLService {
        @GET("columns/" + "{suffix}/posts")
        Observable<List<ListModel>> getList(@Path("suffix") String suffix, @Query("limit") int limit, @Query("offset") int offset);
    }

}
