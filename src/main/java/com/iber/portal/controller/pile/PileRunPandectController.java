package com.iber.portal.controller.pile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.pile.PileStatisticsService;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.pile.PileContDetailVo;
import com.iber.portal.vo.pile.PileCountDataVo;
import com.iber.portal.vo.pile.PileStatisticsVo;


@Controller
public class PileRunPandectController extends MainController{
	
	private final static Logger log= Logger.getLogger(PileRunPandectController.class);
	@Autowired
	private CityService cityService ;
	
	@Autowired
	private PileStatisticsService pileStatisticsService ;
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="充电桩统计页面")
	@RequestMapping("/PileRunPandect_page") 
	public String memberRights_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("充电桩统计页面");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = user.getCityCode();
		String cityName = cityService.queryNameByCode(cityCode);
	    HttpSession session = request.getSession();  
	    session.setAttribute("cityCode",cityCode);  
	    if(cityName !=null && !("".equals(cityName))){
	    	session.setAttribute("cityName",cityName);
	    }
		return "pile/pileRunPandect" ;
	}
	
	@SystemServiceLog(description="根据cityCode获取桩的数量")
	@RequestMapping("/pile_nums_statistics") 
	public String getPileNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获取桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getPileNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}  
	@SystemServiceLog(description="获取各省份的充电桩数")
	@RequestMapping("/province_pileTotal_statistics") 
	public String getProvincePileNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获取桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getProvincePileNums(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}  
	
	@SystemServiceLog(description="获取各市的充电桩数")
	@RequestMapping("/city_pileTotal_statistics") 
	public String getCityPileNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获城市桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getCityPileNums(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	} 
	
	@SystemServiceLog(description="获取各区县的充电桩数")
	@RequestMapping("/area_pileTotal_statistics") 
	public String getAreaPileNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获区县桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getAreaPileNums(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据桩类型划分")
	@RequestMapping("/city_pileType_statistics") 
	public String getCityPileType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、桩类型划分的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getCityPileType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据省份桩类型划分")
	@RequestMapping("/province_pileType_statistics") 
	public String getProvincePileType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、桩类型划分的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getProvincePileType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据桩服务类型划分")
	@RequestMapping("/city_pileServiceType_statistics") 
	public String getCityPileServiceType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、桩服务类型的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getCityPileServiceType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据省份桩服务类型划分")
	@RequestMapping("/province_pileServiceType_statistics") 
	public String getProvincePileServiceType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、桩服务类型的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getProvincePileServiceType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	
	@SystemServiceLog(description="根据枪类型划分")
	@RequestMapping("/city_conncetorType_statistics") 
	public String getCityConncetorType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、枪类型划分的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getCityConncetorType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据省份枪类型划分")
	@RequestMapping("/province_conncetorType_statistics") 
	public String getProvinceConncetorType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、枪类型划分的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getProvinceConncetorType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据枪状态划分")
	@RequestMapping("/city_conncetorStatusType_statistics") 
	public String getCityConncetorStatusType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、枪状态划分的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getCityConncetorStatusType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据省份枪状态划分")
	@RequestMapping("/province_conncetorStatusType_statistics") 
	public String getProvinceConncetorStatusType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode、枪状态划分的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getProvinceConncetorStatusType(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="统计今日/昨日，本月/上月,累计的充电桩数据")
	@RequestMapping("/queryPileCountDatas")
	public void queryPileCountDatas(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		Map<String,Object> paramMap = new HashMap<String, Object>() ;
		String countType = request.getParameter("countType");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		String level = request.getParameter("layer");
		String memberType = request.getParameter("memberType");
		String memberLevel = request.getParameter("memberLevel");
		String returnCar = request.getParameter("returnCar");
		paramMap.put("cityCode",cityCode);
		paramMap.put("level",level);
		if(StringUtils.isNotBlank(memberType)){
			paramMap.put("memberType", memberType);
		}
		if(StringUtils.isNotBlank(memberLevel)){
			paramMap.put("memberLevel", memberLevel);
		}
		if(StringUtils.isNotBlank(returnCar)){
			paramMap.put("returnCar", returnCar);
		}
		List<PileCountDataVo> pileCountDataVo=null;
		if("1".equals(countType)){
			pileCountDataVo =pileStatisticsService.getAllChargingTimesData(paramMap);
		}else if("2".equals(countType)){
			pileCountDataVo =pileStatisticsService.getAllChargingPersonData(paramMap);
		}else if("3".equals(countType)){
			pileCountDataVo =pileStatisticsService.getAllChargingKWHData(paramMap);
		}else if("4".equals(countType)){
			pileCountDataVo =pileStatisticsService.getAllChargingIncomeData(paramMap);	
		}
		response.getWriter().print(Data2Jsp.listToJson(pileCountDataVo));
	}
	
	@SystemServiceLog(description="统计今日/昨日，本月/上月,累计的会员的详细数据信息")
	@RequestMapping("/queryPileCountDetail")
	public void queryPileCountDetail(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		Map<String,Object> paramMap = new HashMap<String, Object>() ;
		String countType = request.getParameter("countType");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		String level = request.getParameter("layer");
		String memberType = request.getParameter("memberType");
		String memberLevel = request.getParameter("memberLevel");
		String returnCar = request.getParameter("returnCar");
		String periodType = request.getParameter("periodType");
		paramMap.put("cityCode",cityCode);
		paramMap.put("level",level);
		if(StringUtils.isNotBlank(memberType)){
			paramMap.put("memberType", memberType);
		}
		if(StringUtils.isNotBlank(memberLevel)){
			paramMap.put("memberLevel", memberLevel);
		}
		if(StringUtils.isNotBlank(returnCar)){
			paramMap.put("returnCar", returnCar);
		}
		paramMap.put("periodType", periodType);
		//今天或本月
		List<PileCountDataVo> currList=null;
		//昨天或上月
		List<PileCountDataVo> lastList=null;
		if("1".equals(countType)){
			if("0".equals(periodType)){
				currList =pileStatisticsService.getAllTodayChargingTimeData(paramMap);
				lastList=pileStatisticsService.getAllYesterdayChargingTimeData(paramMap);
			}else{
				currList =pileStatisticsService.getAllThisMonthChargingTimeData(paramMap);
				lastList=pileStatisticsService.getAllLastMonthChargingTimeData(paramMap);
			}
			
		}else if("2".equals(countType)){
			if("0".equals(periodType)){
				currList =pileStatisticsService.getAllTodayChargingPersonData(paramMap);
				lastList=pileStatisticsService.getAllYesterdayChargingPersonData(paramMap);
			}else{
				currList =pileStatisticsService.getAllThisMonthChargingPersonData(paramMap);
				lastList=pileStatisticsService.getAllLastMonthChargingPersonData(paramMap);
			}
		}else if("3".equals(countType)){
			if("0".equals(periodType)){
				currList =pileStatisticsService.getAllTodayChargingKWHData(paramMap);
				lastList=pileStatisticsService.getAllYesterdayChargingKWHData(paramMap);
			}else{
				currList =pileStatisticsService.getAllThisMonthChargingKWHData(paramMap);
				lastList=pileStatisticsService.getAllLastMonthChargingKWHData(paramMap);
			}
		}else if("4".equals(countType)){
			if("0".equals(periodType)){
				currList =pileStatisticsService.getAllTodayChargingIncomeData(paramMap);
				lastList=pileStatisticsService.getAllYesterdayChargingIncomeData(paramMap);
			}else{
				currList =pileStatisticsService.getAllThisMonthChargingIncomeData(paramMap);
				lastList=pileStatisticsService.getAllLastMonthChargingIncomeData(paramMap);
			}	
		}
		PileContDetailVo detailInfo = new PileContDetailVo();
		if(null != currList && null != lastList){

			if(null != currList && !currList.isEmpty()){

				detailInfo.setCurrList(currList);
			}
			if(null != lastList && !lastList.isEmpty()){

				detailInfo.setLastList(lastList);
			}
		response.getWriter().print(FastJsonUtils.toJson(detailInfo));
		}
	}
	
	
	/*统计省份充电桩个数*/
	@SystemServiceLog(description="根据cityCode获取桩的数量")
	@RequestMapping("/province_pile_nums_statistics") 
	public String getProPileNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获取桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getProPileNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	} 
	/*统计全国，城市充电枪个数*/
	@SystemServiceLog(description="根据cityCode获取桩的数量")
	@RequestMapping("/connector_nums_statistics") 
	public String getConnectorNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获取桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = null;
		}
		List<PileStatisticsVo> data = pileStatisticsService.getConnectorNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	} 
	/*统计省份充电枪个数*/
	@SystemServiceLog(description="根据cityCode获取桩的数量")
	@RequestMapping("/province_conncetor_nums_statistics") 
	public String getProvinceConnectorNums(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("根据cityCode获取桩的数量");
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		List<PileStatisticsVo> data = pileStatisticsService.getProvinceConnectorNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	} 
}
