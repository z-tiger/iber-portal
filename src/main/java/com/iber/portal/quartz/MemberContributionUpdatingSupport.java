/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.time.FastDateFormat;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.service.base.MemberLevelService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.coupon.CouponService;
import com.iber.portal.service.member.MemberRightsService;
import com.iber.portal.util.BuildCouponNo;
import com.iber.portal.vo.member.MemberCouponVo;

/**
 * 每月1号更新会员的贡献值和等级编码，并根据更新后的等级来给会员发放相应权益的优惠券
 * @author ouxx
 * @since 2016-12-27 下午1:51:15
 *
 */
public class MemberContributionUpdatingSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberLevelService memberLevelService;

	@Autowired
	private MemberRightsService memberRightsService;
	
	@Autowired
	private CouponService couponService;
	
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.info("每月1号更新会员的贡献值和等级编码，并根据更新后的等级来给会员发放相应权益的优惠券");
		//需要先更新贡献值，再更新会员等级编码，因为等级编码是按照贡献值来定的
		memberService.updateContributedVal();
		
		List<MemberLevel> levelList = memberLevelService.selectAllMemberLevel();
		if(null != levelList && !levelList.isEmpty()){
			memberService.updateLevelCode(levelList);
		}
		
		List<MemberCouponVo> rightsList = memberRightsService.getMemberCoupons();
		//用memberId和等级名称levelName来标识是否已给用户发因等级变化而获得的优惠券(绑定优惠券时，levelName作为优惠券的title插入)
		Iterator<MemberCouponVo> it = rightsList.iterator();
		while(it.hasNext()){
			MemberCouponVo vo = it.next();
			String levelName = vo.getLevelName();
			Integer memberId = vo.getMemberId();
			int existCnt = this.couponService.getCntByTitleAndMemberId(memberId, levelName);
			//如果已经存在，则不给此会员发放此优惠券
			if(0 < existCnt){
				it.remove();
			}
		}
		
		List<Coupon> couponList = new ArrayList<Coupon>();
		for(MemberCouponVo vo : rightsList){
			Integer number = vo.getNumber();//优惠券张数
			for(int i = 0; i < number; ++i){
				Coupon coupon = new Coupon();
				Calendar now = Calendar.getInstance();
				FastDateFormat df = FastDateFormat.getInstance("yyyyMMddHHmmss");
				String batchNo = df.format(now);
				String couponNo = BuildCouponNo.generateShortUuid();
				coupon.setDescription("会员等级更新为" + vo.getLevelName());
				coupon.setTitle(vo.getLevelName());//把等级名称作为title
				coupon.setMemberId(vo.getMemberId());
				coupon.setBalance(vo.getValue() * 100.0);
				coupon.setCouponNo(couponNo);
				coupon.setBatchNo(batchNo);
				coupon.setCreateTime(now.getTime());
				coupon.setStartTime(now.getTime());
				now.add(Calendar.DAY_OF_MONTH, 30);//优惠券期限为30天
				coupon.setEndTime(now.getTime());
				coupon.setStatus("1");
				coupon.setUseStatus("0");
				coupon.setType("1");//自动
				
				couponList.add(coupon);
				
			}

//			//发送推送消息
//			PushCommonBean push = new PushCommonBean("server_push_bing_coupon",
//					"1", "尊敬的会员，" + number + "张" + vo.getValue() + "元(折)面值优惠券已经送到您账户，请注意查收！","") ;
//			String memberPhone = vo.getPhone();//获取会员手机号
//			List<String> cidList = PushClient.queryClientId(memberPhone);
//			for (String memberCid : cidList) {
//				PushClient.push(memberCid, JSONObject.fromObject(push));
//			}
		}
		
		//发放优惠券
		if(!couponList.isEmpty()){
			this.couponService.insertBatch(couponList);	
		}
	}

}
