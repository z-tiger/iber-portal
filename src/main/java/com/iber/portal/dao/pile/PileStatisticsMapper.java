package com.iber.portal.dao.pile;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.pile.PileCountDetail;
import com.iber.portal.vo.pile.PileCountDataVo;
import com.iber.portal.vo.pile.PileStatisticsVo;

public interface PileStatisticsMapper {
	 List<PileStatisticsVo> getPileNums(String cityCode);
	 
	 List<PileStatisticsVo> getProvincePileNums(String cityCode);
	 
	 List<PileStatisticsVo> getCityPileNums(String cityCode);
	 
	 List<PileStatisticsVo> getAreaPileNums(String cityCode);
	 
	 List<PileStatisticsVo> getCityPileType(String cityCode);
	 
	 List<PileStatisticsVo> getCityPileServiceType(String cityCode);
	 
	 List<PileStatisticsVo> getCityConncetorType(String cityCode);
	 
	 List<PileStatisticsVo> getCityConncetorStatusType(String cityCode);
	 
	 List<PileStatisticsVo> getProvincePileType(String cityCode);
	 
	 List<PileStatisticsVo> getProvincePileServiceType(String cityCode);
	 
	 List<PileStatisticsVo> getProvinceConncetorType(String cityCode);
	 
	 List<PileStatisticsVo> getProvinceConncetorStatusType(String cityCode);
	 
	 /**充电统计*/
	 List<PileCountDetail> queryChargingTimes();
	 
	 List<PileCountDetail> queryChargingPerson();
	 
	 List<PileCountDetail> queryChargingKWH();
	 
	 List<PileCountDetail> queryChargingIncome();
	 
	 Integer insertSelective(PileCountDetail vo);
	 
	 /**数据展示区*/
	 List<PileCountDataVo> getAllChargingTimesData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllChargingPersonData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllChargingKWHData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllChargingIncomeData(Map<String, Object> paramMap);
	 
	 /**数据详情*/
	 List<PileCountDataVo> getAllTodayChargingTimeData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllYesterdayChargingTimeData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllThisMonthChargingTimeData(Map<String, Object> paramMap) ;
	 
	 List<PileCountDataVo> getAllLastMonthChargingTimeData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllTodayChargingPersonData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllYesterdayChargingPersonData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllThisMonthChargingPersonData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllLastMonthChargingPersonData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllTodayChargingKWHData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllYesterdayChargingKWHData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllThisMonthChargingKWHData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllLastMonthChargingKWHData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllTodayChargingIncomeData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllYesterdayChargingIncomeData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllThisMonthChargingIncomeData(Map<String, Object> paramMap);
	 
	 List<PileCountDataVo> getAllLastMonthChargingIncomeData(Map<String, Object> paramMap);
	 
	 List<PileStatisticsVo> getProPileNums(String cityCode);
	 
	 List<PileStatisticsVo> getConnectorNums(String cityCode);
	 
	 List<PileStatisticsVo> getProvinceConnectorNums(String cityCode);

	 int insertBatch(List<PileCountDetail> allList);
}

