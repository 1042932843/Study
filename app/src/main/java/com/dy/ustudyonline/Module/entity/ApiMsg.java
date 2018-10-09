package com.dy.ustudyonline.Module.entity;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/8
 * @DESCRIPTION:
 */
public class ApiMsg {
    String message;
    String state;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    String imgPath;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    String resultInfo;
}
