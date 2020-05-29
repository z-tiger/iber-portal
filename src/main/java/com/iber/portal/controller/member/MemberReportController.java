package com.iber.portal.controller.member;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.member.*;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.base.SystemMsgLogService;
import com.iber.portal.service.member.*;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.vo.member.MemberLastOrderVo;
import com.iber.portal.vo.member.MemberReportRelationVo;
import com.iber.portal.vo.member.MemberReportVo;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MemberReportController extends MainController{

	@Autowired
	private MemberReportService memberReportService;
	
	@Autowired
	private EvidenceRelationService evidenceRelationService;
	
	@Autowired
	private MemberBehaviorService memberBehaviorService;
	
	@Autowired
	private MemberBehaviorTypeService memberBehaviorTypeService;
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private MemberBlacklistLogService memberBlacklistLogService;
	
	@Autowired
	private MemberBlacklistService memberBlacklistService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SystemMsgLogService systemMsgLogService;
	
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	@Autowired
	private ParkService parkService;
	
	public static final Integer CHECK_STATUC_PASS = 1;
	public static final Integer CHECK_STATUC_NON = 0;
	public static final Integer CHECK_STATUC_REFUSE = 2;
	
	@SystemServiceLog(description="memberCreditManage_page页面")
	@RequestMapping(value="/memberCreditManage_page", method={RequestMethod.GET, RequestMethod.POST})
	public String memberCreditManagePage(HttpServletRequest request , HttpServletResponse response)throws Exception{
		return "credit/memberCredit";
	}
	
	@SystemServiceLog(description="反馈列表")
	@RequestMapping(value="/credit_list", method={RequestMethod.GET, RequestMethod.POST})
	public void CreditList(int page, int rows,HttpServletRequest request , HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		 String cityCode = request.getParameter("cityCode");
		 if(StringUtils.isNotBlank(cityCode)&&!StringUtils.equals("00", cityCode))
			 	paramMap.put("cityCode",cityCode);
		paramMap.put("behaviorParentId", request.getParameter("behaviorParentId"));
		paramMap.put("lpn", request.getParameter("lpn"));
		paramMap.put("reportedMemberName", request.getParameter("reportedMemberName"));
		paramMap.put("createName", request.getParameter("createName"));
		paramMap.put("status", request.getParameter("status"));
		
		
		Pager<MemberReportVo> pager = memberReportService.pagerList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_credit_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String behaviorParentId,String lpn,String reportedMemberName,String createName, String status,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CreditReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市","被追究会员姓名", "手机号码", "网点", "车位号码", "车牌号","分类",
				"子分类","反馈人","反馈人身份","反馈时间","审核状态" };
		
		String keys[] = {"cityName", "reportedMemberName", "reportedPhone",
				"parkName", "parkNo","lpn","behaviorType", "behaviorName", "createName",
				"isMemberComplain","createTime","status"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		 if(StringUtils.isNotBlank(cityCode)&&!StringUtils.equals("00", cityCode))
			 	paramMap.put("cityCode",cityCode);
		paramMap.put("behaviorParentId", behaviorParentId);
		paramMap.put("lpn", lpn);
		paramMap.put("reportedMemberName", reportedMemberName);
		paramMap.put("createName", createName);
		paramMap.put("status", status);
		paramMap.put("from", null);
		paramMap.put("to", null);
		
		Pager<MemberReportVo> pager = memberReportService.pagerList(paramMap);
		List<MemberReportVo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "会员信用数据报表");
		list.add(sheetNameMap);
		list.addAll(createData2(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
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
		
		return null;
		
	}
	private List<Map<String, Object>> createData2(
			List<MemberReportVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = {"cityName", "reportedMemberName", "reportedPhone",
				"parkName", "parkNo","lpn","behaviorType", "behaviorName", "createName",
				"isMemberComplain","createTime","status"};
		for (MemberReportVo member : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], member.getCityName());
			map.put(keys[1], member.getReportedMemberName());
			map.put(keys[2], member.getReportedPhone());
			map.put(keys[3], member.getParkName());
			map.put(keys[4], member.getParkNo());
			map.put(keys[5], member.getLpn());
			map.put(keys[6], member.getBehaviorType());
			map.put(keys[7], member.getBehaviorName());
			map.put(keys[8], member.getCreateName());
			if(member.getIsMemberComplain() != null ){
				switch (member.getIsMemberComplain()) {
				case 0:
					map.put(keys[9], "用户");
					break;
				case 1:
					map.put(keys[9], "员工");
					break;
				case 2:
					map.put(keys[9], "客服");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[9], member.getIsMemberComplain());
			}
			map.put(keys[10], member.getCreateTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getCreateTime()):""); 
			if(member.getStatus() != null){
				switch (member.getStatus()) {
				case 0:
					map.put(keys[11], "未审核");
					break;
				case 1:
					map.put(keys[11], "通过");
					break;
				case 2:
					map.put(keys[11], "不通过");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[11], member.getStatus());
			}
			list.add(map);
		}
		return list;
	}
	
	private String uploadFileImg(MultipartFile boxfile,int reportId) throws Exception{
		InputStream is = boxfile.getInputStream();  
		String filename = boxfile.getOriginalFilename(); 
		if(StringUtils.isBlank(filename)){return "";}
		String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
        //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
        OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String imgUrl = oss.putObject(newFileName, is, "reportEvidence/");
		if(StringUtils.isNotBlank(imgUrl)){
			EvidenceRelation relation = new EvidenceRelation();
			relation.setCreateTime(new Date());
			relation.setIsMemberComplain(3);
			relation.setPictureEvidenceUrl(imgUrl);
			relation.setReportId(reportId);
			evidenceRelationService.insert(relation);
		}
        return imgUrl;
	}
	
	@SystemServiceLog(description = "新增用户信用")
	@RequestMapping(value = "/addMemberCredit", method = { RequestMethod.GET,RequestMethod.POST })
	public void addMemberCredit(HttpServletRequest request,
			HttpServletResponse response, MultipartFile boxfile,
			MultipartFile boxfile1, MultipartFile boxfile2,
			MultipartFile boxfile3) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		String reportedMemberName = request.getParameter("reportedMemberName");
		String reportedPhone = request.getParameter("reportedPhone");
		String lpn = request.getParameter("lpn");
		String cityCode = request.getParameter("cityCode");
		String behaviorParentId = request.getParameter("behaviorParentId");
		String behaviorChildrenId = request.getParameter("behaviorChildrenId");
		String parkId = request.getParameter("parkId");
		String parkNo = request.getParameter("parkNo");
		String parkName= request.getParameter("parkName");
		String auditExplain = request.getParameter("auditExplain");
		String contriVal = request.getParameter("contriVal");
		MemberBehaviorType memberBehaviorType = memberBehaviorTypeService.selectMemberBehaviorTypeByKey(Integer.parseInt(behaviorParentId));
		MemberBehavior memberBehavior = memberBehaviorService.selectByPrimaryKey(Integer.parseInt(behaviorChildrenId));
		MemberReport report = new MemberReport();
		int records = memberReportService.getMemberRecords(reportedMemberName.trim(),reportedPhone.trim());
		if(records == 0){
			json.put("status", "fail");
			json.put("message", "会员信息错误，请确认并重新输入！");
			response.getWriter().print(json.toString());
			return;
		}
		if(StringUtils.contains(memberBehavior.getType(), "CHARGING_PILE")){//对充电桩的反馈行为
			Park park = parkService.selectParkByName(parkName.trim());
			if(park != null)parkId = park.getId()+"";
			//获取最后订单
			MemberLastOrderVo vo = memberReportService.getLastChargingOrderInfo(parkId,parkNo,reportedMemberName.trim(),reportedPhone.trim());
			if(vo == null ){
				json.put("status", "fail");
				json.put("message", "该会员尚未租用过该充电桩，请确认并重新输入！");
				response.getWriter().print(json.toString());
				return;
			}
			report.setParkId(parkId);
			report.setParkNo(parkNo);
			report.setLastOrderId(vo.getOrderId());
			report.setReportedMemberId(Integer.parseInt(vo.getMemberId()));
		}else {//对车点反馈行为
			//获取最后订单 验证信息是否正确
			MemberLastOrderVo vo = memberReportService.getLastCarOrderInfo(lpn.trim(),reportedMemberName.trim(),reportedPhone.trim());
			if(vo == null ){
				json.put("status", "fail");
				json.put("message", "该会员尚未租用过该车，请确认并重新输入！");
				response.getWriter().print(json.toString());
				return;
			}
			report.setLpn(lpn);
			report.setReportedMemberId(Integer.parseInt(vo.getMemberId()));
			report.setLastOrderId(vo.getOrderId());
			report.setLastCarType(vo.getCarType());
		}
		
		
		//保存数据
		report.setCityCode(cityCode);
		report.setBehaviorParentId(Integer.parseInt(behaviorParentId));
		report.setBehaviorChildrenId(Integer.parseInt(behaviorChildrenId));
		report.setCreateTime(new Date());
		report.setStatus(CHECK_STATUC_PASS);
		report.setAuditExplain(auditExplain);
		report.setAuditTime(new Date());
		report.setIsMemberComplain(2);
		report.setAuditId(getUser(request).getId());
		//判读是否是严重行为 是否加入黑名单 不是调接口加减信用分
		if(memberBehaviorType != null && StringUtils.equals(MemberBehaviorTypeService.BEHAVIOR_TYPE_BAD, memberBehaviorType.getBehaviorType())){
			int count = memberBlacklistService.getRecordsByMemberId(report.getReportedMemberId());
			if(count >0){
				json.accumulate("status", "fail");
				json.accumulate("message", "该用户已在黑名单中");
				response.getWriter().print(json.toString());
				return;
			}
			report = memberReportService.insertMemberReport(report);
			MemberBlacklist blackList = new MemberBlacklist();
			blackList.setMemberId(report.getReportedMemberId());
			blackList.setCreateId(getUser(request).getId());
			blackList.setCreateTime(new Date());
			blackList.setReason(memberBehavior.getName());
			blackList.setIsAuto(1);//0表示系统自动拉入黑名单，1表示因严重行为拉入黑名单
			int record = memberBlacklistService.insert(blackList,report.getId()==null?null:report.getId().toString(),memberBehavior.getType());
			//加入黑名单
			if(record > 0){
				//保存图片  待完成
				if(boxfile != null)uploadFileImg(boxfile,report.getId());
				if(boxfile1 != null)uploadFileImg(boxfile1,report.getId());
				if(boxfile2 != null)uploadFileImg(boxfile2,report.getId());
				if(boxfile3 != null)uploadFileImg(boxfile3,report.getId());
				MemberBlacklistLog blackLog = new MemberBlacklistLog();
				blackLog.setCreateId(getUser(request).getId());
				blackLog.setCreateTime(new Date());
				blackLog.setMemberId(report.getReportedMemberId());
				blackLog.setOperate(0);
				blackLog.setReason(memberBehavior.getName());
				memberBlacklistLogService.insert(blackLog);
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(21);
				//发送推送
				if(systemMsg != null){
					SystemMsgLog systemMsgLog = new SystemMsgLog();
					systemMsgLog.setMsgType("member");
					systemMsgLog.setCreateTime(new Date());
					systemMsgLog.setMemberId(report.getReportedMemberId());
					systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
					systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}",memberBehavior.getName()));					
					systemMsgLogService.insertSelective(systemMsgLog);
					PushCommonBean push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
					List<String> alias = PushClient.queryClientId(reportedPhone);
					if(!alias.isEmpty() && alias.size()> 0){
						for(String cid : alias){
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
							PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
						}
					}
				}
				//是发生短信提醒用户 
				SysParam sendUrl = sysParamService.selectByKey("send_sms_url");
				String url = "";
				if(sendUrl !=null)url=sendUrl.getValue();
				sendSMS(url, reportedPhone, "", "2680",memberBehavior.getName());
				json.accumulate("status", "success");
			}else{
				json.accumulate("status", "fail");
				json.accumulate("message", "添加黑名单失败,请稍后再试");
			}
		}else{
			//调用提供者接口加减信用分
			SysParam sysParam = sysParamService.selectByKey("http_url");
			String url = "";
			if(sysParam != null)url = sysParam.getValue();
			String result = "{}";
			if(StringUtils.equals(memberBehaviorType.getBehaviorName(), "车辆信息反馈")){
				//反馈的人减分
				if(!StringUtils.equals(memberBehavior.getType(), MemberBehaviorNameEnum.MEMBER_CONTRIBUTED_NEAT_LEVEL_ACCESS.getName()))
					result = insertMemberContributedDetailByBehavior(
						url,"{'memberId':'"+report.getReportedMemberId()+"','objId':'"+report.getId()+"','typeEnum':'"+memberBehavior.getType()+"_SUB"+"','multiplicand':'','createId':'"+getUser(request).getId()+"','contriVal':'"+contriVal+"','reason':'"+report.getRemark()+"'}",
						report.getReportedMemberId()+"", reportedPhone,"insertMemberContributedDetailByBehavior");
			}else{
				result = insertMemberContributedDetailByBehavior(
						url,"{'memberId':'"+report.getReportedMemberId()+"','objId':'"+report.getId()+"','typeEnum':'"+memberBehavior.getType()+"','multiplicand':'','createId':'"+getUser(request).getId()+"','contriVal':'"+contriVal+"','reason':'"+report.getRemark()+"'}",
						report.getReportedMemberId()+"", reportedPhone,"insertMemberContributedDetailByBehavior");
			}
			JSONObject jsonObject = JSONObject.fromObject(result) ;
			String code = jsonObject.getString("code") ;
			if(code.equals("00")){ // 调用成功 将数据保存的数据库中
				json.accumulate("status", "success");
				report = memberReportService.insertMemberReport(report);
				//保存图片  待完成
				//保存图片  待完成
				if(boxfile != null)uploadFileImg(boxfile,report.getId());
				if(boxfile1 != null)uploadFileImg(boxfile1,report.getId());
				if(boxfile2 != null)uploadFileImg(boxfile2,report.getId());
				if(boxfile3 != null)uploadFileImg(boxfile3,report.getId());
			}else{
				json.accumulate("status", "fail");
				json.accumulate("message", "接口调用异常,请稍后再试");
			}
		}
		//保存数据 调用接口
		response.getWriter().print(json.toString());
	}
	
	/**
	 * 修改用户调教的反馈信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SystemServiceLog(description="编辑用户反馈信息")
	@RequestMapping(value="editMemberReport", method={RequestMethod.GET, RequestMethod.POST})
	public void editMemberReport(HttpServletRequest request , HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		String behaviorParentId = request.getParameter("behaviorParentId");
		String behaviorChildrenId = request.getParameter("behaviorChildrenId");
		String reportedMemberName = request.getParameter("reportedMemberName");
		String reportedPhone = request.getParameter("reportedPhone");
		if(StringUtils.isBlank(id)){
			json.accumulate("status", "fail");
			json.accumulate("message", "系统繁忙,请稍后再试");
			response.getWriter().print(json.toString());
			return;
		}
		MemberReport model = memberReportService.selectById(Integer.parseInt(id));
		//set参数
		if(model != null){
			MemberBehavior memberBehavior = memberBehaviorService.selectByPrimaryKey(Integer.parseInt(behaviorChildrenId));
			if(StringUtils.contains(memberBehavior.getType(), "CHARGING_PILE")){
				//充电桩  获取最后订单
				MemberLastOrderVo vo = memberReportService.getLastChargingOrderInfo(model.getParkId(),model.getParkNo(),reportedMemberName.trim(),reportedPhone.trim());
				if(vo == null ){
					json.put("status", "fail");
					json.put("message", "该会员尚未租用过该充电桩，请确认并重新输入！");
					response.getWriter().print(json.toString());
					return;
				}
				model.setLastOrderId(vo.getOrderId());
				model.setReportedMemberId(Integer.parseInt(vo.getMemberId()));
			}else{
				//车  获取最后订单
				MemberLastOrderVo vo = memberReportService.getLastCarOrderInfo(model.getLpn(),reportedMemberName.trim(),reportedPhone.trim());
				if(vo == null ){
					json.put("status", "fail");
					json.put("message", "该会员尚未租用过该车辆，请确认并重新输入！");
					response.getWriter().print(json.toString());
					return;
				}
				model.setReportedMemberId(Integer.parseInt(vo.getMemberId()));
				model.setLastOrderId(vo.getOrderId());
				model.setLastCarType(vo.getCarType());
			}
			if(StringUtils.isNotBlank(behaviorParentId)){model.setBehaviorParentId(Integer.parseInt(behaviorParentId));}
			if(StringUtils.isNotBlank(behaviorChildrenId)){model.setBehaviorChildrenId(Integer.parseInt(behaviorChildrenId));}
			int record = memberReportService.updateMemberReport(model);
			if(record >0){
				json.accumulate("status", "success");
			}else{
				json.accumulate("status", "fail");
				json.accumulate("message", "更新失败,请稍后再试");
			}
		}
		response.getWriter().print(json.toString());
	}
	
	@SystemServiceLog(description="获取反馈凭证图片")
	@RequestMapping(value="/getReportEvidenceImg" , method={RequestMethod.GET, RequestMethod.POST})
	public void getReportEvidenceImg(int reportId , HttpServletRequest request , HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<EvidenceRelation> dataList = evidenceRelationService.getByReportId(reportId);
		response.getWriter().print(Data2Jsp.listToJson(dataList));
	}
	
	@SystemServiceLog(description="获取用户行为子分类type")
	@RequestMapping(value="/getTypeCodeById" , method={RequestMethod.GET, RequestMethod.POST})
	public void getTypeCodeById(int id, HttpServletRequest request , HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		MemberBehavior memberBehavior = memberBehaviorService.selectByPrimaryKey(id);
		String result = "";
		if(memberBehavior != null)result = memberBehavior.getType();
		response.getWriter().print(result);
	}
	
	@SystemServiceLog(description="获取被反馈用户")
	@RequestMapping(value="/reportedByName_list" , method={RequestMethod.GET, RequestMethod.POST})
	public void reported_list(String reportedMemberName, HttpServletRequest request , HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		List<Member> memberList = memberService.selectByName(reportedMemberName);
		
		if(memberList != null && memberList.size() > 0){
			int length = 10;
			if(memberList.size() < length) length =memberList.size();
			for(int index=0; index < length ;index++){
					Member member = memberList.get(index);
						tree.append("{");
						tree.append("\"id\":\"" + member.getId() + "\",");
						tree.append("\"text\":\"" + member.getName() + "\"");
						if (index < length-1) {
							tree.append("},");
						} else {
							tree.append("}");
						}
				}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
	@SystemServiceLog(description="审核反馈信息")
	@RequestMapping(value="/auditMemberCreditRefuse" , method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
	public Map<String,Object> auditMemberCreditRefuse( HttpServletRequest request , HttpServletResponse response) throws Exception {
//		response.setContentType("text/html;charset=utf-8");
//		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		String behaviorParentId = request.getParameter("behaviorParentId");
		String behaviorChildrenId = request.getParameter("behaviorChildrenId");
		String memberId = request.getParameter("memberId");
		String parkId = request.getParameter("parkId");
		String parkNo = request.getParameter("parkNo");
		String auditExplain = request.getParameter("auditExplain");
		String orderId = request.getParameter("orderId");
		String lastCarType = request.getParameter("lastCarType");
		if(StringUtils.isBlank(id)){
		    return fail("系统繁忙,请稍后再试");
			/*json.accumulate("status", "fail");
			json.accumulate("message", "系统繁忙,请稍后再试");
			response.getWriter().print(json.toString());*/

		}
		MemberReport model = memberReportService.selectById(Integer.parseInt(id));
		if(StringUtils.isNotBlank(behaviorChildrenId))model.setBehaviorChildrenId(Integer.parseInt(behaviorChildrenId));
		if(StringUtils.isNotBlank(behaviorParentId))model.setBehaviorParentId(Integer.parseInt(behaviorParentId));
		if(StringUtils.isNotBlank(lastCarType))model.setLastCarType(lastCarType);
		if(StringUtils.isNotBlank(orderId))model.setLastOrderId(orderId);
		if(StringUtils.isNotBlank(parkId))model.setParkId(parkId);
		if(StringUtils.isNotBlank(parkNo))model.setParkNo(parkNo);
		if(StringUtils.isNotBlank(auditExplain))model.setAuditExplain(auditExplain);
		if(StringUtils.isNotBlank(memberId))model.setReportedMemberId(Integer.parseInt(memberId));
		model.setAuditId(getUser(request).getId());
		model.setStatus(CHECK_STATUC_REFUSE);
		model.setAuditTime(new Date());
		memberReportService.updateMemberReport(model);
		//发送反馈信息给反馈人
		if(model.getIsMemberComplain() == 0) {
		 SystemMsgLog systemMsgLog = new SystemMsgLog();
	     systemMsgLog.setMsgType("member");
	     systemMsgLog.setCreateTime(new Date());
	     systemMsgLog.setMemberId(model.getCreateId());
	     systemMsgLog.setMsgTitle("");
	     systemMsgLog.setMsgContent("尊敬的会员，您的反馈信息，已处理完毕，感谢您的反馈！");
	     systemMsgLogService.insertSelective(systemMsgLog);
	     PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log", "1", "系统通知消息", systemMsgLog);
	     Member creator =  memberService.selectByPrimaryKey(model.getCreateId());
	     List<String> cidMemberList = PushClient.queryClientId(creator.getPhone());
	        for (String memberCid : cidMemberList) {
	            JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
                PushClient.push(memberCid, net.sf.json.JSONObject.fromObject(systemPush, jsonConfig));
	        }
		}
		return success();
//		response.getWriter().print(json.toString());
	}
	
	/***
	 * 反馈审核通过
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SystemServiceLog(description="审核反馈信息")
	@RequestMapping(value="/auditMemberCreditPass" , method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
	public Map<String,Object> auditMemberCredit( HttpServletRequest request , HttpServletResponse response) throws Exception {
//		response.setContentType("text/html;charset=utf-8");
//		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		String behaviorParentId = request.getParameter("behaviorParentId");
		String behaviorChildrenId = request.getParameter("behaviorChildrenId");
		String memberId = request.getParameter("memberId");
		String parkId = request.getParameter("parkId");
		String parkNo = request.getParameter("parkNo");
		String auditExplain = request.getParameter("auditExplain");
		String orderId = request.getParameter("orderId");
		String lastCarType = request.getParameter("lastCarType");
		String reportedPhone = request.getParameter("reportedPhone");
		String lpn = request.getParameter("lpn");
		if(StringUtils.isBlank(id)){
		    return fail( "系统繁忙,请稍后再试");
			/*json.accumulate("status", "fail");
			json.accumulate("message", "系统繁忙,请稍后再试");
			response.getWriter().print(json.toString());
			return;*/
		}
		MemberReport model = memberReportService.selectById(Integer.parseInt(id));
		if(StringUtils.isNotBlank(behaviorChildrenId))model.setBehaviorChildrenId(Integer.parseInt(behaviorChildrenId));
		if(StringUtils.isNotBlank(behaviorParentId))model.setBehaviorParentId(Integer.parseInt(behaviorParentId));
		if(StringUtils.isNotBlank(lastCarType))model.setLastCarType(lastCarType);
		if(StringUtils.isNotBlank(orderId))model.setLastOrderId(orderId);
		if(StringUtils.isNotBlank(parkId))model.setParkId(parkId);
		if(StringUtils.isNotBlank(parkNo))model.setParkNo(parkNo);
		if(StringUtils.isNotBlank(auditExplain))model.setAuditExplain(auditExplain);
		if(StringUtils.isNotBlank(memberId))model.setReportedMemberId(Integer.parseInt(memberId));
		if(StringUtils.isNotBlank(lpn))model.setLpn(lpn);
		model.setAuditId(getUser(request).getId());
		model.setStatus(CHECK_STATUC_PASS);
		model.setAuditTime(new Date());
		MemberBehavior memberBehavior = memberBehaviorService.selectByPrimaryKey(model.getBehaviorChildrenId());
		MemberBehaviorType memberBehaviorType = memberBehaviorTypeService.selectMemberBehaviorTypeByKey(Integer.parseInt(behaviorParentId));
		if(memberBehaviorType != null && StringUtils.equals(MemberBehaviorTypeService.BEHAVIOR_TYPE_BAD, memberBehaviorType.getBehaviorType())){
			int count = memberBlacklistService.getRecordsByMemberId(model.getReportedMemberId());
			if(count >0){
                return fail("该用户已在黑名单中");
                /*json.accumulate("status", "fail");
				json.accumulate("message", "该用户已在黑名单中");
				response.getWriter().print(json.toString());
				return;*/
			}
			MemberBlacklist blackList = new MemberBlacklist();
			blackList.setMemberId(model.getReportedMemberId());
			blackList.setCreateId(getUser(request).getId());
			blackList.setCreateTime(new Date());
			blackList.setReason(memberBehavior.getName());
			blackList.setIsAuto(1);//0表示系统自动拉入黑名单，1表示因严重行为拉入黑名单
			int record = memberBlacklistService.insert(blackList,model.getId().toString(),memberBehavior.getType());
			//加入黑名单
			if(record > 0){
				memberReportService.updateMemberReport(model);
				MemberBlacklistLog blackLog = new MemberBlacklistLog();
				blackLog.setCreateId(getUser(request).getId());
				blackLog.setCreateTime(new Date());
				blackLog.setMemberId(model.getReportedMemberId());
				blackLog.setOperate(0);
				blackLog.setReason(memberBehavior.getName());
				memberBlacklistLogService.insert(blackLog);
				//发送推送
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(21);
				//发送推送
				if(systemMsg != null){
					SystemMsgLog systemMsgLog = new SystemMsgLog();
					systemMsgLog.setMsgType("member");
					systemMsgLog.setCreateTime(new Date());
					systemMsgLog.setMemberId(model.getReportedMemberId());
					systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
					systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}",memberBehavior.getName()));					
					systemMsgLogService.insertSelective(systemMsgLog);
					PushCommonBean push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
					List<String> alias = PushClient.queryClientId(reportedPhone);
					if(!alias.isEmpty() && alias.size()> 0){
						for(String cid : alias){
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
							PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
						}
					}
				}
				//是发生短信提醒用户 
				SysParam sendUrl = sysParamService.selectByKey("send_sms_url");
				String url = "";
				if(sendUrl !=null)url=sendUrl.getValue();
				sendSMS(url, reportedPhone, "", "2680",memberBehavior.getName());
//				json.accumulate("status", "success");
                return success();
			}else{
			    return fail("添加黑名单失败,请稍后再试");
			/*	json.accumulate("status", "fail");
				json.accumulate("message", "添加黑名单失败,请稍后再试");*/
			}
		}else{
			//调用提供者接口加减信用分
			SysParam sysParam = sysParamService.selectByKey("http_url");
			String url = "";
			//String url = "http://192.168.1.85:8888/services/i/e/";
			if(sysParam != null)url = sysParam.getValue();
			String result = "";
			if(StringUtils.equals(memberBehaviorType.getBehaviorName(), "车辆信息反馈")){
					if(!StringUtils.equals(memberBehavior.getType(), MemberBehaviorNameEnum.MEMBER_CONTRIBUTED_NEAT_LEVEL_ACCESS.getName())){
						if(model.getIsMemberComplain() == 0){
							Member member = memberService.selectByPrimaryKey(model.getCreateId());
							result = insertMemberContributedDetailByBehavior(
									url,"{'memberId':'"+model.getCreateId()+"','objId':'"+model.getId()+"','typeEnum':'"+memberBehavior.getType()+"','multiplicand':'','createId':'"+getUser(request).getId()+"'}",
									model.getCreateId()+"", member.getPhone(),"insertMemberContributedDetailByBehavior");
						}
						
						result = insertMemberContributedDetailByBehavior(
								url,"{'memberId':'"+model.getReportedMemberId()+"','objId':'"+model.getId()+"','typeEnum':'"+memberBehavior.getType()+"_SUB"+"','multiplicand':'','createId':'"+getUser(request).getId()+"'}",
								model.getReportedMemberId()+"", reportedPhone,"insertMemberContributedDetailByBehavior");
					}
				}else{
				result = insertMemberContributedDetailByBehavior(
						url,"{'memberId':'"+memberId+"','objId':'"+model.getId()+"','typeEnum':'"+memberBehavior.getType()+"','multiplicand':'','createId':'"+getUser(request).getId()+"'}",
						memberId+"", reportedPhone,"insertMemberContributedDetailByBehavior");
			}
			JSONObject jsonObject = JSONObject.fromObject(result) ;
			String code = jsonObject.getString("code") ;
			if(code.equals("00")){ // 调用成功 将数据保存的数据库中
//				json.accumulate("status", "success");
				memberReportService.updateMemberReport(model);
				//发送反馈信息给反馈人
				if(model.getIsMemberComplain() == 0) {
				 SystemMsgLog systemMsgLog = new SystemMsgLog();
			     systemMsgLog.setMsgType("member");
			     systemMsgLog.setCreateTime(new Date());
			     systemMsgLog.setMemberId(model.getCreateId());
			     systemMsgLog.setMsgTitle("");
			     systemMsgLog.setMsgContent("尊敬的会员，您的反馈信息，已处理完毕，感谢您的反馈！");
			     systemMsgLogService.insertSelective(systemMsgLog);
			     PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log", "1", "系统通知消息", systemMsgLog);
			     Member creator =  memberService.selectByPrimaryKey(model.getCreateId());
			     List<String> cidMemberList = PushClient.queryClientId(creator.getPhone());
			        for (String memberCid : cidMemberList) {
			            JsonConfig jsonConfig = new JsonConfig();
                        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
                        PushClient.push(memberCid, net.sf.json.JSONObject.fromObject(systemPush, jsonConfig));
			        }
				}
				return success();
			}else{
                return fail("接口调用异常,请稍后再试");
                /*json.accumulate("status", "fail");
				json.accumulate("message", "接口调用异常,请稍后再试");*/
			}
		}
//		response.getWriter().print(json.toString());
	}
	
	@SystemServiceLog(description = "审核关联订单")
	@RequestMapping(value="/getOrderInfo"  , method={RequestMethod.GET, RequestMethod.POST})
	public void getOrderInfo(int page, int rows, HttpServletRequest request , HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String id = request.getParameter("id");
		String typeCode = request.getParameter("typeCode");
		String lpn = request.getParameter("lpn");
		String parkName = request.getParameter("parkName");
		String parkNo = request.getParameter("parkNo");
		MemberReport model = memberReportService.selectById(Integer.parseInt(id));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("createTime", model.getCreateTime());
		if(StringUtils.equals("0", model.getIsMemberComplain().toString())){
			paramMap.put("createId", model.getCreateId());
		}
		if(StringUtils.contains(typeCode, "CHARGING_PILE")){
			paramMap.put("parkNo", parkNo);
			paramMap.put("parkName", parkName);
		}else{
			paramMap.put("lpn", lpn);
		}
		paramMap.put("from", from);
		paramMap.put("to", to);
		 
		Pager<MemberReportRelationVo> pager = memberReportService.getRelationVo(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description = "反馈审核详情")
	@RequestMapping(value="/getRepordsDetail"  , method={RequestMethod.GET, RequestMethod.POST})
	public void getRepordDetail( HttpServletRequest request , HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		String id = request.getParameter("id");
		MemberReportVo data = memberReportService.selectDetailById(Integer.parseInt(id));
		List<EvidenceRelation> dataList = evidenceRelationService.getByReportId(Integer.parseInt(id));
		json.accumulate("data", data);
		json.accumulate("dataList", dataList);
		json.accumulate("status", "success");
		response.getWriter().print(json.toString());
	}
	
}
