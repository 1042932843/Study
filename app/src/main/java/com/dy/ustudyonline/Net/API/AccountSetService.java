package com.dy.ustudyonline.Net.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Name: AccountSetService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 16:33
 */

public interface AccountSetService {

    @POST("appOrdinaryUser/toUpdateUserInfo.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> toUpdateUserInfo(@Field("userId") String userId);

    @POST("appOrdinaryUser/updateRealName.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateRealName(@Field("userId") String userId,
                                            @Field("realName") String realName);

    @POST("appOrdinaryUser/updateIdCard.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateIdCard(@Field("userId") String userId,
                                            @Field("idCard") String idCard);

    @POST("appOrdinaryUser/updateTelePhone.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateTelePhone(@Field("userId") String userId,
                                          @Field("telePhone") String telePhone);

    @POST("appOrdinaryUser/updateAddress.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateAddress(@Field("userId") String userId,
                                          @Field("address") String address);

    @POST("appOrdinaryUser/additionalUser.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updatePolitical(@Field("userId") String userId,
                                          @Field("politicalStatus") String school);
    @POST("appOrdinaryUser/additionalUser.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateEducation(@Field("userId") String userId,
                                             @Field("education") String school);

    @POST("appOrdinaryUser/additionalUser.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateSex(@Field("userId") String userId,
                                             @Field("sex") String sex);

    @POST("appOrdinaryUser/updateSchool.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateSchool(@Field("userId") String userId,
                                          @Field("school") String school);

    @POST("appOrdinaryUser/updateMajor.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> updateMajor(@Field("userId") String userId,
                                          @Field("major") String major);


}
