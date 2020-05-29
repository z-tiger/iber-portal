package com.iber.portal.controller.coupon;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import com.iber.portal.common.Pager;
import com.iber.portal.model.base.CounponList;
import com.iber.portal.model.base.CounponName;
import com.iber.portal.model.coupon.CouponStrategy;
import com.iber.portal.service.coupon.CouponStrategyService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.YuanTransToPennyUtil;

@Controller
public class CouponStrategyController {

	private final static Logger log = Logger
			.getLogger(CouponStrategyController.class);
	@Autowired
	private CouponStrategyService couponDeployService;

	// Servrice start
	@Autowired(required = false)
	// 自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	// private CouponLogService CouponLogService;
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description = "优惠券策略配置页面")
	@RequestMapping(value = "/couponDeployPage", method = { RequestMethod.GET })
	public String xCouponLog_page(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("优惠券策略配置页面");
		return "coupon/couponStrategy";
	}

	@SystemServiceLog(description = "优惠券类型查询")
	@RequestMapping(value = "/couponnames", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String couponCombobox(HttpServletRequest request,
			HttpServletResponse response,String cityCode) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		List<CounponName> counponName = couponDeployService.selectCouponName(cityCode);
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (counponName != null && counponName.size() > 0) {
			for (int i = 0; i < counponName.size(); i++) {
				CounponName icounponName = counponName.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + icounponName.getId() + "\",");
				tree.append("\"text\":\"" + icounponName.getName() + "\"");
				if (i < counponName.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");

		response.getWriter().print(tree.toString());
		return null;
	}

	/** 查询优惠券策略配置信息列表 */
	@SystemServiceLog(description = "查询优惠券策略配置信息列表")
	@RequestMapping(value = "/queryCouponList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String CouponList(int page, int rows, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		// 优惠券类型
		String itemCode = request.getParameter("itemName");
		// 优惠券状态
		String couponState = request.getParameter("couponState");
		// 生效时间
		String queryDateFrom1 = request.getParameter("queryDateFrom");
		String cityCode = request.getParameter("city_code");
		if (StringUtils.isNotBlank(queryDateFrom1)) {

			String queryDateFrom = queryDateFrom1 + " 00:00:00";
			map.put("queryDateFrom", queryDateFrom);
		}
		map.put("itemCode", itemCode);
		map.put("couponState", couponState);
		map.put("cityCode", cityCode);
		Pager<CounponList> data = couponDeployService.queryPageList(map);
		// int totalRecords = couponDeployService.getAllNum(map);
		String json = Data2Jsp.Json2Jsp(data);
		response.getWriter().print(json);
		return null;
	}

	private boolean isMinGreaterThanMax(Integer min, Integer max, HttpServletResponse response)throws IOException {
		if(max > 0 && min > max){
			response.getWriter().print("minGreaterThanMax");
			return true;
		}
		return false;
	}

	/**
	 * 添加或修改数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@SystemServiceLog(description = "优惠券配置策略添加或修改数据")
	@RequestMapping(value = "/saveOrUpdateCoupon", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String saveOrUpdate( HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("优惠券配置策略新增或更新");
		String id = request.getParameter("id");
		Integer min = YuanTransToPennyUtil.TransToPenny(request.getParameter("min"));
		Integer max = YuanTransToPennyUtil.TransToPenny(request.getParameter("max"));
		Integer balance = YuanTransToPennyUtil.TransToPenny(request.getParameter("balance"));
		String number = request.getParameter("number");
		String startTime = request.getParameter("startTime");
		String deadline = request.getParameter("deadline");
		String couUseType = request.getParameter("couUseType");
		Integer minUseValue = YuanTransToPennyUtil.TransToPenny(request.getParameter("minUseValue"));
		Integer maxDeductionValue = YuanTransToPennyUtil.TransToPenny(request.getParameter("maxDeductionValue"));
		String itemCode = request.getParameter("itemName");
		String cityCode = request.getParameter("city_code");
		if(isMinGreaterThanMax(min, max, response)){
			return null;
		}

        CouponStrategy entity = new CouponStrategy();
        if(-1!=balance && -1!=min && -1!=max && -1!=minUseValue){
        	 entity.setBalance(balance);
        	 entity.setMin(min);
        	 entity.setMax(max);
        }else {
        	response.getWriter().print("num-wrong");
        	return null;
        }
        entity.setMaxDeductionValue(maxDeductionValue);
        if(StringUtils.equals(couUseType, "2")) {
        	entity.setTotal(balance);
        }else {
        	entity.setTotal(Integer.valueOf(number)*balance);
        }
        entity.setNumber(Integer.valueOf(number));
        entity.setUseType(Integer.valueOf(couUseType));
        entity.setItemCode(itemCode);
        entity.setDeadline(Integer.valueOf(deadline));
        entity.setCityCode(cityCode);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTime.StringToDate(startTime));
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        entity.setStartTime1(calendar.getTime());
        entity.setMinUseValue(minUseValue);
        
        if(StringUtils.isBlank(id)){ //id 为空即是添加 一条记录
        	entity.setCreateTime1(new Date());
			couponDeployService.insert(entity);
        }else {
        	entity.setId(Integer.valueOf(id));
			couponDeployService.updateByPrimaryKey(entity);
        }
		response.getWriter().print("success");
		return null;
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@SystemServiceLog(description = "优惠券配置策略删除数据")
	@RequestMapping("/deleteCouponById")
	public void delete(Integer id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		log.info("优惠券配置策略删除");
		if (id != null && id > 0) {
			couponDeployService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}

}
