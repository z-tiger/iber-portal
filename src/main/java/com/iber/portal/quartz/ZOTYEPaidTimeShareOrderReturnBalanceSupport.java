package com.iber.portal.quartz;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import com.iber.portal.dao.base.MoneyLogMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.patch.PatchesMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.MoneyLog;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.util.BatchSetterUtil;
import com.iber.portal.util.DateTime;
import com.iber.portal.vo.timeShare.TimeShareOrderDiscountRecord;
import com.iber.portal.vo.timeShare.ZOTYEReturnCashOrderVo;

public class ZOTYEPaidTimeShareOrderReturnBalanceSupport extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SysParamMapper sysParamMapper;
	
	@Autowired
	private PatchesMapper patchesMapper;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	private MoneyLogMapper moneyLogMapper;

	// 用于批量上载数据

	private static final String UPDATE_SQL = "UPDATE `x_member_card` c set money = c.money + ? WHERE member_id = ?";

	private static final String DISCOUNT_RETURNED = "zotye_discount_returned";
	
	private static final String EV_TIME_SHARE_ORDER_DISCOUNT = "EV_TIME_SHARE_ORDER_DISCOUNT";

	private static final String TITLE_MODEL_1 = "【宜步出行】尊敬的会员，%s元余额返现已经送到您账户，请注意查收！";

	@Transactional(rollbackFor = Exception.class)
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		SysParam sysParam = sysParamMapper.selectByKey(DISCOUNT_RETURNED);
        long nowTimeStamp = System.currentTimeMillis();
        DecimalFormat df = new DecimalFormat("######0.00"); 
		if (null != sysParam && !StringUtils.isBlank(sysParam.getValue()) && "ON".equals(sysParam.getValue())) {

			log.info("定时任务开始,众泰车型订单返现");
			SysParam sysParams = sysParamMapper.selectByKey(EV_TIME_SHARE_ORDER_DISCOUNT);
			Integer value = Integer.valueOf(sysParams.getValue());
			Map<String, Object> mapParam = new HashMap<String, Object>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(nowTimeStamp - 24 * 60 * 60 * 1000));
			int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			// 6代表星期天,0代表星期一
			if(!(6==week||0==week)){
				Date startTime = DateTime.getStartTimeOfDay(new Date(nowTimeStamp - 24 * 60 * 60 * 1000));
				Date endTime = DateTime.getEndTimeOfDay(new Date(nowTimeStamp - 24 * 60 * 60 * 1000));
				mapParam.put("startTime", startTime);
				mapParam.put("endTime", endTime);
				mapParam.put("brandName", "众泰");
				List<TimeShareOrder> lists = patchesMapper.getTimeShareOrderInfoByCarType(mapParam);
				List<TimeShareOrderDiscountRecord> records = new ArrayList<TimeShareOrderDiscountRecord>(); 
				for (TimeShareOrder timeShareOrder : lists) {
					TimeShareOrderDiscountRecord record = new TimeShareOrderDiscountRecord();
					record.setOrderId(timeShareOrder.getOrderId());
					record.setCreateTime(new Date());
					record.setCashReturnStatus("unfinish");
					record.setCategory(DISCOUNT_RETURNED);
					records.add(record);
				}
				patchesMapper.insertTimeShareOrderDiscountRecord(records);
			}
			List<ZOTYEReturnCashOrderVo> noReturnLists = patchesMapper.getZOTYEFinishPaidOrderInfos(mapParam);
			
			// pushLists记录个推信息对象,key的数据是存储用户的电话号码
			List<Map<String, PushCommonBean>> pushLists = new ArrayList<Map<String, PushCommonBean>>();
			final List<MoneyLog> moneyLoglists = new ArrayList<MoneyLog>();
			final List<SystemMsgLog> systemMsgLogs = new ArrayList<SystemMsgLog>();
			final List<Map<String, Integer>> mapLists = new ArrayList<Map<String, Integer>>();
			Date now = new Date();
			List<String> orderIds = new ArrayList<String>();
			List<String> zeroMoneyOrderIds = new ArrayList<String>();
			for (ZOTYEReturnCashOrderVo timeShareOrder : noReturnLists) {
				Double returnMoney = new Double(String.valueOf((timeShareOrder.getOrderMoney()-timeShareOrder.getFreeCompensationMoney()))) * value/100;
			    if(1>returnMoney){ 
					zeroMoneyOrderIds.add(timeShareOrder.getOrderId());
					continue;
				}
				MoneyLog log = new MoneyLog();
				Map<String, Integer> moneyMap = new HashMap<String, Integer>();
				log.setCategory(DISCOUNT_RETURNED);
				log.setCreateTime(now);
				log.setMemberId(timeShareOrder.getMemberId());
				log.setObjId(timeShareOrder.getOrderId());
				log.setDescribe("众泰车型订单折扣返现");
				log.setType("+");
				log.setMoney(returnMoney.intValue());
				String title = new String("");
				moneyMap.put("money", returnMoney.intValue());
				title = String.format(TITLE_MODEL_1, df.format(new Double(returnMoney.intValue())/100));
				
			    moneyLoglists.add(log);
				moneyMap.put("memberId", timeShareOrder.getMemberId());
				mapLists.add(moneyMap);
				Map<String, PushCommonBean> pushMap = new HashMap<String, PushCommonBean>();
				PushCommonBean pushBean = new PushCommonBean(DISCOUNT_RETURNED,"1", title, log.getObjId());
				pushMap.put(timeShareOrder.getPhone(), pushBean);
				pushLists.add(pushMap);
	
				// SystemMsgLog
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType(DISCOUNT_RETURNED);
				systemMsgLog.setCreateTime(new Date());
				systemMsgLog.setMemberId(timeShareOrder.getMemberId());
				systemMsgLog.setMsgTitle("众泰折扣返现");
				systemMsgLog.setMsgContent(title);
				systemMsgLogs.add(systemMsgLog);
				orderIds.add(timeShareOrder.getOrderId());
			}
			if(0<mapLists.size()){
				jdbcTemplate.batchUpdate(UPDATE_SQL,
						new BatchSetterUtil<Map<String, Integer>>(mapLists) {
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								Map<String, Integer> map = mapLists.get(i);
								ps.setInt(1, map.get("money"));
								ps.setInt(2, map.get("memberId"));
							}
						});
			}
			// 把已完成折扣返现的订单的返现状态设置为done
			if(0<orderIds.size()){
				patchesMapper.updateDiscountRecordsBatch(orderIds);
			}
			if(0<systemMsgLogs.size()){
				systemMsgLogMapper.insertSystemMsgLogBatch(systemMsgLogs);
			}
			if(0<moneyLoglists.size()){
				moneyLogMapper.insertMoneyLogBatch(moneyLoglists);
			}
			// 把打折扣的錢为0的订单从x_time_share_order_discount_records表删掉
			if(0<zeroMoneyOrderIds.size()){
				patchesMapper.batchDeleteZeroMoneyOrder(zeroMoneyOrderIds);
			}
			for (Map<String, PushCommonBean> mapPush : pushLists) {
				// mapPush的entrySet只有一个,此处习惯性加上for循环
				for (Entry<String, PushCommonBean> entry : mapPush.entrySet()) {
					// 此处判空处理,以免发生异常
					if (null != entry && !StringUtils.isBlank(entry.getKey())) {
						String memberPhone = entry.getKey();
						List<String> cidMemberList = PushClient
								.queryClientId(memberPhone);
						for (String memberCid : cidMemberList) {
						    PushClient.push(memberCid,JSONObject.fromObject(entry.getValue()));
						}
					}
				}
			}
		}
	}
}
