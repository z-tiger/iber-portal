package com.iber.portal.quartz;

import com.iber.portal.dao.charging.ChargingOrderInfoMapper;
import com.iber.portal.model.charging.ChargingOrderInfo;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;

import java.util.List;

/**
 * 定时查询给非宜步车辆充电的订单，结束充电并结束订单
 * @author ouxx
 * @since 2017-3-20 下午5:04:54
 *
 */
public class StopChargingExceptIberCarSupport extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SysParamService sysParamService ;

	@Autowired
    private ChargingOrderInfoMapper chargingOrderInfoMapper ;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("定时查询给非宜步车辆充电的订单，结束充电并结束订单");
		StringBuffer sb = new StringBuffer("结束了");
		SysParam intervalParam = sysParamService.selectByKey("update_battery_cache_interval");
		SysParam chargingStartedMinutesParam = sysParamService.selectByKey("chargingStartedMinutes");
		int updateBatteryCacheInterval = (null != intervalParam) ? Integer.parseInt(intervalParam.getValue()) : 1;
		int chargingStartedMinutes = (null != chargingStartedMinutesParam) ? Integer.parseInt(chargingStartedMinutesParam.getValue()) : 10;
//		List<ChargingEquipmentVo> list = chargingOrderInfoMapper.queryChargingExceptIberCarList(updateBatteryCacheInterval);

		//更新正常的充电订单。已被定为正常的，就无需再判断其是否偷电
		chargingOrderInfoMapper.updateNormalOrder(chargingStartedMinutes);
		List<ChargingOrderInfo> list = chargingOrderInfoMapper.queryChargingNotIberCarOrderList(updateBatteryCacheInterval, chargingStartedMinutes);
		if(CollectionUtils.isNotEmpty(list)){
			for(ChargingOrderInfo order : list){
				sb.append("枪ID=" + order.getConnectorId() + ",");
				order.setMemberStatus("ready");//使此订单需要收费
				order.setChargingStatus("charging");//使此订单需要收费
				chargingOrderInfoMapper.updateStatusWhenStealing(order);
			}
//			for(ChargingEquipmentVo equipmentVo : list){
//				sb.append(equipmentVo.getEquipmentCode() + "的枪" + equipmentVo.getConnectorNo() + ",");
//
//				equipmentVo.getOrderInfoId()
//				startOrStopChargingWithoutOrd(equipmentVo.getEquipmentCode(), equipmentVo.getConnectorCnt().byteValue()
//						, equipmentVo.getConnectorNo().byteValue(), equipmentVo.getConnectorId(), false);
//			}
		}

		if(sb.toString().equals("结束了")){
			logger.info("没有需要停止的充电操作");
		}else{
			logger.info("定时查询给非宜步车辆充电的订单，结束充电并结束订单 : " + sb.toString());
		}
	}


//	@Override
//	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//		logger.info("定时任务开始执行 : 定时查询给非宜步车辆充电的订单，结束充电并结束订单") ;
//		StringBuffer sb = new StringBuffer("{") ;
//		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\"stopChargingExceptIberCarQuartz\",") ;
//		sb.append("\"param\":\"{}\",") ;
//		sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
//		sb.append("}") ;
//		SysParam sysParam = sysParamService.selectByKey("http_url_c") ;
//		String json = "" ;
//		if(sysParam.getValue().indexOf("https") == 0){ //https接口
//			json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
//		}else{
//			json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
//		}
//		JSONObject jsonObject = JSONObject.fromObject(json) ;
//		logger.info("定时任务结束 : 定时查询给非宜步车辆充电的订单，结束充电并结束订单" + jsonObject.toString()) ;
//	}

}
