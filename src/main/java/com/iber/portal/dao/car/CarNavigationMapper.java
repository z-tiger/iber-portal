package com.iber.portal.dao.car;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.car.CarNavigation;

public interface CarNavigationMapper {
    int insert(CarNavigation record);

    int insertSelective(CarNavigation record);
    
    int updateStatusByPrimaryKey(String orderId) ;
    
    List<CarNavigation> selectAddressByOrderId(@Param("orderId")String orderId) ;
}