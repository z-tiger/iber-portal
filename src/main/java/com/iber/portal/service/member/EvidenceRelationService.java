package com.iber.portal.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.member.EvidenceRelationMapper;
import com.iber.portal.model.member.EvidenceRelation;

@Service
public class EvidenceRelationService {
	
	@Autowired
	private EvidenceRelationMapper evidenceRelationMapper;
	
	public int insert(EvidenceRelation record){
		return evidenceRelationMapper.insert(record);
	}
	
	public List<EvidenceRelation> getByReportId(int reportId){
		return evidenceRelationMapper.getByReportId(reportId);
	}
}
