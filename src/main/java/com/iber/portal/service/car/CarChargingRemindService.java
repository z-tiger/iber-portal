package com.iber.portal.service.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarChargingRemindMapper;
import com.iber.portal.model.car.CarChargingRemind;

@Service
public class CarChargingRemindService {
	
	@Autowired
	private CarChargingRemindMapper dao;
	public List<CarChargingRemind> selectAll(){
		return dao.selectAll();
	}
	public int batchInsert(List<CarChargingRemind> newReminds) {
		return dao.batchInsert(newReminds);
	}
}
