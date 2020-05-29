package com.iber.portal.service.fence;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.ElectronicFenceCarGroupRelationMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.ElectronicFenceCarGroupRelation;
import com.iber.portal.vo.fence.ElectronicFenceCarGroupRelationVo;

@Service
public class ElectronicFenceCarGroupRelationService {

	@Autowired
	private ElectronicFenceCarGroupRelationMapper electronicFenceCarGroupRelationMapper ;
	
	public int insertSelective(ElectronicFenceCarGroupRelation model) throws ServiceException {
		int len;
		try {
			len = electronicFenceCarGroupRelationMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = electronicFenceCarGroupRelationMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ElectronicFenceCarGroupRelation model) throws ServiceException {
		int len;
		try {
			len = electronicFenceCarGroupRelationMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ElectronicFenceCarGroupRelation selectByPrimaryKey(int id) {
		return electronicFenceCarGroupRelationMapper.selectByPrimaryKey(id);
	}
	
	public List<ElectronicFenceCarGroupRelation> selectAllByGroupIdAndFenceId(Map<String, Object> map){
		return electronicFenceCarGroupRelationMapper.selectAllByGroupIdAndFenceId(map) ;
	}
	public List<ElectronicFenceCarGroupRelationVo> selectAll(Map<String, Object> map){
		return electronicFenceCarGroupRelationMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return electronicFenceCarGroupRelationMapper.selectAllRecords(map);
	}
	
	public int deleteByGroupId(Map<String, String> map) throws ServiceException {
		int len;
		try {
			len = electronicFenceCarGroupRelationMapper.deleteByGroupId(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	public List<ElectronicFenceCarGroupRelation> selectAllByGroupId(Map<String, Object> map){
		return electronicFenceCarGroupRelationMapper.selectAllByGroupId(map) ;
	}
}
