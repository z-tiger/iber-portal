package com.iber.portal.service.timeShare;

import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.car.CarMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.enterprise.EnterpriseUseCarApplyMapper;
import com.iber.portal.dao.member.MemberRightsMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.dao.timeShare.TimeShareRateMapper;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.member.MemberRights;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.model.timeShare.TimeShareRate;
import com.iber.portal.util.MoneyCalculate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MoneyCalculateService {

	@Autowired
	private TimeShareOrderMapper timeShareOrderMapper;

	@Autowired
	private CarMapper carMapper;

	@Autowired
	private CarRunMapper carRunMapper;

	@Autowired
	private TimeShareRateMapper timeShareRateMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberRightsMapper memberRightsMapper;
	
	@Autowired
	private EnterpriseUseCarApplyMapper enterpriseUseCarApplyMapper;
	
	/**
	 * 
	 * @param startTime 订单开始时间
	 * @param endTime 订单结束时间
	 * @param isFreeCompensate 是否使用不计免赔
	 * @param timeRate 时间计费，例如奇瑞现在7.5元/30分钟
	 * @param maxConsump 每天封顶价格
	 * @param maxfreeCompensationMoney 每天不计免赔的封顶价格
	 * @param freeCompensation 不计免赔价格，例如奇瑞是0.5元/30分钟
	 * @param nightTimeRate 夜间计费价格，现在没有夜间优惠，所以和白天的计费一样
	 * @param memberRights 会员权益
	 * @return
	 */
	public Map calcuteNightCalculate(String startTime , String endTime, String isFreeCompensate,
			Integer timeRate, Integer maxConsump, Integer maxfreeCompensationMoney, Integer freeCompensation, Integer nightTimeRate, MemberRights memberRights){

		/**价格*/
		Integer milesRate = 0;// 里程计费单位
		Integer minConsump = 0;// 最低消费
		int timeUnit = 30; //时间单位
		String endNightRate = "6" ;
		if(Integer.parseInt(endNightRate) < 10) {
			endNightRate = "0"+endNightRate ;
		}
		
		Integer nightTotalMinute = 0; //夜间总时间花费
		Integer nightMinuteCost = 0; //夜间总花费
		Integer nightReductionMoney = 0; //夜间总优惠
		
		Integer mile = 7 ; //里程为 0
		
		Date st = MoneyCalculate.getDate(startTime);
		Date et = MoneyCalculate.getDate(endTime);
		// 计算时间长度，并且根据时间单位进行向上取整
		int minute = MoneyCalculate.getDateMin(st, et);
		Map<String, Object> map = new HashMap<String, Object>();
		
		int j = minute / timeUnit;
		int k = minute % timeUnit;
		int m = j + (k > 0 ? 1 : 0);
		map.put("minute", minute); //时间 分钟
		map.put("timeMny", m * timeRate); //时间花费
		map.put("milesMny", mile * milesRate); //里程花费
		map.put("单位",m ) ;
		int freeCompensationMoney = 0 ; //不计免赔
		
		if((minute - 1440) >= 1 ){ //时间有跨天数
			Integer money = 0  ;
			int night_m = 0;
			//查看一共约车多少天多少分钟
			String dayTime = MoneyCalculate.timeDifferenceDay(st, et) ;
			int day = Integer.parseInt(dayTime.split("#")[0]) ;
			int minutes = Integer.parseInt(dayTime.split("#")[1]) ;
			money =  day*maxConsump ; //天数上限金额
			double dayFreeCompensationMoney =  day * maxfreeCompensationMoney ; //天的不计免赔价格
			
			int unitCount = minutes / timeUnit; //分除以时间计费单位获取单位个数
			int unitValue = minutes % timeUnit; //余数
			int unitCounts = unitCount + (unitValue > 0 ? 1 : 0); //单位数
			int minuteMoney =  unitCounts*timeRate; //计算分钟金额
			System.err.println("开始时间："+startTime + " 结束时间： " +endTime +"跨天时间："+dayTime);
			Integer minuteFreeCompensationMoney =  unitCounts*freeCompensation.intValue(); //计算分钟不计免赔
			if(minuteFreeCompensationMoney >= maxfreeCompensationMoney){
				minuteFreeCompensationMoney = maxfreeCompensationMoney ;
			}
			map.put("夜间单位",night_m ) ;
			nightMinuteCost = night_m * nightTimeRate; //时间花费
			nightReductionMoney = night_m * ( timeRate - nightTimeRate) ; //夜间总优惠

			map.put("nightTotalMinute", nightTotalMinute ); //夜间总时间花费
			map.put("nightMinuteCost", nightMinuteCost); //夜间总花费
			map.put("nightReductionMoney", nightReductionMoney); //夜间总优惠

			/******************************夜间计费 end********************************************/
			if(null != isFreeCompensate && !isFreeCompensate.equals("0")){ //不计免赔
				freeCompensationMoney = (int)minuteFreeCompensationMoney + (int)dayFreeCompensationMoney ;
			}
			map.put("freeCompensationMoney",freeCompensationMoney ); //不计免赔金额
			Integer lastMinuteMoney = minuteMoney - nightReductionMoney ;
			if(lastMinuteMoney >= maxConsump){ //分钟金额大于最高消费以最高消费计算
				lastMinuteMoney = maxConsump ;
				map.put("nightMinuteCost", 0); //夜间总花费
				map.put("nightReductionMoney", 0); //夜间总优惠
			}
			Integer discountMoney = 0;
			if (memberRights != null) {
				discountMoney = money+ lastMinuteMoney - (int)((money + lastMinuteMoney) * memberRights.getValue() /Double.valueOf(100));
				map.put("discountMoney", money+ lastMinuteMoney - (int)((money + lastMinuteMoney) * memberRights.getValue() /Double.valueOf(100)));//折扣优惠金额
			}else {
				map.put("discountMoney", 0);
			}
			map.put("money", money - discountMoney + lastMinuteMoney + (int)(freeCompensationMoney)); //总花费

			
		}else{
			if(StringUtils.isNotBlank(isFreeCompensate) && !isFreeCompensate.equals("0")){ //不计免赔
				freeCompensationMoney = (int) (freeCompensation *Math.ceil(((double)minute/30))) ;
				if(freeCompensationMoney > maxfreeCompensationMoney){ 
					freeCompensationMoney = maxfreeCompensationMoney ;
				}
			}
			int night_m = 0 ;
			map.put("夜间单位",night_m ) ;
			nightMinuteCost = night_m * nightTimeRate; //时间花费
			nightReductionMoney = night_m * ( timeRate - nightTimeRate) ; //夜间总优惠

			map.put("nightTotalMinute", nightTotalMinute ); //夜间总时间花费
			map.put("nightMinuteCost", nightMinuteCost); //夜间总花费
			map.put("nightReductionMoney", nightReductionMoney); //夜间总优惠

			/******************************夜间计费 end********************************************/
			
			map.put("freeCompensationMoney", freeCompensationMoney); //不计免赔金额
			Integer d = m * timeRate + mile * milesRate;
			Integer money = d - nightReductionMoney  ; //消费金额 = 白天计费金额 - 夜间优惠金额
			Integer discountMoney = 0 ; //折扣优惠金额
			if (money <= minConsump){
				money = minConsump;
			}else if(money >= maxConsump){
				money = maxConsump ;
				if (memberRights != null) {//如果会员权益大于100的，需要加上折扣金额
					discountMoney = money - (int)(money * memberRights.getValue() / Double.valueOf(100));
				}
				map.put("nightMinuteCost", 0); //夜间总花费
				map.put("nightReductionMoney", 0); //夜间总优惠
			}else{
				//会员享受最高消费后，不参与打折,未享受最高消费，则根据会员权益进行折扣
				if(nightTotalMinute > 0) { //享受夜间优惠不享受折扣
					discountMoney = 0 ;
				}else{
					if(memberRights != null){ 
						discountMoney = money - (int)(money * memberRights.getValue() / Double.valueOf(100)) ; //将存在小数点数据直接转换成int ，去掉小数点 
					}
				}
			}
			map.put("discountMoney", discountMoney); //折扣金额
			map.put("money", money - discountMoney +(int)(freeCompensationMoney) ); //总花费 = 订单金额 - 折扣金额 + 不计免赔费
		}
		return map ;
	}
	
	public Map getMoneyByOrderId(String orderId, String endTime){
		List<TimeShareOrder> orderList = timeShareOrderMapper.queryOrderByOrderId(orderId);
		final TimeShareOrder timeShareOrder = orderList.get(0);
		String startTime = MoneyCalculate.format(timeShareOrder.getBeginTime());
		String isFreeCompensate = timeShareOrder.getIsFreeCompensate();
		String lpn = timeShareOrder.getLpn();
		/**车辆计费策略 */
		Car car = carMapper.selectByLpn(lpn);
		CarRun carRun = carRunMapper.queryCarRun(lpn);
		if(null == carRun){
			return null;
		}
		String cityCode = carRun.getCityCode();
		Integer carTypeId = car.getModelId();
		TimeShareRate timeShareRate = timeShareRateMapper.getByCityCodeAndCarTypeId(cityCode, carTypeId);
		Integer timeRate = null;
		if(null!=timeShareOrder.getTimeRate()){
			timeRate = timeShareOrder.getTimeRate();
		}else{
			timeRate = timeShareRate.getTimeRate();
		}
		Integer timeUints = timeShareRate.getTimeUnit();
		Integer uintAmount = 1440/timeUints;
		Integer remainder = 1440%timeUints;
		uintAmount = uintAmount + (remainder > 0 ? 1 : 0);
		Integer maxConsump = null;
		if(uintAmount*timeRate<=timeShareRate.getMaxConsump()){
			maxConsump = uintAmount*timeRate;
		}else{
			maxConsump = timeShareRate.getMaxConsump();
		}
		Integer maxfreeCompensationMoney = timeShareRate.getMaxFreeCompensationPrice().intValue();
		Integer freeCompensation = timeShareRate.getFreeCompensationPrice().intValue();
		Integer nightTimeRate = timeShareRate.getNightTimeRate();
		Member member = memberMapper.selectByPrimaryKey(Integer.parseInt(timeShareOrder.getMemberId()));
		MemberRights memberRights = null ;
		List<MemberRights> memberRightsList = memberRightsMapper.queryMemberRigthtsByMemberLevel(member.getLevelCode());
		if(null != memberRightsList){
			boolean flag = true;
			for (MemberRights right : memberRightsList) {
				if (right.getIsUseInBirthday() != null && right.getIsUseInBirthday().equals(1)) {//1表示不在生日时使用
					if (flag) {
						memberRights = right;
						flag = false;
					}
				}
			}			
		}
		Map<String, Object> paramMaps = new ConcurrentHashMap<String, Object>();
		paramMaps.put("orderId", orderId);
		// 若果count數量是大於0,說明這個單是企業員工訂單,企業員工單不計入個人權益
		Integer count = enterpriseUseCarApplyMapper.selectApplyRecords(paramMaps);
		if(0<count){
			return calcuteNightCalculate(startTime, endTime, isFreeCompensate, timeRate, maxConsump, maxfreeCompensationMoney, 
					freeCompensation, nightTimeRate, null);
		}
		return calcuteNightCalculate(startTime, endTime, isFreeCompensate, timeRate, maxConsump, maxfreeCompensationMoney, 
				freeCompensation, nightTimeRate, memberRights);
	}
	
	public Map getMoneyByOrderId(String orderId){
		return getMoneyByOrderId(orderId,MoneyCalculate.format(new Date()));
	}
}
