package com.iber.portal.dao.dayRent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.dayRent.DayRentOrderExtend;
import com.iber.portal.vo.dayRent.DayRentOrderAndExtendVo;

public interface DayRentOrderExtendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentOrderExtend record);

    int insertSelective(DayRentOrderExtend record);

    DayRentOrderExtend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentOrderExtend record);

    int updateByPrimaryKey(DayRentOrderExtend record);
    
    List<DayRentOrderExtend> queryDayRentOrderExtendByOrderIdType(Map<String,Object> map);
    
    List<DayRentOrderAndExtendVo> queryFinishByOrderId(@Param("orderId")String orderId,
    		@Param("lastCountTime") Date lastCountTime);
    /**
     * 根据订单id查询
     * @param objId
     * @return
     */
	List<DayRentOrderExtend> selectByOrderId(String objId);
}