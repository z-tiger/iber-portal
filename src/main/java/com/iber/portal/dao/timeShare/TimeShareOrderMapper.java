package com.iber.portal.dao.timeShare;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.car.CarChargingRemind;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.vo.timeShare.LongOrderVo;
import com.iber.portal.vo.timeShare.LongTimeShareOrderVo;
import com.iber.portal.vo.timeShare.NoPayOrderVo;
import com.iber.portal.vo.timeShare.TimeShareCarOrderVo;
import com.iber.portal.vo.timeShare.TimeShareOrderVo;
import com.iber.portal.vo.timeShare.ZOTYEReturnCashOrderVo;
import com.iber.portal.zhima.vo.ZhimaRequestVo;

public interface TimeShareOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeShareOrder record);

    int insertSelective(TimeShareOrder record);

    TimeShareOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeShareOrder record);

    int updateByPrimaryKey(TimeShareOrder record);
    
	List<TimeShareOrderVo> getAll(Map<String,Object> map);
	
	List<TimeShareOrder> getAllOrderInfo();

	int getAllNum(Map<String,Object> map);
	
	List<TimeShareOrderVo> getHistoryAll(Map<String,Object> map) ;
	
	int getHistoryAllNum(Map<String,Object> map) ;
	
	List<TimeShareCarOrderVo> queryTimeShareOrderByLpn(Map<String,Object> map) ;
	
	List<TimeShareOrder> queryOrderByOrderId(String orderId) ;
	
	List<TimeShareOrder> getHistoryAllFinish(Map<String,Object> map) ;
	
	int getHistoryAllFinishNum(Map<String,Object> map) ;
	
	List<TimeShareOrder> queryOrderByMemberId(Integer memberId) ;
	
	/**
	 * 查完成订单
	 * @param orderId
	 * @return
	 * @author ouxx
	 * @date 2016-11-29 下午7:07:00
	 */
	TimeShareOrderVo getFinishedOrderByOrderId(String orderId) ;
	/**
	 * 查询会员未支付的订单
	 * @param memberId
	 * @return
	 */
	List<NoPayOrderVo> queryNoPayOrderByMemberId(Integer memberId);
	
	/**
	 * 查询正在使用，电量低于30%，而且不在充电状态的车
	 * @param paramMap
	 * @return
	 */
	List<CarChargingRemind> selectAllUsingCar(Map<String, Object> paramMap);
	/**
	 * 查询员工或者会员的用车订单
	 * @param map
	 * @return
	 */
	List<TimeShareCarOrderVo> queryOrderByLpn(Map<String, Object> map);
	/**
	 * 查询已经取消的订单
	 * @param record
	 * @return
	 */
	List<TimeShareOrderVo> queryCancelOrder(Map<String, Object> record);
	
	/**
	 * 查询取消订单的数量
	 */
	Integer queryCancelOrderNum(Map<String, Object> record);
	
	/**
	 * 根据车牌查询用户
	 * @param lpn
	 * @return
	 */
	CarChargingRemind selectUserByLpn(String lpn);
	
	/**
	 * 将订单设置为手动保留
	 * @param orderId
	 * @return
	 */
	int updateIsManualSaveByOrderId(String orderId);
	
	/**
	 * 查还在用车中，订单金额大于用户余额的订单。用车金额＝（当前小时数/24）商*最高消费费用＋（当前小时数%24）余数 *日时间计费
	 * @return
	 * @author ouxx
	 * @date 2017-5-11 下午6:00:24
	 */
	List<LongOrderVo> queryOrdCostGreaterThenBalance();

	/**
	 * 查询userCar and return的订单
	 * @return
	 */
	List<LongTimeShareOrderVo> queryAllUserAndReturnOrders(TimeShareOrder param);

	/**
	 * 根据时间查询一个月范围内的订单
	 * @param param
	 * @return
	 */
	List<TimeShareOrder> queryMonthOrderByDate(Map<String,Date> param);

	/**
	 * 查询第一天记录的时间
	 * @return
	 */
	Date queryMinCreateTime();
	
	/*
	 * 解除超长订单预警
	 */
	int updateOverWarn(@Param("orderId") String orderId);
	
	Integer queryCarOrderedStatusByLpn(String lpn);
	
	/**
	 * 查询在规定日期之内未支付的订单
	 * @param map
	 * @return
	 */
	List<ZhimaRequestVo> queryAllNoPayOrderInSpecifiedDate(
			Map<String, Object> map);
	
	/**
	 * 查询未处理违章，但订单已经支付的订单
	 * @param value 
	 * @return
	 */
	List<ZhimaRequestVo> queryUntreatedAndFinishOrder(Integer value);
	
	/**
	 * 查询未处理违章，但订单未支付的订单
	 * @param map
	 * @return
	 */
	List<ZhimaRequestVo> queryUntreatedAndNoPayInSpecifiedDate(
			Map<String, Object> map);

	int getOrderInfoNumByMemberPhone(HashMap<String, Object> map);

	List<TimeShareOrder> getOrderInfoByMemberPhone(HashMap<String, Object> map);

	List<ZhimaRequestVo> queryNotHandleAccidentAndFinishOrderRecords(Integer value);

	List<ZhimaRequestVo> queryAccidentAndNoPayOrderRecords(Map<String, Object> map);

	List<ZhimaRequestVo> queryAllFinishOrder();
	List<TimeShareCarOrderVo> queryAllCarRunOrderInfo();

	TimeShareOrder selectByOrderId(@Param("orderId")String orderId);
	
	TimeShareOrder selectLastedOrderbyLpn(Map<String, Object> map);
	
	List<ZOTYEReturnCashOrderVo> queryZOTYETypeTimeShareRecords(Map<String, Object> map);
	
	TimeShareOrder selectRunningOrderByLpn(@Param("lpn")String lpn);

	List<Map<String, String>> queryOrderIdsByLpn(String lpn);
}