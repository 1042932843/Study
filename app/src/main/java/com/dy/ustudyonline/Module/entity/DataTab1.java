package com.dy.ustudyonline.Module.entity;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/11
 * @DESCRIPTION:
 */
public class DataTab1 {
    public String getMuCount() {
        return muCount;
    }

    public void setMuCount(String muCount) {
        this.muCount = muCount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTerraceName() {
        return terraceName;
    }

    public void setTerraceName(String terraceName) {
        this.terraceName = terraceName;
    }

    public String getCoursePoints() {
        return coursePoints;
    }

    public void setCoursePoints(String coursePoints) {
        this.coursePoints = coursePoints;
    }

    public String getHaveLearn() {
        return haveLearn;
    }

    public void setHaveLearn(String haveLearn) {
        this.haveLearn = haveLearn;
    }

    public List<DataTab1Item> gethCourseList() {
        return hCourseList;
    }

    public void sethCourseList(List<DataTab1Item> hCourseList) {
        this.hCourseList = hCourseList;
    }

    public List<DataTab1Item> getnCourseList() {
        return nCourseList;
    }

    public void setnCourseList(List<DataTab1Item> nCourseList) {
        this.nCourseList = nCourseList;
    }

    public List<DataTab1Item> getrCourseList() {
        return rCourseList;
    }

    public void setrCourseList(List<DataTab1Item> rCourseList) {
        this.rCourseList = rCourseList;
    }

    public List<DataTab1Item> getFlCourseList() {
        return flCourseList;
    }

    public void setFlCourseList(List<DataTab1Item> flCourseList) {
        this.flCourseList = flCourseList;
    }

    public List<Ad> getAdList() {
        return adList;
    }

    public void setAdList(List<Ad> adList) {
        this.adList = adList;
    }

    String muCount;//未读消息
    String realName;//真实姓名
    String terraceName;//平台名
    String coursePoints;//总学分
    String haveLearn;//已学时长
    List<DataTab1Item> hCourseList;//hot
    List<DataTab1Item> nCourseList;//new
    List<DataTab1Item> rCourseList;//猜你喜欢
    List<DataTab1Item> flCourseList;//平台先修
    List<Ad> adList;//广告
}
