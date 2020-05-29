package com.iber.portal.service.dispatcher;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dispatcher.GridMapper;
import com.iber.portal.dao.dispatcher.GridParkRelationMapper;
import com.iber.portal.model.dispatcher.Grid;
import com.iber.portal.model.dispatcher.GridParkRelation;
import com.iber.portal.model.task.EmployeeOnGrid;


@Service
public class GridService {
	
	@Autowired
	private GridMapper gridMapper;

	public Pager<Grid> queryPageList(Map<String, Object> paramMap) {
		List<Grid> listObj = gridMapper.queryPageList(paramMap);
		Pager<Grid> pager = new Pager<Grid>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	private int getAllNum(Map<String, Object> paramMap) {
		return gridMapper.getAllNum(paramMap) ;
	}

	public int save(Grid grid) {
		return gridMapper.save(grid);
	}

	public Grid selectByPrimaryKey(Integer id) {
		return gridMapper.selectByPrimaryKey(id);
	}

	public int deleteGrid(Integer id) {
		return gridMapper.deleteGrid(id);
	}

	public int update(Grid grid) {
		return gridMapper.update(grid);
	}

	public void deleteParkOnGrid(Integer parkId, Integer gridId) {
		gridMapper.deleteParkOnGrid(parkId, gridId);
	}

	public void deleteDispatcherOnGrid(Integer dispatcherId, Integer gridId) {
		gridMapper.deleteDispatcherOnGrid(dispatcherId, gridId);
	}

	public void manageGridAdministration(int dispatherId) {
		gridMapper.manageGridAdministration(dispatherId);
	}

	public List<EmployeeOnGrid> selectEmployeeOnGrid(String cityCode) {
		return gridMapper.selectEmployeeOnGrid(cityCode);
	}

	public void cancelGridAdministration(Integer dispatcherId) {
		gridMapper.cancelGridAdministration(dispatcherId);
	}
	
	public void deleteGridParkRelationByParkId(Integer id){
		gridMapper.deleteGridParkRelationByParkId(id);
	};
	

}
