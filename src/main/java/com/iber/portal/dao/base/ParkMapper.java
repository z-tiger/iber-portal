package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.Park;
import com.iber.portal.vo.charging.ChargingPileVo;
import com.iber.portal.vo.park.ParkTotalVo;
import com.iber.portal.vo.park.ParkTreeVo;

public interface ParkMapper {
	
	List<Park> selectAllParks(Map<String,Object> map);
	int selectAllParksRecords(Map<String,Object> map);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Park record);

    int insertSelective(Park record);

    Park selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Park record);

    int updateByPrimaryKey(Park record);
    
    List<Park> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String,Object> map);
    
    List<Park> selectAllNotPage();
    
    List<Park> queryParkByCode(Map<String,Object> map) ;
    
    List<Park> selectParkByCityCode(@Param("cityCode") String cityCode);
    
    List<Park> queryParkByCodeAndType(Map<String,Object> map) ;
    
    int queryParkByAreaCode(String areaCode) ;
    
    List<Park> queryParkStatusByAreaCode(String areaCode) ;
    
    List<Park> getAll(Map<String,Object> map) ;
    
    int getAllNum(Map<String,Object> map) ;
    
    List<Park> getAllParkName(Map<String,Object> map) ;
    
    int getAllParkNameNum(Map<String,Object> map) ;
    
    List<Park> getDataListPark(Map<String,Object> map) ;
    
    int getDataListParkNum(Map<String,Object> map) ;
    
    List<Park> getAllPark(Map<String,Object> map) ;
    
    int updateEnterpriseId(Map<String,Object> map);
    
    int deleteEnterprise(Map<String,Object> map);

    /**
     * 根据cityCode和网点合作类型查询网点
     * @param cooperationType
     * @param cityCode
     * @return
     * @author ouxx
     * @date 2016-11-21 下午5:13:50
     */
    List<Park> getStaionByCityAndCooperationType(
    		@Param("cooperationType") Integer cooperationType,
    		@Param("cityCode") String cityCode) ;
    
    /**
     * 根据cityCode和网点合作类型查询网点
     * @param cooperationType
     * @param cityCode
     * @return
     * @author ouxx
     * @date 2016-11-21 下午5:13:50
     */
    List<ParkTreeVo> getStaionTreeByCityAndCooperationType(
    		@Param("cooperationType") Integer cooperationType,
    		@Param("cityCode") String cityCode) ;
    /**按网点类型（自有、合作）查询网点总数*/
    List<ParkTotalVo>queryAllParkByType(@Param("cityCode") String cityCode);
    
    /**按网点类型（自有、合作）查询网点总数--按照省份划分*/
    List<ParkTotalVo>queryAllProvinceParkByType(@Param("cityCode") String cityCode);
    
    /**按网点服务类型（1s、2s...）查询网点总数*/
    List<ParkTotalVo>queryAllParkByServiceType(@Param("cityCode") String cityCode);
    
    /**按网点服务类型（1s、2s...）按照省份划分 查询网点总数*/
    List<ParkTotalVo>queryAllProvinceParkByServiceType(@Param("cityCode") String cityCode);
    
    /**根据cityCode获取网点车位总数*/
	List<ParkTotalVo> queryAllParkCarport(@Param("cityCode") String cityCode);
	
	/**根据cityCode获取网点车位总数--按照省份划分*/
	List<ParkTotalVo> queryAllProvinceParkCarport(@Param("cityCode") String cityCode);
	
	/**根据cityCode分别统计快慢充的网点车位数*/
	List<ParkTotalVo> queryAllParkCarportByConnectorType(@Param("cityCode") String cityCode);
	
	/**根据cityCode分别统计快慢充的网点车位数--按照省份划分*/
    List<ParkTotalVo> queryAllProvinceParkCarportByConnectorType(@Param("cityCode") String cityCode);
	
	/**根据cityCode和车辆类型统计网点车位数*/
	List<ParkTotalVo> queryAllParkCarportByCarType(@Param("cityCode") String cityCode);
	
	/**根据cityCode和车辆类型统计网点车位数--按照省份划分*/
	List<ParkTotalVo> queryAllProvinceParkCarportByCarType(@Param("cityCode") String cityCode);
	
	/**根据cityCode获取网点个数的总计*/
    List<ParkTotalVo> queryAllParkNums(@Param("cityCode") String cityCode);
    
    /**根据cityCode获取网点个数的总计*/
	List<ParkTotalVo> queryAllProvinceParkNums(@Param("cityCode") String cityCode);
	
	/**根据cityCode查询省下级城市的网点个数的总计*/
	List<ParkTotalVo> queryAllCityParkNums(@Param("name") String name);
	
	/**查询城市下级的区县的网点个数的总计*/
    
	List<ParkTotalVo> queryAllareaParkNums(String cityCode);
    /**
     * 根据网格id查询网点明细
     * @param gridId
     * @return
     */
	List<Park> getParkDetail(Map<String, Object> paramMap);
	
	int getMyAllParkNum(Map<String,Object> map) ;

	int getAllParkNum(Map<String, Object> paramMap);

	List<Park> selectExceptExistRelation(String cityCode);
	/**
	 * 根据网点名称查询网点下面的充电桩
	 * @param parkName
	 * @return
	 */
	List<ChargingPileVo> getAllChargingPileByParkId(String parkName);

    /**
     * 查询超长订单临时网点的id
     * @return
     */
	Integer queryParkIdIsTemporary(@Param("cityCode") String cityCode);
	
	List<ChargingPileVo> getCooperationParks(String parkName);

	
	List<Park> selectTemporaryParks(String cityCode);

	List<Park> getTotalParks(Map<String, Object> map);
	
	List<Park> queryAllParkExcludeTemporary();
	
	int batchUpdateByParkId(List<Integer> ids);
	
	int batchUpdateByRunParkId(List<Integer> runIds);
	
	Park selectParkByName(@Param("parkName")String parkName);
	
	/**
	 * 通过网点id 查询网点下车的数量(不包含useCar)
	 */
	int queryByPidLpnCount(Integer id);
	
	int updateStatus(@Param("status") Integer status,@Param("id") Integer id);

	/**
	 * 查询city下是否有换车临时网点
	 * @param cityCode
	 * @param longRentChangeParkName
	 * @return
	 */
    Park queryParkByCityCodeAndParkName(@Param("cityCode") String cityCode,@Param("longRentChangeParkName") String longRentChangeParkName);

	/**
	 *	更新areaCode
	 * @param parkList
	 * @param areaId
	 */
	Integer updateAreaCode(@Param("parkList") String[] parkList, @Param("areaId")String areaId);
}