package com.iber.portal.quartz;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.util.AES;
import com.iber.portal.util.PropertyUtil;
import com.iber.portal.util.SendSMS;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.DateTime;

import static com.iber.portal.util.AES.parseByte2HexStr;

public class MemberDriverIdcardValiditySupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private MemberService memberService ;
	
	@Autowired
    private SystemMsgMapper systemMsgMapper ;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	private SysParamService sysParamService ;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("会员驾驶证到期提醒 begin") ;
		List<Member> memberList = memberService.selectMemberDriverIdcardValidity() ;
		
		for(Member member : memberList) {
			member.setDriverIdcardUpdate(1) ; //需要重新上传驾驶证
			memberService.updateByPrimaryKeySelective(member);
			//短信通知消息推送
			//推送消息
			SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(26);
			SystemMsgLog systemMsgLog = new SystemMsgLog();
			systemMsgLog.setMsgType("driverIdcardValidity");
			systemMsgLog.setCreateTime(new Date());
			systemMsgLog.setMemberId( member.getId());
			systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
			systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}",DateTime.getDateString(member.getDriverIdcardValidityTime())));
			systemMsgLogMapper.insertSelective(systemMsgLog);
			PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
			try{
				//短信
				//生成token
				/*String encryptToken = SendSMS.encryptBySalt(member.getPhone());
				Map<String, String> params = new HashMap<String, String>();  
				String param = "{\"telephoneNo\":\""+member.getPhone()+"\",\"ipAddress\":\"\",\"templateId\":\"2671\",\"contentParam\":\""+new String((DateTime.getDateString(member.getDriverIdcardValidityTime())).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
				params.put("msgContentJson", param); 
				HttpsClientUtil.post(sysParamService.selectByKey("send_sms_url").getValue()+"",params) ;*/
				SendSMS.send(member.getPhone(),"",2671,DateTime.getDateString(member.getDriverIdcardValidityTime()));
			}catch (Exception e) {
			}
			
			List<String> memberCidList = PushClient.queryClientId(member.getPhone());
			for (String phoneCid : memberCidList) {
				PushClient.push(phoneCid, net.sf.json.JSONObject.fromObject(systemPush));
			}
		}
		
		log.info("会员驾驶证到期提醒 end") ;
	}
}
