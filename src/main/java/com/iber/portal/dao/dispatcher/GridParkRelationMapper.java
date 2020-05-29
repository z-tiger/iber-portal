package com.iber.portal.dao.dispatcher;


import java.util.ArrayList;
import java.util.List;

import com.iber.portal.model.dispatcher.GridParkRelation;

public interface GridParkRelationMapper {
	
	int save(GridParkRelation gridParkRelation);
	
	/**
	 * 根据网格id查询
	 * @param id
	 * @return
	 */
	List<GridParkRelation> selectGridParkRelationByGridId(Integer id);
	/**
	 * 根据网格id批量删除网格以及网点的关联关系(该网格通过电子网格生成)
	 * @param id
	 * @return
	 */
	int batchDeleteParkByGridId(Integer id);

	List<GridParkRelation> selectByParkIds(ArrayList<Integer> parkIds);

	int batchDeleteAllParkByGridId(Integer id);
	
}
