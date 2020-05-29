/*
 * 
 */
package com.iber.portal.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.service.sys.SysParamService;

/**
 * IBPD-206 定时任务判断充电车辆电量达到设置百分比时，车辆自动上线，更新车辆状态
 * 1、	自动下线自动上线的电量百分比调整
 		奇瑞EQ车型自动下线电量百分比为20%，自动上线电量为40%
		北汽EV160自动下线电量百分比为30%，自动上线电量为50%

	2、	手动下线的，
		判断，如果是慢充的，不自动上线，要上线的话后台手动上线
		如果是快充，按上面两种车型的上线电量执行自动上线。

 * @author ouxx
 * @since 2017-4-10 下午4:31:34
 *
 */
public class UpdateCarOnlineSupport extends QuartzJobBean {

	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
	private CarRunMapper carRunMapper;
	
	//北汽EV160自动下线电量百分比为30%，自动上线电量为50%
	private static final String EV160_CAR_ONLINE_ELECTRIC_LOWER_LIMIT = "EV160_car_online_electric_lower_limit"; 
	private static final String EV160_CAR_TYPE = "EV160"; 
	//奇瑞EQ车型自动下线电量百分比为20%，自动上线电量为40%
	private static final String EQ_CAR_ONLINE_ELECTRIC_LOWER_LIMIT = "EQ_car_online_electric_lower_limit"; 
	private static final String EQ_CAR_TYPE = "EQ"; 
	
	private final Logger logger = LoggerFactory.getLogger(UpdateCarOnlineSupport.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("定时判断充电车辆电量达到设置百分比时，车辆自动上线，更新车辆状态");
		carRunMapper.updateCarOnlineWhileCharging();
//		SysParam lowerLimitEV160Param = sysParamService.selectByKey(EV160_CAR_ONLINE_ELECTRIC_LOWER_LIMIT);
//		SysParam lowerLimitEQParam = sysParamService.selectByKey(EQ_CAR_ONLINE_ELECTRIC_LOWER_LIMIT);
//		if(null != lowerLimitEV160Param && null != lowerLimitEQParam){
//			String lowerLimitEV160Str= lowerLimitEV160Param.getValue();
//			String lowerLimitEQStr= lowerLimitEQParam.getValue();
//			if(StringUtils.isNotBlank(lowerLimitEV160Str)
//					&& StringUtils.isNotBlank(lowerLimitEQStr)){
//				//车辆可上线的电量下限
//				//EV160
//				Double lowerLimitEV160 = Double.valueOf(lowerLimitEV160Str);
//				carRunMapper.updateCarOnlineWhileElectricEnough(lowerLimitEV160, EV160_CAR_TYPE);
//				//EQ
//				Double lowerLimitEQ = Double.valueOf(lowerLimitEQStr);
//				carRunMapper.updateCarOnlineWhileElectricEnough(lowerLimitEQ, EQ_CAR_TYPE);
//			}else{
//				logger.error("sysParam系统配置无EV160_car_online_electric_lower_limit、EQ_car_online_electric_lower_limit此项");
//			}
//			
//		}
	}

}
