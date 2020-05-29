/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.order;

/**
 * 车辆租赁统计数据VO
 * @author ouxx
 * @since 2016-12-1 下午4:04:24
 *
 */
public class RentStatisticsVo {

	private Long todayCnt;
	private Long yesterdayCnt;
	private Long thisMonthCnt;
	private Long lastMonthCnt;
	private Long totalCnt;
	private Integer ratioDayCnt;// 今天次数 占 昨天次数 的比率， 单位：%
	private Integer ratioMonthCnt;// 本月次数 占 上月次数 的比率， 单位：%
	
	public Long getTodayCnt() {
		return todayCnt;
	}
	public void setTodayCnt(Long todayCnt) {
		this.todayCnt = todayCnt;
	}
	public Long getYesterdayCnt() {
		return yesterdayCnt;
	}
	public void setYesterdayCnt(Long yesterdayCnt) {
		this.yesterdayCnt = yesterdayCnt;
	}
	public Long getThisMonthCnt() {
		return thisMonthCnt;
	}
	public void setThisMonthCnt(Long thisMonthCnt) {
		this.thisMonthCnt = thisMonthCnt;
	}
	public Long getLastMonthCnt() {
		return lastMonthCnt;
	}
	public void setLastMonthCnt(Long lastMonthCnt) {
		this.lastMonthCnt = lastMonthCnt;
	}
	public Long getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(Long totalCnt) {
		this.totalCnt = totalCnt;
	}
	public Integer getRatioDayCnt() {
		return ratioDayCnt;
	}
	public void setRatioDayCnt(Integer ratioDayCnt) {
		this.ratioDayCnt = ratioDayCnt;
	}
	public Integer getRatioMonthCnt() {
		return ratioMonthCnt;
	}
	public void setRatioMonthCnt(Integer ratioMonthCnt) {
		this.ratioMonthCnt = ratioMonthCnt;
	}
	
	
}
