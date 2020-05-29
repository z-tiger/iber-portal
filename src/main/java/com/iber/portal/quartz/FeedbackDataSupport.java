package com.iber.portal.quartz;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.common.ParamUtil;
import com.iber.portal.zhima.vo.ZhimaRequestVo;

public class FeedbackDataSupport extends QuartzJobBean {
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private SysParamService sysParamService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		List<ZhimaRequestVo> zhimaRequestVos = timeShareOrderService.queryAllFinishOrder();
		ParamUtil.getParam("9", "2", "100", "finish", "2", sysParamService);
	}

}
