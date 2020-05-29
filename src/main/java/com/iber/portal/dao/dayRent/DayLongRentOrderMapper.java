package com.iber.portal.dao.dayRent;


import com.iber.portal.model.dayRent.LongRentOrder;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;


public interface DayLongRentOrderMapper {

	int deleteByPrimaryKey(Integer id);

    int insert(LongRentOrder record);

    int insertSelective(LongRentOrder record);

    LongRentOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LongRentOrder record);

    int updateByPrimaryKey(LongRentOrder record);
    
    List<LongRentOrder> selectAllDayLongOrder(Map<String, Object> map) ;
    
    int selectCountDayOrder(Map<String, Object> map);
    
    List<LongRentOrder> selectReletNoDetail(Map<String, Object> map) ;
    
    int selectDetailCount(Map<String, Object> map) ;
    
    List<Map> beforeExpireWarn();

    /**
     * 根据订单号查找最新的一条正在用车的订单记录
     * @param orderId
     * @return
     */
    LongRentOrder selectLatestByOrderId(String orderId);
    
    int updateRemindTimes(Integer id);
    
    /**
     * 批量更改消息提醒次数和时间
     */
    int batchUpdateRemindTimesByOrderId(List<Integer> idList) ;

    LongRentOrder selectUsingOrderByOrderId(String orderId);

    /**
     * 根据订单号和车牌号查找最早一条日租订单信息
     * @param orderId
     * @param lpn
     * @return
     */
    LongRentOrder selectLatestByOrderIdAndLpn(@Param("orderId") String orderId, @Param("lpn")String lpn);
    LongRentOrder selectLastedOrderbyLpn(Map<String, Object> map);

    /**
     * 保存换车信息
     * @param longRentOrder
     */
    void updateChangeCarMsg(LongRentOrder longRentOrder);
    
    /**
     * 日租收入报表
     */
    List<LongRentOrder> selectDayRentOrderInCome(Map<String, Object> map);
    int selectDayRentOrderInComeCount(Map<String, Object> map);
    /**
     * 日租收入合计
     */
    LongRentOrder selectTotalSum(Map<String, Object> map);
}