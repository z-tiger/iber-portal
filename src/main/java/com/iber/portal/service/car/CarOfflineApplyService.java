package com.iber.portal.service.car;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarOfflineApplyMapper;
import com.iber.portal.model.car.CarOfflineApply;

/**
 * @Author:cuichongc
 * @Version:1.0
 */
@Service
public class CarOfflineApplyService {
	
	@Autowired
	private CarOfflineApplyMapper carOfflineApplyMapper;
	
	public List<CarOfflineApply> allCarOfflineApply(Map<String, Object> map){
		return carOfflineApplyMapper.allCarOfflineApply(map);
	}
	
	public int allCarOfflineApplyRecords(Map<String, Object> map){
		return carOfflineApplyMapper.allCarOfflineApplyRecords(map);
	}
	
	public CarOfflineApply selectByPrimaryKey(Integer id){
		return carOfflineApplyMapper.selectByPrimaryKey(id);
	}
	public int updateByPrimaryKeySelective(CarOfflineApply model){
		return carOfflineApplyMapper.updateByPrimaryKeySelective(model);
	}
	
	public CarOfflineApply selectByAll(String lpn){
		return carOfflineApplyMapper.selectByAll(lpn);
	}
	public CarOfflineApply selectById(Integer id){
		return carOfflineApplyMapper.selectById(id);
	}
	
	public int carOfflineApplyTotal(Map<String, Object> map){
		return carOfflineApplyMapper.carOfflineApplyTotal(map);
	}
	
	public CarOfflineApply selectSingleRecord(Map<String, Object> map){
		return carOfflineApplyMapper.selectSingleRecord(map);
	}
}
