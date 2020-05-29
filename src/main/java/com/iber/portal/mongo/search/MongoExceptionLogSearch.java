package com.iber.portal.mongo.search;

import java.io.Serializable;

/**
 * Created by lf on 2017/9/5.
 * 异常搜索vo
 */
public class MongoExceptionLogSearch extends MongoTime implements Serializable {

    private static final long serialVersionUID = 4113267551657453693L;

    private Integer memberId; // 会员id
    private String method; // 方法

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
