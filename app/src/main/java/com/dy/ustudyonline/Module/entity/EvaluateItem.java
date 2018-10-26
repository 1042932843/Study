package com.dy.ustudyonline.Module.entity;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/26
 * @DESCRIPTION:
 */
public class EvaluateItem {
    String imgUrl;
    String userName;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    String comment;
    String rank;
    String ts;

}
