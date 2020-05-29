package com.iber.portal.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.getui.PushClientEmployee;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSON;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.vo.car.LowRestBatteryCarVo;

/**
 * 低电量提醒
 * @author 刘晓杰
 *
 */
public class RestBatteryRemindLessThanSixtyPercentSupport extends QuartzJobBean  {
	
	Logger logger = LoggerFactory.getLogger(RestBatteryRemindLessThanSixtyPercentSupport.class);
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private CarRunService carRunService;
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//1 获取低电量提醒的电量下限,上限
		//2 查询出符合要求的车牌（如果该车牌已经有员工在执行充电任务,则不需要提醒）
		logger.info("低电量提醒开始");
		SysParam sysParam = sysParamService.selectByKey("rest_battery_down_level");
		SysParam sysParam1 = sysParamService.selectByKey("rest_battery_up_level");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restBatteryDownLevel", Integer.parseInt(sysParam.getValue()));
		map.put("restBatteryUpLevel", Integer.parseInt(sysParam1.getValue()));
		List<LowRestBatteryCarVo> cars = carRunService.selectLowRestBatteryListByBetweenThirtyAndfifty(map);
		
		for (LowRestBatteryCarVo lowRestBatteryCarVo : cars) {
			//查询出所有不为下班状态的员工
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
		logger.info("低电量提醒结束");
	}

}
