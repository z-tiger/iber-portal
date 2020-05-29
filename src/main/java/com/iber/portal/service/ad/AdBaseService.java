package com.iber.portal.service.ad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.ad.AdBaseMapper;
import com.iber.portal.dao.ad.AdPositionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.ad.AdBase;
import com.iber.portal.model.ad.AdPosition;

@Service
public class AdBaseService {

	@Autowired
	private AdBaseMapper adBaseMapper;
	
	public int insertSelective(AdBase model) throws ServiceException {
		int len;
		try {
			len = adBaseMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = adBaseMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(AdBase model) throws ServiceException {
		int len;
		try {
			len = adBaseMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public AdBase selectByPrimaryKey(int id) {
		return adBaseMapper.selectByPrimaryKey(id);
	}
	
	public Pager<AdBase> getAll(Map<String, Object> paramMap) {
		List<AdBase> listObj = adBaseMapper.getAll(paramMap);
		Pager<AdBase> pager = new Pager<AdBase>();
		pager.setDatas(listObj);
		pager.setTotalCount(adBaseMapper.getAllNum(paramMap));
		return pager;
	}

	public Pager<AdBase> getPreview(Map<String, Object> paramMap) {
		List<AdBase> listObj = adBaseMapper.getPreview(paramMap);
		Pager<AdBase> pager = new Pager<AdBase>();
		pager.setDatas(listObj);
		pager.setTotalCount(listObj.size());
		return pager;
	}
}
