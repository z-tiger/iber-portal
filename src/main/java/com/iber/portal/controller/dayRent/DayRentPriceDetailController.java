package com.iber.portal.controller.dayRent;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.dayRent.DayRentPriceDetail;
import com.iber.portal.service.dayRent.DayRentPriceDetailService;


@Controller
public class DayRentPriceDetailController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private DayRentPriceDetailService dayRentPriceDetailService;
	
	/**
	 * @describe 日租价格明细页面
	 * 
	 */
	@SystemServiceLog(description="日租价格明细页面")
	@RequestMapping(value = "/dayRent_price_detail_page", method = { RequestMethod.GET })
	public String dayRentPriceDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租价格明细页面");
		return "dayRent/dayRentPriceDetail";		
	}
	
	/**
	 * @describe 日租价格明细列表
	 */
	@SystemServiceLog(description="日租价格明细列表")
	@RequestMapping(value = "/dayRent_price_detail_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceDetailList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租价格明细列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String adPid = request.getParameter("adpid");
		String isShow = request.getParameter("is_show");
		String title = request.getParameter("title");
		String cityCode = request.getParameter("area_code");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("adPid", adPid);
		paramMap.put("isShow", isShow);
		paramMap.put("title", title);
		paramMap.put("cityCode", cityCode);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<DayRentPriceDetail> pager = dayRentPriceDetailService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新日租价格明细
	 * 
	 */
	@SystemServiceLog(description="保存更新日租价格明细")
	@RequestMapping(value = "/dayRent_price_detail_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceDetailSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新日租价格明细");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		if (id!=null && !id.equals("")) {
			DayRentPriceDetail currObj = dayRentPriceDetailService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				dayRentPriceDetailService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			DayRentPriceDetail obj = new DayRentPriceDetail();
			dayRentPriceDetailService.insertSelective(obj);
	
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除日租价格明细
	 * 
	 */
	@SystemServiceLog(description="删除日租价格明细")
	@RequestMapping(value = "/dayRent_price_detail_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceDetailDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除日租价格明细");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			dayRentPriceDetailService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	
}
