package com.iber.portal.dao.car;

import java.util.Map;

import com.iber.portal.model.car.CarPreOffline;

public interface CarPreOfflineMapper {

	int updatePreOfflineRecordByLpn(Map<String, Object> map);

	int updateByPrimaryKeySelective(CarPreOffline offline);

	CarPreOffline selectPreOfflineRecordByLpn(Map<String, Object> map);

	int insertSelective(CarPreOffline offline);

}
