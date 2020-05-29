package com.iber.portal.service.dispatcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.dispatcher.GridParkRelationMapper;
import com.iber.portal.model.dispatcher.GridParkRelation;

@Service
public class GridParkRelationService {
	@Autowired
	private GridParkRelationMapper gridParkRelationMapper;
	
	public int save(GridParkRelation gridParkRelation){
		return gridParkRelationMapper.save(gridParkRelation);
	}

	public List<GridParkRelation> selectGridParkRelationByGridId(Integer id) {
		return gridParkRelationMapper.selectGridParkRelationByGridId(id);
	}

	public int batchDeleteParkByGridId(Integer id) {
		return gridParkRelationMapper.batchDeleteParkByGridId(id);
	}

	public List<GridParkRelation> selectByParkIds(ArrayList<Integer> parkIds) {
		return gridParkRelationMapper.selectByParkIds(parkIds);
	}

	public int batchDeleteAllParkByGridId(Integer id) {
		return gridParkRelationMapper.batchDeleteAllParkByGridId(id);
	}
	
}
