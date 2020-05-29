package com.iber.portal.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.order.RentDetailOrderMapper;
import com.iber.portal.model.order.RentDetailOrder;
import com.iber.portal.vo.order.RentCountDetailVo;
import com.iber.portal.vo.order.RentStatisticsVo;

/**
 * 
 * <br>
 * <b>功能：</b>RentDetailOrderService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("rentDetailOrderService")
public class RentDetailOrderService{

	private final static Logger log= Logger.getLogger(RentDetailOrderService.class);
	
	@Autowired
    private RentDetailOrderMapper  rentDetailOrderMapper;

		
	public int insert(RentDetailOrder record){
		return rentDetailOrderMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return rentDetailOrderMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(RentDetailOrder record){
		return rentDetailOrderMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(RentDetailOrder record){
		return rentDetailOrderMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public RentDetailOrder selectByPrimaryKey(Integer id){
		return rentDetailOrderMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return rentDetailOrderMapper.getAllNum(paramMap) ;
	}
	
	public Pager<RentDetailOrder> queryPageList(Map<String, Object> paramMap){
		List<RentDetailOrder> listObj = rentDetailOrderMapper.queryPageList(paramMap);
		Pager<RentDetailOrder> pager = new Pager<RentDetailOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	/**
	 * 收集运营中的车辆租赁数据
	 * @return
	 * @author ouxx
	 * @date 2016-11-29 下午6:15:46
	 */
	public List<RentDetailOrder> getCarRentDatas(){
		return rentDetailOrderMapper.getCarRentDatas();
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 * @author ouxx
	 * @date 2016-11-30 下午5:46:07
	 */
//	public int updateBatch(List<RentDetailOrder> list){
//		return rentDetailOrderMapper.updateBatch(list);
//	}
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 * @author ouxx
	 * @date 2016-11-30 下午6:21:09
	 */
	public int insertBatch(List<RentDetailOrder> list){
		return rentDetailOrderMapper.insertBatch(list);
	}
	
	public int insertSelective(RentDetailOrder vo){
		return rentDetailOrderMapper.insertSelective(vo);
	}
	
	/**
	 * 获取最后统计的日期时间
	 * @return
	 * @author ouxx
	 * @date 2016-12-1 上午11:50:12
	 */
	public Date getLastCountTime(){
		return rentDetailOrderMapper.getLastCountTime();
	}
	
	/**
	 * 查询昨天和今天的车辆租赁次数、人数，countType == 1表示统计租赁人数
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-12-1 下午4:14:02
	 */
	public Object queryRentStatistics(Map<String, Object> paramMap){
//		return rentDetailOrderMapper.queryRentStatistics(paramMap);
		long todayCnt = this.rentDetailOrderMapper.queryTodayCnt(paramMap);
		long yesterdayCnt = this.rentDetailOrderMapper.queryYesterdayCntBeforeHourOfNow(paramMap);//queryYesterdayCnt(paramMap);
		//long thisMonthCntFromHistory = this.rentDetailOrderMapper.queryThisMonthCntFromHistory(paramMap);
		//本月的 = 今天的 + 从历史表查本月的
		long thisMonthCnt = this.rentDetailOrderMapper.queryThisMonthCnt(paramMap);
		long lastMonthCnt = this.rentDetailOrderMapper.queryLastMonthCntBeforeDayOfNow(paramMap);//.queryLastMonthCnt(paramMap);
		//long lastMonthCnt = this.rentDetailOrderMapper.queryLastMonthCnt(paramMap);
		//累计 = 今天的 + 从历史表查全部
		//long totalCntFromHistory = this.rentDetailOrderMapper.queryTotalCntFromHistory(paramMap);
		//long totalCnt = todayCnt + totalCntFromHistory;
		long totalCnt = this.rentDetailOrderMapper.queryTotalCnt(paramMap);
		// 今天次数 占 昨天次数 的比率， 单位：%
		int ratioDayCnt = 0;
		if(yesterdayCnt > 0){
			ratioDayCnt = (int) Math.round((todayCnt - yesterdayCnt) * 100.0 / yesterdayCnt);
		}else{
			if(todayCnt > 0){
				ratioDayCnt = 100;
			}
		}
		// 本月次数 占 上月次数 的比率， 单位：%
		int ratioMonthCnt = 0;
		if(0 < lastMonthCnt){
			ratioMonthCnt = (int) Math.round((thisMonthCnt - lastMonthCnt) * 100.0 / lastMonthCnt);
		}else{
			if(thisMonthCnt>0){
				ratioMonthCnt = 100;
			}
		}
		
		RentStatisticsVo vo = new RentStatisticsVo();
		vo.setTodayCnt(todayCnt);
		vo.setYesterdayCnt(yesterdayCnt);
		vo.setThisMonthCnt(thisMonthCnt);
		vo.setLastMonthCnt(lastMonthCnt);
		vo.setTotalCnt(totalCnt);
		vo.setRatioDayCnt(ratioDayCnt);
		vo.setRatioMonthCnt(ratioMonthCnt);
		return vo;
	}
	
	public List<RentCountDetailVo> queryTodayDetail(Map<String, Object> paramMap){
		return rentDetailOrderMapper.queryTodayDetail(paramMap);
	}
	
	public List<RentCountDetailVo> queryYesterdayDetail(Map<String, Object> paramMap){
		return rentDetailOrderMapper.queryYesterdayDetail(paramMap);
	}
	
	public List<RentCountDetailVo> queryThisMonthDetail(Map<String, Object> paramMap){
		//从历史表查本月全部
		List<RentCountDetailVo> thisMonthDetailFromHistory = 
				this.rentDetailOrderMapper.queryThisMonthDetailFromHistory(paramMap);
		//今天的
		List<RentCountDetailVo> todayList = rentDetailOrderMapper.queryTodayDetail(paramMap);
		//将当天按时分的次数转换成天
		List<RentCountDetailVo> tdList = new ArrayList<RentCountDetailVo>();
		if(null != todayList && !todayList.isEmpty()){
			RentCountDetailVo rcdvo = new RentCountDetailVo();
			Long cnt = 0L;
			for (RentCountDetailVo rentCountDetailVo : todayList) {
				cnt+=rentCountDetailVo.getCnt();
				rcdvo.setOrderDay(rentCountDetailVo.getOrderDay());
				rcdvo.setOrderHour(rentCountDetailVo.getOrderHour());
				rcdvo.setCountTime(rentCountDetailVo.getCountTime());
			}
			rcdvo.setCnt(cnt);
			tdList.add(rcdvo);
		}
		//本月 = 今天 + 历史表中本月的
		if(null != thisMonthDetailFromHistory && null != tdList){
			thisMonthDetailFromHistory.addAll(tdList);			
		}else if(null != tdList){//如果历史表中没有数据，则本月只有今天的
			return tdList;
		}
		
		return thisMonthDetailFromHistory;
	}
	
	public List<RentCountDetailVo> queryLastMonthDetail(Map<String, Object> paramMap){
		return rentDetailOrderMapper.queryLastMonthDetail(paramMap);
	}
	
	
	
	
	/************统计的数据是: 统计目标日 = 触发统计时刻（0点）前一天的数据 *********/
	/********************分时订单 *********/
	/**
	 * 查询当天完成的订单 : 订单开始时间 > 统计目标日0点 and 订单结束时间 < 触发统计的时间
	 * 此语句可获取当天完成的租赁收入数据：订单开始时间和完成时间都在统计目标日中
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:03:50
	 */
	public List<RentDetailOrder> queryTSBeginAndFinishedOnYesterdayCnt(){
		return rentDetailOrderMapper.queryTSBeginAndFinishedOnYesterdayCnt();
	}
	
	/**
	 * 查询跨天完成的订单  1 订单几天前开始,到统计时刻前已完成: 时长 = [统计时刻前一天0点, 订单结束时间]，里程为此时间段内的里程 
	 * 此语句可获取跨天完成的租赁收入数据：订单开始时间  < 统计目标日0点 and 完成时间 < 统计时刻
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:05:44
	 */
	public List<RentDetailOrder> queryTSFromMultiDaysAgo2YesterdayCnt(){
		return rentDetailOrderMapper.queryTSFromMultiDaysAgo2YesterdayCnt();
	}
	
	/**
	 * 查询跨天完成的订单  2.1 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 < 统计目标日0点，: 时长 = 24 * 60 min，里程为此时间段内的里程
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:06:30
	 */
	public List<RentDetailOrder> queryTSFromMultiDaysAgoUnfinishedCnt(){
		return rentDetailOrderMapper.queryTSFromMultiDaysAgoUnfinishedCnt();
	}

	/**
	 * 查询跨天完成的订单  2.2 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 > 统计目标日0点，: 时长 = [订单开始时刻, 统计时刻]，里程为此时间段内的里程 
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:06:53
	 */
	public List<RentDetailOrder> queryTSFromYesterdayUnfinishedCnt(){
		return rentDetailOrderMapper.queryTSFromYesterdayUnfinishedCnt();
	}
	
	
	/********************日租订单 *********/
	/**
	 * 查询当天完成的订单 : 订单开始时间 > 统计目标日0点 and 订单结束时间 < 触发统计的时间
	 * 此语句可获取当天完成的租赁收入数据：订单开始时间和完成时间都在统计目标日中
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:08:01
	 */
	public List<RentDetailOrder> queryDRBeginAndFinishedOnYesterdayCnt(){
		return rentDetailOrderMapper.queryDRBeginAndFinishedOnYesterdayCnt();
	}

	/**
	 * 查询跨天完成的订单  1 订单几天前开始,到统计时刻前已完成: 时长 = [统计时刻前一天0点, 订单结束时间]，里程为此时间段内的里程
	 * 此语句可获取跨天完成的租赁收入数据：订单开始时间  < 统计目标日0点 and 完成时间 < 统计时刻 -->
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:08:56
	 */
	public List<RentDetailOrder> queryDRFromMultiDaysAgo2YesterdayCnt(){
		return rentDetailOrderMapper.queryDRFromMultiDaysAgo2YesterdayCnt();
	}
	
	/**
	 * 查询跨天完成的订单  2.1 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 < 统计目标日0点，: 时长 = 24 * 60 min，里程为此时间段内的里程
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:09:53
	 */
	public List<RentDetailOrder> queryDRFromMultiDaysAgoUnfinishedCnt(){
		return rentDetailOrderMapper.queryDRFromMultiDaysAgoUnfinishedCnt();
	}

	/**
	 * 查询跨天完成的订单  2.2 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 > 统计目标日0点，: 时长 = [订单开始时刻, 统计时刻]，里程为此时间段内的里程 -->
	 * @return
	 * @author ouxx
	 * @date 2016-12-3 上午9:10:40
	 */
	public List<RentDetailOrder> queryDRFromYesterdayUnfinishedCnt(){
		return rentDetailOrderMapper.queryDRFromYesterdayUnfinishedCnt();
	}
	
	/**
	 * 获取已存在传参订单号的记录条量
	 * @param orderId
	 * @return
	 * @author ouxx
	 * @date 2016-12-9 上午10:11:33
	 */
	public int getCntByOrderId(String orderId){
		return rentDetailOrderMapper.getCntByOrderId(orderId);
	}

	/**
	 * @author zixiaobang
	 * @date 20170329
	 * 车辆运时租营跨天订单统计
	 * @return
	 */
	public List<RentDetailOrder> queryTSFromMultiDaysList() {
		return rentDetailOrderMapper.queryTSFromMultiDaysList();
	}

	/**
	 * @author zixiaobang
	 * @date 20170329
	 * 车辆运日租营跨天订单统计
	 * @return
	 */
	public List<RentDetailOrder> queryDRFromMultiDaysList() {
		return rentDetailOrderMapper.queryDRFromMultiDaysList();
	}
}
