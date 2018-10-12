package com.dy.ustudyonline.Net.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Name: LoginRegisterService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 16:33
 */

public interface MainService {

    @POST("appOrdinaryUser/changeTList.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> changeTList(@Field("userId") String id);

    @POST("appOrdinaryUser/utList.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> utList(@Field("userId") String id);

    @POST("appOrdinaryUser/changeTerrace.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> changeNow(@Field("userId") String id,@Field("index") int index);

    @POST("appOrdinaryUser/setDefTerrace.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> setDef(@Field("userName") String userName,@Field("index") int index);
}
