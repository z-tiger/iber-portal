/*
 * 
 */
package com.iber.portal.vo.timeShare;

/**
 * 超长订单VO
 * @author ouxx
 * @since 2017-5-11 下午5:36:44
 *
 */
public class LongOrderVo {
	private Integer currOrderHours;
	private Integer currOrderCost;
	private Integer memberId;
	private String lpn;
	private Integer parkId;
	private String orderId;
	private String type;//车型
	
	public Integer getCurrOrderHours() {
		return currOrderHours;
	}
	public void setCurrOrderHours(Integer currOrderHours) {
		this.currOrderHours = currOrderHours;
	}
	public Integer getCurrOrderCost() {
		return currOrderCost;
	}
	public void setCurrOrderCost(Integer currOrderCost) {
		this.currOrderCost = currOrderCost;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public Integer getParkId() {
		return parkId;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
