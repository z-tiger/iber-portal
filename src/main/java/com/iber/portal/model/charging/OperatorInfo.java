package com.iber.portal.model.charging;

import java.util.Date;

public class OperatorInfo {
	  private Integer id;  
	  
	  private String operatorId; //运营商id
	  
	  private String name;      //运营商名称
	  
	  private String phone1;    //联系电话1
	  
	  private String phone2;   // 联系电话2
	  
	  private String registerAddress;  //注册地址
	  
	  private String remark;    //备注
	  
	  private Date  createTime;  //创建时间
	  
	  private Integer createId;  //创建人
 	  
	  private Date updateTime;   //更新时间 
	  
	  private Integer updateId;  //更新人
	  
	  private String createName;//创建人
	  
	  private String updateName;//更新人
	  
	  private String url;//接口url
	  
	  private Integer runDivideInto;//运营分成比例
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getRegisterAddress() {
		return registerAddress;
	}
	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getRunDivideInto() {
		return runDivideInto;
	}
	public void setRunDivideInto(Integer runDivideInto) {
		this.runDivideInto = runDivideInto;
	}
}
