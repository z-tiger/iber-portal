package com.iber.portal.model.exception;

/**
 * 
 * <br>
 * <b>功能：</b>XExceptionLogEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class ExceptionLog {
	
		private java.lang.Integer id;//   	private java.lang.String param;//   参数	private java.lang.Integer memberid;//   会员ID	private java.lang.String method;//   接口	private java.lang.String exceptioncontent;//   异常内容	private java.util.Date createtime;//   创建时间
	
	private String memberName ;	public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.String getParam() {	    return this.param;	}	public void setParam(java.lang.String param) {	    this.param=param;	}	public java.lang.Integer getMemberid() {	    return this.memberid;	}	public void setMemberid(java.lang.Integer memberid) {	    this.memberid=memberid;	}	public java.lang.String getMethod() {	    return this.method;	}	public void setMethod(java.lang.String method) {	    this.method=method;	}	public java.lang.String getExceptioncontent() {	    return this.exceptioncontent;	}	public void setExceptioncontent(java.lang.String exceptioncontent) {	    this.exceptioncontent=exceptioncontent;	}	public java.util.Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(java.util.Date createtime) {	    this.createtime=createtime;	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
}

