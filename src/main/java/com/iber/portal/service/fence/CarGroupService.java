package com.iber.portal.service.fence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.CarGroupMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.CarGroup;
import com.iber.portal.vo.car.CarGroupVo;

@Service
public class CarGroupService {

	@Autowired
	private CarGroupMapper carGroupMapper ;
	
	public int insertSelective(CarGroup model) throws ServiceException {
		int len;
		try {
			len = carGroupMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = carGroupMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(CarGroup model) throws ServiceException {
		int len;
		try {
			len = carGroupMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public CarGroup selectByPrimaryKey(int id) {
		return carGroupMapper.selectByPrimaryKey(id);
	}
	
	public List<CarGroupVo> selectAll(Map<String, Object> map){
		return carGroupMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return carGroupMapper.selectAllRecords(map);
	}
	
	public List<CarGroup> selectAllNotPage(){
		return carGroupMapper.selectAllNotPage() ;
	}
	    
	public List<CarGroup> selectGroupByCityCode(String cityCode){
		return carGroupMapper.selectGroupByCityCode(cityCode) ;
	}
	
	/**
	 * 根据cityCode和车组名，查询车组
	 * @param cityCode
	 * @param groupName
	 * @return
	 * @author ouxx
	 * @date 2016-9-26 下午2:53:44
	 */
	public List<CarGroup> selectGroupByCityCodeAndGroupName(String cityCode, String groupName){
		return carGroupMapper.selectGroupByCityCodeAndGroupName(cityCode, groupName) ;
	}
}
