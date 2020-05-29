/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
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

/**
 * 车位预约后，10分钟没有开启充电，自动取消，且要把地锁降下
 * @author ouxx
 * @since 2016-11-28 下午4:38:11
 *
 */
public class ChargingOrderTimeOutSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.info("定时任务开始执行") ;
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\"chargingOrderCancelQuartz\",") ;
		sb.append("\"param\":\"{}\",") ;
		sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		SysParam sysParam = sysParamService.selectByKey("http_url_c") ;
		String json = "" ;
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json) ;
		log.info("定时任务结束执行" + jsonObject) ;

	}

}
