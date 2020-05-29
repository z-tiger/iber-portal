package com.iber.portal.controller.charging;

import java.util.Date;
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
import com.iber.portal.model.charging.ChargingPileCar;
import com.iber.portal.model.charging.ChargingPileType;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.ChargingPileCarService;
import com.iber.portal.service.charging.ChargingPileTypeService;

@Controller
public class ChargingPileTypeController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private ChargingPileTypeService chargingPileTypeService;
	
	@Autowired
	private ChargingPileCarService chargingPileCarService;
	
	
	/**
	 * @describe 充电桩类型页面
	 * 
	 */
	@SystemServiceLog(description="充电桩类型页面")
	@RequestMapping(value = "/charging_pile_type_page", method = { RequestMethod.GET })
	public String chargingPileType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电桩类型页面");
		return "charging/chargingPileType";		
	}
	
	/**
	 * @describe 充电桩类型列表
	 */
	@SystemServiceLog(description="充电桩类型列表")
	@RequestMapping(value = "/charging_pile_type_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileTypeList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("充电桩类型列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String brandName = request.getParameter("brand_name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("brandName", brandName);
		Pager<ChargingPileType> pager = chargingPileTypeService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新充电桩类型
	 * 
	 */
	@SystemServiceLog(description="保存更新充电桩类型")
	@RequestMapping(value = "/charging_pile_type_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileTypeSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新充电桩类型");
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		String brandName = request.getParameter("brand_name");
		String pileType = request.getParameter("pile_type");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			ChargingPileType currObj = chargingPileTypeService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setBrandName(brandName);
				currObj.setPileType(pileType);
				currObj.setUpdateCreate(new Date());
				currObj.setUpdateId(user.getId());
				chargingPileTypeService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			ChargingPileType obj = new ChargingPileType();
			obj.setBrandName(brandName);
			obj.setPileType(pileType);
			obj.setCreateId(user.getId());
			obj.setCreateTime(new Date());
			chargingPileTypeService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除充电桩类型
	 * 
	 */
	@SystemServiceLog(description="删除充电桩类型")
	@RequestMapping(value = "/charging_pile_type_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileTypeDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除充电桩类型");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			chargingPileTypeService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	/**
	 * @describe 设置品牌支持
	 * 
	 */
	@SystemServiceLog(description="设置品牌支持")	
	@RequestMapping(value = "/charging_pile_type_set_brand", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileTypeSetBrand(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("设置品牌支持");
		response.setContentType("text/html;charset=utf-8");
		String typeId = request.getParameter("typeId");
		String brandId = request.getParameter("brandId");
		SysUser user = getUser(request);
		ChargingPileCar model = new ChargingPileCar();
		model.setCarBrandId(brandId);
		model.setCreateId(user.getId());
		model.setCreateTime(new Date());
		model.setTypeId(Integer.parseInt(typeId));
		chargingPileCarService.insertSelective(model);
		response.getWriter().print("success");
		return null;
	}
	/**
	 * @describe 取消品牌支持
	 * 
	 */
	@SystemServiceLog(description="取消品牌支持")	
	@RequestMapping(value = "/charging_pile_type_cancel_brand", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileTypeCancelBrand(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("取消品牌支持");
		response.setContentType("text/html;charset=utf-8");
		String typeId = request.getParameter("typeId");
		String brandId = request.getParameter("brandId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("typeId", Integer.parseInt(typeId));
		paramMap.put("brandId", Integer.parseInt(brandId));
		chargingPileCarService.deleteByTypeIdAndBrandId(paramMap);
		response.getWriter().print("success");
		return null;
	}
	
	
}
