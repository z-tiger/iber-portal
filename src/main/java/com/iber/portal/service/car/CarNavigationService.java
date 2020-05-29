package com.iber.portal.service.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarNavigationMapper;
import com.iber.portal.model.car.CarNavigation;

@Service
public class CarNavigationService {

	@Autowired
	private CarNavigationMapper carNavigationMapper;
	
    public int insertSelective(CarNavigation record){
    	return carNavigationMapper.insertSelective(record) ;
    }
    
    public int updateStatusByPrimaryKey(String orderId){
    	return carNavigationMapper.updateStatusByPrimaryKey(orderId) ;
    }
    
    public List<CarNavigation> selectAddressByOrderId(String orderId) {
    	return carNavigationMapper.selectAddressByOrderId(orderId) ;
    }
}
