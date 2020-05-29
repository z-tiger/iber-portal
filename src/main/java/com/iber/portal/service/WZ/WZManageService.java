package com.iber.portal.service.WZ;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.WZQueryMapper;
import com.iber.portal.vo.base.WZQueryVo;
@Service
public class WZManageService {
	@Autowired
	private WZQueryMapper wzQueryMapper;
	
	public List<WZQueryVo> queryWZEmployeeInfos(Map<String, Object> map){
		return wzQueryMapper.queryWZEmployeeInfos(map);		
	}
	public List<WZQueryVo> queryWZMemberInfos(Map<String, Object> map){
		return wzQueryMapper.queryWZMemberInfos(map);		
	}
	public Integer queryWZMemberInfosRecords(Map<String, Object> map){
		return wzQueryMapper.queryWZMemberInfosRecords(map);
	}
	public Integer queryWZEmployeeInfosRecords(Map<String, Object> map){
		return wzQueryMapper.queryWZEmployeeInfosRecords(map);
	}
	public Integer queryWZLongRentInfosRecords(Map<String, Object> map){
		return wzQueryMapper.queryWZLongRentInfosRecords(map);
	}
	public List<WZQueryVo>  queryWZLongRentInfos(Map<String, Object> map) {
		return wzQueryMapper.queryWZLongRentInfos(map);
	}

	public List<WZQueryVo> queryWzInfo(HashMap<String, Object> map) {
		return wzQueryMapper.queryWzInfo(map);
	}

	public Integer queryWzInfoRecord(HashMap<String, Object> map) {
		return wzQueryMapper.queryWzInfoRecord(map);
	}

}
