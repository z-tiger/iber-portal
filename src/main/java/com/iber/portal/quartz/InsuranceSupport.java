package com.iber.portal.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.SendMail;
import com.iber.portal.model.insurance.Insurance;
import com.iber.portal.service.insurance.InsuranceService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.DateTime;

public class InsuranceSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
    private InsuranceService insuranceService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("定时任务开始执行，车辆保单到期提醒。") ;
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("nextMonthDate", DateTime.getNextMonthDateString());
//		List<Insurance> list = insuranceService.selectByNextMonthDate(paramMap);
		List<Insurance> list = insuranceService.selectLpn();
		if(list.size()>0){
			String subject = "车辆保单到期提醒";
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				String lpn = list.get(i).getLpn();
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("lpn", lpn);
				List<Insurance> insuranceList = insuranceService.selectByLpn(paramMap);
				if(insuranceList.size()>0){
					try {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
						Date endTimeD = insuranceList.get(0).getEndTime();
						String nextMonthDate  = DateTime.getNextMonthDateString();
						Date nextMonthD = df.parse(nextMonthDate);
						if(nextMonthD.getTime()>endTimeD.getTime()){
							String endTime = df.format(endTimeD);  
							sb.append("<b>车牌号码：</b>").append(lpn).append("        <b>保险到期时间：</b>").append(endTime).append("<br/>");
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			String content = sb.toString();
			if(!"".equals(content)){
				String _to = sysParamService.selectByKey("bx_send_email_to").getValue();
				try {
					SendMail sendMail = new SendMail(sysParamService);
					sendMail.sendMail(subject, content, _to);
				} catch (Exception e) {
					log.error("车辆保单到期提醒邮件发送失败！") ;
					e.printStackTrace();
				}
			}
		}
		
		log.info("定时任务结束执行，车辆保单到期提醒。") ;
	}
}
