package com.iber.portal.service.timeShare;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.timeShare.TimeSharePayMapper;
import com.iber.portal.model.timeShare.TimeSharePay;
import com.iber.portal.vo.report.CarIncomeReport;
import com.iber.portal.vo.report.TimeShareIncomeReport;

@Service
public class TimeSharePayService {

	@Autowired
	private TimeSharePayMapper timeSharePayMapper;
	 
	public int selectAllCarIncomeReportRecords(Map<String, Object> map){
		return timeSharePayMapper.selectAllCarIncomeReportRecords(map) ;
	}
	
	public List<CarIncomeReport> queryCarIncomeReport(Map<String,Object> map) {
		return timeSharePayMapper.queryCarIncomeReport(map);
	}
	
	public Pager< CarIncomeReport> queryCarIncomeReportList(Map<String ,Object> map) {
		List< CarIncomeReport> listObj = queryCarIncomeReport(map);
		Pager< CarIncomeReport> pager = new Pager< CarIncomeReport>();
		pager.setDatas(listObj);
		pager.setTotalCount(selectAllCarIncomeReportRecords(map));
		return pager;
	}
	
	public int selectTimeShareReportRecords(Map<String, Object> map){
		return timeSharePayMapper.selectTimeShareReportRecords(map) ;
	}
    
	public List<TimeShareIncomeReport> queryTimeShareReport(Map<String, Object> map){
		return timeSharePayMapper.queryTimeShareReport(map) ;
	}
    
	public Pager< TimeShareIncomeReport> queryTimeShareIncomeReportList(Map<String ,Object> map) {
		List< TimeShareIncomeReport> listObj = queryTimeShareReport(map);
		Pager< TimeShareIncomeReport> pager = new Pager< TimeShareIncomeReport>();
		pager.setDatas(listObj);
		pager.setTotalCount(selectTimeShareReportRecords(map));
		return pager;
	}
	
	/**
	 * 根据订单号查询，并且订单完成时间要在lastCountTime之后
	 * @param orderId
	 * @return
	 * @author ouxx
	 * @date 2016-11-30 下午1:56:49
	 */
	public TimeSharePay getByOrderId(String orderId, Date lastCountTime){
		return this.timeSharePayMapper.getByOrderId(orderId, lastCountTime);
	}
	
	public List<TimeSharePay> selectByOrderTimeAndLpn(String date , String lpn){
		return this.timeSharePayMapper.selectByOrderTimeAndLpn(date, lpn) ;
	}

	public TimeSharePay selectByOrderId(String objId) {
		return timeSharePayMapper.selectByOrderId(objId);
	}

	public int updateInvoiceStatus(String id, Integer invoiceStatus) {
		return timeSharePayMapper.updateInvoiceStatus(id,invoiceStatus);
	}
	public int bitchUpdateInvoiceStatus(List<String> ids, Integer invoiceStatus) {
		return timeSharePayMapper.bitchUpdateInvoiceStatus(ids, invoiceStatus);
	}
}
