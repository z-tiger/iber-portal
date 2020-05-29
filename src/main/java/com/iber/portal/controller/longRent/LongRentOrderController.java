package com.iber.portal.controller.longRent;

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
import com.iber.portal.model.longRent.LongRentOrder;
import com.iber.portal.service.longRent.LongRentOrderService;


@Controller
public class LongRentOrderController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private LongRentOrderService longRentOrderService;
	
	/**
	 * @describe 长租订单页面
	 * 
	 */
	@SystemServiceLog(description="长租订单页面")
	@RequestMapping(value = "/longRent_order_page", method = { RequestMethod.GET })
	public String longRentOrder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("长租订单页面");
		return "dayRent/longRentOrder";		
	}
	
	/**
	 * @describe 长租订单列表
	 */
	@SystemServiceLog(description="长租订单列表")
	@RequestMapping(value = "/longRent_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String longRentOrderList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("长租订单列表");
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
		Pager<LongRentOrder> pager = longRentOrderService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新长租订单
	 * 
	 */
	@SystemServiceLog(description="保存更新长租订单")
	@RequestMapping(value = "/longRent_order_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String longRentOrderSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新长租订单");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		if (id!=null && !id.equals("")) {
			LongRentOrder currObj = longRentOrderService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				longRentOrderService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			LongRentOrder obj = new LongRentOrder();
			longRentOrderService.insertSelective(obj);
	
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除长租订单
	 * 
	 */
	@SystemServiceLog(description="删除长租订单")
	@RequestMapping(value = "/longRent_order_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String longRentOrderDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除长租订单");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			longRentOrderService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	
}
