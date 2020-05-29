package com.iber.portal.controller.charging;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.charging.ChargingOrderInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.ChargingOrderInfoService;


@Controller
public class ChargingOrderInfoController extends MainController{
	
	private final static Logger log= Logger.getLogger(ChargingOrderInfoController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ChargingOrderInfoService chargingOrderService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="ChargingOrder页面")
	@RequestMapping("/chargingOrder_page") 
	public String chargingOrder_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("ChargingOrder页面");
		return "charging/chargingOrder" ;
	}
	
	
	/**
	 * 订单数据
	 */
	@SystemServiceLog(description="订单数据列表")
	@RequestMapping("/dataListChargingOrder") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("订单数据列表");
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) getUser(request) ;
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String bt = request.getParameter("bt");
		String et = request.getParameter("et");
		String chargingStatus =request.getParameter("chargingStatus");
		String cityCode =request.getParameter("cityCode");
		if(StringUtils.isBlank(cityCode)){
			cityCode = user.getCityCode();			
		}
		String memberName =request.getParameter("memberName");
		String userType =request.getParameter("userType");
		String phone =request.getParameter("phone");
		String lpn = request.getParameter("lpn");
		String stationName = request.getParameter("stationName");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("stationName",stationName);
		paramMap.put("memberName", memberName);
		paramMap.put("userType", StringUtils.isBlank(userType) ? "member" : userType);
		paramMap.put("phone", phone);
		paramMap.put("lpn", lpn);
		paramMap.put("chargingStatus",chargingStatus);
		paramMap.put("bt",bt);
		paramMap.put("et",et);
		if(cityCode.equals("00")){
	    	paramMap.put("cityCode",null);
        }else{
        	paramMap.put("cityCode", cityCode);
        }
		Pager<ChargingOrderInfo> pager = chargingOrderService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_chargingOrder_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String memberName,String userType,String phone ,String stationName ,String bt ,String et ,
			String chargingStatus ,String cityCode ,String lpn,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "ChargOrderReport" ;
		//列名充电桩编码	
		String columnNames [] = {"网点名称", "使用人",  "手机号码", "充电订单号", "充电车辆", "充电桩类型", 
				"充电车位号","充电状态","使用人状态","充电量（KW/H）","充电时长","创建时间","开始时间","结束时间","订单金额（元）","支付金额（元）",
				"是否免单","免单原因","支付方式"};
		
		String keys[] = { "stationName", "memberName","phone", 
				"chargeSeq", "lpn", "equipmentType", "carport",
				"chargingStatus","memberStatus","chargingAmount","chargingTime","createTime","startTime","endTime",
				"orderMoney","payMoney","isFreeOrder","freeReason","payType"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberName", memberName);
		map.put("userType", StringUtils.isBlank(userType) ? "member" : userType);
		map.put("phone", phone);
		map.put("lpn", lpn);
		map.put("stationName", stationName);
		map.put("bt", bt);
		map.put("et", et);
		map.put("chargingStatus", chargingStatus);
		SysUser user = (SysUser) getUser(request) ;
		if(StringUtils.isBlank(cityCode)){
			cityCode = user.getCityCode();			
		}
		if(cityCode.equals("00")){
			map.put("cityCode",null);
        }else{
        	map.put("cityCode", cityCode);
        }
		map.put("from", null);
		map.put("to", null);
		Pager<ChargingOrderInfo> pager = chargingOrderService.queryPageList(map);
		List<ChargingOrderInfo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "充电订单数据报表");
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
			List<ChargingOrderInfo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "stationName", "memberName","phone", 
				"chargeSeq", "lpn", "equipmentType", "carport",
				"chargingStatus","memberStatus","chargingAmount","chargingTime","createTime","startTime","endTime",
				"orderMoney","payMoney","isFreeOrder","freeReason","payType"};
		for (ChargingOrderInfo order : data) {
				map = new HashMap<String, Object>();
				map.put(keys[0], order.getStationName());
				map.put(keys[1], order.getMemberName());
				map.put(keys[2], order.getPhone());
				map.put(keys[3], order.getChargeSeq());
				String lpn = order.getLpn();
				if (lpn != null) {
					if(lpn.equals("")){
						map.put(keys[4], "");
					}else {
						map.put(keys[4], lpn.indexOf("•") < 0 ? lpn.substring(0, 2) + "•" + lpn.substring(2) : lpn);
					}
				}
				map.put(keys[5], order.getEquipmentType());
				map.put(keys[6], order.getCarport());
				if (order.getChargingStatus() != null) {
					if (order.getChargingStatus().equals("charging")) map.put(keys[7], "充电中");
					if (order.getChargingStatus().equals("noPay")) map.put(keys[7], "未支付");
					if (order.getChargingStatus().equals("finish")) map.put(keys[7], "已完成");
					if (order.getChargingStatus().equals("afterReturn")) map.put(keys[7], "还车扫码充电中");
				} else {
					map.put(keys[7], order.getChargingStatus());
				}
				if (order.getMemberStatus() != null) {
					if (order.getMemberStatus().equals("ready")) map.put(keys[8], "就绪");
					if (order.getMemberStatus().equals("experience")) map.put(keys[8], "体验");
					if (order.getMemberStatus().equals("ordered")) map.put(keys[8], "预约");
					if (order.getMemberStatus().equals("useCar")) map.put(keys[8], "用车");
					if (order.getMemberStatus().equals("return")) map.put(keys[8], "还车");
				} else {
					map.put(keys[8], order.getMemberStatus());
				}
				map.put(keys[9], order.getChargingAmount());
				map.put(keys[10], order.getChargingTime());
				map.put(keys[11], order.getCreateTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()) : "");
				map.put(keys[12], order.getStartTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getStartTime()) : "");
				map.put(keys[13], order.getEndTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getEndTime()) : "");
				map.put(keys[14], order.getOrderMoney() != null ? new DecimalFormat("0.00").format(order.getOrderMoney() / 100.0) : 0.00);
				map.put(keys[15], order.getPayMoney() != null ? new DecimalFormat("0.00").format(order.getPayMoney() / 100.0) : 0.00);
				String ifRepair = order.getIsFreeOrder();
				if (ifRepair != null) {
					if (ifRepair.equals("Y")) {
						map.put(keys[16], "是");
					} else {
						map.put(keys[16], "否");
					}
				} else {
					map.put(keys[16], "否");
				}
				map.put(keys[17], order.getFreeReason());
				if (order.getPayType() != null) {
					if (order.getPayType().equals("B")) map.put(keys[18], "余额");
					if (order.getPayType().equals("A")) map.put(keys[18], "支付宝");
					if (order.getPayType().equals("T")) map.put(keys[18], "财付通");
					if (order.getPayType().equals("WX")) map.put(keys[18], "微信");
					if (order.getPayType().equals("U")) map.put(keys[18], "银联");
					if (order.getPayType().equals("AP")) map.put(keys[18], "apple pay");
				} else {
					map.put(keys[18], order.getPayType());
				}
				list.add(map);
		}
		return list;
	}
	/**
	 * 添加或修改订单数据
	 */
	@SystemServiceLog(description="添加或修改订单数据")
	@RequestMapping("/saveOrUpdateChargingOrder")
	public void saveOrUpdate(ChargingOrderInfo entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			chargingOrderService.insert(entity);
		}else{
			chargingOrderService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 */
	@SystemServiceLog(description="删除订单数据")
	@RequestMapping("/deleteChargingOrderById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			chargingOrderService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
