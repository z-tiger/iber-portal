package com.iber.portal.controller.version;

import com.alibaba.fastjson.JSONObject;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.function.AppVersion;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.version.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

@Controller
public class MemberAppVersionController extends MainController{
	
	@Autowired
	private AppVersionService appVersionService ;
	@Autowired
	private SysParamService sysParamService ;
	/**
	 * 版本管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="版本管理")
	@RequestMapping(value = "/app_version", method = { RequestMethod.GET })
	public String app_version(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "version/memberAppVersionsBox";		
	}
	/**
	 * 版本管理列表
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="版本管理列表")
	@RequestMapping(value = "/app_version_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String app_version_list(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> record = new  HashMap<String, Object>();
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		record.put("page", pageInfo.get("first_page"));
		record.put("size", pageInfo.get("page_size"));
		List<AppVersion> data= appVersionService.selectByAPPVersionList(record) ;
		JSONObject obj = new JSONObject();
		obj.put("total", appVersionService.selectByAPPVersionListRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		return null;		
	}
	/**
	 * 增加版本
	 * @param request
	 * @param response
	 * @param appFile
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="增加版本")
	@RequestMapping(value = "/add_app_versions", method = { RequestMethod.GET , RequestMethod.POST })
	public String addVersionsRearview(HttpServletRequest request, HttpServletResponse response,MultipartFile appFile)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String appCategory = "member";
		String appName = request.getParameter("appName");
		String versionNo = request.getParameter("versionNo");
		String isForceUpdate = request.getParameter("isForceUpdate");
		String currentRecord = request.getParameter("currentRecord");
		String appType = request.getParameter("type");
		String appDesc = request.getParameter("appDesc");
		if ("employee".equals(appType)){
			appCategory = "employee";
		}
		AppVersion appVersion = new AppVersion() ;
		if (id!=null && !id.equals("")) {
			appVersion = appVersionService.selectByPrimaryId(Integer.parseInt(id));
		}
		if(appFile != null) {
			String versionUrl = uploadFileCDN(appFile) ;
			appVersion.setAppSize(Double.parseDouble((appFile.getSize()/1024)+""));
			appVersion.setDownloadUrl(versionUrl);
		}
		if (id!=null && !id.equals("")) {
			appVersion.setAppCategory(appCategory) ;
			appVersion.setAppDesc(appDesc) ;
			appVersion.setAppName(appName) ;
			appVersion.setCurrentRecord(Integer.parseInt(currentRecord)) ;
			appVersion.setIsForceUpdate(isForceUpdate) ;
			appVersion.setPublishDate(new Date()) ;
			appVersion.setVersionNo(versionNo) ;
			appVersion.setAppType(appType);
			appVersionService.updateByPrimaryKeySelective(appVersion);
		}else{	
			appVersion.setAppCategory(appCategory) ;
			appVersion.setAppDesc(appDesc) ;
			appVersion.setAppName(appName) ;
			appVersion.setCurrentRecord(Integer.parseInt(currentRecord)) ;
			appVersion.setIsForceUpdate(isForceUpdate) ;
			appVersion.setPublishDate(new Date()) ;
			appVersion.setVersionNo(versionNo) ;
			appVersion.setAppType(appType);
			appVersionService.insertSelective(appVersion);
		}
		response.getWriter().print("success");
		return null;	
	}	
	
	private String uploadFileCDN(MultipartFile file) throws Exception{
        String filename = file.getOriginalFilename();  
        InputStream is = file.getInputStream();  
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
      //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String fileURL = oss.putObject(newFileName, is, "upgrade/");
	    return fileURL;
	}
	/**
	 * 删除版本
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除版本")
	@RequestMapping(value = "/del_app_versions", method = { RequestMethod.GET , RequestMethod.POST })
	public String versionsRearviewDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			appVersionService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}	
}
