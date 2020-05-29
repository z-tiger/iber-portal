package com.iber.portal.quartz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.util.DateTime;

/**
 * 车辆个推状态查询
 * 
 * @date 2017-10-26
 * @author zixb
 *
 */
public class CarGetuiStatusSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarRunMapper carRunMapper;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.info("查询车辆个推状态定时器 启动.....start"+DateTime.getNowDateString());
		//查询所有运营车辆
		List<CarRun> CarRunList = carRunMapper.selectALL();
		for (CarRun carRun : CarRunList) {
			// 查询车辆cid
			List<String> list = PushClient.queryClientId(carRun.getLpn());
			if(!list.isEmpty()){
				for (String cid : list) {
					//校验个推状态
					String getuiStatus = PushClient.getUserStatus(cid);
					//若个推状态没有改变则不更新状态  若有改变则更新更新状态
					if(!StringUtils.equals(carRun.getGetuiStatus(), getuiStatus)){
						carRunMapper.updateGetuiStatusByLpn(getuiStatus,carRun.getLpn());
					}
				}
			}
		}
		log.info("查询车辆个推状态定时器 结束.....end"+DateTime.getNowDateString());
	}

}
