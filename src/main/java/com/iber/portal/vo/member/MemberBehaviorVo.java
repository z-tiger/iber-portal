package com.iber.portal.vo.member;


public class MemberBehaviorVo {

	private Integer isIncrease;//标识加分还是扣分
	
	private Integer isRatio;//标识contri_value数值
	
	private String name;//行为名称

	private String contriValue;//贡献值
	
	private Integer behaviorId;

	public Integer getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(Integer behaviorId) {
		this.behaviorId = behaviorId;
	}

	public Integer getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(Integer isIncrease) {
		this.isIncrease = isIncrease;
	}

	public Integer getIsRatio() {
		return isRatio;
	}

	public void setIsRatio(Integer isRatio) {
		this.isRatio = isRatio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContriValue() {
		return contriValue;
	}

	public void setContriValue(String contriValue) {
		this.contriValue = contriValue;
	}
	
	
	
}
