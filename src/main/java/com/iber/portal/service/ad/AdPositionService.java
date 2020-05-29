package com.iber.portal.service.ad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.ad.AdPositionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.ad.AdPosition;

@Service
public class AdPositionService {

	@Autowired
	private AdPositionMapper adPositionMapper;
	
	public int insertSelective(AdPosition model) throws ServiceException {
		int len;
		try {
			len = adPositionMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = adPositionMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(AdPosition model) throws ServiceException {
		int len;
		try {
			len = adPositionMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public AdPosition selectByPrimaryKey(int id) {
		return adPositionMapper.selectByPrimaryKey(id);
	}
	
	public Pager<AdPosition> getAll(Map<String, Object> paramMap) {
		List<AdPosition> listObj = adPositionMapper.getAll(paramMap);
		Pager<AdPosition> pager = new Pager<AdPosition>();
		pager.setDatas(listObj);
		pager.setTotalCount(adPositionMapper.getAllNum(paramMap));
		return pager;
	}
}
