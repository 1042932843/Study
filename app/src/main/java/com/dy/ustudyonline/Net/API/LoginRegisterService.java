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

public interface LoginRegisterService {

    @POST("appOrdinaryUser/userLogin.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("userName") String account,
                                   @Field("passWord") String password);


    @POST("appOrdinaryUser/userLoginOut.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> userLoginOut(@Field("userId") String userId);

    @POST("appOrdinaryUser/updateUserPwd.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> reset(@Field("userId") String id,
                                   @Field("userPwd") String oldpassword,
                                   @Field("userPwd1") String newpassword);

}
