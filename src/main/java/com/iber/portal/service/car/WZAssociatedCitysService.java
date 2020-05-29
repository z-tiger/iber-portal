package com.iber.portal.service.car;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.WZAssociatedCitysMapper;
import com.iber.portal.model.base.WZAssociatedCitys;

@Service("wzAssociatedCitysService")
public class WZAssociatedCitysService {
	private final static Logger log= Logger.getLogger(WZAssociatedCitysService.class);
    
	@Autowired
    private WZAssociatedCitysMapper  wzAssociatedCitysMapper;
	public List<WZAssociatedCitys> selectAll(HashMap<String, Object> map) {
		return wzAssociatedCitysMapper.selectAll(map);
	}
	public int selectAllRecords(HashMap<String, Object> map) {
		return wzAssociatedCitysMapper.selectAllRecords(map);
	}
	public WZAssociatedCitys selectByPrimaryId(int id) {
		return wzAssociatedCitysMapper.selectByPrimaryId(id);
	}
	public int updateByPrimaryKeySelective(WZAssociatedCitys currObj) {
		return wzAssociatedCitysMapper.updateByPrimaryKeySelective(currObj);
	}
	public int insert(WZAssociatedCitys obj) {
		return wzAssociatedCitysMapper.insert(obj);
	}
	public int delete(int id) {
		return wzAssociatedCitysMapper.delete(id);
	}
	public List<WZAssociatedCitys> selectAllAssociatedCitys() {
		return wzAssociatedCitysMapper.selectAllAssociatedCitys();
	}
	public WZAssociatedCitys selectCityByCode(String cityCode) {
		return wzAssociatedCitysMapper.selectCityByCode(cityCode);
	}
	public int queryIfRecord(String cityCode) {
		return wzAssociatedCitysMapper.queryIfRecord(cityCode);
	}
}
