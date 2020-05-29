package com.iber.portal.service.car;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarTypeMapper;
import com.iber.portal.model.car.CarType;

@Service
public class CarTypeService {

	@Autowired
	private CarTypeMapper carTypeMapper;
	
	public int deleteByPrimaryKey(Integer id){
		return carTypeMapper.deleteByPrimaryKey(id);
	}

	public  int insert(CarType record){
		return carTypeMapper.insert(record);
	}

	public int insertSelective(CarType record){
		return carTypeMapper.insertSelective(record);
	}

	public CarType selectByPrimaryKey(Integer id){
		return carTypeMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(CarType record){
		return carTypeMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(CarType record){
		return carTypeMapper.updateByPrimaryKey(record);
	}
    
	public List<CarType> selectAll(Map<String, Object> map){
		return carTypeMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return carTypeMapper.selectAllRecords(map);
	}
	
	public  List<CarType> selectAllNotPage(){
		return carTypeMapper.selectAllNotPage();
	}
	public CarType selectSomeColumn(Integer id){
		return carTypeMapper.selectSomeColumn(id);
	}

	public List<CarType> selectAllCarBrandList() {
		return carTypeMapper.selectCarBrandList();
	}
}
