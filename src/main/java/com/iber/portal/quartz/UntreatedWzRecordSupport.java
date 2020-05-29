package com.iber.portal.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.taskdefs.Zip;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.common.ParamUtil;
import com.iber.portal.zhima.vo.ZhimaRequestVo;

public class UntreatedWzRecordSupport extends QuartzJobBean {

	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	Logger logger = LoggerFactory.getLogger(OrderLongTimeNoPaySupport.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("-- 统计未处理的违章记录开始 --");
		SysParam sysParam = sysParamService.selectByKey("wz_handle_date");
		//筛选出未处理的，订单已经支付的违章记录
		List<ZhimaRequestVo> zhimaRequestVos = timeShareOrderService.queryUntreatedAndFinishOrder(Integer.parseInt(sysParam.getValue()));
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("2", "2", "509", "finish", "2", sysParamService);
		}
		//筛选出未处理的，订单未支付的违章记录
		Map<String, Object> map = new HashMap<String, Object>();
		//如果订单在规定时间内未支付
		sysParam = sysParamService.selectByKey("time_share_order_no_pay_date");
		String value = sysParam.getValue();
		map.put("status", 1);
		map.put("specifiedDate", Integer.valueOf(value));
		map.put("value", Integer.parseInt(sysParam.getValue()));
		zhimaRequestVos = timeShareOrderService.queryUntreatedAndNoPayInSpecifiedDate(map);
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("3", "0", "509", "noPay", "0", sysParamService);
		}
		//如果订单超过规定时间仍未支付
		map.put("status", 2);
		zhimaRequestVos = timeShareOrderService.queryUntreatedAndNoPayInSpecifiedDate(map);
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("4", "1", "509", "noPay", "1", sysParamService);
		}
		
		logger.info("-- 统计未处理的违章记录结束 --");
	}

}
