package com.iber.portal.controller;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.dao.base.*;
import com.iber.portal.dao.member.MemberFreezeLogMapper;
import com.iber.portal.dao.sys.SysDicMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.dao.sys.SysRoleMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.*;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.deposit.Deposit;
import com.iber.portal.model.sys.*;
import com.iber.portal.service.base.MemberCardService;
import com.iber.portal.service.base.MemberChargeLogService;
import com.iber.portal.service.base.MemberRefundLogService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.deposit.DepositService;
import com.iber.portal.service.member.MemberContributedDetailService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.sys.SysUserService;
import com.iber.portal.util.SendSMS;
import com.iber.portal.util.refund.AlipayRefundCore;
import com.iber.portal.util.refund.WXRefundCore;
import com.iber.portal.vo.member.ChargeLogVo;
import com.iber.portal.vo.member.MemberRefundVo;
import com.iber.portal.vo.timeShare.NoPayOrderVo;
import com.iber.portal.vo.wzFeeAndPoint.WzFeeAndPointVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MemberRefundController extends MainController{

	private static final Logger logger = LoggerFactory
			.getLogger(MemberRefundController.class);

	@Autowired
	private MemberRefundLogService memberRefundLogService;
	@Autowired
	private MemberRefundLogMapper memberRefundLogMapper;
	@Autowired
	private MemberRefundWorderDetailMapper  memberRefundWorderDetailMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private WZQueryMapper wzQueryMapper1;
	
	@Autowired
	private SysDicMapper sysDicMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	@Autowired
	private MemberCardMapper memberCardMapper;
	
	@Autowired
	private SysParamMapper sysParamMapper;
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
    private MoneyLogMapper moneyLogMapper ;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private MemberContributedDetailService memberContributedDetailService;
	
	@Autowired
	private MemberChargeLogService memberChargeLogService;
	
	@Autowired
	private TimeShareOrderMapper timeShareOrderMapper;
	
	@Autowired
	private MemberCardService memberCardService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberChargeLogMapper memberChargeLogMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private DepositService depositService;

	@Autowired
	private MemberFreezeLogMapper memberFreezeLogMapper;

	
	
	/**跳转到退款页面*/ 
	@SystemServiceLog(description="跳转到退款页面")
	@RequestMapping(value = "/member_refund_page", method = { RequestMethod.GET })
	public String memberRefundPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询退款审核顺序
		List<SysDic> sysDicData =  sysDicMapper.selectListByDicCode("REFUND_PARAMS");
		if(null != sysDicData){
			String refundFlow = sysDicData.get(0).getCode();
			SysRole firstRole = sysRoleMapper.selectByPrimaryKey(Integer.parseInt(refundFlow.split(",")[0]));
			request.setAttribute("firstRole", firstRole);
		}
		return "member/refund";		
	} 
	@SystemServiceLog(description="查询退款页面")
	@RequestMapping(value = "/refund_audit_base_info", method = { RequestMethod.GET,RequestMethod.POST})
	public String queryMemberRefundLogDetail(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		MemberRefundLog data = memberRefundLogMapper.selectByPrimaryKey(id);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONObject.fromObject(data, jsonConfig).toString());
		return null;
	} 
	@SystemServiceLog(description="退款明细")
	@RequestMapping(value = "/refund_worder_detail_log", method = { RequestMethod.GET, RequestMethod.POST })
	public String refundWorderDetailLog(Integer id,HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<MemberRefundWorderDetail> data = memberRefundWorderDetailMapper.selectMemberRefundWorderDetailByRid(id);
		StringBuffer html = new StringBuffer();
		if(null != data){
			String ptime = "0";
			int  idx = 0;
			for(MemberRefundWorderDetail log : data){
				idx++;
				if(idx == data.size()){
					 ptime = "0";
				}else{
					ptime = data.get(idx).getAuditorDatetimeStr();
				}
				long seconds = (log.getAuditorDatetime().getTime()-log.getApplyTime().getTime())/1000;
				long hour = seconds/3600;
				long mins = (seconds - 3600*hour)/60;
				long second = seconds%60;
				StringBuffer subHtml = new StringBuffer();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				subHtml.append("<tr><td rowspan='3' align='center'>").append(sdf.format(log.getAuditorDatetime()));
				subHtml.append("</td><td>").append(log.getAuditor());
				subHtml.append(log.getAuditorResult().equals("1")? "通过。" :"不通过。");
				subHtml.append(log.getAuditorRemark()).append(StringUtils.isBlank(log.getAuditorRemark())? "" :"。");
				subHtml.append("</td></tr>");
				subHtml.append("<tr><td>").append("附件:<a href='").append(log.getAuditorAccessoryFile()).append("'>").append(StringUtils.isBlank(log.getAuditorAccessoryFilename()) ? "" : log.getAuditorAccessoryFilename()).append("</a></td></tr>");
				subHtml.append("<tr><td>").append("审核用时:约").append(hour+"小时"+mins+"分"+second+"秒").append("</td></tr>");
				html.append(subHtml.toString());
			}
			if(html != null){
				html.append("<tr><td align='center'>").append("审核：</td><td></td></tr>");
			}
		}
		response.getWriter().print(html.toString());
		return null;
	}
	
	@SystemServiceLog(description="退款详情导出")
	@RequestMapping(value = "/refund_download_file", method = { RequestMethod.GET, RequestMethod.POST })
	public String refund_download_file(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String filePath = request.getParameter("file_path");
		//String  realPath = request.getSession().getServletContext().getRealPath(".");
		//String fileRealPath = realPath+"/"+filePath;
		//if(new File(fileRealPath).exists()){
			FileUtil.fileDownload(filePath, response);
		//}
		response.sendError(404, "File does not exist!");
		return null;
	}
	
	/**查询退款明细列表*/
	@SystemServiceLog(description="查询退款明细列表")
	@RequestMapping(value = "/member_refund_data_list", method = { RequestMethod.GET,RequestMethod.POST})
	public String memberRefundLogList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
    	   map.put("cityCode", user.getCityCode());
       }
		//map.put("cityCode", request.getParameter("cityCode"));
		map.put("name", request.getParameter("name"));
		map.put("phone", request.getParameter("phone"));
		map.put("status", request.getParameter("status"));
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		map.put("isHandleReturn", request.getParameter("isHandleReturn"));
		map.put("passTime", request.getParameter("passTime"));
		List<SysUserRole> sysUserRoles = sysUserService.selectUserRoleByUserId(user.getId());
		//如果筛选审核中的，运营的只看到运营的，财务的只看到财务审核中的
		if ("1".equals(request.getParameter("status"))) {
			for (SysUserRole sysUserRole : sysUserRoles) {
				if (!"admin".equals(user.getAccount()) && sysUserRole.getRoleId().equals(68)) {
					map.put("nextHandleRole", sysUserRole.getRoleId());
				}else if (!"admin".equals(user.getAccount()) && sysUserRole.getRoleId().equals(67)) {
					map.put("nextHandleRole", null);
				}
			}
		}
		//如果是admin账户，可以看到运营和财务处于审核中的记录
		if ("admin".equals(user.getAccount())){
			map.put("role","admin");
		}else {
			map.put("role",null);
		}
		List<MemberRefundLog> data = memberRefundLogService.selectAllRefundLog(map);
		int totalRecords = memberRefundLogService.selectAllRefundLogRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;		
	}
	@SystemServiceLog(description="退款工单审核")
	@RequestMapping(value = "/member_refund_worder_detail", method = { RequestMethod.GET,RequestMethod.POST})
	public String refundWorderDetail(int rid, int currRoleId,  MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String auditorResult = request.getParameter("auditorResult");
		String auditorRemark = request.getParameter("auditorRemark");
		//如果会员取消退款，就不能审核
		MemberRefundLog memberRefundLog = memberRefundLogMapper.selectByPrimaryKey(rid);
		if (memberRefundLog.getStatus().equals("4")) {//取消退款
			response.getWriter().print("hadCancel");
			return null;
		}
		Date currDate = new Date();
		
		PushCommonBean push = null;
		
		//发送短信
		//String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();
		String param = ""; 
		String ip = getRemortIP(request);
		Map<String, String> params = new HashMap<String, String>();  
		
		MemberRefundWorderDetail memberRefundWorderDetail = new MemberRefundWorderDetail();
		if(!uploadFile.isEmpty()){
//			String fileName = uploadFile.getOriginalFilename();
//			String fileNameP = fileName.substring(fileName.lastIndexOf("."));
//			String path =  request.getSession().getServletContext().getRealPath("member/refundFile");
//			String tmpFileName = CharacterUtils.getRandomString2(10)+ fileNameP;
//			String tmpPath = path + File.separator + tmpFileName;	//上传文件的临时目录
//			uploadFile.transferTo(new File(tmpPath));
			
	        String filename = uploadFile.getOriginalFilename();  
	        InputStream is = uploadFile.getInputStream();  
	        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
	      //文件上传到CDN
	        String endpoint = sysParamService.selectByKey("endpoint").getValue();
	        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
	        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
	        String bucketName = sysParamService.selectByKey("bucketName").getValue();
	        String dns = sysParamService.selectByKey("dns").getValue();
		    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
			String fileURL = oss.putObject(newFileName, is, "refundFile/");
			
			memberRefundWorderDetail.setAuditorAccessoryFilename(filename);
			memberRefundWorderDetail.setAuditorAccessoryFile(fileURL);
		}
		memberRefundWorderDetail.setRid(rid);
		memberRefundWorderDetail.setAuditorResult(auditorResult);
		memberRefundWorderDetail.setAuditorRemark(auditorRemark);
		memberRefundWorderDetail.setAuditor(getUser(request).getName());
		memberRefundWorderDetail.setAuditorRoleId(String.valueOf(currRoleId));
		memberRefundWorderDetail.setAuditorDatetime(currDate);
		memberRefundWorderDetailMapper.insertSelective(memberRefundWorderDetail);
		
		
		//update 
		
		memberRefundLog.setLastHandleUser(getUser(request).getName());
		memberRefundLog.setLastHandleTime(currDate);
		memberRefundLog.setStatus(auditorResult);
		Member memeber = memberMapper.selectByPrimaryKey(memberRefundLog.getMemberId());
		if(!auditorResult.equals("1")){
			memberRefundLog.setFailReason(auditorRemark);
			memberMapper.updateMemberAccountStatus("0", memberRefundLog.getMemberId());
			//保存用户账户解冻日志
			String reason = "会员退款被驳回，资金解冻";
			new InsertFreezeLogUtil(memberFreezeLogMapper).insertFreezeLog(null,String.valueOf(memeber.getId()),reason,1);
			SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(2);
			if(null != systemMsg){
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType("refund");
				systemMsgLog.setCreateTime(currDate);
				systemMsgLog.setMemberId( memberRefundLog.getMemberId());
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = systemMsg.getMsgContent().replaceAll("FAILREASON", auditorRemark);
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
				if(push != null){
					List<String> alias = PushClient.queryClientId(memeber.getPhone());
					if(!alias.isEmpty() && alias.size()> 0){
						for(String cid : alias){
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
							PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
						}
					}
				}
				//发送短信 驳回
				String s = memberRefundLog.getName() + "|" + auditorRemark + "|" + sysParamService.selectByKey("kf_phone").getValue();
				//获取加密token
				/*String encryptToken = SendSMS.encryptBySalt(memberRefundLog.getRefundUserMoblie());
				param ="{\"telephoneNo\":\""+memberRefundLog.getRefundUserMoblie()+"\",\"ipAddress\":\""+ip+"\",\"templateId\":\"2499\",\"contentParam\":\""+new String(s.getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
				params.put("msgContentJson", param); 
				HttpsClientUtil.post(sendUrl+"",params) ;*/
				//发送短信
				SendSMS.send(memberRefundLog.getRefundUserMoblie(),ip,2499,s);


				memberRefundLogMapper.updateByPrimaryKeySelective(memberRefundLog);
				//查询出退款记录对应的充值记录
				List<MemberChargeLog> memberChargeLogs = memberChargeLogMapper.selectLogsByRefundId(rid);
				List<MemberRefundVo> vos = new ArrayList<MemberRefundVo>();
				for (MemberChargeLog memberChargeLog : memberChargeLogs) {
					MemberRefundVo memberRefundVo = new MemberRefundVo();
					memberRefundVo.setChargingId(memberChargeLog.getId());
					vos.add(memberRefundVo);
				}
				//将充值记录标记未退款
				if (vos.size() > 0) {
					Map<String,Object> paramMap = new HashMap<String, Object>();
					paramMap.put("vos", vos);
					paramMap.put("refundId", null);
					paramMap.put("isRefund", 0);
					memberChargeLogMapper.updateIsRefund(paramMap);
				}
				response.getWriter().print("succ");
				return null;
			}
		}
		Deposit deposit = depositService.selectDepositByMemberLevel(memeber.getLevelCode());
		Integer depositValue = 100000;
		if (deposit != null) {
			depositValue = deposit.getDepositValue();
		}
		//获得会员退款金额
		MemberCard memberCard = memberCardMapper.selectByMemberId(memeber.getId());
		Integer sum = memberRefundLog.getMoney() + depositValue;
		
		//查询会员是否还有未支付的订单（包括充电，日租，时租）
		List<NoPayOrderVo> vos = timeShareOrderMapper.queryNoPayOrderByMemberId(memberRefundLog.getMemberId());
		if(vos.size()<=0){
			  String cw = 	sysParamMapper.selectByKey("REFUND_CW__ROLE_ID").getValue();
			  String yy = 	sysParamMapper.selectByKey("REFUND_YY__ROLE_ID").getValue();
			  SystemMsg systemMsg =null;
			  if(cw.equals(String.valueOf(currRoleId))){
				  // systemMsg = systemMsgMapper.selectByPrimaryKey(7);  //与下面消息模板ID=1重复
			  }
			  if(yy.equals(String.valueOf(currRoleId))){
				  systemMsg = systemMsgMapper.selectByPrimaryKey(8);
			  }
			if(null != systemMsg){
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType("refund");
				systemMsgLog.setCreateTime(currDate);
				systemMsgLog.setMemberId( memberRefundLog.getMemberId());
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				String content = systemMsg.getMsgContent().replaceAll("MONEY", String.valueOf(memberRefundLog.getMoney() / 100D));
				systemMsgLog.setMsgContent(content);
				systemMsgLogMapper.insertSelective(systemMsgLog);
				push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
				if(push != null){
					List<String> alias = PushClient.queryClientId(memeber.getPhone());
					if(!alias.isEmpty() && alias.size()> 0){
						for(String cid : alias){  
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
							PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
						}
					}
				}
			}
		}else if(vos.size() > 0 && "1".equals(auditorResult) &&  sum > memberCard.getDeposit()){
			response.getWriter().print("noPay");
			return null;
		}
		
		List<SysDic> sysDicData =  sysDicMapper.selectListByDicCode("REFUND_PARAMS");
		if(null != sysDicData){
			int idx = 0;
			String refundFlow = sysDicData.get(0).getCode();
			String[] tmpArr = refundFlow.split(",");
			for(int i=0; i<tmpArr.length;i++){
				if(tmpArr[i].equals(String.valueOf(currRoleId))){
					idx = i;
					break;
				}
			}
			if(idx != (tmpArr.length-1)){
				memberRefundLog.setNextHandleUserRoleId(Integer.parseInt(tmpArr[idx+1]));	
			}else{
				//表示处理完毕
				//memberMapper.updateMemberAccountStatus("0", memberRefundLog.getMemberId());
				
				if(auditorResult.equals("1")){
					memberRefundLog.setStatus("5");
					/*//处理完毕后，账户钱减去
					MemberCard memberCard = memberCardMapper.selectByMemberId(memberRefundLog.getMemberId());
					Integer refundMoney = 0;
					if(memberRefundLog.getChargeCategory().equalsIgnoreCase("B")){
						if(memberCard.getDeposit() < 0){
							refundMoney = memberRefundLog.getMoney() + memberCard.getDeposit();
							if (memberCard.getTotalRefundMoney() != null) {
								refundMoney = refundMoney + memberCard.getTotalRefundMoney();
							}
							memberCardMapper.updatMemberCardMoney(1, refundMoney,  memberRefundLog.getMemberId());
						}else{
							refundMoney = memberRefundLog.getMoney();
							if (memberCard.getTotalRefundMoney() != null) {
								refundMoney = refundMoney + memberCard.getTotalRefundMoney();
							}
							memberCardMapper.updatMemberCardMoney(0, refundMoney,  memberRefundLog.getMemberId());
						}
					}
					if(memberRefundLog.getChargeCategory().equalsIgnoreCase("D")){
						if(memberCard.getMoney() < 0){
							refundMoney = memberRefundLog.getMoney() + memberCard.getMoney();
							Integer totalRefundMoney = 0;
							if (memberCard.getTotalRefundMoney() != null) {
								totalRefundMoney = refundMoney + memberCard.getTotalRefundMoney();
							}
							memberCardMapper.updatMemberCardDeposit(1, refundMoney,  memberRefundLog.getMemberId(),totalRefundMoney);
						}else{
							refundMoney = memberRefundLog.getMoney();
							Integer totalRefundMoney = 0;
							if (memberCard.getTotalRefundMoney() != null) {
								totalRefundMoney = refundMoney + memberCard.getTotalRefundMoney();
							}
							memberCardMapper.updatMemberCardDeposit(0, refundMoney,  memberRefundLog.getMemberId(),totalRefundMoney);
						}
					}
					//资金明细新增退款信息
					MoneyLog moneyLog = new MoneyLog();
					moneyLog.setCreateTime(new Date());
					moneyLog.setMemberId(memberRefundLog.getMemberId());
					moneyLog.setMoney(refundMoney);
					moneyLog.setType("-");
					moneyLog.setObjId(memberRefundLog.getRefundId());
					moneyLog.setCategory("refund");
					moneyLogMapper.insertSelective(moneyLog);
					
					SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(1);
					if(null != systemMsg){
						SystemMsgLog systemMsgLog = new SystemMsgLog();
						systemMsgLog.setMsgType("refund");
						systemMsgLog.setCreateTime(currDate);
						systemMsgLog.setMemberId( memberRefundLog.getMemberId());
						systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
						String content = systemMsg.getMsgContent().replaceAll("MONEY", String.valueOf(memberRefundLog.getMoney() / 100D));
						systemMsgLog.setMsgContent(content);
						systemMsgLogMapper.insertSelective(systemMsgLog);
						push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
						if(push != null){
							List<String> alias = PushClient.queryClientId(memeber.getPhone());
							if(!alias.isEmpty() && alias.size()> 0){
								for(String cid : alias){
									JsonConfig jsonConfig = new JsonConfig();
									jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
									PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
								}
							}
						}
						//发送短信 成功
						String s = memberRefundLog.getName() + "|" + String.valueOf(memberRefundLog.getMoney() / 100D) ;
						param ="{\"telephoneNo\":\""+memberRefundLog.getRefundUserMoblie()+"\",\"ipAddress\":\""+ip+"\",\"templateId\":\"2498\",\"contentParam\":\""+new String(s.getBytes("utf-8"),"ISO-8859-1")+"\"}";
						params.put("msgContentJson", param); 
						HttpsClientUtil.post(sendUrl+"",params) ;
					}
					*/
				}
			}
		}
		memberRefundLogMapper.updateByPrimaryKeySelective(memberRefundLog);
		response.getWriter().print("succ");
		return null;
	}
	
	@Autowired
	private MemberRefundWorderUsecarMapper memberRefundWorderUsecarMapper;
	private Object wzQueryMapper;
	
	@SystemServiceLog(description="违章事故录入")
	@RequestMapping(value = "/use_car_entry", method = { RequestMethod.GET, RequestMethod.POST })
	public String useCarEntry(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "member/useCarEntry";
	}
	@SystemServiceLog(description="违章事故数据")
	@RequestMapping(value = "/use_car_entry_data", method = { RequestMethod.GET, RequestMethod.POST })
	public String useCarEntryData(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//map.put("cityCode", request.getParameter("cityCode"));
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
    	   map.put("cityCode", user.getCityCode());
       }
		map.put("lpn", request.getParameter("lpn"));
		map.put("bt", request.getParameter("bt"));
		map.put("et", request.getParameter("et"));
		map.put("custName", request.getParameter("custName"));
		map.put("custPhone", request.getParameter("custPhone"));
		map.put("isTrafficCitation", request.getParameter("isTrafficCitation"));
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		if(!StringUtils.isBlank(request.getParameter("orderId"))){
	    	   String orderId = request.getParameter("orderId");
	    	   map.put("orderId", orderId);
	    }
        
		List<MemberRefundWorderUsecar> data = memberRefundWorderUsecarMapper.selectOrderList(map);
		int records = memberRefundWorderUsecarMapper.selectOrderListRecords(map);
		String json = Data2Jsp.Json2Jsp(data, records);
		response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description="违章事故数据详情")
	@RequestMapping(value = "/use_car_entry_detail", method = { RequestMethod.GET, RequestMethod.POST })
	public String useCarEntryDetail(String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		MemberRefundWorderUsecar data = memberRefundWorderUsecarMapper.selectUserDetailByOrderId(orderId);//查违章订单的详细记录
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONObject.fromObject(data, jsonConfig).toString());
		return null;
	}
	@SystemServiceLog(description="违章事故数据存储")
	@RequestMapping(value = "/use_car_entry_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String useCarEntrySave(MemberRefundWorderUsecar record, MultipartFile uploadFile,  HttpServletRequest request, HttpServletResponse response, int isFeeByCompany) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String tmp = request.getParameter("hiddenIsTrafficCitation");
		String orderId = request.getParameter("myOrderId");
		String lpn = request.getParameter("hiddenLpn");
		//根据车牌号查询车架号以及发动机号
		Car car = carService.selectByLpn(lpn);
		if (car != null) {
			record.setCarVin(car.getClassno());
			record.setCarEngine(car.getEngineno());
		}
		record.setLpn(lpn);
		record.setIsTrafficCitation(tmp);
		record.setOrderId(orderId);
		/*if(tmp.equals("1")){
			record.setTrafficCitationCharge(record.getTrafficCitationCharge() * 100);
		}*/
		
		Integer memberId = null;//会员ID
		//查询出该订单产生的所有违章记录
		List<WZQuery> wzQuerys = wzQueryMapper1.selectByOrderId(record.getOrderId());
		//表示该订单存在违章
		if (tmp.equals("1")){
		//判断是由会员支付还是公司支付
			if (isFeeByCompany == 1){
			//如果是会员支付，将违章费用和违章分数累加
				Double wzAmount = 0.0;
				int wzPoint = 0;
				for (WZQuery wzQuery : wzQuerys) {
					memberId = wzQuery.getMemberId();
					String money = wzQuery.getMoney();
					wzAmount += Double.valueOf(money);
					wzPoint += Integer.valueOf(wzQuery.getFen());
				}
				record.setWzFee(BigDecimal.valueOf(wzAmount).multiply(new BigDecimal(100)));
				record.setWzPoint(wzPoint);
				//由会员支付，那么违章支付状态设置为1，表示由会员支付
				record.setWzPayStatus(1);
			}else {
				//如果是公司支付，得到违章总费用，以及违章总分数
				record.setWzFee(record.getTotalWzFee().multiply(new BigDecimal(100)));
				record.setWzPoint(record.getWzPoint());
				//由公司支付
				record.setWzPayStatus(2);
			}
		}
		
		if(!uploadFile.isEmpty()){
//			String fileName = uploadFile.getOriginalFilename();
//			String fileNameP = fileName.substring(fileName.lastIndexOf("."));
//			String path =  request.getSession().getServletContext().getRealPath("member/useCarEntry");
//			String tmpFileName = CharacterUtils.getRandomString2(10)+ fileNameP;
//			String tmpPath = path + File.separator + tmpFileName;	//上传文件的临时目录
//			uploadFile.transferTo(new File(tmpPath));
			
			 String filename = uploadFile.getOriginalFilename();  
	        InputStream is = uploadFile.getInputStream();  
	        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
	       //文件上传到CDN
	        String endpoint = sysParamService.selectByKey("endpoint").getValue();
	        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
	        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
	        String bucketName = sysParamService.selectByKey("bucketName").getValue();
	        String dns = sysParamService.selectByKey("dns").getValue();
		    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
			String fileURL = oss.putObject(newFileName, is, "useCarEntry/");
			
			record.setAuditorAccessoryFilename(filename);
			record.setAuditorAccessoryFile(fileURL);
		}
		record.setAuditor(getUser(request).getName());
		record.setAuditorTime(new Date());
		int r = memberRefundWorderUsecarMapper.insertSelective(record);
		if(r > 0){
			//若是会员自行支付处理违章，则加贡献值500
//			if(record.getWzPayStatus().equals(1) && null != memberId){
//				String type = "MEMBER_CONTRIBUTED_DEALED_WZ";
//				List<SysDic> dicList = this.sysDicMapper.selectListByDicCode(type);
//				if(null != dicList && !dicList.isEmpty()){
//					SysDic dic = dicList.get(0);
//					this.memberContributedDetailService.insertMemberContributedDetail(memberId, orderId, 
//							dic.getDicCode(), Double.valueOf(dic.getCode()));
//				}
//			}
			
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	@SystemServiceLog(description="违章事故数据清单")
	@RequestMapping(value = "/use_car_entry_query_detail", method = { RequestMethod.GET, RequestMethod.POST })
	public String useCarEntryQueryDetail(String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		MemberRefundWorderUsecar data = memberRefundWorderUsecarMapper.selectUserDetailByOrderId(orderId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONObject.fromObject(data, jsonConfig).toString());
		return null;
	}
	@SystemServiceLog(description="退款审核")
	@RequestMapping(value = "/refund_audit_cust_card_info", method = { RequestMethod.GET, RequestMethod.POST })
	public String refundAuditQueryCustCardInfo(int  memberId,HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
	    JSONObject obj = new JSONObject();
	    //查询是否违章记录信息
	    int r = memberRefundLogMapper.selectCustUsecarIsTrafficCitation(memberId);
	    if(r > 0){
	    	obj.put("isTrafficCitation", "未审核");
	    	obj.put("trafficCitationCharge", "未审核");
	    }else{
	    	int trafficCitationCharge = memberRefundLogMapper.selectCustUsecartrafficCitationCharge(memberId);
	    	obj.put("isTrafficCitation", "已审核");
	    	obj.put("trafficCitationCharge", trafficCitationCharge);
	    }
		response.getWriter().print(obj.toString());
		return null;
	}
	
	/**
	 * 根据memberId查询会员的违章记录
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/query_wz_record", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryWzRecord(Integer memberId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//List<WZQuery> wzQueries = wzQueryMapper1.queryWzRecord(memberId);
		List<WZQuery> wzQueries = wzQueryMapper1.queryUndisposedWzRecord(memberId);
		//根据会员id查询会员的押金
//		MemberCard memberCard = memberCardMapper.selectByMemberId(memberId);
//		Integer deposit = memberCard.getDeposit();
		//获取所有订单号，放到list集合中
//		List<String> orderIdList = new ArrayList<String>();
//		for (WZQuery wzQuery : wzQueries) {
//			orderIdList.add(wzQuery.getOrderId());
//			wzQuery.setDeposit(BigDecimal.valueOf(deposit));
//				if (wzQuery.getMemberRefundWorderUsecar().getWzPayStatus().equals(1)) {//如果由会员支付
//					wzQuery.setReturnFee(wzQuery.getMemberRefundWorderUsecar().getWzFee());
//				}else {//如果由公司支付
//					wzQuery.setReturnFee(BigDecimal.valueOf(deposit).subtract(wzQuery.getMemberRefundWorderUsecar().getWzFee()));
//				}
//			}
		
		//遍历所有的订单号
//		for (int index = 0; index < orderIdList.size() - 1; index++) {
			//将金额转换为以元为单位
//			if (wzQueries.get(index).getMemberRefundWorderUsecar().getWzFee()!=null) {
//				wzQueries.get(index).getMemberRefundWorderUsecar().setWzFee(wzQueries.get(index).
//						getMemberRefundWorderUsecar().getWzFee().divide(new BigDecimal(100)));
//			}
//			if (wzQueries.get(index).getDeposit()!=null) {
//				wzQueries.get(index).setDeposit(wzQueries.get(index).getDeposit().divide(new BigDecimal(100)));
//			}
//			if (wzQueries.get(index).getReturnFee()!=null) {
//				wzQueries.get(index).setReturnFee(wzQueries.get(index).getReturnFee().divide(new BigDecimal(100)));
//			}
			
			//如果订单号相同，只需要显示第一个违章金额，违章金额支付方以及退款金额
//			if (orderIdList.get(index).equals(orderIdList.get(index + 1))) {
//				wzQueries.get(index+1).getMemberRefundWorderUsecar().setWzFee(null);
//				wzQueries.get(index+1).getMemberRefundWorderUsecar().setWzPayStatus(null);
//				wzQueries.get(index+1).setReturnFee(null);
//			}
			
//		}
		//押金只需要显示在第一行
//		for (int index = 0; index < orderIdList.size(); index++) {
//			if (index!=0) {
//				wzQueries.get(index).setDeposit(null);
//			}
//		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONArray.fromObject(wzQueries, jsonConfig).toString());
		return null;
	}
	
	@RequestMapping(value="/query_wzFee_and_wzPoint", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryWzFeeAndWzPoint(String orderId, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<WZQuery> wzQuerys = wzQueryMapper1.selectByOrderId(orderId);
		Double wzFee = 0.0;
		int wzPoint = 0;
		for (WZQuery wzQuery : wzQuerys) {
			String money = wzQuery.getMoney();
			wzFee +=  Double.valueOf(money);
			wzPoint += Integer.valueOf(wzQuery.getFen());
		}
		WzFeeAndPointVO vo = new WzFeeAndPointVO();
		vo.setWzFee(BigDecimal.valueOf(wzFee));
		vo.setWzPoint(wzPoint);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONObject.fromObject(vo, jsonConfig).toString());
		return null;
	}
	
	@RequestMapping(value="/wz_order_record", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryWzOrderRecord(String orderId, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<WZQuery> wzQuerys = wzQueryMapper1.selectByOrderId(orderId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONArray.fromObject(wzQuerys, jsonConfig).toString());
		return null;
	}
    /**
     * 会员退款数据导出excel链接
	 * @Date   2017-05-17 16:00:00
	 * @author zhaocj
	 * @param cityCode     城市代号码
	 * @param name         会员姓名
	 * @param phone        会员电话号码
	 * @param refundStatus 退款状态
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
      */
	@SystemServiceLog(description = "会员退款数据导出excel链接")
	@RequestMapping(value = "/export_member_refund_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exportMemberRefundExcel(String cityCode,String name,String phone,String refundStatus,
			HttpServletRequest request,HttpServletResponse response)
					throws Exception{
		String fileName = "memberRefundReport";
		// 列名
		String columnNames[] = {  "所属城市", "姓名", "手机号码", "退款金额(元)", "银行开户行",
				"退款银行卡号", "申请时间", "最后用车时间", "退款状态", "退款原因", "最后处理时间","最后处理人","下一步处理人角色" };
		String keys[] = { "cityName", "name", "phone", "refundMoney",
				"bankName", "bankAccount", "applyTime", "userCarTime", "refundStatus", "refundReason",
				"lastHandleTime", "lastHandler","nextHandlerRole" };
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
        }else{
  	       map.put("cityCode", user.getCityCode());
       }
		//map.put("cityCode", user.getCityCode());
		map.put("name", new String(request.getParameter("name")));
		map.put("phone", request.getParameter("phone"));
		map.put("status", request.getParameter("status"));
		map.put("isHandleReturn", request.getParameter("isHandleReturn"));
		map.put("offset", null);
		map.put("rows", null);
		List<MemberRefundLog> data = memberRefundLogService.selectAllRefundLog(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "会员退款数据");
		list.add(sheetNameMap);
		list.addAll(createMemberRefundReportExcelRecord(data));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,null)
					.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	private List<Map<String, Object>> createMemberRefundReportExcelRecord(List<MemberRefundLog> MemberRefundLogs){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		for (MemberRefundLog memberRefundLog :MemberRefundLogs) {
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("cityName", memberRefundLog.getCityName());
			mapValue.put("name", memberRefundLog.getName());
			mapValue.put("phone", memberRefundLog.getPhone());
			mapValue.put("refundMoney",String.format("%.2f",(float)memberRefundLog.getMoney()/100));
			mapValue.put("bankName", memberRefundLog.getBankName());
			mapValue.put("bankAccount", memberRefundLog.getBankCard());
			mapValue.put("applyTime", null==memberRefundLog.getCreateTime()?"":formatter.format(memberRefundLog.getCreateTime()));
			mapValue.put("userCarTime", null==memberRefundLog.getUserCarTime()?"":formatter.format(memberRefundLog.getUserCarTime()));
			String status = memberRefundLog.getStatus();
			if("1".equals(status)){
				mapValue.put("refundStatus", "审核中");
			}else if ("2".endsWith(status)) {
				mapValue.put("refundStatus", "退款驳回");
			}else if ("3".equals(status)) {
				mapValue.put("refundStatus", "退款完成");
			}else {
				mapValue.put("refundStatus", "取消");
			}
			mapValue.put("refundReason", memberRefundLog.getReason());
			mapValue.put("lastHandleTime", null==memberRefundLog.getLastHandleTime()?"": formatter.format(memberRefundLog.getLastHandleTime()));
			mapValue.put("lastHandler", null==memberRefundLog.getLastHandleUser()?"":memberRefundLog.getLastHandleUser());
            mapValue.put("nextHandlerRole", memberRefundLog.getNextHandleUserRoleIdStr());
			listmap.add(mapValue);
		}
		return listmap;
	}
	
	@SystemServiceLog(description = "判断会员退款走原路返回还是手动打款")
	@RequestMapping(value="/member_refund", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberRefund(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		MemberRefundLog memberRefundLog = memberRefundLogMapper.selectByPrimaryKey(Integer.parseInt(id));
		if (!memberRefundLog.getStatus().equals("5")) {
			response.getWriter().print("disagree");
			return null;
		}
		//根据退款id查询对应的充值记录
		List<MemberChargeLog> logs = memberChargeLogService.selectByRefundId(Integer.parseInt(id));
		Integer count = 0;
		if (logs.size() > 0){
			count = logs.size();
		}
		/*for (MemberChargeLog memberChargeLog : logs) {
			Date applyTime = memberChargeLog.getCreateTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(applyTime);
			//获得充值记录的充值时间
			//判断是微信充值还是支付宝充值
			if ("A".equals(memberChargeLog.getBankCategory())) {//如果是支付宝充值，那么原路返回的时限是3个月
				//获得充值记录的充值时间
				calendar.add(Calendar.MONTH, 3);
				if (calendar.getTime().getTime() > new Date().getTime()) {//可以原路返回
					count = count + 1;
					continue;
				}
			}else if ("WX".equals(memberChargeLog.getBankCategory())) {//如果是微信充值,那么原路返回的时限是1年
				calendar.add(Calendar.YEAR, 1);
				if (calendar.getTime().getTime() > new Date().getTime()) {
					count = count + 1;
					continue;
				}
			}
		}*/
		if (count > 0) {//如果count>0,说明可以原路返回
			response.getWriter().print("success");
		}else{//不可以原路返回
			response.getWriter().print("fail");
		}
		return null;
	}
	
	/**
	 * 会员退款原路返回
	 */
	@SystemServiceLog(description = "会员退款原路返回")
	@RequestMapping(value="/member_return_refund", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberReturnRefund(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String id = request.getParameter("returnId");
		//根据退款id查询对应的充值记录
		List<MemberChargeLog> logs = memberChargeLogService.selectByRefundId(Integer.parseInt(id));
		MemberRefundLog memberRefundLog = memberRefundLogService.selectByPrimaryKey(Integer.parseInt(id));
		Member member = memberService.selectByPrimaryKey(memberRefundLog.getMemberId());
		if ("5".equals(memberRefundLog.getStatus())) {
			Integer refundMoneySum = 0;
			Integer count = 0;
			List<MemberChargeLog> wXLogs = new ArrayList<MemberChargeLog>();
			List<MemberChargeLog> aliLogs = new ArrayList<MemberChargeLog>();
			Integer aliLogCount = 0;
			for (MemberChargeLog memberChargeLog : logs) {
				if ("A".equals(memberChargeLog.getBankCategory())) {
					aliLogs.add(memberChargeLog);
				}else{
					wXLogs.add(memberChargeLog);
				}
			}
			for (MemberChargeLog wxLog : wXLogs) {
				Thread.sleep(1000L);
				logger.info("-- 微信退款开始 --");
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
				String refundOrderId = dateFormat.format( now );
			    String transactionId = wxLog.getTradeNo();
				String outTradeNo = wxLog.getChargeId();
				Integer totalFee = wxLog.getMoney();
				Integer chargedNum = memberRefundLog.getChargedNum() == null ? 0 : memberRefundLog.getChargedNum();
				memberRefundLog.setChargedNum(chargedNum + 1);
				memberRefundLogMapper.updateByPrimaryKeySelective(memberRefundLog);
				 	/**
					 * 
					 * @param transactionId  原支付流水号
					 * @param outTradeNo  原订单号
					 * @param refundOrderId  退款订单号，由商户生成，（32位字符组成）
					 * @param totalFee 原支付总价，单位分
					 * @param refundFee 退款总价，单位分，（注意：只能小于等于原支付总价）
					 */
				WXRefundCore core = new  WXRefundCore(transactionId,outTradeNo, refundOrderId, totalFee, totalFee);
				Map<String,String> map = core.wxRefundForMap();
				String returnCode = map.get("return_code").toUpperCase();
				String resultCode = null;
				if ("SUCCESS".equals(returnCode)) {
					resultCode = map.get("result_code").toUpperCase();
				}
				
				if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {//如果原路返回成功
					updateCardAndInsertMoneyLogIfSuccess(
							memberRefundLog, refundMoneySum, wxLog,
							totalFee);
						
				}else{//原路返回失败
					//将充值记录的is_refund设置为0
					updateChargedLogIfFail(count, wxLog,memberRefundLog);
				}
				
				if (memberRefundLog.getChargedNum() == logs.size()) {
					//将退款记录标记为退款完成,原路返回
					memberRefundLog.setStatus("3");
					memberRefundLog.setIsHandleReturn(1);
					memberRefundLog.setLastHandleTime(new Date());
					memberRefundLogService.updateByPrimaryKeySelective(memberRefundLog);
					//解冻资金
					memberMapper.updateMemberAccountStatus("0", memberRefundLog.getMemberId());
					//保存用户账户解冻日志
					String reason = "会员退款完成，资金解冻";
					new InsertFreezeLogUtil(memberFreezeLogMapper).insertFreezeLog(null,String.valueOf(member.getId()),reason,1);

				}
					
				//如果成功退款的记录大于0，并且现在是最后一条退款记录，就发送短信并推送消息
				Integer failNum = memberRefundLog.getRefundFailNum() == null ? 0 : memberRefundLog.getRefundFailNum();
				chargedNum = memberRefundLog.getChargedNum() == null ? 0 : memberRefundLog.getChargedNum();
				if (logs.size() - failNum > 0 && logs.size() == memberRefundLog.getChargedNum()) {
					sendMessage(memberRefundLog, member, memberRefundLog.getRefundSuccessMoney());
				}
				logger.info("--微信退款结束--");
			}
			for (MemberChargeLog aliLog : aliLogs) {
				Thread.sleep(1000L);
				Random r = new Random();
				String outTradeNo = aliLog.getTradeNo();
				//String batchNo = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + r.nextInt(9999);
				String batchNo = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + r.nextInt(9999);
				//String notifyUrl = "http://183.239.183.44:18888/services/i/refund_fastpay_by_platform_nopwd-JAVA-GBK/servlet/DBNotifyServlet";
				SysParam sysParam = sysParamService.selectByKey("alipay_refund_notify_url");
				String notifyUrl = sysParam.getValue();
				//String notifyUrl = "";
				Integer refundFee = aliLog.getMoney();
				/**
				 * 支付宝退款
				 * @param batchNo 批次号    格式为：退款日期（8 位当天 日期yyyyMMddHHmmss）+流水号（3～24 位， 流水号可以接受数字或英文 字符，建议使用数字，但不 可接受“000”）
				 * @param tradeNo  原银行流水号
				 * @param mny 退款金额（单位元，只能允许2位小数，如0.01元）
				 * @param notifyUrl 异步通知地址URL
				 */
				AlipayRefundCore core = new AlipayRefundCore(batchNo, outTradeNo, String.valueOf(Double.valueOf(refundFee)/Integer.valueOf(100)), notifyUrl);
				String alipayRefund = core.alipayRefund();
				aliLogCount += 1;
			}
		
			response.getWriter().print("success");
		}else {
			response.getWriter().print("hadfinish");
		}
		
		return null;
	}
	
	/**
	 * 如果原路返回失败，对充值记录进行更新
	 * @param count
	 * @param memberChargeLog
	 * @param memberRefundLog 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	private void updateChargedLogIfFail(Integer count,
			MemberChargeLog memberChargeLog, MemberRefundLog memberRefundLog) {
		Member member = memberService.selectByPrimaryKey(memberChargeLog.getMemberId());
		List<MemberRefundVo> vos = new ArrayList<MemberRefundVo>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isRefund", 0);
		paramMap.put("refundId",memberChargeLog.getRefundId());
		MemberRefundVo vo = new MemberRefundVo();
		vo.setChargingId(memberChargeLog.getId());
		vos.add(vo);
		paramMap.put("vos", vos);
		memberChargeLogService.updateIsRefund(paramMap);
		Integer refundFailNum = memberRefundLog.getRefundFailNum() == null ? 0 : memberRefundLog.getRefundFailNum();
		memberRefundLog.setRefundFailNum(refundFailNum + 1);
		memberRefundLogMapper.updateByPrimaryKey(memberRefundLog);
		//退款失败推送消息
		SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(24);
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("refund");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId( memberRefundLog.getMemberId());
		systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
		systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}",String.valueOf(memberChargeLog.getMoney()/Double.valueOf(100))));
		systemMsgLogMapper.insertSelective(systemMsgLog);
		PushCommonBean push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
		if(push != null){
			List<String> alias = PushClient.queryClientId(member.getPhone());
			if(!alias.isEmpty() && alias.size()> 0){
				for(String cid : alias){
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
					PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
				}
			}
		}
		//退款失败发送短信
//		String param = String.valueOf(memberChargeLog.getMoney()/Double.valueOf(100));
		SendSMS.send(member.getPhone(),getRemortIP(request),2700,"");
	}
	
	/**
	 * 原路返回成功后对会员账户进行更新，并生成一条流水记录
	 * @param memberRefundLog
	 * @param refundMoneySum
	 * @param memberChargeLog
	 * @param totalFee
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	private void updateCardAndInsertMoneyLogIfSuccess(
			MemberRefundLog memberRefundLog, Integer refundMoneySum,
			MemberChargeLog memberChargeLog, Integer totalFee) {
		MemberCard memberCard = memberCardService.selectByMemberId(memberChargeLog.getMemberId());
		//将用户账户的押金减去相应的值
		memberCard.setDeposit(memberCard.getDeposit() - totalFee);
		if (memberCard.getTotalRefundMoney() != null) {
			memberCard.setTotalRefundMoney(memberCard.getTotalRefundMoney() + totalFee);
		}
		memberCard.setLastDateTime(new Date());
		memberCard.setLastMoney(totalFee);
		memberCard.setLastRefundTime(new Date());
		memberCardService.updateByPrimaryKey(memberCard);
		//资金明细新增退款信息
		MoneyLog moneyLog = new MoneyLog();
		moneyLog.setCreateTime(new Date());
		moneyLog.setMemberId(memberChargeLog.getMemberId());
		moneyLog.setMoney(totalFee);
		moneyLog.setType("-");
		moneyLog.setObjId(memberRefundLog.getRefundId());
		moneyLog.setCategory("refund");
		moneyLogMapper.insertSelective(moneyLog);
		Integer refundSuccessMoney = memberRefundLog.getRefundSuccessMoney() == null ? 0 : memberRefundLog.getRefundSuccessMoney();
		memberRefundLog.setRefundSuccessMoney(refundSuccessMoney + totalFee);
		memberRefundLogMapper.updateByPrimaryKey(memberRefundLog);
		////如果回调成功，将这条充值记录再置为已退款
		memberChargeLog.setIsRefund(1);
		memberChargeLog.setRefundId(memberRefundLog.getId());
		memberChargeLogMapper.updateByPrimaryKey(memberChargeLog);
	}
	
	
	/**
	 * 会员退款手动打款
	 */
	@SystemServiceLog(description = "会员退款手动打款")
	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value="/member_handle_return_refund", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberHandleReturnRefund(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String id = request.getParameter("handleId");
		MemberRefundLog memberRefundLog = memberRefundLogService.selectByPrimaryKey(Integer.parseInt(id));
		MemberCard memberCard = memberCardService.selectByMemberId(memberRefundLog.getMemberId());
		Member member = memberService.selectByPrimaryKey(memberRefundLog.getMemberId());
		//解冻资金
		if ("5".equals(memberRefundLog.getStatus())) {
			memberMapper.updateMemberAccountStatus("0", memberRefundLog.getMemberId());
			//保存用户账户解冻日志
			String reason = "会员退款完成，资金解冻";
			new InsertFreezeLogUtil(memberFreezeLogMapper).insertFreezeLog(null,String.valueOf(member.getId()),reason,1);
			//将用户账户的押金减去相应的值
			//设置最后交易金额，最后交易时间，最后退款时间
			memberCard.setDeposit(memberCard.getDeposit() - memberRefundLog.getMoney());
			if (memberCard.getTotalRefundMoney() != null) {
				memberCard.setTotalRefundMoney(memberCard.getTotalRefundMoney() + memberRefundLog.getMoney());
			}
			memberCard.setLastDateTime(new Date());
			memberCard.setLastMoney(memberRefundLog.getMoney());
			memberCard.setLastRefundTime(new Date());
			int count1 = memberCardService.updateByPrimaryKey(memberCard);
			//资金明细新增退款信息
			MoneyLog moneyLog = new MoneyLog();
			moneyLog.setCreateTime(new Date());
			moneyLog.setMemberId(memberRefundLog.getMemberId());
			moneyLog.setMoney(memberRefundLog.getMoney());
			moneyLog.setType("-");
			moneyLog.setObjId(memberRefundLog.getRefundId());
			moneyLog.setCategory("refund-handle");
			moneyLogMapper.insertSelective(moneyLog);
			//将退款记录标记为退款完成
			memberRefundLog.setStatus("3");
			memberRefundLog.setIsHandleReturn(0);
			memberRefundLog.setLastHandleTime(new Date());
			int count2 = memberRefundLogService.updateByPrimaryKeySelective(memberRefundLog);
			//给会员发送消息和短信
			//发送短信和验证码
			if (count1 > 0 && count2 > 0) {
				sendMessage(memberRefundLog, member, memberRefundLog.getMoney());
				response.getWriter().print("success");
			}else {
				response.getWriter().print("fail");
			}
		}else {
			response.getWriter().print("hadfinish");
		}
		
		return null;
	}
	
	/**
	 * 发送短信并推送消息
	 * @param memberRefundLog
	 * @param member
	 * @param refundMoneySum
	 * @throws UnsupportedEncodingException
	 */
	private void sendMessage(MemberRefundLog memberRefundLog, Member member,
			Integer refundMoneySum) throws UnsupportedEncodingException {
		SystemMsg systemMsg = null;
		//发送短信 成功
		//String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();
		String param = "";
		//获取加密token
		String encryptToken = SendSMS.encryptBySalt(memberRefundLog.getRefundUserMoblie());
		Map<String, String> params = new HashMap<String, String>();
		if (memberRefundLog.getIsHandleReturn() != null && memberRefundLog.getIsHandleReturn() == 1) {//如果是原路返回
			systemMsg = systemMsgMapper.selectByPrimaryKey(23);
			String s = memberRefundLog.getName() +  "|" + 2; //"|" + String.valueOf(refundMoneySum / 100D) + 去除金額
			param ="{\"telephoneNo\":\""+memberRefundLog.getRefundUserMoblie()+"\",\"ipAddress\":\""+"\",\"templateId\":\"2688\",\"contentParam\":\""+new String(s.getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
		}else{//如果手动打款
			systemMsg = systemMsgMapper.selectByPrimaryKey(1);
			String s = memberRefundLog.getName() + "|" + String.valueOf(refundMoneySum / 100D) ;
			param ="{\"telephoneNo\":\""+memberRefundLog.getRefundUserMoblie()+"\",\"ipAddress\":\""+"\",\"templateId\":\"2498\",\"contentParam\":\""+new String(s.getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
		}
		if(null != systemMsg){
			SystemMsgLog systemMsgLog = new SystemMsgLog();
			systemMsgLog.setMsgType("refund");
			systemMsgLog.setCreateTime(new Date());
			systemMsgLog.setMemberId( memberRefundLog.getMemberId());
			systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
			String content = systemMsg.getMsgContent().replaceAll("MONEY", String.valueOf(refundMoneySum / 100D));
			systemMsgLog.setMsgContent(content);
			systemMsgLogMapper.insertSelective(systemMsgLog);
			PushCommonBean push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
			if(push != null){
				List<String> alias = PushClient.queryClientId(member.getPhone());
				if(!alias.isEmpty() && alias.size()> 0){
					for(String cid : alias){
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
						PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
					}
				}
			}
			params.put("msgContentJson", param);
			/*HttpsClientUtil.post(sendUrl+"",params) ;*/
			//发送短信
			SendSMS.send(param);

		}
	}

	@SystemServiceLog(description = "根据退款记录查询会员充值记录")
	@RequestMapping(value = "/query_member_charege_log",method = {RequestMethod.POST,RequestMethod.GET})
	public String queryMemberChargeLog(Integer id,Integer memberId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<ChargeLogVo> vos = memberRefundLogService.selectAllChargeLogByRefundId(id);
		response.getWriter().println(Data2Jsp.listToJson(vos));
		return null;
	}

	@SystemServiceLog(description = "查询会员退款驳回记录")
	@RequestMapping(value = "/query_member_reject_log",method = {RequestMethod.POST,RequestMethod.GET})
	public String queryMemberRejectLog(Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<Map<String,Object>> vos = memberRefundLogService.selectAllRejectLogByMemberId(id);
		response.getWriter().println(Data2Jsp.listToJson(vos));
		return null;
	}
	
	@SystemServiceLog(description = "手动打款方式驳回退款")
	@RequestMapping(value = "/contradict_refund",method = {RequestMethod.POST,RequestMethod.GET})
	public String contradictRefund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String refundId = request.getParameter("refund-id");
		String failReason = request.getParameter("contradictReason");
		if(StringUtils.isBlank(failReason)){
			response.getWriter().print("reasonNull");
			return null;
		}
		MemberRefundLog log = memberRefundLogMapper.selectByPrimaryKey(Integer.valueOf(refundId));
		String status = log.getStatus();
		if(Objects.equals("2", status)){
			response.getWriter().print("contradict");
			return null;
		}
		if(Objects.equals("4", status)){
			response.getWriter().print("cancel");
			return null;
		}
		if(Objects.equals("3", status)){
			response.getWriter().print("done");
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("failReason", failReason);
		map.put("id", refundId);
		// 退款驳回状态码是 2
		map.put("status", "2");
		map.put("memberId", log.getMemberId());
		map.put("accoutStatus", "0");
		Integer count = memberRefundLogMapper.updateRecordByMap(map);
		if(0<count){
			response.getWriter().print("success");
		}
		return null;
	}

}
