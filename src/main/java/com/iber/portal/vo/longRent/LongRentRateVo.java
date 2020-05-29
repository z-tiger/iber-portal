package com.iber.portal.vo.longRent;

import com.iber.portal.vo.base.DateVo;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 日租计费vo
 */
public class LongRentRateVo extends DateVo implements Serializable{

    private static final long serialVersionUID = 2908907947720152156L;
    private String cityCode;
    private Integer carTypeId; // 车辆类型id

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    @Override
    public String toString() {
        return "LongRentReteVo{" +
                "cityCode='" + cityCode + '\'' +
                ", carTypeId=" + carTypeId +
                "} " + super.toString();
    }
}
