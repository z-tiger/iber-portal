package com.iber.portal.dao.timeShare;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.timeShare.TimeShareRate;

public interface TimeShareRateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeShareRate record);

    int insertSelective(TimeShareRate record);

    TimeShareRate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeShareRate record);

    int updateByPrimaryKey(TimeShareRate record);

	List<TimeShareRate> getAllTimeShareRate();

	List<TimeShareRate> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
	
	/**
	 * 根据cityCode和carTypeId查车的租赁价格信息
	 * @param cityCode
	 * @param carTypeId
	 * @return
	 * @author ouxx
	 * @date 2016-9-27 上午11:27:09
	 */
	TimeShareRate getByCityCodeAndCarTypeId(@Param("cityCode")String cityCode, @Param("carTypeId")Integer carTypeId);
	
}