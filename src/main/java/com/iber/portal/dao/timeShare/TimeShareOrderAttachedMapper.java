package com.iber.portal.dao.timeShare;

import com.iber.portal.model.timeShare.TimeShareOrderAttached;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TimeShareOrderAttachedMapper {
    /**
     * 保存附属订单
     * @param timeShareOrderAttached
     */
    Integer saveTimeShareOrderAttached( TimeShareOrderAttached timeShareOrderAttached);

    List<TimeShareOrderAttached> findAll();

    List<HashMap> getPage(Map<String, Object> map);

    int getAllNum(Map<String, Object> map);

    Integer findOrder(String order);

    List<Map> findLongTimeUnpay();

    /**
     * 根据附属订单id查找附属订单收费项目list
     * @param id
     * @return
     */
    List<HashMap> queryAttachedOrderDetail(String id);

    /**
     * 查询附属订单收入报表
     * @param map
     * @return
     */
    List<HashMap> getPageReport(Map<String, Object> map);

    /**
     * 附属订单收入报表totalcount
     * @param map
     * @return
     */
    int getAllNumReport(Map<String, Object> map);
    /**
     * 附属订单收入报表未分页结果
     * @param map
     * @return
     */
    List<Map<String, Object>> getPageReportAll(Map<String, Object> map);

    /**
     * 统计附属订单收入报表总计
     * @param map
     * @return
     */
    HashMap countTotalToReport(Map<String, Object> map);

    /**
     * 统计附属订单收入报表中误工费item对应的总计
     * @param map
     * @return
     */
    Double countlostIncomeTotalToReport(Map<String, Object> map);
    Double countRescueTotalToReport(Map<String, Object> map);
    Double countMaintainTotalToReport(Map<String, Object> map);
    Double countIllegalTotalToReport(Map<String, Object> map);
    Double countInsuranceTotalToReport(Map<String, Object> map);

    /**
     * 通过orderid统计日租条数
     * @param orderId
     * @return
     */
    Integer findLongRentOrder(String orderId);
}
