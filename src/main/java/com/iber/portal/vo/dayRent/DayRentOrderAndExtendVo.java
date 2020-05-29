/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.dayRent;

import java.util.Date;

import com.iber.portal.model.dayRent.DayRentOrderExtend;

/**
 * @author ouxx
 * @since 2016-11-30 下午4:48:48
 *
 */
public class DayRentOrderAndExtendVo extends DayRentOrderExtend{

	//实际取车时间
    private Date actualTakenCarTime;

    //实际还车时间
    private Date actualReturnCarTime;
    
    private Double mileage;//里程，单位：公里
    
	public Date getActualTakenCarTime() {
		return actualTakenCarTime;
	}

	public void setActualTakenCarTime(Date actualTakenCarTime) {
		this.actualTakenCarTime = actualTakenCarTime;
	}

	public Date getActualReturnCarTime() {
		return actualReturnCarTime;
	}

	public void setActualReturnCarTime(Date actualReturnCarTime) {
		this.actualReturnCarTime = actualReturnCarTime;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

}
