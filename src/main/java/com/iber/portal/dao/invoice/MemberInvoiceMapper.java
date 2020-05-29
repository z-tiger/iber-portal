package com.iber.portal.dao.invoice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.invoice.MemberInvoice;
import com.iber.portal.vo.invoice.InvoiceOrderInfoVo;
import com.iber.portal.vo.invoice.MemberInvoiceVO;


public interface MemberInvoiceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberInvoice record);

    int insertSelective(MemberInvoice record);

    MemberInvoice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberInvoice record);

    int updateByPrimaryKey(MemberInvoice record);

	List<MemberInvoiceVO> queryPageList(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	int updateStatusById(@Param("id")Integer id, @Param("status")Integer status);

	MemberInvoiceVO selectVOById(@Param("id")Integer id);

	Double getOrderMoney(List<String> olist);

	Double getCharingMoney(List<String> clist);
	
	Double getLrMoney(List<String> rlist);

	List<InvoiceOrderInfoVo> getOrderInfoList(Map<String, Object> paramMap);

	int getAllOrderInfoNum(Map<String, Object> paramMap);
}