package com.iber.portal.controller.city;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.Area;
import com.iber.portal.model.base.City;
import com.iber.portal.model.task.TaskScoreStrategy;
import com.iber.portal.service.base.AreaService;
import com.iber.portal.service.task.TaskScoreStrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地区管理
 * @author Administrator
 *
 */
@Controller
public class AreaManageController extends MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AreaService areaService;

	/**
	 * 地区管理
	 */
	@SystemServiceLog(description = "地区管理")
	@RequestMapping(value = "area_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cityCode = getUser(request).getCityCode();
        City city = getCity(request, cityCode);
        request.setAttribute("lat", city.getLatitude());
        request.setAttribute("lng", city.getLongitude());
		return "/city/areaManagement";
	}

    /**
     * 地区管理列表
     */
    @SystemServiceLog(description = "地区管理分页查询")
    @RequestMapping(value = "area_management_page" , method = { RequestMethod.GET, RequestMethod.POST })
    public String gridAreaPage(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Pager<HashMap> pager = areaService.queryPageList(page,rows,request);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }


    /**
     * 新增，或修改
     */
    @SystemServiceLog(description = "新增修改地区")
    @RequestMapping(value = "area_management_saveorupdate" , method = { RequestMethod.GET, RequestMethod.POST })
    public String saveOrUpdate(Area area, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        areaService.saveOrUpdate(area,request, response);
        return null;
    }

    /**
     * 区域网点分页
     */
    @SystemServiceLog(description = "区域网点分页查询")
    @RequestMapping(value = "area_management_park" , method = { RequestMethod.GET, RequestMethod.POST })
    public String gridAreaParkPage(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Pager<HashMap> pager = areaService.queryAreaParkPageList(page,rows,request);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    /**
     * 查询网点（未绑定该区域的）
     */
    @SystemServiceLog(description = "查询未绑定到区域下的网点")
    @RequestMapping(value = "area_management_unbind" , method = { RequestMethod.GET, RequestMethod.POST })
    public String gridAreaParkUnBindPage(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Pager<HashMap> pager = areaService.queryAreaParkUnBindPageList(page,rows,request);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    /**
     * 绑定区域网点
     */
    @SystemServiceLog(description = "绑定区域网点")
    @RequestMapping(value = "bindAreaPark" , method = { RequestMethod.GET, RequestMethod.POST })
    public String bindAreaPark( HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        areaService.bindAreaPark(request,response);
        return null;
    }

    /**
     * 移除区域网点
     */
    @SystemServiceLog(description = "移除区域网点")
    @RequestMapping(value = "unbindAreaPark" , method = { RequestMethod.GET, RequestMethod.POST })
    public String unbindAreaPark( HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        areaService.unbindAreaPark(request,response);
        return null;
    }

    /**
     * 根据城市分组获取区域网点
     */
    @SystemServiceLog(description = "根据城市分组获取区域网点")
    @RequestMapping(value = "get_area_group_by_city" , method = { RequestMethod.GET, RequestMethod.POST })
    public String getAreaGroupByCity( HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        List<HashMap> group = areaService.getAreaGroupByCity();
        response.getWriter().print(JSON.toJSON(group));
        return null;
    }

}
