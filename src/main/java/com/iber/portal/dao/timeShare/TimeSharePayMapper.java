package com.iber.portal.dao.timeShare;

import com.iber.portal.model.timeShare.TimeShareCancel;
import com.iber.portal.model.timeShare.TimeSharePay;
import com.iber.portal.vo.report.CarIncomeReport;
import com.iber.portal.vo.report.TimeShareIncomeReport;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TimeSharePayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeSharePay record);

    int insertSelective(TimeSharePay record);

    TimeSharePay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeSharePay record);

    int updateByPrimaryKey(TimeSharePay record);
    
    int selectAllCarIncomeReportRecords(Map<String, Object> map);
    
    List<CarIncomeReport> queryCarIncomeReport(Map<String, Object> map);
    
    int selectTimeShareReportRecords(Map<String, Object> map);
    
    List<TimeShareIncomeReport> queryTimeShareReport(Map<String, Object> map);
    
    /**根据订单时间查询车牌号码*/
    List<TimeSharePay> selectLpnByOrderTime(@Param("st") String st, @Param("et") String et);
    
    /**根据订单时间，车牌号码查询订单信息*/
    List<TimeSharePay> selectByOrderTimeAndLpn(@Param("date") String date,  @Param("lpn") String lpn);
    
    /**
     * 根据订单号查询，并且订单完成时间要在lastCountTime之后
     * @param orderId
     * @return
     * @author ouxx
     * @date 2016-11-30 下午1:49:16
     */
    TimeSharePay getByOrderId(@Param("orderId") String orderId, 
    		@Param("lastCountTime") Date lastCountTime);
    /**
     * 根据订单号查询
     * @param objId
     * @return
     */
	TimeSharePay selectByOrderId(String objId);
	
	/**
	 * 查会员从startDate以来的用车情况
	 * @param memberId
	 * @param startDate
	 * @return
	 * @author ouxx
	 * @date 2017-4-21 下午6:04:16
	 */
	List<TimeSharePay> queryOrdListFromDate(@Param("memberId") Integer memberId, @Param("startDate") Date startDate);
	
	/**
	 * 查从startDate以来的用车次数大于val的会员ID列表
	 * @param startDate
	 * @param val
	 * @return
	 * @author ouxx
	 * @date 2017-4-22 下午1:43:36
	 */
  	List<Integer> queryMemberIdListOrdCntGreaterThenValFromDate(
  			@Param("startDate") Date startDate, @Param("val") Integer val);
  	
  	/**
  	 * 查从latestNoWzOrdEndTime或startDate以来的用车次数大于val的会员ID列表
  	 * @param startDate
  	 * @param val
  	 * @param excludeMemberIdList
  	 * @return
  	 * @author ouxx
  	 * @date 2017-5-8 上午9:32:37
  	 */
  	List<TimeShareCancel> queryMemberList(
  			@Param("startDate") Date startDate, @Param("val") Integer val, 
  			@Param("list") List<Integer> excludeMemberIdList);

	int updateInvoiceStatus(@Param("id")String id, @Param("invoiceStatus")Integer invoiceStatus);
	
	int bitchUpdateInvoiceStatus(@Param("list") List<String> list, @Param("invoiceStatus")Integer invoiceStatus);

    List<TimeSharePay> test();
}