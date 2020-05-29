package com.iber.portal.service.exception;

import java.util.List;
import java.util.Map;

import com.iber.portal.mongo.ExceptionLogNosql;
import com.iber.portal.mongo.search.MongoExceptionLogSearch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.exception.ExceptionLogMapper;
import com.iber.portal.model.exception.ExceptionLog;
import org.springframework.util.CollectionUtils;

/**
 * 
 * <br>
 * <b>功能：</b>XExceptionLogService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("exceptionLogService")
public class ExceptionLogService{

	private final static Logger log= Logger.getLogger(ExceptionLogService.class);
	
	@Autowired
    private ExceptionLogMapper  dao;

	@Autowired
	private ExceptionLogNosql exceptionLogNosql;


	public int insert(ExceptionLog record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(ExceptionLog record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public ExceptionLog selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<ExceptionLog> queryPageList(Map<String, Object> paramMap){
		List<ExceptionLog> listObj = dao.queryPageList(paramMap);
		Pager<ExceptionLog> pager = new Pager<ExceptionLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	/**
	 * 使用mongodb查询
	 * lf
	 * 2017-10-18 10:24:27
	 * @param search 查询条件
	 * @return
	 */
	public Pager<Map<String, Object>> searchLogs(final MongoExceptionLogSearch search){
		final List<Map<String, Object>> logs = exceptionLogNosql.searchLogs(search);
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		// 判断
		if (CollectionUtils.isEmpty(logs)) return pager;
		pager.setDatas(logs);
		// 总记录数
		pager.setTotalCount((int)exceptionLogNosql.getCount(search));
		return pager;
	}
}
