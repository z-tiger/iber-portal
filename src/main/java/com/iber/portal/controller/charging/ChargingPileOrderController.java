package com.iber.portal.controller.charging;

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
import com.iber.portal.model.charging.ChargingPileOrder;
import com.iber.portal.service.charging.ChargingPileOrderService;

@Controller
public class ChargingPileOrderController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private ChargingPileOrderService chargingPileOrderService;
	
	/**
	 * @describe 充电订单页面
	 * 
	 */
	@SystemServiceLog(description="充电订单页面")
	@RequestMapping(value = "/charging_pile_order_page", method = { RequestMethod.GET })
	public String chargingPileOrder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电订单页面");
		return "charging/chargingPileOrder";		
	}
	
	/**
	 * @describe 充电订单列表
	 */
	@SystemServiceLog(description="充电订单列表")
	@RequestMapping(value = "/charging_pile_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileOrderList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电订单列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String city_code = request.getParameter("city_code");
		String member_name = request.getParameter("member_name");
		String park_name = request.getParameter("park_name");
		String pile_type = request.getParameter("pile_type");
		String pay_type = request.getParameter("pay_type");
		String charging_type = request.getParameter("charging_type");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("cityCode", city_code);
		paramMap.put("memberName", member_name);
		paramMap.put("parkName", park_name);
		paramMap.put("pileType", pile_type);
		paramMap.put("payType", pay_type);
		paramMap.put("chargingType", charging_type);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<ChargingPileOrder> pager = chargingPileOrderService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
}
