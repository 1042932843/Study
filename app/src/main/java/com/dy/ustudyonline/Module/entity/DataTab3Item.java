package com.dy.ustudyonline.Module.entity;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/15
 * @DESCRIPTION:
 */
public class DataTab3Item {
    String imgUrl;
    String videoLenth;
    String remark;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoLenth() {
        return videoLenth;
    }

    public void setVideoLenth(String videoLenth) {
        this.videoLenth = videoLenth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCourseTerraceId() {
        return courseTerraceId;
    }

    public void setCourseTerraceId(String courseTerraceId) {
        this.courseTerraceId = courseTerraceId;
    }

    public String getStudyLenth() {
        return studyLenth;
    }

    public void setStudyLenth(String studyLenth) {
        this.studyLenth = studyLenth;
    }

    public String getCoursePoint() {
        return coursePoint;
    }

    public void setCoursePoint(String coursePoint) {
        this.coursePoint = coursePoint;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(String lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    String courseTerraceId;
    String studyLenth;
    String coursePoint;
    String courseTeacher;
    String courseType;
    String lastPlayTime;
    String courseName;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect;

    public boolean isShow() {
        return Show;
    }

    public void setShow(boolean show) {
        Show = show;
    }

    public boolean Show;
}
