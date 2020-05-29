package com.iber.portal.vo.longOrderParam;

import java.io.Serializable;
import java.util.Date;

public class LongOrderParamVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5249420128811356208L;
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 会员等级编码
	 */
	private Integer levelCode;
	/**
	 * 车型id
	 */
	private Integer carTypeId;
	/**
	 * 订单金额阀值
	 */
	private Integer budgetAmount;
	/**
	 * 租车时限
	 */
	private Float budgetTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者id
	 */
	private Integer createId;
	/**
	 * 城市编码
	 */
	private String cityCode;

	/**
	 * 有效状态 1 有效 0 无效
	 */
	private Integer validStatus;
	/**
	 * 车型名称
	 */
	private String carTypeName;
	/**
	 * 等级名称
	 */
	private String levelName;
	/**
	 * 创建人姓名
	 */
	private String createName;
	/**
	 * 城市名称
	 */
	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(Integer carTypeId) {
		this.carTypeId = carTypeId;
	}

	public Integer getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(Integer budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public Float getBudgetTime() {
		return budgetTime;
	}

	public void setBudgetTime(Float budgetTime) {
		this.budgetTime = budgetTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

}
