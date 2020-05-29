package com.iber.portal.quartz;

import com.alibaba.fastjson.JSON;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.vo.car.LowRestBatteryCarVo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 低电量提醒
 * @author 刘晓杰
 *
 */
public class RestBatteryRemindLessThanThirtyPercentSupport extends QuartzJobBean  {
	
	Logger logger = LoggerFactory.getLogger(RestBatteryRemindLessThanThirtyPercentSupport.class);
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private CarRunService carRunService;
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//1 获取低电量提醒的电量下限
		//2 查询出符合要求的车牌（如果该车牌已经有员工在执行充电任务,则不需要提醒）
		logger.info("请开始你的表演");
		SysParam sysParam = sysParamService.selectByKey("rest_battery_down_level");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restBatteryLevel", Integer.parseInt(sysParam.getValue()));
		List<LowRestBatteryCarVo> cars = carRunService.selectLowRestBatteryList(map);
		
		for (LowRestBatteryCarVo lowRestBatteryCarVo : cars) {
			//查询出当前片区所有不为下班状态的员工
			List<String> phoneList = employeeService.selectPhoneListByParkId(lowRestBatteryCarVo.getParkId());
			String msg = lowRestBatteryCarVo.getLpn()+",电量为"+lowRestBatteryCarVo.getRestBattery()+",所在网点:"+lowRestBatteryCarVo.getParkName()+",请相关责任人尽快安排调度充电";
			for (String phone : phoneList) {
				PushCommonBean pushCommon = new PushCommonBean("server_push_car_low_rest_battery","1",msg,"") ;
				List<String> cidList = PushClientEmployee.queryClientId(phone);
				for (String employeeCid : cidList) {
					PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
				}
			}
		}
		logger.info("表演结束");
	}

}
