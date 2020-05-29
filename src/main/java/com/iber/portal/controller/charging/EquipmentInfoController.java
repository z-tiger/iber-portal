package com.iber.portal.controller.charging;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.iber.portal.model.base.Park;
import com.iber.portal.model.charging.CarBrand;
import com.iber.portal.model.charging.EquipmentBrandRelation;
import com.iber.portal.model.charging.EquipmentInfo;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.CarBrandService;
import com.iber.portal.service.charging.ConnectorInfoService;
import com.iber.portal.service.charging.ConnectorStatusInfoService;
import com.iber.portal.service.charging.EquipmentBrandRelationService;
import com.iber.portal.service.charging.EquipmentInfoService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.vo.charging.ConnectorInfoVo;
import com.iber.portal.vo.pile.EquipmentInfoVo;


@Controller
public class EquipmentInfoController extends MainController{
	
	private final static Logger log= Logger.getLogger(EquipmentInfoController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private EquipmentInfoService equipmentInfoService; 
	
	@Autowired(required=false)
	private  ConnectorInfoService connectorInfoService;
	
	@Autowired(required=false)
	private  ParkService  parkService;
	
	@Autowired(required=false)
	private  EquipmentBrandRelationService equipmentBrandRelationService;
	
	@Autowired(required=false)
	private CarBrandService carBrandService;
	
	@Autowired(required=false)
	private  ConnectorStatusInfoService connectorStatusInfoService ;
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="EquipmentInfo页面")
	@RequestMapping("/equipmentInfo_page") 
	public String equipmentInfo_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("EquipmentInfo页面");
		return "charging/equipmentInfo" ;
	}
	
	
	/**
	 * 数据列表
	 */
	@SystemServiceLog(description="充电设备数据列表")
	@RequestMapping("/dataListEquipmentInfo") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String cityCode = user.getCityCode();
		String stationName = request.getParameter("stationName");
		String  equipmentCode=request.getParameter("equipmentCode");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("equipmentCode",equipmentCode);
		paramMap.put("stationName", stationName);
		if(cityCode.equals("00")){
	    	paramMap.put("cityCode", null);
        }else{
        	paramMap.put("cityCode", cityCode);
        }
		//Pager<EquipmentInfo> pager = equipmentInfoService.queryPageList(paramMap);
		
		Pager<EquipmentInfoVo> pager = equipmentInfoService.selectUpgradePageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_equipment_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String stationName,String equipmentCode,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "DispatcherReport" ;
		//列名充电桩编码	
		String columnNames [] = {"网点名称", "充电桩编码",  "充电桩类型", "充电桩型号", "充电枪个数（个）", "电桩版本类型", 
				"电桩最新版本","设备提供商名称","生产日期"};
		
		String keys[] = { "stationName", "equipmentCode","equipmentType", "equipmentModel",
				"connectorNumber", "softTypeName", "pileVersionNo",
				"affordName","productionDate"};
		SysUser user = (SysUser) getUser(request) ;
		String cityCode = user.getCityCode();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", null);
		paramMap.put("to", null);
		paramMap.put("equipmentCode",equipmentCode);
		paramMap.put("stationName", stationName);
		if(cityCode.equals("00")){
	    	paramMap.put("cityCode", null);
        }else{
        	paramMap.put("cityCode", cityCode);
        }
		Pager<EquipmentInfoVo> pager = equipmentInfoService.selectUpgradePageList(paramMap);
		List<EquipmentInfoVo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "调度员理数据报表");
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
			List<EquipmentInfoVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "stationName", "equipmentCode","equipmentType", "equipmentModel",
				"connectorNumber", "softTypeName", "pileVersionNo",
				"affordName","productionDate"};
		for (EquipmentInfoVo vo : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], vo.getStationName());
			map.put(keys[1],vo.getEquipmentCode());
			if(vo.getEquipmentType() != null){
				switch (vo.getEquipmentType()) {
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
			}else{
				map.put(keys[2], vo.getEquipmentType());
			}
			map.put(keys[3], vo.getEquipmentModel());
			map.put(keys[4], vo.getConnectorNumber());
			map.put(keys[5], vo.getSoftTypeName());
//			if( vo.getVersionRecord() != null && vo.getPileVersionRecord()!= null ){
//				if(vo.getVersionRecord().equals("")||Float.parseFloat(vo.getVersionRecord())<Float.parseFloat(vo.getPileVersionRecord())){
//					map.put(keys[6], vo.getVersionCode()+"[升]");
//				}else{
//					map.put(keys[6], vo.getVersionCode()+"[新]");
//				}
//			}
			map.put(keys[6], vo.getPileVersionNo());
			map.put(keys[7], vo.getAffordName()); 
			map.put(keys[8], vo.getProductionDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getProductionDate()):"");
			
			list.add(map);
		}
		return list;
	}
	/**
	 * 添加或修改数据 
	 */
	@SystemServiceLog(description="充电设备数据添加或修改")
	@RequestMapping("/saveOrUpdateEquipmentInfo")
	public void saveOrUpdate(EquipmentInfo entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("充电设备新增或更新");
		
		SysUser user = (SysUser) getUser(request) ;
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			Park park = parkService.selectByPrimaryKey(Integer.valueOf(entity.getStationName()));
			entity.setCreateTime(new Date());
			entity.setCreateId(user.getId());
			entity.setUpdateId(user.getId());
			entity.setUpdateTime(new Date());
			entity.setEquipmentLng(park.getLongitude());
			entity.setEquipmentLat(park.getLatitude());
			entity.setOperatorId(park.getOperatorId());
			entity.setStationId(Integer.parseInt(entity.getStationName()));
		    equipmentInfoService.insert(entity);
			String ids= request.getParameter("ids");
			String[] id = ids.split(",");
			for(int i=0;i<id.length;i++){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("brandId",Integer.valueOf(id[i]));
				paramMap.put("equipmentId",entity.getId());
				paramMap.put("createId", user.getId());
				equipmentBrandRelationService.insertRecords(paramMap);
			}
		}else{
			entity.setUpdateTime(new Date());
			entity.setUpdateId(user.getId());
			Map<String, Object> paramMaps = new HashMap<String, Object>();
			paramMaps.put("id",entity.getId());
			equipmentBrandRelationService.deleteRecordsByEquipmentId(paramMaps);
			String ids =request.getParameter("ids");
			String[] id = ids.split(",");
			for(int i=0;i<id.length;i++){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("brandId",Integer.valueOf(id[i]));
				paramMap.put("equipmentId",entity.getId());
				paramMap.put("createId", user.getId());
				equipmentBrandRelationService.insertRecords(paramMap);
			}
			equipmentInfoService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据 
	 */
	@SystemServiceLog(description="充电设备数据删除")
	@RequestMapping("/deleteEquipmentInfoById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			equipmentInfoService.deleteByPrimaryKey(id);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id",id);
			equipmentBrandRelationService.deleteRecordsByEquipmentId(paramMap);
			//删除枪下面的接口状态数据
			connectorStatusInfoService.deleteByEquipmentId(id);
			//删除桩下的枪数据
			connectorInfoService.deleteByEquipmentId(id);
			
			
		}
		response.getWriter().print("success");
	}
	
	/**combobox列表*/
	@SystemServiceLog(description="设备编码combobox列表")
	@RequestMapping(value = "/equipmentId_Combobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String stationCombobox (HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("设备编码combobox列表");
		response.setContentType("text/html;charset=utf-8");	
		SysUser user = (SysUser) getUser(request) ;
		List<EquipmentInfo> equipmentCodeList = new ArrayList<EquipmentInfo>();
		equipmentCodeList=equipmentInfoService.getAllBycityCode(user.getCityCode());
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(equipmentCodeList!=null && equipmentCodeList.size()>0) {				
			for(int i=0;i<equipmentCodeList.size();i++ ){
				EquipmentInfo equipmentInfo=equipmentCodeList.get(i);
				tree.append("{");
				tree.append("\"id\":\""+equipmentInfo.getId()+"\",");
				tree.append("\"text\":\""+equipmentInfo.getEquipmentCode()+"\"");
				if(i<equipmentCodeList.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
		}
		tree.append("]");
				
		response.getWriter().print(tree.toString());
		return null;
	}
	
	/**设备附加列表*/
	@SystemServiceLog(description="设备附加列表")
	@RequestMapping(value = "/connector_attachment_preview", method = { RequestMethod.GET, RequestMethod.POST})
	public void  equipmentlist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("设备附件数据列表");
		//获取其他查询参数
		//String title = request.getParameter("title");
		response.setContentType("text/html;charset=utf-8");	
		String id = request.getParameter("id");
		Pager<ConnectorInfoVo> pager = connectorInfoService.queryAttachmentList(Integer.parseInt(id));
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**查询所有网点+设备编码 分页查询*/
	@SystemServiceLog(description="查询所有网点+设备编码")
	@RequestMapping(value = "/parkEquipment_allByPage", method = { RequestMethod.GET,RequestMethod.POST })
	public String parkNamelist(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info(" 网点列表");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
 		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String cityCode = user.getCityCode();
		String equipmentCode  = request.getParameter("equipmentCode");
		paramMap.put("from", from);
		paramMap.put("to", to);
		if(cityCode.equals("00")){
	    	paramMap.put("cityCode", null);
        }else{
        	paramMap.put("cityCode", cityCode);
        }
		paramMap.put("equipmentCode", equipmentCode);
		Pager<EquipmentInfo> pager = equipmentInfoService.getAllParkEquipment(paramMap);
		
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
	
	/**所有的车辆品牌信息*/
	@SystemServiceLog(description="桩车辆品牌信息")
	@RequestMapping(value = "/allBrand", method = { RequestMethod.GET , RequestMethod.POST })
	public String getAllBrand(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("桩支持车辆品牌列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String brandName = request.getParameter("brandName");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("brandName", brandName);
		Pager<CarBrand> pager = carBrandService.getAllRecords(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 设备支持车辆品牌列表
	 */
	@SystemServiceLog(description="桩支持车辆品牌列表")
	@RequestMapping(value = "/charging_car_brand_list_by_equipmentId", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingCarBrandListByEquipment(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("桩支持车辆品牌列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String equipmentId = request.getParameter("equipmentId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("equipmentId", equipmentId);
		Pager<EquipmentBrandRelation> pager = equipmentBrandRelationService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	/**
	 * @describe 设备当前没有支持车辆品牌列表
	 */
	@SystemServiceLog(description="桩还没有设置支持的车辆品牌列表")
	@RequestMapping(value = "/allNotBelongBrand", method = { RequestMethod.GET , RequestMethod.POST })
	public String carBrandList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("桩还没有设置支持的车辆品牌列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String brandName = request.getParameter("brandName");
		String equipmentId = request.getParameter("equipmentId");
		Pager<CarBrand> pager = carBrandService.getAllBrandByEquipmentId(Integer.parseInt(equipmentId),from,to,brandName);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**给设备设置车辆品牌支持*/
	@SystemServiceLog(description="桩设置车辆品牌支持")
	@RequestMapping(value = "/equipment_brand_add", method = { RequestMethod.GET,RequestMethod.POST })
	public String updateBelongEnterprise(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info("设备设置车辆品牌支持");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		String equipmentId = request.getParameter("equipment_id");
		String ids= request.getParameter("ids");
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("brandId",Integer.valueOf(id[i]));
			paramMap.put("equipmentId",  Integer.valueOf(equipmentId));
			paramMap.put("createId", user.getId());
			equipmentBrandRelationService.insertRecords(paramMap);
		}
		response.getWriter().print("success");
		return null;		
	}
	
	/**给设备取消车辆品牌支持*/
	@SystemServiceLog(description="取消车辆品牌支持")
	@RequestMapping(value = "/deleteRecordsById", method = { RequestMethod.GET,RequestMethod.POST })
	public String deleteEnterprise(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info("取消车辆品牌支持");
		response.setContentType("text/html;charset=utf-8");
		String ids= request.getParameter("id");
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id",Integer.valueOf(id[i]));
			equipmentBrandRelationService.deleteRecords(paramMap);
		}
		response.getWriter().print("success");
		return null;		
	}
}
