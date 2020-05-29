package com.iber.portal.dao.charging;

import com.iber.portal.model.charging.ChargingOrderInfo;
import com.iber.portal.vo.charging.*;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;



public interface ChargingOrderInfoMapper {
	
	int insert(ChargingOrderInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(ChargingOrderInfo record);
	
	ChargingOrderInfo selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<ChargingOrderInfo> queryPageList(Map<String, Object> paramMap);
	
	
	/**
	 * 城市-充电站列表
	 * @return
	 * @author ouxx
	 * @date 2016-11-17 下午1:59:59
	 */
	List<ChargingReportVo> getCityParkList();
	
	/**
	 * 生成充电运营报表 
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-11-17 下午1:59:59
	 */
	List<ChargingReportVo> getChargingReport(Map<String, Object> paramMap);

	Integer getReportCount(Map<String, Object> paramMap);
	
	/**
	 * 充电桩充电明细表 【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【使用者】【使用者身份证】【手机号码】【充电电量】【充电时长】【计费金额（元）】、【消费金额（元）】，两个小项：【支付金额（元）】、【优惠券（元）】
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-11-24 上午11:16:05
	 */
	List<ChargingDetailVo> getChargingDetailReport(Map<String, Object> paramMap);
	
	Integer getChargingDetailReportCount(Map<String, Object> paramMap);
	
	/**
	 * 充电桩收入报表  【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【充电电量】【价格】（两个小项：【物业电价】、【会员电价】）【计费金额（元）】【充电收入】
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-11-24 下午2:47:17
	 */
	List<ChargingIncomeVo> getIncomeDetailReport(Map<String, Object> paramMap);
	
	Integer getIncomeDetailReportCount(Map<String, Object> paramMap);
	
	/**
	 * 用电量报表 
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-11-24 下午7:58:31
	 */
	List<ChargingConsumptionVo> getChargingConsumptionReport(Map<String, Object> paramMap);
	
	Integer getChargingConsumptionReportCount(Map<String, Object> paramMap);

	/**
	 * 获取一个充电桩的充电明细表 
	 * @param equipmentId
	 * @return
	 * @author ouxx
	 * @date 2016-11-19 下午5:47:34
	 */
	List<ChargingDetailVo> getOnePileChargingDetail(@Param("equipmentId")Integer equipmentId, 
			@Param("from")Integer from, @Param("to")Integer to);
	
	Integer getOnePileChargingDetailCount(@Param("equipmentId")Integer equipmentId);
	/**
	 * 根据充电的序列号查询
	 * @param objId
	 * @return
	 */
	ChargingOrderInfo selectByChargeSeq(String objId);
	/**
	 * 根据用户id查询用户正在预约的订单
	 * @param connectorId
	 * @return
	 */
	ChargingOrderInfo selectByMemberIdAndStatus(Integer connectorId);

	ChargingOrderInfo selectByMemberIdAndChargingStatus(Integer connectorId);
	/**
	 * 根据枪id查询正在被预约的订单
	 * @param connectorId
	 * @return
	 */
	ChargingOrderInfo selectByConnectorId(Integer connectorId);
	/**
	 * 查询员工正在充电中的订单
	 * @param connectorId
	 * @return
	 */
	List<ChargingOrderInfo> selectEmployeeChargingOrderByConnectorId(
			Integer connectorId);
	/**
	 * 查询充电桩使用人和使用时间
	 * @param map
	 * @return
	 */
	ChargingOrderInfo selectUserByConnectorId(Map<String, Object> map);
	/**
	 * 查询还车扫码充电的会员
	 * @param map
	 * @return
	 */
	ChargingOrderInfo selectReturnCarCharging(Map<String, Object> map);
	
	/**
	 * 充电财务报表
	 * @return
	 * @author ouxx
	 * @date 2017-4-11 下午7:08:33
	 */
	List<ChargingFinanceReportVo> queryChargingFinanceReport(@Param("cityCode") String cityCode
            ,@Param("payStatus") String payStatus
			, @Param("begin") Timestamp begin, @Param("end") Timestamp end
            ,@Param("payBeginTime") Timestamp payBeginTime,@Param("payEndTime") Timestamp payEndTime
			, @Param("from") Integer from, @Param("to") Integer to, @Param("invoiceStatus")String invoiceStatus);

    List<ChargingFinanceReportVo> queryChargingFinanceReport(Map<String, Object> map);

    int queryChargingFinanceReportRecords(Map<String, Object> map);


    /**
	 * 查询充电财务报表记录数
	 * @return
	 * @author ouxx
	 * @date 2017-4-11 下午7:08:33
	 */
	int queryChargingFinanceReportRecords(@Param("cityCode") String cityCode,@Param("payStatus") String payStatus
			, @Param("begin") Timestamp begin, @Param("end") Timestamp end
            ,@Param("payBeginTime") Timestamp payBeginTime,@Param("payEndTime") Timestamp payEndTime
            ,@Param("invoiceStatus")String invoiceStatus);
	
	/**
	 * 本年合计
	 * @param cityCode
	 * @return
	 * @author ouxx
	 * @param yearEnd 
	 * @param yearStart 
	 * @date 2017-4-11 下午8:35:15
	 */
	ChargingFinanceReportVo queryChargingThisYearSum(@Param("cityCode") String cityCode, @Param("invoiceStatus")String invoiceStatus,@Param("yearStart") String yearStart,@Param("yearEnd") String yearEnd);

    /**
     * 充电财务报表本年合计和总合计
     * @param map
     * @return
     */
    ChargingFinanceReportVo queryChargingSum(Map<String, Object> map);



    /**
	 * 总合计
	 * @param cityCode
	 * @return
	 * @author ouxx
	 * @param yearEnd 
	 * @param yearStart 
	 * @date 2017-4-11 下午8:35:15
	 */
	ChargingFinanceReportVo queryChargingTotalSum(@Param("cityCode") String cityCode, @Param("invoiceStatus")String invoiceStatus, @Param("yearStart") String yearStart,@Param("yearEnd") String yearEnd);

    /**
	 * 查询充电桩的使用次数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Integer getChargingTotolCount(@Param("begin")Timestamp beginDate,  @Param("end")Timestamp endDate);

	List<ChargingReportVo> getAllChargingReport(Map<String, Object> paramMap);
	ChargingOrderInfo queryCooperationChargingOrder(Integer connId);

	int updateInvoiceStatus(@Param("id")String id, @Param("invoiceStatus")int invoiceStatus);

	int bitchUpdateInvoiceStatus(@Param("list") List<String> list, @Param("invocieStatus") Integer invocieStatus);

	/**
	 * 查询非宜步车的充电信息列表，用于控制结束充电
	 * @return
	 * @author ouxx
	 * @date 2017-3-20 下午2:34:54
	 */
	List<ChargingOrderInfo> queryChargingNotIberCarOrderList(
			@Param("updateBatteryCacheInterval") Integer updateBatteryCacheInterval,
			@Param("chargingStartedMinutes") Integer chargingStartedMinutes);

	int updateNormalOrder(@Param("chargingStartedMinutes") Integer chargingStartedMinutes);

	int updateStatusWhenStealing(ChargingOrderInfo record);
}
