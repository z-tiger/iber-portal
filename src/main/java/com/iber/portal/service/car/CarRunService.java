package com.iber.portal.service.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.model.car.CarRepair;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.vo.car.CarRunNumVo;
import com.iber.portal.vo.car.CarStatusVo;
import com.iber.portal.vo.car.LowRestBatteryCarVo;
import com.iber.portal.vo.car.carRunAnalysisVo;

@Service
public class CarRunService {

	@Autowired
	private CarRunMapper carRunMapper;
	

	/**
	 * 查询车辆运营分析数据
	 * @param map
	 * @return
	 */
	public List<carRunAnalysisVo> getCarRunStatistics(Map<String, Object> map) {
		return carRunMapper.getCarRunStatistics(map);
	}
	
	public List<CarRun> queryCarRunByCity(String cityCode) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode) ;
		return carRunMapper.queryCarRunByCity(map) ;
	}
	
	public CarRun queryCarRun(String lpn){
		return carRunMapper.queryCarRun(lpn) ;
	}
	
	public List<CarRun> queryCarRunByparkId(Integer parkId) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("parkId", parkId) ;
		return carRunMapper.queryCarRunByPackId(map) ;
	}
	
	public List<CarStatusVo> queryCarRunStatusByCityCode(String cityCode){
		Map<String,Object> map = new HashMap<String, Object>() ;
		if(cityCode.equals("00")){
			map.put("cityCode", null) ;
		}else{
			map.put("cityCode", cityCode) ;
		}
		return carRunMapper.queryCarRunStatusByCityCode(map) ;
	}
	
	public List<CarRun> queryCarRunStatusByPackId(Integer parkId, String status) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("parkId", parkId) ;
		map.put("status", status) ;
		return carRunMapper.queryCarRunStatusByPackId(map) ;
	}
	
	public int  updateParkInfo(int id, int parkId){
		return carRunMapper.updateParkInfo(id, parkId) ;
	}
	
	public List<CarRun> queryCarRunByMemberId(int memberId){
		return carRunMapper.queryCarRunByMemberId(memberId);
	}
	
	public List<CarRun> queryCarLpnByStatus(Map<String,Object> map){
		return carRunMapper.queryCarLpnByStatus(map) ;
	}
	
	public int  updateByPrimaryKey(CarRun record){
		return carRunMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public int  queryParkCarByAreaCode(String areaCode){
		return carRunMapper.queryParkCarByAreaCode(areaCode) ;
	}
	
	public List<CarStatusVo> queryCarRunStatusByAreaCode(String areaCode){
		return carRunMapper.queryCarRunStatusByAreaCode(areaCode) ;
	}
	
	/**根据车牌查询车辆状态*/
	public String selectStatusByLpn(String lpn){
		return carRunMapper.selectStatusByLpn(lpn);
	}
	
	/**根据cityCode查询运营车辆情况*/
	public List<CarRunNumVo> queryRunCarNum(String cityCode){
		return carRunMapper.queryRunCarNum(cityCode);
	}
	/**根据cityCode查询运营车辆情况--按照省份划分*/
	public List<CarRunNumVo> queryProvinceRunCarNuma(String cityCode){
		return carRunMapper.queryProvinceRunCarNuma(cityCode);
	}
	
	/**根据订单类型查询运营车辆数据（cityCode区分）*/
	public List<CarRunNumVo> queryorderTypeListStatisticsRun(String cityCode){
		return carRunMapper.queryorderTypeListStatisticsRun(cityCode);
	}
	
	/**根据订单类型查询运营车辆数据（cityCode区分--按省份划分*/
	public List<CarRunNumVo> queryProvinceorderTypeListRun(String cityCode){
		return carRunMapper.queryProvinceorderTypeListRun(cityCode);
	}
	
	/**根据会员类型查询运营车辆数据（cityCode区分）*/
	public List<CarRunNumVo> queryMemberTypeListStatisticsRun(String cityCode){
		return carRunMapper.queryMemberTypeListStatisticsRun(cityCode);
	}
	
	/**根据会员类型查询运营车辆数据（cityCode区分--按照省份划分）*/
	public List<CarRunNumVo> queryProvinceMemberTypeListStatisticsRun(String cityCode){
		return carRunMapper.queryProvinceMemberTypeListStatisticsRun(cityCode);
	}
	
	/**根据车辆状态查询车辆总数情况（cityCode区分）*/
	public List<CarRunNumVo> queryCarStatusListStatisticsSum(String cityCode){
		return carRunMapper.queryCarStatusListStatisticsSum(cityCode);
	}
	/**根据省份名称查询code*/
	public List<CarRunNumVo> queryCodeByName(Map<String, Object> paramMap){
		return carRunMapper.queryCodeByName(paramMap);
	}
	/**根据车辆状态查询车辆总数情况（省级区分）*/
	public List<CarRunNumVo> provinceCarStatusListStatisticsSum(String cityCode){
		return carRunMapper.provinceCarStatusListStatisticsSum(cityCode);
	}
	/**根据车辆类型查询车辆总数情况（cityCode区分）*/
	public List<CarRunNumVo> queryCarTypeListStatisticsSum(String cityCode){
		return carRunMapper.queryCarTypeListStatisticsSum(cityCode);
	}
	 /**根据车辆类型查询车辆总数情况（cityCode区分按照省份区分）*/
	public List<CarRunNumVo> queryProvinceCarTypeListStatisticsSum(String cityCode){
		return carRunMapper.queryProvinceCarTypeListStatisticsSum(cityCode);
	}

	public CarRun selectByLpn(String lpn,String[] status) {
		return carRunMapper.selectByLpn(lpn,status);
	}

	public CarRun selectByPrimaryKey(Integer id) {
		return carRunMapper.selectByPrimaryKey(id);
	}
	
	public List<carRunAnalysisVo> getOrderCarNumber(Map<String, Object> paramMap){
		return carRunMapper.getOrderCarNumber(paramMap);
	}
	
	public List<carRunAnalysisVo> getOrderNumber(Map<String, Object> paramMap){
		return carRunMapper.getOrderNumber(paramMap);
	}
	
	public List<carRunAnalysisVo> getOrderTime(Map<String, Object> paramMap){
		return carRunMapper.getOrderTime(paramMap);
	}
	
	public List<carRunAnalysisVo> getOrderIncome(Map<String, Object> paramMap){
		return carRunMapper.getOrderIncome(paramMap);
	}

	public CarRun queryCarRunByLpn(String lpn) {
		return carRunMapper.queryCarRunByLpn(lpn);
	}
	
	public CarRun getCarInfo(String lpn) {
		return carRunMapper.getCarInfo(lpn);
	}

	public List<LowRestBatteryCarVo> selectLowRestBatteryList(
			Map<String, Object> map) {
		return carRunMapper.selectLowRestBatteryList(map);
	}

	public List<LowRestBatteryCarVo> selectLowRestBatteryListByBetweenThirtyAndfifty(
			Map<String, Object> map) {
		return carRunMapper.selectLowRestBatteryListByBetweenThirtyAndfifty(map);
	}

	public List<CarRun> selectALL() {
		return carRunMapper.selectALL();
	}

	public int updateLatestOrderMileage(String lpn) {
		return carRunMapper.updateLatestOrderMileage(lpn);
	}
	/**
	 * 根据id更新car_run parkId
	 * @param id
	 * @param parkId
	 */
	public void updateCarRunParkId(Integer id, int parkId) {
		carRunMapper.updateCarRunParkId(id,parkId);
	}

	public int updateCarRunStatusById(Integer id){
		return carRunMapper.updateCarRunStatusById(id);
	}
	public Integer selectIdByCarApplyStatus(String lpn){
		return carRunMapper.selectIdByCarApplyStatus(lpn);
	}
}
