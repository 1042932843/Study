package com.dy.ustudyonline.Module.entity;

import java.util.List;

/**
 * @AUTHOR: dsy
 * @TIME: 2018/10/22
 * @DESCRIPTION:
 */
public class DataTab4 {
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<DataTab4Item> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<DataTab4Item> newsList) {
        this.newsList = newsList;
    }

    String typeName;
    String typeId;
    List<DataTab4Item> newsList;
}
