/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.model.member.MemberCountDetail;
import com.iber.portal.model.order.RentDetailOrder;
import com.iber.portal.model.pile.PileCountDetail;
import com.iber.portal.service.base.ParkUseDetailService;
import com.iber.portal.service.member.MemberCountDetailService;
import com.iber.portal.service.order.RentDetailOrderService;
import com.iber.portal.service.pile.PileStatisticsService;

/**
 * 定时收集统计数据，并插入数据表
 * @author ouxx
 * @since 2016-12-1 下午2:13:15
 *
 */
public class InsertStatisticDatasSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RentDetailOrderService rentDetailOrderService; 
	
	/*
	 * 网点使用明细
	 */
	@Autowired
	private ParkUseDetailService parkUseDetailService;
	
	/*
	 * 会员统计明细
	 */
	@Autowired
	private MemberCountDetailService memberCountDetailService;
	
	
	@Autowired
	private PileStatisticsService pileStatisticsService ;
//	@Autowired
//	private TimeSharePayService timeSharePayService;
//	
//	@Autowired
//	private DayRentOrderExtendService dayRentOrderExtendService;
	
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		//insertOrderStatisticDatas();
		//insertParkStatisticDatas();
		//insertMemberStatisticDatas();
		//insertPileStatisticDatas();
	}

	
	/**
	 * 定时收集网点使用明细数据，并插入数据表
	 * @author ouxx
	 * @date 2016-12-14 下午7:31:24
	 */
	public void insertParkStatisticDatas(){
		log.info("收集网点统计数据，并插入数据表 -- start");
		try{
			parkUseDetailService.insertQuatz();
		}catch (Exception e) {
			log.error("收集网点统计数据，并插入数据表 异常 ---"+e.getMessage());
		}
		log.info("收集网点统计数据，并插入数据表  ---end");
	}
	
	
	private List<RentDetailOrder> collectAllList(List<RentDetailOrder>... listArray){
		List<RentDetailOrder> allList = new ArrayList<RentDetailOrder>();
		if(null != listArray){
			for(List<RentDetailOrder> list : listArray){
				if(null != list && !list.isEmpty()){
					for(RentDetailOrder vo : list){
						if(StringUtils.isNotBlank(vo.getLpn()) 
								&& StringUtils.isNotBlank(vo.getOrderId())){
							//日租会存在orderId一样，但订单类型不同的记录，所以不能加以下过滤
							//如果已存在订单号为vo.getOrderId()的，则不增加
//							if(0 < this.rentDetailOrderService.getCntByOrderId(vo.getOrderId())){
//								continue;
//							}
//							this.rentDetailOrderService.insertSelective(vo);
							allList.add(vo);
						}
					}
				}
			}
		}
		return allList;
	}
	
	@SuppressWarnings("unchecked")
	public void insertOrderStatisticDatas()  {
		log.info("收集运营统计数据，并插入数据表 ---start");
		
		/********************分时订单 *********/
		//当天完成的订单 : 订单开始时间 > 统计目标日0点 and 订单结束时间 < 触发统计的时间
		//可获取当天完成的租赁收入数据：订单开始时间和完成时间都在统计目标日中
		List<RentDetailOrder> tsBeginAndFinishedOnYesterdayList = 
				this.rentDetailOrderService.queryTSBeginAndFinishedOnYesterdayCnt();
		//查询跨天完成的订单  1 订单几天前开始,到统计时刻前已完成: 时长 = [统计时刻前一天0点, 订单结束时间]，里程为此时间段内的里程 
		//此语句可获取跨天完成的租赁收入数据：订单开始时间  < 统计目标日0点 and 完成时间 < 统计时刻
//		List<RentDetailOrder> tsFromMultiDaysAgo2YesterdayList = 
//				this.rentDetailOrderService.queryTSFromMultiDaysAgo2YesterdayCnt();
		//查询跨天完成的订单  2.1 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 < 统计目标日0点，: 时长 = 24 * 60 min，里程为此时间段内的里程
//		List<RentDetailOrder> tsFromMultiDaysAgoUnfinishedList = 
//				this.rentDetailOrderService.queryTSFromMultiDaysAgoUnfinishedCnt();
		//查询跨天完成的订单  2.2 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 > 统计目标日0点，: 时长 = [订单开始时刻, 统计时刻]，里程为此时间段内的里程
//		List<RentDetailOrder> tsFromYesterdayUnfinishedList = 
//				this.rentDetailOrderService.queryTSFromYesterdayUnfinishedCnt();
		//跨天时租订单
		List<RentDetailOrder> tsFromMultiDaysList = this.rentDetailOrderService.queryTSFromMultiDaysList();
		/********************日租订单 *********/
		//查询当天完成的订单 : 订单开始时间 > 统计目标日0点 and 订单结束时间 < 触发统计的时间
		//此语句可获取当天完成的租赁收入数据：订单开始时间和完成时间都在统计目标日中
		List<RentDetailOrder> drBeginAndFinishedOnYesterdayList = 
				this.rentDetailOrderService.queryDRBeginAndFinishedOnYesterdayCnt();
//		//查询跨天完成的订单  1 订单几天前开始,到统计时刻前已完成: 时长 = [统计目标日0点, 订单结束时间]，里程为此时间段内的里程
//		List<RentDetailOrder> drFromMultiDaysAgo2YesterdayList = 
//				this.rentDetailOrderService.queryDRFromMultiDaysAgo2YesterdayCnt();
//		//查询跨天完成的订单  2.1 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 < 统计目标日0点，: 时长 = 24 * 60 min，里程为此时间段内的里程
//		List<RentDetailOrder> drFromMultiDaysAgoUnfinishedList = 
//				this.rentDetailOrderService.queryDRFromMultiDaysAgoUnfinishedCnt();
//		//查询跨天完成的订单  2.2 订单到统计时刻仍未完成，订单为用车状态 and 订单开始时间 > 统计目标日0点，: 时长 = [订单开始时刻, 统计时刻]，里程为此时间段内的里程
//		List<RentDetailOrder> drFromYesterdayUnfinishedList = 
//				this.rentDetailOrderService.queryDRFromYesterdayUnfinishedCnt();
		//跨天日租订单
		List<RentDetailOrder> drFromMultiDaysList = this.rentDetailOrderService.queryDRFromMultiDaysList();
		List<RentDetailOrder> allList = collectAllList(tsBeginAndFinishedOnYesterdayList, tsFromMultiDaysList, drBeginAndFinishedOnYesterdayList, drFromMultiDaysList );
		try{
			if(null != allList && !allList.isEmpty()){
				this.rentDetailOrderService.insertBatch(allList);		
			}
		}catch (Exception e) {
			log.info("收集运营统计数据，并插入数据表异常 ---"+e.getMessage());
		}
			log.info("收集运营统计数据，并插入数据表 ---end");
			
	}
	
	
	/**
	 * 定时收集会员统计明细数据，并插入数据表
	 * @author xyq
	 * @date 2017.1.6
	 */
	@SuppressWarnings("unchecked")
	public void insertMemberStatisticDatas(){
		log.info("收集会员统计数据，并插入数据表 ---start");
		//注册数
		List<MemberCountDetail> registerNumber = memberCountDetailService.queryRegisterNumber();
		//正式会员数
		List<MemberCountDetail> officialMemberNumber = memberCountDetailService.queryOfficialMemberNumber();
		//充值余额
		List<MemberCountDetail> rechargeBalance = memberCountDetailService.queryRechargeBalance();
		//充值押金
		List<MemberCountDetail> Deposit = memberCountDetailService.queryDeposit();
		//花费金额
		List<MemberCountDetail> costMoneyList = memberCountDetailService.queryCostMoneyList();
		//退款金额
		List<MemberCountDetail> refundMoneyList = memberCountDetailService.queryRefundMoneyList();
		
		List<MemberCountDetail> allList = collectMemberCountAllList(registerNumber,officialMemberNumber,rechargeBalance,Deposit,costMoneyList,refundMoneyList);
		if(allList != null && !allList.isEmpty()){
			try{
				memberCountDetailService.insertBatch(allList);
			}catch (Exception e) {
				log.error("收集会员统计数据，并插入数据表 异常---"+e.getMessage());
			}
		}
		log.info("收集会员统计数据，并插入数据表 ---end");
	}
	private List<MemberCountDetail> collectMemberCountAllList(List<MemberCountDetail>... listArray){
		 List<MemberCountDetail> allList =  new ArrayList<MemberCountDetail>();
		if(null != listArray){
			for(List<MemberCountDetail> list : listArray){
				if(null != list && !list.isEmpty()){
					for(MemberCountDetail vo : list){
						vo.setCreateTime(new Date());
						allList.add(vo);
//						try{
//							memberCountDetailService.insertSelective(vo);
//						}catch (Exception e) {
//							log.error("收集会员统计数据，并插入数据表 异常---"+e.getMessage()+" 数据MemberCountDetail:"+vo);
//						}
					}
				}
			}
		}

		return allList;
	}
	
	/**
	 * 定时收集充电桩统计明细数据，并插入数据表
	 * @author xyq
	 * @date 2017.1.6
	 */
	@SuppressWarnings("unchecked")
	public void insertPileStatisticDatas(){
		log.info("收集统计充电桩数据，并插入数据表");
		//充电次数
		List<PileCountDetail> chargingTimes = pileStatisticsService.queryChargingTimes();
		//充电人数
		List<PileCountDetail> chargingPerson = pileStatisticsService.queryChargingPerson();
		//充电电度数
		List<PileCountDetail> chargingKWH = pileStatisticsService.queryChargingKWH();
		//充电收入
		List<PileCountDetail> chargingIncome = pileStatisticsService.queryChargingIncome();
		
		collectPileCountAllList(chargingTimes,chargingPerson,chargingKWH,chargingIncome);
		log.info("充电桩数据，插入完毕");
	}
	
	private List<PileCountDetail> collectPileCountAllList(List<PileCountDetail>... listArray){

		if(null != listArray){
			for(List<PileCountDetail> list : listArray){
				if(null != list && !list.isEmpty()){
					for(PileCountDetail vo : list){
						vo.setCreateTime(new Date());
						try{
							pileStatisticsService.insertSelective(vo);
						}catch (Exception e) {
							log.error("充电座运营数据统计添加   异常---"+ e.getMessage());
						}
					}
				}
			}
		}

		return null;
	}
}
