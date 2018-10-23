package com.dy.ustudyonline.Net.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Name: homePageTab4Service
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 16:33
 */

public interface homePageTab4Service {
    @POST("appNews/pagelistNews.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> pagelistNews(@Field("userId") String userId);

    @POST("appNews/readNews.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> readNews(@Field("newsId") String newsId);

    @POST("appNews/moreNews.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> moreNews(@Field("typeId") String typeId);
}
