package com.iber.portal.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.service.overall.OverallService;

/**
 * 定时收集总体情况统计数据，并插入数据表
 * 
 * @author zixb
 * @since 2017-07-03
 * 
 */
public class InsertStatisticOverallDatasSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OverallService overallService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("总体情况分析 定时器 start...");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		String result = overallService.setOneDayData(yesterday, "all");
		log.info("分析结果 ："+result+" ; 总体情况分析 定时器 end...");
	}


}
