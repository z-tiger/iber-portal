package com.iber.portal.service.fence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.ElectronicFenceGpsMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.ElectronicFenceGps;

@Service
public class ElectronicFenceGpsService {

	@Autowired
	private ElectronicFenceGpsMapper electronicFenceGpsMapper ;
	
	public int insertSelective(ElectronicFenceGps model) throws ServiceException {
		int len;
		try {
			len = electronicFenceGpsMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = electronicFenceGpsMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ElectronicFenceGps model) throws ServiceException {
		int len;
		try {
			len = electronicFenceGpsMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ElectronicFenceGps selectByPrimaryKey(int id) {
		return electronicFenceGpsMapper.selectByPrimaryKey(id);
	}
	
//	public List<ElectronicFenceGps> selectAll(Map<String, Object> map){
//		return electronicFenceGpsMapper.selectAll(map);
//	}
    
//	public int selectAllRecords(Map<String, Object> map){
//		return electronicFenceGpsMapper.selectAllRecords(map);
//	}
	
	public void deleteFenceGpsByFenceId(Integer fenceId){
		electronicFenceGpsMapper.deleteFenceGpsByFenceId(fenceId) ;
	}
	
	public List<ElectronicFenceGps> selectGpsByFenceId(Integer fenceId) {
		return electronicFenceGpsMapper.selectGpsByFenceId(fenceId) ;
	}
}
