/*
 * 
 */
package com.iber.portal.dao.base;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * @author ouxx
 * @since 2017-4-18 下午2:22:38
 *
 */
public interface SplitHistoryTablesMapper {

	int queryCmdInfoCnt();
	int queryCarRunLogCnt();
	int querySysWarnCnt();
	
	/**
	 * 创建新的历史表，表名后是增加时间信息的
	 * @param newTableName
	 * @param origTableName
	 * @param latestTime
	 * @author ouxx
	 * @date 2017-4-18 下午2:56:37
	 */
	void createNewHistoryTable(@Param("newTableName") String newTableName,
			@Param("origTableName") String origTableName, @Param("latestTime") Date latestTime);
	
	/**
	 * 删除原表中create_time < latestTime 的记录
	 * @param origTableName
	 * @param latestTime
	 * @author ouxx
	 * @date 2017-4-18 下午2:56:42
	 */
	void deleteOrigTableRecords(@Param("origTableName") String origTableName, @Param("latestTime") Date latestTime);
}
