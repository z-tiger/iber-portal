package com.iber.portal.controller.charging;

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
import com.iber.portal.model.charging.CarBrand;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.CarBrandService;
import com.iber.portal.service.sys.SysParamService;


@Controller
public class CarBrandController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CarBrandService carBrandService;
	
	@Autowired
    private SysParamService sysParamService ;
	
	/**
	 * @describe 车辆品牌页面
	 * 
	 */
	@SystemServiceLog(description="车辆品牌页面")
	@RequestMapping(value = "/charging_car_brand_page", method = { RequestMethod.GET })
	public String chargingCarBrand(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("车辆品牌页面");
		return "charging/carBrand";		
	}
	
	/**
	 * @describe 车辆品牌列表
	 */
	@SystemServiceLog(description="车辆品牌列表")
	@RequestMapping(value = "/charging_car_brand_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingCarBrandList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("车辆品牌列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String brandName = request.getParameter("brand_name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("brandName", brandName);
		Pager<CarBrand> pager = carBrandService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新车辆品牌
	 * 
	 */
	@SystemServiceLog(description="保存更新车辆品牌")
	@RequestMapping(value = "/charging_car_brand_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingCarBrandSaveOrUpdate(HttpServletRequest request, HttpServletResponse response,MultipartFile file)
			throws Exception {
		log.info("保存更新车辆品牌");
		response.setContentType("text/html;charset=utf-8");
		String images =null ;
		String filename = file.getOriginalFilename(); 
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
        if(!filename.equals("")){
        //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
	    InputStream is = file.getInputStream();
		 images = oss.putObject(newFileName, is, "carBrand/");
        }
		String id = request.getParameter("id");
		String brandName = request.getParameter("brand_name");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			CarBrand currObj = carBrandService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setBrandName(brandName);
				if(images!=null){
				currObj.setImages(images);
				}
				currObj.setUpdateTime(new Date());
				currObj.setUpdateId(user.getId());
				carBrandService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			CarBrand obj = new CarBrand();
			obj.setBrandName(brandName);
			obj.setImages(images);
			obj.setCreateId(user.getId());
			obj.setCreateTime(new Date());
			carBrandService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除车辆品牌
	 * 
	 */
	@SystemServiceLog(description="删除车辆品牌")
	@RequestMapping(value = "/charging_car_brand_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingCarBrandDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除车辆品牌");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			carBrandService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 桩支持车辆品牌列表
	 */
	@SystemServiceLog(description="桩支持车辆品牌列表")
	@RequestMapping(value = "/charging_car_brand_list_by_type", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingCarBrandListByType(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("桩支持车辆品牌列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String typeId = request.getParameter("typeId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("typeId", typeId);
		Pager<CarBrand> pager = carBrandService.getAllBrand(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
}
