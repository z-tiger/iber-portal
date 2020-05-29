package com.iber.portal.dao.dayRent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.dayRent.DayRentPrice;

public interface DayRentPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentPrice record);

    int insertSelective(DayRentPrice record);

    DayRentPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentPrice record);

    int updateByPrimaryKey(DayRentPrice record);

	List<DayRentPrice> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	
}