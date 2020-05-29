package com.iber.portal.model.base;

import java.util.Date;

public class WZCitysQuery {
    private Integer id;

    private String cityCode;

    private String cityName;

    private String queryCityCode;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getQueryCityCode() {
        return queryCityCode;
    }

    public void setQueryCityCode(String queryCityCode) {
        this.queryCityCode = queryCityCode == null ? null : queryCityCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}