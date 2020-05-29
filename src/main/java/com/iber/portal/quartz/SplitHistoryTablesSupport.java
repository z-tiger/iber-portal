/*
 * 
 */
package com.iber.portal.quartz;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.iber.portal.util.SendSMS;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.dao.base.SplitHistoryTablesMapper;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;

/**
 * 定时查cmd、car_run_log、sys_warn等历史表，各表如果记录数超过sysParam中的配置数，则进行分表
 * @author ouxx
 * @since 2017-4-18 下午2:18:13
 *
 */
public class SplitHistoryTablesSupport extends QuartzJobBean {

	private final Logger logger = LoggerFactory.getLogger(SplitHistoryTablesSupport.class);
	//cmd、car_run_log、sys_warn的分表记录条数阈值
	private static final String HIS_TAB_REC_CNT_LIMIT = "his_tab_rec_cnt_limit";
	//cmd、car_run_log、sys_warn的分表记录条数发送预警短信的阈值
	private static final String HIS_TAB_SEND_MSG_LIMIT = "his_tab_send_msg_limit"; 
	
	/**分表人员电话号码：欧旭轩、唐德志、徐亚桥、刘晓杰*/
	private static final String[] PHONE_LIST = new String[]{"15015157936", 
			"18922941414",
//			"18871534283",
			"13580791813"};
	
//	private static FastDateFormat format = FastDateFormat.getInstance("yyyyMMdd");
	private static FastDateFormat format = FastDateFormat.getInstance("yyyyMMddHH");

	@Autowired
	private SplitHistoryTablesMapper splitHistoryTablesMapper;
	
	@Autowired
    private SysParamService sysParamService ;
	
	public String sendSMS(String url,String telephoneNo,String ipAddress,String templateId,String contentParam) throws Exception{
		/*Map<String, String> paramss = new HashMap<String, String>();
		//获取加密token
		String encryptToken = SendSMS.encryptBySalt(telephoneNo);
		String param = "{\"telephoneNo\":\""+ telephoneNo+ "\",\"ipAddress\":\""+ipAddress+"\",\"templateId\":\""+templateId+"\",\"contentParam\":\""+ new String(contentParam.getBytes("utf-8"),"ISO-8859-1") + "\",\"token\":\""+encryptToken+"\"}";
		paramss.put("msgContentJson", param);*/
		SendSMS.send(telephoneNo,ipAddress,Integer.parseInt(templateId),contentParam);
		return "";
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
//	@Override
//	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
//
//		logger.info("定时查cmd、car_run_log、sys_warn等历史表，各表如果记录数超过sysParam中的配置数，则进行分表");
//		String limitStr= sysParamService.selectByKey(HIS_TAB_REC_CNT_LIMIT).getValue();
//		String msgLimitStr= sysParamService.selectByKey(HIS_TAB_SEND_MSG_LIMIT).getValue();
//		final int limit = StringUtils.isNotBlank(limitStr) ? Integer.valueOf(limitStr).intValue() : 1000000;
//		final int msgLimit = StringUtils.isNotBlank(msgLimitStr) ? Integer.valueOf(msgLimitStr).intValue() : 3000000;
//
//		//当天0点
//		Calendar now = Calendar.getInstance();
//		now.set(Calendar.HOUR_OF_DAY, 0);
//		now.set(Calendar.MINUTE, 0);
//		now.set(Calendar.SECOND, 0);
//		//前一天的日期
//		Calendar lastDate =  Calendar.getInstance();
//		lastDate.add(Calendar.DATE, -1);
//
//		String lastDateStr = format.format(lastDate);
//
//		//发送短信的配置
//		SysParam sendUrl = sysParamService.selectByKey("send_sms_url");
//		String url = "";
//		if(sendUrl != null){
//			url = sendUrl.getValue();
//		}
//
//		final String CMD_INFO_TABLE_NAME = "c_cmd_info";
//		final String CAR_RUN_LOG_TABLE_NAME = "x_car_run_log";
//		final String SYS_WARN_TABLE_NAME = "x_sys_warn_info";
//
//		StringBuffer tableStr = new StringBuffer("");
//		try{
//			//如果cmd表记录数超出上限
//			{
//				int cmdInfoCnt = splitHistoryTablesMapper.queryCmdInfoCnt();
//				if(cmdInfoCnt >= limit){
//					logger.info("cmd表记录数超出上限 " + limit + "， 记录条数=" + cmdInfoCnt);
//
//					String newCmdTableName = CMD_INFO_TABLE_NAME + "_" + lastDateStr;
//					splitHistoryTablesMapper.createNewHistoryTable(newCmdTableName, CMD_INFO_TABLE_NAME, now.getTime());
//					splitHistoryTablesMapper.deleteOrigTableRecords(CMD_INFO_TABLE_NAME, now.getTime());
//				}
//			}
//
//			//如果car_run_log表记录数超出上限
//			{
//				int carRunLogCnt = splitHistoryTablesMapper.queryCarRunLogCnt();
//				if(carRunLogCnt >= limit){
//					logger.info("car_run_log表记录数超出上限 " + limit + "， 记录条数=" + carRunLogCnt);
//
//					String newCarRunLogTableName = CAR_RUN_LOG_TABLE_NAME + "_" + lastDateStr;
//					splitHistoryTablesMapper.createNewHistoryTable(newCarRunLogTableName, CAR_RUN_LOG_TABLE_NAME, now.getTime());
//					splitHistoryTablesMapper.deleteOrigTableRecords(CAR_RUN_LOG_TABLE_NAME, now.getTime());
//				}
//			}
//
//			//如果sys_warn表记录数超出上限
//			{
//				int sysWarnCnt = splitHistoryTablesMapper.querySysWarnCnt();
//				if(sysWarnCnt >= limit){
//					logger.info("sys_warn表记录数超出上限 " + limit + "， 记录条数=" + sysWarnCnt);
//
//					String newSysWarnTableName = SYS_WARN_TABLE_NAME + "_" + lastDateStr;
//					splitHistoryTablesMapper.createNewHistoryTable(newSysWarnTableName, SYS_WARN_TABLE_NAME, now.getTime());
//					splitHistoryTablesMapper.deleteOrigTableRecords(SYS_WARN_TABLE_NAME, now.getTime());
//				}
//			}
//		}finally{
//			{
//				int newCmdInfoCnt = splitHistoryTablesMapper.queryCmdInfoCnt();
//				if(newCmdInfoCnt >= msgLimit){
//					tableStr.append(CMD_INFO_TABLE_NAME + ",");
//				}
//			}
//			{
//				int newCarRunLogCnt = splitHistoryTablesMapper.queryCarRunLogCnt();
//				if(newCarRunLogCnt >= msgLimit){
//					tableStr.append(CAR_RUN_LOG_TABLE_NAME + ",");
//				}
//			}
//			{
//				int newSysWarnCnt = splitHistoryTablesMapper.querySysWarnCnt();
//				if(newSysWarnCnt >= msgLimit){
//					tableStr.append(SYS_WARN_TABLE_NAME + " ");
//				}
//			}
//			//发送预警短信
//			if(0 < tableStr.toString().length()){
//				try{
//					for(String phone : PHONE_LIST){
//						sendSMS(url, phone, "", "2682", tableStr.toString() +  "|" + msgLimit);
//					}
//				}catch(Exception e){
//					logger.error("发送预警短信出错", e);
//				}
//			}
//		}
//	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		logger.info("定时查cmd、car_run_log、sys_warn等历史表，各表如果记录数超过sysParam中的配置数，则进行分表");
		String limitStr= sysParamService.selectByKey(HIS_TAB_REC_CNT_LIMIT).getValue();
		String msgLimitStr= sysParamService.selectByKey(HIS_TAB_SEND_MSG_LIMIT).getValue();
		final int limit = StringUtils.isNotBlank(limitStr) ? Integer.valueOf(limitStr).intValue() : 1000000;
		final int msgLimit = StringUtils.isNotBlank(msgLimitStr) ? Integer.valueOf(msgLimitStr).intValue() : 3000000;

		//当天0点
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);

		String currDateStr = format.format(now);

		//发送短信的配置
		SysParam sendUrl = sysParamService.selectByKey("send_sms_url");
		String url = "";
		if(sendUrl != null){
			url = sendUrl.getValue();
		}

//		final String BACK_UP_DB_NAME = "iber_log.";
		final String BACK_UP_DB_NAME = "iber_backup.";
//		final String CMD_INFO_TABLE_NAME = "c_cmd_info";
		final String CAR_RUN_LOG_TABLE_NAME = "x_car_run_log";
		final String SYS_WARN_TABLE_NAME = "x_sys_warn_info";

		StringBuffer tableStr = new StringBuffer("");
		try{
			//如果cmd表记录数超出上限
//			{
//				int cmdInfoCnt = splitHistoryTablesMapper.queryCmdInfoCnt();
//				if(cmdInfoCnt >= limit){
//					logger.info("cmd表记录数超出上限 " + limit + "， 记录条数=" + cmdInfoCnt);
//
//					String newCmdTableName = BACK_UP_DB_NAME + CMD_INFO_TABLE_NAME + "_" + currDateStr;
//					splitHistoryTablesMapper.createNewHistoryTable(newCmdTableName, CMD_INFO_TABLE_NAME, now.getTime());
//					splitHistoryTablesMapper.deleteOrigTableRecords(CMD_INFO_TABLE_NAME, now.getTime());
//				}
//			}

			//如果car_run_log表记录数超出上限
			{
				int carRunLogCnt = splitHistoryTablesMapper.queryCarRunLogCnt();
				if(carRunLogCnt >= limit){
					logger.info("car_run_log表记录数超出上限 " + limit + "， 记录条数=" + carRunLogCnt);

					String newCarRunLogTableName = BACK_UP_DB_NAME + CAR_RUN_LOG_TABLE_NAME + "_" + currDateStr;
					splitHistoryTablesMapper.createNewHistoryTable(newCarRunLogTableName, CAR_RUN_LOG_TABLE_NAME, now.getTime());
					splitHistoryTablesMapper.deleteOrigTableRecords(CAR_RUN_LOG_TABLE_NAME, now.getTime());
				}
			}

			//如果sys_warn表记录数超出上限
			{
				int sysWarnCnt = splitHistoryTablesMapper.querySysWarnCnt();
				if(sysWarnCnt >= limit){
					logger.info("sys_warn表记录数超出上限 " + limit + "， 记录条数=" + sysWarnCnt);

					String newSysWarnTableName = BACK_UP_DB_NAME + SYS_WARN_TABLE_NAME + "_" + currDateStr;
					splitHistoryTablesMapper.createNewHistoryTable(newSysWarnTableName, SYS_WARN_TABLE_NAME, now.getTime());
					splitHistoryTablesMapper.deleteOrigTableRecords(SYS_WARN_TABLE_NAME, now.getTime());
				}
			}
		}finally{
//			{
//				int newCmdInfoCnt = splitHistoryTablesMapper.queryCmdInfoCnt();
//				if(newCmdInfoCnt >= msgLimit){
//					tableStr.append(CMD_INFO_TABLE_NAME + ",");
//				}
//			}
			{
				int newCarRunLogCnt = splitHistoryTablesMapper.queryCarRunLogCnt();
				if(newCarRunLogCnt >= msgLimit){
					tableStr.append(CAR_RUN_LOG_TABLE_NAME + ",");
				}
			}
			{
				int newSysWarnCnt = splitHistoryTablesMapper.querySysWarnCnt();
				if(newSysWarnCnt >= msgLimit){
					tableStr.append(SYS_WARN_TABLE_NAME + " ");
				}
			}
			//发送预警短信
			if(0 < tableStr.toString().length()){
				try{
					for(String phone : PHONE_LIST){
						sendSMS(url, phone, "", "2682", tableStr.toString() +  "|" + msgLimit);
					}
				}catch(Exception e){
					logger.error("发送预警短信出错", e);
				}
			}
		}
	}

}
