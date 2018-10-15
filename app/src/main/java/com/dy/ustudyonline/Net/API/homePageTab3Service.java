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

public interface homePageTab3Service {
    @POST("appCourse/unLearnedCourses.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> unLearnedCourses(@Field("userId") String userId,
                                      @Field("pageNum") int pageNum);

}
