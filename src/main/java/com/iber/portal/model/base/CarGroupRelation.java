package com.iber.portal.model.base;

public class CarGroupRelation {
    private Integer id;

    private String lpn;

    private Integer groupId;

    public CarGroupRelation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CarGroupRelation(Integer id, String lpn, Integer groupId) {
		super();
		this.id = id;
		this.lpn = lpn;
		this.groupId = groupId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}