package com.iber.portal.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

/**
 * 统计未支付订单，并反馈给芝麻信用
 * @author 刘晓杰
 *
 */
public class OrderLongTimeNoPaySupport extends QuartzJobBean {
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	Logger logger = LoggerFactory.getLogger(OrderLongTimeNoPaySupport.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("-- 统计未支付订单开始  --");
		SysParam sysParam = sysParamService.selectByKey("time_share_order_no_pay_date");
		String value = sysParam.getValue();
		Map<String, Object> map = new HashMap<String, Object>();
		//1 查询在规定日期之内未支付的订单
		map.put("status", 1);
		map.put("specifiedDate", Integer.valueOf(value));
		List<ZhimaRequestVo> zhimaRequestVos = timeShareOrderService.queryAllNoPayOrderInSpecifiedDate(map);
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("0", "0", "100", "noPay", "0", sysParamService);
		}
		
		//2 查询在超过规定日期未支付的订单
		map.put("status", 2);
		zhimaRequestVos = timeShareOrderService.queryAllNoPayOrderInSpecifiedDate(map);
		if (zhimaRequestVos.size() > 0) {
			ParamUtil.getParam("1", "1", "100", "noPay", "1", sysParamService);
		}
		logger.info("-- 统计未支付订单结束  --");
		
	}
	
}
