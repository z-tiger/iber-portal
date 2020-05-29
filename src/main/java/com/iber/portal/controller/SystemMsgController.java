package com.iber.portal.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.model.base.SystemMsg;

@Controller
public class SystemMsgController extends MainController{

	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	@SystemServiceLog(description="系统通知设置")
	@RequestMapping(value = "/system_msg_save_update", method = { RequestMethod.GET , RequestMethod.POST })
	public String systemMsgSaveOrUpdate(SystemMsg record, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		int r = -1;
		if(record.getId() != null){
			record.setUpdateTime(new Date());
			record.setUpdateBy(getUser(request).getId());
			r = systemMsgMapper.updateByPrimaryKeySelective(record);
		}else{
			record.setCreateBy(getUser(request).getId());
			record.setCreateTime(new Date());
			r = systemMsgMapper.insertSelective(record);
		}
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	} 
	@SystemServiceLog(description="系统通知删除")
	@RequestMapping(value = "/system_msg_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String systemMsgDel(int id, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		int r = systemMsgMapper.deleteByPrimaryKey(id);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	@SystemServiceLog(description="系统通知页面跳转")
	@RequestMapping(value = "/system_msg", method = { RequestMethod.GET })
	public String systemMsg(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		return "msg/systemMsg";
	}
	@SystemServiceLog(description="系统通知数据")
	@RequestMapping(value = "/system_msg_data", method = { RequestMethod.GET , RequestMethod.POST })
	public String shareContentDate(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<SystemMsg> data = systemMsgMapper.selectAllSystemMsg();
		String json = Data2Jsp.Json2Jsp(data, data.size());
		response.getWriter().print(json);
		return null;
	}
}
