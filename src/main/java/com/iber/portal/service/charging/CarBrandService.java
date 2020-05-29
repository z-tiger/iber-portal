package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.CarBrandMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.CarBrand;

@Service
public class CarBrandService {

	@Autowired
	private CarBrandMapper carBrandMapper;
	
	public int insertSelective(CarBrand model) throws ServiceException {
		int len;
		try {
			len = carBrandMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = carBrandMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(CarBrand model) throws ServiceException {
		int len;
		try {
			len = carBrandMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public CarBrand selectByPrimaryKey(int id) {
		return carBrandMapper.selectByPrimaryKey(id);
	}
	
	public Pager<CarBrand> getAll(Map<String, Object> paramMap) {
		List<CarBrand> listObj = carBrandMapper.getAll(paramMap);
		Pager<CarBrand> pager = new Pager<CarBrand>();
		pager.setDatas(listObj);
		pager.setTotalCount(carBrandMapper.getAllNum(paramMap));
		return pager;
	}

	public Pager<CarBrand> getAllBrand(Map<String, Object> paramMap) {
		List<CarBrand> listObj = carBrandMapper.getAllBrand(paramMap);
		Pager<CarBrand> pager = new Pager<CarBrand>();
		pager.setDatas(listObj);
		pager.setTotalCount(carBrandMapper.getAllBrandNum(paramMap));
		return pager;
	}
	
	/**充电设备功能：设置支持车辆品牌*/
	
	public Pager<CarBrand> getAllBrandByEquipmentId(int equipmentId,int from,int to,String brandName) {
		List<CarBrand> listObj = carBrandMapper.selectNotSupportBrand(equipmentId,from,to,brandName);
		Pager<CarBrand> pager = new Pager<CarBrand>();
		pager.setDatas(listObj);
		pager.setTotalCount(carBrandMapper.selectAllBrand(equipmentId,brandName));
		return pager;
	}
	
	/**获取所有的车辆品牌列表*/
	public Pager<CarBrand> getAllRecords(Map<String, Object> paramMap) {
		List<CarBrand> listObj = carBrandMapper.getAllRecords(paramMap);
		Pager<CarBrand> pager = new Pager<CarBrand>();
		pager.setDatas(listObj);
		pager.setTotalCount(carBrandMapper.getAllRecordsSum());
		return pager;
	}
	

}
