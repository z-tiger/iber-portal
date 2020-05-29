package com.iber.portal.service.dispatcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.dispatcher.ElectronicGridGpsMapper;
import com.iber.portal.model.dispatcher.ElectronicGridGps;

@Service
public class ElectronicGridGpsService {
	
	@Autowired
	private ElectronicGridGpsMapper electronicGridGpsMapper;
	
	public int insert(ElectronicGridGps electronicGridGps){
		return electronicGridGpsMapper.insert(electronicGridGps);
	}

	public int deleteByGridId(Integer id) {
		return electronicGridGpsMapper.deleteByGridId(id);
	}

	public List<ElectronicGridGps> selectElectronicGridGpsByGridId(Integer id) {
		return electronicGridGpsMapper.selectElectronicGridGpsByGridId(id);
	}
}
