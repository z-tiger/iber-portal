package com.iber.portal.dao.car;

import java.util.List;

import com.iber.portal.model.car.CarChargingRemind;

public interface CarChargingRemindMapper {
	
	List<CarChargingRemind> selectAll();

	int batchInsert(List<CarChargingRemind> newReminds);
}
