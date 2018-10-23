package com.dy.ustudyonline.Net;


import com.dy.ustudyonline.Base.DuskyApp;
import com.dy.ustudyonline.Net.API.AccountSetService;
import com.dy.ustudyonline.Net.API.IntroductionService;
import com.dy.ustudyonline.Net.API.LoginRegisterService;
import com.dy.ustudyonline.Net.API.MainService;
import com.dy.ustudyonline.Net.API.homePageTab1Service;
import com.dy.ustudyonline.Net.API.homePageTab2Service;
import com.dy.ustudyonline.Net.API.homePageTab3Service;
import com.dy.ustudyonline.Net.API.homePageTab4Service;
import com.dy.ustudyonline.Net.API.homePageTab5Service;
import com.dy.ustudyonline.Utils.CommonUtil;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Name: RetrofitHelper
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-09 15:30
 */
public class RetrofitHelper {

  private static OkHttpClient mOkHttpClient;

  static {
    initOkHttpClient();
  }

  public static LoginRegisterService getLoginRegisterAPI() {
    return createApi(LoginRegisterService.class, ApiConstants.Base_URL);
  }
  public static homePageTab1Service gethomePageTab1API() {
    return createApi(homePageTab1Service.class, ApiConstants.Base_URL);
  }
  public static homePageTab2Service gethomePageTab2API() {
    return createApi(homePageTab2Service.class, ApiConstants.Base_URL);
  }
  public static homePageTab4Service gethomePageTab4API() {
    return createApi(homePageTab4Service.class, ApiConstants.Base_URL);
  }

  public static homePageTab5Service gethomePageTab5API() {
    return createApi(homePageTab5Service.class, ApiConstants.Base_URL);
  }

  public static AccountSetService getAccountSetAPI() {
    return createApi(AccountSetService.class, ApiConstants.Base_URL);
  }
  public static MainService getMainAPI() {
    return createApi(MainService.class, ApiConstants.Base_URL);
  }

  public static IntroductionService getIntroductionAPI() {
    return createApi(IntroductionService.class, ApiConstants.Base_URL);
  }

  public static homePageTab3Service gethomePageTab3API() {
    return createApi(homePageTab3Service.class, ApiConstants.Base_URL);
  }

  /**
   * 根据传入的baseUrl，和api创建retrofit
   */
  private static <T> T createApi(Class<T> clazz, String baseUrl) {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(mOkHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    return retrofit.create(clazz);
  }

  /**
   * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
   */
  private static void initOkHttpClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    //StatusInterceptor statusInterceptor=new StatusInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    if (mOkHttpClient == null) {
      synchronized (RetrofitHelper.class) {
        if (mOkHttpClient == null) {
          //设置Http缓存
          Cache cache = new Cache(new File(DuskyApp.getInstance()
              .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
          ClearableCookieJar cookieJar =
                  new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(DuskyApp.getInstance()));
          mOkHttpClient = new OkHttpClient.Builder()
                  .cookieJar(cookieJar)
              .cache(cache)
              //.addInterceptor(interceptor).addInterceptor(statusInterceptor)
              .addNetworkInterceptor(new CacheInterceptor())//这里关闭缓存
              .retryOnConnectionFailure(true)
              .connectTimeout(20, TimeUnit.SECONDS)
              .writeTimeout(20, TimeUnit.SECONDS)
              .readTimeout(20, TimeUnit.SECONDS)
              //.addInterceptor(new UserAgentInterceptor())
              .build();
        }
      }
    }
  }


  /**
   * 添加UA拦截器

  private static class UserAgentInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      Request originalRequest = chain.request();
      Request requestWithUserAgent = originalRequest.newBuilder()
          .removeHeader("User-Agent")
          .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
          .build();
      return chain.proceed(requestWithUserAgent);
    }
  } */

  /**
   * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
   */
  private static class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      // 有网络时 设置缓存超时时间1个小时
      int maxAge = 60 * 60;
      // 无网络时，设置超时为1天
      int maxStale = 60 * 60 * 24;
      Request request = chain.request();
      if (CommonUtil.isNetworkAvailable(DuskyApp.getInstance())) {
        //有网络时只从网络获取
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build();
      } else {
        //无网络时只从缓存中读取
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_CACHE)
            .build();
      }
      Response response = chain.proceed(request);
      if (CommonUtil.isNetworkAvailable(DuskyApp.getInstance())) {
        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, max-age=" + maxAge)
            .build();
      } else {
        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
            .build();
      }
      return response;
    }
  }
}
