package com.iber.portal.controller.monitorCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.SysConstant;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.charging.ChargingPileService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.vo.city.CityVo;
import com.iber.portal.vo.pile.PileTotalVo;

@Controller
public class PileMonitorController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
    private ChargingPileService chargingPileService ;
	
	@Autowired
    private ParkService parkService ;
	
	@Autowired
    private CityService cityService ;
	
	/**
	 * @describe 网点监控
	 * 
	 */
	@SystemServiceLog(description="网点监控")
	@RequestMapping(value = "/pile_monitoring", method = { RequestMethod.GET })
	public String parkMonitoring(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("当前订单页面跳转") ;
		return "monitorCenter/pileMonitorCenter";		
	}
	 
	/**获取网点列表*/
	@SystemServiceLog(description="获取网点列表")
	@RequestMapping(value = "/pile_list_data", method = { RequestMethod.GET, RequestMethod.POST })
	public String parkListCode(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String cityCode = null ;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(!user.getCityCode().equals("00")){
			cityCode = user.getCityCode() ;
		}
		map.put("cityCode",cityCode);
		List<Park> parkList = parkService.queryParkByCodeAndType(map);
		JSONArray json = JSONArray.fromObject(parkList);
		response.getWriter().print(json.toString());
		return null;
	}
	
	/**
	 * @describe 获取网点充电桩列表
	 * 
	 */
	@SystemServiceLog(description="获取网点充电桩列表")
	@RequestMapping(value = "/query_pile_park_id", method = { RequestMethod.GET , RequestMethod.POST})
	public String query_pile_park_id(Integer parkId ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		Map<String, Object> paramMap = new HashMap<String, Object>() ;
		paramMap.put("parkId", parkId) ;
		List<ChargingPile> pileList = chargingPileService.queryPileByParkId(paramMap) ;
		paramMap.put("status", SysConstant.PILE_STATUS_EMPTY) ;
		
		paramMap.put("chargingType", SysConstant.PILE_TYPE_FAST) ; //快充
		List<ChargingPile> fastPileList = chargingPileService.queryPileByParkId(paramMap) ;
		
		paramMap.put("chargingType", SysConstant.PILE_TYPE_SLOW) ;//慢充
		List<ChargingPile> slowPileList = chargingPileService.queryPileByParkId(paramMap) ;
		
		Map<String,Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("fastNum", fastPileList.size()) ;
		resultMap.put("slowNum", slowPileList.size()) ;
		resultMap.put("totalNum", pileList.size()) ;
		resultMap.put("pileList", pileList) ;
		response.getWriter().print(Data2Jsp.mapToJson(resultMap));
		return null;		
	}
	
	/**
	 * @describe 结束充电
	 */
	@SystemServiceLog(description="结束充电")
	@RequestMapping(value = "/end_charging", method = { RequestMethod.GET , RequestMethod.POST })
	public String end_charging(Integer pileId , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		response.getWriter().print("succ");
		return null ;
	}
	
	/**
	 * 区域充电桩统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="区域充电桩统计")
	@RequestMapping(value = "/park_list_pile_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String park_list_statistics(String cityCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		Map<String, Object> paramMap = new HashMap<String, Object>() ;
		Map<String, Object> map = new HashMap<String, Object>() ;
		String cityName = "全国" ;
		if(cityCode.equals("00")){
			cityCode = null ;
		}else{
			cityName = cityService.queryCityByCode(cityCode).get(0).getName() ;
		}
		paramMap.put("cityCode",cityCode);
		
		List<ChargingPile> pileList = null ; //
		if(cityCode != null && cityCode.length() > 4){
			if(cityCode.substring(4).equals("00")){ //市级
				pileList = chargingPileService.queryPileByAreaCode(paramMap) ;
			}else{ //区级
				map.put("areaCode", cityCode) ;
				pileList = chargingPileService.queryPileStatusByAreaCode(map) ;
			}
		}else{
			pileList = chargingPileService.queryPileByAreaCode(paramMap) ;
		}
		paramMap.put("chargingType", SysConstant.PILE_TYPE_FAST) ; //快充
		List<ChargingPile> pileFastList = chargingPileService.queryPileByAreaCode(paramMap) ;
		
		if(cityCode != null && cityCode.length() > 4 && !cityCode.substring(4).equals("00")){ //区级
			map.put("chargingType", SysConstant.PILE_TYPE_FAST) ;
			pileFastList = chargingPileService.queryPileStatusByAreaCode(map) ;
		}
		
		Integer pileTotal = pileList.size() ; //总数
		Integer pileFastTotal = pileFastList.size() ;//快充
		
		Integer pileSlowTotal = pileList.size() - pileFastList.size() ;//慢充
		
		PileTotalVo parkVo = new PileTotalVo(cityName,pileTotal,pileFastTotal,pileSlowTotal) ;
		response.getWriter().print(Data2Jsp.mapToJson(parkVo));
		return null;		
	}
	
	/**
	 * 地图充电站统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="地图充电站统计")
	@RequestMapping(value = "/map_pile_list_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String map_park_list_statistics(String cityCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		//通过城市ID查询城市网点
		List<CityVo> data = cityService.queryCityPileParkNum() ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
}

