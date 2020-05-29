/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.order;

import java.util.Calendar;
import java.util.Date;

/**
 * 统计详情VO，即今天/昨天中每小时的数据，本月/上月中每天的数据
 * @author ouxx
 * @since 2016-12-5 下午12:46:44
 *
 */
public class RentCountDetailVo {

	// 今天/昨天中countHour对应的数据、本月/上月中countTime对应的数据
	private Long cnt;
	private Integer orderHour;
	private Date countTime;
	private Integer orderDay;//订单日期，即订单创建时的日期值，1-31
	
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	public Integer getOrderHour() {
		return orderHour;
	}
	public void setOrderHour(Integer orderHour) {
		this.orderHour = orderHour;
	}
	public Date getCountTime() {
		return countTime;
	}
	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}
	public Integer getOrderDay() {
		return orderDay;
	}
	public void setOrderDay(Integer orderDay) {
		this.orderDay = orderDay;
	}
	
	
	// 今天/昨天中countHour对应的数据、本月/上月中countTime对应的数据
//	private Integer count;
//	private Integer memberCount;
//	private Integer rentTime;
//	private Integer mileage;
//	private Integer income;
//	private Integer orderHour;
//	private Date countTime;
//	private Integer orderDay;//订单日期，即订单创建时的日期值，1-31
	
	
	
}
