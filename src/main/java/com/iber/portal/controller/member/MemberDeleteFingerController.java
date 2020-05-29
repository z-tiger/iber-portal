package com.iber.portal.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.member.MemberBehavior;
import com.iber.portal.model.member.MemberDeleteFinger;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.member.MemberDeleteFingerService;

@Controller
public class MemberDeleteFingerController {
	
	private final static Logger log= Logger.getLogger(MemberBehaviorController.class);

	@Autowired(required=false)
	private MemberDeleteFingerService memberDeleteFingerService;
	
	@SystemServiceLog(description="指纹人脸删除记录")
    @RequestMapping("/getAllDeleteLog")
    public String behaviorList(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws IOException {
     response.setContentType("text/html;charset=utf-8");
     String name = request.getParameter("name");
     String phone = request.getParameter("phone");
     String deleteType = request.getParameter("deleteType");
//		 Integer deleteType = Integer.parseInt(request.getParameter("deleteType"));
     SysUser user = (SysUser) request.getSession().getAttribute("user");
     Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
     HashMap<String, Object> map = new HashMap<String, Object>();
     map.put("offset", pageInfo.get("first_page"));
     map.put("rows", pageInfo.get("page_size"));
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
     if(!StringUtils.isBlank(name)){
         map.put("name", name);
     }
     if(!StringUtils.isBlank(phone)){
         map.put("phone", phone);
     }
    if (StringUtils.isNotBlank(deleteType)) {
        map.put("deleteType", deleteType);
    }
    List<MemberDeleteFinger> data = memberDeleteFingerService.selectAll(map);
     int totalRecords = memberDeleteFingerService.selectAllRecords(map);
     String json = Data2Jsp.Json2Jsp(data, totalRecords);
     response.getWriter().print(json);
     return null;
    }
}
