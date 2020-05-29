/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.report;

import java.util.Date;

import com.iber.portal.model.coupon.Coupon;

/**
 * 优惠券报表
 * @author ouxx
 * @since 2016-10-8 上午9:55:51
 *
 */
public class CouponReport extends Coupon {

	private Integer totalNum;
	private Double totalAmount;//优惠券总额
	private Integer collectedNum;
	private Double collectedAmount;//已领取总额
	private Integer usedNum;
	private Double usedAmount;//已消费总额
	private Integer invalidNum;
	private Double invalidAmount;//已作废总额
	
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getCollectedNum() {
		return collectedNum;
	}
	public void setCollectedNum(Integer collectedNum) {
		this.collectedNum = collectedNum;
	}
	public Double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Integer getUsedNum() {
		return usedNum;
	}
	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}
	public Double getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(Double usedAmount) {
		this.usedAmount = usedAmount;
	}
	public Integer getInvalidNum() {
		return invalidNum;
	}
	public void setInvalidNum(Integer invalidNum) {
		this.invalidNum = invalidNum;
	}
	public Double getInvalidAmount() {
		return invalidAmount;
	}
	public void setInvalidAmount(Double invalidAmount) {
		this.invalidAmount = invalidAmount;
	}
	
//	private Double[] total = {0.0, 0.0};//总数量/总额
//	private Double[] collected = {0.0, 0.0};//已领取数量/金额
//	private Double[] used = {0.0, 0.0};//已使用数量/金额
//	private Double[] invalid = {0.0, 0.0};//已作废数量/金额
	
	
}
