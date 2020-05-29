package com.iber.portal.dao.longRent;

import com.iber.portal.model.longRent.LongRentOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LongRentOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LongRentOrder record);

    int insertSelective(LongRentOrder record);

    LongRentOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LongRentOrder record);

    int updateByPrimaryKey(LongRentOrder record);

    List<LongRentOrder> getAll(Map<String, Object> paramMap);

    int getAllNum(Map<String, Object> paramMap);

    int bitchUpdateInvoiceStatus(@Param("list") List<String> list, @Param("invocieStatus") Integer invocieStatus);


}