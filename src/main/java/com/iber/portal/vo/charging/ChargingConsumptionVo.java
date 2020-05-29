/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.charging;

/**
 * 用电量VO
 * @author ouxx
 * @since 2016-11-24 下午7:31:37
 *
 */
public class ChargingConsumptionVo {

	private String cityCode;
	private String cityName;
	private String stationName;
	private Integer cooperationType;
	private String equipmentCnt;
	private Integer fastPileCnt;
	private Integer slowPileCnt;
	private Integer combinedPileCnt;
	private Integer chargedQuantity;
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Integer getCooperationType() {
		return cooperationType;
	}
	public void setCooperationType(Integer cooperationType) {
		this.cooperationType = cooperationType;
	}
	public String getEquipmentCnt() {
		return equipmentCnt;
	}
	public void setEquipmentCnt(String equipmentCnt) {
		this.equipmentCnt = equipmentCnt;
	}
	public Integer getChargedQuantity() {
		return chargedQuantity;
	}
	public void setChargedQuantity(Integer chargedQuantity) {
		this.chargedQuantity = chargedQuantity;
	}
	public Integer getFastPileCnt() {
		return fastPileCnt;
	}
	public void setFastPileCnt(Integer fastPileCnt) {
		this.fastPileCnt = fastPileCnt;
	}
	public Integer getSlowPileCnt() {
		return slowPileCnt;
	}
	public void setSlowPileCnt(Integer slowPileCnt) {
		this.slowPileCnt = slowPileCnt;
	}
	public Integer getCombinedPileCnt() {
		return combinedPileCnt;
	}
	public void setCombinedPileCnt(Integer combinedPileCnt) {
		this.combinedPileCnt = combinedPileCnt;
	}
	
	
}
