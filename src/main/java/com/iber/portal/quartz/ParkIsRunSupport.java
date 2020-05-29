package com.iber.portal.quartz;

import com.iber.portal.model.base.Park;
import com.iber.portal.service.network.ParkService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkIsRunSupport extends QuartzJobBean{
	
	Logger logger = LoggerFactory.getLogger(ParkIsRunSupport.class);
	
	@Autowired
	private ParkService parkService;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.info("------网点是否运营查询开始------");
		List<Park> parks = parkService.queryAllParkExcludeTemporary();
		List<Integer> ids = new ArrayList<Integer>();
		List<Integer> runIds = new ArrayList<Integer>();
		for (Park park : parks) {
			//获取网点运营的开始时间和结束时间
			String runStartTime = park.getRunStartTime();
			String runEndTime = park.getRunEndTime();
			//获取当前时间
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String now = sdf.format(new Date());
			if(0 == park.getStatus() && 1 == park.getIsRun()){
//				parks.remove((Object)park.getId());
				parks.remove(park);
			}else{
				if (!StringUtils.isBlank(runStartTime) && !StringUtils.isBlank(runEndTime)) {
					Integer startMinute = getMinute(runStartTime);
					Integer endMinute = getMinute(runEndTime);
					Integer nowMinute = getMinute(now);
					if (nowMinute < startMinute || nowMinute > endMinute) {
						ids.add(park.getId());
					}else if (nowMinute >= startMinute && nowMinute <= endMinute) {
						runIds.add(park.getId());
					}
				}
			}
			
		}
		if (ids.size() > 0) {
			parkService.batchUpdateByParkId(ids);
		}
		if (runIds.size() > 0) {
			parkService.batchUpdateByRunParkId(runIds);
		}
		
		logger.info("------网点是否运营查询结束------");
	}

	private static Integer getMinute(String runStartTime) {
		int sum = 0;
		if (!StringUtils.isBlank(runStartTime)) {
			String[] times = runStartTime.split(":");
			if ("0".equals(times[0].substring(0, 1))) {
				String hour = times[0].substring(1);
				sum += Integer.parseInt(hour)*60;
			}else{
				String hour = times[0];
				sum += Integer.parseInt(hour)*60;
			}
			if ("0".equals(times[1].subSequence(0, 1))) {
				String minute = times[1].substring(1);
				sum += Integer.parseInt(minute);
			}else{
				String minute = times[1];
				sum += Integer.parseInt(minute);
			}
		}
		return sum;
	}

	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String now = sdf.format(new Date());
		System.out.println(now);
		System.out.println(getMinute(now));
	}
}
