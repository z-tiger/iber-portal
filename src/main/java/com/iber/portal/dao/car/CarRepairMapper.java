package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.car.CarRepair;

public interface CarRepairMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarRepair record);

    int insertSelective(CarRepair record);

    CarRepair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarRepair record);

    int updateByPrimaryKey(CarRepair record);
    
    List<CarRepair> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
    
    List<CarRepair> queryCarRepairByLpnStatus(Map<String, Object> map);
    
    int selectTotalRepairsInfoRecords(Map<String, Object> map);

    List<CarRepair> selectTotalRepairsInfos(Map<String, Object> map);
    
    int updateStatusCarBylpn(@Param("lpn")String lpn,@Param("status") String status,@Param("statusCache") String statusCache);
    //更改车辆运营管理对应的维修记录为已（维修、维护、补电）
    int updateStatusByCarRunStatus(Map<String, Object> paramMap);
}