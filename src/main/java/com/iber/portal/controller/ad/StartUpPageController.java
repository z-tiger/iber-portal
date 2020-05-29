/**
 * 
 */
package com.iber.portal.controller.ad;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.ad.StartUpPage;
import com.iber.portal.service.ad.StartUpPageService;
import com.iber.portal.service.sys.SysParamService;


@Controller
public class StartUpPageController extends MainController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StartUpPageService startUpPageService;
	@Autowired
	private SysParamService sysParamService;
	/**
	* @Description:启动页管理页面
	* @Author:cuichongc
	* @date 2017-6-21  下午11:08:06  
	 */
	@SystemServiceLog(description="启动页管理页面")
	@RequestMapping(value ="/start_up_page",method={RequestMethod.GET})
	public String adStartUp(HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("启动页管理页面");
		return "ad/adStartUp";
	}
	
	/**
	* @Title:startUpList
	* @Description:启动页列表页面
	* @Author:cuichongc
	* @date 2017-6-27  下午4:07:53  
	* @return String
	* @throws Exception
	 */
	@SystemServiceLog(description="启动页列表页面")
	@RequestMapping(value="/start_up_list",method={RequestMethod.GET,RequestMethod.POST})
	public String startUpList(int rows,int page,HttpServletResponse response,HttpServletRequest request)throws Exception{
		log.info("启动页列表");
		response.setCharacterEncoding("utf-8");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String title = request.getParameter("title");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		map.put("title", title);
		map.put("queryDateFrom", queryDateFrom);
		map.put("queryDateTo", queryDateTo);
		Pager<StartUpPage> pager = startUpPageService.getAll(map);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	/**
	* @Title:addStartUpSaveUpdate
	* @Description:保存更新启动页图片
	* @Author:cuichongc
	* @date 2017-6-27  下午7:19:51  
	* @return String
	* @throws
	 */
	@SystemServiceLog(description="保存更新启动页图片")
	@RequestMapping(value="/add_startUp_saveUpdate",method={RequestMethod.GET,RequestMethod.POST})
	public String addStartUpSaveUpdate(HttpServletRequest request,HttpServletResponse response,MultipartFile file)throws Exception{
		response.setCharacterEncoding("utf-8");
		log.info("保存更新启动页图片");
		String fileName = file.getOriginalFilename();
		long size = file.getSize();
		String newFileName = UUID.randomUUID().toString()+ "." +fileName.substring(fileName.lastIndexOf(".")+1);
		
		//文件上传到CDN
		 String endpoint = sysParamService.selectByKey("endpoint").getValue();
	     String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
	     String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
	     String bucketName = sysParamService.selectByKey("bucketName").getValue();
	     String dns = sysParamService.selectByKey("dns").getValue();
		 OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		 InputStream is = file.getInputStream();
		 String adFileUrl = oss.putObject(newFileName, is, "ad/");
		 
		 String id = request.getParameter("id");
		 String title = request.getParameter("title");
		 String from = request.getParameter("queryDateFrom");
		 String to = request.getParameter("queryDateTo");
		 String linkUrl = request.getParameter("linkUrl");
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 if(id != null && !id.equals("")){
			StartUpPage currObj = startUpPageService.selectByPrimaryKey(Integer.parseInt(id));
			if(currObj != null){
					currObj.setTitle(title);
				if(size != 0){
					//currObj.setTitle(title);
					currObj.setUrl(adFileUrl);
					currObj.setSize(String.valueOf(size));
				}	
					
					currObj.setCreateTime(new Date());
					currObj.setUpdateTime(new Date());
					String ff = format.format(currObj.getStartTime());
					String tt = format.format(currObj.getEndTime());
					if((format.parse(from).compareTo(format.parse(to)))>=0){
						response.getWriter().print("error");
						return null;
					}else if(!from.equals(ff)){
						if(startUpPageService.getStartTime(from)>0){
							response.getWriter().print("start");
							return null;
						}
					}else if(!to.equals(tt)){
						if(startUpPageService.getEndTime(to)>0){
							response.getWriter().print("end");
							return null;
						}
					}
					currObj.setStartTime(format.parse(from));
					currObj.setEndTime(format.parse(to));
					currObj.setIsShow(Integer.parseInt("1"));
					currObj.setLinkUrl(linkUrl);
					startUpPageService.updateByPrimaryKeySelective(currObj);
				}
		 	}else{
					StartUpPage obj = new StartUpPage();
					obj.setTitle(title);
					obj.setUrl(adFileUrl);
					obj.setSize(String.valueOf(size));
					obj.setCreateTime(new Date());
					obj.setUpdateTime(new Date());
					int f = startUpPageService.getStartTime(from);
					int t = startUpPageService.getEndTime(to);
					if(f>0){
						response.getWriter().print("start");
						return null;
					}else if(t>0){
						response.getWriter().print("end");
						return null;
					}else{
						obj.setStartTime(format.parse(from));
						obj.setEndTime(format.parse(to));
					}
					if(from.equals(to)||(format.parse(from).compareTo(format.parse(to)))>0){
						response.getWriter().print("error");
						return null;
					}
					obj.setIsShow(Integer.parseInt("1"));
					obj.setLinkUrl(linkUrl);
					startUpPageService.insertSelective(obj);
		 	}
		 response.getWriter().print("success");
		 return null;
	}
	
	/**
	* @Title:startUpPageDel
	* @Description:选择删除启动页
	* @Author:cuichongc
	* @date 2017-6-27  下午9:07:27  
	* @return String
	* @throws Exception
	 */
	@SystemServiceLog(description="选择删除启动页")
	@RequestMapping(value="/start_up_page_del",method={RequestMethod.GET,RequestMethod.POST})
	public String startUpPageDel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("删除启动页");
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		if(id != null && !id.equals("")){
			startUpPageService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	

}
