package com.iber.portal.service.car;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarPhotoMapper;
import com.iber.portal.model.car.CarPhoto;
import com.iber.portal.vo.car.CarPhotoVo;

@Service
public class CarPhotoService {

	@Autowired
	private CarPhotoMapper carPhotoMapper;
	
	public int deleteByPrimaryKey(Integer id){
		return carPhotoMapper.deleteByPrimaryKey(id);
	}

	public  int insert(CarPhoto record){
		return carPhotoMapper.insert(record);
	}

	public int insertSelective(CarPhoto record){
		return carPhotoMapper.insertSelective(record);
	}

	public CarPhoto selectByPrimaryKey(Integer id){
		return carPhotoMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(CarPhoto record){
		return carPhotoMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(CarPhoto record){
		return carPhotoMapper.updateByPrimaryKey(record);
	}
    
	public List<CarPhotoVo> selectAll(Map<String, Object> map){
		return carPhotoMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return carPhotoMapper.selectAllRecords(map);
	} 
}
