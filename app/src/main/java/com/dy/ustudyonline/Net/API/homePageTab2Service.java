package com.dy.ustudyonline.Net.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Name: homePageTab2Service
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 16:33
 */

public interface homePageTab2Service {
    @POST("appCourse/choseCourseFirstPage.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> choseCourseFirstPage(@Field("userId") String userId);

    @POST("appCourse/choseCourseSecondPage.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> choseCourseSecondPage(@Field("userId") String userId,
                                                   @Field("courseTypeId") String courseTypeId);
    @POST("appCourse/choseCourseThirdPage.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> choseCourseThirdPage(@Field("userId") String userId,
                                                   @Field("courseTypeId2") String courseTypeId);

    @POST("appCourse/choseCourse.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> choseCourse(@Field("userId") String userId,
                                                  @Field("courseTerrace_ids") String courseTypeId);
}
