package com.iber.portal.quartz;

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

public class WarnInfoSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("预警信息执行开始") ;
		
		SysParam sysParam = sysParamService.selectByKey("http_url") ;
		SysParam chargingSysParam = sysParamService.selectByKey("http_url_c") ;
		
		//会员预约车位，只停车，不充电，平台对地锁状态进行预警，让客服提醒会员或者现场工作人员提醒移车位
		String occupyParkWithoutChargingParam =getHttpParam("saveOccupyParkWithoutChargingSysWarnInfo") ;
		executeGet(chargingSysParam, occupyParkWithoutChargingParam) ;
		
		String cancelOrderParam =getHttpParam("saveCancelOrderSysWarnInfo") ;
		executeGet(sysParam, cancelOrderParam) ;

		String gpsParam =getHttpParam("saveLongTimeCarNotUploadGpsSysWarnInfo") ;
		executeGet(sysParam, gpsParam) ;

		String returnCarParam =getHttpParam("saveLongTimeNotReturnCarSysWarnInfo") ;
		executeGet(sysParam, returnCarParam) ;
		
		String parkParam =getHttpParam("saveParkLittleCarSysWarnInfo") ;
		executeGet(sysParam, parkParam) ;
		
		log.info("预警信息执行结束") ;
	}
	
	public String getHttpParam(String method){
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\""+method+"\",") ;
		sb.append("\"param\":\"{}\",") ;
		sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		return sb.toString() ;
	}
	
	public void executeGet(SysParam sysParam , String params){
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			HttpsClientUtil.get(sysParam.getValue(), params) ;
		}else{
			HttpUtils.commonSendUrl(sysParam.getValue(), params) ;
		}
	}
}
