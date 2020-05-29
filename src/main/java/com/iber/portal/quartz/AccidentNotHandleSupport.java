package com.iber.portal.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class AccidentNotHandleSupport extends QuartzJobBean {
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	Logger logger = LoggerFactory.getLogger(OrderLongTimeNoPaySupport.class);

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("-- 统计会员未处理的事故开始 --");
		SysParam sysParam = sysParamService.selectByKey("accident_handle_date");
		String value = sysParam.getValue();
		//查询出事故未处理，但订单已经支付的记录
		List<ZhimaRequestVo> zhimaRequestVos = timeShareOrderService.queryNotHandleAccidentAndFinishOrderRecords(Integer.parseInt(value));
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("5", "2", "510", "finish", "2", sysParamService);
		}
		
		//查询出事故未处理，订单未支付的记录
		sysParam = sysParamService.selectByKey("time_share_order_no_pay_date");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		map.put("specifiedDate", Integer.parseInt(sysParam.getValue()));
		//如果在规定时间内
		zhimaRequestVos = timeShareOrderService.queryAccidentAndNoPayOrderRecords(map);
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("6", "0", "510", "noPay", "0", sysParamService);
		}
		//如果超过规定时间
		map.put("status", 2);
		zhimaRequestVos = timeShareOrderService.queryAccidentAndNoPayOrderRecords(map);
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("7", "1", "510", "noPay", "1", sysParamService);
		}
		logger.info("-- 统计会员未处理的事故结束 --");
	}

}
