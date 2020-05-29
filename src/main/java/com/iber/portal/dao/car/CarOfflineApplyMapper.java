package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.car.CarOfflineApply;

//com.iber.portal.dao.car.CarOfflineApplyMapper
/**
 * @Author:cuichongc
 * @Version:1.0
 */
public interface CarOfflineApplyMapper {
	
	List<CarOfflineApply> allCarOfflineApply(Map<String, Object> map);
	
	int allCarOfflineApplyRecords(Map<String, Object> map);
	
	CarOfflineApply selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKeySelective(CarOfflineApply model);
	
	CarOfflineApply selectByAll(String lpn);
	
	CarOfflineApply selectById(Integer id);
	
	int carOfflineApplyTotal(Map<String, Object> map);
	
	int insertOfflineRecord(CarOfflineApply model);
	
	CarOfflineApply selectSingleRecord(Map<String, Object> map);
	
}
