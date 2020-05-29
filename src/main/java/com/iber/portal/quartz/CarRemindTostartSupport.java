package com.iber.portal.quartz;

import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.task.TaskPoolMapper;
import com.iber.portal.model.car.report.CarRunSmallBatteryDetail;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.SendSMS;
import com.iber.portal.vo.car.CarReportQuery;
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
 * 小电瓶电量过低提醒启动车辆充电
 * @author zengfeiyue
 */
public class CarRemindTostartSupport extends QuartzJobBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CarRunMapper carRunMapper;
    @Autowired
    private SysParamService sysParamService;

    private static volatile Map<String,Object> isSend = new HashMap<String,Object>(50);
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("小电瓶电量过低提醒启动车辆充电的任务");
        SysParam small_battery_loss= sysParamService.selectByKey(SysConstant.SMALL_BATTERY_SEND_MSG);
        List<CarRunSmallBatteryDetail> carRunEmptySmallBatteryDetails = carRunMapper.selectCarRunSmallBatteryLossDetail(Integer.parseInt(small_battery_loss.getValue()));

        for (CarRunSmallBatteryDetail carRunSmallBatteryDetail:carRunEmptySmallBatteryDetails) {
            //如果是员工订单，则不用短信通知
            String orderType = carRunSmallBatteryDetail.getOrderId().split("-")[1];
            if (orderType.equals("employee")) {
                continue;
            }
            // this is containsKey
            if (isSend.containsKey(carRunSmallBatteryDetail.getLpn())&&
                    carRunSmallBatteryDetail.getOrderId().equals(isSend.get(carRunSmallBatteryDetail.getLpn()))){
                // This is already send
            }else {
                sendMessage(carRunSmallBatteryDetail.getMemberPhone(),carRunSmallBatteryDetail.getLpn(),carRunSmallBatteryDetail.getOrderId());
            }
        }
    }

    public void sendMessage(String phone,String lpn,String orderId){
        //send msg map
        isSend.put(lpn,orderId);
        SendSMS.send(phone,"",2705,lpn);
    }
}
