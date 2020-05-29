package com.iber.portal;

/**
 * Created by 刘晓杰 on 2017/11/29.
 */
public class A {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public A(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
