/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.charging;

/**
 * 充电桩运营月报表 【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【充电次数】【充电使用率】【充电电量】【充电时长】【计费金额（元）】
 * @author ouxx
 * @since 2016-11-16 下午6:00:09
 *
 */
public class ChargingReportVo {
	//城市级别
	private Integer cityLayer;
	//城市名称
	private String cityName;
	//城市ID
	private Integer cityId;
	//上一级城市ID
	private Integer cityPid;
	//城市编码
	private String cityCode;
	//网点ID
	private Integer parkId;
	//网点名
	private String stationName;
	//充电桩编码
	private String equipmentCode;
	//充电桩ID
	private Integer equipmentId;
	//桩类型和枪数目：如 "快充-单枪"
	private String typeAndCnt;
	//桩类型
	private Integer equipmentType;
	//枪数目
	private Integer connectorCnt;
	//充电次数
	private Integer chargedTimes;
	//充电使用率
	private Integer chargedFreq;
	//充电电量
	private Double chargedQuantity;
	//充电时长
	private Integer totalChargingTime;
	//计费金额（分）
	private Integer fee;
	//网点合作类型(0、自有网点  1、合作网点)
	private Integer cooperationType;
	//电桩使用时长
	private Integer useTime;
	//电桩使用时长合计
	private Integer useTimeSum;
	
	private Integer chargingTimeSum;//充电时间总合计
	private Double chargingAmountSum;//充电量总合计
	private Integer orderMoneySum;//订单金额总合计
	private Integer chargedTimesSum;//充电次数合计
	
	
	public Double getChargedQuantity() {
		return chargedQuantity;
	}
	public void setChargedQuantity(Double chargedQuantity) {
		this.chargedQuantity = chargedQuantity;
	}
	public Integer getChargingTimeSum() {
		return chargingTimeSum;
	}
	public void setChargingTimeSum(Integer chargingTimeSum) {
		this.chargingTimeSum = chargingTimeSum;
	}
	public Double getChargingAmountSum() {
		return chargingAmountSum;
	}
	public void setChargingAmountSum(Double chargingAmountSum) {
		this.chargingAmountSum = chargingAmountSum;
	}
	public Integer getOrderMoneySum() {
		return orderMoneySum;
	}
	public void setOrderMoneySum(Integer orderMoneySum) {
		this.orderMoneySum = orderMoneySum;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Integer getChargedTimes() {
		return chargedTimes;
	}
	public void setChargedTimes(Integer chargedTimes) {
		this.chargedTimes = chargedTimes;
	}
	public Integer getChargedFreq() {
		return chargedFreq;
	}
	public void setChargedFreq(Integer chargedFreq) {
		this.chargedFreq = chargedFreq;
	}
	public Integer getTotalChargingTime() {
		return totalChargingTime;
	}
	public void setTotalChargingTime(Integer totalChargingTime) {
		this.totalChargingTime = totalChargingTime;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public String getTypeAndCnt() {
		return typeAndCnt;
	}
	public void setTypeAndCnt(String typeAndCnt) {
		this.typeAndCnt = typeAndCnt;
	}
	public Integer getConnectorCnt() {
		return connectorCnt;
	}
	public void setConnectorCnt(Integer connectorCnt) {
		this.connectorCnt = connectorCnt;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Integer getCityPid() {
		return cityPid;
	}
	public void setCityPid(Integer cityPid) {
		this.cityPid = cityPid;
	}
	public Integer getParkId() {
		return parkId;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getCityLayer() {
		return cityLayer;
	}
	public void setCityLayer(Integer cityLayer) {
		this.cityLayer = cityLayer;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	public Integer getCooperationType() {
		return cooperationType;
	}
	public void setCooperationType(Integer cooperationType) {
		this.cooperationType = cooperationType;
	}
	public Integer getUseTime() {
		return useTime;
	}
	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}
	public Integer getUseTimeSum() {
		return useTimeSum;
	}
	public void setUseTimeSum(Integer useTimeSum) {
		this.useTimeSum = useTimeSum;
	}
	public Integer getChargedTimesSum() {
		return chargedTimesSum;
	}
	public void setChargedTimesSum(Integer chargedTimesSum) {
		this.chargedTimesSum = chargedTimesSum;
	}

    @Override
    public String toString() {
        return "ChargingReportVo{" +
                "cityLayer=" + cityLayer +
                ", cityName='" + cityName + '\'' +
                ", cityId=" + cityId +
                ", cityPid=" + cityPid +
                ", cityCode='" + cityCode + '\'' +
                ", parkId=" + parkId +
                ", stationName='" + stationName + '\'' +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentId=" + equipmentId +
                ", typeAndCnt='" + typeAndCnt + '\'' +
                ", equipmentType=" + equipmentType +
                ", connectorCnt=" + connectorCnt +
                ", chargedTimes=" + chargedTimes +
                ", chargedFreq=" + chargedFreq +
                ", chargedQuantity=" + chargedQuantity +
                ", totalChargingTime=" + totalChargingTime +
                ", fee=" + fee +
                ", cooperationType=" + cooperationType +
                ", useTime=" + useTime +
                ", useTimeSum=" + useTimeSum +
                ", chargingTimeSum=" + chargingTimeSum +
                ", chargingAmountSum=" + chargingAmountSum +
                ", orderMoneySum=" + orderMoneySum +
                ", chargedTimesSum=" + chargedTimesSum +
                '}';
    }
}
