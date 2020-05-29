package com.iber.portal.controller.insurance;

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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.insurance.Insurance;
import com.iber.portal.model.insurance.InsuranceAttachment;
import com.iber.portal.model.insurance.InsuranceRecord;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.insurance.InsuranceAttachmentService;
import com.iber.portal.service.insurance.InsuranceRecordService;
import com.iber.portal.service.insurance.InsuranceService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.DateTime;


@Controller
public class InsuranceRecordController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private InsuranceRecordService insuranceRecordService;
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private InsuranceAttachmentService insuranceAttachmentService;
	
	@Autowired
    private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CarService carService;
	
	/**
	 * @describe 出险页面
	 * 
	 */
	@SystemServiceLog(description="出险页面")
	@RequestMapping(value = "/insuranceRecord_page", method = { RequestMethod.GET })
	public String insuranceRecord(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("出险页面");
		return "insurance/insuranceRecord";		
	}
	
	/**
	 * @describe 出险列表
	 */
	@SystemServiceLog(description="出险列表")
	@RequestMapping(value = "/insuranceRecord_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceRecordList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("出险列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String address = request.getParameter("address");
		String lpn = request.getParameter("lpn");
		String insuranceCompany = request.getParameter("insurance_company");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("address", address);
		paramMap.put("lpn", lpn);
		paramMap.put("insuranceCompany", insuranceCompany);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		//城市过滤
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null ;
		if(!"00".equals(user.getCityCode())){
			cityCode = user.getCityCode() ;
		}
		paramMap.put("cityCode", cityCode);
		Pager<InsuranceRecord> pager = insuranceRecordService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	@SystemServiceLog(description="车辆出险数据导出")
	@RequestMapping(value = "/export_insuranceRecord_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String lpn,String address,String insuranceCompany,String queryDateFrom,String queryDateTo,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "InsuranceRecordReport" ;
		//列名充电桩编码	
		String columnNames [] = {"车牌号码", "出险时间",  "出险地址", "描述", "保险单号", "承保公司", 
				"赔偿金额（元）","订单号","会员姓名","会员手机号"};
		
		String keys[] = { "lpn", "occurTime","address", 
				"describe", "insuranceNo", "insuranceCompany", "money",
				"orderNo","memberName","memberPhone"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("address", address);
		paramMap.put("lpn", lpn);
		paramMap.put("insuranceCompany", insuranceCompany);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		paramMap.put("from", null);
		paramMap.put("to", null);
		Pager<InsuranceRecord> pager = insuranceRecordService.getAll(paramMap);
		List<InsuranceRecord> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆出险数据报表");
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
			List<InsuranceRecord> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "lpn", "occurTime","address", 
				"describe", "insuranceNo", "insuranceCompany", "money",
				"orderNo","memberName","memberPhone"};
		for (InsuranceRecord record : data) {
			map = new HashMap<String, Object>();
			String lpn = record.getLpn();
			if(lpn != null){
				map.put(keys[0], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[1], record.getOccurTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getOccurTime()):"");
			map.put(keys[2], record.getAddress());
			map.put(keys[3], record.getDescribe());
			map.put(keys[4], record.getInsuranceNo());
			map.put(keys[5], record.getInsuranceCompany());
			map.put(keys[6], (Integer)record.getMoney()!=null?new DecimalFormat("0.00").format(record.getMoney()/100.0):0.00);
			map.put(keys[7], record.getOrderNo()); 
			map.put(keys[8], record.getMemberName());
			map.put(keys[9], record.getMemberPhone());
			
			list.add(map);
		}
		return list;
	}
	/**
	 * @describe 保存更新出险
	 * 
	 */
	@SystemServiceLog(description="保存更新出险")
	@RequestMapping(value = "/insuranceRecord_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceRecordSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新出险");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String address = request.getParameter("address");
		String occurTime = request.getParameter("occur_time");
		String lpn = request.getParameter("lpn").replace("•", "");
		String describe = request.getParameter("describe");
		String insuranceCompany = "";
		String insuranceNo = "";
		String money = request.getParameter("money");
		Double moneyD = Double.parseDouble(money);
		int moneyI = (int) (moneyD*100) ;
		String orderNo = request.getParameter("order_no");
		String uploud_url_arr = request.getParameter("uploud_url_arr");
		int memberId = 0;
		String memberName = "";
		String memberPhone = "";
		//保单查询
		if (lpn!=null && !lpn.equals("")) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lpn", lpn);
			paramMap.put("occurTime", occurTime);
			List<Insurance> insuranceList = insuranceService.selectByLpn(paramMap);
			if(insuranceList.size()>0){
				for (int i = 0; i < insuranceList.size(); i++) {
					Insurance is = insuranceList.get(i);
					insuranceCompany = is.getInsuranceCompany();
					insuranceNo += is.getInsuranceNo()+",";
				}
			}
					
		}
		//用车订单查询
		if (orderNo!=null && !orderNo.equals("")) {
			List<TimeShareOrder> order = timeShareOrderService.queryOrderByOrderId(orderNo);
			if(order.size()>0){
				memberId = Integer.parseInt(order.get(0).getMemberId());
				//lpn = order.get(0).getLpn();
				Member m = memberService.selectByPrimaryKey(memberId);
				memberName = m.getName();
				memberPhone = m.getPhone();
			}
		}
		
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			InsuranceRecord currObj = insuranceRecordService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setAddress(address);
				currObj.setDescribe(describe);
				currObj.setInsuranceCompany(insuranceCompany);
				currObj.setInsuranceNo(insuranceNo);
				currObj.setMoney(moneyI);
				currObj.setLpn(lpn);
				currObj.setMemberId(memberId);
				currObj.setMemberName(memberName);
				currObj.setMemberPhone(memberPhone);
				currObj.setOccurTime(DateTime.getDateTime(occurTime));
				currObj.setOrderNo(orderNo);
				currObj.setUpdateTime(new Date());
				currObj.setUpdateUser(user.getName());
				
				if (uploud_url_arr!=null && !uploud_url_arr.equals("")) {
					currObj.setIsAttachment(currObj.getIsAttachment()+uploud_url_arr.split("@#@#@#@#@").length);//有附件
				}
				insuranceRecordService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			InsuranceRecord obj = new InsuranceRecord();
			obj.setAddress(address);
			obj.setCreateTime(new Date());
			obj.setCreateUser(user.getName());
			obj.setDescribe(describe);
			obj.setInsuranceCompany(insuranceCompany);
			obj.setInsuranceNo(insuranceNo);
			obj.setMoney(moneyI);
			obj.setLpn(lpn);
			obj.setMemberId(memberId);
			obj.setMemberName(memberName);
			obj.setMemberPhone(memberPhone);
			obj.setOccurTime(DateTime.getDateTime(occurTime));
			obj.setOrderNo(orderNo);
			if (uploud_url_arr!=null && !uploud_url_arr.equals("")) {
				obj.setIsAttachment(uploud_url_arr.split("@#@#@#@#@").length);//附件数
			}else{
				obj.setIsAttachment(0);//没有附件
			}
			insuranceRecordService.insertSelective(obj);
			id = String.valueOf(obj.getId());
		}
		
		if (uploud_url_arr!=null && !uploud_url_arr.equals("")) {
			String urlArr[] = uploud_url_arr.split("@#@#@#@#@");
			for (int i = 0; i < urlArr.length; i++) {
				String attachName =  urlArr[i].split("::")[0];
				String attachUrl = urlArr[i].split("::")[1];
				String attachSize = urlArr[i].split("::")[2];
				InsuranceAttachment obj = new InsuranceAttachment();
				obj.setAttachName(attachName);
				obj.setAttachSize(attachSize);
				obj.setAttachType("insuranceRecord");
				obj.setAttachUrl(attachUrl);
				obj.setCreateTime(new Date());
				obj.setInsuranceId(Integer.parseInt(id));
				insuranceAttachmentService.insertSelective(obj);
			}
		}
		response.getWriter().print("success");
		return null;	
	}
	/**
	 * @describe 图片预览
	 * 
	 */
	@SystemServiceLog(description="图片预览")
	@RequestMapping(value = "/insuranceRecord_show_attachment", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceRecordShowAttachment(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("图片预览");
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("insuranceId", id);
		paramMap.put("attachType", "insuranceRecord");
		List<InsuranceAttachment> insuranceAttachment = insuranceAttachmentService.getAllAttachment(paramMap);
		JSONArray obj = new JSONArray();
		obj.add(insuranceAttachment);
		response.getWriter().print(obj);
		return null;
	}
	
	/**
	 * @describe 删除出险
	 * 
	 */
	@SystemServiceLog(description="删除出险")
	@RequestMapping(value = "/insuranceRecord_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceRecordDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除出险");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			insuranceRecordService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 车牌号检查
	 * 
	 */
	@SystemServiceLog(description="车牌号检查")
	@RequestMapping(value = "/insuranceRecord_lpn_check", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceRecordLpnCheck(String lpn, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("车牌号检查");
		String msg = "fail";
		response.setContentType("text/html;charset=utf-8");
		if (lpn!=null && !lpn.equals("")) {
			Car car =carService.selectByLpn(lpn);
			if(car!=null){
				msg = "success";
			}
		}
		response.getWriter().print(msg);
		return null;
	}
	
}
