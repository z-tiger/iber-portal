package com.iber.portal.controller.coupon;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.coupon.CouponItem;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.coupon.CouponItemService;
import com.iber.portal.util.DateTime;

/**
 * 
 * <br>
 * <b>功能：</b>CouponItemController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：
 */ 
@Controller
public class CouponItemController extends MainController {
	
	private final static Logger log= Logger.getLogger(CouponItemController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private CouponItemService couponItemService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="CouponItem页面")
	@RequestMapping("/couponItem_page") 
	public String couponItem_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("CouponItem页面");
		return "coupon/couponItem" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="CouponItem数据列表")
	@RequestMapping("/dataListCouponItem") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String itemName = request.getParameter("itemName");
		String status = request.getParameter("status");
		String cityCode = request.getParameter("city_code");
		//获取其他查询参数 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("itemName",itemName);
		paramMap.put("status",status);
		paramMap.put("cityCode",cityCode);
		Pager<CouponItem> pager = couponItemService.queryPageList(paramMap);
		
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param CouponItem
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="CouponItem添加或修改数据")
	@RequestMapping(value = "/saveOrUpdateCouponItem")
	public  String saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		SysUser user = (SysUser) getUser(request) ;
		//精度
		DecimalFormat df=new DecimalFormat("0.00");
		String msg = "success" ;
		String reg = "^[A-Za-z]+$";
		Map<String, Object> map = new HashMap<String, Object>();
		String itemcode = request.getParameter("itemcode");
		String id = request.getParameter("id");
		String itemname = request.getParameter("itemname");
		String number = request.getParameter("number");
		String city_code = request.getParameter("city_code");
		String balance = request.getParameter("balance");
		String status = request.getParameter("status");
		String deadline = request.getParameter("deadline");
		String useType = request.getParameter("couponUseType");
		String minUseValue = request.getParameter("minUseValue").trim();
		String maxDeductionValue = request.getParameter("maxDeductionValue").trim();
		String start = request.getParameter("startTime");
		String end = request.getParameter("endTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cd = Calendar.getInstance();
		if(itemcode.equals("fourAutomaticGive")||itemcode.equals("fiveAutomaticGive")){
			if(!StringUtils.isBlank(status)){
				map.put("status", Integer.valueOf(status));
			}else {
				map.put("status",0);
			}
		}else{
			try {
				if(!StringUtils.isBlank(start)&&!StringUtils.isBlank(end)){
					if(!start.equals(end) && (sdf.parse(end)).compareTo((sdf.parse(start)))>0){
						//固定格式:开始时间必须以 :00:00:00  结束时间 23:59:59
						Date startTime = DateTime.getStartTimeOfDay(sdf.parse(start));
						Date endTime = DateTime.getEndTimeOfDay(sdf.parse(end));
						map.put("startTime", startTime);
						map.put("endTime", endTime);
						if(cd.getTime().after(sdf.parse(start)) && cd.getTime().before(sdf.parse(end))){
							map.put("status",1);
						}else{
							map.put("status", 0);
						}
					}else{
						response.getWriter().print("timeErr");
						return null;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		map.put("city_code", city_code);
		map.put("itemcode", itemcode);
 		map.put("id", id);
		// 传过来的参数如果为空白,则默认设置为 0
		if(!StringUtils.isBlank(balance)){
			BigDecimal b1=new BigDecimal(Double.valueOf(balance));
			BigDecimal b2=new BigDecimal(100);
			map.put("balance", df.format(b1.multiply(b2).doubleValue()));
		}
		map.put("itemname",itemname);
		// 1代表抵扣券
        if(!StringUtils.isBlank(minUseValue)){
			int index = minUseValue.indexOf(".");
			if (-1 != index) {  // 不为-1,存在小数点，可能为小数
				String str = minUseValue.substring(0, index) + minUseValue.substring(index + 1);
				if (StringUtils.isNumeric(str)) {
					Double d = Double.valueOf(minUseValue)*100;
					map.put("minUseValue", d.intValue());
				}else {
					response.getWriter().print("num-wrong");
					return null;
				}
			}else if(StringUtils.isNumeric(minUseValue)){ // 
				map.put("minUseValue", Integer.valueOf(minUseValue)*100);
			}else {
				response.getWriter().print("num-wrong");
				return null;
			}
        }else{
        	map.put("minUseValue", 0);
        }
		map.put("useType", useType);
		if(!StringUtils.isBlank(number)){
			map.put("number",Integer.valueOf(number));
		}else {
			map.put("number",0);
		}
		if(!StringUtils.isBlank(deadline)){
			map.put("deadline",Integer.valueOf(deadline));
		}else {
			map.put("deadline",0);
		}
		
		if(StringUtils.isNumeric(maxDeductionValue)) {
			Double d = Double.valueOf(maxDeductionValue)*100;
			map.put("maxDeductionValue", d.intValue());
		}else {
			map.put("maxDeductionValue", 0);
		}
		if(id==null||StringUtils.isBlank(id)){
			if(couponItemService.queryByItemCode(itemcode) > 0) {
				 msg = "fail" ;
			}else{
				map.put("createid", user.getId());
				map.put("createtime", new Date());
				couponItemService.insertItem(map);
			}
		}else{
			map.put("updateid", user.getId());
			couponItemService.updateByCouponId(map);
		}
		response.getWriter().print(msg);
		return null;
	}
 
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="CouponItem删除数据")
	@RequestMapping("/deleteCouponItemById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if (id!=null && id > 0) {
			couponItemService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
