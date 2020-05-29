package com.iber.portal.controller.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.member.MemberCountDetail;
import com.iber.portal.model.member.MemberCountDetailVo;
import com.iber.portal.service.member.MemberCountDetailService;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.order.RentCountDetailInfoVo;
import com.iber.portal.vo.order.RentCountDetailVo;


@Controller
public class MemberCountDetailController{
	
	private final static Logger log= Logger.getLogger(MemberCountDetailController.class);
	
	
	@Autowired(required=false) 
	private MemberCountDetailService memberCountDetailService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description=" 会员统计详细页面")
	@RequestMapping("/memberCountDetail_page") 
	public String memberCountDetail_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("MemberCountDetail页面");
		return "member/memberCountDetail" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" 会员统计数据")
	@RequestMapping("/dataListMemberCountDetail") 
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
		 
		Pager<MemberCountDetail> pager = memberCountDetailService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param MemberCountDetail
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" 会员统计添加或修改数据")
	@RequestMapping("/saveOrUpdateMemberCountDetail")
	public void saveOrUpdate(MemberCountDetail entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			memberCountDetailService.insert(entity);
		}else{
			memberCountDetailService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员统计删除数据")
	@RequestMapping("/deleteMemberCountDetailById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberCountDetailService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	@SystemServiceLog(description="统计今日/昨日，本月/上月,累计的会员的详细数据信息")
	@RequestMapping("/queryMemberCountDetail")
	public void queryMemberCountDetail(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
		String periodType = request.getParameter("periodType");
		paramMap.put("cityCode",cityCode);
		paramMap.put("level",level);
		if(StringUtils.isNotBlank(memberType)){
			paramMap.put("memberType", memberType);
		}
		if(StringUtils.isNotBlank(memberLevel)){
			paramMap.put("memberLevel", memberLevel);
		}
		paramMap.put("periodType", periodType);
		//今天或本月
		List<MemberCountDetail> currList=null;
		//昨天或上月
		List<MemberCountDetail> lastList=null;
		if("1".equals(countType)){
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayRegisterData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayRegisterData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthRegisterData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthRegisterData(paramMap);
			}
			
		}else if("2".equals(countType)){
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayMemberData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayMemberData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthMemberData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthMemberData(paramMap);
			}
		}else if("3".equals(countType)){
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayDepositData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayDepositData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthDepositData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthDepositData(paramMap);
			}
		}else if("4".equals(countType)){
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayBalanceData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayBalanceData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthBalanceData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthBalanceData(paramMap);
			}	
		}else if("6".equals(countType)){//消费金额
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayCostData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayCostData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthCostData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthCostData(paramMap);
			}	
		}else if("7".equals(countType)){//未支付金额
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayNoPayData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayNoPayData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthNoPayData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthNoPayData(paramMap);
			}	
		}else if("8".equals(countType)){//已退款金额
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayRefundData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayRefundData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthRefundData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthRefundData(paramMap);
			}	
		}else{
			if("0".equals(periodType)){
				currList =memberCountDetailService.getAllTodayChargeData(paramMap);
				lastList=memberCountDetailService.getAllYesterdayChargeData(paramMap);
			}else{
				currList =memberCountDetailService.getAllThisMonthChargeData(paramMap);
				lastList=memberCountDetailService.getAllLastMonthChargeData(paramMap);
			}	
		}	
		MemberCountDetailVo detailInfo = new MemberCountDetailVo();
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
	
	@SystemServiceLog(description="统计今日/昨日，本月/上月,累计的会员数据")
	@RequestMapping("/queryMemberCountDatas")
	public void queryMemberCountDatas(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
		paramMap.put("cityCode",cityCode);
		paramMap.put("level",level);
		if(StringUtils.isNotBlank(memberType)){
			paramMap.put("memberType", memberType);
		}
		if(StringUtils.isNotBlank(memberLevel)){
			paramMap.put("memberLevel", memberLevel);
		}
		List<MemberCountDetail> memberCountDetail=null;
		if("1".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllRegisterData(paramMap);
		}else if("2".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllMemberData(paramMap);
		}else if("3".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllDepositData(paramMap);
		}else if("4".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllBalanceData(paramMap);	
		}else if("6".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllCostData(paramMap);	
		}else if("7".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllNoPayData(paramMap);	
		}else if("8".equals(countType)){
			memberCountDetail =memberCountDetailService.getAllRefundData(paramMap);	
		}else{
			memberCountDetail =memberCountDetailService.getAllChargeNumberData(paramMap);
		}	
		response.getWriter().print(Data2Jsp.listToJson(memberCountDetail));
	}
}
