package com.iber.portal.mongo.search;

import java.io.Serializable;

/**
 * Created by 刘晓杰 on 2018/3/15.
 */
public class MongoMemberLogExtendSearch extends MongoMemberLogSearch implements Serializable {

    private static final long serialVersionUID = -8938847661573039581L;

    private String scene; // 方法描述

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }


}
