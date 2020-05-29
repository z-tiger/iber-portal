package com.iber.portal.controller;




import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.model.evaluate.OrderEvaluate;
import com.iber.portal.model.evaluate.OrderEvaluateLabel;
import com.iber.portal.service.evaluate.OrderEvaluateLabelService;
import com.iber.portal.service.evaluate.OrderEvaluateService;





/**
 * @describe 
 * 
 * @author yangguiwu
 * @date 2016年01月20日
 */
@Controller
public class OrderEvaluateController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderEvaluateService orderEvaluateService;	
	
	@Autowired
	private OrderEvaluateLabelService orderEvaluateLabelService;	
	
	
	
	
	/**
	 * @describe 用车订单页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年03月30日
	 * @throws Exception
	 */
	@SystemServiceLog(description="用车订单页面")
	@RequestMapping(value = "/orderEvaluate", method = { RequestMethod.GET })
	public String orderEvaluate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "evaluate/orderEvaluate";		
	}
	@SystemServiceLog(description="用车订单列表")
    @RequestMapping(value = "/order_evaluate_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderEvaluateList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        if(!StringUtils.isBlank(request.getParameter("evaluateType"))){
        	record.put("evaluateType", request.getParameter("evaluateType"));
        }
        if(!StringUtils.isBlank(request.getParameter("orderId"))){
        	record.put("orderId", request.getParameter("orderId"));
        }      
        if(!StringUtils.isBlank(request.getParameter("name"))){
        	record.put("name", request.getParameter("name"));
        }
        if(!StringUtils.isBlank(request.getParameter("phone"))){
        	record.put("phone", request.getParameter("phone"));
        } 
        
        if(!StringUtils.isBlank(request.getParameter("evaluateStar"))){
        	record.put("evaluateStar", request.getParameter("evaluateStar"));
        } 

			Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			record.put("page", pageInfo.get("first_page"));
			record.put("size", pageInfo.get("page_size"));
			List<OrderEvaluate> data = orderEvaluateService.selectByPrimaryKey(record);
			JSONObject obj = new JSONObject();
			obj.put("total", orderEvaluateService.selectByPrimaryKeyRecords(record));
			obj.put("rows", data);
			response.getWriter().print(obj.toString());
			return null;
	}
    
    
	/**
	 * @describe 评价标签配置页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年03月30日
	 * @throws Exception
	 */
	@SystemServiceLog(description="评价标签配置页面")
	@RequestMapping(value = "/orderEvaluateLabel", method = { RequestMethod.GET })
	public String orderEvaluateLabel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "evaluate/orderEvaluateLabel";		
	}   
	@SystemServiceLog(description="评价标签配置列表")
    @RequestMapping(value = "/order_evaluate_label_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String orderEvaluateLabelList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        if(!StringUtils.isBlank(request.getParameter("type"))){
        	record.put("type", request.getParameter("type"));
        }
        if(!StringUtils.isBlank(request.getParameter("star"))){
        	record.put("star", request.getParameter("star"));
        }      
        if(!StringUtils.isBlank(request.getParameter("label"))){
        	record.put("label", request.getParameter("label"));
        } 

			Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			record.put("page", pageInfo.get("first_page"));
			record.put("size", pageInfo.get("page_size"));
			List<OrderEvaluateLabel> data = orderEvaluateLabelService.selectByPrimaryKey(record);
			JSONObject obj = new JSONObject();
			obj.put("total", orderEvaluateLabelService.selectByPrimaryKeyRecords(record));
			obj.put("rows", data);
			response.getWriter().print(obj.toString());
			return null;
	}		

	/**
	 * @describe 删除评价标签记录
	 * 
	 * @auther yangguiwu
	 * @date 2016年03月30日
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除评价标签配置")
	@RequestMapping(value = "/order_evaluate_label_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String orderEvaluateLabelDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		response.setContentType("text/html;charset=utf-8");

		if (id!=null && !id.equals("")) {
			orderEvaluateLabelService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}

	/**
	 * @describe 添加评价标签
	 * 
	 */
	@SystemServiceLog(description="添加评价标签配置")
	@RequestMapping(value = "/add_evaluate_label", method = { RequestMethod.GET , RequestMethod.POST })
	public String addEvaluateLabel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		String type = request.getParameter("modType");
		String star = request.getParameter("modStar");
		String label = request.getParameter("modLabel");
		
		if (id!=null && !id.equals("")) {
			OrderEvaluateLabel currObj = orderEvaluateLabelService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setType(type);
				currObj.setStar(Integer.parseInt(star));
				currObj.setLabel(label);
				orderEvaluateLabelService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{	
			OrderEvaluateLabel obj = new OrderEvaluateLabel();
			obj.setType(type);
			obj.setStar(Integer.parseInt(star));
			obj.setLabel(label);
			orderEvaluateLabelService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 评价审核
	 * 
	 */
	@SystemServiceLog(description="评价审核")
	@RequestMapping(value = "/order_evaluate_check", method = { RequestMethod.GET , RequestMethod.POST })
	public String order_evaluate_check(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Integer id = Integer.parseInt(request.getParameter("id"));
		String checkStatus = request.getParameter("checkStatus");
		String checkReason = request.getParameter("checkReason");
		OrderEvaluate orderEvaluate = orderEvaluateService.selectByPrimaryId(id) ;
		orderEvaluate.setCheckReason(checkReason) ;
		orderEvaluate.setCheckStatus(checkStatus) ;
		orderEvaluate.setCheckTime(new Date()) ;
		orderEvaluateService.updateByPrimaryKey(orderEvaluate) ;
		response.getWriter().print("success");
		return null;	
	}
}
