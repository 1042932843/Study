package com.dy.ustudyonline.Net;

/**
 * Name: ApiConstants
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-06-25 18:35
 */

public interface ApiConstants {
       String Base_URL= "http://119.146.222.170:9082/subpfv32/";
}

       /**
        * dusky Tip
        * request demo
        */
       /*
       RetrofitHelper.getAccountSetAPI()
                .toUpdateUserInfo(PreferenceUtil.getStringPRIVATE("id",""))
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    ApiMsg apiMsg = JSON.parseObject(a,ApiMsg.class);
                    String state = apiMsg.getState();
                    switch (state){
                        case "0000":
                            ToastUtil.ShortToast(apiMsg.getMessage());

                            break;
                        case "-1":
                        case "-2":
                        default:
                            ToastUtil.ShortToast(apiMsg.getMessage());
                            break;
                    }
                    dialog.dismiss();
                }, throwable -> {
                    dialog.dismiss();
                    ToastUtil.ShortToast("返回错误，请确认网络正常或服务器正常");
                });
        */