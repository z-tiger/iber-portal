package com.iber.portal.model.coupon;

public class CouponStrategy {
	private java.lang.Integer id ;
	private String itemCode ;
	private String groupCode ;
	private java.lang.Integer  min ;
	private java.lang.Integer max ;
	private java.lang.Integer balance ;
	private java.lang.Integer number ;
	private java.lang.Integer total ;
	private java.util.Date startTime1 ;
	private java.lang.Integer deadline ;
	private java.lang.Integer createId ;
	private java.util.Date createTime1;
	private java.lang.Integer updateId ;
	private java.util.Date updateTime1 ;
	private String startTime ;
	private String createTime;
	private String updateTime ;
	private Integer minUseValue;
	private Integer useType;
	private String cityCode;
	private Integer maxDeductionValue;
	private Integer isOnlyOnce;//同一会员是否只能领取一次(1：是，0：否)
	
	public Integer getIsOnlyOnce() {
		return isOnlyOnce;
	}
	public void setIsOnlyOnce(Integer isOnlyOnce) {
		this.isOnlyOnce = isOnlyOnce;
	}
	public Integer getMaxDeductionValue() {
		return maxDeductionValue;
	}
	public void setMaxDeductionValue(Integer maxDeductionValue) {
		this.maxDeductionValue = maxDeductionValue;
	}
	public Integer getMinUseValue() {
		return minUseValue;
	}
	public void setMinUseValue(Integer minUseValue) {
		this.minUseValue = minUseValue;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public java.lang.Integer getMin() {
		return min;
	}
	public void setMin(java.lang.Integer min) {
		this.min = min;
	}
	public java.lang.Integer getMax() {
		return max;
	}
	public void setMax(java.lang.Integer max) {
		this.max = max;
	}
	public java.lang.Integer getBalance() {
		return balance;
	}
	public void setBalance(java.lang.Integer balance) {
		this.balance = balance;
	}
	public java.lang.Integer getNumber() {
		return number;
	}
	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}
	public java.lang.Integer getTotal() {
		return total;
	}
	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
	public java.util.Date getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(java.util.Date startTime1) {
		this.startTime1 = startTime1;
	}
	public java.lang.Integer getDeadline() {
		return deadline;
	}
	public void setDeadline(java.lang.Integer deadline) {
		this.deadline = deadline;
	}
	public java.lang.Integer getCreateId() {
		return createId;
	}
	public void setCreateId(java.lang.Integer createId) {
		this.createId = createId;
	}
	public java.util.Date getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(java.util.Date createTime1) {
		this.createTime1 = createTime1;
	}
	public java.lang.Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(java.lang.Integer updateId) {
		this.updateId = updateId;
	}
	public java.util.Date getUpdateTime1() {
		return updateTime1;
	}
	public void setUpdateTime1(java.util.Date updateTime1) {
		this.updateTime1 = updateTime1;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}

