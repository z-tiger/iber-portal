package com.iber.portal.dao.dispatcher;

import java.util.List;

import com.iber.portal.model.dispatcher.ElectronicGridGps;

public interface ElectronicGridGpsMapper {
	
	int insert(ElectronicGridGps record);
	
	int deleteByGridId(Integer gridId);

	List<ElectronicGridGps> selectElectronicGridGpsByGridId(Integer id);
}
