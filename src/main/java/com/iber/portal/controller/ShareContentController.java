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
import com.iber.portal.model.base.ShareContent;
import com.iber.portal.service.base.ShareContentService;

@Controller
public class ShareContentController extends MainController{

	@Autowired
	private ShareContentService shareContentService;
	
	/**分享内容的新增或者修改*/
	@SystemServiceLog(description="分享内容的新增或者修改")
	@RequestMapping(value = "/share_content_save_update", method = { RequestMethod.GET , RequestMethod.POST })
	public String shareContentSaveOrUpdate(ShareContent record, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		int r = -1;
		if(record.getId() != null){
			record.setUpdateTime(new Date());
			record.setUpdateBy(getUser(request).getId());
			r = shareContentService.updateByPrimaryKeySelective(record);
		}else{
			record.setCreateBy(getUser(request).getId());
			record.setCreateTime(new Date());
			r = shareContentService.insertSelective(record);
		}
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	} 
	
	/**分享内容的删除*/
	@SystemServiceLog(description="分享内容的删除")
	@RequestMapping(value = "/share_content_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String shareContentDel(int id, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		int r = shareContentService.deleteByPrimaryKey(id);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	@SystemServiceLog(description="分享内容")
	@RequestMapping(value = "/share_content", method = { RequestMethod.GET })
	public String shareContent(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		return "msg/shareContent";
	}
	
	/**分享内容*/
	@SystemServiceLog(description="分享内容数据")
	@RequestMapping(value = "/share_content_data", method = { RequestMethod.GET , RequestMethod.POST })
	public String shareContentDate(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<ShareContent> data = shareContentService.selectAllShareContent();
		String json = Data2Jsp.Json2Jsp(data, data.size());
		response.getWriter().print(json);
		return null;
	}
}
