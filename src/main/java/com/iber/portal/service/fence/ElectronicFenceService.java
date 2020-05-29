package com.iber.portal.service.fence;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iber.portal.dao.base.ElectronicFenceMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.ElectronicFence;
import com.iber.portal.model.base.ElectronicFenceGps;

@Service
public class ElectronicFenceService {

	@Autowired
	private ElectronicFenceMapper electronicFenceMapper;
	
	@Autowired
	private ElectronicFenceGpsService electronicFenceGpsService;
	
	public int insertSelective(ElectronicFence model) throws ServiceException {
		int len;
		try {
			len = electronicFenceMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	@Transactional(rollbackFor=Exception.class)
	public int insertModel(ElectronicFence model,String fencePoint)throws ServiceException  {
		int len;
		try {
			len = electronicFenceMapper.insertSelective(model);
			if(fencePoint.length() > 0) {
				//清除电子围栏gps信息
				electronicFenceGpsService.deleteFenceGpsByFenceId(model.getId()) ;
				
				//新增电子围栏gps信息
				String[] fencePoints = fencePoint.split(",") ;
				for(int i = 0 ; i < fencePoints.length ; i ++) {
					String[] points = fencePoints[i].split("#") ;
					ElectronicFenceGps gpsModel = new ElectronicFenceGps(null,model.getId(),points[0],points[1]) ;
					electronicFenceGpsService.insertSelective(gpsModel) ;
				} 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int updateModel(ElectronicFence model,String fencePoint)throws ServiceException  {
		int len;
		try {
			
			len = electronicFenceMapper.updateByPrimaryKeySelective(model);
			if(fencePoint.length() > 0) {
				//清除电子围栏gps信息
				electronicFenceGpsService.deleteFenceGpsByFenceId(model.getId()) ;
				
				//新增电子围栏gps信息
				String[] fencePoints = fencePoint.split(",") ;
				for(int i = 0 ; i < fencePoints.length ; i ++) {
					String[] points = fencePoints[i].split("#") ;
					ElectronicFenceGps gpsModel = new ElectronicFenceGps(null,model.getId(),points[0],points[1]) ;
					electronicFenceGpsService.insertSelective(gpsModel) ;
				} 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = electronicFenceMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ElectronicFence model) throws ServiceException {
		int len;
		try {
			len = electronicFenceMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ElectronicFence selectByPrimaryKey(int id) {
		return electronicFenceMapper.selectByPrimaryKey(id);
	}
	
	public List<ElectronicFence> selectAll(Map<String, Object> map){
		return electronicFenceMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return electronicFenceMapper.selectAllRecords(map);
	}
	
	public List<ElectronicFence> selectFenceByCityCode(Map<String,String> map){
		return electronicFenceMapper.selectFenceByCityCode(map) ;
	}
}
