package com.iber.portal.dao.car;

import com.iber.portal.model.car.CarRunLog;
import com.iber.portal.vo.car.CarWarnVo;

import java.util.List;
import java.util.Map;

public interface CarRunLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarRunLog record);

    int insertSelective(CarRunLog record);

    CarRunLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarRunLog record);

    int updateByPrimaryKey(CarRunLog record);
    
    CarRunLog queryCarRunLogByLpn(Map<String,Object> map);
    
    List<CarRunLog> queryCarRunLogByLpnAndOrderId(Map<String,Object> map);
    
    List<CarRunLog> queryCarRunLogByLpnAndLimit(Map<String,Object> map);
    
    List<CarWarnVo> selectAll(Map<String, Object> map);
    
    int selectAllCarWarnVoRecords(Map<String, Object> map);
    
    List<CarRunLog> queryCarRunLogByOrderId(String orderId);

    /**
     * 查询指定时间车牌的运行记录
     * @param map 条件
     * @return
     */
    List<CarRunLog> queryCarRunLogByLpnAndDate(Map<String,Object> map);

}