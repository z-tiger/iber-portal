package com.iber.portal.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.order.RentDetailOrder;
import com.iber.portal.vo.order.RentCountDetailVo;
import com.iber.portal.vo.order.RentStatisticsVo;

/**
 * 
 * <br>
 * <b>功能：</b>RentDetailOrderDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface RentDetailOrderMapper {
	
	int insert(RentDetailOrder record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(RentDetailOrder record);
	
	int updateByPrimaryKeySelective(RentDetailOrder record);
	
	RentDetailOrder selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	/**
	 * 获取已存在传参订单号的记录条量
	 * @param orderId
	 * @return
	 * @author ouxx
	 * @date 2016-12-9 上午10:08:25
	 */
	int getCntByOrderId(@Param("orderId") String orderId);
	
	List<RentDetailOrder> queryPageList(Map<String, Object> paramMap);
	
	/**
	 * 收集运营中的车辆租赁数据
	 * @return
	 * @author ouxx
	 * @date 2016-11-29 下午6:11:43
	 */
	List<RentDetailOrder> getCarRentDatas();
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 * @author ouxx
	 * @date 2016-11-30 下午5:44:55
	 */
//	int updateBatch(List<RentDetailOrder> list);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 * @author ouxx
	 * @date 2016-11-30 下午6:20:26
	 */
	int insertBatch(List<RentDetailOrder> list);
	
	int insertSelective(RentDetailOrder vo);
	
	/**
	 * 获取最后统计的日期时间
	 * @return
	 * @author ouxx
	 * @date 2016-12-1 上午11:48:53
	 */
	Date getLastCountTime();
	
	/**
	 * 查询昨天和今天的车辆租赁次数、人数，countType == 1表示统计租赁人数
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-12-1 下午4:12:07
	 */
	RentStatisticsVo queryRentStatistics(Map<String, Object> paramMap);
	
	/*****************统计详情，即今天/昨天中每小时、本月/上月中每天的情况*/
	List<RentCountDetailVo> queryTodayDetail(Map<String, Object> paramMap);
	List<RentCountDetailVo> queryYesterdayDetail(Map<String, Object> paramMap);
	List<RentCountDetailVo> queryLastMonthDetail(Map<String, Object> paramMap);
	//从历史表查本月的数据
	List<RentCountDetailVo> queryThisMonthDetailFromHistory(Map<String, Object> paramMap);
	//从历史表查全部的数据。累计 = 实时查今天的 + 历史表上所有的
	List<RentCountDetailVo> queryTotalDetailFromHistory(Map<String, Object> paramMap);
	
	
	/************统计的数据是: 统计目标日 = 触发统计时刻（0点）前一天的数据 *********/
	/********************分时订单 *********/
	/**
	 * 查询当天完成的订单 : 订单开始时间 > 统计目标日0点 and 订单结束时间 < 触发统计的时间
	 * 此语句可获取当天完成的租赁收入数据：订单开始时间和完成时间都在统计目标日中
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:03:50
	 */
	List<RentDetailOrder> queryTSBeginAndFinishedOnYesterdayCnt();
	
	/**
	 * 查询跨天完成的订单  1 订单几天前开始,到统计时刻前已完成: 时长 = [统计时刻前一天0点, 订单结束时间]，里程为此时间段内的里程 
	 * 此语句可获取跨天完成的租赁收入数据：订单开始时间  < 统计目标日0点 and 完成时间 < 统计时刻
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:05:44
	 */
	List<RentDetailOrder> queryTSFromMultiDaysAgo2YesterdayCnt();

	
	/**
	 * 查询跨天完成的订单  2.1 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 < 统计目标日0点，: 时长 = 24 * 60 min，里程为此时间段内的里程
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:06:30
	 */
	List<RentDetailOrder> queryTSFromMultiDaysAgoUnfinishedCnt();

	/**
	 * 查询跨天完成的订单  2.2 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 > 统计目标日0点，: 时长 = [订单开始时刻, 统计时刻]，里程为此时间段内的里程 
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:06:53
	 */
	List<RentDetailOrder> queryTSFromYesterdayUnfinishedCnt();
	
	
	/********************日租订单 *********/
	/**
	 * 查询当天完成的订单 : 订单开始时间 > 统计目标日0点 and 订单结束时间 < 触发统计的时间
	 * 此语句可获取当天完成的租赁收入数据：订单开始时间和完成时间都在统计目标日中
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:08:01
	 */
	List<RentDetailOrder> queryDRBeginAndFinishedOnYesterdayCnt();

	/**
	 * 查询跨天完成的订单  1 订单几天前开始,到统计时刻前已完成: 时长 = [统计时刻前一天0点, 订单结束时间]，里程为此时间段内的里程
	 * 此语句可获取跨天完成的租赁收入数据：订单开始时间  < 统计目标日0点 and 完成时间 < 统计时刻 -->
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:08:56
	 */
	List<RentDetailOrder> queryDRFromMultiDaysAgo2YesterdayCnt();
	
	/**
	 * 查询跨天完成的订单  2.1 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 < 统计目标日0点，: 时长 = 24 * 60 min，里程为此时间段内的里程
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:09:53
	 */
	List<RentDetailOrder> queryDRFromMultiDaysAgoUnfinishedCnt();

	/**
	 * 查询跨天完成的订单  2.2 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 > 统计目标日0点，: 时长 = [订单开始时刻, 统计时刻]，里程为此时间段内的里程 -->
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:10:40
	 */
	List<RentDetailOrder> queryDRFromYesterdayUnfinishedCnt();
	
	/**查今天、昨天、本月、上月、累计的总体数据。
	 * 昨天、上月的直接查历史表
	 * 本月的 = 今天的 + 从历史表查本月的
	 * 累计 = 今天的 + 从历史表查全部
	 * */
	long queryTodayCnt(Map<String, Object> paramMap);
	long queryYesterdayCnt(Map<String, Object> paramMap);
	long queryThisMonthCntFromHistory(Map<String, Object> paramMap);
	long queryLastMonthCnt(Map<String, Object> paramMap);
	long queryTotalCntFromHistory(Map<String, Object> paramMap);
	
	long queryYesterdayCntBeforeHourOfNow(Map<String, Object> paramMap);
	long queryLastMonthCntBeforeDayOfNow(Map<String, Object> paramMap);


	/**
	 * @author zixiaobang
	 * @date 20170329
	 * 车辆运时租营跨天订单统计
	 * @return
	 */
	List<RentDetailOrder> queryTSFromMultiDaysList();


	/**
	 * @author zixiaobang
	 * @date 20170329
	 * 车辆运日租营跨天订单统计
	 * @return
	 */
	List<RentDetailOrder> queryDRFromMultiDaysList();
	/**
	 * 查询当月统计数据
	 * @param paramMap
	 * @return
	 */
	long queryThisMonthCnt(Map<String, Object> paramMap);

	long queryTotalCnt(Map<String, Object> paramMap);
	
}
