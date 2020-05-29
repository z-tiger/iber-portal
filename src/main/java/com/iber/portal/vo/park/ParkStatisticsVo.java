/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.park;

/**
 * 车辆租赁统计数据VO
 * @author ouxx
 * @since 2016-12-1 下午4:04:24
 *
 */
public class ParkStatisticsVo {

	private Integer todayCnt;
	private Integer yesterdayCnt;
	private Integer thisMonthCnt;
	private Integer lastMonthCnt;
	private String 	todayName;
	private String yesterdayName;
	private String thisMonthName;
	private String lastMonthName;	
	private Integer todaytotalCnt;
	private Integer monthtotalCnt;
	
	
	
	public Integer getTodaytotalCnt() {
		return todaytotalCnt;
	}
	public void setTodaytotalCnt(Integer todaytotalCnt) {
		this.todaytotalCnt = todaytotalCnt;
	}
	public Integer getMonthtotalCnt() {
		return monthtotalCnt;
	}
	public void setMonthtotalCnt(Integer monthtotalCnt) {
		this.monthtotalCnt = monthtotalCnt;
	}
	
	
	
	
	public String getLastMonthName() {
		return lastMonthName;
	}
	public void setLastMonthName(String lastMonthName) {
		this.lastMonthName = lastMonthName;
	}
	public String getYesterdayName() {
		return yesterdayName;
	}
	public void setYesterdayName(String yesterdayName) {
		this.yesterdayName = yesterdayName;
	}
	public String getThisMonthName() {
		return thisMonthName;
	}
	public void setThisMonthName(String thisMonthName) {
		this.thisMonthName = thisMonthName;
	}
	public String getTodayName() {
		return todayName;
	}
	public void setTodayName(String todayName) {
		this.todayName = todayName;
	}
	public Integer getTodayCnt() {
		return todayCnt;
	}
	public void setTodayCnt(Integer todayCnt) {
		this.todayCnt = todayCnt;
	}
	public Integer getYesterdayCnt() {
		return yesterdayCnt;
	}
	public void setYesterdayCnt(Integer yesterdayCnt) {
		this.yesterdayCnt = yesterdayCnt;
	}
	public Integer getThisMonthCnt() {
		return thisMonthCnt;
	}
	public void setThisMonthCnt(Integer thisMonthCnt) {
		this.thisMonthCnt = thisMonthCnt;
	}
	public Integer getLastMonthCnt() {
		return lastMonthCnt;
	}
	public void setLastMonthCnt(Integer lastMonthCnt) {
		this.lastMonthCnt = lastMonthCnt;
	}
}
