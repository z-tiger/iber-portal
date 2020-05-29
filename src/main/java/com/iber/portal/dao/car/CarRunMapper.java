package com.iber.portal.dao.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.model.car.report.CarRunSmallBatteryDetail;
import com.iber.portal.model.car.report.RunStatusVo;
import com.iber.portal.vo.car.*;
import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.sys.SysParam;

public interface CarRunMapper {
	
	List<carRunAnalysisVo> getCarRunStatistics(Map<String, Object> map);
	
    int deleteByPrimaryKey(Integer id);
    
    int deleteByLpn(String lpn);

    int insert(CarRun record);

    int insertSelective(CarRun record);

    CarRun selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarRun record);

    int updateByPrimaryKey(CarRun record);
    
    List<CarRun> queryCarRunByCity(Map<String,Object> map) ;
    
    CarRun queryCarRun(String lpn) ;
    
    List<CarRun> queryCarRunByPackId(Map<String,Object> map) ;
    
    int updateCarRunStatusByLpn(Map<String,Object> map);

    List<CarStatusVo> queryCarRunStatusByCityCode(Map<String,Object> map) ;
    
    List<CarRun> queryCarRunStatusByPackId(Map<String,Object> map) ;
    
    int updateParkInfo(@Param("id") int id, @Param("parkId") int parkId);
    
    List<CarRun> queryCarRunByMemberId(@Param("memberId") int memberId);
    
    List<CarRun> queryCarLpnByStatus(Map<String,Object> map) ;
    
	int queryParkCarByAreaCode(String areaCode) ;
    
	List<CarStatusVo> queryCarRunStatusByAreaCode(String areaCode) ;
	
	/**根据车牌查询车辆状态*/
	String selectStatusByLpn(String lpn);
	
	/**根据cityCode查询运营车辆情况*/
	 List<CarRunNumVo> queryRunCarNum(@Param("cityCode") String cityCode);
	 
	 /**根据cityCode查询运营车辆情况--按照省份划分*/
	 List<CarRunNumVo> queryProvinceRunCarNuma(@Param("cityCode") String cityCode);
	
	 /**根据订单类型查询运营车辆数据（cityCode区分）*/
	List<CarRunNumVo> queryorderTypeListStatisticsRun(@Param("cityCode") String cityCode);
	
	/**根据订单类型查询运营车辆数据（cityCode区分--按省份划分*/
	List<CarRunNumVo> queryProvinceorderTypeListRun(@Param("cityCode") String cityCode);
	
	
	/**根据会员类型查询运营车辆数据（cityCode区分）*/
	List<CarRunNumVo> queryMemberTypeListStatisticsRun(@Param("cityCode") String cityCode);
	
	/**根据会员类型查询运营车辆数据（cityCode区分--按照省份划分）*/
	List<CarRunNumVo> queryProvinceMemberTypeListStatisticsRun(@Param("cityCode") String cityCode);
	
    /**根据车辆状态查询车辆总数情况（cityCode区分）*/
    List<CarRunNumVo> queryCarStatusListStatisticsSum(@Param("cityCode") String cityCode); 
    
    /**根据省份名称查询code*/
	List<CarRunNumVo> queryCodeByName(Map<String, Object> paramMap);
    
    /**根据车辆状态查询车辆总数情况（省级区分）*/
    List<CarRunNumVo> provinceCarStatusListStatisticsSum(@Param("cityCode") String cityCode); 
    
    /**根据车辆类型查询车辆总数情况（cityCode区分按照全国、地级市区分）*/
    List<CarRunNumVo> queryCarTypeListStatisticsSum(@Param("cityCode") String cityCode);
    
    /**根据车辆类型查询车辆总数情况（cityCode区分按照省份区分）*/
    List<CarRunNumVo> queryProvinceCarTypeListStatisticsSum(@Param("cityCode") String cityCode);
    /**
     * 查询运营中的车辆数
     * @return
     */
	int selectCountByStatus();

	CarRun selectByLpn(@Param("lpn")String lpn,@Param("status")String[] status);
	/**
	 * 查询所有空闲的运营车辆
	 * @return
	 */
	List<CarRunExtendVo> selectCarRunList();
    
    List<carRunAnalysisVo> getOrderCarNumber(Map<String, Object> paramMap);
	
    List<carRunAnalysisVo> getOrderNumber(Map<String, Object> paramMap);
    
    List<carRunAnalysisVo> getOrderTime(Map<String, Object> paramMap);
    
    List<carRunAnalysisVo> getOrderIncome(Map<String, Object> paramMap);
    
    
    List<CarRun>  getAllCarInfo(Double  restBattery);
    
    CarRun queryCarRunByLpn(String lpn);
    
    String queryCarOrderId(String lpn);
    
    CarRun getCarInfo(String lpn);
    
    /**
     * 判断充电车辆电量达到设置百分比时，车辆自动上线(status置为empty)，更新车辆状态
     * @param lowerLimit 车辆可上线的电量下限
     * @return
     * @author ouxx
     * @date 2017-4-10 下午4:55:43
     */
    int updateCarOnlineWhileElectricEnough(
    		@Param("lowerLimit") double lowerLimit, @Param("carType") String carType);
    
    /**
     * 判断充电车辆电量达到设置百分比时，车辆自动上线(status置为empty)，更新车辆状态
     * @param lowerLimit 车辆可上线的电量下限
     * @return
     * @author ouxx
     * @date 2017-4-10 下午4:55:43
     */
    int updateCarOnlineWhileCharging();

	List<LowRestBatteryCarVo> selectLowRestBatteryList(Map<String, Object> map);

	List<LowRestBatteryCarVo> selectLowRestBatteryListByBetweenThirtyAndfifty(
			Map<String, Object> map);

	List<CarRun> selectALL();

	int updateLatestOrderMileage(@Param("lpn")String lpn);

	int updateGetuiStatusByLpn(@Param("getuiStatus")String getuiStatus, @Param("lpn")String lpn);
	
	String selectCarStatus(@Param("lpn")String lpn);

    /**
     * 根据id更新car_run parkId
     * @param id
     * @param parkId
     */
    void updateCarRunParkId(@Param("id")Integer id, @Param("parkId")int parkId);

    /**
     * 根据lpn更新carRun status orderId memberId三个状态
     * @param map
     */
    void updateCarRunStatusAndOrderIdByLpn(Map<String,Object> map);

    /**
     * 更新carRun表中的版本号
     * @param carRun
     * @return
     */
    int updateVersionIsEmpy(CarRun carRun);

    /**
     * 查找车辆运营状态占比：运营，维修，维护，补电
     * @return
     */
    RunStatusVo selectCarRunStatus();

    /**
     * 根据状态查询运营车辆小电瓶亏电数量
     * @param small_battery_loss
     * @return
     */
    List<CarRunSmallBatteryDetail> selectCarRunSmallBatteryLossDetailRun(@Param("small_battery_loss") Integer small_battery_loss);

    /**
     * 根据状态查询空闲车辆小电瓶亏电数量
     * @param small_battery_loss
     * @return
     */
    List<CarRunSmallBatteryDetail> selectCarRunSmallBatteryLossDetailEmpty(@Param("small_battery_loss") Integer small_battery_loss);

    /**
     * 查询运营中的大电瓶电量和用户信息
     * @return
     */
    List<CarRunSmallBatteryDetail> selectUseCarRestBattery(@Param("CarReportQuery") List<CarReportQuery> CarReportQuery);

    /**
     * 查询运营中的大电瓶电量和用户信息
     * @return
     */
    List<HashMap> selectEmptyAndChargingRestBattery(@Param("CarReportQuery") List<CarReportQuery> CarReportQuery);

    /**
     * 根据车牌更改车辆状态（车辆上线）
     */
     int updateCarRunStatusById(@Param("id")Integer id);
     Integer selectIdByCarApplyStatus(@Param("lpn") String lpn);

    /**
     * 根据状态查询运营车辆小电瓶亏电详情，包含orderId
     * @param small_battery_loss
     * @return
     */
    List<CarRunSmallBatteryDetail> selectCarRunSmallBatteryLossDetail(@Param("small_battery_loss") Integer small_battery_loss);
}