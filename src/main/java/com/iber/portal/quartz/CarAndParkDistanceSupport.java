package com.iber.portal.quartz;

import com.alibaba.fastjson.JSON;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.sys.SysWarnInfoMapper;
import com.iber.portal.dao.sys.SysWarnItemMapper;
import com.iber.portal.model.sys.SysWarnInfo;
import com.iber.portal.model.sys.SysWarnItem;
import com.iber.portal.mongo.WarnInfoNosql;
import com.iber.portal.util.DistanceUtil;
import com.iber.portal.vo.car.CarRunExtendVo;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CarAndParkDistanceSupport extends QuartzJobBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarRunMapper carRunMapper;
	
	@Autowired
	private SysWarnItemMapper sysWarnItemMapper;
	
	@Autowired
	private SysWarnInfoMapper sysWarnInfoMapper;

	@Autowired
	private WarnInfoNosql warnInfoNosql;

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		log.info("车辆与网点距离定时任务开始执行") ;
		//查询空闲中的车辆
		List<CarRunExtendVo> carRunVos = carRunMapper.selectCarRunList();
		SysWarnItem parkLittleCarSysWarnItem = sysWarnItemMapper.selectByCode(SysConstant.SYS_WARN_CAR_AND_PARK_DISTANCE);
		String thresholdValue = parkLittleCarSysWarnItem.getThresholdValue();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (CarRunExtendVo carRunVo : carRunVos) {
			String latitude = carRunVo.getLatitude();//车辆所处纬度
			String longitude = carRunVo.getLongitude();//车辆所处经度
			String parkLatitude = carRunVo.getParkLatitude();//车辆所属网点纬度
			String parkLongitude = carRunVo.getParkLongitude();//车辆所属网点经度
			if (!StringUtils.isBlank(latitude) && !StringUtils.isBlank(longitude) 
					&& !StringUtils.isBlank(parkLatitude) && !StringUtils.isBlank(parkLongitude)) {
					Double latitudeDouble = Double.parseDouble(latitude);
					Double longitudeDouble = Double.parseDouble(longitude);
					Double parkLatitudeDouble = Double.parseDouble(parkLatitude);
					Double parkLongitudeDouble = Double.parseDouble(parkLongitude);
					Double distance = DistanceUtil.GetDistance(longitudeDouble, latitudeDouble, parkLongitudeDouble, parkLatitudeDouble);
					if (!StringUtils.isBlank(thresholdValue)) {
						Double value = Double.parseDouble(thresholdValue);
						if (distance.compareTo(value) > 0) {//如果距离大于预警的范围
							SysWarnInfo sysWarnInfo = new SysWarnInfo();
							sysWarnInfo.setActualValue(String.valueOf(distance));
							sysWarnInfo.setCreateTime(new Date());
							sysWarnInfo.setLpn(carRunVo.getLpn());
							sysWarnInfo.setMemberId(carRunVo.getMemberId());
							sysWarnInfo.setOrderId(carRunVo.getOrderId());
							sysWarnInfo.setParkId(carRunVo.getParkId());
							sysWarnInfo.setThresholdValue(thresholdValue);
							sysWarnInfo.setToDispatch(parkLittleCarSysWarnItem.getToDispatch());
							sysWarnInfo.setWarnContent(parkLittleCarSysWarnItem.getWarnTpl().replace("{0}", carRunVo.getLpn()).replace("{1}", carRunVo.getParkName())
									.replace("{2}", sdf.format(new Date())).replace("{3}", String.valueOf(distance)+"km"));
							sysWarnInfo.setWarnItemCode(SysConstant.SYS_WARN_CAR_AND_PARK_DISTANCE);
//							sysWarnInfoMapper.insert(sysWarnInfo);
							warnInfoNosql.insert(JSON.toJSONString(sysWarnInfo));
							}
						}
					}
				}
		log.info("车辆与网点距离定时任务结束执行") ; 
	}

}
