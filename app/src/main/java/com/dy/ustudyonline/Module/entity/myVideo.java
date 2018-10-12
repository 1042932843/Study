package com.dy.ustudyonline.Module.entity;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/12
 * @DESCRIPTION:
 */
public class myVideo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChapter_url() {
        return chapter_url;
    }

    public void setChapter_url(String chapter_url) {
        this.chapter_url = chapter_url;
    }

    public String getChapter_length() {
        return chapter_length;
    }

    public void setChapter_length(String chapter_length) {
        this.chapter_length = chapter_length;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    String id;
    String time;
    String chapter_url;
    String chapter_length;
    String chapter_name;
}
