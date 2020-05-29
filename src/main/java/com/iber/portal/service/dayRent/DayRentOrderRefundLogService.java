package com.iber.portal.service.dayRent;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.util.SendSMS;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.Pager;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.base.MoneyLogMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.dayRent.DayRentOrderPayLogMapper;
import com.iber.portal.dao.dayRent.DayRentOrderRefundLogMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MoneyLog;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.dayRent.DayRentOrderPayLog;
import com.iber.portal.model.dayRent.DayRentOrderRefundLog;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.vo.dayRent.DayRentRefundVo;

@Service
public class DayRentOrderRefundLogService {

	@Autowired
	private DayRentOrderRefundLogMapper dayRentOrderRefundLogMapper;
	@Autowired
	private MoneyLogMapper moneyLogMapper;
	@Autowired
	private SysParamMapper sysParamMapper;
	@Autowired
	private DayRentOrderPayLogMapper dayRentOrderPayLogMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	public Pager<DayRentRefundVo> getAll(Map<String, Object> paramMap) {
		List<DayRentRefundVo> listObj = dayRentOrderRefundLogMapper.getAll(paramMap);
		Pager<DayRentRefundVo> pager = new Pager<DayRentRefundVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentOrderRefundLogMapper.getAllNum(paramMap));
		return pager;
	}

	public DayRentOrderRefundLog queryRefundLogByOrderId(
			String orderId) {
		return dayRentOrderRefundLogMapper.queryRefundLogByOrderId(orderId);
	} 
	public DayRentOrderRefundLog selectByPrimaryKey(
			Integer id) {
		return dayRentOrderRefundLogMapper.selectByPrimaryKey(id);
	} 
	
	public int updateByPrimaryKeySelective(DayRentOrderRefundLog model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderRefundLogMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public String RentOrderRefundLog(DayRentOrderRefundLog dayRentOrderRefundLog) throws Exception {
		String msg = "success";
		Map<String, Object> payLogMap = new HashMap<String, Object>();
		payLogMap.put("orderId", dayRentOrderRefundLog.getOrderId());
		payLogMap.put("orderType", SysConstant.DAY_RENT_TYPE_DAY_RENT);
		List<DayRentOrderPayLog> dayRentOrderPayLogs = dayRentOrderPayLogMapper.queryDayRentOrderPayLogByOrderId(payLogMap);
		if(dayRentOrderPayLogs.size()>0){
			DayRentOrderPayLog dayRentOrderPayLog = dayRentOrderPayLogs.get(0);
			String refundType = dayRentOrderRefundLog.getRefundType();
			String transactionId = dayRentOrderPayLog.getPayId();//支付ID
			String outTradeNo = dayRentOrderPayLog.getTradeNo();//支付流水号
			int totalFee = dayRentOrderPayLog.getPayMoney();//支付金额
			if("A".equals(refundType)){//支付宝
				JSONObject json = new JSONObject();
				json.put("cId", "platForm");
				json.put("memberId", dayRentOrderRefundLog.getMemberId());
				json.put("method", "aliRefund");
				JSONObject paramJson = new JSONObject();
				paramJson.put("batchNo", dayRentOrderRefundLog.getRefundId());//推款ID
				paramJson.put("outTradeNo", outTradeNo);//支付流水号
				paramJson.put("refundFee", String.valueOf(dayRentOrderRefundLog.getRefundMoney()));//退款金额
				paramJson.put("notifyUrl", "");
				json.put("param", paramJson);
				json.put("phone", "");
				json.put("type", "platForm");
				json.put("version", "1.0");
				String str = "";
				SysParam sysParam = sysParamMapper.selectByKey("http_url") ;
				if(sysParam.getValue().indexOf("https") == 0){ //https接口
					str = HttpsClientUtil.get(sysParam.getValue(), json.toString());
				}else{
					str = HttpUtils.commonSendUrl(sysParam.getValue(), json.toString());
				}
				JSONObject jsonObject = JSONObject.fromObject(str) ;
				String code = jsonObject.getString("code");
				if("00".equals(code)){
					//插入消费日志信息
					MoneyLog moneyLog = new MoneyLog();
					moneyLog.setCategory(dayRentOrderRefundLog.getOrderType());
					moneyLog.setCreateTime(new Date());
					moneyLog.setMemberId(dayRentOrderRefundLog.getMemberId());
					moneyLog.setMoney(dayRentOrderRefundLog.getRefundMoney());
					moneyLog.setType("+");
					moneyLog.setObjId(dayRentOrderRefundLog.getOrderId());
					moneyLogMapper.insertSelective(moneyLog);
					//记录退款信息
					dayRentOrderRefundLog.setRefundStatus("finish");
					dayRentOrderRefundLog.setTradeNo(dayRentOrderRefundLog.getRefundId());
					dayRentOrderRefundLogMapper.updateByPrimaryKeySelective(dayRentOrderRefundLog);
					//发送消息
					sendMsg(10,2659,dayRentOrderRefundLog.getMemberId(),String.valueOf(totalFee-dayRentOrderRefundLog.getRefundMoney()),String.valueOf(dayRentOrderRefundLog.getRefundMoney()));
				}else{
					msg = "fail";
				}
				
			}else if("WX".equals(refundType)){//微信
				JSONObject json = new JSONObject();
				json.put("cId", "platForm");
				json.put("memberId", dayRentOrderRefundLog.getMemberId());
				json.put("method", "wxRefund");
				JSONObject paramJson = new JSONObject();
				paramJson.put("transactionId", transactionId);//支付id
				paramJson.put("outTradeNo", outTradeNo);//支付流水号
				paramJson.put("refundOrderId", dayRentOrderRefundLog.getRefundId());//推款ID
				paramJson.put("totalFee", String.valueOf(totalFee));//支付金额
				paramJson.put("refundFee", String.valueOf(dayRentOrderRefundLog.getRefundMoney()));//退款金额
				json.put("param", paramJson);
				json.put("phone", "");
				json.put("type", "platForm");
				json.put("version", "1.0");
				String str = "";
				SysParam sysParam = sysParamMapper.selectByKey("http_url") ;
				if(sysParam.getValue().indexOf("https") == 0){ //https接口
					str = HttpsClientUtil.get(sysParam.getValue(), json.toString());
				}else{
					str = HttpUtils.commonSendUrl(sysParam.getValue(), json.toString());
				}
				JSONObject jsonObject = JSONObject.fromObject(str) ;
				
				String code = jsonObject.getString("code");
				if("00".equals(code)){
					String refundId = jsonObject.getString("result");
					//插入消费日志信息
					MoneyLog moneyLog = new MoneyLog();
					moneyLog.setCategory(dayRentOrderRefundLog.getOrderType());
					moneyLog.setCreateTime(new Date());
					moneyLog.setMemberId(dayRentOrderRefundLog.getMemberId());
					moneyLog.setMoney(dayRentOrderRefundLog.getRefundMoney());
					moneyLog.setType("+");
					moneyLog.setObjId(dayRentOrderRefundLog.getOrderId());
					moneyLogMapper.insertSelective(moneyLog);
					//记录退款信息
					dayRentOrderRefundLog.setRefundStatus("finish");
					dayRentOrderRefundLog.setTradeNo(refundId);
					dayRentOrderRefundLogMapper.updateByPrimaryKeySelective(dayRentOrderRefundLog);
					//发送消息
					sendMsg(10,2659,dayRentOrderRefundLog.getMemberId(),String.valueOf(totalFee-dayRentOrderRefundLog.getRefundMoney()),String.valueOf(dayRentOrderRefundLog.getRefundMoney()));
				}else{
					msg = "fail";
				}
			}else{//余额
				msg = "fail";
			}
		}else{
			msg = "fail";
		}
		return msg;
	}
	/**
	 * 发送消息和短信
	 * @param i 消息ID
	 * @param j 短信ID
	 * @param memberId 会员ID
	 * @param m 手续费
	 * @param money 退款费
	 * @throws Exception
	 */
	private void sendMsg(int i, int j, Integer memberId, String m,
			String money) throws Exception {
		Member member = memberMapper.selectByPrimaryKey(memberId);
		SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(i);
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("dayRentCancel");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId(memberId);
		systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
		systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}",m).replace("{1}",money));
		systemMsgLogMapper.insertSelective(systemMsgLog);
		PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
		List<String> memberCidList = PushClient.queryClientId(member.getPhone());
		
		for (String phoneCid : memberCidList) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			PushClient.push(phoneCid, net.sf.json.JSONObject.fromObject(systemPush, jsonConfig));
		}
		
		/*//发短信
		Map<String, String> params = new HashMap<String, String>();
		//获取加密token
		String encryptToken = SendSMS.encryptBySalt(member.getPhone());
		String param ="{\"telephoneNo\":\""+member.getPhone()+"\",\"ipAddress\":\"\",\"templateId\":\""+j+"\",\"contentParam\":\""+new String((m+"|"+money).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
		params.put("msgContentJson", param); 
		HttpsClientUtil.post(sysParamMapper.selectByKey("send_sms_url").getValue()+"",params) ;*/
		//发送短信
		SendSMS.send(member.getPhone(),"",j,m+"|"+money);


	}
	
}
