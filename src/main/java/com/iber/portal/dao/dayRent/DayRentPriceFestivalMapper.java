package com.iber.portal.dao.dayRent;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.dayRent.DayRentPriceFestival;

public interface DayRentPriceFestivalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentPriceFestival record);

    int insertSelective(DayRentPriceFestival record);

    DayRentPriceFestival selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentPriceFestival record);

    int updateByPrimaryKey(DayRentPriceFestival record);

	List<DayRentPriceFestival> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	List<DayRentPriceFestival> queryFestivalDays(Map<String, Object> paramMap);
}