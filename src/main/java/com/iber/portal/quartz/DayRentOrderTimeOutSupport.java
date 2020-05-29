package com.iber.portal.quartz;

import net.sf.json.JSONObject;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;

public class DayRentOrderTimeOutSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("定时取消订单job开始执行") ;
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\"requestCancelDayRentOrderQuartz\",") ;
		sb.append("\"param\":\"{}\",") ;
		sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		SysParam sysParam = sysParamService.selectByKey("http_url") ;
		String json = "" ;
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json) ;
		log.info("定时取消订单job结束执行" + jsonObject) ;
		
		log.info("取消订单前5分钟短信提醒会员job开始执行" + jsonObject) ;
		StringBuffer sb1 = new StringBuffer("{") ;
		sb1.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\"sendWarnMessageQuratz\",") ;
		sb1.append("\"param\":\"{}\",") ;
		sb1.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb1.append("}") ;

		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), sb1.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), sb1.toString()) ;
		}
		JSONObject resultJsonObject = JSONObject.fromObject(json) ;
		log.info("取消订单前5分钟短信提醒会员job结束执行" + resultJsonObject) ;
	}
}
