package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.EquipmentBrandRelationMapper;
import com.iber.portal.model.charging.EquipmentBrandRelation;


@Service("equipmentBrandRelationService")
public class EquipmentBrandRelationService{

	private final static Logger log= Logger.getLogger(EquipmentBrandRelationService.class);
	
	@Autowired
    private    EquipmentBrandRelationMapper equipmentBrandRelationMapper ;

	public Pager<EquipmentBrandRelation> queryPageList(Map<String, Object>paramMap){
		List<EquipmentBrandRelation> listObj = equipmentBrandRelationMapper.selectByEquipmentId(paramMap);
		Pager<EquipmentBrandRelation> pager = new Pager<EquipmentBrandRelation>();
		pager.setDatas(listObj);
		pager.setTotalCount(equipmentBrandRelationMapper.selectAll(paramMap));
		return pager;
	}
	
	public int insertRecords(Map<String, Object>paramMap){
		return equipmentBrandRelationMapper.insertRecords(paramMap);
	}
	
	public int deleteRecords(Map<String, Object>paramMap){
		return equipmentBrandRelationMapper.deleteRecords(paramMap);
	}
	
	
	public int deleteRecordsByEquipmentId(Map<String, Object>paramMap){
		return equipmentBrandRelationMapper.deleteRecordsByEquipmentId(paramMap);
	}
	
}
