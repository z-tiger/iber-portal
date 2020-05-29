package com.iber.portal.model.base;

import java.util.Date;

public class CounponList {
	
	private Integer id;

	private String itemName;

	private Integer min;
    
    private Integer max;
    
    private Integer balance;
    
    private Integer number;
    
    private Integer total;
    
    private Date startTime;
    
    private Integer deadline;
    
    private Integer useType;
    
    private Integer minUseValue;
    
    private String itemCode;
    
    private Integer couponItemId;

    //对应策略的生效城市
    private String cityName;

    private String cityCode;
    
    private Integer maxDeductionValue;

	public Integer getMaxDeductionValue() {
		return maxDeductionValue;
	}

	public void setMaxDeductionValue(Integer maxDeductionValue) {
		this.maxDeductionValue = maxDeductionValue;
	}

	public Integer getCouponItemId() {
		return couponItemId;
	}

	public void setCouponItemId(Integer couponItemId) {
		this.couponItemId = couponItemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public Integer getMinUseValue() {
		return minUseValue;
	}

	public void setMinUseValue(Integer minUseValue) {
		this.minUseValue = minUseValue;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}