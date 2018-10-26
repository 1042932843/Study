package com.dy.ustudyonline.Net.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Name: homePageTab3Service
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-30 16:33
 */

public interface PlayService {
    //Tab4
    @POST("appCourse/peComment.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> peComment(@Field("userId") String userId,
                                              @Field("rank") int rank,
                                              @Field("courseTerraceId") String courseTerraceId,
                                              @Field("comment") String comment);
    @POST("appCourse/peCommentList.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> peCommentList(@Field("courseTerraceId") String userId);

    //Tab3
    @POST("appCourse/courseQuizList.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> courseQuizList(@Field("courseTerraceId") String userId);

    @POST("appCourse/courseQuiz.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> courseQuiz(@Field("courseTerraceId") String courseTerraceId,
                                        @Field("userId") String userId,
                                        @Field("qesTitle") String qesTitle,
                                        @Field("qestion") String question);

    @POST("appCourse/qesComment.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> qesComment(@Field("comment") String comment,
                                        @Field("userId") String userId,
                                        @Field("qesId") String qesId);

    @POST("appCourse/qesCommentList.xhtml")
    @FormUrlEncoded
    Observable<ResponseBody> qesCommentList(@Field("qesId") String qesTitle);
}
