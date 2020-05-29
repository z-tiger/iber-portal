package com.iber.portal.controller.fence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.CarGroup;
import com.iber.portal.model.base.CarGroupRelation;
import com.iber.portal.model.base.ElectronicFenceCarGroupRelation;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.fence.CarGroupRelationService;
import com.iber.portal.service.fence.CarGroupService;
import com.iber.portal.service.fence.ElectronicFenceCarGroupRelationService;
import com.iber.portal.vo.car.CarGroupVo;

/**
 * 车辆组管理
 * @author Administrator
 *
 */
@Controller
public class CarGroupController extends MainController {

	@Autowired
	private CarGroupService carGroupService;
	
	@Autowired
	private CarGroupRelationService carGroupRelationService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private ElectronicFenceCarGroupRelationService electronicFenceCarGroupRelationService ;
	/**
	 * 车辆类型
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆类型")
	@RequestMapping(value = "/car_group", method = { RequestMethod.GET })
	public String carType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "fence/carGroup";		
	}
	/**
	 * 车辆类型列表
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆类型列表")
	@RequestMapping(value = "/car_group_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
        }else{
    	   map.put("cityCode", user.getCityCode());
        }
		String name = request.getParameter("name");
		map.put("name", name);
		List<CarGroupVo> data = carGroupService.selectAll(map);
		int totalRecords = carGroupService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	/**
	 * 添加车辆组信息
	 * @param carGroup
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="添加车辆组信息")
	@RequestMapping(value = "/car_group_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeSave(CarGroup carGroup, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carGroup.setCreateTime(new Date());
		carGroup.setCreateName(getUser(request).getName());
		carGroupService.insertSelective(carGroup);
		response.getWriter().print("succ");
		return null;
	}
	/**
	 * 修改车辆组信息
	 * @param carGroup
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="修改车辆组信息")
	@RequestMapping(value = "/car_group_edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeEdit(CarGroup carGroup, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carGroup.setUpdateTime(new Date());
		carGroup.setUpdateName(getUser(request).getName());
		carGroupService.updateByPrimaryKeySelective(carGroup);
		response.getWriter().print("succ");
		return null;
	}
	/**
	 * 删除修改车辆组信息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除修改车辆组信息")
	@RequestMapping(value = "/car_group_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeDel(int id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carGroupService.deleteByPrimaryKey(id);
		response.getWriter().print("succ");
		return null;
	}
	
	/**根据城市编码查询网点信息*/
	@SystemServiceLog(description="根据城市编码查询网点信息")
	@RequestMapping(value = "/car_group_city_code", method = { RequestMethod.GET , RequestMethod.POST})
	public String parkByCityCode(String cityCode, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String groupName = request.getParameter("groupName") ;//车组名称
		List<CarGroup> data = null;
		if(cityCode != null) {
			if(cityCode.equals("00")){
				data = carGroupService.selectAllNotPage();
			}else{
				data = carGroupService.selectGroupByCityCodeAndGroupName(cityCode, groupName);
			}
		}
		
		JSONArray json = JSONArray.fromObject(data);
		response.getWriter().print(json.toString());
		return null;
	}
	/**
	 * 车辆组设置
	 * @param lpns
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆组设置")
	@RequestMapping(value = "/set_car_group", method = { RequestMethod.GET, RequestMethod.POST })
	public String set_car_group(String lpns, int groupId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		response.setContentType("text/html;charset=utf-8");
		try {
			if(lpns.indexOf(",") != -1){
				String[] arr = lpns.split(",");
				for(String lpn : arr){
					//通过组id和车牌号查询，不存在则添加
					List<CarGroupRelation> data = carGroupRelationService.selectLpnByGroupIdAndLpn(groupId, lpn) ;
					if(data.size() <= 0) {
						CarGroupRelation model = new CarGroupRelation(null,lpn,groupId) ;
						carGroupRelationService.insertSelective(model) ;
					}
				}
			}else{
				List<CarGroupRelation> data = carGroupRelationService.selectLpnByGroupIdAndLpn(groupId, lpns) ;
				if(data.size() <= 0) {
					CarGroupRelation model = new CarGroupRelation(null,lpns,groupId) ;
					carGroupRelationService.insertSelective(model) ;
				}
			}
			response.getWriter().print("succ");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	/**
	 * 车辆组查询
	 * @param page
	 * @param rows
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆组查询")
	@RequestMapping(value = "/car_group_lpn", method = { RequestMethod.GET, RequestMethod.POST })
	public String car_group_lpn(int page, int rows,int groupId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try { 
			Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("offset", pageInfo.get("first_page"));
			map.put("rows", pageInfo.get("page_size"));
			map.put("groupId", groupId) ;
			List<CarGroupRelation> data = carGroupRelationService.selectAll(map);
			int totalRecords = carGroupRelationService.selectAllRecords(map);
			String json = Data2Jsp.Json2Jsp(data, totalRecords);
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	/**
	 * 车辆组关系删除
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆组关系删除")
	@RequestMapping(value = "/car_group_relation_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String car_group_relation_del(String ids, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try {
			if(ids.indexOf(",") != -1){
				String[] arr = ids.split(",");
				for(String id : arr){
					carGroupRelationService.deleteByPrimaryKey(Integer.parseInt(id)) ;
				}
			}else{
				carGroupRelationService.deleteByPrimaryKey(Integer.parseInt(ids)) ;
			}
			response.getWriter().print("succ");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	/**
	 * 车辆组增加车辆
	 * @param groupId
	 * @param lpn
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆组增加车辆")
	@RequestMapping(value = "/car_group_add_car", method = { RequestMethod.GET, RequestMethod.POST })
	public String car_group_add_car(String groupId,String lpn, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try {
			
			Car car = carService.selectByLpn(lpn) ;
			if(car != null) {
				List<CarGroupRelation> data = carGroupRelationService.selectLpnByGroupIdAndLpn(Integer.parseInt(groupId), lpn) ;
				if(data.size() <= 0) {
					CarGroupRelation model = new CarGroupRelation(null,lpn,Integer.parseInt(groupId)) ;
					carGroupRelationService.insertSelective(model) ;
				}
				response.getWriter().print("succ");
			}else{
				response.getWriter().print("no");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	/**
	 * 增加电子围栏
	 * @param groupId
	 * @param fenceId
	 * @param category
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="增加电子围栏")
	@RequestMapping(value = "/car_group_add_fence", method = { RequestMethod.GET, RequestMethod.POST })
	public String car_group_add_fence(Integer groupId,Integer fenceId,String category, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("groupId", groupId) ;
		map.put("fenceId", fenceId) ;
		map.put("category", category) ;
		try {
			if(category.equals("park")){
				//判断网点是否设置电子围栏
				if(electronicFenceCarGroupRelationService.selectAllByGroupId(map).size() > 0){	
					response.getWriter().print("exist");
				}else{
					ElectronicFenceCarGroupRelation model = new ElectronicFenceCarGroupRelation(null,groupId,fenceId,new Date(),getUser(request).getName(),category) ;
					electronicFenceCarGroupRelationService.insertSelective(model) ;
					response.getWriter().print("succ");
				}
			}else if(category.equals("group")) {
				// 判断是否存在
				if(electronicFenceCarGroupRelationService.selectAllByGroupIdAndFenceId(map).size() > 0){
					response.getWriter().print("exist");
				}else{
					ElectronicFenceCarGroupRelation model = new ElectronicFenceCarGroupRelation(null,groupId,fenceId,new Date(),getUser(request).getName(),category) ;
					electronicFenceCarGroupRelationService.insertSelective(model) ;
					response.getWriter().print("succ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	
}
