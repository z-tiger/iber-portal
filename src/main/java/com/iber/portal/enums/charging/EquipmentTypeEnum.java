/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.enums.charging;

/**
 * 充电桩类型枚举
 * @author ouxx
 * @since 2016-11-17 下午2:39:31
 *
 */
public enum EquipmentTypeEnum {

	FAST(1, "快充"),
	SLOW(2, "慢充"),
	AC_DC_COMBINED(3, "交直流一体");
	
	private int no;
	private String type;
	EquipmentTypeEnum(int no, String type){
		this.no = no;
		this.type = type;
	}
	public int getNo() {
		return no;
	}
	public String getType() {
		return type;
	}
	
	public static EquipmentTypeEnum getByNo(int no){
		EquipmentTypeEnum tempEnum = null;
		for(EquipmentTypeEnum en : EquipmentTypeEnum.values()){
			if(en.getNo() == no){
				tempEnum = en;
				break;
			}
		}
		if(tempEnum == null){
			throw new RuntimeException("Enum value not exist");
		}
		return tempEnum;
	}
	
	public static String getType(int no){
		EquipmentTypeEnum tempEnum = getByNo(no);
		return tempEnum.getType();
	}
}
