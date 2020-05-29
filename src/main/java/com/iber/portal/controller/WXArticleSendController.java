package com.iber.portal.controller;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Picture;
import com.iber.portal.dao.base.PMsgMapper;
import com.iber.portal.model.base.PMsg;
import com.iber.portal.service.sys.SysParamService;

@Controller
public class WXArticleSendController extends MainController{
	
	@Autowired
	private PMsgMapper pmsgMapper;
	
	@Autowired
    private SysParamService sysParamService ;

	
	@SystemServiceLog(description="微信文章消息")	
	@RequestMapping(value = "/wx_article_psg", method = { RequestMethod.GET, RequestMethod.POST })
	public String wxPmsgLook(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		return "msg/wxPage";
	}
	
	
	@SystemServiceLog(description="获取所有的微信文章消息")	
	@RequestMapping(value = "/getAllArticle", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllArticle(int page, int rows,HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page,rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String title = request.getParameter("title");
		map.put("title", title);
		List<PMsg> data = pmsgMapper.selectArticlePmsg(map);
		int totalRecords = pmsgMapper.selectArticlePmsgRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="保存微信文章消息")	
	@RequestMapping(value = "/article_msg_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveInfo(PMsg msg,HttpServletRequest request, HttpServletResponse response,MultipartFile file)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String filename = file.getOriginalFilename();
		if(!filename.equals("")){
			CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	        File file1 = fi.getStoreLocation();
	        InputStream is =   Picture.resizePNG(file1, 200, 100,filename.substring(filename.lastIndexOf(".") +1)) ;
			String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
	        
	        //文件上传到CDN
	        String endpoint = sysParamService.selectByKey("endpoint").getValue();
	        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
	        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
	        String bucketName = sysParamService.selectByKey("bucketName").getValue();
	        String dns = sysParamService.selectByKey("dns").getValue();
		    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
			String carFileUrl = oss.putObject(newFileName, is, "msg/");
			msg.setMsgFirstP(carFileUrl);
		}
		msg.setMsgStatus("2");
		int r = pmsgMapper.insertSelective(msg);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	@SystemServiceLog(description="修改微信文章消息")	
	@RequestMapping(value = "/article_mrg_edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String editInfo(PMsg msg,HttpServletRequest request, HttpServletResponse response,MultipartFile efile)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String filename = efile.getOriginalFilename(); 
		if(!filename.equals("")){
			CommonsMultipartFile cf= (CommonsMultipartFile)efile; //这个myfile是MultipartFile的
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
	        File file1 = fi.getStoreLocation();
	        InputStream is =   Picture.resizePNG(file1, 200, 100,filename.substring(filename.lastIndexOf(".") +1)) ;
			String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
	        
	        //文件上传到CDN
	        String endpoint = sysParamService.selectByKey("endpoint").getValue();
	        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
	        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
	        String bucketName = sysParamService.selectByKey("bucketName").getValue();
	        String dns = sysParamService.selectByKey("dns").getValue();
		    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		    
			String carFileUrl = oss.putObject(newFileName, is, "msg/");
			msg.setMsgFirstP(carFileUrl) ;
		}
		int r = pmsgMapper.updateByPrimaryKeySelective(msg);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	
	@SystemServiceLog(description="删除微信文章消息")	
	@RequestMapping(value = "/article_psg_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteInfo(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Integer id = Integer.valueOf(request.getParameter("id"));
		int r = pmsgMapper.deleteByPrimaryKey(id);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
}
