package com.iber.portal.model.base;

import java.util.Date;

public class ElectronicFenceCarGroupRelation {
    private Integer id;

    private Integer groupId;

    private Integer fenceId;

    private Date createTime;

    private String createName;
    
    private String category ;
    
    public ElectronicFenceCarGroupRelation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ElectronicFenceCarGroupRelation(Integer id, Integer groupId,
			Integer fenceId, Date createTime, String createName,String category) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.fenceId = fenceId;
		this.createTime = createTime;
		this.createName = createName;
		this.category = category;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getFenceId() {
        return fenceId;
    }

    public void setFenceId(Integer fenceId) {
        this.fenceId = fenceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }
}