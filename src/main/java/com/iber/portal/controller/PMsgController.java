package com.iber.portal.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.dao.base.PMsgMapper;
import com.iber.portal.model.base.PMsg;
import com.iber.portal.service.sys.SysParamService;

@Controller
public class PMsgController extends MainController{
	
	@Autowired
	private PMsgMapper pmsgMapper;
	
	@Autowired
    private SysParamService sysParamService ;
	@SystemServiceLog(description="图文消息")	
	@RequestMapping(value = "/pmsg", method = { RequestMethod.GET, RequestMethod.POST })
	public String pmsgLook(int id, String tf , HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PMsg pmsg = pmsgMapper.selectByPrimaryKey(id);
		//String path = request.getContextPath();
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		if(tf.equalsIgnoreCase("t")){
			pmsgMapper.updateRecords(id);
		}
		//request.getRequestDispatcher(pmsg.getMsgUrl()).forward(request, response);
		response.sendRedirect(sysParamService.selectByKey("psmg_http_url").getValue() + pmsg.getMsgUrl());
		return null;
		//return pmsg.getMsgUrl();
	}
	
	@SystemServiceLog(description="跳转图文消息页面")	
	@RequestMapping(value = "/protal_psg", method = { RequestMethod.GET })
	public String pmsg(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		return "msg/pmsg";
	}
	@SystemServiceLog(description="图文消息页面数据列表")
	@RequestMapping(value = "/protal_psg_list_data", method = { RequestMethod.GET, RequestMethod.POST })
	public String pmsgListData(int page, int rows, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String title = request.getParameter("title");
		String kssj = request.getParameter("kssj");
		String jssj = request.getParameter("jssj");
		String status = request.getParameter("status");
		map.put("title", title);
		map.put("kssj", kssj);
		map.put("jssj", jssj);
		map.put("status", status);
		List<PMsg> data = pmsgMapper.selectAllPmsg(map);
		int totalRecords = pmsgMapper.selectAllPmsgRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="删除图文消息页面数据")
	@RequestMapping(value = "/protal_psg_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String pmsgDel(int id, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
	    //PMsg pmsg = pmsgMapper.selectByPrimaryKey(id);
//	    if(!StringUtils.isBlank(pmsg.getMsgUrl())){
//	    	String tmp = pmsg.getMsgUrl().substring(pmsg.getMsgUrl().indexOf("/") + 1);
//	    	tmp = tmp.substring(0, tmp.indexOf("/"));
//	    	String path = request.getSession().getServletContext().getRealPath("pmsg/" + tmp);
//	    	deleteDir(new File(path));
//	    }
	    int r = pmsgMapper.deleteByPrimaryKey(id);
	    if(r >0 ){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	@SystemServiceLog(description="图文消息审核")
	@RequestMapping(value = "/protal_psg_ex", method = { RequestMethod.GET, RequestMethod.POST })
	public String pmsgEx(int id , String msgStatus, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		if(StringUtils.isBlank(msgStatus)){
			response.getWriter().print("statusErr");
			return null;
		}
		String sysUserName = getUser(request).getName();
	   int r = pmsgMapper.updateEx(id, msgStatus,sysUserName);
	    if(r >0 ){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	private String uploadMultipartFile(MultipartFile multipartFile,
			HttpServletRequest request, String useFile) throws Exception {
		String filename = multipartFile.getOriginalFilename();  
	    InputStream is = multipartFile.getInputStream();  
	    String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
	    String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String fileURL = oss.putObject(newFileName, is, useFile +"/");
		return fileURL;
	}
	
	@SystemServiceLog(description="增加或修改图文消息")
	@RequestMapping(value = "/protal_psg_save_update", method = { RequestMethod.GET, RequestMethod.POST })
	public String pmsgSaveOrUpdate(PMsg pMsg, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
//		String dir = sysParamService.selectByKey("pmsg_file_dir").getValue();
		
		String msgPictureFile = pMsg.getMsgPicture().getOriginalFilename();
		String msgPictureFileName = msgPictureFile.substring(msgPictureFile.lastIndexOf(".") +1);
		String msgFirstP = "";
		if(msgPictureFileName.equalsIgnoreCase("png")||msgPictureFileName.equalsIgnoreCase("jpeg")
				||msgPictureFileName.equalsIgnoreCase("jpg")
				||msgPictureFileName.equalsIgnoreCase("bmp")){
			msgFirstP = uploadMultipartFile(pMsg.getMsgPicture(),request,"pmsg");
		}else{
			response.getWriter().print("picFormatErr");
			return null;
		}
		int r = -1;
		if(null!=pMsg.getUploadFile()&&!pMsg.getUploadFile().isEmpty()){
			String docURL = uploadMultipartFile(pMsg.getUploadFile(),request,"pmsg");
			pMsg.setMsgUrl(docURL);
			
//			String fileName = pMsg.getUploadFile().getOriginalFilename();
//			String fileNameP = fileName.substring(fileName.lastIndexOf(".") +1);
//			if(!fileNameP.equalsIgnoreCase("zip")){
//				response.getWriter().print("fileFormatErr");
//				return null;
//			}
			
//			String tmpFileName = CharacterUtils.getRandomString2(10);
//			String tmpPath =dir + tmpFileName+ ".zip";	//上传文件的临时目录
//			pMsg.getUploadFile().transferTo(new File(tmpPath));
//			EctractZip.unZip(tmpPath, dir + "pmsg" +  File.separator + tmpFileName +    File.separator);
//			String msgFirstP = getMsgFirstP(dir + "pmsg" +  File.separator + tmpFileName  + "/index.html");
//			
//			pMsg.setMsgUrl( "pmsg/" + tmpFileName+ "/index.html");
//			if(!StringUtils.isBlank(msgFirstP)){
//				pMsg.setMsgFirstP("pmsg/" + tmpFileName +"/"+ msgFirstP);
//			}
//			new File(tmpPath).delete();
			
//			String dir = CharacterUtils.getRandomString2(10);
//			String path =  request.getSession().getServletContext().getRealPath("pmsg");
//			String tmpPath = path + File.separator + dir+ ".zip";	//上传文件的临时目录
//			pMsg.getUploadFile().transferTo(new File(tmpPath));
//			EctractZip.unZip(tmpPath, path +  File.separator+ dir +    File.separator);
//			//EctractZip.Ectract(tmpPath, descPath);
//			//pMsg.setMsgUrl( "pmsg/" + dir+ "/index.html");
//			//new File(tmpPath).delete();
//			String msgFirstP = getMsgFirstP(request.getSession().getServletContext().getRealPath("pmsg/" + dir+ "/index.html"));
//			
//			Map<String, String> fileMap = new HashMap<String, String>(); //file_serivce_http_url
//			fileMap.put("userfile", tmpPath);
//			String s = HttpPostArgument.formUpload(sysParamService.selectByKey("file_serivce_http_url").getValue() + "PMsgFileServlet", null, fileMap);
//			if(s.equalsIgnoreCase("fail")){
//				response.getWriter().print("fail");
//				return null;
//			}
//			pMsg.setMsgUrl( "pmsg/" + s+ "/index.html");
//			if(!StringUtils.isBlank(msgFirstP)){
//				pMsg.setMsgFirstP("pmsg/" + s +"/"+ msgFirstP);
//			}
//			//删除文件
//			deleteDir(new File(path +  File.separator+ dir+    File.separator));
//			new File(tmpPath).delete();
		}else{
			
		}
		if(pMsg.getId() != null){
			//删除原文件
			 PMsg dbPmsg = pmsgMapper.selectByPrimaryKey(pMsg.getId());
//		    if(!StringUtils.isBlank(dbPmsg.getMsgUrl()) && !pMsg.getUploadFile().isEmpty()){
//		    	String tmp = dbPmsg.getMsgUrl().substring(dbPmsg.getMsgUrl().indexOf("/") + 1);
//		    	tmp = tmp.substring(0, tmp.indexOf("/"));
//		    	//String path = request.getSession().getServletContext().getRealPath("pmsg/" + tmp);
//		    	deleteDir(new File(dir + "pmsg" + File.separator + tmp));
//		    }
			 pMsg.setId(dbPmsg.getId());
			 pMsg.setMsgFirstP(msgFirstP);
			 pMsg.setUpdateTime(new Date());
			 pMsg.setUpdateUser(getUser(request).getName());
			r = pmsgMapper.updateByPrimaryKeySelective(pMsg);
		}else{
			pMsg.setMsgFirstP(msgFirstP);
			pMsg.setCreateTime(new Date());
			pMsg.setMsgStatus("1");
			pMsg.setCreateUser(getUser(request).getName());
			r = pmsgMapper.insertSelective(pMsg);
		}
		if(r >0 ){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	

	private  boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }

	private String getMsgFirstP(String indexPath) {
		//Parser parser = Parser.createParser(indexPath, "UTF-8");
		try {
			Parser parser = new  Parser(indexPath);
			NodeFilter filter = new TagNameFilter("img");
			NodeList nodeList =  parser.extractAllNodesThatMatch(filter);
			if(null != nodeList && nodeList.size() > 0){
				ImageTag node = (ImageTag) nodeList.elementAt(0);
				return node.getAttribute("src");
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}
}
