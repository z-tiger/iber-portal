package com.iber.portal.controller.ad;

import java.io.InputStream;
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
import com.iber.portal.model.ad.AdAttachment;
import com.iber.portal.service.ad.AdAttachmentService;
import com.iber.portal.service.sys.SysParamService;

@Controller
public class AdAttachmentController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AdAttachmentService adAttachmentService;
	
	@Autowired
    private SysParamService sysParamService ;
	
	/**
	 * @describe 广告图片附件页面
	 * 
	 */
	@SystemServiceLog(description="广告图片附件页面")
	@RequestMapping(value = "/ad_attachment_page", method = { RequestMethod.GET })
	public String adAttachment(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告图片附件页面");
		return "ad/adAttachment";		
	}
	
	/**
	 * @describe 广告图片多文件上传附件页面
	 * 
	 */
	@SystemServiceLog(description="广告图片多文件上传附件页面")
	@RequestMapping(value = "/ad_uploader_page", method = { RequestMethod.GET })
	public String adUploader(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告图片多文件上传附件页面");
		return "ad/uploader";		
	}
	/**
	 * @describe 广告图片多文件上传
	 * 
	 */
	@SystemServiceLog(description="广告图片多文件上传")
	@RequestMapping(value = "/ad_uploader_upload", method = { RequestMethod.GET , RequestMethod.POST })
	public String adUploaderUpload(HttpServletRequest request, HttpServletResponse response,MultipartFile file)
			throws Exception {
		log.info("广告图片多文件上传");
		
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
		String licenseFileUrl = oss.putObject(newFileName, is, "ad/");
		return "{\"status\":true,\"newName\":\""+licenseFileUrl+"\"}";		
	}
	
	/**
	 * @describe 广告图片附件列表
	 */
	@SystemServiceLog(description="广告图片附件列表")
	@RequestMapping(value = "/ad_attachment_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String adAttachmentList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告图片附件列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String attachName = request.getParameter("attachname");
		String attachType = request.getParameter("attach_type");
		String isShow = request.getParameter("is_show");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		String adName = request.getParameter("title");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("attachName", attachName);
		paramMap.put("attachType", attachType);
		paramMap.put("isShow", isShow);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		paramMap.put("adName", adName);
		Pager<AdAttachment> pager = adAttachmentService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新广告图片附件
	 * 
	 */
	@SystemServiceLog(description="保存更新广告图片附件")
	@RequestMapping(value = "/ad_attachment_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String adAttachmentSaveOrUpdate(HttpServletRequest request, HttpServletResponse response,MultipartFile file)
			throws Exception {
		log.info("保存更新广告图片附件");
		response.setContentType("text/html;charset=utf-8");
		String filename = file.getOriginalFilename(); 
		long size = file.getSize();
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
        
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
		String linkUrl = request.getParameter("linkUrl");
		String attachType = request.getParameter("attachType");
		String adId = request.getParameter("adId");
		
		if (id!=null && !id.equals("")) {
			AdAttachment currObj = adAttachmentService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				if(size != 0){
					currObj.setAttachName(filename);
					currObj.setAttachSize(String.valueOf(size));
					currObj.setUploadUrl(adFileUrl);
				}
				currObj.setAttachType(attachType);
				currObj.setAttachUrl("");
				currObj.setCreateTime(new Date());
				currObj.setAdId(Integer.parseInt(adId));
				currObj.setIsShow("0");
				currObj.setIsUpload(1);
				currObj.setLinkUrl(linkUrl);
				currObj.setUrlFrom("1");
				adAttachmentService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			AdAttachment obj = new AdAttachment();
			obj.setAttachName(filename);
			obj.setAttachSize(String.valueOf(size));
			obj.setAttachType(attachType);
			obj.setAttachUrl("");
			obj.setCreateTime(new Date());
			obj.setAdId(Integer.parseInt(adId));
			obj.setIsShow("0");
			obj.setIsUpload(1);
			obj.setLinkUrl(linkUrl);
			obj.setUploadUrl(adFileUrl);
			obj.setUrlFrom("1");
			adAttachmentService.insertSelective(obj);
	
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除广告图片附件
	 * 
	 */
	@SystemServiceLog(description="删除广告图片附件")
	@RequestMapping(value = "/ad_attachment_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String adAttachmentDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除广告图片附件");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			adAttachmentService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 审核广告图片
	 * 
	 */
	@SystemServiceLog(description="审核广告图片")
	@RequestMapping(value = "/ad_attachment_auditing", method = { RequestMethod.GET , RequestMethod.POST })
	public String adAttachmentAuditing(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("审核广告图片");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			AdAttachment currObj = adAttachmentService.selectByPrimaryKey(Integer.parseInt(id));
			currObj.setIsShow("1");
			adAttachmentService.updateByPrimaryKeySelective(currObj);
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 广告图片附件
	 */
	@SystemServiceLog(description="广告图片附件")
	@RequestMapping(value = "/ad_attachment_preview", method = { RequestMethod.GET , RequestMethod.POST })
	public String adAttachmentPreview( HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告图片附件");
		response.setContentType("text/html;charset=utf-8");
		String adId = request.getParameter("id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adId", adId);
		Pager<AdAttachment> pager = adAttachmentService.getPreview(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
}
