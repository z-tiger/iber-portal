package com.iber.portal.controller.report;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.CityMapper;
import com.iber.portal.model.base.City;
import com.iber.portal.model.dayRent.DayRentOrderPayLog;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.ChargingOrderInfoService;
import com.iber.portal.service.dayRent.DayRentOrderPayLogService;
import com.iber.portal.service.finance.AccountCheckService;
import com.iber.portal.service.order.RentReportService;
import com.iber.portal.service.timeShare.TimeSharePayService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.charging.ChargingFinanceReportVo;
import com.iber.portal.vo.finance.AccountCheckTotalVo;
import com.iber.portal.vo.finance.AccountCheckVo;
import com.iber.portal.vo.order.RentReportSumVo;
import com.iber.portal.vo.order.RentReportVo;
import com.iber.portal.vo.report.CarIncomeReport;
import com.iber.portal.vo.report.CarIncomeReportVo;
import com.iber.portal.vo.report.TimeShareIncomeReport;
import com.iber.portal.vo.report.TimeShareIncomeReportVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FinanceReportController extends MainController {

	@Autowired
	private TimeSharePayService timeSharePayService;

	@Autowired
	private DayRentOrderPayLogService dayRentOrderPayLogService;

	/*
	 * 租赁收入报表
	 */
	@Autowired
	private RentReportService rentReportService;

	/*
	 * 对账单
	 */
	@Autowired
	private AccountCheckService accountCheckService;
	
	@Autowired
	private ChargingOrderInfoService chargingOrderInfoService;
	
	@Autowired
	private CityMapper cityMapper;

	/**
	 * @describe 车辆收入查询
	 * @throws Exception
	 */
	@SystemServiceLog(description = "车辆收入查询")
	@RequestMapping(value = "/carIncomeReport", method = { RequestMethod.GET })
	public String carIncomeReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "report/carIncomeReport";
	}
	
	/**
	 * 车辆收入报表
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "车辆收入报表")
	@RequestMapping(value = "/car_income_report", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String carIncomeList(int page, int rows, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");

		Map<String, Object> record = new HashMap<String, Object>();

		if ("00".equals(user.getCityCode())) {
			if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
				if (request.getParameter("cityCode").equals("00")) {
					record.put("cityCode", null);
				} else {
					record.put("cityCode", request.getParameter("cityCode"));
				}
			}
		} else {
			record.put("cityCode", user.getCityCode());
		}

		if (!StringUtils.isBlank(request.getParameter("lpn"))) {
			record.put("lpn", request.getParameter("lpn"));
		}
		if (!StringUtils.isBlank(request.getParameter("queryDateFrom"))) {
			String startTime = request.getParameter("queryDateFrom");
			record.put("queryDateFrom", startTime+" 00:00:00");
		}
		if (!StringUtils.isBlank(request.getParameter("queryDateTo"))) {
			String endTime = request.getParameter("queryDateTo");
			record.put("queryDateTo", endTime+" 23:59:59");
		}
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		record.put("page", pageInfo.get("first_page"));
		record.put("size", pageInfo.get("page_size"));

		Pager<CarIncomeReport> pager = timeSharePayService
				.queryCarIncomeReportList(record);
		List<CarIncomeReport> list = pager.getDatas();
		CarIncomeReportVo vo = null;
		if (list.size() > 0) {
			vo = new CarIncomeReportVo();
			vo.setDayRentMoneySum(list.get(0).getDayRentMoneySum());
			vo.setTimeShareMoneySum(list.get(0).getTimeShareMoneySum());
		}
		response.getWriter().print(Data2Jsp.Json2Jsp(pager, vo));
		return null;
	}

	/**
	 * 专车收入查询导出excel链接
	 * 
	 * @param cityCode
	 * @param lpn
	 * @param carType
	 * @param code
	 * @param queryDateFrom
	 * @param queryDateTo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "专车收入查询导出excel链接")
	@RequestMapping(value = "/export_car_income_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<CarIncomeReport> exportCarIncomeExcel(String cityCode,
			String lpn, String carType, String code, String queryDateFrom,
			String queryDateTo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String fileName = "carIncomeReport";
		// 填充reports数据
//		String lpnNew = new String(request.getParameter("lpn").getBytes(
//				"iso-8859-1"), "utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityCode", cityCode);
		map.put("lpn", lpn);
		if (!StringUtils.isBlank(request.getParameter("queryDateFrom"))) {
			String startTime = request.getParameter("queryDateFrom");
			map.put("queryDateFrom", startTime+" 00:00:00");
		}
		if (!StringUtils.isBlank(request.getParameter("queryDateTo"))) {
			String endTime = request.getParameter("queryDateTo");
			map.put("queryDateTo", endTime+" 23:59:59");
		}
		map.put("page", 0);
		map.put("size", 100000);
		List<CarIncomeReport> reports = createCarIncomeReportData(map);
		Pager<CarIncomeReport> pager = timeSharePayService
				.queryCarIncomeReportList(map);
		List<CarIncomeReport> data = pager.getDatas();
		CarIncomeReportVo vo = null;
		if (data.size() > 0) {
			vo = new CarIncomeReportVo();
			vo.setDayRentMoneySum(data.get(0).getDayRentMoneySum());
			vo.setTimeShareMoneySum(data.get(0).getTimeShareMoneySum());
		}
		DecimalFormat df = new DecimalFormat("0.00");
		List<Map<String, Object>> list = createCarIncomeReportExcelRecord(reports);
		String columnNames[] = { "车辆所属城市", "车牌号", "车辆品牌", "车类型", "颜色",
				"时租收入(元)", "日租收入(元)" };// 列名
		String totalSum [] = { "", "", "", "", "", 
				vo.getTimeShareMoneySum()==null?"0.00":String.valueOf(df.format(vo.getTimeShareMoneySum())), 
				vo.getDayRentMoneySum()==null?"0.00":String.valueOf(df.format(vo.getDayRentMoneySum())) };// 列名
		String keys[] = { "cityName", "lpn", "brandName", "type", "color",
				"timeShareMoney", "dayRentMoney" };// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook2(list, keys, columnNames,totalSum).write(os);
		} catch (IOException e) {
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

	private List<CarIncomeReport> createCarIncomeReportData(
			Map<String, Object> map) {
		List<CarIncomeReport> carObj = timeSharePayService
				.queryCarIncomeReport(map);
		return carObj;
	}

	private List<Map<String, Object>> createCarIncomeReportExcelRecord(
			List<CarIncomeReport> reports) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		CarIncomeReport report = null;
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("cityName", report.getCityName());
			mapValue.put("lpn", report.getLpn());
			mapValue.put("brandName", report.getBrandName());
			mapValue.put("type", report.getType());
			mapValue.put("color", report.getColor());
			mapValue.put("timeShareMoney", report.getTimeShareMoney());
			mapValue.put("dayRentMoney", report.getDayRentMoney());
			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * @describe 分时租赁收入查询页面
	 * 
	 * @throws Exception
	 */
	@SystemServiceLog(description = "分时租赁收入查询页面")
	@RequestMapping(value = "/timeShareReport", method = { RequestMethod.GET })
	public String timeLeaseReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "report/timeShareReport";
	}

	/**
	 * 分时租赁收入报表
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "分时租赁收入报表")
	@RequestMapping(value = "/time_share_list_report", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String timeLeaseList(Integer page, Integer rows, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> record = new HashMap<String, Object>();
        String payBeginTime = request.getParameter("payBeginTime");
        String payEndTime = request.getParameter("payEndTime");
        record.put("payBeginTime", payBeginTime);
        record.put("payEndTime", payEndTime);

        if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
			record.put("cityCode", request.getParameter("cityCode"));
		}

		if (!StringUtils.isBlank(request.getParameter("lpn"))) {
			record.put("lpn", request.getParameter("lpn"));
		}
		if (!StringUtils.isBlank(request.getParameter("queryDateFrom"))) {
			String startTime = request.getParameter("queryDateFrom");
			record.put("queryDateFrom", startTime);
		}
		if (!StringUtils.isBlank(request.getParameter("queryDateTo"))) {
			String endTime = request.getParameter("queryDateTo");
			record.put("queryDateTo", endTime);
		}
		if (!StringUtils.isBlank(request.getParameter("name"))) {
			String name = request.getParameter("name");
			record.put("name", name);
		}
		if (StringUtils.isNotBlank(request.getParameter("invoiceStatus"))) {
			String invoiceStatus = request.getParameter("invoiceStatus");
			record.put("invoiceStatus", invoiceStatus);
		}
        record.put("payStatus", request.getParameter("payStatus"));

		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		record.put("page", pageInfo.get("first_page"));
		record.put("size", pageInfo.get("page_size"));
		Pager<TimeShareIncomeReport> pager = timeSharePayService
				.queryTimeShareIncomeReportList(record);
		List<TimeShareIncomeReport> list = pager.getDatas();
		TimeShareIncomeReportVo vo = null;
		if (list.size() > 0) {
			vo = new TimeShareIncomeReportVo();
			vo.setPayMoneySum(list.get(0).getPayMoneySum());
			vo.setReductionPayMoneySum(list.get(0).getReductionPayMoneySum());
			vo.setTotalMileageSum(list.get(0).getTotalMileageSum());
			vo.setTotalMinuteSum(list.get(0).getTotalMinuteSum());
			vo.setInvoiceMoneySum(list.get(0).getTotalInvoiceMoney());
			vo.setTotalPayMoneySum(list.get(0).getTotalPayMoneySum());
            vo.setTotalCompensationSum(list.get(0).getFreeCompensationMoneySum());
        }
		response.getWriter().print(Data2Jsp.Json2Jsp(pager, vo));

		return null;
	}

	/**
	 * 专车收入查询导出excel链接
	 * 
	 * @param name
	 * @param cityCode
	 * @param lpn
	 * @param carType
	 * @param code
	 * @param queryDateFrom
	 * @param queryDateTo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "专车收入查询导出excel链接")
	@RequestMapping(value = "/export_time_share_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<TimeShareIncomeReport> exportTimeLeaseExcel(String name,
			String cityCode, String lpn, String carType, String code,
			String queryDateFrom, String queryDateTo,String payBeginTime,String payEndTime,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = "timeLeaseReport";
		// 填充reports数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityCode", cityCode);
		map.put("lpn", lpn);
		map.put("queryDateFrom", queryDateFrom);
		map.put("queryDateTo", queryDateTo);
		map.put("payBeginTime", payBeginTime);
		map.put("payEndTime", payEndTime);
		map.put("name", name);
		map.put("invoiceStatus", request.getParameter("invoiceStatus"));
        map.put("payStatus", request.getParameter("payStatus"));

        List<TimeShareIncomeReport> reports = createTimeLeaseReportData(map);
		String totalSum [] = { "", "", "", "", "","","","", "", "", "", "","","",reports.get(0).getTotalMinuteSum().toString(),
				 reports.get(0).getTotalMileageSum().toString(), reports.get(0).getPayMoneySum().toString(),
                reports.get(0).getTotalPayMoneySum().toString(),reports.get(0).getFreeCompensationMoneySum().toString(), "",
				 reports.get(0).getReductionPayMoneySum().toString(),
				"",reports.get(0).getTotalInvoiceMoney()==null?"0.00":reports.get(0).getTotalInvoiceMoney().toString() };
		List<Map<String, Object>> list = createTimeLeaseReportExcelRecord(reports);

		String columnNames[] = { "日期", "支付时间","支付状态","所属城市", "车牌号", "车辆品牌","车型号", "车类型", "会员姓名",
				"手机号码", "单价(元/30分钟)", "里程单价(元/公里)", "最低消费(元)", "最高消费(元)",
				"租赁时长(分钟)", "行驶里程(公里)", "租赁收入(元)","订单金额（元）","商业保险(元)", "优惠券编号", "优惠券面值(元)",
				"优惠金额(元)","开票金额（元）" };// 列名
		String keys[] = { "endTime", "payTime","payStatus","cityName", "lpn", "brandName","carModel", "type",
				"name", "phone", "timeRate", "milesRate", "minConsump",
				"maxConsump", "totalMinute", "totalMileage", "payMoney","totalPayMoney","freeCompensationMoney",
				"couponNo", "couponBalance", "reductionPayMoney", "invoiceMoney" };// map中的key
		// String keys[] = { "endTime", "cityName", "lpn", "brandName", "type",
		// "name", "phone", "timeRate/timeUnit",
		// "milesRate/mileageTemp", "minConsump", "maxConsump", "totalMinute",
		// "totalMileage", "payMoney",
		// "couponNo", "couponBalance", "reductionPayMoney" };// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,totalSum)
					.write(os);
		} catch (IOException e) {
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

	private List<TimeShareIncomeReport> createTimeLeaseReportData(
			Map<String, Object> map) {
		List<TimeShareIncomeReport> leaseObj = timeSharePayService
				.queryTimeShareReport(map);
		return leaseObj;
	}

	private List<Map<String, Object>> createTimeLeaseReportExcelRecord(
			List<TimeShareIncomeReport> reports) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		TimeShareIncomeReport report = null;
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			String payStatus = report.getPayStatus().equals("noPay")?"未支付":"已支付";
			Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("payTime","");
            mapValue.put("endTime", formatter.format(report.getEndTime()));
            mapValue.put("freeCompensationMoney", report.getFreeCompensationMoney());
            mapValue.put("carModel", report.getCarModel());
            mapValue.put("payStatus",payStatus );
            mapValue.put("cityName", report.getCityName());
			mapValue.put("lpn", report.getLpn());
			mapValue.put("brandName", report.getBrandName());
			mapValue.put("type", report.getType());
			mapValue.put("name", report.getName());
			mapValue.put("phone", report.getPhone());
			mapValue.put("timeUnit", report.getTimeUnit());
			mapValue.put("timeRate", report.getTimeRate());
			mapValue.put("milesRate", report.getMilesRate());
			mapValue.put("minConsump", report.getMinConsump());
			mapValue.put("maxConsump", report.getMaxConsump());
			mapValue.put("totalMinute", report.getTotalMinute());
			mapValue.put("totalMileage", report.getTotalMileage());
			mapValue.put("payMoney", report.getPayMoney());
			mapValue.put("totalPayMoney", report.getTotalPayMoney());
			mapValue.put("reductionPayMoney", report.getReductionPayMoney());
			mapValue.put("couponNo", StringUtils.isBlank(report.getCouponNo())?"":report.getCouponNo());
			mapValue.put("couponBalance", report.getCouponBalance());
			mapValue.put("invoiceMoney", report.getInvocieMoney());

			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * @describe 日租收入查询页面
	 * 
	 * @throws Exception
	 */
	@SystemServiceLog(description = "日租收入查询页面")
	@RequestMapping(value = "/day_rent_pay", method = { RequestMethod.GET })
	public String dayRentPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "operationReport/dayRentPay";
	}

	/**
	 * @describe 日租收入报表
	 * 
	 * @throws Exception
	 */
	@SystemServiceLog(description = "日租收入报表")
	@RequestMapping(value = "/day_rent_pay_list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String dayRentPayList(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> record = new HashMap<String, Object>();
		if (user.getCityCode().equals("00")) {
			if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
				if (request.getParameter("cityCode").equals("00")) {
					record.put("cityCode", null);
				} else {
					record.put("cityCode", request.getParameter("cityCode"));
				}
			}
		} else {
			record.put("cityCode", user.getCityCode());
		}
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String cityCode = request.getParameter("city_code");
		String lpn = request.getParameter("lpn");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("cityCode", cityCode);
		paramMap.put("lpn", lpn);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<DayRentOrderPayLog> pager = dayRentOrderPayLogService
				.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}

	/**
	 * @describe 日租收入报表导出
	 * @throws Exception
	 */
	@SystemServiceLog(description = "日租收入报表导出")
	@RequestMapping(value = "/day_rent_pay_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String dayRentPayExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> record = new HashMap<String, Object>();
		if (user.getCityCode().equals("00")) {
			if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
				if (request.getParameter("cityCode").equals("00")) {
					record.put("cityCode", null);
				} else {
					record.put("cityCode", request.getParameter("cityCode"));
				}
			}
		} else {
			record.put("cityCode", user.getCityCode());
		}
		String cityCode = request.getParameter("city_code");
		String lpn = request.getParameter("lpn");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("lpn", lpn);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		List<DayRentOrderPayLog> reports = dayRentOrderPayLogService
				.getAllExcel(paramMap);
		List<Map<String, Object>> list = createDayRentReportExcelRecord(reports);

		String columnNames[] = { "所属区域", "订单号", "车牌号", "会员名称", "支付时间", "金额",
				"支付方式" };// 列名
		String keys[] = { "cityName", "orderId", "lpn", "memberName",
				"payTime", "payMoney", "payType" };// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,null)
					.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("日租收入.xls").getBytes(), "iso-8859-1"));
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

	private List<Map<String, Object>> createDayRentReportExcelRecord(
			List<DayRentOrderPayLog> reports) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		DayRentOrderPayLog report = null;
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("cityName", report.getCityName());
			mapValue.put("orderId", report.getOrderId());
			mapValue.put("lpn", report.getLpn());
			mapValue.put("memberName", report.getMemberName());
			mapValue.put("payTime",
					DateTime.getDateTimeString(report.getPayTime()));
			mapValue.put("payMoney", report.getPayMoney());
			mapValue.put("payType", report.getPayType());
			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * @describe 租赁收入查询
	 * @throws Exception
	 */
	@SystemServiceLog(description = "租赁收入查询")
	@RequestMapping(value = "/rentReport_page", method = { RequestMethod.GET })
	public String rentReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "report/rentReport";
	}

	/**
	 * 租赁收入报表
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "租赁收入报表")
	@RequestMapping(value = "/rent_report", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryRentReport(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		String lpn = request.getParameter("lpn");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String orderType = request.getParameter("orderType");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");

		String beginStr = request.getParameter("begin");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}

		Pager<RentReportVo> pager = this.rentReportService.queryRentReport(
				cityCode, name, lpn, begin, end, from, to,invoiceStatus,orderType);
		List<RentReportVo> list = pager.getDatas();
		RentReportSumVo vo = null;
		if (list.size() > 0) {
			vo = new RentReportSumVo();
			vo.setCouponBalanceSum(list.get(0).getCouponBalanceSum());
			vo.setFreeCompsationMoneySum(list.get(0)
					.getFreeCompsationMoneySum());
			vo.setPayMoneySum(list.get(0).getPayMoneySum());
			vo.setTotalMinuteSum(list.get(0).getTotalMinuteSum());
			vo.setTotalPayMoneySum(list.get(0).getTotalPayMoneySum());
		}
		response.getWriter().print(Data2Jsp.Json2Jsp(pager, vo));
		return null;
	}

	/**
	 * @describe 租赁收入报表导出
	 * @throws Exception
	 */
	@SystemServiceLog(description = "租赁收入报表导出")
	@RequestMapping(value = "/export_rent_report_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String exportRentReportExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String cityCode = request.getParameter("cityCode");
		if (StringUtils.isNotBlank(cityCode)) {
			if (cityCode.equals("00")) {
				cityCode = null;
			}
		}

		String name = request.getParameter("name");
		String lpn = request.getParameter("lpn");
		String beginStr = request.getParameter("begin");
		String orderType = request.getParameter("orderType");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}

		List<RentReportVo> reports = rentReportService.queryRentReportList(
				cityCode, name, lpn, begin, end,invoiceStatus,orderType);
		RentReportSumVo vo = null;
		if (reports.size() > 0) {
			vo = new RentReportSumVo();
			vo.setCouponBalanceSum(reports.get(0).getCouponBalanceSum());
			vo.setFreeCompsationMoneySum(reports.get(0)
					.getFreeCompsationMoneySum());
			vo.setPayMoneySum(reports.get(0).getPayMoneySum());
			vo.setTotalMinuteSum(reports.get(0).getTotalMinuteSum());
			vo.setTotalPayMoneySum(reports.get(0).getTotalPayMoneySum());
			
		}
		List<Map<String, Object>> list = createRentReportExcelRecord(reports);
		final RentReportVo rentTotal = this.rentReportService.getRentTotal(
				cityCode, name, lpn, begin, end, invoiceStatus,orderType);
		String columnNames[] = { "日期", "车牌号", "车辆品牌","订单类型","所属城市","车型", "会员ID", "会员姓名",
				"手机号码", "租车时长", "单价", "收入","优惠券面值","夜间优惠", "总优惠", "保险", "实际收入","开票金额" };// 列名
		String keys[] = { "endTime", "lpn", "brandName","","cityName","type", "memberId",
				"name", "phone", "totalMinute", "rate", "totalPayMoney",
				"couponBalance","nightReductionMoney","reductionMoney",
				"freeCompensationMoney", "payMoney","invoiceMoney" };// map中的key
		String totalSum[] = { 
				"", "", "", "", "", "", "", "", "",
				String.valueOf(vo.getTotalMinuteSum()), "",
				vo.getTotalPayMoneySum().toString(),
				vo.getCouponBalanceSum().toString(),
				reports.get(0).getNightReductionMoney().toString(),
				String.valueOf(rentTotal.getReductionMoney()),
				String.valueOf(new DecimalFormat("0.00").format(vo.getFreeCompsationMoneySum())),
				vo.getPayMoneySum().toString(),
				String.valueOf(rentTotal.getTotalInvoiceMoney())
				};
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,totalSum)
					.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("租赁收入报表.xls").getBytes(), "iso-8859-1"));
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

	private List<Map<String, Object>> createRentReportExcelRecord(
			List<RentReportVo> reports) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "租赁收入报表");
		listmap.add(map);
		RentReportVo report = null;
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("endTime",
					DateTime.getDateTimeString(report.getEndTime()));
			mapValue.put("lpn", report.getLpn());
			mapValue.put("brandName", report.getBrandName());
			if (StringUtils.isNotBlank(report.getOrderId())) {
				if (report.getOrderId().indexOf("TS") != -1) {
					mapValue.put("", "时租");
				}else{
					mapValue.put("", "日租");
				}
			}
			mapValue.put("cityName", report.getCityName());
			mapValue.put("type", report.getType());
			mapValue.put("memberId", report.getMemberId());
			mapValue.put("name", report.getName());

			mapValue.put("phone", report.getPhone());
			mapValue.put("totalMinute", report.getTotalMinute());
			mapValue.put("rate", report.getRate());
			mapValue.put("payMoney", report.getPayMoney());
			mapValue.put("couponBalance",
					CommonUtil.nullToZero(report.getCouponBalance()));
			mapValue.put("nightReductionMoney",
					CommonUtil.nullToZero(report.getNightReductionMoney()));
			mapValue.put("reductionMoney",
					CommonUtil.nullToZero(report.getReductionMoney()));
			mapValue.put("freeCompensationMoney",
					CommonUtil.nullToZero(report.getFreeCompensationMoney()));
			mapValue.put("totalPayMoney", report.getTotalPayMoney());
			mapValue.put("invoiceMoney", CommonUtil.nullToZero(report.getInvoiceMoney()));
			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * @describe 租赁收入报表导出
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算今天、本月、本年的合计")
	@RequestMapping(value = "/getTodayAndThisMonthAndYearAmount", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getTodayAndThisMonthAndYearAmount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		String lpn = request.getParameter("lpn");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String orderType = request.getParameter("orderType");

		final RentReportVo todayAmount = this.rentReportService.getTodayAmount(
				cityCode, name, lpn,orderType);
		final RentReportVo thisMonthAmount = this.rentReportService
				.getThisMonthAmount(cityCode, name, lpn,orderType);
		final RentReportVo thisYearAmount = this.rentReportService
				.getThisYearAmount(cityCode, name, lpn, invoiceStatus,orderType);

		Map<String, RentReportVo> map = new HashMap<String, RentReportVo>() {
			private static final long serialVersionUID = 1L;

			{
				put("todayAmount", todayAmount);
				put("thisMonthAmount", thisMonthAmount);
				put("thisYearAmount", thisYearAmount);
			}
		};
		response.getWriter().print(FastJsonUtils.toJson(map));
		return null;
	}

	/**
	 * @describe 计算本年的合计
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算本年的合计")
	@RequestMapping(value = "/getRentYearTotal", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String getRentYearTotal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		String lpn = request.getParameter("lpn");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String orderType = request.getParameter("orderType");
		final RentReportVo thisYearAmount = this.rentReportService
				.getThisYearAmount(cityCode, name, lpn, invoiceStatus,orderType);

		Map<String, RentReportVo> map = new HashMap<String, RentReportVo>() {
			private static final long serialVersionUID = 1L;
			{
				put("thisYearAmount", thisYearAmount);
			}
		};
		response.getWriter().print(FastJsonUtils.toJson(map));
		return null;
	}

	/**
	 * @describe 计算合计
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算本年的合计")
	@RequestMapping(value = "/getRentTotal", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String getRentTotal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String cityCode = request.getParameter("cityCode");
		String name = request.getParameter("name");
		String lpn = request.getParameter("lpn");
		String invoiceStatus = request.getParameter("invoiceStatus");
		String orderType = request.getParameter("orderType");
		String beginStr = request.getParameter("begin");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}

		final RentReportVo rentTotal = this.rentReportService.getRentTotal(
				cityCode, name, lpn, begin, end, invoiceStatus,orderType);

		Map<String, RentReportVo> map = new HashMap<String, RentReportVo>() {
			private static final long serialVersionUID = 1L;
			{
				put("rentTotal", rentTotal);
			}
		};
		response.getWriter().print(FastJsonUtils.toJson(map));
		return null;
	}

	/**
	 * @describe 会员消费对账单
	 * @throws Exception
	 */
	@SystemServiceLog(description = "会员消费对账单")
	@RequestMapping(value = "/accountCheck_page", method = { RequestMethod.GET })
	public String accountCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "report/accountCheck";
	}

	/**
	 * 会员消费对账单
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "会员消费对账单")
	@RequestMapping(value = "/account_report", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryAccountReport(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");

		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");
		String cityName = request.getParameter("cityName");
		String brandName = request.getParameter("brandName");
		String beginStr = request.getParameter("begin");


      /*  Timestamp payBeginTime = getTimestampReqParam(request, "payBeginTime");
        Timestamp payEndTime = getTimestampReqParam(request, "payEndTime");
*/
        Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}

        String consumptionType = request.getParameter("comsuptionType");
        String payType = request.getParameter("payType");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        paramMap.put("from", from);
        paramMap.put("to", to);
        paramMap.put("cityName", cityName);
        paramMap.put("brandName", brandName);
        paramMap.put("begin", begin);
        paramMap.put("end", end);
        paramMap.put("consumptionType", consumptionType);
        paramMap.put("payType", payType);

	/*	Pager<AccountCheckVo> pager = this.accountCheckService
				.queryAccountReport(name, begin, end, from, to,cityName,brandName);*/
        Pager<AccountCheckVo> pager = this.accountCheckService
                .queryAccountReport(paramMap);

		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}

	/**
	 * @describe 会员消费对账单导出
	 * @throws Exception
	 */
	@SystemServiceLog(description = "会员消费对账单导出")
	@RequestMapping(value = "/export_account_report_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String exportAccountReportExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		 String name = request.getParameter("name");

		String beginStr = request.getParameter("begin");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String cityName = request.getParameter("cityName");
		String brandName = request.getParameter("brandName");
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}
		String consumptionType = request.getParameter("comsuptionType");
        String payType = request.getParameter("payType");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        paramMap.put("cityName", cityName);
        paramMap.put("brandName", brandName);
        paramMap.put("begin", begin);
        paramMap.put("end", end);
        paramMap.put("consumptionType", consumptionType);
        paramMap.put("payType", payType);

    /*    List<AccountCheckVo> reports = accountCheckService
                .queryAccountReportList(name, begin, end, cityName, brandName);*/

        List<AccountCheckVo> reports = accountCheckService
                .queryAccountReportList(paramMap);
        List<Map<String, Object>> list = createAccountReportExcelRecord(reports);
/*
        //当年总合计
        AccountCheckVo yearAccountCheck = accountCheckService.getCurrentYearTotal(paramMap);
        //总合计
        AccountCheckVo totalCheck = accountCheckService.getTotalAccountCheck(paramMap);
*/


//		AccountCheckVo thisYearAmount = this.accountCheckService	.getAccountTotal(name, begin, end);

		/*
		
		//获取总合计数据开始
		List<AccountCheckVo> depositTotals = accountCheckService
				.getAccountChargeTotal(name, begin, end);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("begin", begin);
		map.put("end", end);
		List<AccountCheckVo> ztoyeLists = accountCheckService.getTotalZtoyeReturnedBalanceByTime(map);
		// 查询会员余额消费总额
		Double balanceConsumptionTotal = accountCheckService
				.getAccountBalanceConsumptionTotal(name, begin, end);
		

		final double HUNDRED = 100.0;
		AccountCheckTotalVo totalVo = new AccountCheckTotalVo();
		if (thisYearAmount == null) {
			totalVo.setConsumeTotal(0.0);
			totalVo.setBalanceConsumeTotal(0.0);
			totalVo.setRefundTotal(0.0);
			totalVo.setReturnedMoneyTotal(0.0);
		} else {
			Double consumptionMoney = CommonUtil.nullToZero(thisYearAmount  
					.getConsumptionMoney());
			totalVo.setConsumeTotal(consumptionMoney-balanceConsumptionTotal);
			totalVo.setRefundTotal(CommonUtil.nullToZero(thisYearAmount
					.getRefundMoney()));
			totalVo.setBalanceConsumeTotal(balanceConsumptionTotal);
			AccountCheckVo vo = ztoyeLists.get(0);
			double mon = 0.00;
			try {
				mon = vo.getChargeMoney();
			} catch (Exception e) {
				mon = 0.00;
			}
			totalVo.setReturnedMoneyTotal(mon/ HUNDRED);
		}

		if (CollectionUtils.isEmpty(depositTotals)) {
			totalVo.setDepositChargeTotal(0.0);
			totalVo.setBalanceChargeTotal(0.0);
		} else {

			for (AccountCheckVo accountCheckVo : depositTotals) {
				if (SysConstant.BALANCE_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					totalVo.setBalanceChargeTotal(CommonUtil.nullToZero(accountCheckVo.getChargeMoney()) / HUNDRED);
				} else if (SysConstant.DEPOSIT_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					totalVo.setDepositChargeTotal(CommonUtil
							.nullToZero(accountCheckVo.getChargeMoney())
							/ HUNDRED);
				}
			}
		}

		// 获取本年度的会员余额和押金的合计金额
		AccountCheckVo banlanceTotal = accountCheckService
				.getAccountBanlanceTotal(name, begin, end);
		if (banlanceTotal != null) {
			totalVo.setBalanceTotal(CommonUtil.nullToZero(banlanceTotal
					.getBalance()));
			totalVo.setDepositTotal(CommonUtil.nullToZero(banlanceTotal
					.getDeposit()));
		} else {
			totalVo.setBalanceTotal(0L);
			totalVo.setDepositTotal(0L);
		}
		//获取总合计结束
		*/
	/*	String columnNames[] = { "所属城市","车牌号","车辆品牌","日期", "会员ID", "会员姓名", "手机号码", "押金充值", "余额充值",
				"充值日期", "即时消费","余额消费", "消费日期","返余现", "退款", "押金", "余额" };// 列名*/

        String columnNames[] = { "所属城市","车牌号码","车辆品牌","交易日期", "会员ID", "会员姓名", "手机号码", "支付方式", "费用类型",
                "支付金额", "押金", "余额" };// 列名

/*
		String keys[] = { "cityName","lpn","brandName","endTime", "memberId", "name", "phone",
				"depositChargeMoney", "chargeMoney", "chargeTime",
				"consumptionMoney","balanceConsumptionMoney","consumptionTime", "returnedMoneyTotal","refundMoney",
				"deposit", "balance" };// map中的key
*/


        String keys[] = { "cityName","lpn","brandName","endTime", "memberId", "name", "phone",
                "payType","consumptionType", "payMoney","deposit", "balance" };// map中的key

		//获取总合计结束
//		BigDecimal bd = new BigDecimal(totalVo.getBalanceTotal());
		DecimalFormat df = new DecimalFormat("0.00");
		/*String totalSum[] = { "", "", "", "", "", "", "","","",
				totalVo.getDepositChargeTotal()==null?"0.00":totalVo.getDepositChargeTotal().toString(),
				totalVo.getBalanceChargeTotal()==null?"0.00":totalVo.getBalanceChargeTotal().toString(), "",
				totalVo.getConsumeTotal()==null?"0.00":df.format(totalVo.getConsumeTotal()),
				totalVo.getBalanceConsumeTotal().toString(), "",
				totalVo.getReturnedMoneyTotal().toString(),
				totalVo.getRefundTotal().toString(),
				String.valueOf(totalVo.getDepositTotal()/100d),
				String.valueOf((bd.divide(new BigDecimal(100.0)))) };*/
/*		String totalSum[] = {"", "", "", "", "", "", "","","",
            totalCheck.getPayMoney().toString(),totalCheck.getDeposit().toString(),totalCheck.getBalance().toString()
        };*/
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
//			ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,totalSum ).write(os);
			ExcelUtil.createWorkBook(list, keys, columnNames ).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("会员消费对账单.xls").getBytes(), "iso-8859-1"));
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

	private List<Map<String, Object>> createAccountReportExcelRecord(
			List<AccountCheckVo> reports) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "会员消费对账单");
		listmap.add(map);
		AccountCheckVo report = null;
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("cityName", report.getCityName());
			mapValue.put("lpn", report.getLpn());
			mapValue.put("brandName", report.getBrandName());
			mapValue.put("endTime",
					DateTime.getDateTimeString(report.getEndTime()));
			mapValue.put("memberId", report.getMemberId());
			mapValue.put("name", report.getName());
			mapValue.put("phone", report.getPhone());
            mapValue.put("payMoney", report.getPayMoney());
            mapValue.put("payType", report.getPayType());
            mapValue.put("consumptionType", report.getConsumptionType());
//			mapValue.put("depositChargeMoney",CommonUtil.nullToZero(report.getDepositChargeMoney()));
//			mapValue.put("chargeMoney",CommonUtil.nullToZero(report.getChargeMoney()));
//			mapValue.put("chargeTime",DateTime.getDateTimeString(report.getChargeTime()));
//			mapValue.put("consumptionMoney",CommonUtil.nullToZero(report.getConsumptionMoney()));
//			mapValue.put("balanceConsumptionMoney",CommonUtil.nullToZero(report.getBalanceConsumptionMoney()));
//			mapValue.put("consumptionTime",DateTime.getDateTimeString(report.getConsumptionTime()));
//			mapValue.put("returnedMoneyTotal",CommonUtil.nullToZero(report.getReturnedMoneyTotal()));
//			mapValue.put("refundMoney",CommonUtil.nullToZero(report.getRefundMoney()));
			if (null != report.getBalance()) {
				mapValue.put("balance",
						CommonUtil.fenToYuanDecimal(report.getBalance(), 2));
			}
			if (null != report.getDeposit()) {
				mapValue.put("deposit",
						CommonUtil.fenToYuanDecimal(report.getDeposit(), 2));
			}

			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * @describe 租赁收入报表导出
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算今天、本月、本年的合计")
	@RequestMapping(value = "/getAccountTodayAndThisMonthAndYearAmount", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getAccountTodayAndThisMonthAndYearAmount(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");

		final AccountCheckVo todayAmount = this.accountCheckService
				.getTodayAmount();
		final AccountCheckVo thisMonthAmount = this.accountCheckService
				.getThisMonthAmount();
		final AccountCheckVo thisYearAmount = this.accountCheckService
				.getThisYearAmount();

		Map<String, AccountCheckVo> map = new HashMap<String, AccountCheckVo>() {
			private static final long serialVersionUID = 1L;

			{
				put("todayAmount", todayAmount);
				put("thisMonthAmount", thisMonthAmount);
				put("thisYearAmount", thisYearAmount);
			}
		};
		response.getWriter().print(FastJsonUtils.toJson(map));
		return null;
	}

	/**
	 * @describe 会员对账单
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算本年的合计")
	@RequestMapping(value = "/getAccountYearTotal", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getAccountYearTotal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");

		final AccountCheckVo thisYearAmount = this.accountCheckService
				.getThisYearTotal(name);
		List<AccountCheckVo> depositTotals = accountCheckService
				.getThisYearDepositTotal(name);
		// 获取众泰返现的数据
		List<AccountCheckVo> ztoyeLists = accountCheckService.getZtoyeReturnedBalanceRecords(name);
		// 查询会员余额消费总额
		Double balanceConsumptionTotal = accountCheckService.getThisYearAccountBalanceConsumptionTotal(name);

		AccountCheckTotalVo totalVo = new AccountCheckTotalVo();
		final double HUNDRED = 100.0;
		if (thisYearAmount == null) {
			totalVo.setConsumeTotal(0.0);
			totalVo.setBalanceConsumeTotal(0.0);
			totalVo.setRefundTotal(0.0);
		} else {
			totalVo.setConsumeTotal(CommonUtil.nullToZero(thisYearAmount
					.getConsumptionMoney()-balanceConsumptionTotal));
			totalVo.setRefundTotal(CommonUtil.nullToZero(thisYearAmount
					.getRefundMoney()));
			totalVo.setBalanceConsumeTotal(balanceConsumptionTotal);
			AccountCheckVo vo = ztoyeLists.get(0);
			double mon = 0;
			try {
				mon = vo.getChargeMoney();
			} catch (Exception e) {
				mon = 0;
			}
			totalVo.setReturnedMoneyTotal(mon/HUNDRED);
		}

		if (CollectionUtils.isEmpty(depositTotals)) {
			totalVo.setDepositChargeTotal(0.0);
			totalVo.setBalanceChargeTotal(0.0);
		} else {

			for (AccountCheckVo accountCheckVo : depositTotals) {
				if (SysConstant.BALANCE_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					totalVo.setBalanceChargeTotal(CommonUtil
							.nullToZero(accountCheckVo.getChargeMoney())
							/ HUNDRED);
				} else if (SysConstant.DEPOSIT_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					totalVo.setDepositChargeTotal(CommonUtil
							.nullToZero(accountCheckVo.getChargeMoney())
							/ HUNDRED);
				}
			}
		}

		// 获取本年度的会员余额和押金的合计金额
		AccountCheckVo banlanceTotal = accountCheckService
				.getThisBanlanceTotal(name);
		if (banlanceTotal != null) {
			totalVo.setBalanceTotal(CommonUtil.nullToZero(banlanceTotal
					.getBalance()));
			totalVo.setDepositTotal(CommonUtil.nullToZero(banlanceTotal
					.getDeposit()));
		} else {
			totalVo.setBalanceTotal(0L);
			totalVo.setDepositTotal(0L);
		}
		response.getWriter().print(FastJsonUtils.toJson(totalVo));
		return null;
	}

	/**
	 * @describe 会员对账单合计
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算合计")
	@RequestMapping(value = "/getAccountTotal", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String getAccountTotal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");
		String beginStr = request.getParameter("begin");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}

		AccountCheckVo thisYearAmount = this.accountCheckService
				.getAccountTotal(name, begin, end);

		List<AccountCheckVo> depositTotals = accountCheckService
				.getAccountChargeTotal(name, begin, end);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("begin", begin);
		map.put("end", end);
		List<AccountCheckVo> ztoyeLists = accountCheckService.getTotalZtoyeReturnedBalanceByTime(map);
		// 查询会员余额消费总额
		Double balanceConsumptionTotal = accountCheckService
				.getAccountBalanceConsumptionTotal(name, begin, end);
		

		final double HUNDRED = 100.0;
		AccountCheckTotalVo totalVo = new AccountCheckTotalVo();
		if (thisYearAmount == null) {
			totalVo.setConsumeTotal(0.0);
			totalVo.setBalanceConsumeTotal(0.0);
			totalVo.setRefundTotal(0.0);
			totalVo.setReturnedMoneyTotal(0.0);
		} else {
			Double consumptionMoney = CommonUtil.nullToZero(thisYearAmount
					.getConsumptionMoney());
			totalVo.setConsumeTotal(consumptionMoney-balanceConsumptionTotal);
			totalVo.setRefundTotal(CommonUtil.nullToZero(thisYearAmount
					.getRefundMoney()));
			totalVo.setBalanceConsumeTotal(balanceConsumptionTotal);
			AccountCheckVo vo = ztoyeLists.get(0);
			double mon = 0.00;
			try {
				mon = vo.getChargeMoney();
			} catch (Exception e) {
				mon = 0.00;
			}
			totalVo.setReturnedMoneyTotal(mon/ HUNDRED);
		}

		if (CollectionUtils.isEmpty(depositTotals)) {
			totalVo.setDepositChargeTotal(0.0);
			totalVo.setBalanceChargeTotal(0.0);
		} else {

			for (AccountCheckVo accountCheckVo : depositTotals) {
				if (SysConstant.BALANCE_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					totalVo.setBalanceChargeTotal(CommonUtil.nullToZero(accountCheckVo.getChargeMoney()) / HUNDRED);
				} else if (SysConstant.DEPOSIT_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					totalVo.setDepositChargeTotal(CommonUtil
							.nullToZero(accountCheckVo.getChargeMoney())
							/ HUNDRED);
				}
			}
		}

		// 获取本年度的会员余额和押金的合计金额
		AccountCheckVo banlanceTotal = accountCheckService
				.getAccountBanlanceTotal(name, begin, end);
		if (banlanceTotal != null) {
			totalVo.setBalanceTotal(CommonUtil.nullToZero(banlanceTotal
					.getBalance()));
			totalVo.setDepositTotal(CommonUtil.nullToZero(banlanceTotal
					.getDeposit()));
		} else {
			totalVo.setBalanceTotal(0L);
			totalVo.setDepositTotal(0L);
		}
		response.getWriter().print(FastJsonUtils.toJson(totalVo));
		return null;
	}

	
	/************* 充电财务报表 ****************/
	@SystemServiceLog(description = "充电财务报表")
	@RequestMapping(value = "/chargingFinanceReport", method = { RequestMethod.GET })
	public String chargingFinanceReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "report/chargingFinanceReport";
	}
	
	@SystemServiceLog(description="充电车辆所属公司(城市)")	
	@RequestMapping(value = "/all_car_cityCombobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String sysCityCombobox(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");	
		
		List<City> cityList = cityMapper.queryAllCarCity();
		
		if(cityList != null && cityList.size() > 0) {
			StringBuffer tree=new StringBuffer();
			tree.append("[{\"id\":\"00\",\"text\":\"不限\"},");
			tree.append("{\"id\":\"-1\",\"text\":\"外部车辆\"},");
			
			for(int i=0; i < cityList.size(); i++ ){
				City city=cityList.get(i);
				tree.append("{");
				tree.append("\"id\":\""+city.getCode()+"\",");
				tree.append("\"text\":\""+city.getName()+"\"");
				if(i < cityList.size() - 1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
			tree.append("]");
			response.getWriter().print(tree.toString());
		}else{
			response.getWriter().print("[{\"id\":\"00\",\"text\":\"不限\"}]");
		}
		return null;	
	}
	
	/**
	 * 会员消费对账单
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "充电财务报表")
	@RequestMapping(value = "/charging_finance_report", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queryChargingFinanceReport(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//充电车辆所属公司(城市)
		String cityCode = null;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if (user.getCityCode().equals("00")) {
			if (StringUtils.isNotBlank(request.getParameter("cityCode"))) {
				if (!request.getParameter("cityCode").equals("00")) {
					cityCode = request.getParameter("cityCode");
				}
			}
		} else {
			cityCode = user.getCityCode();
		}
		
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		Integer from = pageInfo.get("first_page");
		Integer to = pageInfo.get("page_size");

		String beginStr = request.getParameter("begin");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}
		String invoiceStatus = request.getParameter("invoiceStatus");

        Timestamp payBeginTime = getTimestampReqParam(request, "payBeginTime");

        Timestamp payEndTime = getTimestampReqParam(request, "payEndTime");
        String payStatus = request.getParameter("payStatus");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cityCode", cityCode);
        map.put("from", from);
        map.put("to", to);
        map.put("begin", begin);
        map.put("end", end);
        map.put("invoiceStatus",invoiceStatus);
        map.put("payBeginTime", payBeginTime);
        map.put("payEndTime", payEndTime);
        map.put("payStatus", payStatus);
        Pager<ChargingFinanceReportVo> pager = chargingOrderInfoService.queryChargingFinanceReport(map);
/*
		Pager<ChargingFinanceReportVo> pager = this.chargingOrderInfoService.queryChargingFinanceReport(
				cityCode,payStatus, begin, end, payBeginTime,payEndTime,from, to, invoiceStatus);*/

		
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}

	/**
	 * @describe 会员消费对账单导出
	 * @throws Exception
	 */
	
	@SystemServiceLog(description = "充电财务报表导出")
	@RequestMapping(value = "/export_charging_finance_report_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String exportChargingFinanceReportExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		//充电车辆所属公司(城市)
		String cityCode = null;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if (user.getCityCode().equals("00")) {
			if (StringUtils.isNotBlank(request.getParameter("cityCode"))) {
				if (!request.getParameter("cityCode").equals("00")) {
					cityCode = request.getParameter("cityCode");
				}
			}
		} else {
			cityCode = user.getCityCode();
		}
		String beginStr = request.getParameter("begin");
		Timestamp begin = null;
		if (StringUtils.isNotBlank(beginStr)) {
			begin = DateTime.getDateTime(beginStr);
		}
		String endStr = request.getParameter("end");
		Timestamp end = null;
		if (StringUtils.isNotBlank(endStr)) {
			end = DateTime.getDateTime(endStr);
		}
		String invoiceStatus = request.getParameter("invoiceStatus");
        Timestamp payBeginTime = getTimestampReqParam(request, "payBeginTime");

        Timestamp payEndTime = getTimestampReqParam(request, "payEndTime");

        String payStatus = request.getParameter("payStatus");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cityCode", cityCode);
        map.put("from", null);
        map.put("to", null);
        map.put("begin", begin);
        map.put("end", end);
        map.put("invoiceStatus",invoiceStatus);
        map.put("payBeginTime", payBeginTime);
        map.put("payEndTime", payEndTime);
        map.put("payStatus", payStatus);


//		List<ChargingFinanceReportVo> reportsList = chargingOrderInfoService.queryChargingFinanceReportList(cityCode,payStatus, begin, end,payBeginTime,payEndTime, invoiceStatus);
		List<ChargingFinanceReportVo> reportsList = chargingOrderInfoService.queryChargingFinanceReportList(map);
		// 添加合计到execl
//		ChargingFinanceReportVo vo = chargingOrderInfoService.queryChargingSum(cityCode,payStatus,request.getParameter("payBeginTime"),request.getParameter("payEndTime") ,invoiceStatus,beginStr,endStr);
        List<ChargingFinanceReportVo> totalList = chargingOrderInfoService.getChargeSum(map);
        reportsList.addAll(totalList);
        List<Map<String, Object>> list = createChargingFinanceReportExcelRecord(reportsList);

		String columnNames[] = { "所属城市","充电开始时间","支付时间","支付状态","车牌号", "会员ID", "网点名称", "充电桩编码",
				"类型【慢快充】", "充电度数","电费单价（元/度）", "服务费单价（元/度）", "充电收入（元）","计费金额（元）", "开票金额（元）",""};// 列名
		/*String totalSum[] = {
				"", "", "", "", "", "", "",
				vo.getChargingAmountSum()==null?"0.00":vo.getChargingAmountSum().toString(),
				"", "", vo.getPayMoneySum()==null?"0.00":vo.getPayMoneySum().toString(),
				vo.getInvoiceMoneySum()==null?"0.00":vo.getInvoiceMoneySum().toString()
				};*/
		String keys[] = {  "cityName","createTime", "payTime","payStatus","lpn","memberId",
				"parkName", "equipmentCode", "equipmentType",
				"chargingAmount","chargingPrice","servicePrice", "payMoney","orderMoney", "invoiceMoney"};// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
		/*	ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,totalSum )
					.write(os);*/
            ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
        } catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("充电财务报表.xls").getBytes(), "iso-8859-1"));
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

	private List<Map<String, Object>> createChargingFinanceReportExcelRecord(
			List<ChargingFinanceReportVo> reports) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "充电财务报表");
		listmap.add(map);
		ChargingFinanceReportVo report = null;
		for (int j = 0; j < reports.size(); j++) {
			report = reports.get(j);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("createTime",
					DateTime.getDateTimeString(report.getCreateTime()));
            mapValue.put("payTime",isEmpty(report.getPayTime())==true ? "":    DateTime.getDateTimeString(report.getPayTime()));
            String payStatus = "";
            if (isNotEmpty(report.getPayStatus())) {
                mapValue.put("payStatus", report.getPayStatus().equals("finish")?"已支付":"未支付");
            }else {
                mapValue.put("payStatus", payStatus);
            }
            mapValue.put("lpn", StringUtils.isBlank(report.getLpn())?"":report.getLpn());
			mapValue.put("cityName", report.getCityName());
			mapValue.put("memberId", isEmpty(report.getMemberId())==true?"":report.getMemberId());
			mapValue.put("parkName", isEmpty(report.getParkName())==true?"":report.getParkName());
			mapValue.put("equipmentCode",isEmpty(report.getEquipmentCode())==true?"": report.getEquipmentCode());
			if (report.getEquipmentType() != null ) {
				switch (report.getEquipmentType()) {
				case 1:
					mapValue.put("equipmentType", "快");
					break;
				case 2:
					mapValue.put("equipmentType", "慢");
					break;
				default:
					break;
				}
			}
			mapValue.put("chargingAmount", report.getChargingAmount());
			mapValue.put("chargingPrice",  isEmpty(report.getChargingPrice())==true?0.0:report.getChargingPrice());
			mapValue.put("servicePrice", isEmpty(report.getServicePrice())==true?0.0: report.getServicePrice());
			mapValue.put("payMoney",  isEmpty(report.getPayMoney())==true?0.0:report.getPayMoney());
			mapValue.put("orderMoney",  isEmpty(report.getOrderMoney())==true?0.0:report.getOrderMoney());
			mapValue.put("invoiceMoney", isEmpty(report.getInvoiceMoney())==true?0.0: report.getInvoiceMoney());

			listmap.add(mapValue);
		}
		return listmap;
	}

	/**
	 * @describe 会员对账单
	 * @throws Exception
	 */
	@SystemServiceLog(description = "计算本年合计、总合计")
	@RequestMapping(value = "/getChargingSum", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getChargingSum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String beginStr = request.getParameter("begin");
		String endStr = request.getParameter("end");
        String  payBeginTime = request.getParameter( "payBeginTime");

        String payEndTime = request.getParameter( "payEndTime");
		//充电车辆所属公司(城市)
		String cityCode = null;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if (user.getCityCode().equals("00")) {
			if (StringUtils.isNotBlank(request.getParameter("cityCode"))) {
				if (!request.getParameter("cityCode").equals("00")) {
					cityCode = request.getParameter("cityCode");
				}
			}
		} else {
			cityCode = user.getCityCode();
		}
		String invoiceStatus = request.getParameter("invoiceStatus");
        String payStatus = request.getParameter("payStatus");
        ChargingFinanceReportVo vo = chargingOrderInfoService.queryChargingSum(cityCode,payStatus,payBeginTime ,payEndTime,invoiceStatus,beginStr,endStr);
		response.getWriter().print(FastJsonUtils.toJson(vo));
		return null;
	}

}
