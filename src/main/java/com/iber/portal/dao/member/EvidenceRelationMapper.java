package com.iber.portal.dao.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.member.EvidenceRelation;



/**
 * 
 * <br>
 * <b>功能：</b>EvidenceRelationDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface EvidenceRelationMapper {
	
	int insert(EvidenceRelation record);

	List<EvidenceRelation> getByReportId(@Param("reportId") Integer reportId);

}
