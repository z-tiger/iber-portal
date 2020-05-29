package com.iber.portal.controller;



import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.DateTimeUtil;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.dayRent.DayLongRentOrderMapper;
import com.iber.portal.dao.operationReport.RuningDayReportMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.MemberChargeLog;
import com.iber.portal.model.dayRent.LongRentOrder;
import com.iber.portal.model.operationReport.*;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.MemberChargeLogService;
import com.iber.portal.service.operationReport.*;
import com.iber.portal.service.sys.SysUserService;
import com.iber.portal.util.DateTime;
import com.iber.portal.vo.car.CarMonthlyOperationVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;






/**
 * @describe 
 * 
 * @author yangguiwu
 * @date 2016年03月31日
 */
@Controller
public class OperationReportController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarApplyDetailService carApplyDetailService;	

	@Autowired
	private MemberCapitalService memberCapitalService;
	
	
	@Autowired
	private MemberConsumptionService memberConsumptionService;
	
	
	@Autowired
	private CarMonthlyOperationService carMonthlyOperationService;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	
	@Autowired
	private MemberRegisterService memberRegisterService;	
	
	
	@Autowired
	private RuningDayReportService runingDayReportService;
	
	@Autowired
	private MemberChargingCapitalService memberChargingCapitalService;
	
	@Autowired
	private MemberChargeLogService memberChargeLogService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private DayLongRentOrderMapper dayLongRentOrderMapper ;
	
	/**
	 * @describe 车辆使用明细报表页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年03月30日
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆使用明细报表页面")
	@RequestMapping(value = "/carApplyDetail", method = { RequestMethod.GET })
	public String carApplyDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "operationReport/carApplyDetail";		
	}
	@SystemServiceLog(description="车辆使用明细报表明细")
	@RequestMapping(value = "/car_apply_detail", method = { RequestMethod.GET, RequestMethod.POST })
	public String carApplyDetailList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        if(!StringUtils.isBlank(request.getParameter("lpn"))){
        	record.put("lpn", request.getParameter("lpn"));
        }
        if(!StringUtils.isBlank(request.getParameter("name"))){
        	record.put("name", request.getParameter("name"));
        }
        if(!StringUtils.isBlank(request.getParameter("phone"))){
        	record.put("phone", request.getParameter("phone"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("idCard"))){
        	record.put("idCard", request.getParameter("idCard"));
        }
        
        /**增加车辆型号查询条件*/
        
        if(!StringUtils.isBlank(request.getParameter("carType"))){
        	record.put("carType",request.getParameter("carType"));
        }
       
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        }
       
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<CarApplyDetail> data = carApplyDetailService.selectByPrimaryKey(record);
        CarApplyDetailVo vo = new CarApplyDetailVo();
        if (data.size() > 0) {
        	vo.setTotalMileage(data.get(0).getTotalMileageVal());
        	vo.setTotalMinute(data.get(0).getTotalMinuteVal());
        	vo.setTotalPayMoney(data.get(0).getTotalPayMoneyVal());
		}
        
        JSONObject obj = new JSONObject();
		obj.put("total", carApplyDetailService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		obj.put("footer", vo);
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}		
	@SystemServiceLog(description="车辆查询查询导出excel链接")
	@RequestMapping(value = "/export_car_apply_detail_excel", method = { RequestMethod.GET, RequestMethod.POST })
	public List<CarApplyDetail> exportCarApplyDetailExcel(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		 response.setContentType("text/html;charset=utf-8");
        String fileName="carApplyDetail";
        //填充reports数据
		Map<String,Object> map=new HashMap<String,Object>();
		
	        if(!StringUtils.isBlank(request.getParameter("lpn"))){
	        	map.put("lpn", request.getParameter("lpn"));
	        }
	        if(!StringUtils.isBlank(request.getParameter("name"))){
	        	map.put("name", request.getParameter("name"));
	        }
	        if(!StringUtils.isBlank(request.getParameter("phone"))){
	        	map.put("phone", request.getParameter("phone"));
	        }
	        if(!StringUtils.isBlank(request.getParameter("idCard"))){
	        	map.put("idCard", request.getParameter("idCard"));
	        }
	        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
	        	map.put("beginTime", request.getParameter("beginTime") +  " 00:00:00");
	        }
	        if(!StringUtils.isBlank(request.getParameter("endTime"))){
	        	map.put("endTime", request.getParameter("endTime") +  " 23:59:59");
	        
	        }
	        /**增加车辆型号查询条件*/
	        if(!StringUtils.isBlank(request.getParameter("carType"))){
	        	map.put("carType",request.getParameter("carType"));
	        }
		
       if(!StringUtils.isBlank(request.getParameter("beginTime"))){
    	   map.put("page", null);
    	   map.put("size", null);
           List<CarApplyDetail> data = carApplyDetailService.selectByPrimaryKey(map);
           CarApplyDetailVo vo = new CarApplyDetailVo();
           if (data.size() > 0) {
           	vo.setTotalMileage(data.get(0).getTotalMileageVal());
           	vo.setTotalMinute(data.get(0).getTotalMinuteVal());
           	vo.setTotalPayMoney(data.get(0).getTotalPayMoneyVal());
   		}
    	   List<CarApplyDetail> reports=createCarApplyDetailData(map);
           List<Map<String,Object>> list=createCarApplyDetailExcelRecord(reports);
           String columnNames[]={"车牌号","车辆型号","使用日期","开始时间","结束时间","使用者","使用者身份证","手机","使用时长(分)",
           		"使用里程(公里)","取车地点","还车地点","计费金额(元)"};//列名
           // 总合计导出execl
           String totalSum []={"","","","","","","","",String.valueOf(vo.getTotalMinute()),
              		String.valueOf(vo.getTotalMileage()),"","",String.valueOf(vo.getTotalPayMoney())};
           String keys[]    =     {"lpn","carType","useDate","beginTime","endTime","name","idCard","phone","totalMinute",
           		"totalMileage","parkName","returnParkName","totalPayMoney"};//map中的key
           ByteArrayOutputStream os = new ByteArrayOutputStream();
           try {
               ExcelUtil.createWorkBook2(list,keys,columnNames,totalSum).write(os);
           } catch (IOException e) {
               e.printStackTrace();
           }
           byte[] content = os.toByteArray();
           InputStream is = new ByteArrayInputStream(content);
           // 设置response参数，可以打开下载页面
           response.reset();
           response.setContentType("application/vnd.ms-excel;charset=utf-8");
           response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
           ServletOutputStream out = response.getOutputStream();
           BufferedInputStream bis = null;
           BufferedOutputStream bos = null;
           try {
               bis = new BufferedInputStream(is);
               bos = new BufferedOutputStream(out);
               byte[] buff = new byte[2048];
               int bytesRead;
               // Simple read/write loop.
               while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                   bos.write(buff, 0, bytesRead);
               }
           } catch (final IOException e) {
               throw e;
           } finally {
               if (bis != null)
                   bis.close();
               if (bos != null)
                   bos.close();
           }
       }
        return null;
    }	

    private List<CarApplyDetail> createCarApplyDetailData(Map<String, Object> map) {
		List<CarApplyDetail> leaseObj =carApplyDetailService.selectByPrimaryKeyExcel(map) ;	
		return leaseObj;
    }
    private List<Map<String, Object>> createCarApplyDetailExcelRecord(List<CarApplyDetail> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        CarApplyDetail report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("lpn", report.getLpn());
            mapValue.put("carType",report.getCarType());//将车型一起保存到表格中
            mapValue.put("useDate", dateFormatter.format(report.getUseDate()));
            mapValue.put("beginTime", formatter.format(report.getBeginTime()));
            mapValue.put("endTime", formatter.format(report.getEndTime()));
            mapValue.put("name", report.getName());
            mapValue.put("idCard",report.getIdCard());
            mapValue.put("phone", report.getPhone());
            mapValue.put("totalMinute",report.getTotalMinute());
            mapValue.put("totalMileage",report.getTotalMileage());
            mapValue.put("parkName",report.getParkName());
            mapValue.put("returnParkName",report.getReturnParkName());
            if(report.getTotalPayMoney()==null){
            	mapValue.put("totalPayMoney","0.00");	
            }else{
            	mapValue.put("totalPayMoney",report.getTotalPayMoney());	
            }
            listmap.add(mapValue);
        }
        return listmap;
    }    	

	
    /**
	 * @describe 车辆运营月报表页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月05日
	 * @throws Exception
	 */
    @SystemServiceLog(description="车辆运营月报表页面")
	@RequestMapping(value = "/carMonthlyOperation", method = { RequestMethod.GET })
	public String carMonthlyOperation(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "operationReport/carMonthlyOperation";		
	}
    @SystemServiceLog(description="车辆运营月报表明细")
	@RequestMapping(value = "/car_monthly_operation", method = { RequestMethod.GET, RequestMethod.POST })
	public String carMonthlyOperationList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        int  rentSum =0;

        if(!StringUtils.isBlank(request.getParameter("idcard"))){
        	record.put("idcard", request.getParameter("idcard"));
        }
        
        String yearValue = request.getParameter("yearValue");
        String monthValue = request.getParameter("monthValue");
        String queryMonth = yearValue + "-" + StringUtils.leftPad(monthValue, 2, "0") ;
        String lpn = request.getParameter("lpn");
        String carType = request.getParameter("carType");
        /*String rentType = request.getParameter("rentType");*/
        record.put("queryMonth", queryMonth);
        record.put("lpn",lpn);
        record.put("carType",carType);
        /*record.put("rentType",rentType);*/
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<CarMonthlyOperation> data = carMonthlyOperationService.selectByPrimaryKey(record);
        for (CarMonthlyOperation carMonthlyOperation : data) {
			System.out.println(carMonthlyOperation);
		}
        CarMonthlyOperationVo vo = null;
        if (data.size() > 0) {
			vo = new CarMonthlyOperationVo();
			vo.setFreeCompensationMoneySum(data.get(0).getFreeCompensationMoneySum());
			vo.setPayMoneySum(data.get(0).getPayMoneySum());
			vo.setRentTimeSum(data.get(0).getRentTimeSum());
			vo.setTotalMileageSum(data.get(0).getTotalMileageSum());
			vo.setTotalPayMoneySum(data.get(0).getTotalPayMoneySum());
			vo.setTotalMinuteSum(data.get(0).getTotalMinuteSum());
		}
        /**求出本月的总租赁次数*/
        for(int i=0;i<data.size();i++){
        	rentSum+=data.get(i).getRentTime();
        }
        /**设置到 rentTotal中*/
        for(int i=0;i<data.size();i++){
        	data.get(i).setRentTotal(rentSum);
        }
        JSONObject obj = new JSONObject();
		obj.put("total", carMonthlyOperationService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		obj.put("footer", vo);
		response.getWriter().print(obj.toString());
		return null;
	}  
    @SystemServiceLog(description="车辆运营月报表明细导出excel链接")
	@RequestMapping(value = "/export_car_monthly_operation_excel", method = { RequestMethod.GET, RequestMethod.POST })
	public List<CarMonthlyOperation> exportCarMonthlyOperationExcel(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		 response.setContentType("text/html;charset=utf-8");
        String fileName="carMonthlyOperation";
        //填充reports数据
        
        String yearValue = request.getParameter("yearValue");
        String monthValue = request.getParameter("monthValue");
        String queryMonth = yearValue + "-" + StringUtils.leftPad(monthValue, 2, "0") ;
        
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("queryMonth", queryMonth);
		if(!StringUtils.isBlank(request.getParameter("lpn"))){
        	map.put("lpn", request.getParameter("lpn"));
        }
		if(!StringUtils.isBlank(request.getParameter("carType"))){
        	map.put("carType", request.getParameter("carType"));
        }
		if(!StringUtils.isBlank(request.getParameter("rentType"))){
        	map.put("rentType", request.getParameter("rentType"));
        }
		map.put("page", null);
        map.put("size", null);
		List<CarMonthlyOperation> data = carMonthlyOperationService.selectByPrimaryKey(map);
        CarMonthlyOperationVo vo = null;
        if (data.size() > 0) {
			vo = new CarMonthlyOperationVo();
			vo.setFreeCompensationMoneySum(data.get(0).getFreeCompensationMoneySum());
			vo.setPayMoneySum(data.get(0).getPayMoneySum());
			vo.setRentTimeSum(data.get(0).getRentTimeSum());
			vo.setTotalMileageSum(data.get(0).getTotalMileageSum());
			vo.setTotalPayMoneySum(data.get(0).getTotalPayMoneySum());
			vo.setTotalMinuteSum(data.get(0).getTotalMinuteSum());
		}
//        List<CarMonthlyOperation> reports=createCarMonthlyOperationData(map);
        List<Map<String,Object>> list=createCarMonthlyOperationExcelRecord(data);
        String columnNames[]={"车牌号","车辆类型","租赁次数","行驶里程（公里）",
        		"行驶时间（分）","支付金额(元)","商业保险（元）","订单金额元（）"};//列名
        String keys[]    =     {"lpn","carType","rentTime","totalMileage",
        		"totalMinute","payMoney","sumCompensation","totalPayMoney"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String totalSum []    =     {"","",
        		String.valueOf(vo.getRentTimeSum()),
        		String.valueOf(vo.getTotalMileageSum()),
        		String.valueOf(vo.getTotalMinuteSum()),
        		String.valueOf(vo.getPayMoneySum()),
        		String.valueOf(vo.getFreeCompensationMoneySum()),
        		String.valueOf(vo.getTotalPayMoneySum())};
        try {
            ExcelUtil.createWorkBook2(list,keys,columnNames,totalSum).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }	

    private List<CarMonthlyOperation> createCarMonthlyOperationData(Map<String,String> map) {
		List<CarMonthlyOperation> obj =carMonthlyOperationService.selectByPrimaryKeyExcel(map) ;
		return obj;
    }
    private List<Map<String, Object>> createCarMonthlyOperationExcelRecord(List<CarMonthlyOperation> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        CarMonthlyOperation report=null;
        /**求出本月的总租赁次数*/
        int rentSum = 0 ;
        for(int i=0;i<reports.size();i++){
        	rentSum+=reports.get(i).getRentTime();
        }
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("lpn", report.getLpn());
            mapValue.put("carType", report.getCarType());
            mapValue.put("rentTime",report.getRentTime());
            mapValue.put("totalMileage",report.getTotalMileage());
            mapValue.put("totalMinute",report.getTotalMinute());
            mapValue.put("payMoney", report.getPayMoney());
            mapValue.put("sumCompensation", StringUtils.isBlank(report.getSumCompensation())?"0.00":report.getSumCompensation());
			if (report.getTotalPayMoney() == null) {
				mapValue.put("totalPayMoney", "0.0");
			} else {
				mapValue.put("totalPayMoney", report.getTotalPayMoney());
			}

            listmap.add(mapValue);
        }
        return listmap;
    } 	
	
	
	/**
	 * @describe 会员资金报表报表页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月01日
	 * @throws Exception
	 */
    @SystemServiceLog(description="会员资金报表报表页面")
	@RequestMapping(value = "/memberCapital", method = { RequestMethod.GET })
	public String memberCapital(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "operationReport/memberCapital";		
	}
    @SystemServiceLog(description="会员资金报表报表页面")
	@RequestMapping(value = "/member_capital", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberCapitalList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        if(!StringUtils.isBlank(request.getParameter("code"))){
        	record.put("code", request.getParameter("code"));
        }
        if(!StringUtils.isBlank(request.getParameter("phone"))){
        	record.put("phone", request.getParameter("phone"));
        }
        if(!StringUtils.isBlank(request.getParameter("name"))){
        	record.put("name", request.getParameter("name"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        }
        
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<MemberCapital> data = memberCapitalService.selectByPrimaryKey(record);
        MemberCapitalVo vo = null;
        if (data.size() > 0) {
        	vo = new MemberCapitalVo();
			vo.setDepositSum(data.get(0).getDepositSum());
			vo.setMoneySum(data.get(0).getMoneySum());
			vo.setTotalChargeMoneySum(data.get(0).getTotalChargeMoneySum());
			vo.setTotalConsumeMoneySum(data.get(0).getTotalConsumeMoneySum());
			vo.setTotalCouponMoneySum(data.get(0).getTotalCouponMoneySum());
			vo.setTotalRefundMoneySum(data.get(0).getTotalRefundMoneySum());
		}
        JSONObject obj = new JSONObject();
		obj.put("total", memberCapitalService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		obj.put("footer", vo);
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}
    @SystemServiceLog(description="会员成功充值明细链接")
	@RequestMapping(value = "/member_recharge_detail", method = { RequestMethod.GET, RequestMethod.POST })
    public String memberRechargedetails(int page, int rows,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	 response.setContentType("text/html;charset=utf-8");
         if(null==request.getParameter("memberId")){
        	 return null;
         }
		else {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			map.put("page", pageInfo.get("first_page"));
			map.put("size", pageInfo.get("page_size"));
			map.put("memberId", Integer.valueOf(request.getParameter("memberId")));
            Pager<MemberRecharge> pagers = memberCapitalService.selectMemberRechargeDetails(map);	
            response.getWriter().print(Data2Jsp.Json2Jsp(pagers));
            return null;
		}  	 
    }
    @SystemServiceLog(description="会员资金报表报表页面导出excel链接")
	@RequestMapping(value = "/export_member_capital_excel", method = { RequestMethod.GET, RequestMethod.POST })
	public List<MemberCapital> exportMemberCapitalExcel(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		 response.setContentType("text/html;charset=utf-8");
        String fileName="MemberCapital";
        Enumeration pNames=request.getParameterNames();
        while(pNames.hasMoreElements()){
            String name=(String)pNames.nextElement();
            String value=request.getParameter(name);
            System.out.print(name + "=" + value);
        }
        //填充reports数据
		Map<String,String> map=new HashMap<String,String>();
		
		if(!StringUtils.isBlank(request.getParameter("code1"))){
			map.put("code", request.getParameter("code1"));
        }
        if(!StringUtils.isBlank(request.getParameter("phone1"))){
        	map.put("phone", request.getParameter("phone1"));
        }
        if(!StringUtils.isBlank(request.getParameter("name1"))){
        	map.put("name", request.getParameter("name1"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("beginTime1"))){
        	map.put("beginTime", request.getParameter("beginTime1") +  " 00:00:00");
        }
        if(!StringUtils.isBlank(request.getParameter("endTime1"))){
        	map.put("endTime", request.getParameter("endTime1") +  " 23:59:59");
        
        }
		
        if (!StringUtils.isBlank(request.getParameter("beginTime1"))) { 
        	List<MemberCapital> reports=createMemberCapitalData(map);
        	List<Map<String,Object>> list=createMemberCapitalExcelRecord(reports);
        	String columnNames[]={"区域名称","日期","手机号码","姓名","会员押金(元)","充值金额(元)","消费金额-支付(元)","消费金额-优惠券(元)",
        			"退款金额(元)","余额(元)","会员等级","会员贡献值"};//列名
        	String keys[]    =     {"cityName","createTime","phone","name","deposit","totalChargeMoney","totalConsumeMoney",
        			"totalCouponMoney","totalRefundMoney","money","levelCode","contributedVal"};//map中的key
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	try {
        		ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	byte[] content = os.toByteArray();
        	InputStream is = new ByteArrayInputStream(content);
        	// 设置response参数，可以打开下载页面
        	response.reset();
        	response.setContentType("application/vnd.ms-excel;charset=utf-8");
        	response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        	ServletOutputStream out = response.getOutputStream();
        	BufferedInputStream bis = null;
        	BufferedOutputStream bos = null;
        	try {
        		bis = new BufferedInputStream(is);
        		bos = new BufferedOutputStream(out);
        		byte[] buff = new byte[2048];
        		int bytesRead;
        		// Simple read/write loop.
        		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
        			bos.write(buff, 0, bytesRead);
        		}
        	} catch (final IOException e) {
        		throw e;
        	} finally {
        		if (bis != null)
        			bis.close();
        		if (bos != null)
        			bos.close();
        	}
        }
        return null;
    }	

    private List<MemberCapital> createMemberCapitalData(Map<String,String> map) {
		List<MemberCapital> obj =memberCapitalService.selectByPrimaryKeyExcel(map) ;
		return obj;
    }
    private List<Map<String, Object>> createMemberCapitalExcelRecord(List<MemberCapital> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        MemberCapital report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("cityName", report.getCityName());
            mapValue.put("createTime", formatter.format(report.getCreateTime()));
            mapValue.put("phone", report.getPhone());
            mapValue.put("name", report.getName());
            mapValue.put("levelCode",report.getLevelCode());
            mapValue.put("contributedVal",report.getContributedVal());
			if (report.getDeposit() == null) {
				mapValue.put("deposit", "0.00");
			} else {
				mapValue.put("deposit", report.getDeposit());
			}

			if (report.getTotalChargeMoney() == null) {
				mapValue.put("totalChargeMoney", "0.00");
			} else {
				mapValue.put("totalChargeMoney", report.getTotalChargeMoney());
			}

			if (report.getTotalConsumeMoney() == null) {
				mapValue.put("totalConsumeMoney", "0.00");
			} else {
				mapValue.put("totalConsumeMoney", report.getTotalConsumeMoney());
			}

			if (report.getTotalRefundMoney() == null) {
				mapValue.put("totalRefundMoney", "0.00");
			} else {
				mapValue.put("totalRefundMoney", report.getTotalRefundMoney());
			}

			if (report.getMoney() == null) {
				mapValue.put("money", "0.00");
			} else {
				mapValue.put("money", report.getMoney());
			}

			if (report.getTotalMoney() == null) {
				mapValue.put("totalPayMoney", "0.00");
			} else {
				mapValue.put("totalMoney", report.getTotalMoney());
			}
			
			if (report.getTotalCouponMoney() == null) {
				mapValue.put("totalCouponMoney", "0.00");
			} else {
				mapValue.put("totalCouponMoney", report.getTotalCouponMoney());
			}
            listmap.add(mapValue);
        }
        return listmap;
    }    	


    /**
     * @describe 会员消费明细页面
     * 
     * @auther yangguiwu
     * @date 2016年04月05日
     * @throws Exception
     */
    @SystemServiceLog(description="会员消费明细页面")
    @RequestMapping(value = "/memberConsumption", method = { RequestMethod.GET })
    public String memberConsumption(HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	return "operationReport/memberConsumption";		
    }

    @SystemServiceLog(description="会员消费明细页面")
	@RequestMapping(value = "/member_consumption", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberConsumptionList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        if(!StringUtils.isBlank(request.getParameter("name"))){
        	record.put("name", request.getParameter("name"));
        }
        if (StringUtils.isNotBlank("phoneNumber")) {
            record.put("phoneNumber", request.getParameter("phoneNumber"));
        }
        if(!StringUtils.isBlank(request.getParameter("cityName"))){
            record.put("cityName", request.getParameter("cityName"));
        }
        if(!StringUtils.isBlank(request.getParameter("brandName"))){
            record.put("brandName", request.getParameter("brandName"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime");
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime");
        	record.put("endTime", endTime);
        
        }
        
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<MemberConsumption> data = memberConsumptionService.selectByPrimaryKey(record);
//      log.info("data>>>>>" + data.toString());
        MemberTimeShareConsumptionVo vo = null;
        if (data.size() > 0) {
			vo = new MemberTimeShareConsumptionVo();
			vo.setCouponBalanceSum(data.get(0).getCouponBalanceSum());
			vo.setFreeCompensateMoneySum(data.get(0).getFreeCompensateMoneySum());
			vo.setPayMoneySum(data.get(0).getPayMoneySum());
			vo.setTotalMileageSum(data.get(0).getTotalMileageSum());
			vo.setTotalMinuteSum(data.get(0).getTotalMinuteSum());
			vo.setTotalPayMoneySum(data.get(0).getTotalPayMoneySum());
		}
        JSONObject obj = new JSONObject();
		obj.put("total", memberConsumptionService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		obj.put("footer", vo);
		log.info("obj===>"+obj.toString());
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}

	@SystemServiceLog(description="会员消费明细页面导出excel链接")
	@RequestMapping(value = "/export_member_consumption_excel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<MemberConsumption> exportMemberConsumptionExcel(String cityName,String phoneNumber,String brandName,String name,String beginTime,String endTime,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        String fileName="MemberConsumption";
        //填充reports数据
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("page", null);
        map.put("size", null);
		if(StringUtils.isNotBlank(cityName)){
            map.put("cityName", cityName);
        }
        if(StringUtils.isNotBlank(phoneNumber)){
            map.put("phoneNumber", phoneNumber);
        }
        if (StringUtils.isNotBlank(brandName)) {
            map.put("brandName", brandName);
        }
        List<MemberConsumption> reports=createMemberConsumptionData(map);
        
        List<MemberConsumption> data = memberConsumptionService.selectByPrimaryKey(map);
        MemberTimeShareConsumptionVo vo = null;
        if (data.size() > 0) {
			vo = new MemberTimeShareConsumptionVo();
			vo.setCouponBalanceSum(data.get(0).getCouponBalanceSum());
			vo.setFreeCompensateMoneySum(data.get(0).getFreeCompensateMoneySum());
			vo.setPayMoneySum(data.get(0).getPayMoneySum());
			vo.setTotalMileageSum(data.get(0).getTotalMileageSum());
			vo.setTotalMinuteSum(data.get(0).getTotalMinuteSum());
			vo.setTotalPayMoneySum(data.get(0).getTotalPayMoneySum());
		}
        List<Map<String,Object>> list=createMemberConsumptionExcelRecord(reports);
        String columnNames[]={"所属城市","车辆号码","车辆品牌","会员姓名","性别","出生年月","当次使用日期","使用时长(分钟)",
        		"使用里程(公里)","取车时间","还车时间","手机号","会员等级","会员贡献值","支付方式","支付金额(元)","消费金额(元)","优惠券(元)","商业保险(元)"};//列名
        // 总合计导出execl
		String totalSum[] = { "", "", "", "", "", "", "",
				String.valueOf(vo.getTotalMinuteSum()),
				String.valueOf(vo.getTotalMileageSum()), "", "", "",
				"", "", "", String.valueOf(vo.getPayMoneySum()),
				String.valueOf(vo.getTotalPayMoneySum()),
				String.valueOf(vo.getCouponBalanceSum()),
				String.valueOf(vo.getFreeCompensateMoneySum()) };
        String keys[]    =     {"cityName","lpn","brandName","name","sex","idcard","useDate","totalMinute",
        		"totalMileage","beginTime","endTime","phone","memberLevel","integral","payType","payMoney","totalPayMoney","couponBalance","freeCompensateMoney"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook2(list,keys,columnNames,totalSum).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }	

    private List<MemberConsumption> createMemberConsumptionData(Map<String, Object> map) {
		List<MemberConsumption> obj =memberConsumptionService.selectByPrimaryKeyExcel(map) ;
		return obj;
    }
    private List<Map<String, Object>> createMemberConsumptionExcelRecord(List<MemberConsumption> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        MemberConsumption report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("name",report.getName());
            mapValue.put("sex", report.getSex());
             mapValue.put("idcard", report.getIdcard());
			 mapValue.put("useDate", dayFormatter.format(report.getUseDate()));
			 mapValue.put("totalMinute",report.getTotalMinute());
			 mapValue.put("totalMileage",report.getTotalMileage());
			 mapValue.put("beginTime", formatter.format(report.getBeginTime()));
			 mapValue.put("endTime", formatter.format(report.getEndTime()));
			 mapValue.put("phone",report.getPhone());
			 mapValue.put("memberLevel", report.getMemberLevel());
			 mapValue.put("integral",report.getIntegral());
            switch(report.getPayType()){
                case "B":
                    mapValue.put("payType","余额支付");
                    break;
                case "A":
                    mapValue.put("payType","支付宝支付");
                    break;
                case "T":
                    mapValue.put("payType","财付通支付");
                    break;
                case "WX":
                    mapValue.put("payType","微信支付");
                    break;
                case "U":
                    mapValue.put("payType","银联支付");
                    break;
                case "AP":
                    mapValue.put("payType","apple pay支付");
                    break;
                case "EB":
                    mapValue.put("payType","企业余额支付");
                    break;
                default:
                    mapValue.put("payType","");
                    break;
            }
            mapValue.put("cityName", report.getCityName());
            mapValue.put("lpn", report.getLpn());
            mapValue.put("brandName", report.getBrandName());


            if (report.getPayMoney() == null) {
				mapValue.put("payMoney", "0.00");
			} else {
				mapValue.put("payMoney", report.getPayMoney());
			}
			
			if (report.getTotalPayMoney() == null) {
				mapValue.put("totalPayMoney", "0.00");
			} else {
				mapValue.put("totalPayMoney", report.getTotalPayMoney());
			}
			
			if (report.getCouponBalance() == null) {
				mapValue.put("couponBalance", "0.00");
			} else {
				mapValue.put("couponBalance", report.getCouponBalance());
			}
			
			if (report.getFreeCompensateMoney() == null) {
				mapValue.put("freeCompensateMoney", "0.00");
			} else {
				mapValue.put("freeCompensateMoney", report.getFreeCompensateMoney());
			}

            listmap.add(mapValue);
        }
        return listmap;
    } 

  
    /**
     * @describe 会员注册报表页面
     * 
     * @auther yangguiwu
     * @date 2016年04月07日
     * @throws Exception
     */
    @SystemServiceLog(description="会员注册报表页面")
    @RequestMapping(value = "/memberRegister", method = { RequestMethod.GET })
    public String memberRegister(HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	return "operationReport/memberRegister";		
    }    
    @SystemServiceLog(description="会员注册报表页面")
	@RequestMapping(value = "/member_register", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberRegisterList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        }
       
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<MemberRegister> data = memberRegisterService.selectByPrimaryKey(record);
        JSONObject obj = new JSONObject();
		obj.put("total", memberRegisterService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}      
    
    @SystemServiceLog(description="会员注册报表页面导出excel链接")
	@RequestMapping(value = "/export_member_register_excel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<MemberRegister> exportMemberRegisterExcel(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        String fileName="memberRegister"+request.getParameter("beginTime")+"to"+request.getParameter("endTime");
        //填充reports数据
        HashMap<String, Object> record = new  HashMap<String, Object>();

        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        }       
        
        List<MemberRegister> reports=createMemberRegisterData(record);
        List<Map<String,Object>> list=createMemberRegisterExcelRecord(reports);
        String columnNames[]={"区域名称","注册数量","区域编号"};//列名
        String keys[]    =     {"name","total","cityCode"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }	

    private List<MemberRegister> createMemberRegisterData(Map<String, Object> record) throws ServiceException {
		List<MemberRegister> obj =memberRegisterService.selectByPrimaryKeyExcel(record) ;
		return obj;
    }
    private List<Map<String, Object>> createMemberRegisterExcelRecord(List<MemberRegister> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        MemberRegister report=null;
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("name",report.getCityName());
            mapValue.put("total", report.getTotal());
            mapValue.put("cityCode", report.getCityCode());
            listmap.add(mapValue);
        }
        return listmap;
    }     
   
	
    /**
     * @describe 会员注册详细信息列表页面
     * 
     * @auther yangguiwu
     * @date 2016年04月06日
     * @throws Exception
     */
    @SystemServiceLog(description="会员注册详细信息列表页面")
	@RequestMapping(value = "/member_register_info", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberRegisterInfoList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        
        if(!StringUtils.isBlank(request.getParameter("cityCode"))){
        	record.put("cityCode", request.getParameter("cityCode"));
        }
       
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        }
        
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<MemberRegister> data = memberRegisterService.selectRegisterInfo(record);
        JSONObject obj = new JSONObject();
		obj.put("total", memberRegisterService.selectRegisterInfoRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}      
   
    
	/**
     * @describe 会员信息报表页面
     * 
     * @auther yangguiwu
     * @date 2016年04月06日
     * @throws Exception
     */
    @SystemServiceLog(description="会员信息报表页面")
    @RequestMapping(value = "/memberInfo", method = { RequestMethod.GET })
    public String memberInfo(HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	return "operationReport/memberInfo";		
    }
    
    /**
	 * 获取系统用户list
	 */
	@SystemServiceLog(description = "获取所有系统用户")
	@RequestMapping(value = "get_sysUser_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String get_sysUser_list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//List<Lpn> lpns =taskService.selectAllLpn();
		List<SysUser> users = sysUserService.selectAllOnTheJob();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		Map<String, Object> defalutMap = new HashMap<String, Object>();  
		defalutMap.put("id", "");
		defalutMap.put("text", "全部");
		list.add(defalutMap);
		for (int i = 0; i < users.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", users.get(i).getId()); 
	        map.put("text",users.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
    @SystemServiceLog(description="会员信息报表页面")
	@RequestMapping(value = "/member_info", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberInfoList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        if(!StringUtils.isBlank(request.getParameter("code"))&&!StringUtils.equals("00", request.getParameter("code"))){
        	record.put("code", request.getParameter("code"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("registerCategory"))){
        	record.put("registerCategory", request.getParameter("registerCategory"));
        }
        if(!StringUtils.isBlank(request.getParameter("memberPhone"))){
        	record.put("memberPhone", request.getParameter("memberPhone"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        }
        if(!StringUtils.isBlank(request.getParameter("memberLevel"))){
        	record.put("memberLevel",request.getParameter("memberLevel"));
        }
        if(StringUtils.isNotBlank(request.getParameter("examine_start_time"))){
        	record.put("examine_start_time", request.getParameter("examine_start_time") +  " 00:00:00");
        }
        if(StringUtils.isNotBlank(request.getParameter("examine_end_time"))){
        	record.put("examine_end_time", request.getParameter("examine_end_time") +  " 23:59:59");
        }
        if(StringUtils.isNotBlank(request.getParameter("examine_id"))){
        	record.put("examine_id", request.getParameter("examine_id"));
        }
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<MemberInfo> data = memberInfoService.selectByPrimaryKey(record);
        JSONObject obj = new JSONObject();
		obj.put("total", memberInfoService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}   
    
    @SystemServiceLog(description="会员信息报表页面导出excel链接")
	@RequestMapping(value = "/export_member_info_excel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<MemberInfo> exportMemberInfoExcel(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        String fileName="memberInfo"+request.getParameter("beginTime")+"to"+request.getParameter("endTime");
        //填充reports数据
        HashMap<String, Object> record = new  HashMap<String, Object>();
        
        if(!StringUtils.isBlank(request.getParameter("code"))){
        	record.put("code", request.getParameter("code"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("registerCategory"))){
        	record.put("registerCategory", request.getParameter("registerCategory"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("memberPhone"))){
        	record.put("memberPhone", request.getParameter("memberPhone"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        
        } 
        
        if(!StringUtils.isBlank(request.getParameter("memberLevel"))){
        	record.put("memberLevel", request.getParameter("memberLevel"));
        }
        
        if(StringUtils.isNotBlank(request.getParameter("examine_start_time"))){
        	record.put("examine_start_time", request.getParameter("examine_start_time") +  " 00:00:00");
        }
        if(StringUtils.isNotBlank(request.getParameter("examine_end_time"))){
        	record.put("examine_end_time", request.getParameter("examine_end_time") +  " 23:59:59");
        }
        if(StringUtils.isNotBlank(request.getParameter("examine_id"))){
        	record.put("examine_id", request.getParameter("examine_id"));
        }
        
        List<MemberInfo> reports=createMemberInfoData(record);
        List<Map<String,Object>> list=createMemberInfoExcelRecord(reports);
        String columnNames[]={"会员id","会员姓名","性别","手机号","身份证","驾驶证","状态","注册IP",
        		"注册方式","注册时间","审核时间","审核人","注册城市","会员等级","会员贡献值","时租用车次数","日租用车次数","充电次数"};//列名
        String keys[]    =     {"id","name","sex","phone","idcard","driverIdcard","status","registerIp",
        		"registerCategory","createTime","examineTime","userName","cityName","memberLevel","integral","timeRentUserTotal","dayRentUserTotal","chargingTotal"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }	

    private List<MemberInfo> createMemberInfoData(Map<String, Object> record) throws ServiceException {
		List<MemberInfo> obj =memberInfoService.selectByPrimaryKeyExcel(record) ;
		return obj;
    }
    private List<Map<String, Object>> createMemberInfoExcelRecord(List<MemberInfo> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        MemberInfo report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("id", report.getId());
            mapValue.put("name",report.getName());
            mapValue.put("sex", report.getSex());
            mapValue.put("phone", report.getPhone());
            mapValue.put("idcard", report.getIdcard());
			mapValue.put("driverIdcard", report.getDriverIdcard());
			mapValue.put("status",report.getStatus());
			mapValue.put("registerIp",report.getRegisterIp());
			mapValue.put("registerCategory",report.getRegisterCategory());
			mapValue.put("createTime", formatter.format(report.getCreateTime()));
			mapValue.put("examineTime", report.getExamineTime() != null ? formatter.format(report.getExamineTime()):"");
			mapValue.put("userName", report.getUserName());
			mapValue.put("cityName",report.getCityName());
			mapValue.put("memberLevel", report.getMemberLevel());
			mapValue.put("integral", report.getIntegral());
			mapValue.put("timeRentUserTotal", report.getTimeRentUserTotal());
			mapValue.put("dayRentUserTotal",report.getDayRentUserTotal());
			mapValue.put("chargingTotal",report.getChargingTotal());
            listmap.add(mapValue);
        }
        return listmap;
    }     
    
    @Autowired
    private RuningDayReportMapper runingDayReportMapper;
    
	/**
     * @describe 每日运营数据报表页面
     * 
     * @auther yangguiwu
     * @date 2016年04月05日
     * @throws Exception
     */
    @SystemServiceLog(description="每日运营数据报表页面")
	@RequestMapping(value = "/runingDayReport",method = { RequestMethod.GET, RequestMethod.POST })
	public String runingDayReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		String queryMonthTime = "";
		String queryDaySTime = "";
		String queryDayETime = "";
		String queryTime = "";
	   if(!StringUtils.isBlank(request.getParameter("queryTime"))){
		   queryTime = request.getParameter("queryTime");
	   }else{
		   queryTime = DateTimeUtil.getCurrDate();
	   }
	   queryMonthTime = queryTime.split("-")[0] + "-" + queryTime.split("-")[1];
	   queryDaySTime = queryTime + " 00:00:00";
	   queryDayETime = queryTime + " 23:59:59";
	   
	   request.setAttribute("queryMonthTime", queryMonthTime);
	   request.setAttribute("queryDaySTime", queryDaySTime);
	   request.setAttribute("queryDayETime", queryDayETime);
	   request.setAttribute("queryTime", queryTime);
	   
	   int month_regitster_counts = runingDayReportMapper.countsMonthRegiterMember(queryMonthTime);
	   int day_regitster_counts = runingDayReportMapper.countsDayRegiterMeber(queryDaySTime, queryDayETime);
	   int countsMonthChargeMember = runingDayReportMapper.countsMonthChargeMember(queryMonthTime);
	   request.setAttribute("month_regitster_counts", month_regitster_counts);
	   request.setAttribute("day_regitster_counts", day_regitster_counts);
	   request.setAttribute("countsMonthChargeMember", countsMonthChargeMember);
		
	   request.setAttribute("countsDayChargeMember", runingDayReportMapper.countsDayChargeMember(queryDaySTime, queryDayETime));
	   request.setAttribute("countsCar", runingDayReportMapper.countsCar());
	   request.setAttribute("countsRunCar", runingDayReportMapper.countsRunCar());
	   
	   request.setAttribute("countsMonthUseCarRecords", runingDayReportMapper.countsMonthUseCarRecords(queryMonthTime));
	   request.setAttribute("countsDayUseCarRecords", runingDayReportMapper.countsDayUseCarRecords(queryDaySTime, queryDayETime));
	   
	   request.setAttribute("countsMonthUseCarTimes", runingDayReportMapper.countsMonthUseCarTimes(queryMonthTime));
	   request.setAttribute("countsDayUseCarTimes", runingDayReportMapper.countsDayUseCarTimes(queryDaySTime, queryDayETime));
		return "operationReport/runingDayReport";		
	}
    @SystemServiceLog(description="每日运营数据报表页面列表")
    
	@RequestMapping(value = "/runing_day_report_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String runingDayReportList(Map<String,Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        
        if(!StringUtils.isBlank(request.getParameter("queryTime"))){
        	record.put("queryTime", request.getParameter("queryTime"));
        } 
        
 
        	 List<RuningDayReport> obj =runingDayReportService.selectByPrimaryKey(record);
        //	request.getSession().setAttribute("counts",memberRegisterService.selectRegisterInfoRecords(record)); 
        	model.put("personList", obj);
           return "operationReport/runingDayReport";
		
	}  
    
  
//    @RequestMapping(value = "/runingDayReport", method = { RequestMethod.GET, RequestMethod.POST })
//	public String runingDayReportList(Map<String,Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception{
//
//	     HashMap<String, Object> record = new  HashMap<String, Object>();
//	     
//	        if(!StringUtils.isBlank(request.getParameter("queryTime"))){
//	        	record.put("queryTime", "2016-04-11");
//	        }
//
//	     System.out.print("00000000000000011117777==== " +request.getParameter("queryTime") );
//     if (!StringUtils.isBlank(request.getParameter("queryTime"))) { 
//	      List<RuningDayReport> obj =runingDayReportService.selectByPrimaryKey(record);
//	      System.out.println("0000000000000111133333==== " +obj+"         ");
//	        model.put("personList", obj);
//	     }
//	       
//     request.getSession().setAttribute("counts", "1000"); 
//     return "operationReport/runingDayReport";
//	     
//	
//	}

	/**
	 * @describe 会员充电明细查询页面
     *
	 * @throws Exception
	 */
    @SystemServiceLog(description="会员充电明细查询页面")
	@RequestMapping(value = "/member_charging_detail", method = { RequestMethod.GET })
	public String memberChargingCapital(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "operationReport/memberChargingCapital";		
	}
	 
	/**
	 * @describe 会员充电明细
     *
	 * @throws Exception
	 */
    @SystemServiceLog(description="会员充电明细")
	@RequestMapping(value = "/member_chargingDetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberChargingCapitalList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
  
        if(!StringUtils.isBlank(request.getParameter("name"))){
        	record.put("name", request.getParameter("name"));
        }
        if(!StringUtils.isBlank(request.getParameter("phoneNumber"))){
            record.put("phoneNumber", request.getParameter("phoneNumber"));
        }
        
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	String startTime = request.getParameter("beginTime") +  " 00:00:00";
        	record.put("beginTime", startTime);
        }
        if(!StringUtils.isBlank(request.getParameter("endTime"))){
        	String endTime = request.getParameter("endTime") +  " 23:59:59";
        	record.put("endTime", endTime);
        }
        
        if (!StringUtils.isBlank(request.getParameter("beginTime"))) { 
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<MemberConsumption> data = memberChargingCapitalService.selectChargingCapitalByPrimaryKey(record);
        MemberConsumptionVo vo = null;
        if (data.size() > 0) {
			vo = new MemberConsumptionVo();
			vo.setChargingAmountSum(data.get(0).getChargingAmountSum());
			vo.setChargingTimeSum(data.get(0).getChargingTimeSum());
			vo.setCouponMoneySum(data.get(0).getCouponMoneySum());
			vo.setOrderMoneySum(data.get(0).getOrderMoneySum().doubleValue());
			vo.setPayMoneySum(data.get(0).getPayMoneySum());
		}
        JSONObject obj = new JSONObject();
		obj.put("total", memberChargingCapitalService.selectChargingCapitalCountByPrimaryKey(record));
		obj.put("rows", data);
		obj.put("footer", vo);
		response.getWriter().print(obj.toString());
		return null;
		} else {
			return null;
		}
	}
    @SystemServiceLog(description="会员充电明细excel导出链接")
	@RequestMapping(value = "/export_member_ChargingCapital_excel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<MemberConsumption> exportMemberChargingCapitalExcel(String name,String phoneNumber,String beginTime,String endTime,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        String fileName="MemberConsumption";
        //填充reports数据
		Map<String,Object> map=new HashMap<String,Object>();
        if (StringUtils.isNotBlank(phoneNumber)) {
            map.put("phoneNumber", phoneNumber);
        }
        map.put("name", name);
		map.put("beginTime", beginTime+" 00:00:00");
		map.put("endTime", endTime+" 23:59:59");
		map.put("page", null);
        map.put("size", null);
		
        List<MemberConsumption> reports=createMemberChargingCapitalData(map);
        List<MemberConsumption> data = memberChargingCapitalService.selectChargingCapitalByPrimaryKey(map);
        MemberConsumptionVo vo = null;
        if (data.size() > 0) {
			vo = new MemberConsumptionVo();
			vo.setChargingAmountSum(data.get(0).getChargingAmountSum());
			vo.setChargingTimeSum(data.get(0).getChargingTimeSum());
			vo.setCouponMoneySum(data.get(0).getCouponMoneySum());
			vo.setOrderMoneySum(data.get(0).getOrderMoneySum().doubleValue());
			vo.setPayMoneySum(data.get(0).getPayMoneySum());
		}
        List<Map<String,Object>> list=createMemberChargingCapitalExcelRecord(reports);
        String columnNames[]={"会员姓名","性别","手机号","会员等级","会员贡献值","开始时间","充电时长","充电量","支付金额(元)","消费金额(元)","优惠券(元)",};//列名
        //添加合计导出execl
    	String totalSum [] = { "", "", "", "", "", "",
				vo==null || vo.getChargingTimeSum()==null?"0":String.valueOf(vo.getChargingTimeSum()),
				vo==null || vo.getChargingAmountSum()==null?"0.00":String.valueOf(vo.getChargingAmountSum()),
				vo==null ||vo.getPayMoneySum()==null?"0.00":String.valueOf(Math.floor(vo.getPayMoneySum())),
				vo==null ||vo.getOrderMoneySum()==null?"0.00":String.valueOf(vo.getOrderMoneySum()),
				vo==null ||vo.getCouponMoneySum()==null?"0.00":String.valueOf(vo.getCouponMoneySum()) };
        String keys[]    =     {"name","sex","phone","memberLevel","integral","startTime","chargingTime","chargingAmount","payMoney","orderMoney","couponValue"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook2(list,keys,columnNames,totalSum).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }	

    private List<MemberConsumption> createMemberChargingCapitalData(Map<String, Object> map) {
		List<MemberConsumption> obj =memberChargingCapitalService.selectChargingCapitalByPrimaryKeyExcel(map) ;
		return obj;
    }
    private List<Map<String, Object>> createMemberChargingCapitalExcelRecord(List<MemberConsumption> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        MemberConsumption report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("name",report.getName());
            mapValue.put("sex", report.getSex());
		    mapValue.put("startTime", formatter.format(report.getStartTime()));
		    mapValue.put("phone",report.getPhone());
		    mapValue.put("memberLevel", report.getMemberLevel());
		    mapValue.put("integral",report.getIntegral());
		    
			 
			if (report.getPayMoney() == null) {
				mapValue.put("payMoney", "0.00");
			} else {
				mapValue.put("payMoney", report.getPayMoney());
			}
			if (report.getOrderMoney() == null) {
				mapValue.put("orderMoney", "0.00");
			} else {
				mapValue.put("orderMoney", report.getOrderMoney());
			}
			
			if (report.getCouponValue() == null) {
				mapValue.put("couponValue", "0.00");
			} else {
				mapValue.put("couponValue", report.getCouponValue());
			}
			if (report.getChargingAmount() == null) {
				mapValue.put("chargingAmount", "0.00");
			} else {
				mapValue.put("chargingAmount", report.getChargingAmount());
			}
			if (report.getChargingTime() == null) {
				mapValue.put("chargingTime", "0.00");
			} else {
				mapValue.put("chargingTime", report.getChargingTime());
			}

            listmap.add(mapValue);
        }
        return listmap;
    } 
    
    /**
    * @Title:memberOriginalRefund
    * @Description:会员原路退款明细页面
    * @Author:cuichongc
    * @date 2017-6-12  下午4:07:05  
    * @return String
    * @throws Exception
     */
    @SystemServiceLog(description="原路退款明细页面")
    @RequestMapping(value="/member_original_refund_details",method={RequestMethod.GET,RequestMethod.POST})
    public String memberOriginalRefund(HttpServletRequest request,HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
    	return "operationReport/memberOriginalRefund";
    	
    }
    
    /**
    * @Title:memberOriginalRefundList
    * @Description:查询原路退款明细表
    * @Author:cuichongc
    * @date 2017-6-12  下午7:41:40  
    * @return String
    * @throws Exception
     */
    @SystemServiceLog(description="查询原路退款明细表")
    @RequestMapping(value="/member_original_refund_list",method={RequestMethod.GET,RequestMethod.POST})
    public String memberOriginalRefundList(int rows,int page, HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setCharacterEncoding("utf-8");
    	Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        String phoneNumber = request.getParameter("phoneNumber");
        HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("name", request.getParameter("name"));
        if (StringUtils.isNotBlank(phoneNumber)) {
            map.put("phoneNumber", phoneNumber);
        }
        String beginStr = request.getParameter("begin");
    	Timestamp begin = null;
      	if(StringUtils.isNotBlank(beginStr)){
    		begin = DateTime.getDateTime(beginStr);
    	}
    	map.put("begin", begin);
    	String endStr = request.getParameter("end");
    	Timestamp end = null;
    	if(StringUtils.isNotBlank(endStr)){
    		end = DateTime.getDateTime(endStr);
    	}
    	map.put("end", end);
    	
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
    	List<MemberChargeLog> list = memberChargeLogService.memberOriginalRefundList(map);
    	int total = memberChargeLogService.memberOriginalRefundTotal(map);
    	String json = Data2Jsp.Json2Jsp(list, total);
    	response.getWriter().print(json);
    	return null;
    }
    /**
    * @Title:memberOriginalRefundExcel
    * @Description:原路退款报表导出
    * @Author:cuichongc
    * @date 2017-6-13  上午11:00:25  
    * @return void
    * @throws Exception
     */
    @SystemServiceLog(description="原路退款明细报表导出excel")
    @RequestMapping(value="/member_original_refund_excel",method={RequestMethod.GET,RequestMethod.POST})
    public List<MemberChargeLog> createMemberOriginalRefundExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setCharacterEncoding("utf-8");
    	String name = request.getParameter("name");
    	String begin =request.getParameter("begin");
    	String end = request.getParameter("end");
        String phoneNumber = request.getParameter("phoneNumber");
        Map<String, Object> map = new HashMap<String, Object>();
    	String fileName = "memberOriginalRefundReport";
        if (StringUtils.isNotBlank(phoneNumber)) {
            map.put("phoneNumber", phoneNumber);
        }
        map.put("name",name);
		map.put("begin", begin);
		map.put("end", end);
		map.put("offset", null);
		map.put("rows", null);	
		
		String columnNames [] = {"会员ID","会员姓名","手机号码","押金充值","充值日期","退款金额","申请退款时间","原路退回时间","最后处理人","退回账户类型","退回账户账号"};
		String keys [] = {"mid","name","phone","money","createTime","refundMoney","applyCreateTime","lastHandleTime","lastHandleUser","bankCategory","refundUserMoblie"};
		List<MemberChargeLog> data = memberChargeLogService.memberOriginalRefundList(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "原路退款明细");
		list.add(sheetNameMap);
		list.addAll(memberOriginalRefundExcel(data));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		
        return null;
    }
    
    private List<Map<String, Object>> memberOriginalRefundExcel(List<MemberChargeLog> memberChargeLogs){
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	DecimalFormat df=new DecimalFormat("0.00");
    	for(MemberChargeLog memberChargeLog : memberChargeLogs){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("mid", memberChargeLog.getMid());//会员id
    		map.put("name", memberChargeLog.getName());//会员姓名
    		map.put("phone", memberChargeLog.getPhone());//会员手机号
    		if(memberChargeLog.getMoney()==null){
    			map.put("money", "0.00");
    		}else{
    			map.put("money",df.format(memberChargeLog.getMoney()/100.0));
    		}
    		map.put("createTime",formatter.format(memberChargeLog.getCreateTime()));
    		if(memberChargeLog.getRefundMoney()==null){
    			map.put("refundMoney", "0.00");
    		}else{
    			map.put("refundMoney",df.format(memberChargeLog.getRefundMoney()/100.0) );
    		}
    		map.put("applyCreateTime",formatter.format(memberChargeLog.getApplyCreateTime()) );
    		map.put("lastHandleTime",formatter.format(memberChargeLog.getLastHandleTime()) );
    		map.put("lastHandleUser",memberChargeLog.getLastHandleUser() );
    		String bankCategory = memberChargeLog.getBankCategory();
    		if(bankCategory.equals("U")){
    			map.put("bankCategory","银联" );
    		}else if(bankCategory.equals("A")){
    			map.put("bankCategory", "支付宝");
    		}else if(bankCategory.equals("WX")){
    			map.put("bankCategory", "微信");
    		}else if(bankCategory.equals("T")){
    			map.put("bankCategory", "财付通");
    		}else{
    			map.put("bankCategory", "其他");
    		}
    		map.put("refundUserMoblie",memberChargeLog.getRefundUserMoblie() );
    		list.add(map);
    	}
    	return list;
    	
    }
    

    /**
    * @Title:memberManualRefund
    * @Description:手动退款明细页面
    * @Author:cuichongc
    * @date 2017-6-12  下午4:14:01  
    * @return String
    * @throws Exception
     */
    @SystemServiceLog(description="手动退款明细页面")
    @RequestMapping(value="/member_manual_refund_details",method={RequestMethod.GET,RequestMethod.POST})
    public String memberManualRefund (HttpServletRequest request,HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
    	return "operationReport/memberManualRefund";	
    }
    
    
    /**
    * @Title:memberManualRefundList
    * @Description:手动退款明细报表
    * @Author:cuichongc
    * @date 2017-6-13  下午3:17:44  
    * @return String
    * @throws Exception
     */
    @SystemServiceLog(description="手动退款明细报表")
    @RequestMapping(value="/member_manual_refund_list",method={RequestMethod.POST,RequestMethod.GET})
    public String memberManualRefundList(int rows,int page,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setCharacterEncoding("utf-8");
        String phoneNumber = request.getParameter("phoneNumber");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);

        HashMap<String, Object> map = new HashMap<String, Object>();
    	/*String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
    	String begin = new String(request.getParameter("begin").getBytes("iso-8859-1"),"utf-8");
    	String end = new String(request.getParameter("end").getBytes("iso-8859-1"),"utf-8");*/
    	map.put("name", request.getParameter("name"));
        if (StringUtils.isNotBlank(phoneNumber)) {
            map.put("phoneNumber", phoneNumber);
        }
    	
    	String beginStr = request.getParameter("begin");
    	Timestamp begin = null;
    	if(StringUtils.isNotBlank(beginStr)){
    		begin = DateTime.getDateTime(beginStr);
    	}
    	map.put("begin", begin);
    	String endStr = request.getParameter("end");
    	Timestamp end = null;
    	if(StringUtils.isNotBlank(endStr)){
    		end = DateTime.getDateTime(endStr);
    	}
    	map.put("end", end);
    	
    	map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		List<MemberChargeLog> list = memberChargeLogService.memberManualRefundList(map);
		int total = memberChargeLogService.memberManualRefundTotal(map);
		String json = Data2Jsp.Json2Jsp(list, total);
		response.getWriter().print(json);
    	return null;
    }
   
    
  
    /**
    * @Title:createMemberManualRefundExcel
    * @Description:手动退款明细报表
    * @Author:cuichongc
    * @date 2017-6-13  下午4:07:31  
    * @return List<MemberChargeLog>
    * @throws Exception
     */
    @SystemServiceLog(description="手动退款明细报表")
    @RequestMapping(value="/member_manual_refund_excel",method={RequestMethod.GET,RequestMethod.POST})
    public List<MemberChargeLog> createMemberManualRefundExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	response.setCharacterEncoding("utf-8");
    	String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String begin = request.getParameter("begin");
    	String end = request.getParameter("end");
    	String fileName = "memberManualRefundReport";
		map.put("name",name);
        map.put("phoneNumber", phoneNumber);
        map.put("begin", begin);
		map.put("end", end);
		map.put("offset", null);
		map.put("rows", null);	
		//列名
		String columnNames [] = {"会员ID","会员姓名","手机号码","退款金额","申请退款时间","原路退回时间","最后处理人","开户支行","退款卡号"};
		String keys [] = {"mid","name","phone","refundMoney","applyCreateTime","lastHandleTime","lastHandleUser","bankName","bankCard"};
		List<MemberChargeLog> data = memberChargeLogService.memberManualRefundList(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "手动退款明细");
		list.add(sheetNameMap);
		list.addAll(memberManualRefundExcel(data));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		
    	return null;
    }   

    private List<Map<String, Object>> memberManualRefundExcel(List<MemberChargeLog> memberChargeLogs){
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	DecimalFormat df=new DecimalFormat("0.00");
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ss:HH:mm:ss");
    	for(MemberChargeLog memberChargeLog:memberChargeLogs){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("mid", memberChargeLog.getMid());//会员id
    		map.put("name", memberChargeLog.getName());//会员姓名
    		map.put("phone", memberChargeLog.getPhone());//会员手机号
    		if(memberChargeLog.getRefundMoney()==null){
    			map.put("refundMoney", "0.00");
    		}else{
    			map.put("refundMoney",df.format(memberChargeLog.getRefundMoney()/100.0)/*memberChargeLog.getRefundMoney()*/);
    		}
    		if(memberChargeLog.getApplyCreateTime()!=null){
    			map.put("applyCreateTime",dateFormat.format(memberChargeLog.getApplyCreateTime()) );
    		}
    		if(memberChargeLog.getLastHandleTime()!=null){
    			map.put("lastHandleTime",dateFormat.format(memberChargeLog.getLastHandleTime()) );
    		}
    		map.put("lastHandleUser",memberChargeLog.getLastHandleUser() );
    		map.put("bankName",memberChargeLog.getBankName());
    		map.put("bankCard",memberChargeLog.getBankCard());
    		list.add(map);
    	}
    	
    	return list;
    	
    }
    
    /**
     * 车辆日租收入报表页面
     */
    @SystemServiceLog(description="跳转车辆日租收入页面")
    @RequestMapping(value="/car_rent_report",method={RequestMethod.GET,RequestMethod.POST})
    public String carRentReport (HttpServletRequest request,HttpServletResponse response){
    	response.setCharacterEncoding("utf-8");
    	return "operationReport/carRentReport";	
    }
    
    @SystemServiceLog(description="车辆日租收入数据列表")
    @RequestMapping(value="/day_rent_report_list",method={RequestMethod.GET,RequestMethod.POST})
    public String dataGridReport(HttpServletRequest request,HttpServletResponse response,Integer rows,Integer page ) throws Exception {
    	response.setCharacterEncoding("utf-8");
    	int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String cityCode = request.getParameter("cityCode");
		String lpn = request.getParameter("lpn");
		String memberName = request.getParameter("memberName");
		String phone = request.getParameter("phone");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String bt = request.getParameter("bt");
		String et = request.getParameter("et");
		map.put("memberName", memberName);
		if(StringUtils.isNotBlank(cityCode)){
			if(StringUtils.equals(cityCode, "00")){
				map.put("cityCode", null);
			}else{
				map.put("cityCode", cityCode);
			}
		}
		map.put("lpn", lpn);
		map.put("phone", phone);
		map.put("invoiceStatus", invoiceStatus);
		map.put("bt", bt);
		map.put("et", et);
		// 总合计
		map.put("searchType", 0);
		LongRentOrder vo = dayLongRentOrderMapper.selectTotalSum(map);
		// 合计
		map.put("searchType", 1);
		map.put("from", from);
		map.put("to", to);
		LongRentOrder vo2 = dayLongRentOrderMapper.selectTotalSum(map);
		
		List<LongRentOrder> data = dayLongRentOrderMapper.selectDayRentOrderInCome(map);
		data.add(vo2);
		data.add(vo);
		int total = dayLongRentOrderMapper.selectDayRentOrderInComeCount(map);
		String json = Data2Jsp.Json2Jsp(data, total);
		response.getWriter().print(json);

		return null ;
    }
    
    @SystemServiceLog(description="日租数据导出")
	@RequestMapping(value = "/export_carDayRent_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List<LongRentOrder> exportExecl(String memberName,String cityCode,String lpn,String phone,String invoiceStatus,String bt,String et,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "dayRentOrderReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市","订单号", "会员姓名",  "手机号", "车型", "车牌号", "开始时间", 
				"结束时间","首租金额（元）","续租金额（元）","首租商业保险（元）","续租商业保险（元）","首租优惠券面值（元）","续租优惠券面值（元）","首租折扣金额（元）","续租折扣金额（元）","支付总金额（元）",
				"支付方式","是否开票"};
		
		String keys[] = { "cityName","orderId" ,"memberName","phone", "type", "lpn", "beginTime", "returnTime","orderMoney",
				"reletOrderMoney","freeCompensateMoney","reletFreeMoney","couponBalance","reletCouponBalance","discountMoney",
				"reletDiscountMoney","payMoney","payType","invoiceStatus"};
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>() ;
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(cityCode)){
	         	if(cityCode.equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", cityCode);
	         	}
	         }
       }else{
      	    map.put("cityCode", user.getCityCode());
       }
		map.put("lpn", lpn);
		map.put("phone", phone);
		map.put("invoiceStatus", invoiceStatus);
		map.put("memberName", memberName);
		map.put("bt", bt);
		map.put("et", et);
		map.put("from", null);
		map.put("to", null);
		map.put("searchType", 0);
		List<LongRentOrder> datas = dayLongRentOrderMapper.selectDayRentOrderInCome(map);
		LongRentOrder vo = dayLongRentOrderMapper.selectTotalSum(map);
		datas.add(vo);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "日租订单收入数据报表");
		list.add(sheetNameMap);
		list.addAll(createData3(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}	
		
		return null;
		
	}
	
	private List<Map<String, Object>> createData3(
			List<LongRentOrder> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName","orderId" ,"memberName","phone", "type", "lpn", "beginTime", "returnTime","orderMoney",
				"reletOrderMoney","freeCompensateMoney","reletFreeMoney","couponBalance","reletCouponBalance","discountMoney",
				"reletDiscountMoney","payMoney","payType","invoiceStatus"};
		for (LongRentOrder order : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], order.getCityName());
			map.put(keys[1], order.getOrderId());
			map.put(keys[2], order.getMemberName());
			map.put(keys[3], order.getPhone());
			map.put(keys[4], order.getType());
			String lpn = order.getLpn();
			if(lpn != null){
				if (lpn.indexOf(",") != -1) {
					map.put(keys[5], lpn.substring(lpn.lastIndexOf(",")+1).substring(0,2)+"•"+lpn.substring(lpn.lastIndexOf(",")+1).substring(2));
				}else{
					map.put(keys[5], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
				}
			}
			map.put(keys[6], order.getBeginTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getBeginTime()):"");
			String payStatus = order.getPayStatus();
			if (StringUtils.isNotBlank(payStatus)) {
				if(payStatus.indexOf(",")!=-1){
					String[] split = payStatus.split(",");
					if(split[0].equals("finish")&&split[1].equals("finish")){
						map.put(keys[7], order.getReturnTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getReturnTime()):"");
					}else if(split[0].equals("finish")&&!split[1].equals("finish")) {
						map.put(keys[7], order.getReturnTimes()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getReturnTimes()):"");
					}else{
						map.put(keys[7], order.getReturnTimes()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getReturnTimes()):"");
					}
				}else{
					map.put(keys[7], order.getReturnTimes()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getReturnTimes()):"");
				}
			}else{
				map.put(keys[7], "");
			}
			map.put(keys[8], order.getOrderMoney()!=null?new DecimalFormat("#0.00").format(order.getOrderMoney()/100.0):0.00);
			map.put(keys[9], order.getReletOrderMoney()!=null?new DecimalFormat("#0.00").format(order.getReletOrderMoney()/100.0):0.00);
			map.put(keys[10], order.getFreeCompensateMoney()!=null?new DecimalFormat("#0.00").format(order.getFreeCompensateMoney()/100.0):0.00);
			map.put(keys[11], order.getReletFreeMoney()!=null?new DecimalFormat("#0.00").format(order.getReletFreeMoney()/100.0):0.00);
			map.put(keys[12], order.getCouponBalance()!=null?new DecimalFormat("#0.00").format(order.getCouponBalance()/100.0):0.00);
			map.put(keys[13], order.getReletCouponBalance()!=null?new DecimalFormat("#0.00").format(order.getReletCouponBalance()/100.0):0.00);
			map.put(keys[14], order.getDiscountMoney()!=null?new DecimalFormat("#0.00").format(order.getDiscountMoney()/100.0):0.00 );
			map.put(keys[15], order.getReletDiscountMoney()!=null?new DecimalFormat("#0.00").format(order.getReletDiscountMoney()/100.0):0.00);
			map.put(keys[16], order.getPayMoney()!=null?new DecimalFormat("#0.00").format(order.getPayMoney()/100.0):0.00);
			if (StringUtils.isNotBlank(order.getPayType())) {
				if(order.getPayType().equals("B")) map.put(keys[17], "余额");
				if(order.getPayType().equals("A")) map.put(keys[17], "支付宝");
				if(order.getPayType().equals("T")) map.put(keys[17], "财付通");
				if(order.getPayType().equals("WX")) map.put(keys[17], "微信");
				if(order.getPayType().equals("U")) map.put(keys[17], "银联");
				if(order.getPayType().equals("AP")) map.put(keys[17], "apple pay");
			} else {
				map.put(keys[17], "");
			}
			if (order.getInvoiceStatus() != null ) {
				switch (order.getInvoiceStatus()) {
				case 0:
					map.put(keys[18], "未开票");
					break;
				case 1:
					map.put(keys[18], "未开票");
					break;
				case 2:
					map.put(keys[18], "开票中");
					break;
				case 3:
					map.put(keys[18], "已开票");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[18], "");
			}
			
			list.add(map);
		}
		return list;
	}
    
    
}






