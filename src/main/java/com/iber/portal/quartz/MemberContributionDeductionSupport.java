/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.util.SendSMS;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.dao.base.WZQueryMapper;
import com.iber.portal.dao.member.MemberBehaviorMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.model.base.WZQuery;
import com.iber.portal.model.member.MemberBehavior;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.base.MemberCardService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.member.MemberContributedDetailService;
import com.iber.portal.vo.order.UnpaidMemberVo;

/**
 * 定时统计七天未支付订单，然后扣除对应会员的贡献值
 * @author ouxx
 * @since 2016-12-27 下午4:58:09
 *
 */
public class MemberContributionDeductionSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberBehaviorMapper memberBehaviorMapper;
	
	@Autowired
	private MemberService memberService;
	
//	@Autowired
//	private MemberCardService memberCardService;
	
//	@Autowired
//	private SysDicService sysDicService;
	
	@Autowired
	private MemberContributedDetailService memberContributedDetailService;
	
	@Autowired
	private WZQueryMapper wzQueryMapper;//违章记录
	
	@Autowired
	private SysParamMapper sysParamMapper;
	
	private static final String NOPAY_SMS_NOTIFY_TEMPLATE_ID = "2696";
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
//	@Override
//	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//		log.info("每天定时统计七天未支付订单，然后扣除对应会员的贡献值");
//		List<UnpaidMemberVo> memberList = this.memberService.queryMemberWithUnpaidOrder();
//		
//		List<MemberContributedDetail> contributedList = new ArrayList<MemberContributedDetail>();
//		if(null != memberList && !memberList.isEmpty()){
//			
//			Date now = new Date();
//			List<SysDic> dicList = this.sysDicService.selectListByDicCode(CONTRIBUTED_TYPE);
//			if(null != dicList && !dicList.isEmpty()){
//				SysDic dic = dicList.get(0);
//				String code = dic.getCode();
//				if(StringUtils.isNotBlank(code)){
//					Integer deductWeight = Integer.valueOf(code);//扣分权重
//					for(UnpaidMemberVo vo : memberList){
//						String orderId = vo.getOrderId();
//						int cnt = this.memberContributedDetailService.getCntByTypeAndObjId(CONTRIBUTED_TYPE, orderId);
//						//同一个订单，一次未支付就只扣一次
//						if(0 < cnt){
//							continue;
//						}
//						
//						MemberContributedDetail contributed = new MemberContributedDetail();
//						//百分数，要除以100，若是金额类型，则要再除以100，则除以10000
//						Double valDelta = vo.getMoney() * deductWeight / 10000.0;
//						Integer contributedValDelta = (int) Math.round(valDelta);
//						contributed.setContributedValDelta(contributedValDelta);
//						contributed.setMemberId(vo.getMemberId());
//						contributed.setObjId(vo.getOrderId());
//						contributed.setType(CONTRIBUTED_TYPE);
//						contributed.setCreateTime(now);
//						
//						contributedList.add(contributed);
//						//扣减会员卡表中的贡献值
//						this.memberCardService.decreContributedVal(vo.getMemberId(), contributedValDelta);
//					}
//					
//					if(null != contributedList && !contributedList.isEmpty()){
//						this.memberContributedDetailService.insertBatch(contributedList);
//					}
//				}
//			}
//		}
//		
//		
//	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		//因未支付订单而扣分
		updateByUnpaidOrder();
		//因违章超过30天不作违章处理的,-100贡献值
		//updateByBreakRulesNoDealt();
	}
	
	/**
	 * 因未支付订单而扣分
	 * 
	 * @author ouxx
	 * @date 2017-4-18 下午5:16:52
	 */
	private void updateByUnpaidOrder(){
		log.info("每天定时统计七天未支付订单，然后扣除对应会员的贡献值");
		MemberBehavior behavior = memberBehaviorMapper.getByType(MemberBehaviorNameEnum.UNPAID_ORDER.getName());
		if(null == behavior){
			log.error("不存在名为" + MemberBehaviorNameEnum.UNPAID_ORDER + "的会员行为配置");
			return;
		}
		Integer days = StringUtils.isNotBlank(behavior.getConditionVal()) ?
				Integer.valueOf(behavior.getConditionVal()) : 7;
		List<UnpaidMemberVo> memberList = this.memberService.queryMemberWithUnpaidOrder(days);
		
		if(null != memberList && !memberList.isEmpty()){
			Date now = new Date();
			for(UnpaidMemberVo vo : memberList){
				String orderId = vo.getOrderId();
				if(StringUtils.isNotBlank(orderId)){
					int cnt = this.memberContributedDetailService.getCntByTypeAndObjId(
							MemberBehaviorNameEnum.UNPAID_ORDER.getName(), orderId);
					//同一个订单，一次未支付就只扣一次
					if(0 < cnt){
						continue;
					}
				}
				
				this.memberContributedDetailService.insertMemberContributedDetail2(now,
						vo.getMemberId(), orderId, MemberBehaviorNameEnum.UNPAID_ORDER.getName(), null);
			}
			
		}
		
		//短信通知6天未支付的用户
		 SysParam sysParam = sysParamMapper.selectByKey("noPay_sms_notify_day");
		 Integer day = sysParam == null ? 6 : Integer.valueOf(sysParam.getValue());
		 List<UnpaidMemberVo> noPayMemberList = this.memberService.queryNoPayMemberByBetwentDay(day,day+1);
		 if(noPayMemberList != null && !noPayMemberList.isEmpty()){
			 SysParam sendUrl = sysParamMapper.selectByKey("send_sms_url");
			 String url = "";
			 if(sendUrl !=null) url=sendUrl.getValue();
			 for (UnpaidMemberVo unpaidMemberVo : noPayMemberList) {
				//是发生短信提醒用户 
					try {
						sendSMS(url, unpaidMemberVo.getPhone(), "",NOPAY_SMS_NOTIFY_TEMPLATE_ID , day.toString());
					} catch (Exception e) {
						log.error("MemberContributionDeductionSupport updateByUnpaidOrder 发送未支付提醒短信失败 ; 异常信息："+e.getMessage());
					}
			}
		 }
	}
	
	/**
	 * 短信发送
	 * 
	 * @param url
	 * @param telephoneNo
	 * @param ipAddress
	 * @param templateId
	 * @param contentParam
	 * @return
	 * @throws Exception
	 */
	public String sendSMS(String url,String telephoneNo,String ipAddress,String templateId,String contentParam) throws Exception{
		//Map<String, String> paramss = new HashMap<String, String>();
		//获取加密token
		/*String encryptToken = SendSMS.encryptBySalt(telephoneNo);
		String param = "{\"telephoneNo\":\""+ telephoneNo+ "\",\"ipAddress\":\""+ipAddress+"\",\"templateId\":\""+templateId+"\",\"contentParam\":\""+ new String(contentParam.getBytes("utf-8"),"ISO-8859-1") + "\",\"token\":\""+encryptToken+"\"}";
		paramss.put("msgContentJson", param);*/
		SendSMS.send(telephoneNo,ipAddress,Integer.parseInt(templateId),contentParam);

		return "";
	}
	
	/**
	 * BREAK_RULES_NO_DEALT 因违章超过30天不作违章处理的,-100贡献值
	 * 
	 * @author ouxx
	 * @date 2017-4-18 下午9:55:12
	 */
	private void updateByBreakRulesNoDealt(){
		log.info("因违章超过30天不作违章处理的,-100贡献值");
		MemberBehavior behavior = memberBehaviorMapper.getByType(MemberBehaviorNameEnum.BREAK_RULES_NO_DEALT.getName());
		if(null == behavior){
			log.error("不存在名为" + MemberBehaviorNameEnum.BREAK_RULES_NO_DEALT + "的会员行为配置");
			return;
		}
		Integer days = StringUtils.isNotBlank(behavior.getConditionVal()) ?
				Integer.valueOf(behavior.getConditionVal()) : 30;
		List<WZQuery> wzList = this.wzQueryMapper.queryNoDealt(days);
		if(null != wzList && !wzList.isEmpty()){
			Date now = new Date();
			for(WZQuery vo : wzList){
				String orderId = vo.getOrderId();
				if(StringUtils.isNotBlank(orderId)){
					int cnt = this.memberContributedDetailService.getCntByTypeAndObjId(
							MemberBehaviorNameEnum.BREAK_RULES_NO_DEALT.getName(), orderId);
					//同一个订单，一次未处理就只扣一次
					if(0 < cnt){
						continue;
					}					
				}
				
				this.memberContributedDetailService.insertMemberContributedDetail2(now,
						vo.getMemberId(), orderId, MemberBehaviorNameEnum.BREAK_RULES_NO_DEALT.getName(), null);
				
			}
			
		}
	}

}
