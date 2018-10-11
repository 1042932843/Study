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

public interface homePageTab1Service {

    @POST("appOrdinaryUser/toIndex.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> indexall (@Field("userId") String userId,
                                   @Field("deviceType") String deviceType);//deviceType 1==ios 2==android

    @POST("appOrdinaryUser/uploadPicture.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> uploadPicture (@Field("userId") String userId,
                                       @Field("pictureBase64Source") String deviceType);

}
