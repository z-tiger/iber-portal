package com.iber.portal.common;


public class PageLimitByUser extends PageLimit{

	private String name;
	private String account;
	
	public PageLimitByUser(int currOffset, int currRows, String name, String account) {
		super(currOffset, currRows);
		this.name = name;
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
