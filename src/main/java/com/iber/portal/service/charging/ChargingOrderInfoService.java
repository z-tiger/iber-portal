package com.iber.portal.service.charging;

import com.iber.portal.common.Pager;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.charging.ChargingOrderInfoMapper;
import com.iber.portal.model.charging.ChargingOrderInfo;
import com.iber.portal.util.DateTime;
import com.iber.portal.vo.charging.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("chargingOrderMsgService")
public class ChargingOrderInfoService{

	private final static Logger log= Logger.getLogger(ChargingOrderInfoService.class);
	
	@Autowired
    private ChargingOrderInfoMapper  chargingOrderInfoMapper;
		
	public int insert(ChargingOrderInfo record){
		return chargingOrderInfoMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return chargingOrderInfoMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(ChargingOrderInfo record){
		return chargingOrderInfoMapper.updateByPrimaryKey(record) ;
	}

	public ChargingOrderInfo selectByPrimaryKey(Integer id){
		return chargingOrderInfoMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return chargingOrderInfoMapper.getAllNum(paramMap) ;
	}
	
	public Pager<ChargingOrderInfo> queryPageList(Map<String, Object> paramMap){
		List<ChargingOrderInfo> listObj = chargingOrderInfoMapper.queryPageList(paramMap);
		for (ChargingOrderInfo chargingOrderInfo : listObj) {
			Integer type = chargingOrderInfo.getConnectorType();
			if(type.equals(4)) {
				chargingOrderInfo.setEquipmentType("直流快充");
			}else {
				chargingOrderInfo.setEquipmentType("交流慢充");
			}
			Integer orderMoney = chargingOrderInfo.getOrderMoney();
			if (null!=orderMoney && 0<orderMoney) {   // 支付金额不为零,则非免单
				   chargingOrderInfo.setIsFreeOrder("N");
                   chargingOrderInfo.setFreeReason("无");
			}else {
				chargingOrderInfo.setIsFreeOrder("Y"); // 充电订单取消，免单
				 if("member".equals(chargingOrderInfo.getUserType())){
					 if(null!=chargingOrderInfo.getMemberStatus()&&SysConstant.MEMBER_STATUS_USECAR.equals(chargingOrderInfo.getMemberStatus())){
						 //会员用车过程中充电，免单
						 chargingOrderInfo.setFreeReason("会员用车充电");
					 }else if(null!=chargingOrderInfo.getMemberStatus()&&SysConstant.MEMBER_STATUS_RETURN.equals(chargingOrderInfo.getMemberStatus())){// 会员还车充电，免单
						chargingOrderInfo.setFreeReason("会员还车充电");
					}
				}else if("employee".equals(chargingOrderInfo.getUserType())){
					chargingOrderInfo.setFreeReason("员工端充电");
				}
			}
		}
		Pager<ChargingOrderInfo> pager = new Pager<ChargingOrderInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingOrderInfoMapper.getAllNum(paramMap));
		return pager;
	}
	
	/**
	 * 城市-充电站列表
	 * @return
	 * @author ouxx
	 * @date 2016-11-21 上午11:58:49
	 */
	public List<ChargingReportVo> getCityParkList(){
		return chargingOrderInfoMapper.getCityParkList();
	}
	
	/**
	 * 生成充电运营报表
	 * @param cityCode
	 * @param begin
	 * @param end
	 * @return
	 * @author ouxx
	 * @date 2016-11-19 下午1:36:15
	 */
	public List<ChargingReportVo> getChargingReport(final Integer cooperationType,final Integer parkId, final String cityCode,
			final Timestamp begin, final Timestamp end, final Integer from, final Integer to){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("begin", begin);
				put("end", end);
				put("from", from);
				put("to", to);
			}
		};
		return chargingOrderInfoMapper.getChargingReport(paramMap);
	}
	
	public Integer getReportCount(final Integer cooperationType,final Integer parkId, final String cityCode,
			final Timestamp begin, final Timestamp end, final Integer from, final Integer to){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("begin", begin);
				put("end", end);
			}
		};
		Integer count = chargingOrderInfoMapper.getReportCount(paramMap);
		return (null != count) ? count : 0;
	}
	
	/**
	 * 充电桩充电明细表 【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【使用者】【使用者身份证】【手机号码】【充电电量】【充电时长】【计费金额（元）】、【消费金额（元）】，两个小项：【支付金额（元）】、【优惠券（元）】
	 * @param cooperationType
	 * @param parkId
	 * @param cityCode
	 * @param begin
	 * @param end
	 * @param from
	 * @param to
	 * @return
	 * @author ouxx
	 * @date 2016-11-24 上午11:19:35
	 */
	public List<ChargingDetailVo> getChargingDetailReport(final Integer equipmentId, final Integer cooperationType,final Integer parkId, final String cityCode,
			final Timestamp begin, final Timestamp end, final Integer from, final Integer to){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("equipmentId", equipmentId);
				put("begin", begin);
				put("end", end);
				put("from", from);
				put("to", to);
			}
		};
		return chargingOrderInfoMapper.getChargingDetailReport(paramMap);
	}
	
	public Integer getChargingDetailReportCount(final Integer equipmentId, final Integer cooperationType,final Integer parkId, final String cityCode,
			final Timestamp begin, final Timestamp end){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("equipmentId", equipmentId);
				put("begin", begin);
				put("end", end);
			}
		};
		Integer count = chargingOrderInfoMapper.getChargingDetailReportCount(paramMap);
		return (null != count) ? count : 0;
	}
	
	/**
	 * 充电桩收入报表  【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【充电电量】【价格】（两个小项：【物业电价】、【会员电价】）【计费金额（元）】【充电收入】
	 * @param equipmentId
	 * @param cooperationType
	 * @param parkId
	 * @param cityCode
	 * @param begin
	 * @param end
	 * @param from
	 * @param to
	 * @return
	 * @author ouxx
	 * @date 2016-11-24 下午2:44:24
	 */
	public List<ChargingIncomeVo> getIncomeDetailReport(final Integer equipmentId, final Integer cooperationType,final Integer parkId, final String cityCode,
			final Timestamp begin, final Timestamp end,final Timestamp payBeginTime,final Timestamp payEndTime, final Integer from, final Integer to, final String invoiceStatus){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("equipmentId", equipmentId);
				put("begin", begin);
				put("end", end);
				put("from", from);
				put("to", to);
				put("invoiceStatus", invoiceStatus);
                put("payBeginTime", payBeginTime);
                put("payEndTime",payEndTime);

			}
		};
		return chargingOrderInfoMapper.getIncomeDetailReport(paramMap);
	}
	
	public Integer getIncomeDetailReportCount(final Integer equipmentId, final Integer cooperationType,final Integer parkId, final String cityCode,
			final Timestamp begin, final Timestamp end,final Timestamp payBeginTime,final Timestamp payEndTime, final String invoiceStatus){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("equipmentId", equipmentId);
				put("begin", begin);
				put("end", end);
				put("invoiceStatus", invoiceStatus);
                put("payBeginTime", payBeginTime);
                put("payEndTime",payEndTime);
			}
		};
		Integer count = chargingOrderInfoMapper.getIncomeDetailReportCount(paramMap);
		return (null != count) ? count : 0;
	}

	/**
	 * 用电量报表
	 * 按城市、网点（模糊查询）、类型、桩类型、日期区间查询
	 * @param cooperationType
	 * @param equipmentType
	 * @param cityCode
	 * @param stationName
	 * @param begin
	 * @param end
	 * @param from
	 * @param to
	 * @return
	 */
	public List<ChargingConsumptionVo> getChargingConsumptionReport (final Integer cooperationType,
			final Integer equipmentType, final String cityCode,
			final String stationName, final Timestamp begin, final Timestamp end, final Integer from, final Integer to){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("cityCode", cityCode);
				put("equipmentType", equipmentType);
				put("stationName", stationName);
				put("cooperationType", cooperationType);
				put("begin", begin);
				put("end", end);
				put("from", from);
				put("to", to);
			}
		};
		return chargingOrderInfoMapper.getChargingConsumptionReport(paramMap);
	}
	
	public Integer getChargingConsumptionReportCount(final Integer cooperationType,
			final Integer equipmentType, final String cityCode,
			final String stationName, final Timestamp begin, final Timestamp end, final Integer from, final Integer to){
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("cityCode", cityCode);
				put("equipmentType", equipmentType);
				put("stationName", stationName);
				put("cooperationType", cooperationType);
				put("begin", begin);
				put("end", end);
				put("from", from);
				put("to", to);
			}
		};
		Integer count = chargingOrderInfoMapper.getChargingConsumptionReportCount(paramMap);
		return (null != count) ? count : 0;
	}
	
	/**
	 * 获取一个充电桩的充电明细表 
	 * @param equipmentId
	 * @return
	 * @author ouxx
	 * @date 2016-11-19 下午5:51:05
	 */
	public List<ChargingDetailVo> getOnePileChargingDetail(Integer equipmentId, final Integer from, final Integer to){
		return chargingOrderInfoMapper.getOnePileChargingDetail(equipmentId, from, to);
	}
	
	public Integer getOnePileChargingDetailCount(Integer equipmentId){
		Integer count = chargingOrderInfoMapper.getOnePileChargingDetailCount(equipmentId);
		return (null != count) ? count : 0;
	}

	public ChargingOrderInfo selectByChargeSeq(String objId) {
		return chargingOrderInfoMapper.selectByChargeSeq(objId);
	}

	public ChargingOrderInfo selectByMemberIdAndStatus(Integer connectorId) {
		return chargingOrderInfoMapper.selectByMemberIdAndStatus(connectorId);
	}

	public ChargingOrderInfo selectByMemberIdAndChargingStatus(Integer id) {
		return chargingOrderInfoMapper.selectByMemberIdAndChargingStatus(id);
	}

	public ChargingOrderInfo selectByConnectorId(Integer connectorId) {
		return chargingOrderInfoMapper.selectByConnectorId(connectorId);
	}

	public List<ChargingOrderInfo> selectEmployeeChargingOrderByConnectorId(
			Integer connectorId) {
		return chargingOrderInfoMapper.selectEmployeeChargingOrderByConnectorId(connectorId);
	}

	public ChargingOrderInfo selectUserByConnectorId(Map<String, Object> map) {
		return chargingOrderInfoMapper.selectUserByConnectorId(map);
	}

	public ChargingOrderInfo selectReturnCarCharging(Map<String, Object> map) {
		return chargingOrderInfoMapper.selectReturnCarCharging(map);
	}
	
	
	/**
	 * 充电财务报表
	 * @param cityCode
	 * @param begin
	 * @param end
	 * @return
	 * @author ouxx
	 * @date 2017-4-11 下午7:15:08
	 */
	public Pager<ChargingFinanceReportVo> queryChargingFinanceReport(String cityCode,String payStatus
			, Timestamp begin, Timestamp end, Timestamp payBeginTime,Timestamp payEndTime,Integer from, Integer to, String invoiceStatus){
		List<ChargingFinanceReportVo> list = chargingOrderInfoMapper.queryChargingFinanceReport(cityCode,payStatus, begin, end, payBeginTime,payEndTime,from, to, invoiceStatus);
		Pager<ChargingFinanceReportVo> pager = new Pager<ChargingFinanceReportVo>();
		if(null != list && list.size() > 0){
			int cnt = chargingOrderInfoMapper.queryChargingFinanceReportRecords(cityCode, payStatus,begin, end,payBeginTime,payEndTime, invoiceStatus);
			pager.setDatas(list);
			pager.setTotalCount(cnt);
		}
		return pager;
	}

    public Pager<ChargingFinanceReportVo> queryChargingFinanceReport(Map<String,Object> map){
        List<ChargingFinanceReportVo> list = chargingOrderInfoMapper.queryChargingFinanceReport(map);
        Pager<ChargingFinanceReportVo> pager = new Pager<ChargingFinanceReportVo>();
        pager.setTotalCount(chargingOrderInfoMapper.queryChargingFinanceReportRecords(map));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

        map.put("searchType",1);
        map.put("begin", yearStart);
        map.put("end", yearEnd);
        map.put("payBeginTime", yearStart);
        map.put("payEndTime", yearEnd);
        //本年合计
        ChargingFinanceReportVo thisYear = chargingOrderInfoMapper.queryChargingSum(map);

        map.put("searchType",2);
        map.remove("begin");
        map.remove("end");
        map.remove("payBeginTime");
        map.remove("payEndTime");
        ChargingFinanceReportVo total  = chargingOrderInfoMapper.queryChargingSum(map);
        list.add(thisYear);
        list.add(total);
        pager.setDatas(list);
        return pager;

    }

    public List<ChargingFinanceReportVo> getChargeSum(Map<String, Object> map) {
        List<ChargingFinanceReportVo> list = new ArrayList<ChargingFinanceReportVo>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

        map.put("searchType",1);
        map.put("begin", yearStart);
        map.put("end", yearEnd);
        map.put("payBeginTime", yearStart);
        map.put("payEndTime", yearEnd);
        //本年合计
        ChargingFinanceReportVo thisYear = chargingOrderInfoMapper.queryChargingSum(map);

        map.put("searchType",2);
        map.remove("begin");
        map.remove("end");
        map.remove("payBeginTime");
        map.remove("payEndTime");
        ChargingFinanceReportVo total  = chargingOrderInfoMapper.queryChargingSum(map);
        list.add(thisYear);
        list.add(total);
        return list;
    }




    public List<ChargingFinanceReportVo> queryChargingFinanceReportList(String cityCode,String payStatus
			, Timestamp begin, Timestamp end, Timestamp payBeginTime,Timestamp payEndTime,String invoiceStatus){
		return chargingOrderInfoMapper.queryChargingFinanceReport(cityCode, payStatus,begin, end, payBeginTime,payEndTime,null, null, invoiceStatus);
	}

    public List<ChargingFinanceReportVo> queryChargingFinanceReportList(Map<String ,Object> map){
        return chargingOrderInfoMapper.queryChargingFinanceReport(map);
    }
	
	/**
	 * 合计:本年合计、总合计
	 * @param cityCode
	 * @return
	 * @author ouxx
	 * @param yearEnd 
	 * @param yearStart 
	 * @date 2017-4-11 下午8:45:01
	 */
	public ChargingFinanceReportVo queryChargingSum(String cityCode,String payStatus,String payBeginTime ,String payEndTime, String invoiceStatus, String yearStart, String yearEnd){
		ChargingFinanceReportVo vo = new ChargingFinanceReportVo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String begin = null ;
		String end = null ;
		String beginTime = null;
		String endTime = null;
		if (StringUtils.isNotBlank(yearStart)) begin = sdf.format(DateTime.getStartTimeOfYear(DateTime.getDate(yearStart)));
		if (StringUtils.isNotBlank(yearEnd)) end = sdf.format(DateTime.getStartTimeOfYear(DateTime.getDate(yearEnd)));
		if (StringUtils.isNotBlank(payBeginTime)) beginTime = sdf.format(DateTime.getStartTimeOfYear(DateTime.getDate(payBeginTime)));
		if (StringUtils.isNotBlank(payEndTime)) endTime = sdf.format(DateTime.getStartTimeOfYear(DateTime.getDate(payEndTime)));

		ChargingFinanceReportVo thisYearSum = chargingOrderInfoMapper.queryChargingThisYearSum(cityCode,invoiceStatus,begin,end);
		
		ChargingFinanceReportVo totalSum = chargingOrderInfoMapper.queryChargingTotalSum(cityCode,invoiceStatus,begin,end);
		//本年合计
		if(null != thisYearSum){
			vo.setChargingAmountThisYearSum(thisYearSum.getChargingAmount());
			vo.setPayMoneyThisYearSum(thisYearSum.getPayMoney());
			vo.setInvoiceMoneyYearSum(thisYearSum.getInvoiceMoney());
		}else{
			vo.setChargingAmountThisYearSum(0.0);
			vo.setPayMoneyThisYearSum(0.0);
			vo.setInvoiceMoneyYearSum(0.0);
		}
		//总合计
		if(null != totalSum){
			vo.setChargingAmountSum(totalSum.getChargingAmount());
			vo.setPayMoneySum(totalSum.getPayMoney());
			vo.setInvoiceMoneySum(totalSum.getInvoiceMoney());
		}else{
			vo.setChargingAmountSum(0.0);
			vo.setPayMoneySum(0.0);
			vo.setInvoiceMoneySum(0.0);
		}
		
		return vo;
		
	}

	public Integer getChargingTotolCount(Timestamp beginDate, Timestamp endDate) {
		return chargingOrderInfoMapper.getChargingTotolCount(beginDate,endDate);
	}

	public List<ChargingReportVo> getAllChargingReport(final Integer cooperationType,
			final Integer parkId, final String cityCode, final Timestamp begin,
			final Timestamp end) {
		Map<String, Object> paramMap = new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("parkId", parkId);
				put("cityCode", cityCode);
				put("cooperationType", cooperationType);
				put("begin", begin);
				put("end", end);
			}
		};
		return chargingOrderInfoMapper.getAllChargingReport(paramMap);
	}
    public	ChargingOrderInfo queryCooperationChargingOrder(Integer connId){
    	return chargingOrderInfoMapper.queryCooperationChargingOrder(connId);
    }

	public int updateInvoiceStatus(String id, int invoiceStatus) {
		return chargingOrderInfoMapper.updateInvoiceStatus(id,invoiceStatus);
	}

	public int bitchUpdateInvoiceStatus(List<String> list, Integer invocieStatus) {
		return chargingOrderInfoMapper.bitchUpdateInvoiceStatus(list, invocieStatus);
	}
}
