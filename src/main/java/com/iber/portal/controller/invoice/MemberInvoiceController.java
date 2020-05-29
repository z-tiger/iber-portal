package com.iber.portal.controller.invoice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.invoice.MemberInvoice;
import com.iber.portal.model.invoice.MemberMailInfo;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.base.SystemMsgLogService;
import com.iber.portal.service.charging.ChargingOrderInfoService;
import com.iber.portal.service.invoice.MemberInvoiceService;
import com.iber.portal.service.longRent.LongRentOrderService;
import com.iber.portal.service.memberMail.MemberMailService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeSharePayService;
import com.iber.portal.vo.invoice.InvoiceOrderInfoVo;
import com.iber.portal.vo.invoice.MemberInvoiceVO;
import com.iber.portal.vo.memberMail.MemberMailInfoVO;

/**
 * 发票管理controler
 * 
 * @date create 2017/11/10 by zixb
 */
@Controller
public class MemberInvoiceController extends MainController{

	private Logger log = LoggerFactory.getLogger(MemberInvoiceController.class);
	@Autowired
	private MemberInvoiceService memberInvoiceService;
	
	@Autowired
	private MemberMailService memberMailService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TimeSharePayService timeSharePayService;
	
	@Autowired
	private ChargingOrderInfoService chargingOrderInfoService;
	
	@Autowired
	private SystemMsgLogService systemMsgLogService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LongRentOrderService longRentOrderService;
	
	@SystemServiceLog(description = "发票管理页面")
	@RequestMapping(value = "invoiceManage_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String invoiceManagePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "invoice/invoiceManage";
	}
	
	@SystemServiceLog(description = "邮寄地址管理页面")
	@RequestMapping(value = "memberMail_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String memberMailPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "memberMail/memberMail";
	}
	
	/**
	 *  发票管理列表
	 *  
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SystemServiceLog(description = "发票管理列表")
	@RequestMapping(value = "invoice_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public void invoiceList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		String invocieHead = request.getParameter("invocieHead");
		String invoiceType = request.getParameter("invoiceType");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String beginTime = request.getParameter("bt");
		String endTime = request.getParameter("et");
		//获取其他查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		paramMap.put("cityCode", StringUtils.equals("00", cityCode)?"":cityCode);
		paramMap.put("invoiceType", invoiceType);
		paramMap.put("name", name);
		paramMap.put("invocieHead", invocieHead);
		paramMap.put("invoiceStatus", invoiceStatus);
		paramMap.put("beginTime", StringUtils.isBlank(beginTime)?"": beginTime+" 00:00:00");
		paramMap.put("endTime", StringUtils.isBlank(endTime)?"":endTime+" 23:59:59");
		Pager<MemberInvoiceVO> pager = memberInvoiceService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	@SystemServiceLog(description = "发票管理列表excel导出")
	@RequestMapping(value = "invoice_list_excel" , method = { RequestMethod.GET, RequestMethod.POST })
	public void invoiceListExcel( HttpServletRequest request, HttpServletResponse response) throws Exception{
		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		String invocieHead = request.getParameter("invocieHead");
		String invoiceType = request.getParameter("invoiceType");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String beginTime = request.getParameter("bt");
		String endTime = request.getParameter("et");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", StringUtils.equals("00", cityCode)?"":cityCode);
		paramMap.put("invoiceType", invoiceType);
		paramMap.put("name", name);
		paramMap.put("invocieHead", invocieHead);
		paramMap.put("invoiceStatus", invoiceStatus);
		paramMap.put("beginTime", StringUtils.isBlank(beginTime)?"": beginTime+" 00:00:00");
		paramMap.put("endTime", StringUtils.isBlank(endTime)?"":endTime+" 23:59:59");
		List<MemberInvoiceVO> invoiceList = memberInvoiceService.queryList(paramMap);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String fileName = "会员开票明细";
		List<Map<String, Object>> list = createInvoiceReportExcelRecord(invoiceList);
		String columnNames[] = { "序号", "会员名称", "申请开票日期", "开票种类（电子发票、纸质普票、专票）", "开票名称（发票台头）","纳税人识别号", "地址、电话","开户行及账号","开票内容","","开票金额","","发票号" };// 列名
		String secondColumnNames[] = { "汽车租赁费", "充电服务费","汽车租赁金额","充电金额" };
		String keys[] = { "no", "memberName", "createTime", "invoiceType", "invoiceHead", "taxpayerCode", "invoiceAddress","bankDetail","orderPayMoney","chargingPayMoney","orderMoney","chargingMoney","invoiceNo" };// map中的key
		try {
			ExcelUtil.createInvoiceWorkBook(list,keys,fileName,columnNames,secondColumnNames).write(os);
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
	
	private List<Map<String, Object>> createInvoiceReportExcelRecord(
			List<MemberInvoiceVO> invoiceList) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		MemberInvoiceVO report = null;
		DecimalFormat decimalFormat = new java.text.DecimalFormat("0.00");
		//String keys[] = { "no", "memberName", "createTime", "invoiceType", "invoiceHead", "taxpayerCode", "invoiceAddress","bankDetail","orderMoney","chargingMoney","payMoney","invoiceNo" };// map中的key
		for (int j = 0; j < invoiceList.size(); j++) {
			report = invoiceList.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("no",j+1);
			mapValue.put("memberName", report.getMemberName());
			mapValue.put("createTime", report.getCreateTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(report.getCreateTime()):"");
			if(report.getInvoiceType() != null){
				switch (report.getInvoiceType() ) {
				case 1:
					mapValue.put("invoiceType", "普通电子发票");
					break;
				case 2:
					mapValue.put("invoiceType", "普通纸质发票");
					break;
				case 3:
					mapValue.put("invoiceType", "专用纸质发票");
					break;
				default:
					break;
				}
			}
			
			mapValue.put("invoiceHead", report.getInvocieHead());
			mapValue.put("taxpayerCode", report.getTaxpayerCode());
			mapValue.put("invoiceAddress", report.getInvoiceAddress());
			mapValue.put("bankDetail", report.getBankDetail());
			mapValue.put("orderMoney", report.getOrderMoney() != null?decimalFormat.format(report.getOrderMoney()):0.00);
			mapValue.put("chargingMoney", report.getChargingMoney() != null?decimalFormat.format(report.getChargingMoney()):0.00);
			
			if(report.getServerType() != null){
				switch (report.getServerType()) {
				case 1:
				case 4:
				case 5:
					mapValue.put("orderPayMoney", "√");
					break;
				case 2:
					mapValue.put("chargingPayMoney", "√");
					break;
				case 3:
				case 6:
				case 7:
					mapValue.put("orderPayMoney", "√");
					mapValue.put("chargingPayMoney", "√");
				default:
					break;
				}
			}else{
				mapValue.put("orderPayMoney", "");
				mapValue.put("chargingPayMoney", "");
			}
			mapValue.put("invoiceNo", report.getInvoiceNo());
			listmap.add(mapValue);
		}
		return listmap;
	}

	@SystemServiceLog(description = "开票订单详情列表")
	@RequestMapping(value = "getOrderInfoList" , method = { RequestMethod.GET, RequestMethod.POST })
	public void getOrderInfoList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		String orderId = request.getParameter("orderId");
		String orderType = request.getParameter("orderType");
		String id = request.getParameter("id");
		MemberInvoice invoice = memberInvoiceService.selectById(Integer.parseInt(id));
		JSONObject orderJson = JSONObject.fromObject(invoice.getOrderIds());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(orderJson.containsKey("tS")){
			List<Integer> tsList = new ArrayList<Integer>();
			String tSIds =orderJson.getString("tS");
			String[] tsId = tSIds.split(",");
			if(tsId.length > 0){
				for (String payid : tsId) {
					if(StringUtils.isNotBlank(payid))
						tsList.add(Integer.parseInt(payid));
				}	
			}
			if(!tsList.isEmpty())
				paramMap.put("tsList", tsList);
		}
		if(orderJson.containsKey("charging")){
			List<Integer> chargingList = new ArrayList<Integer>();
			String chargingIds =orderJson.getString("charging");
			String[] chargingId = chargingIds.split(",");
			if(chargingId.length > 0){
				for (String chid : chargingId) {
					if(StringUtils.isNotBlank(chid))
						chargingList.add(Integer.parseInt(chid));
				}	
			}
			if(!chargingList.isEmpty())
				paramMap.put("chargingList", chargingList);
		}
		if(orderJson.containsKey("lr")){
			List<Integer> lrList = new ArrayList<Integer>();
			String lrIds =orderJson.getString("lr");
			String[] lrId = lrIds.split(",");
			if(lrId.length > 0){
				for (String lid : lrId) {
					if(StringUtils.isNotBlank(lid))
						lrList.add(Integer.parseInt(lid));
				}	
			}
			if(!lrList.isEmpty())
				paramMap.put("lrList", lrList);
		}
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		paramMap.put("orderId", orderId);
		paramMap.put("orderType", orderType);
		Pager<InvoiceOrderInfoVo> pager = memberInvoiceService.getOrderInfoList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	/**
	 * 邮寄地址管理列表
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SystemServiceLog(description = "邮寄地址管理列表")
	@RequestMapping(value = "member_mail_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public void memberMailList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		//String orderType = request.getParameter("orderType");
		String phone = request.getParameter("phone");
		//获取其他查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		paramMap.put("cityCode", StringUtils.equals("00", cityCode)?"":cityCode);
		//paramMap.put("orderType", orderType);
		paramMap.put("name", name);
		paramMap.put("phone", phone);
		Pager<MemberMailInfoVO> pager = memberMailService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_mail_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String cityCode,String name,String phone,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "MailReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市", "会员名",  "会员电话", "收件人", "收件人电话", "所在地区", "详细地址","创建时间" };
		
		String keys[] = { "cityName", "memberName","memberPhone", 
				"receiver", "phone", "areaName", "address",
				"createTime" };
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offset", null);
		paramMap.put("rows", null);
		paramMap.put("cityCode", StringUtils.equals("00", cityCode)?"":cityCode);
		paramMap.put("name", name);
		paramMap.put("phone", phone);
		Pager<MemberMailInfoVO> pager = memberMailService.queryPageList(paramMap);
		List<MemberMailInfoVO> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "邮件地址数据报表");
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
			List<MemberMailInfoVO> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "memberName","memberPhone", "receiver", "phone", "areaName", "address","createTime" };
		for (MemberMailInfoVO mail : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], mail.getCityName());
			map.put(keys[1], mail.getMemberName());
			map.put(keys[2], mail.getMemberPhone());
			map.put(keys[3], mail.getReceiver());
			map.put(keys[4], mail.getPhone());
			map.put(keys[5], mail.getAreaName());
			map.put(keys[6], mail.getAddress());
			map.put(keys[7], mail.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mail.getCreateTime()):""); 
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 邮寄地址管理编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "邮寄地址管理修改")
	@ResponseBody
	@RequestMapping(value = "memberMail_modify" , method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object> memberMail_modify(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String receiver = request.getParameter("receiver");
		String receiverPhone = request.getParameter("receiverPhone");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String area = request.getParameter("area");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		if(StringUtils.isBlank(id)){
			map.put("status", "fail");
			map.put("msg", "获取id失败，请刷新后再试！");
			return map;
		}
		MemberMailInfo memberMailInfo = memberMailService.selectMemberMailById(Integer.parseInt(id));
		if(memberMailInfo == null){
			map.put("status", "fail");
			map.put("msg", "请确认数据是否被删除！");
			return map;
		}
		if(StringUtils.isNotBlank(receiver)){
			memberMailInfo.setReceiver(receiver);
		}
		if(StringUtils.isNotBlank(receiverPhone)){
			memberMailInfo.setPhone(receiverPhone);
		}
		if(StringUtils.isNotBlank(address)){
			memberMailInfo.setAddress(address);
		}
		if(StringUtils.isNotBlank(email)){
			memberMailInfo.setEmail(email);
		}
		
		if(StringUtils.isNotBlank(area)){
			memberMailInfo.setArea(area);
		}
		
		if(StringUtils.isNotBlank(city)){
			memberMailInfo.setCity(city);
		}
		
		if(StringUtils.isNotBlank(province)){
			memberMailInfo.setProvince(province);
		}
		
		int record = memberMailService.updateByPrimaryKeySelective(memberMailInfo);
		if(record == 0){
			map.put("status", "fail");
			map.put("msg", "保存失败！");
			return map;
		}
		map.put("status", "success");
		map.put("msg", "保存成功！");
		return map;
	}
	
	
	/**
	 * 财务接受发票申请  更新发票申请状态
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "更新发票申请状态")
	@ResponseBody
	@RequestMapping(value="updateMemberInvoice" , method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> updateMemberInvoice(Integer id)throws Exception{
		 Map<String, Object> map = new HashMap<String, Object>();
		 MemberInvoice invoice = memberInvoiceService.selectById(id);
		 if(invoice == null){
			 map.put("status", "fail");
			 map.put("msg", "请确认数据是否被删除！");
			 return map;
		 }
		 if(!StringUtils.equals(invoice.getStatus().toString(), "1")){
			 map.put("status", "fail");
			 map.put("msg", "发票申请状态不为待开票，请刷新后再试！");
			 return map;
		 }
		 int record = memberInvoiceService.updateStatusById(id,2);
		 if(record == 0){
			 map.put("status", "fail");
			 map.put("msg", "网络繁忙，请稍后再试！");
			 return map;
		 }
		 
		 String orderIds = invoice.getOrderIds();
		 JSONObject jsonObject = JSONObject.fromObject(orderIds) ;
		 updateOrderInvoiceStatus(jsonObject, 2);
		 map.put("status", "success");
		 map.put("msg", "操作成功！");
		 return map;
	}
	
	/**
	 * 财务发送发票完成 更新纸质发票信息
	 * 
	 * @param id
	 * @param invoiceNo
	 * @param fastMailNo
	 * @param fastMailCompany
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="编辑纸质发票信息")
	@ResponseBody
	@RequestMapping(value = "sendPaperInvoice" , method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> sendPaperInvoice(Integer id, String invoiceNo, String fastMailNo, String fastMailCompany,
			HttpServletRequest request) throws Exception {
		 Map<String, Object>  map = new HashMap<String, Object>();
		 MemberInvoice invoice = memberInvoiceService.selectById(id);
		 if(invoice == null){
			 map.put("status", "fail");
			 map.put("msg", "请确认数据是否被删除！");
			 return map;
		 }
		 /**编辑invoiceNo json 对象*/
		 if(StringUtils.isBlank(invoiceNo)){
			 map.put("status", "fail");
			 map.put("msg", "发票号不能全为空！");
			return map;
		 }
		 invoice.setInvoiceNo(invoiceNo);
		 invoice.setFastMailCompany(fastMailCompany);
		 invoice.setFastMailNo(fastMailNo);
		 invoice.setStatus(3);
		 invoice.setOperator(getUser(request).getName());
		 invoice.setUpdateTime(new Date());
		 int record = memberInvoiceService.updateByPrimaryKeySelective(invoice);
		 if(record == 0){
			 map.put("status", "fail");
			 map.put("msg", "网络繁忙，请稍后再试！");
			 return map;
		 }
		 
		//更新对应订单中的状态
		 String orderIds = invoice.getOrderIds();
		 JSONObject jsonObject = JSONObject.fromObject(orderIds) ;
		 updateOrderInvoiceStatus(jsonObject, 3);
		 //推送到客户端
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("invoice_msg");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId( invoice.getMemberId());
		systemMsgLog.setMsgTitle("已开票");
		systemMsgLog.setMsgContent("尊敬的会员：您申请的发票已开票，可在发票->开票历史中查看详细信息。如有疑问，请联系客服4000769755。");
		systemMsgLogService.insertSelective(systemMsgLog);
		Member  member = memberService.selectByPrimaryKey(invoice.getMemberId());
		pushBindSuccMsg(systemMsgLog,member.getPhone());
		 map.put("status", "success");
		 map.put("msg", "操作成功！");
		 return map;
	}
	
	/**
	 * 财务发送发票完成 更新电子发票信息
	 * 
	 * @param boxfile1
	 * @param invoiceNo
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="编辑电子发票信息")
	@ResponseBody
	@RequestMapping(value = "sendEleInvoice" , method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> sendEleInvoice(MultipartFile boxfile, String invoiceNo,Integer id,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		 MemberInvoice invoice = memberInvoiceService.selectById(id);
		 if(invoice == null){
			 map.put("status", "fail");
			 map.put("msg", "请确认数据是否被删除！");
			 return map;
		 }
		 
		 /**编辑invoiceNo json 对象*/
		if(StringUtils.isBlank(invoiceNo)){
			 map.put("status", "fail");
			 map.put("msg", "发票号不能全为空！");
			return map;
		}
		String filename = boxfile.getOriginalFilename(); 
		long tsSize = boxfile.getSize();
		if(StringUtils.isBlank(filename)){
			 map.put("status", "fail");
			 map.put("msg", "请先选择要上传电子发票！");
			return map;
		}
		
		/**上传电子发票*/
		String elecInvoiceUrl = uploadFile(filename, boxfile.getInputStream());
		/**更新开票申请*/
		invoice.setElecInvoiceUrl(elecInvoiceUrl);
		invoice.setInvoiceNo(invoiceNo);
		invoice.setStatus(3);
		invoice.setOperator(getUser(request).getName());
		invoice.setUpdateTime(new Date());
		invoice.setElecInvoiceSize(tsSize+"");
		int record = memberInvoiceService.updateByPrimaryKeySelective(invoice);
		if(record == 0){//更新失败 提醒用户
			 map.put("status", "fail");
			 map.put("msg", "网络繁忙，请稍后再试！");
			 return map;
		}
		 
		 /**更新对应订单中的状态*/
		String orderIds = invoice.getOrderIds();
		JSONObject jsonObject = JSONObject.fromObject(orderIds) ;
		updateOrderInvoiceStatus(jsonObject, 3);
		 //推送到客户端
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("invoice_msg");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId( invoice.getMemberId());
		systemMsgLog.setMsgTitle("已开票");
		systemMsgLog.setMsgContent("尊敬的会员：您申请的发票已开票，可在发票->开票历史中查看详细信息。如有疑问，请联系客服4000769755。");
		systemMsgLogService.insertSelective(systemMsgLog);
		Member  member = memberService.selectByPrimaryKey(invoice.getMemberId());
		pushBindSuccMsg(systemMsgLog,member.getPhone());
		map.put("status", "success");
		map.put("msg", "操作成功！");
		return map;
	}

	/**
	 * 更新订单发票状态
	 * 
	 * @param jsonObject
	 * @param invoiceStatus
	 */
	private void updateOrderInvoiceStatus(JSONObject jsonObject, int invoiceStatus) {
		if(jsonObject.containsKey("tS")){
			 String tsOrderIds = jsonObject.getString("tS");
			 String[] ids = tsOrderIds.split(",");
			 List<String> list = new ArrayList<String>();
			 for (String oid : ids) {
				 list.add(oid);
			 }
			 if(!list.isEmpty())
				 timeSharePayService.bitchUpdateInvoiceStatus(list, invoiceStatus);
		}
		
		if(jsonObject.containsKey("charging")){
			 String chOrderIds = jsonObject.getString("charging");
			 String[] ids = chOrderIds.split(",");
			 List<String> list = new ArrayList<String>();
			 for (String oid : ids) {
				 list.add(oid);
			}
			 if(!list.isEmpty())
				 chargingOrderInfoService.bitchUpdateInvoiceStatus(list, invoiceStatus);
		}
		
		if(jsonObject.containsKey("lr")){
			String lrIds = jsonObject.getString("lr");
			String[] ids = lrIds.split(",");
			List<String> list = new ArrayList<String>();
			for (String lid : ids) {
				list.add(lid);
			}
			if(!list.isEmpty())
				longRentOrderService.bitchUpdateInvoiceStatus(list, invoiceStatus);
		}
		
	}
	/**
	 * 推送消息给顾客
	 * 
	 * @param systemMsgLog
	 * @param memberPhone
	 */
	private void pushBindSuccMsg(SystemMsgLog systemMsgLog,String memberPhone) {
		PushCommonBean push = new PushCommonBean("server_push_send_invoice",
				"1", systemMsgLog.getMsgContent(),"") ;
		List<String> cidList = PushClient.queryClientId(memberPhone);
		for (String memberCid : cidList) {
			PushClient.push(memberCid,push);
		}
	}
	/**
	 * 上传电子发票
	 * 
	 * @param filename
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String filename, InputStream is) throws Exception {
		String newFileName = UUID.randomUUID().toString() + "."	+ filename.substring(filename.lastIndexOf(".") + 1);
		// 文件上传到CDN
		String endpoint = sysParamService.selectByKey("endpoint").getValue();
		String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
		String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
		String bucketName = sysParamService.selectByKey("bucketName").getValue();
		String dns = sysParamService.selectByKey("dns").getValue();
		OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String elecInvoiceUrl = oss.putObject(newFileName, is,"invoice/");
		return elecInvoiceUrl;
	}
	
	/**
	 * 查询电子发票信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="查询电子发票信息")
	@ResponseBody
	@RequestMapping(value = "selectInvoice" , method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> selectInvoice(Integer id)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		MemberInvoiceVO invoice = memberInvoiceService.selectVOById(id);
		map.put("data", invoice);
		map.put("status", "success");
		map.put("msg", "操作成功！");
		return map;
		
	}
	
	/**
	 * 编辑发票申请信息
	 * 
	 * @param invoice
	 * @param request
	 * @param mailId
	 * @return
	 */
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="修改发票申请")
	@ResponseBody
	@RequestMapping(value = "updateInvoice" , method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> updateInvoice(MultipartFile boxfile, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		MemberInvoice invoice = memberInvoiceService.selectById(Integer.parseInt(id));
		if(invoice == null){
			 map.put("status", "fail");
			 map.put("msg", "请确认数据是否被删除！");
			 return map;
		}
		String invoiceNo = request.getParameter("invoiceNo");
		invoice.setInvoiceNo(invoiceNo);
		
//		String money = request.getParameter("money");
//		if(StringUtils.isNotBlank(money)){
//			Double dmoney = Double.parseDouble(StringUtils.replaceChars(money, ",", ""));
//			invoice.setMoney(Double.valueOf(dmoney*100).intValue());
//		}
		
		String personType = request.getParameter("personType");
		if(StringUtils.isNotBlank(personType)){
			invoice.setPersonType(Integer.parseInt(personType));
		}
		
		String invoiceType = request.getParameter("invoiceType");
		if(StringUtils.isNotBlank(invoiceType)){
			invoice.setInvoiceType(Integer.parseInt(invoiceType));
		}
		
		String invocieHead = request.getParameter("invocieHead");
		if(StringUtils.isNotBlank(invocieHead)){
			invoice.setInvocieHead(invocieHead);
		}
		
		String serverType = request.getParameter("serverType");
		if(StringUtils.isNotBlank(serverType)){
			invoice.setServerType(Integer.parseInt(serverType));
		}
		String fastMailNo = request.getParameter("fastMailNo");
		if(StringUtils.isNotBlank(fastMailNo)){
			invoice.setFastMailNo(fastMailNo);
		}
		String fastMailCompany = request.getParameter("fastMailCompany");
		if(StringUtils.isNotBlank(fastMailCompany)){
			invoice.setFastMailCompany(fastMailCompany);
		}
		invoice.setUpdateTime(new Date());
		
		String filename = boxfile.getOriginalFilename(); 
		/**上传电子发票*/
		if (StringUtils.isNotBlank(filename)) {
			long tsSize = boxfile.getSize();
			String elecInvoiceUrl = uploadFile(filename, boxfile.getInputStream());
			invoice.setElecInvoiceUrl(elecInvoiceUrl);
			invoice.setElecInvoiceSize(tsSize+"");
		}
		
		int record = memberInvoiceService.updateByPrimaryKeySelective(invoice);
		
		String mailId = request.getParameter("mailId");
		if(StringUtils.isNotBlank(mailId)){
			MemberMailInfo mailInfo = memberMailService.selectMemberMailById(Integer.parseInt(mailId));
			if(mailInfo != null){
				mailInfo.setReceiver(request.getParameter("receiver"));
				mailInfo.setPhone(request.getParameter("receiverPhone"));
				mailInfo.setAddress(request.getParameter("address"));
				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String province = request.getParameter("province");
				mailInfo.setProvince(province);
				mailInfo.setCity(city);
				mailInfo.setArea(area);
				record = memberMailService.updateByPrimaryKeySelective(mailInfo);
			}
		}
		 if(record == 0){
			 map.put("status", "fail");
			 map.put("msg", "网络繁忙，请稍后再试！");
			 return map;
		 }
		 map.put("status", "success");
		 map.put("msg", "操作成功！");
		return map;
	}

	/**
	 * 查询所属城市
	 * 
	 * @param parentCode
	 * @param layer
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "所属城市")
	@RequestMapping(value = "/cityCombobox_by_parentCode")
	public String cityCombobox_by_ParentCode(String parentCode,Integer layer,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			response.setContentType("text/html;charset=utf-8");
			List<City> cityList = cityService.queryCityByParentCodeAndLayer(parentCode,layer);
			StringBuffer tree = new StringBuffer();
			tree.append("[");
			tree.append("{");
			tree.append("\"id\":\"\",");
			tree.append("\"text\":\"请选择\"");
				tree.append("},");
			if (cityList != null && cityList.size() > 0) {
				for (int i = 0; i < cityList.size(); i++) {
					City city = cityList.get(i);
					if ("00".equals(city.getCode()))
						continue;
					tree.append("{");
					tree.append("\"id\":\"" + city.getCode() + "\",");
					tree.append("\"text\":\"" + city.getName() + "\"");
					if (i < cityList.size() - 1) {
						tree.append("},");
					} else {
						tree.append("}");
					}
				}
			}
			tree.append("]");
			response.getWriter().print(tree.toString());
		return null;
	}
	
	@SystemServiceLog()
	@ResponseBody
	@RequestMapping(value="refuseInvoice")
	public Map<String, Object> refuseInvoice(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		MemberInvoice invoice = memberInvoiceService.selectById(Integer.parseInt(id));
		if(invoice == null){
			log.error("查询发票申请记录为空");
			map.put("status", "fail");
			return map;
		}
		invoice.setRemark(remark);
		invoice.setStatus(5);
		//更新记录
		int records = memberInvoiceService.updateByPrimaryKeySelective(invoice);
		if(records > 0){
			//更新订单记录
			 /**更新对应订单中的状态*/
			String orderIds = invoice.getOrderIds();
			JSONObject jsonObject = JSONObject.fromObject(orderIds) ;
			updateOrderInvoiceStatus(jsonObject, 0);
		}
		map.put("status", "success");
		return map;
	}

}
