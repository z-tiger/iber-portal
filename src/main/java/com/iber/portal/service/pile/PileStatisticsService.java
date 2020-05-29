package com.iber.portal.service.pile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.dao.pile.PileStatisticsMapper;
import com.iber.portal.model.pile.PileCountDetail;
import com.iber.portal.vo.pile.PileCountDataVo;
import com.iber.portal.vo.pile.PileStatisticsVo;

@Service
public class PileStatisticsService {
	@Autowired
	private PileStatisticsMapper pileStatisticsMapper;
	
	public List<PileStatisticsVo> getPileNums(String cityCode) {
		return pileStatisticsMapper.getPileNums(cityCode);
	}
	
	public List<PileStatisticsVo> getProvincePileNums(String cityCode) {
		return pileStatisticsMapper.getProvincePileNums(cityCode);
	}
	
	public List<PileStatisticsVo> getCityPileNums(String cityCode) {
		return pileStatisticsMapper.getCityPileNums(cityCode);
	}
	
	public List<PileStatisticsVo> getAreaPileNums(String cityCode) {
		return pileStatisticsMapper.getAreaPileNums(cityCode);
	}
	public List<PileStatisticsVo> getCityPileType(String cityCode) {
		return pileStatisticsMapper.getCityPileType(cityCode);
	}
	
	public List<PileStatisticsVo> getCityPileServiceType(String cityCode) {
		return pileStatisticsMapper.getCityPileServiceType(cityCode);
	}
	
	public List<PileStatisticsVo> getCityConncetorType(String cityCode) {
		return pileStatisticsMapper.getCityConncetorType(cityCode);
	}
	
	public List<PileStatisticsVo> getCityConncetorStatusType(String cityCode) {
		return pileStatisticsMapper.getCityConncetorStatusType(cityCode);
	}
	
	public List<PileStatisticsVo> getProvincePileType(String cityCode) {
		return pileStatisticsMapper.getProvincePileType(cityCode);
	}
	
	public List<PileStatisticsVo> getProvincePileServiceType(String cityCode) {
		return pileStatisticsMapper.getProvincePileServiceType(cityCode);
	}
	
	public List<PileStatisticsVo> getProvinceConncetorType(String cityCode) {
		return pileStatisticsMapper.getProvinceConncetorType(cityCode);
	}
	
	public List<PileStatisticsVo> getProvinceConncetorStatusType(String cityCode) {
		return pileStatisticsMapper.getProvinceConncetorStatusType(cityCode);
	}
	
	/**充电统计*/
	public List<PileCountDetail> queryChargingTimes() {
		return pileStatisticsMapper.queryChargingTimes();
	}
	public List<PileCountDetail> queryChargingPerson() {
		return pileStatisticsMapper.queryChargingPerson();
	}
	public List<PileCountDetail> queryChargingKWH() {
		return pileStatisticsMapper.queryChargingKWH();
	}
	public List<PileCountDetail> queryChargingIncome() {
		return pileStatisticsMapper.queryChargingIncome();
	}
	
	public Integer insertSelective(PileCountDetail vo) {
		return pileStatisticsMapper.insertSelective(vo);
	}
	
	/**数据展示区*/
	public List<PileCountDataVo> getAllChargingTimesData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllChargingTimesData(paramMap);
	}
	public List<PileCountDataVo> getAllChargingPersonData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllChargingPersonData(paramMap);
	}
	public List<PileCountDataVo> getAllChargingKWHData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllChargingKWHData(paramMap);
	}
	public List<PileCountDataVo> getAllChargingIncomeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllChargingIncomeData(paramMap);
	}
	
	/**数据详情*/
	
	public List<PileCountDataVo> getAllTodayChargingTimeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllTodayChargingTimeData(paramMap);
	}
	public List<PileCountDataVo> getAllYesterdayChargingTimeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllYesterdayChargingTimeData(paramMap);
	}
	public List<PileCountDataVo> getAllThisMonthChargingTimeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllThisMonthChargingTimeData(paramMap);
	}
	public List<PileCountDataVo> getAllLastMonthChargingTimeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllLastMonthChargingTimeData(paramMap);
	}
	public List<PileCountDataVo> getAllTodayChargingPersonData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllTodayChargingPersonData(paramMap);
	}
	public List<PileCountDataVo> getAllYesterdayChargingPersonData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllYesterdayChargingPersonData(paramMap);
	}
	public List<PileCountDataVo> getAllThisMonthChargingPersonData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllThisMonthChargingPersonData(paramMap);
	}
	public List<PileCountDataVo> getAllLastMonthChargingPersonData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllLastMonthChargingPersonData(paramMap);
	}
	public List<PileCountDataVo> getAllTodayChargingKWHData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllTodayChargingKWHData(paramMap);
	}
	public List<PileCountDataVo> getAllYesterdayChargingKWHData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllYesterdayChargingKWHData(paramMap);
	}
	public List<PileCountDataVo> getAllThisMonthChargingKWHData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllThisMonthChargingKWHData(paramMap);
	}
	public List<PileCountDataVo> getAllLastMonthChargingKWHData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllLastMonthChargingKWHData(paramMap);
	}
	public List<PileCountDataVo> getAllTodayChargingIncomeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllTodayChargingIncomeData(paramMap);
	}
	public List<PileCountDataVo> getAllYesterdayChargingIncomeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllYesterdayChargingIncomeData(paramMap);
	}
	public List<PileCountDataVo> getAllThisMonthChargingIncomeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllThisMonthChargingIncomeData(paramMap);
	}
	public List<PileCountDataVo> getAllLastMonthChargingIncomeData(Map<String, Object> paramMap) {
		return pileStatisticsMapper.getAllLastMonthChargingIncomeData(paramMap);
	}
	
	public List<PileStatisticsVo> getProPileNums(String cityCode) {
		return pileStatisticsMapper.getProPileNums(cityCode);
	}
	
	public List<PileStatisticsVo> getConnectorNums(String cityCode) {
		return pileStatisticsMapper.getConnectorNums(cityCode);
	}
	public List<PileStatisticsVo> getProvinceConnectorNums (String cityCode) {
		return pileStatisticsMapper.getProvinceConnectorNums(cityCode);
	}

	public int insertBatch(List<PileCountDetail> allList) {
		return pileStatisticsMapper.insertBatch(allList);
	}
	
	
}
