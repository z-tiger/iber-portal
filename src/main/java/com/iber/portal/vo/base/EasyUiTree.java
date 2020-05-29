/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.base;

import java.util.List;

/**
 * easyui-tree 属性对象
 * @author ouxx
 * @since 2016-11-22 上午9:06:29
 * 
 */
public class EasyUiTree {

	// id：节点ID，对加载远程数据很重要。
	private Integer id;
	// text：显示节点文本。
	private String text;
	// state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
	private String state = "open";
	// checked：表示该节点是否被选中。
	private Boolean checked = null;
	// attributes: 被添加到节点的自定义属性。
	private Object attributes;
	// children: 一个节点数组声明了若干节点。
	private List<EasyUiTree> children;
	
	/**
	 * 父节点ID
	 * 城市的				parentId = 0；
	 * 					id = cityCode
	 * 城市下的网点合作类型的	parentId = cityCode；
	 * 					id = cityCode 与 type字符串的拼接，再转int
	 * 网点				parentId = 城市下的网点合作类型的id；
	 * 					id = parkId
	 */
	private Integer parentId;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<EasyUiTree> getChildren() {
		return children;
	}

	public void setChildren(List<EasyUiTree> children) {
		this.children = children;
	}

	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 父节点ID
	 * <p>城市的				parentId = 0；
	 * <p>					id = cityCode
	 * <p>城市下的网点合作类型的	parentId = cityCode；
	 * <p>					id = cityCode 与 type字符串的拼接，再转int
	 * <p>网点				parentId = 城市下的网点合作类型的id；
	 * <p>					id = parkId
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
