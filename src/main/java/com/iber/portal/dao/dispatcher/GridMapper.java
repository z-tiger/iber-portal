package com.iber.portal.dao.dispatcher;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.dispatcher.Grid;
import com.iber.portal.model.task.EmployeeOnGrid;

public interface GridMapper {
	
	List<Grid> queryPageList(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	int save(Grid grid);
	
	Grid selectByPrimaryKey(Integer id);

	int deleteGrid(Integer id);

	int update(Grid grid);

	void deleteParkOnGrid(@Param("parkId")Integer parkId, @Param("gridId")Integer gridId);

	void deleteDispatcherOnGrid(@Param("dispatcherId")Integer dispatcherId, @Param("gridId")Integer gridId);

	void manageGridAdministration(Integer dispatherId);

	List<EmployeeOnGrid> selectEmployeeOnGrid(String cityCode);

	void cancelGridAdministration(int dispatcherId);
	 
	void deleteGridParkRelationByParkId(Integer parkId);

}
