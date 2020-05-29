package com.iber.portal.controller.member;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.charging.CarBrand;
import com.iber.portal.model.member.MemberRights;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.member.MemberRightsService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.FastJsonUtils;


@Controller
public class MemberRightsController extends MainController{
	
	private final static Logger log= Logger.getLogger(MemberRightsController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberRightsService memberRightsService; 
	
	@Autowired
    private SysParamService sysParamService ;
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="会员权益管理页面")
	@RequestMapping("/memberRights_page") 
	public String memberRights_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("会员权益管理页面");
		return "member/memberRights" ;
	}
	
	
	/**
	 * 数据列表
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" 会员权益数据")
	@RequestMapping("/dataListMemberRights") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String rightsName = request.getParameter("rightsName");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("rightsName", rightsName);
		Pager<MemberRights> pager = memberRightsService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/*@SystemServiceLog(description=" 查询所有的会员权益数据")
	@RequestMapping("/queryAllMemberRights") 
	public void  queryAllMemberRights(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		List<MemberRights> list  = memberRightsService.queryAllMemberRights();
		response.getWriter().print(FastJsonUtils.toJson(list));
	}*/
	
	
	/**
	 * 添加或修改会员权益
	 * @param MemberRights
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="添加或修改会员权益数据")
	@RequestMapping("/saveOrUpdateMemberRights")
	public void saveOrUpdate(MemberRights memberRights,HttpServletRequest request, HttpServletResponse response,MultipartFile file) throws Exception{
		log.info("保存更新会员权益");
		response.setContentType("text/html;charset=utf-8");
		if(!memberRights.getIconUrlMultipartFile().isEmpty()){
			String iconUrl = uploadIconGrayUrlMultipartFile(memberRights,request,"iconUrl");
			memberRights.setIconUrl(iconUrl);
		}
		if(!memberRights.getGrayIconUrlMultipartFile().isEmpty()){
			String grayIconUrl = uploadIconGrayUrlMultipartFile(memberRights,request,"grayIconUrl");
			memberRights.setGrayIconUrl(grayIconUrl);
		}
		SysUser user = getUser(request);
//		if(memberRights.getId()!=null && !memberRights.getId().equals("")){
		if(null!=memberRights && null!=memberRights.getId()){
			memberRights.setUpdateTime(new Date());
			memberRights.setUpdateId(user.getId());
			memberRightsService.updateByPrimaryKeySelective(memberRights);
		}else{
			memberRights.setCreateId(user.getId());
			memberRights.setCreateTime(new Date());
			memberRightsService.insertSelective(memberRights);
		}
		/*String id = request.getParameter("id");
		String rightsName = request.getParameter("rightsName");
		String descUrl = request.getParameter("descUrl");
		String grayIconUrl =request.getParameter("grayIconUrl");
		String value =request.getParameter("value");
		String type =request.getParameter("type");
		String number = request.getParameter("number");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			MemberRights currObj = memberRightsService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setRightsName(rightsName);
				if(images!=null){
				currObj.setDescUrl(descUrl);
				currObj.setGrayIconUrl(grayIconUrl);
				currObj.setValue(Integer.parseInt(value));
				currObj.setType(Integer.parseInt(type));
				currObj.setNumber(Integer.parseInt(number));
				currObj.setUpdateTime(new Date());
				currObj.setUpdateId(user.getId());
				memberRightsService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			MemberRights obj = new MemberRights();
			obj.setRightsName(rightsName);
			obj.setIconUrl(images);
			obj.setDescUrl(descUrl);
			obj.setGrayIconUrl(grayIconUrl);
			obj.setValue(Integer.parseInt(value));
			obj.setType(Integer.parseInt(type));
			obj.setNumber(Integer.parseInt(number));
			obj.setCreateTime(new Date());
			obj.setCreateId(user.getId());
			memberRightsService.insertSelective(obj);
		}
		response.getWriter().print("success");
	
		}*/
		response.getWriter().print("success");
	}

	private String uploadIconGrayUrlMultipartFile(MemberRights memberRights,HttpServletRequest request,String useFile) throws Exception{
        MultipartFile uploadFile = null ;
        if(useFile.equals("iconUrl")){
        	uploadFile =  memberRights.getIconUrlMultipartFile(); 
        }else {
        	uploadFile =  memberRights.getGrayIconUrlMultipartFile();
        }
        String filename = uploadFile.getOriginalFilename();  
        InputStream is = uploadFile.getInputStream();  
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
      //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String fileURL = oss.putObject(newFileName, is, "rights/");
	    return fileURL;
	}
	


	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" 删除会员权益数据")
	@RequestMapping("/deleteMemberRightsById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberRightsService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
