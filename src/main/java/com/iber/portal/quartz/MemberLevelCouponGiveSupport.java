package com.iber.portal.quartz;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONObject;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.model.coupon.CouponItem;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.base.SystemMsgLogService;
import com.iber.portal.service.coupon.CouponItemService;
import com.iber.portal.service.coupon.CouponService;
import com.iber.portal.util.BuildCouponNo;
import com.iber.portal.util.DateTime;

/**
 * @Author:cuichongc
 * @Version:1.0
 */
public class MemberLevelCouponGiveSupport extends QuartzJobBean {
	
	Logger log = LoggerFactory.getLogger(MemberLevelCouponGiveSupport.class);
	
	@Autowired
	private  MemberService memberService;
	
	@Autowired
	private  CouponItemService couponItemService;
	
	@Autowired
	private  CouponService couponService;
	
	@Autowired
	private SystemMsgLogService systemMsgLogService;

	/* 
	 * 四星、五星会员月赠送优惠卷
	 */
	private static final String FOUR_AUTOMATIC_GIVE = "fourAutomaticGive";//四星
	private static final String FIVE_AUTOMATIC_GIVE = "fiveAutomaticGive";//五星
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.info("四星,五星会员赠送优惠卷开始");
		//查询所有的四星五星用户
		List<Member> members = memberService.selectFourFiveStar();
		if(members !=null && members.size() != 0){
			for(Member ms : members){
				//判断账号状态是否被冻结
				if(ms.getAccoutStatus().equals("0") || ms.getAccoutStatus().equals("1")){
					CouponItem couponItem;
					LinkedList<Coupon> insertList = new LinkedList<Coupon>();
					/**
					 * 四星赠送
					 */
					if(ms.getLevelCode().equals(4)){
						
						couponItem = couponItemService.selectByItemCode(FOUR_AUTOMATIC_GIVE,"00");
						 /**
						  * 遍历数量：num
						  */
						int num = couponItem.getNumber();
		
						if(couponItem != null && couponItem.getStatus().equals(1)){
							
							for (int i = 0; i < num; i++) {
									Coupon coupon = new Coupon();
									
									//批量处理数据
									batchInsertCoupon(coupon,couponItem, ms.getCityCode(), ms.getId());
									//批量插入
									insertList.add(coupon);
								}
								if(null != insertList && !insertList.isEmpty()){
									couponService.insertFourFiveStartBatch(insertList);
								}else{
									log.info("四星赠送数据异常!");
								}
								//推送消息	
								sendPush(ms.getPhone(), num, ms.getId(), couponItem.getBalance(),String.valueOf(ms.getLevelCode()));	
						 }
					}
					/**
					 * 五星赠送
					 */
					if(ms.getLevelCode().equals(5)){
						
						couponItem = couponItemService.selectByItemCode(FIVE_AUTOMATIC_GIVE,"00");
						
						int num = couponItem.getNumber();
						
						if(couponItem != null && couponItem.getStatus().equals(1)){
							
							for (int i = 0; i < num; i++) {
								
								Coupon coupon = new Coupon();
								//批量处理数据
								batchInsertCoupon(coupon,couponItem, ms.getCityCode(), ms.getId());
								//批量插入
								insertList.add(coupon);
							 }
							
							 if(null != insertList && !insertList.isEmpty()){
								 couponService.insertFourFiveStartBatch(insertList);
							 }else{
								 log.info("五星赠送数据异常!");
							 }
							 
							 //推送消息
							 sendPush(ms.getPhone(), num, ms.getId(), couponItem.getBalance(),String.valueOf(ms.getLevelCode()));
						 }
					}
				}
			}
		}
		log.info("四星,五星会员赠送优惠卷结束");
	}	
				/**
				 * 推送消息
				 */
	protected void sendPush(String phone, Integer num, Integer id,
			Integer balance, String levelCode) {
		DecimalFormat df=new DecimalFormat("0.00");

		if (Integer.valueOf(levelCode.trim()) == 4) {
			levelCode = "四";
			// System.out.println(levelCode);
		} else if (Integer.valueOf(levelCode.trim()) == 5) {
			levelCode = "五";
			// System.out.println(levelCode);
		}
		// 发送推送消息
		PushCommonBean push = new PushCommonBean("server_push_bing_coupon",
				"1", "【宜步出行】尊敬的" + levelCode + "星会员，" + num + "张"
						+ df.format((float)balance / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！", "");
		String memberPhone = phone;// 获取会员手机号
		List<String> cidList = PushClient.queryClientId(memberPhone);
		for (String memberCid : cidList) {
			PushClient.push(memberCid, JSONObject.fromObject(push));
		}
		// 将消息保存
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("coupon");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId(id);
		systemMsgLog.setMsgTitle(levelCode + "星赠送优惠券");
		systemMsgLog.setMsgContent("【宜步出行】尊敬的" + levelCode + "星会员，" + num + "张"
				+ df.format((float)balance / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！");
		systemMsgLogService.insertSelective(systemMsgLog);

	}
	/**
	 * batch 数据
	 */
	protected void batchInsertCoupon(Coupon coupon,CouponItem couponItem,String cityCode,Integer id){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		Calendar cd = Calendar.getInstance();
		Date start = cd.getTime();
		/**
		 * deadline:期限
		 */
		cd.add(Calendar.DATE,couponItem.getDeadline());
		Date end = cd.getTime();

		coupon.setCityCode(couponItem.getCityCode());
		coupon.setCouponNo(BuildCouponNo.generateShortUuid());//优惠卷编号
		coupon.setTitle(couponItem.getItemname());//优惠卷名称
		coupon.setMemberId(id);//会员id
		coupon.setBalance((couponItem.getBalance()).doubleValue());//面值(分)
		String batchNo = sdf.format(Calendar.getInstance().getTime());
		coupon.setBatchNo(batchNo);//批次号

		coupon.setStartTime((DateTime.getStartTimeOfDay(start)));
		coupon.setEndTime((DateTime.getEndTimeOfDay(end)));

		coupon.setStatus("1");
		coupon.setCategory(0);
		coupon.setUseStatus("0");
		coupon.setCreateTime(start);

		coupon.setType("1");//1表示自动
		coupon.setItemCode(couponItem.getItemcode());//券编码
		//券类型 0-现金券,1-满减券
		if(couponItem.getUseType()==null){
			coupon.setUseType(0);
		}else{
			coupon.setUseType(couponItem.getUseType());
		}
		if(couponItem.getMinUseValue()==null){
			coupon.setMinUseValue(0);
		}else{
			coupon.setMinUseValue(couponItem.getMinUseValue());
		}

	}
}
