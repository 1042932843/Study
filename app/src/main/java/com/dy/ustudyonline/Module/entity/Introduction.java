package com.dy.ustudyonline.Module.entity;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/12
 * @DESCRIPTION:
 */
public class Introduction {
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    String imageUrl;
    String remark;
    String isSelected;
    String videoLength;
    String coursePoint="0";
    String courseTeacher;
    String courseName;
    String lastPlayTime;

    public String getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(String lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public String getStudyPercent() {
        return studyPercent;
    }

    public void setStudyPercent(String studyPercent) {
        this.studyPercent = studyPercent;
    }

    String studyPercent;
}
