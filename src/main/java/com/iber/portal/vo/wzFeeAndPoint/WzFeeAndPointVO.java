package com.iber.portal.vo.wzFeeAndPoint;

import java.math.BigDecimal;
import java.util.List;

import com.iber.portal.model.base.MemberRefundWorderUsecar;
import com.iber.portal.model.base.WZQuery;

/**
 * 违章订单VO
 * @author Administrator
 *
 */
@SuppressWarnings("unused")
public class WzFeeAndPointVO {
	
	private BigDecimal wzFee;
	private int wzPoint;
	public BigDecimal getWzFee() {
		return wzFee;
	}
	public void setWzFee(BigDecimal wzFee) {
		this.wzFee = wzFee;
	}
	public int getWzPoint() {
		return wzPoint;
	}
	public void setWzPoint(int wzPoint) {
		this.wzPoint = wzPoint;
	}
	
	
	
	
}
