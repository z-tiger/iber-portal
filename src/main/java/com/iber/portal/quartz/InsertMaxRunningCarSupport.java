package com.iber.portal.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iber.portal.util.SendSMS;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.car.CarMaxRunMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.CarMaxRun;
import com.iber.portal.service.sys.SysParamService;

/**
 * 定时统计最大运营车辆总数
 * @author Administrator
 *
 */
public class InsertMaxRunningCarSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CarRunMapper carRunMapper;
	@Autowired
	private CarMaxRunMapper carMaxRunMapper;
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	@Autowired
    private SysParamService sysParamService ;
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	@Autowired
	private HttpServletRequest request;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("运营车辆总数定时统计开始");
		//将最大运营车辆总数插入数据表，并发短信通知
		try {
			InsertMaxRunningCar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void InsertMaxRunningCar() throws Exception {
		//查询出所有处于运营中的车辆
		int count = carRunMapper.selectCountByStatus();
		//查询出最大运营车辆总数表中的车辆总数，如果这个总数小于count，就删除最大运营车辆总数表中的
		//数据，并插入一条新的数据
		CarMaxRun carMaxRun = carMaxRunMapper.selectAllRecords();
		if (carMaxRun == null || count > carMaxRun.getMaxNumber()) {//如果数据表中没有数据或者最大运营车辆总数小于运营中的车辆总数
			carMaxRunMapper.deleteRecord();
			//插入一条数据
			carMaxRun = new CarMaxRun();
			carMaxRun.setMaxNumber(count);
			carMaxRun.setCreateTime(new Date());
			carMaxRunMapper.insertRecord(carMaxRun);
			//发送短信
			Map<String, String> params = new HashMap<String, String>();
			//获取加密token
			/*String encryptToken = SendSMS.encryptBySalt("18520880021");
			String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();*/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateString = sdf.format(carMaxRun.getCreateTime());
			/*String param = "";
			param ="{\"telephoneNo\":\""+"18520880021"+"\",\"ipAddress\":\"\",\"templateId\":\""+2672+"\",\"contentParam\":\""+new String((count+"|"+dateString).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
			params.put("msgContentJson", param); 
			HttpsClientUtil.post(sendUrl+"",params);*/
			SendSMS.send("18718511395","",2672,count+"|"+dateString);

		}
	}
	
}
