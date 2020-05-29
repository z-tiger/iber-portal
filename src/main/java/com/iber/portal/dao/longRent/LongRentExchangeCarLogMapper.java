package com.iber.portal.dao.longRent;

import com.iber.portal.model.longRent.LongRentExchangeCarLog;

import java.util.List;

/**
 * @author zengfeiyue
 */
public interface LongRentExchangeCarLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table x_long_rent_exchange_car_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table x_long_rent_exchange_car_log
     *
     * @mbggenerated
     */
    int insert(LongRentExchangeCarLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table x_long_rent_exchange_car_log
     *
     * @mbggenerated
     */
    int insertSelective(LongRentExchangeCarLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table x_long_rent_exchange_car_log
     *
     * @mbggenerated
     */
    LongRentExchangeCarLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table x_long_rent_exchange_car_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LongRentExchangeCarLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table x_long_rent_exchange_car_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LongRentExchangeCarLog record);

    /**
     * 根据orderid查询最新一次的换车记录
     * @param orderId
     * @return
     */
    LongRentExchangeCarLog selectLatestByOrderId(String orderId);

    /**
     * 根据订单号查找所有换车记录
     * @param orderId
     * @return
     */
    List<LongRentExchangeCarLog> queryAllHistory(String orderId);
}