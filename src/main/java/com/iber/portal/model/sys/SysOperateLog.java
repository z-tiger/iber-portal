package com.iber.portal.model.sys;



import java.util.Date;

public class SysOperateLog {
	
		private Integer id;//   	private Integer userId;//   用户ID	private String ip;//   ip	private String inParam;//   输入参数	private String outParam;//   返回值	private String methodDescribe;//   方法描述	private String methodName;//   处理方法	private  Date createTime;//   
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getInParam() {
		return inParam;
	}
	public void setInParam(String inParam) {
		this.inParam = inParam;
	}
	public String getOutParam() {
		return outParam;
	}
	public void setOutParam(String outParam) {
		this.outParam = outParam;
	}
	public String getMethodDescribe() {
		return methodDescribe;
	}
	public void setMethodDescribe(String methodDescribe) {
		this.methodDescribe = methodDescribe;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
}

