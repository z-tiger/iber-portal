package com.iber.portal.model.car;

import java.io.Serializable;

/**
 * @author liubiao
 */
public class CarRelationEnterprise implements Serializable {
    private static final long serialVersionUID = -8460163230316903525L;

    private Integer id ;
    private Integer carId;
    private Integer enterpriseId;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CarRelationEnterprise{" +
                "id=" + id +
                ", carId=" + carId +
                ", enterpriseId=" + enterpriseId +
                '}';
    }
}
