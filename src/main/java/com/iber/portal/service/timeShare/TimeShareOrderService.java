package com.iber.portal.service.timeShare;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.model.car.CarChargingRemind;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.vo.timeShare.LongTimeShareOrderVo;
import com.iber.portal.vo.timeShare.TimeShareCarOrderVo;
import com.iber.portal.vo.timeShare.TimeShareOrderVo;
import com.iber.portal.zhima.vo.ZhimaRequestVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeShareOrderService {

	@Autowired
	private TimeShareOrderMapper timeShareOrderMapper;
	 
	public List<TimeShareOrderVo> getAll(Map<String,Object> map) {
		return timeShareOrderMapper.getAll(map);
	}
	
	public int getAllNum(Map<String,Object> map){
    	return timeShareOrderMapper.getAllNum(map);
    }
	
	public Pager<TimeShareOrderVo> getPagerAll(Map<String,Object> map) {
		List<TimeShareOrderVo> listObj = getAll(map);
		Pager< TimeShareOrderVo> pager = new Pager< TimeShareOrderVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(map));
		return pager;
	}
	
	public List<TimeShareOrderVo> getHistoryAll(Map<String,Object> map) {
		return timeShareOrderMapper.getHistoryAll(map);
	}
	
	public int getHistoryAllNum(Map<String,Object> map){
    	return timeShareOrderMapper.getHistoryAllNum(map);
    }
	
	public Pager<TimeShareOrderVo> getPagerHistoryAll(Map<String,Object> map) {
		List<TimeShareOrderVo> listObj = getHistoryAll(map);
		Pager<TimeShareOrderVo> pager = new Pager<TimeShareOrderVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getHistoryAllNum(map));
		return pager;
	}
	
	
	public List<TimeShareOrder> getHistoryAllFinish(Map<String,Object> map) {
		return timeShareOrderMapper.getHistoryAllFinish(map);
	}
	
	public int getHistoryAllFinishNum(Map<String,Object> map){
    	return timeShareOrderMapper.getHistoryAllFinishNum(map);
    }
	
	public Pager< TimeShareOrder> getHistoryAllFinish(String lpn ,String cityCode ,String memberName ,int first_page, int page_size) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("memberName", memberName) ;
		map.put("offset", first_page) ;
		map.put("rows", page_size) ;
		map.put("cityCode", cityCode) ;
		map.put("lpn", lpn) ;
		List< TimeShareOrder> listObj = getHistoryAllFinish(map);
		Pager< TimeShareOrder> pager = new Pager< TimeShareOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(getHistoryAllFinishNum(map));
		return pager;
	}
	
	public List<TimeShareCarOrderVo> queryTimeShareOrderByLpn(Map<String,Object> map){
		return timeShareOrderMapper.queryTimeShareOrderByLpn(map) ;
	}
	
	public List<TimeShareOrder> queryOrderByOrderId(String orderId){
		return timeShareOrderMapper.queryOrderByOrderId(orderId) ;
	}
	
	public void saveOrUpdateOrder(TimeShareOrder order) {
		timeShareOrderMapper.updateByPrimaryKeySelective(order) ;
	}
	public List<TimeShareOrder> queryOrderByMemberId(Integer memberId) {
		return timeShareOrderMapper.queryOrderByMemberId(memberId) ;
	}
	
	/**
	 * 查完成了的订单
	 * @param orderId
	 * @return
	 * @author ouxx
	 * @date 2016-11-29 下午7:09:52
	 */
	public TimeShareOrderVo getFinishedOrderByOrderId(String orderId){
		return timeShareOrderMapper.getFinishedOrderByOrderId(orderId);
	}

	public List<CarChargingRemind> selectAllUsingCar(Map<String, Object> paramMap) {
		return timeShareOrderMapper.selectAllUsingCar(paramMap);
	}

	public List<TimeShareCarOrderVo> queryOrderByLpn(Map<String, Object> map) {
		return timeShareOrderMapper.queryOrderByLpn(map);
	}

	public Pager<TimeShareOrderVo> queryPageCancelOrder(Map<String, Object> record) {
		List<TimeShareOrderVo> listObj = timeShareOrderMapper.queryCancelOrder(record);
		Pager< TimeShareOrderVo> pager = new Pager< TimeShareOrderVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(queryCancelOrderNum(record));
		return pager;
	}

	private int queryCancelOrderNum(Map<String, Object> record) {
		return timeShareOrderMapper.queryCancelOrderNum(record);
	}

	public CarChargingRemind selectUserByLpn(String lpn) {
		return timeShareOrderMapper.selectUserByLpn(lpn);
	}
	
	public int updateIsManualSaveByOrderId(String orderId) {
		return timeShareOrderMapper.updateIsManualSaveByOrderId(orderId);
	}

	public List<LongTimeShareOrderVo> queryAllUserAndReturnOrders(TimeShareOrder param){
		return timeShareOrderMapper.queryAllUserAndReturnOrders(param);
	}
	
	public List<TimeShareOrder> getAllOrderInfo() {
		return timeShareOrderMapper.getAllOrderInfo();
	}

	/**
	 * 根据时间查询一个月范围内的订单
	 * @param param 时间
	 * @return
	 */
	public List<TimeShareOrder> queryMonthOrderByDate(Map<String, Date> param) {
		return timeShareOrderMapper.queryMonthOrderByDate(param);
	}

	/**
	 * 查询第一天记录的时间
	 * @return
	 */
	public Date queryMinCreateTime(){
		return timeShareOrderMapper.queryMinCreateTime();
	}
	
	public Integer queryCarOrderedStatusByLpn(String lpn){
	  return timeShareOrderMapper.queryCarOrderedStatusByLpn(lpn);
	}

	public List<ZhimaRequestVo> queryAllNoPayOrderInSpecifiedDate(Map<String, Object> map) {
		return timeShareOrderMapper.queryAllNoPayOrderInSpecifiedDate(map);
	}

	public List<ZhimaRequestVo> queryUntreatedAndFinishOrder(Integer value) {
		return timeShareOrderMapper.queryUntreatedAndFinishOrder(value);
	}

	public List<ZhimaRequestVo> queryUntreatedAndNoPayInSpecifiedDate(
			Map<String, Object> map) {
		return timeShareOrderMapper.queryUntreatedAndNoPayInSpecifiedDate(map);
	}

	public Pager<TimeShareOrder> getOrderInfoByMemberPhone(
			HashMap<String, Object> map) {
		List< TimeShareOrder> listObj = timeShareOrderMapper.getOrderInfoByMemberPhone(map);
		Pager< TimeShareOrder> pager = new Pager< TimeShareOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(getOrderInfoNumByMemberPhone(map));
		return pager;
	}

	private int getOrderInfoNumByMemberPhone(HashMap<String, Object> map) {
		return timeShareOrderMapper.getOrderInfoNumByMemberPhone(map);
	}

	public List<ZhimaRequestVo> queryNotHandleAccidentAndFinishOrderRecords(Integer value) {
		return timeShareOrderMapper.queryNotHandleAccidentAndFinishOrderRecords(value);
	}

	public List<ZhimaRequestVo> queryAccidentAndNoPayOrderRecords(
			Map<String, Object> map) {
		return timeShareOrderMapper.queryAccidentAndNoPayOrderRecords(map);
	}

	public List<ZhimaRequestVo> queryAllFinishOrder() {
		return timeShareOrderMapper.queryAllFinishOrder();
	}
	public List<TimeShareCarOrderVo> queryAllCarRunOrderInfo() {
		return timeShareOrderMapper.queryAllCarRunOrderInfo();
	}

	public List<Map<String, String>> queryOrderIdsByLpn(String lpn) {
		return timeShareOrderMapper.queryOrderIdsByLpn(lpn);
	}
}
