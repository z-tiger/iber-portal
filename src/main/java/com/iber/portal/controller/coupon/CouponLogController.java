package com.iber.portal.controller.coupon;

import java.io.IOException;
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

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.coupon.CouponLog;
import com.iber.portal.service.coupon.CouponLogService;

/**
 * 
 * <br>
 * <b>功能：
 * <b>作者：
 * <b>日期：
 * <b>版权所有
 */ 
@Controller
public class CouponLogController{
	
	private final static Logger log= Logger.getLogger(CouponLogController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private CouponLogService CouponLogService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="CouponLog页面")
	@RequestMapping("/couponLogPage") 
	public String xCouponLog_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("CouponLog页面");
		return "coupon/CouponLog";
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="CouponLog页面数据")
	@RequestMapping("/dataListCouponLog") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String couponNo = request.getParameter("couponNo");
		String memberId = request.getParameter("memberId");
		String status = request.getParameter("status");
		String batchNo = request.getParameter("batchNo");
		String memberName = request.getParameter("memberName");
		String memberPhone = request.getParameter("memberPhone");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("couponNo",couponNo);
		paramMap.put("memberId",memberId);
		paramMap.put("status",status);
		paramMap.put("batchNo",batchNo);
		paramMap.put("memberName",memberName);
		paramMap.put("memberPhone",memberPhone);
		Pager<CouponLog> pager = CouponLogService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param CouponLog
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="CouponLog页面添加或修改数据")
	@RequestMapping("/saveOrUpdateCouponLog")
	public void saveOrUpdate(CouponLog entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			entity.setCreatetime(new Date()) ;
			CouponLogService.insert(entity);
		}else{
			CouponLogService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="CouponLog页面删除数据")
	@RequestMapping("/deleteCouponLogById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if (id!=null && id > 0 ) {
			CouponLogService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
