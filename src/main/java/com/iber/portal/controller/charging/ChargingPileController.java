package com.iber.portal.controller.charging;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.ChargingPileService;

@Controller
public class ChargingPileController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private ChargingPileService chargingPileService;
	
	/**
	 * @describe 充电桩页面
	 * 
	 */
	@SystemServiceLog(description="充电桩页面")
	@RequestMapping(value = "/charging_pile_page", method = { RequestMethod.GET })
	public String chargingPile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电桩页面");
		return "charging/chargingPile";		
	}
	
	/**
	 * @describe 充电桩列表
	 */
	@SystemServiceLog(description="充电桩列表")
	@RequestMapping(value = "/charging_pile_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电桩列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String pile_type = request.getParameter("pile_type");
		String park_name = request.getParameter("park_name");
		String city_code = request.getParameter("city_code");
		if(StringUtils.isNotBlank(city_code)&&!StringUtils.equals(city_code, "00")){
			paramMap.put("cityCode", city_code);
		}else{
			//城市过滤
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			String cityCode = null ;
			if(!"00".equals(user.getCityCode())){
				cityCode = user.getCityCode() ;
			}
			paramMap.put("cityCode", cityCode);
		}
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("pileType", pile_type);
		paramMap.put("parkName", park_name);
		
		Pager<ChargingPile> pager = chargingPileService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	@SystemServiceLog(description="充电桩报表导出")
	@RequestMapping(value = "/export_pile_execl_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportPileReportExecl(String park_name,String pile_type, String city_code,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "ChargePileReport" ;
		//列名
		String columnNames [] = {  "地区", "网点名称",  "桩型号", "设备编号",
				"车位号", "充电类型", "电压", "电流", "功率","二维码","状态","是否在线","电桩所属" };
		String keys[] = { "cityName", "name", "pileType",
				"pileNo", "carNo", "chargingType", "voltage", "electricity",
				"power", "dimensionRule","status","isOnline","pileCategory" };
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(city_code)&&!StringUtils.equals(city_code, "00")){
			map.put("cityCode", city_code);
		}else{
			//城市过滤
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			String cityCode = null ;
			if(!"00".equals(user.getCityCode())){
				cityCode = user.getCityCode() ;
			}
			map.put("cityCode", cityCode);
		}
		map.put("parkName", park_name);
		map.put("pileType", pile_type);
		map.put("from", null);
		map.put("to", null);
	    Pager<ChargingPile> pager = chargingPileService.getAll(map);
	    List<ChargingPile> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "充电桩报表");
		list.add(sheetNameMap);
		list.addAll(createChargingPiles(datas));
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

	private List<Map<String, Object>> createChargingPiles(
			List<ChargingPile> ChargingPileList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "name", "pileType", "pileNo",
				"carNo", "chargingType", "voltage", "electricity", "power",
				"dimensionRule", "status", "isOnline", "pileCategory" };
		for (ChargingPile pile : ChargingPileList) {
			map = new HashMap<String, Object>();
			map.put(keys[0], pile.getCityName());
			if (pile.getName() == null) {
				map.put(keys[1], "");
			} else {
				map.put(keys[1], pile.getName());
			}
			map.put(keys[2], pile.getPileType());
			map.put(keys[3], pile.getPileNo());
			map.put(keys[4], pile.getCarNo());
			if(pile.getChargingType().equals("fast")){
				map.put(keys[5], "快充");
			}else if(pile.getChargingType().equals("slow")){
				map.put(keys[5], "慢充");
			}else{
				map.put(keys[5], pile.getChargingType());
			}
			map.put(keys[6], pile.getVoltage());
			map.put(keys[7], pile.getElectricity());
			map.put(keys[8], pile.getPower());
			map.put(keys[9], pile.getDimensionRule());
			if ("empty".equals(pile.getStatus())) {
				map.put(keys[10], "空闲");
			} else if ("handshake".equals(pile.getStatus())) {
				map.put(keys[10], "握手");
			} else if ("charging".equals(pile.getStatus())) {
				map.put(keys[10], "充电");
			} else if ("repair".equals(pile.getStatus())) {
				map.put(keys[10], "维修");
			} else {
				map.put(keys[10], pile.getStatus());
			}
			if ("1".equals(pile.getIsOnline())) {
				map.put(keys[11], "是");
			} else if ("0".equals(pile.getIsOnline())) {
				map.put(keys[11], "否");
			} else {
				map.put(keys[11], pile.getIsOnline());
			}
			if ("1".equals(pile.getPileCategory())) {
				map.put(keys[12], "自有");
			} else if ("2".equals(pile.getPileCategory())) {
				map.put(keys[12], "外部");
			} else {
				map.put(keys[12], pile.getPileCategory());
			}
			list.add(map);
		}
		return list;
	}
	/**
	 * @describe 保存更新充电桩
	 * 
	 */
	@SystemServiceLog(description="保存更新充电桩")
	@RequestMapping(value = "/charging_pile_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新充电桩");
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		String cityCode = request.getParameter("city_code");
		String chargingType = request.getParameter("charging_type");
		String parkId = request.getParameter("park_id");
		String typeId = request.getParameter("pile_id");
		String voltage = request.getParameter("voltage");
		String electricity = request.getParameter("electricity");
		String power = request.getParameter("power");
		String status = request.getParameter("status");
		String isOnline = request.getParameter("is_online");
		String pileCategory = request.getParameter("pile_category");
		String carNo = request.getParameter("car_no");
		String pileNo = request.getParameter("pile_no");
		String dimensionRule = request.getParameter("dimension_rule");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			ChargingPile currObj = chargingPileService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCityCode(cityCode);
				currObj.setUpdateId(user.getId());
				currObj.setUpdateTime(new Date());
				currObj.setCarNo(carNo);
				currObj.setChargingType(chargingType);
				currObj.setDimensionRule(dimensionRule);
				currObj.setElectricity(electricity);
				currObj.setIsOnline(isOnline);
				currObj.setParkId(Integer.parseInt(parkId));
				currObj.setPileCategory(pileCategory);
				currObj.setPileNo(pileNo);
				currObj.setPower(power);
				currObj.setStatus(status);
				currObj.setTypeId(Integer.parseInt(typeId));
				currObj.setVoltage(voltage);
				
				chargingPileService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			ChargingPile obj = new ChargingPile();
			obj.setCreateId(user.getId());
			obj.setCreateTime(new Date());
			obj.setCityCode(cityCode);
			obj.setCarNo(carNo);
			obj.setChargingType(chargingType);
			obj.setDimensionRule(dimensionRule);
			obj.setElectricity(electricity);
			obj.setIsOnline(isOnline);
			obj.setParkId(Integer.parseInt(parkId));
			obj.setPileCategory(pileCategory);
			obj.setPileNo(pileNo);
			obj.setPower(power);
			obj.setStatus(status);
			obj.setTypeId(Integer.parseInt(typeId));
			obj.setVoltage(voltage);
			chargingPileService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除充电桩
	 * 
	 */
	@SystemServiceLog(description="删除充电桩")
	@RequestMapping(value = "/charging_pile_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除充电桩");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			chargingPileService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
}
