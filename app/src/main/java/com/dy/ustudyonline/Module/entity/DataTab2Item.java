package com.dy.ustudyonline.Module.entity;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/11
 * @DESCRIPTION:
 */
public class DataTab2Item {
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    public String getCourseTerraceId() {
        return courseTerraceId;
    }

    public void setCourseTerraceId(String courseTerraceId) {
        this.courseTerraceId = courseTerraceId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCoursePoint() {
        return coursePoint;
    }

    public void setCoursePoint(String coursePoint) {
        this.coursePoint = coursePoint;
    }

    public String getChapeterCount() {
        return chapeterCount;
    }

    public void setChapeterCount(String chapeterCount) {
        this.chapeterCount = chapeterCount;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    String amount;
   String isSelect;
   String courseTerraceId;
   String length;
   String coursePoint;
   String chapeterCount;
   String courseImg;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String imageUrl;
   String courseName;
    public boolean Show;

    public boolean isShow() {
        return Show;
    }

    public void setShow(boolean show) {
        Show = show;
    }

    public boolean isSelect() {
        return Select;
    }

    public void setSelect(boolean select) {
        Select = select;
    }

    public boolean Select;
}
