package com.iber.portal.dao.dayRent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.dayRent.DayRentPrice;
import com.iber.portal.model.dayRent.DayRentPriceDetail;

public interface DayRentPriceDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentPriceDetail record);

    int insertSelective(DayRentPriceDetail record);

    DayRentPriceDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentPriceDetail record);

    int updateByPrimaryKey(DayRentPriceDetail record);

	List<DayRentPriceDetail> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	DayRentPriceDetail selectBycurrDate(Map<String, Object> currMap);

	List<DayRentPriceDetail> queryDayRentPriceDetails(
			Map<String, Object> paramMap);

	List<DayRentPriceDetail> getByCityCodeAndCarTypeId(@Param("cityCode")String cityCode, @Param("carTypeId")Integer carTypeId, @Param("date")Long date);
}