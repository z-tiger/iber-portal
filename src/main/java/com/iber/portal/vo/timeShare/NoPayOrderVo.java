package com.iber.portal.vo.timeShare;
/**
 * 会员未支付的订单
 * @author Administrator
 *
 */
public class NoPayOrderVo {
	private Integer memberId;
	private String orderId;
	private String payStatus;//支付状态
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	
}
