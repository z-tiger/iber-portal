package com.iber.portal.controller.charging;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.iber.portal.model.charging.ChargingPilePrice;
import com.iber.portal.model.charging.ConnectorInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.ChargingPilePriceService;

@Controller
public class ChargingPilePriceController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private ChargingPilePriceService chargingPilePriceService;
	
	/**
	 * @describe 充电桩价格页面
	 * 
	 */
	@SystemServiceLog(description="充电桩价格页面")
	@RequestMapping(value = "/charging_pile_price_page", method = { RequestMethod.GET })
	public String chargingPilePrice(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电桩价格页面");
		return "charging/chargingPilePrice";		
	}
	
	/**
	 * @describe 充电桩价格列表
	 */
	@SystemServiceLog(description="充电桩价格列表")
	@RequestMapping(value = "/charging_pile_price_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPilePriceList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电桩价格列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String equipmentType = request.getParameter("pile_type");
		String name = request.getParameter("name");
		//String city_code = request.getParameter("city_code");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("city_code"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", request.getParameter("city_code"));
	         	}
	         }
       }else{
    	   paramMap.put("cityCode", user.getCityCode());
       }
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("equipmentType", equipmentType);
		paramMap.put("name", name);
		//paramMap.put("cityCode", city_code);
		Pager<ChargingPilePrice> pager = chargingPilePriceService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	@SystemServiceLog(description="电价设置报表导出")
	@RequestMapping(value = "/export_chargePirce_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportPileReportExecl(String name,String pile_type,String city_code,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "ChargePilePriceReport" ;
		//列名充电桩编码	
		String columnNames [] = { "地区", "网点名称", "桩型号",  "充电费（元/度）", "服务费（元/度）", "其他费用", "折扣", "启用状态"};
		
		String keys[] = { "cityName", "name", "equipmentType",
				"chargingPrice", "servicePrice", "otherPrice", "discount", "status" };
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
		map.put("name", name);
		map.put("equipmentType", pile_type);
		map.put("from", null);
		map.put("to", null);
		Pager<ChargingPilePrice> pager = chargingPilePriceService.getAll(map);
		List<ChargingPilePrice> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "充电枪报表");
		list.add(sheetNameMap);
		list.addAll(createData(datas));
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
	
	private List<Map<String, Object>> createData(
			List<ChargingPilePrice> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "name", "equipmentType",
				"chargingPrice", "servicePrice", "otherPrice", "discount", "status" };
		for (ChargingPilePrice pilePrice : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], pilePrice.getCityName());
			if("".equals(pilePrice.getName())){
				map.put(keys[1], "");
			}else{
				map.put(keys[1], pilePrice.getName());
			}
			Integer type = pilePrice.getEquipmentType();
			if(type != null){
				switch (type) {
				case 1:
					map.put(keys[2], "直流设备");
					break;
				case 2:
					map.put(keys[2], "交流设备");
					break;
				case 3:
					map.put(keys[2], "交直流一体设备");
					break;
				default:
					break;
				}
			}else {
				map.put(keys[2], "未知");
			}
			map.put(keys[3], pilePrice.getChargingPrice() != null ? df.format(pilePrice.getChargingPrice()/100.0) : 0.00);
			map.put(keys[4], pilePrice.getServicePrice() != null ? df.format(pilePrice.getServicePrice()/100.0) : 0.00);
			map.put(keys[5], pilePrice.getOtherPrice() != null ? df.format(pilePrice.getOtherPrice()/100.0) : 0.00);
			map.put(keys[6], pilePrice.getDiscount());
			if("1".equals(pilePrice.getStatus())){
				map.put(keys[7], "是");
			}else if("0".equals(pilePrice.getStatus())) {
				map.put(keys[7], "否");
			}else {
				map.put(keys[7], pilePrice.getStatus());
			}
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * @describe 保存更新充电桩价格
	 * 
	 */
	@SystemServiceLog(description="保存更新充电桩价格")
	@RequestMapping(value = "/charging_pile_price_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPilePriceSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新充电桩价格");
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		Integer chargingPrice = (int)(Double.parseDouble(request.getParameter("charging_price"))*100);
		String cityCode = request.getParameter("city_code");
		Integer discount = (int)(Double.parseDouble(request.getParameter("discount")));
		Integer otherPrice = (int)(Double.parseDouble(request.getParameter("other_price"))*100);
		String parkId = request.getParameter("park_id");
		String equipmentType = request.getParameter("equipmentType");
		Integer servicePrice = (int)(Double.parseDouble(request.getParameter("service_price"))*100);
		String status = request.getParameter("status");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			ChargingPilePrice currObj = chargingPilePriceService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setChargingPrice(chargingPrice);
				currObj.setCityCode(cityCode);
				currObj.setDiscount(discount);
				currObj.setOtherPrice(otherPrice);
//				currObj.setParkId(Integer.parseInt(parkId));
				currObj.setEquipmentType(Integer.parseInt(equipmentType));
				currObj.setServicePrice(servicePrice);
				currObj.setStatus(status);
				currObj.setUpdateCreate(new Date());
				currObj.setUpdateId(user.getId());
				chargingPilePriceService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			ChargingPilePrice obj = new ChargingPilePrice();
			obj.setChargingPrice(chargingPrice);
			obj.setCityCode(cityCode);
			obj.setDiscount(discount);
			obj.setOtherPrice(otherPrice);
			obj.setParkId(Integer.parseInt(parkId));
			obj.setEquipmentType(Integer.parseInt(equipmentType));
			obj.setServicePrice(servicePrice);
			obj.setStatus(status);
			obj.setCreateId(user.getId());
			obj.setCreateTime(new Date());
			chargingPilePriceService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除充电桩价格
	 * 
	 */
	@SystemServiceLog(description="删除充电桩价格")
	@RequestMapping(value = "/charging_pile_price_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPilePriceDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除充电桩价格");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			chargingPilePriceService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
}
