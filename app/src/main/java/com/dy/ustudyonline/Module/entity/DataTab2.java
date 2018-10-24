package com.dy.ustudyonline.Module.entity;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/11
 * @DESCRIPTION:
 */
public class DataTab2 {
   String courseTypeName;

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public List<DataTab2Item> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<DataTab2Item> courseList) {
        this.courseList = courseList;
    }

    String courseTypeId;

    public String getCourseTypeId2() {
        return courseTypeId2;
    }

    public void setCourseTypeId2(String courseTypeId2) {
        this.courseTypeId2 = courseTypeId2;
    }

    String courseTypeId2;
   List<DataTab2Item> courseList;
}
