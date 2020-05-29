package com.iber.portal.model.member;


import java.math.BigDecimal;
/**
 * 
 * <br>
 * <b>功能：</b>会员等级权益<br>
 * <b>作者：</b>xyq<br>
 * <b>日期：</b> 2016.12.30 <br>
 */
public class MemberLevelRightsRelation {
	
		private Integer id;//   	private Integer levelId;//   等级ID	private Integer rightsId;//   权益ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getRightsId() {
		return rightsId;
	}
	public void setRightsId(Integer rightsId) {
		this.rightsId = rightsId;
	}
}

