package com.iber.portal.controller.monitorCenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.charging.CarBrand;
import com.iber.portal.model.order.RentDetailOrder;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.dayRent.DayRentOrderExtendService;
import com.iber.portal.service.dayRent.DayRentOrderService;
import com.iber.portal.service.order.RentDetailOrderService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.service.timeShare.TimeSharePayService;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.order.RentCountDetailInfoVo;
import com.iber.portal.vo.order.RentCountDetailVo;
import com.iber.portal.vo.order.RentStatisticsVo;

/**
 * 
 * <br>
 * <b>功能：</b>RentDetailOrderController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class RentDetailOrderController{
	
	private final static Logger log= Logger.getLogger(RentDetailOrderController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private RentDetailOrderService rentDetailOrderService; 
	
	@Autowired
	private CarService carService;
	
	
	/**
	 * 从HttpServletRequest获取参数的int值
	 * @param request
	 * @param paramName
	 * @return
	 * @author ouxx
	 * @date 2016-11-23 上午9:12:04
	 */
	private Integer getIntReqParam(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer intRst = null;
		if(StringUtils.isNotBlank(str)){
			intRst = Integer.valueOf(str);
		}
		return intRst;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="全国车辆统计")
	@RequestMapping("/run_statistics") 
	public String rentDetailOrder_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("RentDetailOrder页面");
		return "echarts/core/run_statistics" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="全国车辆统计数据列表")
	@RequestMapping("/dataListRentDetailOrder") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		 
		Pager<RentDetailOrder> pager = rentDetailOrderService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param RentDetailOrder
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="全国车辆统计添加或修改数据")
	@RequestMapping("/saveOrUpdateRentDetailOrder")
	public void saveOrUpdate(RentDetailOrder entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			rentDetailOrderService.insert(entity);
		}else{
			rentDetailOrderService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="全国车辆统计删除数据")
	@RequestMapping("/deleteRentDetailOrderById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			rentDetailOrderService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 统计时间周期类型：今天/昨天  、 本月/上月
	 * @author ouxx
	 * @since 2016-12-7 上午11:32:12
	 *
	 */
	private enum PeriodType{
		TODAY_YESTERDAY(0),//今天/昨天
		THIS_MONTH_LAST_MONTY(1)//本月/上月
		;
		private Integer type;
		private PeriodType(Integer type){
			this.type = type;
		}
		public Integer getValue(){
			return this.type;
		}
		public static PeriodType valueOf(Integer value) throws RuntimeException{
			PeriodType tempEnum = null;
			for(PeriodType en : PeriodType.values()){
				if(en.type.intValue() == value.intValue()){
					tempEnum = en;
					break;
				}
			}
			if(tempEnum == null){
				throw new RuntimeException("Enum value not exist");
			}
			return tempEnum;
		}
	}
	
	
	/**
	 * 统计详情，详细的数据，用于折线图显示，如选择“今天”后，折线图的X坐标是小时；选择本月，X坐标是天
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author ouxx
	 * @date 2016-12-5 下午2:38:20
	 */
	@SystemServiceLog(description="统计详细数据,即今天/昨天中每小时、本月/上月中每天的情况")
	@RequestMapping(value = "/queryRentDetail", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String queryRentDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		RentCountDetailInfoVo detailInfo = this.setDetailList(request, paramMap);
		if(null != detailInfo){
			response.getWriter().print(FastJsonUtils.toJson(detailInfo));			
		}
		return null;
	}
	/**
	 * 查询车辆品牌列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SystemServiceLog(description="查询车辆品牌列表")
	@RequestMapping(value = "/getBrandNameList", method = {RequestMethod.GET})
	public String getBrandNameList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		log.info("查询车辆品牌列表");
		response.setContentType("text/html;charset=utf-8");
			
		List<CarBrand> list = this.carService.getBrandNameList();
		response.getWriter().print(FastJsonUtils.toJson(list));
		
		return null;
	}
	

	@SystemServiceLog(description="统计总体数据和详细数据,即今天/昨天总数及其中每小时、本月/上月总数及其中每天的情况")
	@RequestMapping(value = "/queryRentSummarizationAndDetail", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String queryRentSummarizationAndDetail(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//二维坐标相应指获取
		RentCountDetailInfoVo detailInfo = this.setDetailList(request, paramMap);
		RentStatisticsVo statisticsVo = null;
		Object obj = this.rentDetailOrderService.queryRentStatistics(paramMap);
		//如果是从redis中获取数据，则返回的是JSON
		if(obj instanceof JSONObject){
			statisticsVo = JSONObject.toJavaObject((JSONObject)obj, RentStatisticsVo.class);
		}else{
			statisticsVo = (RentStatisticsVo) obj;
		}
		
		if(null != statisticsVo){
			if(null == detailInfo){
				detailInfo = new RentCountDetailInfoVo();
			}
			detailInfo.setTodayCnt(statisticsVo.getTodayCnt());
			detailInfo.setYesterdayCnt(statisticsVo.getYesterdayCnt());
			detailInfo.setThisMonthCnt(statisticsVo.getThisMonthCnt());
			detailInfo.setLastMonthCnt(statisticsVo.getLastMonthCnt());
			detailInfo.setTotalCnt(statisticsVo.getTotalCnt());
			detailInfo.setRatioDayCnt(statisticsVo.getRatioDayCnt());
			detailInfo.setRatioMonthCnt(statisticsVo.getRatioMonthCnt());
		}
		response.getWriter().print(FastJsonUtils.toJson(detailInfo));
		return null;
	}
	
	/**
	 * 查找并设置详情数据
	 * @param request
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2017-1-23 下午6:15:44
	 */
	private RentCountDetailInfoVo setDetailList(HttpServletRequest request, Map<String, Object> paramMap){
		Integer countType = getIntReqParam(request, "countType");
		//periodType用于分辨 今天/昨天 or 本月/上月
		Integer periodType = getIntReqParam(request, "periodType");
		if(null == periodType){
			periodType = 0;
		}
		log.info("统计详细数据,即今天/昨天中每小时、本月/上月中每天的情况 : periodType = " + periodType);
	
		String brandName = request.getParameter("brandName");
		String orderType = request.getParameter("orderType");
		Integer memberType = getIntReqParam(request, "memberType");
		String cityCode = request.getParameter("cityCode");
		Integer layer = getIntReqParam(request, "layer");
		String status = request.getParameter("status");//订单状态
		String statusList = request.getParameter("statusList");//多个订单状态，格式如：'finish','return'
		String excludeStatus = request.getParameter("excludeStatus");//排除某个订单状态
		
		if(null == countType){
			countType = 0;
		}
		if(StringUtils.isNotBlank(status)){
			paramMap.put("status", status);
		} else if(StringUtils.isNotBlank(excludeStatus)){
			paramMap.put("excludeStatus", excludeStatus);
		} else if(StringUtils.isNotBlank(statusList)){
			paramMap.put("statusList", statusList);
		}
		
		paramMap.put("countType", countType);
		if(StringUtils.isNotBlank(orderType)){
			paramMap.put("orderType", orderType);
		} else if(null != memberType){
			paramMap.put("memberType", memberType);
		} else if(StringUtils.isNotBlank(brandName)){
			paramMap.put("brandName", brandName);
		}
		
		if(StringUtils.isNotBlank(cityCode)){
			cityCode = "00".equals(cityCode) ? null : cityCode;
		}
		paramMap.put("cityCode", cityCode);
		paramMap.put("layer", layer);
		
		//今天或本月
		List<RentCountDetailVo> currList = null;
		//昨天或上月
		List<RentCountDetailVo> lastList = null;
		PeriodType period = PeriodType.valueOf(periodType);
		switch(period){
			case TODAY_YESTERDAY:
				currList = this.rentDetailOrderService.queryTodayDetail(paramMap);
				lastList = this.rentDetailOrderService.queryYesterdayDetail(paramMap);
				break;
			case THIS_MONTH_LAST_MONTY:
				
				currList = this.rentDetailOrderService.queryThisMonthDetail(paramMap);
				lastList = this.rentDetailOrderService.queryLastMonthDetail(paramMap);
				break;
			default:break;
		}
		
		RentCountDetailInfoVo detailInfo = new RentCountDetailInfoVo();
		if(null != currList || null != lastList){
			if(null != currList && !currList.isEmpty()){
				detailInfo.setCurrList(currList);
			}
			if(null != lastList && !lastList.isEmpty()){
				detailInfo.setLastList(lastList);
			}
			return detailInfo;
		}
		return null;
		
	}
	
}
