package com.dy.ustudyonline.Module.entity;

import java.io.Serializable;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/25
 * @DESCRIPTION:
 */
public class PlayDataTab3Item implements Serializable {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQesId() {
        return qesId;
    }

    public void setQesId(String qesId) {
        this.qesId = qesId;
    }

    String imgUrl;
    String ts;
    String userName;
    String comment;
    String qesId;

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public String getAnsCount() {
        return ansCount;
    }

    public void setAnsCount(String ansCount) {
        this.ansCount = ansCount;
    }

    String clickCount;
    String ansCount;

    public boolean isZaned() {
        return isZaned;
    }

    public void setZaned(boolean zaned) {
        isZaned = zaned;
    }

    boolean isZaned;
}
