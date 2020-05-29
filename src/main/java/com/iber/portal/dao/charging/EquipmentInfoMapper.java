package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;
import com.iber.portal.model.charging.EquipmentInfo;
import com.iber.portal.vo.pile.EquipmentInfoVo;



public interface EquipmentInfoMapper {
	
	int insert(EquipmentInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(EquipmentInfo record);
	
	int updateByPrimaryKeySelective(EquipmentInfo record);
	
	List<EquipmentInfo> selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<EquipmentInfo> queryPageList(Map<String, Object> paramMap);
	
	int queryIdByCode(String equipmentCode);
	List<EquipmentInfo> queryByStationId(String cityCode);
	
	int getAllParkEquipmentNum(Map<String, Object> paramMap);
	
	List<EquipmentInfo> getAllParkEquipment(Map<String, Object> paramMap);

	List<EquipmentInfoVo> selectUpgradePageList(Map<String, Object> paramMap);

	EquipmentInfo selectByCode(String equipmentCode);

	List<EquipmentInfoVo> queryAllUpgrade(Map<String, Object> map);
}
