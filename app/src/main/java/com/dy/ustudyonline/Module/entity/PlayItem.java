package com.dy.ustudyonline.Module.entity;

import java.io.Serializable;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/15
 * @DESCRIPTION:{"playRate":"0%","chapter_url":"http://119.146.222.170:9980/001001/gmzy/zyk/jycy//CPSHWXG1008164559001.mp4","id":"e3ab77871da6425d814a3dcbddfb6f2c","chapter_name":"如何撰写一流的个人简历","chapter_length":"24.2"}
 */
public class PlayItem implements Serializable {
    public String getPlayRate() {
        return playRate;
    }

    public void setPlayRate(String playRate) {
        this.playRate = playRate;
    }

    public String getChapter_url() {
        return chapter_url;
    }

    public void setChapter_url(String chapter_url) {
        this.chapter_url = chapter_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapter_length() {
        return chapter_length;
    }

    public void setChapter_length(String chapter_length) {
        this.chapter_length = chapter_length;
    }

    String playRate;
    String chapter_url;
    String id;
    String chapter_length;

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    String chapter_name;

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    String localUrl;

    public int getDownloaderId() {
        return DownloaderId;
    }

    public void setDownloaderId(int downloaderId) {
        DownloaderId = downloaderId;
    }

    int DownloaderId;
}
