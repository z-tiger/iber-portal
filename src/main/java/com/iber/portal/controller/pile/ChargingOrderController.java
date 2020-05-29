package com.iber.portal.controller.pile;

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
import com.iber.portal.model.pile.ChargingOrder;
import com.iber.portal.service.pile.ChargingOrderService;


@Controller
public class ChargingOrderController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private ChargingOrderService chargingOrderService;
	
	/**
	 * @describe 充电订单页面
	 * 
	 */
	@SystemServiceLog(description="充电订单页面")
	@RequestMapping(value = "/pile_charging_order", method = { RequestMethod.GET })
	public String chargingOrder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电订单页面");
		return "pile/chargingOrder";		
	}
	
	/**
	 * @describe 充电订单列表
	 */
	@SystemServiceLog(description="充电订单列表")
	@RequestMapping(value = "/pile_charging_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingOrderList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String name = request.getParameter("name");
		String cityCode = request.getParameter("city_code");
		String orderId = request.getParameter("order_id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("name", name);
		paramMap.put("cityCode", cityCode);
		paramMap.put("orderId", orderId);
		Pager<ChargingOrder> pager = chargingOrderService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	
	/**
	 * @describe 保存更新充电订单
	 * 
	 */
	@SystemServiceLog(description="保存更新充电订单")
	@RequestMapping(value = "/pile_charging_order_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingOrderSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		if (id!=null && !id.equals("")) {
			ChargingOrder currObj = chargingOrderService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				
				chargingOrderService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{		
			ChargingOrder obj = new ChargingOrder();
			
			chargingOrderService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}
	
	
	
}
