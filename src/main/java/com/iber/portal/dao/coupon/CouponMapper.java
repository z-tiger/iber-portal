package com.iber.portal.dao.coupon;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.vo.report.CouponReport;

public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

	List<Coupon> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
	//根据会员id 查询对应数据
	List<Coupon> getAllUserId(Map<String, Object> paramMap);

	int getAllNumUserId(Map<String, Object> paramMap);
	
	/**
	 * 在[begin , end]之间的优惠券
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-10-9 下午3:35:51
	 */
	List<Coupon> getAllInTime(Map<String, Object> paramMap);
	/**
	 * 在[begin , end]之间的优惠券数量
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-10-9 下午3:36:10
	 */
	int getAllNumInTime(Map<String, Object> paramMap);

	List<Coupon> selectCanUsing(Map<String, Object> couponParamMap);
	
	/**
	 * 查批次号为batchNo的共有多少张未领取的优惠券
	 * @param batchNo
	 * @return
	 * @author ouxx
	 * @date 2016-9-28 下午5:42:20
	 */
	int getUncollectedCountByBatchNo(@Param("batchNo")String batchNo);
	
	/**
	 * 查批次号为batchNo的未领取的优惠券
	 * @param batchNo
	 * @return
	 * @author ouxx
	 * @date 2016-9-28 下午6:27:24
	 */
	List<Coupon> getUncollectedByBatchNo(@Param("batchNo")String batchNo);

	
	/////////////////优惠券报表
	/**
	 * 根据title分组
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-9-30 下午12:05:49
	 */
	List<CouponReport> getGroupByTitle(Map<String, Object> paramMap);

	/**
	 * 获取报表统计条数
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-10-8 下午2:23:31
	 */
	Integer getReportCount(Map<String, Object> paramMap);
	
	/**更新优惠券是否作废*/
	int updateById(Coupon record);
	
	/**查询优惠券是否被领用*/
	int findById(Map<String, Object> paramMap);
	
	
	int insertBatch(List<Coupon> list);
	
	/**
	 * 根据memberId和title查已发放的优惠券数据
	 * @param memberId
	 * @param title
	 * @return
	 * @author ouxx
	 * @date 2017-1-9 上午9:35:45
	 */
	int getCntByTitleAndMemberId(@Param("memberId") Integer memberId,
			@Param("title") String title);

	List<Coupon> getAllList();
	
	int insertFourFiveStartBatch(List<Coupon> list);

	int selectRecordByItemCodeAndMemberId(@Param("itemCode")String itemCode, @Param("memberId")Integer memberId);

	int selectRecordsBymemberIdAndstrategyId(@Param("memberId") Integer memberId,@Param("strategyId") Integer strategyId);

	Coupon selectByCouponNo(@Param("couponNo")String couponNo);
	
}