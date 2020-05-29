package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;
import com.iber.portal.model.car.CarAccident;


public interface CarAccidentMapper {
	
	int insert(CarAccident record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(CarAccident record);
	
	int updateByPrimaryKeySelective(CarAccident record);
	
	CarAccident selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<CarAccident> queryPageList(Map<String, Object> paramMap);
	/**保存车辆事故信息*/
	int insertSelective(CarAccident record);
	
	CarAccident selectByMap(Map<String, Object> paramMap);
}
