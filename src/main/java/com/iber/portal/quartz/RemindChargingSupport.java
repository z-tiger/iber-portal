package com.iber.portal.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.util.SendSMS;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.model.car.CarChargingRemind;
import com.iber.portal.service.car.CarChargingRemindService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
/**
 * 车辆低电量提醒用户给车辆充电
 * @author Administrator
 *
 */
public class RemindChargingSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private CarChargingRemindService carChargingRemindService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("提醒用户给车充电开始");
		try {
			remindCarCharging();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void remindCarCharging() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("restBattery", 40.0);
		paramMap.put("status", "useCar");
		paramMap.put("batStatus", "0");
		//1、查询当前正在使用的并且电量低于30%的车辆
		List<CarChargingRemind> reminds = timeShareOrderService.selectAllUsingCar(paramMap);
		List<CarChargingRemind> chargingReminds = carChargingRemindService.selectAll();
		List<String> orderList = new ArrayList<String>();
		for (CarChargingRemind remind : chargingReminds) {
			orderList.add(remind.getOrderId());
		}
		//2、将查询的车辆的订单号以及会员id保存起来
		List<CarChargingRemind> newReminds = new ArrayList<CarChargingRemind>();
		for (CarChargingRemind carChargingRemind : reminds) {
			if (!orderList.contains(carChargingRemind.getOrderId())) {
				//如果订单号不存在，就需要发短信
				//发送短信
				Map<String, String> params = new HashMap<String, String>();
				//获取加密token
				/*String encryptToken = SendSMS.encryptBySalt(carChargingRemind.getPhone());
				String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();
				String param = ""; */
				try {
					//param ="{\"telephoneNo\":\""+carChargingRemind.getPhone()+"\",\"ipAddress\":\"\",\"templateId\":\""+2676+"\",\"contentParam\":\""+new String((carChargingRemind.getLpn()).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
					SendSMS.send(carChargingRemind.getPhone(),"",2676,carChargingRemind.getLpn());
					/*params.put("msgContentJson", param);
					HttpsClientUtil.post(sendUrl+"",params);*/
				} catch (Exception e) {
					e.printStackTrace();
				}



				//保存到数据库中，保证同一个订单只发一次短信
				CarChargingRemind newRemind = new CarChargingRemind();
				newRemind.setMemberId(carChargingRemind.getMemberId());
				newRemind.setOrderId(carChargingRemind.getOrderId());
				newRemind.setLpn(carChargingRemind.getLpn());
				newRemind.setPhone(carChargingRemind.getPhone());
				newReminds.add(newRemind);
			}
		}
		if (newReminds.size() > 0) {
			carChargingRemindService.batchInsert(newReminds);
		}
	}

}
