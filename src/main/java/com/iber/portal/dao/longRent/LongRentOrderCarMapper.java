package com.iber.portal.dao.longRent;

import com.iber.portal.model.longRent.LongRentOrderCar;

public interface LongRentOrderCarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LongRentOrderCar record);

    int insertSelective(LongRentOrderCar record);

    LongRentOrderCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LongRentOrderCar record);

    int updateByPrimaryKey(LongRentOrderCar record);
}