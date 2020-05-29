package com.iber.portal.model.member;

/**
 * 会员贡献值明细表
 * <br>
 * <b>功能：</b>MemberContributedDetailEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class MemberContributedDetail {
	
		private java.lang.Integer id;//   	private java.lang.Integer memberId;//   	private java.lang.String type;//   类型。由此类型定贡献值的增或减	private java.lang.Integer contributedValDelta;//   贡献值增量	private java.lang.String objId;//   关联的ID，例如此记录的插入是充值所致，则此为充值订单的id	private java.util.Date createTime;//   
	private Integer hadCancel;//是否已经被撤销黑名单	public Integer getHadCancel() {
		return hadCancel;
	}
	public void setHadCancel(Integer hadCancel) {
		this.hadCancel = hadCancel;
	}
	public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.Integer getMemberId() {	    return this.memberId;	}	public void setMemberId(java.lang.Integer memberId) {	    this.memberId=memberId;	}	public java.lang.String getType() {	    return this.type;	}	public void setType(java.lang.String type) {	    this.type=type;	}	public java.lang.String getObjId() {	    return this.objId;	}	public void setObjId(java.lang.String objId) {	    this.objId=objId;	}	public java.util.Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(java.util.Date createTime) {	    this.createTime=createTime;	}
	public java.lang.Integer getContributedValDelta() {
		return contributedValDelta;
	}
	public void setContributedValDelta(java.lang.Integer contributedValDelta) {
		this.contributedValDelta = contributedValDelta;
	}
}

