package com.iber.portal.dao.patch;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.vo.timeShare.TimeShareOrderDiscountRecord;
import com.iber.portal.vo.timeShare.ZOTYEReturnCashOrderVo;

public interface PatchesMapper {

	int insertTimeShareOrderDiscountRecord(List<TimeShareOrderDiscountRecord> lists);
	int updateDiscountRecordsBatch(List<String> lists);
	int batchDeleteZeroMoneyOrder(List<String> lists);
	List<TimeShareOrder> getTimeShareOrderInfoByCarType(Map<String,Object> map);
	List<ZOTYEReturnCashOrderVo> getZOTYEFinishPaidOrderInfos(Map<String,Object> map);

}