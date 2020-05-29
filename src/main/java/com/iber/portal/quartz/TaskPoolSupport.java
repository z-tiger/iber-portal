package com.iber.portal.quartz;

import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.task.TaskPoolMapper;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.vo.car.CarReportQuery;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.util.HashMap;
import java.util.List;

/**
 * 自动生成任务池的任务
 * @author zengfeiyue
 */
public class TaskPoolSupport extends QuartzJobBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CarRunMapper carRunMapper;
    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private TaskPoolMapper taskPoolMapper;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("自动生成任务池的任务");
        SysParam car_report_query_json= sysParamService.selectByKey("car_report_query_json");
        net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(car_report_query_json.getValue());
        List<CarReportQuery> list = net.sf.json.JSONArray.toList(array,CarReportQuery.class);
        /**查找空闲中和补电中电量低于临界值的车**/
        List<HashMap> restBattery = carRunMapper.selectEmptyAndChargingRestBattery(list);

        for (int i=0;i<restBattery.size();i++){
            Integer count = taskPoolMapper.findByStatusAndLpn(restBattery.get(i).get("lpn"));
            if (count==0){
                taskPoolMapper.saveTaskPoolIgnore(restBattery.get(i).get("lpn"));
            }
        }
        /**
         * 取消任务池的任务
         * 取消规则（未接单的）：
         * 1、任务池已有任务，车辆实际状态是运营，取消这个任务
         * 2、如果是充电任务，车辆已经在充电，补电状态，则取消这个任务
         */
        taskPoolMapper.cancelTaskPool();

    }
}
