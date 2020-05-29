package com.iber.portal.dao.overall;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.overall.OverallPile;

public interface OverallPileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OverallPile record);

    int insertSelective(OverallPile record);

    OverallPile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverallPile record);

    int updateByPrimaryKey(OverallPile record);

	int insertBatch(List<OverallPile> pileList);

	List<OverallPile> selectInitAllOrderInfo(@Param("currentTime")String currentTime);

	List<OverallPile> selectInitFinishOrderInfo(@Param("currentTime")String currentTime);

	List<OverallPile> selectInitPileNum(@Param("currentTime")String currentTime);

	int getPileRecords();

}