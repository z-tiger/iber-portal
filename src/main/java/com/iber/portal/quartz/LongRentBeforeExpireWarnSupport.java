package com.iber.portal.quartz;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.dayRent.DayLongRentOrderMapper;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.util.SendSMS;

/**
 * @Author:cuichongc
 * @Version:1.0
 */
public class LongRentBeforeExpireWarnSupport extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(LongRentBeforeExpireWarnSupport.class);
	
	@Autowired
	private DayLongRentOrderMapper dayLongRentOrderMapper ;
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	final LinkedList<Integer> idList = new LinkedList<Integer>();
	final LinkedList<SystemMsgLog> logList = new LinkedList<SystemMsgLog>();
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("日租订单到期前提醒开始.....") ;
		
		@SuppressWarnings("rawtypes")
		List<Map> longRentOrder = dayLongRentOrderMapper.beforeExpireWarn();
		Date now  = new Date() ;
		if(longRentOrder.size()>=1){
			for (Map map : longRentOrder) {
				Date endTime = (Date) map.get("returnCarTime");
				int expireTime = (int) ((endTime.getTime()-now.getTime())/3600000) ;
				/**发送短信和保存系统消息逻辑**/
				try {
					sendMsg(map, expireTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		
		logger.info("日租订单到期前提醒结束.....") ;
	}
	
	public void sendMsg(Map map,Integer expireTime){
		/**订单 序号reletNo 初始值为0**/
		Integer reletNo = Integer.parseInt(map.get("reletNo").toString());
		Integer duration = Integer.parseInt(map.get("duration").toString());//天数
		String phone = map.get("phone").toString();
		String remindTimes = map.get("remindTimes").toString();
		Integer orderId = Integer.parseInt(map.get("id").toString());
		Integer memberId = Integer.parseInt(map.get("memberId").toString());
		//保存系统消息
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("longRentOrderBeforeExpire");
		systemMsgLog.setCreateTime(new Date());
		
		if(reletNo == 0 && expireTime <= 24){
			if(StringUtils.equals(remindTimes, "0")){
				try {
					/**发送短信**/
					SendSMS.send(phone, "", 2701, "");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				/**保存到系统:到期前第一次提醒**/
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.LONG_RENT_BEFORE_EXPIRE_24);
				systemMsgLog.setMemberId(memberId);
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = "【宜步出行】尊敬的会员：您的约车订单将于24小时后到期，请及时续租或到期后及时将车辆在任一网点还车！";
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				dayLongRentOrderMapper.updateRemindTimes(orderId);
			}
		}
		if(reletNo == 0 && expireTime <= 4){
			if(StringUtils.equals(remindTimes, "1")){
				try {
					SendSMS.send(phone, "", 2702, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				/**到期前第二次提醒**/
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.LONG_RENT_BEFORE_EXPIRE_4);
				systemMsgLog.setMemberId(memberId);
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = "【宜步出行】尊敬的会员：您的日租订单即将于4小时后到期，请及时续租或将车辆在任一网点还车！";
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				dayLongRentOrderMapper.updateRemindTimes(orderId);
			}
		}
		/**续租说明：仅续租一天的，到期前24小时的短信不发送**/
		if(reletNo != 0 && duration > 1){
			if(expireTime <= 24 && StringUtils.equals(remindTimes, "0")){
				try {
					SendSMS.send(phone, "", 2703, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				/**续租到期前第一次提醒**/
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.RENEW_ORDER_BEFORE_EXPIRE_24);
				systemMsgLog.setMemberId(memberId);
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = "【宜步出行】尊敬的会员：您的约车订单将于24小时后到期，到期后请及时将车辆在任一网点还车！";
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				dayLongRentOrderMapper.updateRemindTimes(orderId);
			}else if(expireTime <= 4 && StringUtils.equals(remindTimes, "1")){
				try {
					SendSMS.send(phone, "", 2704, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				/**续租到期前第二次提醒**/
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.RENEW_ORDER_BEFORE_EXPIRE_4);
				systemMsgLog.setMemberId(memberId);
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = "【宜步出行】尊敬的会员：您的日租订单即将于4小时后到期，请及时将车辆在任一网点还车！";
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				dayLongRentOrderMapper.updateRemindTimes(orderId);
			}
		}
		if(reletNo != 0 && duration == 1){
			if(expireTime <= 4 && StringUtils.equals(remindTimes, "0")){
				try {
					SendSMS.send(phone, "", 2704, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				/**续租到期前第二次提醒**/
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.RENEW_ORDER_BEFORE_EXPIRE_4);
				systemMsgLog.setMemberId(memberId);
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = "【宜步出行】尊敬的会员：您的日租订单即将于4小时后到期，请及时将车辆在任一网点还车！";
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				dayLongRentOrderMapper.updateRemindTimes(orderId);
			}
		}
	}
}
