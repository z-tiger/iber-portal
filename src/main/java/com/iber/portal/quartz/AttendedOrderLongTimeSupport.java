package com.iber.portal.quartz;
import com.alibaba.fastjson.JSON;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderAttachedMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.SendSMS;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AttendedOrderLongTimeSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private TimeShareOrderAttachedMapper timeShareOrderAttachedMapper ;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;

	@Autowired
	private SystemMsgMapper systemMsgMapper;

	@Autowired
	private SysParamService sysParamService;

	private static List<Map> msgList = new ArrayList<Map>();
	ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	/**
	 * 发送短信匿名线程
	 */
	Runnable runnable = new Runnable() {
		public void run() {
			sendMsg();
		}
	};
	private static Boolean isConfig = true;
	@Override
	protected void executeInternal(JobExecutionContext arg0) {
		log.info("附属订单超期未支付检查") ;
		/*2695*/
		List<Map> longTimeOrder = timeShareOrderAttachedMapper.findLongTimeUnpay();
		if (longTimeOrder.size()>=1){
			for (Map map:longTimeOrder){
				try {
					if (Integer.parseInt(map.get("dateidff").toString())%30==0){
						sendLogic(map);//执行发送消息逻辑和短信逻辑
					}
				} catch (Exception e) {
				}
			}
		}
		if (isConfig){ //初始化任务执行
			// 延时六小时执行，隔24小时执行一次
			service.scheduleAtFixedRate(runnable, 6, 24, TimeUnit.HOURS);
			isConfig = false;//下次不再执行
		}
		log.info("附属订单超期未支付检查结束") ;
	}

	public void sendLogic(Map map){
		/**发送短信**/
		Map<String, String> params = new HashMap<String, String>();
		Map map2 = new HashMap();
		//获取加密token
		String encryptToken = SendSMS.encryptBySalt(map.get("phone").toString());
		map2.put("telephoneNo",map.get("phone"));
		map2.put("ipAddress","");
		map2.put("templateId","2698");
		map2.put("token",encryptToken);
		try {
			map2.put("contentParam",new String(map.get("type").toString().getBytes("utf-8"),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params.put("msgContentJson", JSON.toJSONString(map2));
		msgList.add(params);//加入到发短信列表


		/**保存到系统**/
		SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.ATTACHEDORDER);
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("refund");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId(Integer.parseInt(map.get("id").toString()));
		systemMsgLog.setMsgTitle(systemMsg.getMsgTitle().replace("{0}",map.get("type").toString()));
		String content = "【宜步出行】尊敬的会员，您的账户存在长时间未处理的（{0}）订单，为不影响您的用车，请及时支付。如有疑问，请联系客服4000769755".replace("{0}",map.get("type").toString());
		systemMsgLog.setMsgContent(content);
		systemMsgLogMapper.insertSelective(systemMsgLog);

		/**个推消息**/
		PushCommonBean push = new PushCommonBean("push_server_system_msg_log",
				"1", "【宜步出行】尊敬的会员，您的账户存在长时间未处理的（{0}）订单，为不影响您的用车，请及时支付。如有疑问，请联系客服4000769755".replace("{0}",map.get("type").toString()),systemMsgLog) ;
		List<String> cidList = PushClient.queryClientId(map.get("phone").toString());
		for (String memberCid : cidList) {
			PushClient.push(memberCid,push);
		}
	}

	/**
	 * 延时发送短信，避免出发的时候是半夜发短信
	 */
	public void sendMsg(){
		if (msgList.size()>0) {
			for (int i = 0; i < msgList.size(); i++) {
				//HttpsClientUtil.post(sysParamService.selectByKey("send_sms_url").getValue() + "", msgList.get(i));
				SendSMS.send(msgList.get(i));

			}
			msgList.clear();
		}
	}
}
