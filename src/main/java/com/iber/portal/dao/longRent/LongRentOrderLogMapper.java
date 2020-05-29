package com.iber.portal.dao.longRent;

import com.iber.portal.model.longRent.LongRentOrderLog;

public interface LongRentOrderLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LongRentOrderLog record);

    int insertSelective(LongRentOrderLog record);

    LongRentOrderLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LongRentOrderLog record);

    int updateByPrimaryKey(LongRentOrderLog record);
}