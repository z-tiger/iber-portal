package com.iber.portal.service.version;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.versions.VersionsPileMapper;
import com.iber.portal.model.versions.VersionsPile;

@Service
public class VersionsPileService {

	@Autowired
	private VersionsPileMapper versionsPileMapper;

	public List<VersionsPile> selectByPrimaryInfo(HashMap<String, Object> record) {
		return 	versionsPileMapper.selectByPrimaryInfo(record);
	}

	public int selectByPrimaryKeyRecords(HashMap<String, Object> record) {
		return versionsPileMapper.selectByPrimaryKeyRecords(record);
	}

	public VersionsPile selectByPrimaryId(int id) {
		return versionsPileMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(VersionsPile record) {
		return  versionsPileMapper.updateByPrimaryKeySelective(record);
	}

	public int insertSelective(VersionsPile record) {
		return versionsPileMapper.insertSelective(record);
	}

	public int deleteByPrimaryKey(int id) {
		return versionsPileMapper.deleteByPrimaryKey(id);
	}

	public List<VersionsPile> selectNewestVersion() {
		return versionsPileMapper.selectNewestVersion();
	}
}
