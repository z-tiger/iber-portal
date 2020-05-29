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

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.service.base.ParkUseDetailService;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.park.ParkDetailInfoVo;
import com.iber.portal.vo.park.ParkDetailStatisticsVo;
import com.iber.portal.vo.park.ParkUseAnalysisVo;

/**
 * 网点使用明细 <br>
 * <b>功能：</b>ParkUseDetailController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Controller
public class ParkUseDetailController {

	private final static Logger log = Logger
			.getLogger(ParkUseDetailController.class);

	// Servrice start
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ParkUseDetailService parkUseDetailService;


	/**
	 * 从HttpServletRequest获取参数的int值
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 * @date 2016-11-23 上午9:12:04
	 */
	private Integer getIntReqParam(HttpServletRequest request, String paramName) {
		String str = request.getParameter(paramName);
		Integer intRst = null;
		if (StringUtils.isNotBlank(str)) {
			intRst = Integer.valueOf(str);
		}
		return intRst;
	}

	/**
	 * 统计时间周期类型：今天/昨天 、 本月/上月
	 * 
	 * @author ouxx
	 * @since 2016-12-7 上午11:32:12
	 * 
	 */
	private enum PeriodType {
		TODAY_YESTERDAY(0), // 今天/昨天
		THIS_MONTH_LAST_MONTY(1)// 本月/上月
		;
		private Integer type;

		private PeriodType(Integer type) {
			this.type = type;
		}

		public Integer getValue() {
			return this.type;
		}

		public static PeriodType valueOf(Integer value) throws RuntimeException {
			PeriodType tempEnum = null;
			for (PeriodType en : PeriodType.values()) {
				if (en.type.intValue() == value.intValue()) {
					tempEnum = en;
					break;
				}
			}
			if (tempEnum == null) {
				throw new RuntimeException("Enum value not exist");
			}
			return tempEnum;
		}
	}

	
	@SystemServiceLog(description="查询即今天/昨天、本月/上月网点使用情况")
	@RequestMapping(value = "/queryParkDetail" ,method = {
			RequestMethod.GET, RequestMethod.POST }) 
	public String queryParkDetail(HttpServletRequest request, HttpServletResponse response) {		
		response.setContentType("text/html;charset=utf-8");
		log.info("查询今天/昨天、本月/上月网点使用详情与总体数据");
		
		//periodType  日/月		 
		Integer periodType = getIntReqParam(request, "periodType"); 
		if(null == periodType){
			periodType = 0;			
		}
		//countType   统计类型
		Integer countType = getIntReqParam(request, "countType");
		//orderType   订单类型
		String orderType = request.getParameter("orderType");
		//会员类型
		Integer memberType = getIntReqParam(request, "memberType");
		//城市编码
		String cityCode = request.getParameter("cityCode");
		//城市层级
		Integer layer = getIntReqParam(request, "layer");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("periodType", periodType);
		if(StringUtils.isNotBlank(orderType)){
			paramMap.put("orderType", orderType);
		} else if(null != memberType){
			paramMap.put("memberType", memberType);
		} else if(null != countType){
			paramMap.put("countType", countType);
		}		
		//有区域限定
		if(StringUtils.isNotBlank(cityCode) && !cityCode.equals("00") && null != layer){
			paramMap.put("cityCode", cityCode);
			paramMap.put("layer", layer);
		}
		
		//今天或本月
		List<ParkDetailStatisticsVo> currList = null;
		//昨天或上月
		List<ParkDetailStatisticsVo> lastList = null;
		Long todayCnt = 0L;
		Long yesterdayCnt = 0L;
		Long thisMonthCnt = 0L;
		Long lastMonthCnt = 0L;
		Long totalCnt = this.parkUseDetailService.queryTotalCnt(paramMap);
		
		//查今天和昨天的数据
		PeriodType period = PeriodType.valueOf(periodType);
		switch(period){
			case TODAY_YESTERDAY:
				currList = this.parkUseDetailService.queryTodayDetail(paramMap);
				lastList = this.parkUseDetailService.queryYesterdayDetail(paramMap);
				todayCnt = this.parkUseDetailService.queryTodayCnt(paramMap);
				yesterdayCnt = this.parkUseDetailService.queryYesterdayCnt(paramMap);
				thisMonthCnt = this.parkUseDetailService.queryThisMonthCnt(paramMap);//本月
				lastMonthCnt = this.parkUseDetailService.queryLastMonthCnt(paramMap);//上月
				break;
			case THIS_MONTH_LAST_MONTY:
				currList = this.parkUseDetailService.queryThisMonthDetail(paramMap);
				lastList = this.parkUseDetailService.queryLastMonthDetail(paramMap);
				todayCnt = this.parkUseDetailService.queryTodayCnt(paramMap);//今天
				yesterdayCnt = this.parkUseDetailService.queryYesterdayCnt(paramMap);//昨天
				thisMonthCnt = this.parkUseDetailService.queryThisMonthCnt(paramMap);
				lastMonthCnt = this.parkUseDetailService.queryLastMonthCnt(paramMap);
				break;
			default:break;
		}
		
		ParkDetailInfoVo detailInfo = new ParkDetailInfoVo();
		if(null != currList && !currList.isEmpty()){
			detailInfo.setCurrList(currList);
		}
		if(null != lastList && !lastList.isEmpty()){
			detailInfo.setLastList(lastList);
		}
		
		todayCnt = null != todayCnt ? todayCnt : 0;
		yesterdayCnt = null != yesterdayCnt ? yesterdayCnt : 0;
		thisMonthCnt = null != thisMonthCnt ? thisMonthCnt : 0;
		lastMonthCnt = null != lastMonthCnt ? lastMonthCnt : 0;
		totalCnt = null != totalCnt ? totalCnt : 0;
		//计算环比
		if(null != yesterdayCnt && 0 < yesterdayCnt){
			detailInfo.setRatioDayCnt((int) Math.round((todayCnt - yesterdayCnt) * 100 / (double)yesterdayCnt));
		}else{//如果昨天没有订单，则环比为100%
			if(todayCnt > 0 )
				detailInfo.setRatioDayCnt(100);
			else
			   detailInfo.setRatioDayCnt(0);
		}
		
		if(null != lastMonthCnt && 0 < lastMonthCnt){
			detailInfo.setRatioMonthCnt((int) Math.round((thisMonthCnt - lastMonthCnt) * 100 / (double)lastMonthCnt));
		}else{//如果上月没有订单，则环比为100%
			if(thisMonthCnt > 0)
				detailInfo.setRatioMonthCnt(100);
			else
				detailInfo.setRatioMonthCnt(0);
		}
		
		detailInfo.setTodayCnt(todayCnt);
		detailInfo.setYesterdayCnt(yesterdayCnt);
		detailInfo.setThisMonthCnt(thisMonthCnt);
		detailInfo.setLastMonthCnt(lastMonthCnt);
		detailInfo.setTotalCnt(totalCnt);
		
		try {
			response.getWriter().print(FastJsonUtils.toJson(detailInfo));
		} catch (IOException e) {
			log.error("查询今天/昨天、本月/上月网点使用详情与总体数据 queryParkDetail", e);
			e.printStackTrace();
		}
		
		return null;
	}
	@SystemServiceLog(description="网点运营情况分析")
	@RequestMapping(value = "/queryParkUserDetail" ,method = {RequestMethod.GET, RequestMethod.POST }) 
	public String queryParkUserDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String countType = request.getParameter("countType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String cityCode  = request.getParameter("cityCode");
		String level = request.getParameter("layer");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(countType)){
			paramMap.put("countType", countType);
		}
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
			paramMap.put("startTime", startTime+" 00:00:00");
			paramMap.put("endTime", endTime+" 23:59:59");
		}
		if(StringUtils.isNotBlank(cityCode) && !"00".equals(cityCode)){
			
			paramMap.put("cityCode", cityCode);
		}
		if(StringUtils.isNotBlank(level)){
			paramMap.put("level", level);
		}
		List<ParkUseAnalysisVo> result = null;
		if( "1".equals(countType)){
			//按照约车类型 进行网点运营分析
			 if("2".equals(level)){
				 result=parkUseDetailService.cityParkUseByOrder(paramMap);
			}else if("3".equals(level)){
				result = parkUseDetailService.ParkUseByOrder(paramMap);
			}else {
				result = parkUseDetailService.provinceParkUseByOrder(paramMap);
			}
			
		}else if("2".equals(countType)){
			//按照还车类型 进行网点运营分析
			if("2".equals(level)){
				result = parkUseDetailService.cityParkUseByReturn(paramMap);
			}else if("3".equals(level)){
				result = parkUseDetailService.ParkUseByReturn(paramMap);
			}else {
				result = parkUseDetailService.provinceParkUseByReturn(paramMap);
			}
		}else if("3".equals(countType)){
			//按照充电类型 进行网点运营分析
			if("2".equals(level)){
				result = parkUseDetailService.cityParkUseByCharging(paramMap);
			}else if("3".equals(level)){
				result = parkUseDetailService.ParkUseByCharging(paramMap);
			}else {
				result = parkUseDetailService.provinceParkUseByCharging(paramMap);
			}
		}
		if(countType==null || "".equals(countType)){
			if("2".equals(level)){
				result = parkUseDetailService.cityParkUse(paramMap);
			}else if("3".equals(level)){
				result = parkUseDetailService.ParkUse(paramMap);
			}else {
				result = parkUseDetailService.provinceParkUse(paramMap);
			}
		}
		response.getWriter().print(Data2Jsp.listToJson(result));
		return null;
	}
}
