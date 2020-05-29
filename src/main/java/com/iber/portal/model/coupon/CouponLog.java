package com.iber.portal.model.coupon;

/**
 * 
 * <br>
 * <b>功能：</b>XCouponLogEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class CouponLog {
	
		private java.lang.Integer id;//   主键ID	private java.lang.String couponno;//   优惠券	private java.lang.Integer memberid;//   会员ID	private java.lang.Integer status;//   状态 0不启用 1启用	private java.lang.String batchno;//   批次号	private java.lang.Integer createid;//   	private java.util.Date createtime;//
	private String  memberName;
	private String 	memberPhone;
	private String  createName;
	private Integer couponNum;
	
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public Integer getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(Integer couponNum) {
		this.couponNum = couponNum;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.String getCouponno() {	    return this.couponno;	}	public void setCouponno(java.lang.String couponno) {	    this.couponno=couponno;	}	public java.lang.Integer getMemberid() {	    return this.memberid;	}	public void setMemberid(java.lang.Integer memberid) {	    this.memberid=memberid;	}	public java.lang.Integer getStatus() {	    return this.status;	}	public void setStatus(java.lang.Integer status) {	    this.status=status;	}	public java.lang.String getBatchno() {	    return this.batchno;	}	public void setBatchno(java.lang.String batchno) {	    this.batchno=batchno;	}	public java.lang.Integer getCreateid() {	    return this.createid;	}	public void setCreateid(java.lang.Integer createid) {	    this.createid=createid;	}	public java.util.Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(java.util.Date createtime) {	    this.createtime=createtime;	}
}

