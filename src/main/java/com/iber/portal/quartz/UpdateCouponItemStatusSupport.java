package com.iber.portal.quartz;

import java.util.Calendar;
import java.util.List;

import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.MemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.model.coupon.CouponItem;
import com.iber.portal.service.coupon.CouponItemService;


/**
 * @Author:cuichongc
 * @Version:1.0
 */
public class UpdateCouponItemStatusSupport extends QuartzJobBean {
	
	Logger log = LoggerFactory.getLogger(UpdateCouponItemStatusSupport.class);
	
	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private MemberMapper memberMapper;

	private static final String FOUR_AUTOMATIC_GIVE = "fourAutomaticGive";//四星
	private static final String FIVE_AUTOMATIC_GIVE = "fiveAutomaticGive";//五星
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.info("活动自动开启和结束 -----start");
		List<CouponItem> couponItems = couponItemService.selectAll();
		
		for(CouponItem ci : couponItems){
			//四星,五星除外//
			if(!StringUtils.isBlank(ci.getItemcode())){
				if(!FOUR_AUTOMATIC_GIVE.equals(ci.getItemcode())||!FIVE_AUTOMATIC_GIVE.equals(ci.getItemcode())){
					if(ci.getStartTime() != null && ci.getEndTime() != null){
						Calendar cd = Calendar.getInstance();
						//判断当前时间是否在次活动时间段
						if(cd.getTime().after(ci.getStartTime()) && cd.getTime().before(ci.getEndTime())){
							ci.setStatus(1);
							couponItemService.updateByPrimaryKey(ci);
						}else{
							ci.setStatus(0);
							couponItemService.updateByPrimaryKey(ci);
							//要把会员的"已领取了只有一次参与机会的优惠券"的状态清0
							if(SysConstant.COUPON_ITEM_CODE_CHARGE.equals(ci.getItemcode())){
								memberMapper.resetDrewOnceCouponStatus();
							}
						}
					}
				}
			}
			
		}
		log.info("活动自动开启和结束 -----end");
	}
	
}
