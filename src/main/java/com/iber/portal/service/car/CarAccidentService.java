package com.iber.portal.service.car;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.car.CarAccidentMapper;
import com.iber.portal.model.car.CarAccident;


@Service("carAccidentService")
public class CarAccidentService{

	private final static Logger log= Logger.getLogger(CarAccidentService.class);
	
	@Autowired
    private CarAccidentMapper  carAccidentMapper;

		
	public int insert(CarAccident record){
		return carAccidentMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return carAccidentMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(CarAccident record){
		return carAccidentMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(CarAccident record){
		return carAccidentMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public CarAccident selectByPrimaryKey(Integer id){
		return carAccidentMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return carAccidentMapper.getAllNum(paramMap) ;
	}
	
	public Pager<CarAccident> queryPageList(Map<String, Object> paramMap){
		List<CarAccident> listObj = carAccidentMapper.queryPageList(paramMap);
		Pager<CarAccident> pager = new Pager<CarAccident>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	/**保存车辆事故信息*/
	public int insertSelective(CarAccident record){
		return carAccidentMapper.insertSelective(record);
	}
	
	public CarAccident selectByMap(Map<String, Object> paramMap){
		return carAccidentMapper.selectByMap(paramMap) ;
	}

}
