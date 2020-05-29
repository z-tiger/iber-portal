package com.iber.portal.service.base;

import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.AreaMapper;
import com.iber.portal.dao.base.ParkMapper;
import com.iber.portal.model.base.Area;
import com.iber.portal.model.sys.SysUser;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 区域管理
 * @author zengfy
 */
@Service
public class AreaService {


    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private ParkMapper parkMapper;

    public Pager<HashMap> queryPageList(int page, int rows, HttpServletRequest request) {
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("from", from);
        paramMap.put("to", to);


        String area = request.getParameter("area");
        paramMap.put("area", area);

        String cityCode = request.getParameter("cityCode");
        paramMap.put("cityCode", cityCode);
        if ("00".equals(cityCode)){
            paramMap.put("cityCode", null);
        }
        List<HashMap> taskPools = areaMapper.findPage(paramMap);
        Integer totalCount = areaMapper.findPageTotalCount(paramMap);
        Pager<HashMap> pager = new Pager<HashMap>();
        pager.setDatas(taskPools);
        pager.setTotalCount(totalCount);
        return pager;
    }

    public void saveOrUpdate(Area area, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        Area count = areaMapper.queryByCityCodeAndAreaName(area.getCityCode(),area.getAreaName());
        if (area.getId()==null&&count==null){
            area.setCreator(user.getName());
            area.setCreateTime(new Date());
            areaMapper.insertSelective(area);
        }else if (area.getId()!=null){
            if (count==null||(count!=null&&count.getId().equals(area.getId()))){
                area.setModifier(user.getName());
                areaMapper.updateByPrimaryKeySelective(area);
            }else{
                response.getWriter().write("exist");
                return;
            }

        }else{
            response.getWriter().write("exist");
            return;
        }
        response.getWriter().write("ok");


    }

    public Pager<HashMap> queryAreaParkPageList(int page, int rows, HttpServletRequest request) {
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("from", from);
        paramMap.put("to", to);


        String id = request.getParameter("id");
        paramMap.put("id", id);

        String park = request.getParameter("park");
        paramMap.put("park", park);

        String cityCode = request.getParameter("cityCode");
        paramMap.put("cityCode", cityCode);
        if ("00".equals(cityCode)){
            paramMap.put("cityCode", null);
        }
        List<HashMap> taskPools = areaMapper.findAreaParkPage(paramMap);
        Integer totalCount = areaMapper.findAreaParkPageTotalCount(paramMap);
        Pager<HashMap> pager = new Pager<HashMap>();
        pager.setDatas(taskPools);
        pager.setTotalCount(totalCount);
        return pager;
    }

    public Pager<HashMap> queryAreaParkUnBindPageList(int page, int rows, HttpServletRequest request) {
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("from", from);
        paramMap.put("to", to);


        String id = request.getParameter("id");
        paramMap.put("id", id);

        String park = request.getParameter("park");
        paramMap.put("park", park);

        String address = request.getParameter("address");
        paramMap.put("address", address);

        String cityCode = request.getParameter("cityCode");
        paramMap.put("cityCode", cityCode);
        if ("00".equals(cityCode)){
            paramMap.put("cityCode", null);
        }
        List<HashMap> taskPools = areaMapper.findUnBindAreaParkPage(paramMap);
        Integer totalCount = areaMapper.findUnBindAreaParkPageTotalCount(paramMap);
        Pager<HashMap> pager = new Pager<HashMap>();
        pager.setDatas(taskPools);
        pager.setTotalCount(totalCount);
        return pager;
    }

    public void bindAreaPark(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String areaId = request.getParameter("areaId");
        String[] parkList = ids.replace("[","").replace("]","").split(",");

        try {
            parkMapper.updateAreaCode(parkList,areaId);
            response.getWriter().write("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unbindAreaPark(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String[] parkList = ids.replace("[","").replace("]","").split(",");

        try {
            parkMapper.updateAreaCode(parkList,"");
            response.getWriter().write("ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HashMap> getAreaGroupByCity() {
        return areaMapper.getAreaGroupByCity();
    }
}
