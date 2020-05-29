package com.iber.portal.dao.car;

import com.iber.portal.model.car.Car;
import com.iber.portal.vo.car.CarMrgVo;
import com.iber.portal.vo.car.CarUpgradeVo;
import com.iber.portal.vo.car.CarVersionVo;
import org.apache.ibatis.annotations.Param;

import java.util.*;

public interface CarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);
    
    List<CarMrgVo> selectAllCarMrg(Map<String, Object> map);
    
    int selectAllCarMrgRecords(Map<String, Object> map);
    
    List<CarVersionVo> queryCarVersion(Map<String, Object> map) ;
    
    int queryCarCount(Map<String, Object> map) ;
    
    List<CarUpgradeVo> queryCarBoxUpgradeVo(Map<String,Object> map) ;
    
    List<CarUpgradeVo> queryCarRearviewUpgradeVo(Map<String,Object> map) ;
    
    Car selectByLpn(String lpn);
    
    Set<String> selectLpns();

	List<Car> selectAllPushCar(HashMap<String, Object> map);
	
	List<Car> getAllCarMrg();
	
	int updateCarPreOfflineStatus(@Param("preOffline") String flag, @Param("lpn") String lpn);
	
	Set<String> selectAllCarLpns();
	
	int updateStatus(@Param("lpn") String lpn,@Param("status")Boolean status,@Param("remark")String remark,@Param("updateId") Integer updateId, @Param("carStopTime") Date carStopTime);

    /**
     * 根据企业id查询企业车辆信息
     * @param enterpriseID
     * @return
     */
    List<CarMrgVo> getCayListByEnterpriseID(Map<String,Object> map);

    /**
     * 根据企业id查询企业所属车辆总数
     * @param enterpriseID
     * @return
     */
    int getCarListByEnterpriseIDNumber(Map<String,Object> map);

    /**
     * 查询没有关联企业的车辆
     * @return
     */
    List<Car> getUnusedEnterpriseCarsList(Map<String,Object> map);

    int getUnsedEnterpriseCarsTotal();

    /**
     * 添加关联企业的车辆
     * @param param
     * @return
     */
    int addEnterpriseCar(Map<String, Integer> param);

    Map<String,Object> getEnterpriseCarRelationMap(Map<String,Object> map);

    /**
     * 删除企业所属的车辆
     * @param id
     * @return
     */
    int removeEnterpriseCar(int id);

    /**
     * 批量删除企业所属的车辆
     * @param idList
     * @return
     */
    int batchRemoveEnterpriseCar(String... idList);

    List<Map<String, Object>> selectEnterpriseCarRelations(Integer enterpriseID);

    List<Map<String, Object>> selectEnterpriseCarRelationsByCarID(Integer carid);


}