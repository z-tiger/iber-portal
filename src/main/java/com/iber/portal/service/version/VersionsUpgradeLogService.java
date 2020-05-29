package com.iber.portal.service.version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.versions.VersionsUpgradeLogMapper;
import com.iber.portal.model.versions.VersionsUpgradeLog;
import com.iber.portal.vo.car.CarVersionVo;

@Service
public class VersionsUpgradeLogService {

	@Autowired
	private VersionsUpgradeLogMapper versionsUpgradeLogMapper;
	
	public int insertSelective(VersionsUpgradeLog record){
		return versionsUpgradeLogMapper.insertSelective(record) ;
	}
	
	public Pager<VersionsUpgradeLog> queryCarVersionsUpgradeLog(String upgradeType , String lpn,int first_page, int page_size) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("upgradeType", upgradeType) ;
		map.put("lpn", lpn) ;
		map.put("offset", first_page) ;
		map.put("rows", page_size) ;
		List<VersionsUpgradeLog> carVersionList = versionsUpgradeLogMapper.queryCarVersionsUpgradeLog(map) ;
		Pager< VersionsUpgradeLog> pager = new Pager< VersionsUpgradeLog>();
		pager.setDatas(carVersionList);
		pager.setTotalCount(versionsUpgradeLogMapper.queryVersionsUpgradeLogCount(map));
		return pager ;
	}
	
	public int queryVersionsUpgradeLogCount(Map<String, Object> map){
		return versionsUpgradeLogMapper.queryVersionsUpgradeLogCount(map) ;
	}
}
