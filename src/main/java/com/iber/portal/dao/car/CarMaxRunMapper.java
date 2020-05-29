package com.iber.portal.dao.car;

import com.iber.portal.model.car.CarMaxRun;

public interface CarMaxRunMapper {

	public CarMaxRun selectAllRecords();

	public void deleteRecord();

	public void insertRecord(CarMaxRun carMaxRun);
}
