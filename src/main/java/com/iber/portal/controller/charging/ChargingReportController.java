/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.controller.charging;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.car.CarMapper;
import com.iber.portal.dao.charging.ChargingOrderInfoMapper;
import com.iber.portal.enums.charging.EquipmentTypeEnum;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.charging.ChargingOrderInfoVo;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.charging.ChargingOrderInfoService;
import com.iber.portal.service.charging.ChargingPilePriceService;
import com.iber.portal.service.charging.ConnectorInfoService;
import com.iber.portal.service.charging.EquipmentInfoService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.sys.SysDicService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.base.EasyUiTree;
import com.iber.portal.vo.charging.*;
import com.iber.portal.vo.city.CityVo;
import com.iber.portal.vo.park.ParkTreeVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 充电运营报表
 * @author ouxx
 * @since 2016-11-16 下午7:07:25
 *
 */
@Controller
public class ChargingReportController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChargingOrderInfoService chargingOrderInfoService;

	@Autowired
	private EquipmentInfoService equipmentInfoService;
	
	@Autowired
	private ConnectorInfoService connectorInfoService;

	@Autowired
	private ChargingPilePriceService chargingPilePriceService;
	
	@Autowired
	private CityService cityService;

	@Autowired
	private ParkService parkService;
	
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private SysDicService sysDicService;//字典
	
	/**
	 * 充电桩运营报表页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午5:34:21
	 */
	@SystemServiceLog(description="充电桩运营报表页面")
	@RequestMapping(value = "/charging_report_page", method = { RequestMethod.GET })
	public String charging_report_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "charging/chargingReport";
	}
	
	/**
	 * 充电桩充电明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午5:34:21
	 */
	@SystemServiceLog(description="充电桩充电明细页面")
	@RequestMapping(value = "/charging_detail_page", method = { RequestMethod.GET })
	public String charging_detail_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "charging/chargingDetailReport";
	}
	
	/**
	 * 充电桩收入报表页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午5:34:21
	 */
	@SystemServiceLog(description="充电桩收入报表页面")
	@RequestMapping(value = "/charging_income_page", method = { RequestMethod.GET })
	public String charging_income_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "charging/chargingIncomeReport";
	}
	
	/**
	 * 用电量报表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午6:31:01
	 */
	@SystemServiceLog(description="用电量报表")
	@RequestMapping(value = "/charging_consumption_page", method = { RequestMethod.GET })
	public String charging_consumption_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "charging/chargingConsumptionReport";
	}
	
	/**
	 * 构造完整的城市Map，Map<cityId, City>
	 * @return
	 * @author ouxx
	 * @date 2016-11-18 上午11:42:22
	 */
	private Map<Integer, City> generaterAllCityMap(){
		Map<Integer, City> cityMap = new HashMap<Integer, City>();
		List<City> cityList = this.cityService.selectAllCityList();
		for(City c : cityList){
			cityMap.put(c.getId(), c);
		}
		return cityMap;
	}
	
	// 需要Map<CityId, List<ChargingReportVo>> 暂时不用
	@Deprecated
	private List<ChargingReportVo> generateReportList(HttpServletRequest request){
		Map<Integer, City> cityMap = this.generaterAllCityMap();

		String cityCode = request.getParameter("cityCode");
		
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");
		
		//生成充电运营报表，typeAndCnt需要拼接，如 "快充-单枪"，充电使用率chargedFreq需要计算
		List<ChargingReportVo> reportList = null;//chargingOrderInfoService.getChargingReport(cityCode, beginDate, endDate);
		List<ChargingReportVo> newList = new LinkedList<ChargingReportVo>();
		//Map<CityId, List<ChargingReportVo>>
		Map<Integer, List<ChargingReportVo>> rstMap = new HashMap<Integer, List<ChargingReportVo>>();
		
		for(ChargingReportVo vo : reportList){
			if( null != cityMap.get(vo.getCityId()) ){
				//插入当前有运营数据的VO
				newList.add(vo);
				//插入当前网点对应城市的上一级城市的城市数据
				Integer pid = vo.getCityPid();
				Integer layer = vo.getCityLayer();
				City parentCity = cityMap.get(pid);
				if(null != parentCity){
					while(null != parentCity){
						ChargingReportVo newVo = new ChargingReportVo();
						newVo.setCityId(pid);
						newVo.setCityLayer(parentCity.getLayer());
						newVo.setCityCode(parentCity.getCode());
						newVo.setCityName(parentCity.getName());
						newVo.setCityPid(parentCity.getpId());
						newList.add(newVo);
						
						parentCity = cityMap.get(parentCity.getpId());
					}
					
				}
			}
		}
		return newList;
	}
	
	/**
	 * 查询网点所在城市的上一级城市，及其城市下的网点总数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-18 下午6:01:36
	 */
	@SystemServiceLog(description="查询网点所在城市的上一级城市，及其城市下的网点总数")
	@RequestMapping(value = "/province_list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getProvinceList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<CityVo> provinceList = this.cityService.queryProvinceList();
		//存放网点数大于0的省份
		List<CityVo> newProvinceList = new ArrayList<CityVo>();
		for(CityVo vo : provinceList){
			if(0 < vo.getNum()){
				String name = vo.getName();
				Integer parkCnt = vo.getNum();
				vo.setName(name + "(" + parkCnt + "个网点)");
				newProvinceList.add(vo);
			}
		}
		response.getWriter().print(FastJsonUtils.toJson(newProvinceList));
		return null;
	}
	
	private static final Integer LEVEL_CITY = 1;
	private static final Integer LEVEL_TYPE = 2;
	private static final Integer LEVEL_STATION = 3;
	
	/**
	 * 从HttpServletRequest获取参数的int值
	 * @param request
	 * @param paramName
	 * @return
	 * @author ouxx
	 * @date 2016-11-23 上午9:12:04
	 *//*
	private Integer getIntReqParam(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer intRst = null;
		if(StringUtils.isNotBlank(str)){
			intRst = Integer.valueOf(str);
		}
		return intRst;
	}
	
	private Timestamp getTimestampReqParam(HttpServletRequest request, String paramName){
		Timestamp time = null;
		String timeStr = request.getParameter(paramName);
		if (StringUtils.isNotBlank(timeStr)) {
			time = DateTime.getDateTime(timeStr);
		}
		return time;
	}*/
	
	/**
	 * 获取有已开通的、网点的城市的列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-21 下午4:45:47
	 */
//	@SystemServiceLog(description="获取有已开通的、网点的城市的列表")
//	@RequestMapping(value = "/city_list", method = {
//			RequestMethod.GET, RequestMethod.POST })
//	public String getCityList(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		log.info("城市-充电站列表");
//		response.setContentType("text/html;charset=utf-8");
//		
//		Integer cityCode = getIntReqParam(request, "cityCode");
//		Integer level = getIntReqParam(request, "level");
//		Integer type = getIntReqParam(request, "type");
//		
//		//根节点
//        EasyUiTree root = new EasyUiTree();
//        JSONArray rootJsonArray = new JSONArray();
//        
//        root.setId(1);
//        root.setText("城市列表");
//        root.setState("open");
//        
//        List<City> cityTreeList = null;
//        if(null == cityCode && null == level && null == type){
//        	cityTreeList = this.cityService.getOpenedCityList();
//        } else {
//        	if(null != cityCode){
//        		cityTreeList = this.cityService.queryCityByCode(String.valueOf(cityCode));
//        	}
//        }
//        
//        
//        
////        if(null != cityCode){
////        	treeList = this.cityService.queryCityByCode(String.valueOf(cityCode));
////        }else{
////        	if(null != level){
////        		if(LEVEL_STATION.equals(level)){
////        			return null;
////        		}else if(LEVEL_CITY.equals(level)){
////        			// 获取根节点下的所有子节点
////                    treeList = this.cityService.getOpenedCityList();
////        		}
////        		
////        	}else{
////        		// 获取根节点下的所有子节点  
////                treeList = this.cityService.getOpenedCityList();
////        	}
////        	
////        }
//        
//        // 遍历子节点下的子节点  
//        if(cityTreeList != null && cityTreeList.size() != 0){  
//            List<EasyUiTree> childrenList = new ArrayList<EasyUiTree>();
//            
//            for (final City t : cityTreeList) {  
////                final String cityCode = t.getCode();
//                List<EasyUiTree> childrenTypeList = new ArrayList<EasyUiTree>();
//                //-------vo
//                EasyUiTree cityNode = new EasyUiTree();
////                children.setId(t.getId());
//                cityNode.setId(Integer.valueOf(t.getCode()));
//                cityNode.setText(t.getName());
//                cityNode.setState("open");
//                cityNode.setAttributes(new HashMap<String, Object>(){
//                	{
//                		put("cityCode", t.getCode());
//                		put("level", LEVEL_CITY);
//                	}
//                });
//                
//                if(null == type || type.equals(0)){
//	                //自有网点列表
//	                List<EasyUiTree> parkList0 = this.parkService.getStaionTreeByCityAndCooperationType(0, t.getCode());
//	                //网点类型
//	                EasyUiTree typeNode0 = new EasyUiTree();
//	                
//	                typeNode0.setId(0);
//	                typeNode0.setText("自有网点");
//	                typeNode0.setState("closed");
//	                typeNode0.setAttributes(new HashMap<String, Object>(){
//	                	{
//	                		put("cityCode", t.getCode());
//	                		put("type", 0);
//	                		put("level", LEVEL_TYPE);
//	                	}
//	                });
//	                if(null != parkList0 && !parkList0.isEmpty()){
//	                	typeNode0.setChildren(parkList0);	                	
//	                }
////	                typeNode0.setAttributes(new HashMap<String, String>(){
////	                	{
////	                		put("url", "get_station_list.do?type=0&cityCode=" + cityCode);
////	                	}
////	                });
//	                //插入网点合作类型
//	                childrenTypeList.add(typeNode0);
//                }
//                if(null == type || type.equals(1)){
//	                //合作网点列表
//	                List<EasyUiTree> parkList1 = this.parkService.getStaionTreeByCityAndCooperationType(1, t.getCode());
//	                EasyUiTree typeNode1 = new EasyUiTree();
//	                typeNode1.setId(1);
//	                typeNode1.setText("合作网点");
//	                typeNode1.setState("closed");
//	                typeNode1.setAttributes(new HashMap<String, Object>(){
//	                	{
//	                		put("cityCode", t.getCode());
//	                		put("type", 1);
//	                		put("level", LEVEL_TYPE);
//	                	}
//	                });
//	                if(null != parkList1 && !parkList1.isEmpty()){
//	                	typeNode1.setChildren(parkList1);	                	
//	                }
////	                typeNode1.setAttributes(new HashMap<String, String>(){
////	                	{
////	                		put("url", "get_station_list.do?type=1&cityCode=" + cityCode);
////	                	}
////	                });
//	                childrenTypeList.add(typeNode1);
//                }
//                
//                
//                //插入网点合作类型子树
//                cityNode.setChildren(childrenTypeList);
//                
//                childrenList.add(cityNode);
//                
//            }
//            
//            root.setChildren(childrenList);
//            
//            rootJsonArray.add(childrenList);
//        } 
////        String treeJson = FastJsonUtils.toJson(root, FastJsonUtils.WRITE_NULL_AS_EMPTY);
////        response.getWriter().print("[" + treeJson + "]");
//        String rstJson = rootJsonArray.toString();
//        rstJson = rstJson.substring(1, rstJson.length() - 1);
//        response.getWriter().print(rstJson);
//		
//		return null;
//	}
	
	/**
	 * 获取有已开通的、网点的城市的列表
	 * 后台只查出扁平数据List<EasyUiTree>，用邻接表模式， EasyUiTree里有上一级的id字段（parentId），用以给JS组装层级树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-21 下午4:45:47
	 */
	@SystemServiceLog(description="城市-充电站列表")
	@RequestMapping(value = "/city_list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getCityList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("城市-充电站列表");
		response.setContentType("text/html;charset=utf-8");
		
		List<EasyUiTree> rstList = new ArrayList<EasyUiTree>();
		
		List<CityVo> cityTreeList = this.cityService.getOpenedCityList();
		
		if(cityTreeList != null && cityTreeList.size() != 0){  
			
			for(final CityVo t : cityTreeList) { 
				final String cityCodeStr = t.getCode();
				final Integer cityCodeInt = Integer.valueOf(cityCodeStr);
				{
					//构建city层
					//城市的 parentId = 0； 
					//id = cityCode 
					EasyUiTree cityNode = new EasyUiTree();
					cityNode.setId(cityCodeInt);
					//网点数 = 自建的 + 合作的
					Integer parkCnt = t.getSelfBuiltParkNum() + t.getCooperationParkNum(); 
					cityNode.setText(t.getName() + "(" + parkCnt + "个网点)");
					cityNode.setParentId(0);
					cityNode.setAttributes(new HashMap<String, Object>(){
	                	{
	                		put("cityCode", cityCodeStr);
	                		put("level", LEVEL_CITY);
	                	}
					});
					rstList.add(cityNode);
				}
				{
					//网点合作类型层: 0 : 自有网点; 1 :合作网点
					//城市下的网点合作类型的 parentId = cityCode； 
					//id = cityCode 与 type字符串的拼接，再转int 
					for(int i = 0; i < 2; ++i){
						EasyUiTree typeNode = new EasyUiTree();
						typeNode.setId(Integer.valueOf(cityCodeStr + i));
						String text = null;
						if(0 == i){
							text = "自有网点(" + t.getSelfBuiltParkNum() + "个)";
						}else{
							text = "合作网点(" + t.getCooperationParkNum() + "个)";
						}
						typeNode.setText(text);
						typeNode.setParentId(cityCodeInt);
						final Integer type = i; 
						typeNode.setAttributes(new HashMap<String, Object>(){
		                	{
		                		put("cityCode", cityCodeStr);
		                		put("type", type);
		                		put("level", LEVEL_TYPE);
		                	}
						});
						rstList.add(typeNode);
					}
				}
				{
					//网点层
					//网点 parentId = 城市下的网点合作类型的id； 
					//id = parkId
					for(int i = 0; i < 2; ++i){
						List<ParkTreeVo> parkList = this.parkService.getStaionTreeByCityAndCooperationType(i, cityCodeStr);
						for(ParkTreeVo park : parkList){
							park.setParentId(Integer.valueOf(cityCodeStr + i));
							Integer equipmentCnt = park.getEquipmentCnt();
							equipmentCnt = (null != equipmentCnt) ? equipmentCnt : 0;
							park.setText(park.getText() + "(" + equipmentCnt + "个桩)");
							final Integer type = i; 
							final Integer stationId = park.getId(); 
							park.setAttributes(new HashMap<String, Object>(){
			                	{
			                		put("cityCode", cityCodeStr);
			                		put("type", type);
			                		put("stationId", stationId);
			                		put("level", LEVEL_STATION);
			                	}
							});
							if(equipmentCnt>0)
								rstList.add(park);
						}
					}
				}
				
			}
		}

		String json = FastJsonUtils.toJson(rstList);
		response.getWriter().print(json);
		
		return null;
	}
	
	/**
	 * 获取网点列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-21 下午4:45:47 
	 */
	@SystemServiceLog(description="获取网点列表")
	@RequestMapping(value = "/get_station_list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getStationList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("获取网点列表");
		response.setContentType("text/html;charset=utf-8");
		
		String cityCode = request.getParameter("cityCode");
		Integer cooperationType = Integer.valueOf(request.getParameter("type"));
		
		List<Park> list = this.parkService.getStaionByCityAndCooperationType(cooperationType, cityCode);
//		List<EasyUiTree> list = this.parkService.getStaionTreeByCityAndCooperationType(cooperationType, cityCode);
		response.getWriter().print(FastJsonUtils.toJson(list, FastJsonUtils.WRITE_NULL_AS_EMPTY));
		return null;
	}
	/**
	 * 充电桩运营报表
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="充电桩运营报表")
	@RequestMapping(value = "/buid_charging_report", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getChargingReport(int page, int rows, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("充电桩运营报表");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");
		
		String cityCode = request.getParameter("cityCode");
		Integer parkId = getIntReqParam(request, "parkId");//网点ID
		Integer cooperationType = getIntReqParam(request, "type");//网点合作类型
		
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");

		//生成充电运营报表，typeAndCnt需要拼接，如 "快充-单枪"，充电使用率chargedFreq需要计算
		List<ChargingReportVo> reportList = chargingOrderInfoService.getChargingReport(
				cooperationType, parkId, cityCode, beginDate, endDate, from, to);
		ChargingReportPartVo chargingReportPartVo = null;
		if (reportList.size() > 0) {
			chargingReportPartVo = new ChargingReportPartVo();
			chargingReportPartVo.setChargingAmountSum(reportList.get(0).getChargingAmountSum().doubleValue());
			chargingReportPartVo.setChargingTimeSum(reportList.get(0).getChargingTimeSum());
			chargingReportPartVo.setOrderMoneySum(reportList.get(0).getOrderMoneySum());
			chargingReportPartVo.setChargedTimesSum(reportList.get(0).getChargedTimesSum());
			chargingReportPartVo.setUseTimeSum(reportList.get(0).getUseTimeSum());
		}
		//所有充电桩的充电次数
//		int totalChargedTimes = 0;
//		for(ChargingReportVo vo : reportList){
//			String cityName = vo.getCityName();
//			vo.setStationName(cityName + "-" + vo.getStationName());
//			
//			//拼接typeAndCnt，如 "快充-单枪"
//			Integer equipmentType = vo.getEquipmentType();
//			Integer connectorCnt = vo.getConnectorCnt();
//			String cnt = null;
//			if(connectorCnt != null && 2 >= connectorCnt){
//				cnt = (connectorCnt == 2 ? "双枪" : "单枪");
//			}else{
//				cnt = connectorCnt + "枪";
//			}
//			String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
//			vo.setTypeAndCnt(typeAndCnt);
//			
//			//计算所有充电桩的使用次数，用于计算使用率chargedFreq
//			totalChargedTimes += vo.getChargedTimes();
//		}
		//查询所有桩的使用次数
		Integer totalChargedTimes = chargingOrderInfoService.getChargingTotolCount(beginDate,endDate);
		//计算每个充电桩的使用频率 = 每个桩的充电次数 / 所有充电桩的充电次数 * 100 %
//		if(0 < totalChargedTimes){
		
			for(ChargingReportVo vo : reportList){
				//拼接typeAndCnt，如 "快充-单枪"
				Integer equipmentType = vo.getEquipmentType();
				Integer connectorCnt = vo.getConnectorCnt();
				String cnt = null;
				if(connectorCnt != null && 2 >= connectorCnt){
					cnt = (connectorCnt == 2 ? "双枪" : "单枪");
				}else{
					cnt = connectorCnt + "枪";
				}
				String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
				vo.setTypeAndCnt(typeAndCnt);
				long freq = Math.round(vo.getChargedTimes().doubleValue() * 100 /totalChargedTimes.doubleValue() );//百分之几
				vo.setChargedFreq((int) freq);
			}
//		}
		
		int totalRecords = chargingOrderInfoService.getReportCount(
				cooperationType, parkId, cityCode, beginDate, endDate, from, to);
		String json = Data2Jsp.Json2Jsp(reportList, totalRecords, chargingReportPartVo);
		response.getWriter().print(json);
		return null;
	}

	private List<Map<String, Object>> createChargingReportExcelRecord(List<ChargingReportVo> reports){

		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "充电运营报表");
		listmap.add(map);
		ChargingReportVo report = null;
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("stationName",report.getStationName()==null?"":report.getStationName());
			mapValue.put("equipmentCode", report.getEquipmentCode()==null?"":report.getEquipmentCode());
			mapValue.put("typeAndCnt", report.getTypeAndCnt() == null?"":report.getTypeAndCnt());
			mapValue.put("chargedTimes", report.getChargedTimes() == null?"":report.getChargedTimes());
			mapValue.put("useTime", report.getUseTime() == null ? "" : Math.floor(report.getUseTime()/60));
			mapValue.put("chargedFreq", report.getChargedFreq() == null?"":report.getChargedFreq() );
			mapValue.put("chargedQuantity", report.getChargedQuantity() == null ? "":report.getChargedQuantity());
			mapValue.put("totalChargingTime", report.getTotalChargingTime() ==null ? "": report.getTotalChargingTime());
			mapValue.put("fee", report.getFee()== null?"":report.getFee()/100.0);
			listmap.add(mapValue);
		}
		return listmap;
	
	}
	
	@SystemServiceLog(description = "充电运营报表导出")
	@RequestMapping(value = "/export_buid_charging_report_excel", method = {RequestMethod.GET, RequestMethod.POST })
	public String export_buid_charging_report_excel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	//	response.setContentType("text/html;charset=utf-8");
		String cityCode = request.getParameter("cityCode");
		Integer parkId = getIntReqParam(request, "parkId");//网点ID
		Integer cooperationType = getIntReqParam(request, "type");//网点合作类型
		
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");

		//生成充电运营报表，typeAndCnt需要拼接，如 "快充-单枪"，充电使用率chargedFreq需要计算
//		List<ChargingReportVo> reportList = chargingOrderInfoService.getAllChargingReport(cooperationType, parkId, cityCode, beginDate, endDate);
		List<ChargingReportVo> reportList = chargingOrderInfoService.getChargingReport(
				cooperationType, parkId, cityCode, beginDate, endDate, null, null);
		
		Integer totalChargedTimes = chargingOrderInfoService.getChargingTotolCount(beginDate,endDate);
		for(ChargingReportVo vo : reportList){
				//拼接typeAndCnt，如 "快充-单枪"
				Integer equipmentType = vo.getEquipmentType();
				Integer connectorCnt = vo.getConnectorCnt();
				String cnt = null;
				if(connectorCnt != null && 2 >= connectorCnt){
					cnt = (connectorCnt == 2 ? "双枪" : "单枪");
				}else{
					cnt = connectorCnt + "枪";
				}
				String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
				vo.setTypeAndCnt(typeAndCnt);
				long freq = Math.round(vo.getChargedTimes().doubleValue() * 100 /totalChargedTimes.doubleValue() );//百分之几
				vo.setChargedFreq((int) freq);
		}
		if (reportList.size() > 0) {
			ChargingReportVo reportvo = new ChargingReportVo();
			reportvo.setStationName("总合计");
			reportvo.setChargedTimes(reportList.get(0).getChargedTimesSum());
			reportvo.setUseTime(reportList.get(0).getUseTimeSum());
			reportvo.setChargedQuantity(reportList.get(0).getChargingAmountSum());
			reportvo.setTotalChargingTime(reportList.get(0).getChargingTimeSum());
			reportvo.setFee(reportList.get(0).getOrderMoneySum());
			reportList.add(reportvo);
		}
		List<Map<String, Object>> list = createChargingReportExcelRecord(reportList);
		String columnNames[] = { "网点", "充电桩编码", "桩类型", "充电次数", "使用时间", "充电使用率","充电电量", "充电时长","订单金额"};// 列名
		String keys[] = { "stationName", "equipmentCode", "typeAndCnt", "chargedTimes","useTime", "chargedFreq", "chargedQuantity","totalChargingTime","fee"};// map中的key
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
				+ new String(("充电运营报表.xls").getBytes(), "iso-8859-1"));
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
			e.printStackTrace();
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}
	/**
	 * 充电桩充电明细表
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午1:51:20
	 */
	@SystemServiceLog(description="充电桩充电明细表")
	@RequestMapping(value = "/get_charging_detail_report", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getChargingDetailReport(int page, int rows, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("充电桩充电明细表 ");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");
		
		Integer equipmentId = getIntReqParam(request, "equipmentId");
		String cityCode = request.getParameter("cityCode");
		Integer parkId = getIntReqParam(request, "parkId");//网点ID
		Integer cooperationType = getIntReqParam(request, "type");//网点合作类型
		
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");
		List<ChargingDetailVo> list = this.chargingOrderInfoService.getChargingDetailReport(
				equipmentId, cooperationType, parkId, cityCode, beginDate, endDate, from, to);
		//取出总合计的所有数据，并封装到ChargingOrderInfoVo
		ChargingOrderInfoVo infoVo = null;
		if (list.size() > 0) {
			infoVo = new ChargingOrderInfoVo();
			infoVo.setChargingAmountSum(list.get(0).getChargingAmountSum());
			infoVo.setChargingTimeSum(list.get(0).getChargingTimeSum());
			infoVo.setCouponValueSum(list.get(0).getCouponValueSum());
			infoVo.setOrderMoneySum(list.get(0).getOrderMoneySum());
			infoVo.setPayMoneySum(list.get(0).getPayMoneySum());
		}
		for(ChargingDetailVo vo : list){
			String cityName = vo.getCityName();
			vo.setStationName(cityName + "-" + vo.getStationName());
			
			//拼接typeAndCnt，如 "快充-单枪"
			Integer equipmentType = vo.getEquipmentType();
			Integer connectorCnt = vo.getConnectorCnt();
			String cnt = null;
			if(2 >= connectorCnt){
				cnt = (connectorCnt == 2 ? "双枪" : "单枪");
			}else{
				cnt = connectorCnt + "枪";
			}
			String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
			vo.setTypeAndCnt(typeAndCnt);
			Integer payMoney = vo.getPayMoney();
			// 根据充电订单实际支付金额判断该订单是否免单
			if(null!= payMoney && 0 < payMoney){
				vo.setIsFreeOrder("N");
				vo.setFreeReason("无");
			}else {
				vo.setIsFreeOrder("Y");
			    if(null!=vo.getChargingStatus()&&"cancel".equals(vo.getChargingStatus())){
			    	vo.setFreeReason("订单已取消");
			    }else {
					if(null!=vo.getMemberStatus()&&"useCar".equals(vo.getMemberStatus())){
						vo.setFreeReason("会员用车充电");
					}else {
						vo.setFreeReason("会员还车充电");
					}
				}

			}
		}
		int totalRecords = this.chargingOrderInfoService.getChargingDetailReportCount(
				equipmentId, cooperationType, parkId, cityCode, beginDate, endDate);
		String json = Data2Jsp.Json2Jsp(list, totalRecords, infoVo);
		System.out.println(json);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="充电桩充电明细表导出")
	@RequestMapping(value = "/export_charging_detail", method = { RequestMethod.GET, RequestMethod.POST })
	public List exportChargingDetail( HttpServletRequest request,HttpServletResponse response ) throws Exception {
		String fileName = "chargingDetailReport" ;
		//列名充电桩编码	
		String columnNames [] = { "网点名称", "充电桩编码",  "桩类型", "车位号", "使用者", "使用者身份证", 
				"手机号码","充电电量","充电时长","订单金额（元/小时）","支付金额（元/小时）","优惠券","是否免单","免单原因"};
		
		String keys[] = { "stationName", "equipmentCode", "typeAndCnt","carport", "memberName", "memberIdcard","memberPhone",
				"chargedQuantity","totalChargingTime","fee","payMoney","couponAmount","isFreeOrder","freeReason"};
		Integer from = null;
		Integer to = null;
		Integer equipmentId = getIntReqParam(request, "equipmentId");
		String cityCode = request.getParameter("cityCode");
		Integer parkId = getIntReqParam(request, "parkId");//网点ID
		Integer cooperationType = getIntReqParam(request, "type");//网点合作类型
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");
		List<ChargingDetailVo> list = this.chargingOrderInfoService.getChargingDetailReport(
				equipmentId, cooperationType, parkId, cityCode, beginDate, endDate, from, to);
		//取出总合计的所有数据，并封装到ChargingOrderInfoVo
		ChargingOrderInfoVo infoVo = null;
		if (list.size() > 0) {
			infoVo = new ChargingOrderInfoVo();
			infoVo.setChargingAmountSum(list.get(0).getChargingAmountSum());
			infoVo.setChargingTimeSum(list.get(0).getChargingTimeSum());
			infoVo.setCouponValueSum(list.get(0).getCouponValueSum());
			infoVo.setOrderMoneySum(list.get(0).getOrderMoneySum());
			infoVo.setPayMoneySum(list.get(0).getPayMoneySum());
		}
		//数据总合计导出execl
		String totalSum  [] = { 
				"", "",  "", "", "", "", "",
				String.valueOf(infoVo.getChargingAmountSum()),
				String.valueOf(infoVo.getChargingTimeSum()),
				String.valueOf(infoVo.getOrderMoneySum()==null?"0.00":infoVo.getOrderMoneySum()/100.0),
				String.valueOf(infoVo.getPayMoneySum()/100.0),
				String.valueOf(infoVo.getCouponValueSum()==null?"0.00":infoVo.getCouponValueSum()),
				"",""};
		for(ChargingDetailVo vo : list){
			String cityName = vo.getCityName();
			vo.setStationName(cityName + "-" + vo.getStationName());
			
			//拼接typeAndCnt，如 "快充-单枪"
			Integer equipmentType = vo.getEquipmentType();
			Integer connectorCnt = vo.getConnectorCnt();
			String cnt = null;
			if(2 >= connectorCnt){
				cnt = (connectorCnt == 2 ? "双枪" : "单枪");
			}else{
				cnt = connectorCnt + "枪";
			}
			String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
			vo.setTypeAndCnt(typeAndCnt);
			Integer payMoney = vo.getPayMoney();
			// 根据充电订单实际支付金额判断该订单是否免单
			if(null!= payMoney && 0 < payMoney){
				vo.setIsFreeOrder("N");
				vo.setFreeReason("无");
			}else {
				vo.setIsFreeOrder("Y");
			    if(null!=vo.getChargingStatus()&&"cancel".equals(vo.getChargingStatus())){
			    	vo.setFreeReason("订单已取消");
			    }else {
					if(null!=vo.getMemberStatus()&&"useCar".equals(vo.getMemberStatus())){
						vo.setFreeReason("会员用车充电");
					}else {
						vo.setFreeReason("会员还车充电");
					}
				}

			}
		}
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "充电明细报表");
		data.add(sheetNameMap);
		data.addAll(createData(list));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook2(data, keys, columnNames,totalSum).write(os);
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
		return null ;
	}
	
	private List<Map<String, Object>> createData(
			List<ChargingDetailVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "stationName", "equipmentCode", "typeAndCnt","carport", "memberName", "memberIdcard","memberPhone",
				"chargedQuantity","totalChargingTime","fee","payMoney","couponAmount","isFreeOrder","freeReason"};
		for (ChargingDetailVo vo : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], vo.getStationName());
			map.put(keys[1], vo.getEquipmentCode());
			map.put(keys[2], vo.getTypeAndCnt());
			map.put(keys[3], vo.getCarport());
			map.put(keys[4], vo.getMemberName());
			map.put(keys[5], vo.getMemberIdcard());
			map.put(keys[6], vo.getMemberPhone());
			map.put(keys[7], vo.getChargedQuantity()==null?0:vo.getChargedQuantity());
			map.put(keys[8], vo.getTotalChargingTime());
			map.put(keys[9], vo.getFee()==null?"0.00":(vo.getFee()/100.0));
			map.put(keys[10], vo.getPayMoney()==null?"0.00":(vo.getPayMoney()/100.0));
			map.put(keys[11], vo.getCouponAmount()==null?"0.00":Math.floor(vo.getCouponAmount()/100.0));
			if (StringUtils.isNotBlank(vo.getIsFreeOrder())) {
				map.put(keys[12], vo.getIsFreeOrder()=="Y"?"是":"否");
			}
			map.put(keys[13], vo.getFreeReason());
			
			list.add(map);
		}
		return list;
	}
	/**
	 * 充电桩收入报表  【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【充电电量】【价格】（两个小项：【物业电价】、【会员电价】）【计费金额（元）】【充电收入】
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午2:43:14
	 */
	@SystemServiceLog(description="充电桩收入报表")
	@RequestMapping(value = "/get_income_report", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getIncomeDetailReport(int page, int rows, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("充电桩收入报表");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");
		
		Integer equipmentId = getIntReqParam(request, "equipmentId");
		String cityCode = request.getParameter("cityCode");
		String invoiceStatus = request.getParameter("invoiceStatus");
		Integer parkId = getIntReqParam(request, "parkId");//网点ID
		Integer cooperationType = getIntReqParam(request, "type");//网点合作类型
		
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");
		Timestamp payBeginTime = getTimestampReqParam(request, "payBeginTime");
        Timestamp payEndTime = getTimestampReqParam(request, "payEndTime");
        List<ChargingIncomeVo> list = this.chargingOrderInfoService.getIncomeDetailReport(
				equipmentId, cooperationType, parkId, cityCode, beginDate, endDate,payBeginTime,payEndTime, from, to, invoiceStatus);
		ChargingIncomePartVo incomePartVo = null;
		if (list.size() > 0) {
			incomePartVo = new ChargingIncomePartVo();
			incomePartVo.setChargingAmountSum(list.get(0).getChargingAmountSum());
			incomePartVo.setFeeSum(list.get(0).getFeeSum());
			incomePartVo.setIncomeSum(list.get(0).getIncomeSum());
			incomePartVo.setTotalPriceSum(list.get(0).getTotalPriceSum());
			incomePartVo.setInvoiceMoneySum(list.get(0).getInvoiceMoneySum());
		}
		// 获取公司营运车辆
		for(ChargingIncomeVo vo : list){
			String cityName = vo.getCityName();
			vo.setStationName(cityName + "-" + vo.getStationName());
			
			//拼接typeAndCnt，如 "快充-单枪"
			Integer equipmentType = vo.getEquipmentType();
			Integer connectorCnt = vo.getConnectorCnt();
			String cnt = null;
			if(2 >= connectorCnt){
				cnt = (connectorCnt == 2 ? "双枪" : "单枪");
			}else{
				cnt = connectorCnt + "枪";
			}
			String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
			vo.setTypeAndCnt(typeAndCnt);
			Integer payMoney = vo.getFee();
			if(null!=payMoney && 0< payMoney){
				vo.setIsFreeOrder("N");
				vo.setFreeReason("无");
			}else {
				vo.setIsFreeOrder("Y");
				
                if(null!=vo.getChargingStatus()&&"cancel".endsWith(vo.getChargingStatus())){
                	vo.setFreeReason("订单已取消");
                }else {
					if(null!=vo.getUserType() && "employee".equals(vo.getUserType())){
						vo.setFreeReason("员工端免单");
					} else {
						if(null!=vo.getMemberStatus()&&"useCar".equals(vo.getMemberStatus())){
							vo.setFreeReason("会员用车充电");
						}
						else {
							vo.setFreeReason("会员还车充电");
						}
					}
				}
			}
		}
		
		int totalRecords = this.chargingOrderInfoService.getIncomeDetailReportCount(
				equipmentId, cooperationType, parkId, cityCode, beginDate, endDate,payBeginTime,payEndTime, invoiceStatus);
		String json = Data2Jsp.Json2Jsp(list, totalRecords,incomePartVo);
		response.getWriter().print(json);
		return null;
	}

	@Autowired
	private ChargingOrderInfoMapper charge;
	
	@SystemServiceLog(description="充电桩收入报表数据导出")
	@RequestMapping(value = "/export_chargeInCome_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String invoiceStatus,String type,String parkId,String equipmentId,String begin,String end,
			String payBeginTime ,String payEndTime,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "chargeInComeReport" ;
		//列名充电桩编码	
		String columnNames [] = {"网点名", "充电桩编码",  "桩类型", "车牌号", "车位号","充电开始时间","支付时间","充电电量",
				"价格","计费金额（元）","充电收入（元）","开票金额（元）","是否免单","免单原因" };
		
		String keys[] = { "stationName", "equipmentCode","typeAndCnt", "lpn",
				"carport","startTime","payTime", "chargedQuantity", "totalPrice", "fee",
				"payMoney","invoiceMoney","isFreeOrder","freeReason"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(parkId)){
			paramMap.put("parkId", Integer.valueOf(parkId));
		}
		paramMap.put("cityCode", cityCode);
		if (StringUtils.isNotBlank(equipmentId)) {
			paramMap.put("equipmentId", Integer.valueOf(equipmentId));
		}
		paramMap.put("invoiceStatus", invoiceStatus);
		if(StringUtils.isNotBlank(begin)){
			paramMap.put("begin",DateTime.getDateTime(begin) );
		}
		if(StringUtils.isNotBlank(end)){
			paramMap.put("end",DateTime.getDateTime(end) );
		}
        if (StringUtils.isNotBlank(payBeginTime)) {
            paramMap.put("payBeginTime", DateTime.getDateTime(payBeginTime));
        }
        if (StringUtils.isNotBlank(payEndTime)) {
            paramMap.put("payEndTime", DateTime.getDateTime(payEndTime));
        }

        paramMap.put("cooperationType",type );
		paramMap.put("from",null );
		paramMap.put("to",null );
		List<ChargingIncomeVo> datas = charge.getIncomeDetailReport(paramMap);
		ChargingIncomePartVo incomePartVo = null;
		if (datas.size() > 0) {
			incomePartVo = new ChargingIncomePartVo();
			incomePartVo.setChargingAmountSum(datas.get(0).getChargingAmountSum());
			incomePartVo.setFeeSum(datas.get(0).getFeeSum());
			incomePartVo.setIncomeSum(datas.get(0).getIncomeSum());
			incomePartVo.setTotalPriceSum(datas.get(0).getTotalPriceSum());
			incomePartVo.setInvoiceMoneySum(datas.get(0).getInvoiceMoneySum());
		}
		// 添加总合计到execl
		double price = incomePartVo.getTotalPriceSum()/100.0;
		double fee = incomePartVo.getFeeSum()/100.0;
		DecimalFormat format = new DecimalFormat("#0.00");
		String totalSum [] = {"", "",  "", "", "","","", incomePartVo.getChargingAmountSum().toString(),
				String.valueOf(format.format(price)),String.valueOf(format.format(fee)),
				incomePartVo.getIncomeSum()==null?"0.00":String.valueOf(incomePartVo.getIncomeSum()/100.0),
						String.valueOf(format.format(incomePartVo.getInvoiceMoneySum()==null?0:incomePartVo.getInvoiceMoneySum()/100.0)),"","" };
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "充电收入数据报表");
		list.add(sheetNameMap);
		list.addAll(createData2(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook2(list, keys, columnNames,totalSum).write(os);
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
			List<ChargingIncomeVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "stationName", "equipmentCode","typeAndCnt", "lpn",
				"carport","startTime","payTime", "chargedQuantity", "totalPrice", "fee",
				"payMoney","invoiceMoney","isFreeOrder","freeReason"};
		for (ChargingIncomeVo vo : data) {
			map = new HashMap<String, Object>();
			Integer payMoney = vo.getFee();
			if(null!=payMoney && 0< payMoney){
				vo.setIsFreeOrder("N");
				vo.setFreeReason("无");
			}else {
				vo.setIsFreeOrder("Y");
                if(null!=vo.getChargingStatus()&&"cancel".endsWith(vo.getChargingStatus())){
                	vo.setFreeReason("订单已取消");
                }else {
					if(null==vo.getUserType()){
						vo.setFreeReason("员工端充电");
					}
					else {
						if(null!=vo.getMemberStatus()&&"useCar".equals(vo.getMemberStatus())){
							vo.setFreeReason("会员用车充电");
						}
						else {
							vo.setFreeReason("会员还车充电");
						}
					}
				}
			}
			map.put(keys[0], vo.getCityName()+"-"+vo.getStationName());
			map.put(keys[1], vo.getEquipmentCode());
			//拼接typeAndCnt，如 "快充-单枪"
			Integer equipmentType = vo.getEquipmentType();
			Integer connectorCnt = vo.getConnectorCnt();
			String cnt = null;
			if(2 >= connectorCnt){
				cnt = (connectorCnt == 2 ? "双枪" : "单枪");
			}else{
				cnt = connectorCnt + "枪";
			}
			String typeAndCnt = EquipmentTypeEnum.getType(equipmentType) + "-" + cnt;
			map.put(keys[2], typeAndCnt);
			map.put(keys[3], vo.getLpn());
			map.put(keys[4], vo.getCarport());
			map.put(keys[5], new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getStartTime()));
			map.put(keys[6], new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getPayTime()));
			map.put(keys[7], vo.getChargedQuantity());
			map.put(keys[8], vo.getTotalPrice()!=null?new DecimalFormat("0.00").format(vo.getTotalPrice()/100.0):0.00);
			map.put(keys[9], vo.getFee()!=null?new DecimalFormat("0.00").format(vo.getFee()/100.0):0.00);
			map.put(keys[10], vo.getPayMoney()!=null?new DecimalFormat("0.00").format(vo.getPayMoney()/100.0):0.00);
			map.put(keys[11], vo.getInvoiceMoney()!=null?new DecimalFormat("0.00").format(vo.getInvoiceMoney()/100.0):0.00);
			if(vo.getIsFreeOrder() != null){
				if(vo.getIsFreeOrder().equals("Y")){
					map.put(keys[12], "是");
				}else{
					map.put(keys[12], "否");
				}
			}
			map.put(keys[13], vo.getFreeReason());
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 用电量报表
	 * 按城市、网点（模糊查询）、类型、桩类型、日期区间查询
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-24 下午7:24:15
	 */
	@SystemServiceLog(description="用电量报表")
	@RequestMapping(value = "/get_consumption_report", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getChargingConsumptionReport(int page, int rows, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("用电量报表");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");
		
		String cityCode = request.getParameter("cityCode");
		String stationName = request.getParameter("stationName");
		Integer equipmentType = getIntReqParam(request, "equipmentType");//桩类型
		Integer cooperationType = getIntReqParam(request, "cooperationType");//网点合作类型
		
		Timestamp beginDate = getTimestampReqParam(request, "begin");
		Timestamp endDate = getTimestampReqParam(request, "end");
		
		List<ChargingConsumptionVo> list = this.chargingOrderInfoService.getChargingConsumptionReport(
				cooperationType, equipmentType, cityCode, stationName, beginDate, endDate, from, to);
		int chargMount = 0;
		Map<String, ChargingConsumptionVo> map = new HashMap<String, ChargingConsumptionVo>();
		for(ChargingConsumptionVo vo : list){
			ChargingConsumptionVo chaConsumptionVo = map.get(vo.getStationName());
			if (null!=chaConsumptionVo) {					
			chargMount = (null==chaConsumptionVo.getChargedQuantity()?0:chaConsumptionVo.getChargedQuantity())+
					(null!=vo.getChargedQuantity()?vo.getChargedQuantity():0);
			chaConsumptionVo.setChargedQuantity(chargMount);
			}else{
				map.put(vo.getStationName(), vo);
			}
		}
        list = new ArrayList<ChargingConsumptionVo>();
		for (Entry<String, ChargingConsumptionVo> entry : map.entrySet()) {
			ChargingConsumptionVo cha = entry.getValue();
			String equipmentCnt = null;
			int allPileCnt = 0;
			int fastPileCnt =cha.getFastPileCnt();
			int slowPileCnt = cha.getSlowPileCnt();
			int combinedPileCnt = cha.getCombinedPileCnt();
			if(null != equipmentType){
				EquipmentTypeEnum typeEnum = EquipmentTypeEnum.getByNo(equipmentType);
				switch(typeEnum){
					case FAST:
						allPileCnt = cha.getFastPileCnt();
						break;
					case SLOW:
						allPileCnt = cha.getSlowPileCnt();
						break;
					case AC_DC_COMBINED:
						allPileCnt = cha.getCombinedPileCnt();
						break;
					default:break;
				}
				equipmentCnt = EquipmentTypeEnum.getType(equipmentType) + allPileCnt + "个";
			}else{
				StringBuilder str = new StringBuilder();
				str.append("快充" + (fastPileCnt >0 ? fastPileCnt + "个" : "0个"));
				str.append("<br />慢充" + (slowPileCnt >0 ? slowPileCnt + "个" : "0个"));
				str.append("<br />交直流一体" + (combinedPileCnt >0 ? combinedPileCnt + "个" : "0个"));
				equipmentCnt = str.toString();
			}
			cha.setEquipmentCnt(equipmentCnt);
			list.add(cha);
		}
		
		int totalRecords = this.chargingOrderInfoService.getChargingConsumptionReportCount(
				cooperationType, equipmentType, cityCode, stationName, beginDate, endDate, from, to);
		String json = Data2Jsp.Json2Jsp(list, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	/**
	 * 获取一个充电桩的充电明细表 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-11-19 下午5:54:07
	 */
	@SystemServiceLog(description="获取一个充电桩的充电明细表 ")
	@RequestMapping(value = "/get_one_pile_charging_detail", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getOnePileChargingDetail(int page, int rows, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("获取一个充电桩的充电明细表 ");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");
		
		Integer equipmentId = getIntReqParam(request, "equipmentId");
		List<ChargingDetailVo> list = this.chargingOrderInfoService.getOnePileChargingDetail(equipmentId, from, to);
		
		int totalRecords = this.chargingOrderInfoService.getOnePileChargingDetailCount(equipmentId);

		String json = Data2Jsp.Json2Jsp(list, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	/**
	 * 构建树
	 * @param reportList
	 * @return
	 * @author ouxx
	 * @date 2016-11-17 下午3:11:39
	 */
	private String createTreeJson(List<ChargingReportVo> reportList) {
		JSONArray rootArray = new JSONArray();
		for(ChargingReportVo city : reportList){
			if(city.getCityPid() == null){
				JSONObject rootObj = createBranch(reportList, city);
				rootArray.add(rootObj);
			}
		}

		return rootArray.toString();
	}
	
	/**
	 * 构建分支
	 * @param list
	 * @param currentNode
	 * @return
	 * @author ouxx
	 * @date 2016-11-17 下午3:11:48
	 */
	private JSONObject createBranch(List<ChargingReportVo> list, ChargingReportVo currentNode) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject currentObj = JSONObject.fromObject(currentNode, jsonConfig);
		JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该
		 * 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			ChargingReportVo newNode = list.get(i);
			if(newNode.getCityPid() != null
					&& newNode.getCityPid().compareTo(currentNode.getCityId()) == 0){
				JSONObject childObj = createBranch(list, newNode);
				childArray.add(childObj);
			}
		}

		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}

		return currentObj;
	}
}
