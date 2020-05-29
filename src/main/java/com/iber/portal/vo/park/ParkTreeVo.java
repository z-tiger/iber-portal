/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.park;

import com.iber.portal.vo.base.EasyUiTree;

/**
 * easyui-tree 城市、网点树的网点节点VO
 * @author ouxx
 * @since 2016-11-25 下午6:44:04
 *
 */
public class ParkTreeVo extends EasyUiTree {

	//某网点下的充电桩数量
	private Integer equipmentCnt;

	public Integer getEquipmentCnt() {
		return equipmentCnt;
	}

	public void setEquipmentCnt(Integer equipmentCnt) {
		this.equipmentCnt = equipmentCnt;
	}
}
