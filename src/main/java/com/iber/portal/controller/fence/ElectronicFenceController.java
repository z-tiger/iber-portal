package com.iber.portal.controller.fence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.ElectronicFence;
import com.iber.portal.model.base.ElectronicFenceGps;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.fence.ElectronicFenceCarGroupRelationService;
import com.iber.portal.service.fence.ElectronicFenceGpsService;
import com.iber.portal.service.fence.ElectronicFenceService;
import com.iber.portal.vo.fence.ElectronicFenceCarGroupRelationVo;

@Controller
public class ElectronicFenceController extends MainController {

	@Autowired
	private ElectronicFenceService electronicFenceService ;
	@Autowired
	private ElectronicFenceGpsService electronicFenceGpsService ;
	
	@Autowired
	private ElectronicFenceCarGroupRelationService electronicFenceCarGroupRelationService ;
	/**
	 * 根据组区域获取电子围栏
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="根据组区域获取电子围栏")
	@RequestMapping(value = "/electronic_fence", method = { RequestMethod.GET })
	public String carType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		return "fence/electronicFence";		
	}
	/**
	 * 根据组区域获取电子围栏
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="根据组区域获取电子围栏")
	@RequestMapping(value = "/electronic_fence_list", method = { RequestMethod.GET, RequestMethod.POST })
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
		
		String fenceName = request.getParameter("fenceName");
		map.put("fenceName", fenceName);
		List<ElectronicFence> data = electronicFenceService.selectAll(map);
		int totalRecords = electronicFenceService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	/**
	 * 添加电子围栏信息
	 * @param fencePoint
	 * @param electronicFence
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="添加电子围栏信息")
	@RequestMapping(value = "/electronic_fence_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeSave(String fencePoint ,ElectronicFence electronicFence, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		
		electronicFence.setCreateTime(new Date());
		electronicFence.setCreateName(getUser(request).getName());
		int len = electronicFenceService.insertModel(electronicFence,fencePoint);
		if(len > 0) {
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		} 
		
		return null;
	}
	/**
	 * 修改电子围栏信息
	 * @param fencePoint
	 * @param electronicFence
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="修改电子围栏信息")
	@RequestMapping(value = "/electronic_fence_edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeEdit(String fencePoint ,ElectronicFence electronicFence, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		electronicFence.setUpdateTime(new Date());
		electronicFence.setUpdateName(getUser(request).getName());
		int len = electronicFenceService.updateModel(electronicFence,fencePoint);
		if(len > 0) {
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		} 
		return null;
	}
	/**
	 * 删除电子围栏信息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除电子围栏信息")
	@RequestMapping(value = "/electronic_fence_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeDel(int id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		electronicFenceService.deleteByPrimaryKey(id);
		//清除电子围栏gps信息
		electronicFenceGpsService.deleteFenceGpsByFenceId(id) ;
		response.getWriter().print("succ");
		return null;
	}
	/**
	 * 根据fenceId获取电子围栏gps信息
	 * @param fenceId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="根据fenceId获取电子围栏gps信息")
	@RequestMapping(value = "/electronic_fence_gps", method = { RequestMethod.GET, RequestMethod.POST })
	public String electronic_fence_gps(int fenceId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<ElectronicFenceGps> data = electronicFenceGpsService.selectGpsByFenceId(fenceId) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;
	}
	/**
	 * 根据组区域获取电子围栏
	 * @param cityCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="根据组区域获取电子围栏")
	@RequestMapping(value = "/electronic_fence_city_code", method = { RequestMethod.GET, RequestMethod.POST })
	public String electronic_fence_city_code(String cityCode, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String fenceName = request.getParameter("fenceName");//电子围栏名称
		Map<String,String> map = new HashMap<String, String>() ;
		map.put("cityCode", cityCode) ;
		map.put("fenceName", fenceName) ;
		List<ElectronicFence> data = electronicFenceService.selectFenceByCityCode(map) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;
	}
	/**
	 * 移除网点电子围栏
	 * @param ids
	 * @param category
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="移除网点电子围栏")
	@RequestMapping(value = "/fence_group_relation_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String car_group_relation_del(String ids,String category , HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try {
			if(category.equals("park")) { //移除网点电子围栏
				Map<String,String> map = new HashMap<String, String>() ;
				map.put("groupId", ids) ;
				map.put("category", "park") ;
				electronicFenceCarGroupRelationService.deleteByGroupId(map) ;
			}else{ //移除车辆组电子围栏
				if(ids.indexOf(",") != -1){
					String[] arr = ids.split(",");
					for(String id : arr){
						electronicFenceCarGroupRelationService.deleteByPrimaryKey(Integer.parseInt(id)) ;
					}
				}else{
					electronicFenceCarGroupRelationService.deleteByPrimaryKey(Integer.parseInt(ids)) ;
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
	 * 电子围栏列表（组）
	 * @param page
	 * @param rows
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="电子围栏列表（组）")
	@RequestMapping(value = "/fence_group_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String fence_group_list(int page, int rows,int groupId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try { 
			Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("offset", pageInfo.get("first_page"));
			map.put("rows", pageInfo.get("page_size"));
			map.put("groupId", groupId) ;
			map.put("category", "group") ;
			List<ElectronicFenceCarGroupRelationVo> data = electronicFenceCarGroupRelationService.selectAll(map);
			int totalRecords = electronicFenceCarGroupRelationService.selectAllRecords(map);
			String json = Data2Jsp.Json2Jsp(data, totalRecords);
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	
	/**
	 * 获取电子围栏列表
	 * @param page
	 * @param rows
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-9-23 上午11:08:32
	 */
	@SystemServiceLog(description="获取电子围栏列表")
	@RequestMapping(value = "/fence_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String getFenceList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try { 
			Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("offset", pageInfo.get("first_page"));
			map.put("rows", pageInfo.get("page_size"));
			List<ElectronicFence> data = electronicFenceService.selectAll(map);
			int totalRecords = electronicFenceService.selectAllRecords(map);
			String json = Data2Jsp.Json2Jsp(data, totalRecords);
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	} 
	
}
