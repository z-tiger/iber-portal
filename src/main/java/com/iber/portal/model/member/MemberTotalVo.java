package com.iber.portal.model.member;

/**
 * 
 * <br>
 * <b>功能：</b>会员统计<br>
 * <b>作者：</b>xyq<br>
 * <b>日期：</b> 2017.1.3 <br>
 */
public class MemberTotalVo {	private String  name;
	private String  code;
	private Integer memberTotal;
	private Integer levelCode;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getMemberTotal() {
		return memberTotal;
	}
	public void setMemberTotal(Integer memberTotal) {
		this.memberTotal = memberTotal;
	}
	public Integer getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}
}

