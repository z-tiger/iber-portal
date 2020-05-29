package com.iber.portal.controller.employee;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.employee.EmployeeOrder;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.employee.EmployeeOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author xyq
 * 员工订单页面
 */

@Controller
public class EmployeeOrderController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private EmployeeOrderService employeeOrderService;
	
	@SystemServiceLog(description = "员工订单页面")
	@RequestMapping(value = "employee_order" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("员工订单页面");
		return "/employee/employeeUseCarOrder";
	}
	
	/**
	 * @describe 历史订单列表
	 */
	@SystemServiceLog(description="历史订单列表")
	@RequestMapping(value = "/employee_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String historyOrderList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);	
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> record = new HashMap<String, Object>() ;
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		record.put("cityCode", null);
	         	}else{
	         		record.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
      	    record.put("cityCode", user.getCityCode());
       }
       
       if(!StringUtils.isBlank(request.getParameter("lpn"))){
       	record.put("lpn", request.getParameter("lpn"));
       }
       if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
       	String startTime = request.getParameter("queryDateFrom");
       	record.put("queryDateFrom", startTime+" 00:00:00");
       }
       if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
       	String endTime = request.getParameter("queryDateTo");
       	record.put("queryDateTo", endTime+" 23:59:59");
       }
	   if(!StringUtils.isBlank(request.getParameter("orderId"))){
    	   String orderId = request.getParameter("orderId");
    	   record.put("orderId", orderId);
       }
	   if(!StringUtils.isBlank(request.getParameter("status"))){ //状态查询
	       	if(request.getParameter("status").equals("useCar")){//用车中
	       		record.put("status", request.getParameter("status")); //查询nopay
	       	}else if(request.getParameter("status").equals("finish")){//已完成
	       		record.put("status", request.getParameter("status"));
	       	}else if(request.getParameter("status").equals("cancel")){
	       		record.put("status", request.getParameter("status"));
	       	}
	    }
	   if(!StringUtils.isBlank(request.getParameter("memberName"))){
		   record.put("memberName", request.getParameter("memberName"));
	   }
        record.put("offset", pageInfo.get("first_page"));
		record.put("rows", pageInfo.get("page_size"));
		Pager<EmployeeOrder> pager = employeeOrderService.getPagerAllInfo(record);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	} 
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_employeeOrder_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String memberName,String cityCode,String lpn ,String orderId ,
			String queryDateFrom ,String queryDateTo ,String status,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarRescueReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市", "订单编号",  "车牌号", "员工姓名", "手机号码", "预约网点",
				"还车网点","预约时间","上车时间","还车时间","用车时长","行驶里程(千米)","规划里程(千米)","订单状态"};
		
		String keys[] = { "cityName", "orderNo","lpn", 
				"memberName", "memberPhone", "parkName", "returnParkName",
				"orderTime","beginTime","endTime","useTime","actualMileage","planMileage","status"};
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(cityCode)){
	         	if(cityCode.equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", cityCode);
	         	}
	         }
		}else{
     	    map.put("cityCode", user.getCityCode());
		}
		map.put("memberName", memberName);
		map.put("lpn", lpn);
		map.put("orderId", orderId);
		if(StringUtils.isNotBlank(queryDateFrom)){
			map.put("queryDateFrom", queryDateFrom+" 00:00:00");
		}
		if(StringUtils.isNotBlank(queryDateTo)){
			map.put("queryDateTo", queryDateTo+" 23:59:59");
		}
		map.put("status", status);
		map.put("offset", null);
		map.put("rows", null);
		Pager<EmployeeOrder> pager = employeeOrderService.getPagerAllInfo(map);
		List<EmployeeOrder> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "员工数据报表");
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
			List<EmployeeOrder> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "orderNo","lpn", 
				"memberName", "memberPhone", "parkName", "returnParkName",
				"orderTime","beginTime","endTime","useTime","actualMileage","planMileage","status"};
		for (EmployeeOrder order : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], order.getCityName());
			map.put(keys[1], order.getOrderNo());
			String lpn = order.getLpn();
			if(lpn != null){
				map.put(keys[2], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[3], order.getMemberName());
			map.put(keys[4], order.getMemberPhone());
			map.put(keys[5], order.getParkName());
			map.put(keys[6], order.getReturnParkName());
			map.put(keys[7], order.getOrderTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderTime()):""); 
			map.put(keys[8], order.getBeginTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getBeginTime()):""); 
			map.put(keys[9], order.getEndTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getEndTime()):"");
			if(order.getStatus() != null ){
				if(order.getStatus().equals("cancel")) map.put("status", "已取消");
				if(order.getStatus().equals("finish")) map.put("status", "已完成");
				if(order.getStatus().equals("useCar")) map.put("status", "用车中");
				if(order.getStatus().equals("ordered")) map.put("status", "预约中");
			}else{
				map.put("status", order.getStatus());
			}
            map.put("useTime", CommonUtil.isEmpty(order.getUseTime())==true?0:DateTimeUtil.secondsFormate(order.getUseTime()));
            map.put("planMileage", CommonUtil.isEmpty(order.getPlanMileage())==true?0.0:order.getPlanMileage());
            map.put("actualMileage", CommonUtil.isEmpty(order.getActualMileage())==true ? 0.0:order.getActualMileage());
            list.add(map);
		}
		return list;
	}
}
