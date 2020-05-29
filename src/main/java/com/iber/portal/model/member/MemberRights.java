package com.iber.portal.model.member;



import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
/**
 * 
 * <br>
 * <b>功能：</b>会员权益	<br>
 * <b>作者：</b>xyq<br>
 * <b>日期：</b> 2016.12.29 <br>
 */
public class MemberRights {	private Integer id;//   	private String rightsName;//   权益名称	private String iconUrl;//   权益图标
	private MultipartFile iconUrlMultipartFile;	private Integer type;//   权益类型.优惠券(0)，或折扣(1)	private String descUrl;//   权益描述的H5 URL	private Integer value;//   值。优惠券面值，或折扣值	private Integer number;//   数量。优惠券张数，或折扣（默认无限制）	private Integer createId;//   	private Date createTime;//   	private Integer updateId;//   	private Date updateTime;//  
	private String createName;
	private String updateName;
	private String grayIconUrl;//权益灰图标
	private MultipartFile grayIconUrlMultipartFile;
	private Integer isUseInBirthday;//是否在生日时使用
	
	public Integer getIsUseInBirthday() {
		return isUseInBirthday;
	}
	public void setIsUseInBirthday(Integer isUseInBirthday) {
		this.isUseInBirthday = isUseInBirthday;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRightsName() {
		return rightsName;
	}
	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDescUrl() {
		return descUrl;
	}
	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
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
	public String getGrayIconUrl() {
		return grayIconUrl;
	}
	public void setGrayIconUrl(String grayIconUrl) {
		this.grayIconUrl = grayIconUrl;
	}
	public MultipartFile getIconUrlMultipartFile() {
		return iconUrlMultipartFile;
	}
	public void setIconUrlMultipartFile(MultipartFile iconUrlMultipartFile) {
		this.iconUrlMultipartFile = iconUrlMultipartFile;
	}
	public MultipartFile getGrayIconUrlMultipartFile() {
		return grayIconUrlMultipartFile;
	}
	public void setGrayIconUrlMultipartFile(MultipartFile grayIconUrlMultipartFile) {
		this.grayIconUrlMultipartFile = grayIconUrlMultipartFile;
	}
	
}

