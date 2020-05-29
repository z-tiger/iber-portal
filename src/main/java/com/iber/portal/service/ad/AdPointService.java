package com.iber.portal.service.ad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.ad.AdBaseMapper;
import com.iber.portal.dao.ad.AdPointMapper;
import com.iber.portal.dao.ad.AdPositionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.ad.AdBase;
import com.iber.portal.model.ad.AdPoint;
import com.iber.portal.model.ad.AdPosition;

@Service
public class AdPointService {

	@Autowired
	private AdPointMapper adPointMapper;
	
	public int insertSelective(AdPoint model) throws ServiceException {
		int len;
		try {
			len = adPointMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = adPointMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(AdPoint model) throws ServiceException {
		int len;
		try {
			len = adPointMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public AdPoint selectByPrimaryKey(int id) {
		return adPointMapper.selectByPrimaryKey(id);
	}
	
	public Pager<AdPoint> getAll(Map<String, Object> paramMap) {
		List<AdPoint> listObj = adPointMapper.getAll(paramMap);
		Pager<AdPoint> pager = new Pager<AdPoint>();
		pager.setDatas(listObj);
		pager.setTotalCount(adPointMapper.getAllNum(paramMap));
		return pager;
	}
}
