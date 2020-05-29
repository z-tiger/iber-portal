package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.EquipmentBrandRelation;

public interface EquipmentBrandRelationMapper {
	int selectAll(Map<String, Object>paramMap);
	List<EquipmentBrandRelation> selectByEquipmentId(Map<String, Object>paramMap);
	int insertRecords(Map<String, Object>paramMap);
	int deleteRecords(Map<String, Object>paramMap);
	int deleteRecordsByEquipmentId(Map<String, Object>paramMap);
}
