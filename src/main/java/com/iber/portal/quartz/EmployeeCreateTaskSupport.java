package com.iber.portal.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.employee.EmployeeInfoMapper;
import com.iber.portal.dao.task.TaskMapper;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.employee.EmployeeInfo;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.service.sys.SysParamService;

public class EmployeeCreateTaskSupport extends QuartzJobBean{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
	private CarRunMapper carRunMapper;
	
	@Autowired 
	private TaskMapper taskMapper;
	
	@Autowired 
	private EmployeeInfoMapper employeeInfoMapper;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("创建员工充电任务执行");
		
		SysParam sysParam = sysParamService.selectByKey("employee_create_task_restBattery") ;
		
		/**查询车辆运营表是否有不在运营状态下的 需要充电的车辆(有订单和 正在充电的排除)*/
		List<CarRun> carRunList = carRunMapper.getAllCarInfo(Double.valueOf(sysParam.getValue()));
		if(carRunList.size()>0){
			for(CarRun carRun:carRunList){
				/**根据车牌查询任务列表中是否存在*/
				TaskInfo taskInfo = taskMapper.getRecords(carRun.getLpn());
				if(taskInfo==null || "3".equals(taskInfo.getStatus())){
					/**如果员工任务表中不存在 或者最后一条记录是完成状态时创建充电任务*/
					TaskInfo newTaskInfo = new TaskInfo();
					newTaskInfo.setLpn(carRun.getLpn());
					newTaskInfo.setBeginParkId(carRun.getParkId());
					newTaskInfo.setCityCode(carRun.getCityCode());
					newTaskInfo.setTaskLevel("1");
					newTaskInfo.setTaskType("1");    
					newTaskInfo.setCreateTime(new Date());
					newTaskInfo.setStatus("1");
					/**发给网格内任务最少且为上班状态的员工*/
					EmployeeInfo employeeInfo =employeeInfoMapper.getRecordsById(carRun.getParkId());
					if(employeeInfo !=null){
						newTaskInfo.setEmployeeId(employeeInfo.getId());
						taskMapper.insertChargingRecords(newTaskInfo);
					}
					
				}
				
			}
		}
		log.info("创建员工充电任务结束");
	}

}
