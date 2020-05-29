package com.iber.portal.service.coupon;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.coupon.CouponMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.vo.report.CouponReport;

@Service
public class CouponService {

	@Autowired
	private CouponMapper couponMapper;
	
	public int insertSelective(Coupon model) throws ServiceException {
		int len;
		try {
			len = couponMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = couponMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(Coupon model) throws ServiceException {
		int len;
		try {
			len = couponMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public Coupon selectByPrimaryKey(int id) {
		return couponMapper.selectByPrimaryKey(id);
	}
	
	public Pager<Coupon> getAll(Map<String, Object> paramMap) {
		List<Coupon> listObj = couponMapper.getAll(paramMap);
		Pager<Coupon> pager = new Pager<Coupon>();
		pager.setDatas(listObj);
		pager.setTotalCount(couponMapper.getAllNum(paramMap));
		return pager;
	}
	
	public Pager<Coupon> getAllUserId(Map<String, Object> paramMap) {
		List<Coupon> listObj = couponMapper.getAllUserId(paramMap);
		Pager<Coupon> pager = new Pager<Coupon>();
		pager.setDatas(listObj);
		pager.setTotalCount(couponMapper.getAllNumUserId(paramMap));
		return pager;
	}
	
	/**
	 * 在[begin , end]之间的优惠券
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-10-9 下午3:36:49
	 */
	public Pager<Coupon> getAllInTime(Map<String, Object> paramMap) {
		List<Coupon> listObj = couponMapper.getAllInTime(paramMap);
		Pager<Coupon> pager = new Pager<Coupon>();
		pager.setDatas(listObj);
		pager.setTotalCount(couponMapper.getAllNumInTime(paramMap));
		return pager;
	}
	
	/**
	 * 查批次号为batchNo的共有多少张未领取的且未失效的优惠券
	 * @param batchNo
	 * @return
	 * @author ouxx
	 * @date 2016-9-28 下午5:43:44
	 */
	public int getUncollectedCountByBatchNo(String batchNo){
		return couponMapper.getUncollectedCountByBatchNo(batchNo);
	}
	
	/**
	 * 查批次号为batchNo的未领取的且未失效的优惠券
	 * @param batchNo
	 * @return
	 * @author ouxx
	 * @date 2016-9-28 下午6:29:58
	 */
	public List<Coupon> getUncollectedByBatchNo(String batchNo){
		return couponMapper.getUncollectedByBatchNo(batchNo);
	}
	
	//-------------------------- 优惠券报表 -------------------------
	/**
	 * 根据title来分组
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-9-30 下午1:37:11
	 */
	public List<CouponReport> getGroupByTitle(Map<String, Object> paramMap) {
		return couponMapper.getGroupByTitle(paramMap);
	}
	
	/**
	 * 获取报表统计条数
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-10-8 下午2:23:49
	 */
	public Integer getReportCount(Map<String, Object> paramMap) {
		return couponMapper.getReportCount(paramMap);
	}
	
	/**优惠券是否作废*/
	public int updateStatus(Coupon record){
		return couponMapper.updateById(record);
	}
	
	/**查询会员是否领取了优惠券*/
	public int findById(Map<String, Object> paramMap){
		return  couponMapper.findById(paramMap);
	}

	public int insertBatch(List<Coupon> list){
		return couponMapper.insertBatch(list);
	}
	/**
	 * 根据memberId和title查已发放的优惠券数据
	 * @param memberId
	 * @param title
	 * @return
	 * @author ouxx
	 * @date 2017-1-9 上午9:35:02
	 */
	public int getCntByTitleAndMemberId(Integer memberId,
			String title){
		return couponMapper.getCntByTitleAndMemberId(memberId, title);
	}

	public List<Coupon> getAllList() {
		return couponMapper.getAllList();
	}
	
	public int insertFourFiveStartBatch(List<Coupon> list){
		return couponMapper.insertFourFiveStartBatch(list);
	}

	public Coupon selectCouponByCouponNo(String couponNo) {
		return couponMapper.selectByCouponNo(couponNo);
	}
}
