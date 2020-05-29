package com.iber.portal.quartz;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.SendMail;
import com.iber.portal.dao.car.CarMapper;
import com.iber.portal.model.car.Car;
import com.iber.portal.service.sys.SysParamService;

public class CarAnnualInspectionSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
    private CarMapper carMapper ;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//车辆年检时间截至前三个月，进行邮件提醒，每天执行一次，直到年检处理为止
		log.info("定时任务开始执行，车辆年检时间临期提醒。") ;
		//查询出x_car表中车辆年检时间三个月内的记录:
		List<Car>  list = carMapper.getAllCarMrg();
		if(list.size()>0){
			String subject="车辆年检时间到期提醒";
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				String lpn = list.get(i).getLpn();
				Date annualInspectionTime =list.get(i).getAnnualInspectionTime();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String str =df.format(annualInspectionTime);
				sb.append("<b>车牌号码：</b>").append(lpn).append("        <b>车辆年检到期时间：</b>").append(str).append("<br/>");
				
			}
			String content = sb.toString();
			if(!"".equals(content)){
				String _to = sysParamService.selectByKey("annual_inspection_send_email_to").getValue();
				try {
					SendMail sendMail = new SendMail(sysParamService);
					sendMail.sendMail(subject, content, _to);
					System.out.println(666);
				} catch (Exception e) {
					log.error("车辆年检到期提醒邮件发送失败！") ;
					e.printStackTrace();
				}
			}
		}
		log.info("定时任务结束执行，车辆年检时间临期提醒。") ;
	}
	
}
