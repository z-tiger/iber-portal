package com.iber.portal.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.iber.portal.mongo.WarnInfoNosql;
import com.iber.portal.util.SendSMS;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.dao.sys.SysWarnInfoMapper;
import com.iber.portal.dao.sys.SysWarnItemMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysWarnInfo;
import com.iber.portal.model.sys.SysWarnItem;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.util.DateTime;

/**
 * 
 * 车辆长时间未行驶，平台预警
 * 
 * @author zixb
 * @date 2017-10-11
 * 
 */
public class CarLongTimeNotRunWarnSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CarRunService carRunService;

	@Autowired
	private TimeShareOrderMapper timeShareOrderMapper;

	@Autowired
	private SysParamMapper sysParamMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private SysWarnInfoMapper sysWarnInfoMapper;

	private final static String LONG_TIME_NOT_RUN_TEMPLATE_ID = "2699";
	
	@Autowired
	private SysWarnItemMapper sysWarnItemMapper;

	@Autowired
	private WarnInfoNosql warnInfoNosql;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.info("车辆长时间未行驶，平台预警 定时器执行......  start : " + DateTime.getNowDateString());
		excuteCarLongTimeNotRunWarn();
		log.info("车辆长时间未行驶，平台预警 定时器执行......  end : " + DateTime.getNowDateString());
	}

	/**
	 * 车辆长时间未行驶，平台预警
	 */
	private void excuteCarLongTimeNotRunWarn() {
		List<CarRun> carRunList = carRunService.selectALL();
		for (CarRun carRun : carRunList) {
			// 只有当车辆被使用的时候 才发预警信息
			if (!StringUtils.equals(carRun.getStatus(), "useCar")) {
				continue;
			}
			// 员工用车不用发送预警提醒
			if (StringUtils.contains(carRun.getOrderId(), "employee")) {
				continue;
			}
			if(carRun.getMileage() == null){
				continue;
			}
			// 若里程有变动则更新里程信息，若没有则判断是否需要发送预警提醒
			Double latestOrderMileage = carRun.getLatestOrderMileage() == null ? 0:carRun.getLatestOrderMileage() ;
			if (carRun.getMileage() > latestOrderMileage) {
				// 更新最新里程
				carRunService.updateLatestOrderMileage(carRun.getLpn());
			} else {
				TimeShareOrder tso = timeShareOrderMapper
						.selectByOrderId(carRun.getOrderId());
				Date startTime = carRun.getLatestOrderTime();
				//新增车辆时时间为空 默认选择订单开始时间
				if(startTime == null){
					startTime = tso.getBeginTime();
				}
				// 若记录的最后时间不为当前订单的时间 则以订单时间为开始时间
				if (tso.getBeginTime().getTime() > startTime.getTime()) {
					startTime = tso.getBeginTime();
				}
				// 计算间隔时间
				long interval = new Date().getTime() - startTime.getTime();
				// 查询发送预警信息的需要的间隔时间
				SysParam long_time_not_run_warn = sysParamMapper
						.selectByKey("long_time_not_run_warn");
				SysParam long_time_not_run_short = sysParamMapper
						.selectByKey("long_time_not_run_short");
				int warn = long_time_not_run_warn != null ? Integer
						.parseInt(long_time_not_run_warn.getValue()) : 4;
				int shortInfo = long_time_not_run_short != null ? Integer
						.parseInt(long_time_not_run_short.getValue()) : 8;
				// 发送平台预警
				if (interval >= (warn * 60 * 60 * 1000)) {
					sendWarn(carRun, (interval/60/60/1000), warn);
				}
				// 发送短信提醒信息
				if (interval >= (shortInfo * 60 * 60 * 1000)) {
					sendShortInfo(carRun, (interval/60/60/1000));
				}
			}
		}
	}

	/**
	 * 发送提醒短信
	 * 
	 * @param carRun
	 * @param interval
	 */
	private void sendShortInfo(CarRun carRun, long interval) {
		log.info("车辆长时间未行驶，平台预警 定时器执行  发送提醒短信 .... start");
		try {
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			// 如果是晚上11点 至 凌晨8点之间 则不用发短信打扰用户
			if (hour >= 8 && hour < 23) {
				SysParam sendUrl = sysParamMapper.selectByKey("send_sms_url");
				String url = "";
				if (sendUrl != null)
					url = sendUrl.getValue();
				Member member = memberMapper.selectByPrimaryKey(carRun
						.getMemberId());
				sendSMS(url, member.getPhone(), "",
						LONG_TIME_NOT_RUN_TEMPLATE_ID, carRun.getLpn() + "|"
								+ interval);
				// 发送过短信后更新最后订单里程 防止重复发送短信
				carRunService.updateLatestOrderMileage(carRun.getLpn());
			}
		} catch (Exception e) {
			log.error("车辆长时间未行驶，平台预警  发送提醒短信预测");
		}
		log.info("车辆长时间未行驶，平台预警 定时器执行  发送提醒短信 .... end");
	}

	/**
	 * 发送平台预警
	 * 
	 * @param carRun
	 * @param interval
	 * @param warn
	 */
	private void sendWarn(CarRun carRun, long interval, int warn) {
		log.info("车辆长时间未行驶，平台预警 定时器执行  发送平台预警 .... start");
		// 判断是否有否是过 如果 没有发送则发送 否则就不做处理
		SysWarnInfo sysWarnInfo = sysWarnInfoMapper.queryByOrderIdAndItemCode(
				carRun.getOrderId(), "car_long_time_not_run");
		if (sysWarnInfo != null) {
			return;
		}
		try {
			SysWarnItem sysWarnItem = sysWarnItemMapper.selectByCode("car_long_time_not_run");
			Member member = memberMapper.selectByPrimaryKey(carRun
					.getMemberId());
			String warnContent= "会员：" + member.getName() + "("
					+ member.getPhone() + ") 预约的车辆" + carRun.getLpn() + "在"
					+ interval + "小时内无里程变化;订单号：" + carRun.getOrderId()
					+ ",预警时间：" + DateTime.getNowDateString();
			if(sysWarnItem !=null){
				warnContent = sysWarnItem.getWarnTpl();
				warnContent = StringUtils.replace(warnContent, "{0}", member.getName());
				warnContent = StringUtils.replace(warnContent, "{1}", member.getPhone());
				warnContent = StringUtils.replace(warnContent, "{2}", carRun.getLpn());
				warnContent = StringUtils.replace(warnContent, "{3}", interval+"");
				warnContent = StringUtils.replace(warnContent, "{4}", carRun.getOrderId());
				warnContent = StringUtils.replace(warnContent, "{5}", DateTime.getNowDateString());
			}
			SysWarnInfo record = new SysWarnInfo();
			record.setActualValue(interval + "");
			record.setCreateTime(new Date());
			record.setLpn(carRun.getLpn());
			record.setOrderId(carRun.getOrderId());
			record.setThresholdValue(warn + "");
			record.setWarnContent(warnContent);
			record.setWarnItemCode("car_long_time_not_run");
//			sysWarnInfoMapper.insert(record);
			warnInfoNosql.insert(JSON.toJSONString(record));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("车辆长时间未行驶，平台预警  发送预警预测");
		}
		log.info("车辆长时间未行驶，平台预警 定时器执行  发送平台预警 .... end");
	}

	/**
	 * 短信发送
	 * 
	 * @param url
	 * @param telephoneNo
	 * @param ipAddress
	 * @param templateId
	 * @param contentParam
	 * @return
	 * @throws Exception
	 */
	public String sendSMS(String url, String telephoneNo, String ipAddress,
			String templateId, String contentParam) throws Exception {
		/*Map<String, String> paramss = new HashMap<String, String>();

		//获取加密token
		String encryptToken = SendSMS.encryptBySalt(telephoneNo);
		String param = "{\"telephoneNo\":\"" + telephoneNo
				+ "\",\"ipAddress\":\"" + ipAddress + "\",\"templateId\":\""
				+ templateId + "\",\"contentParam\":\""
				+ new String(contentParam.getBytes("utf-8"), "ISO-8859-1")
				+ "\",\"token\":\""+encryptToken+"\"}";
		paramss.put("msgContentJson", param);*/
		SendSMS.send(telephoneNo,ipAddress,Integer.parseInt(templateId),contentParam);

		return "";
	}
}
