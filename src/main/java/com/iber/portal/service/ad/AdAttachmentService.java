package com.iber.portal.service.ad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.ad.AdAttachmentMapper;
import com.iber.portal.dao.ad.AdBaseMapper;
import com.iber.portal.dao.ad.AdPointMapper;
import com.iber.portal.dao.ad.AdPositionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.ad.AdAttachment;
import com.iber.portal.model.ad.AdBase;
import com.iber.portal.model.ad.AdPoint;
import com.iber.portal.model.ad.AdPosition;

@Service
public class AdAttachmentService {

	@Autowired
	private AdAttachmentMapper adAttachmentMapper;
	
	public int insertSelective(AdAttachment model) throws ServiceException {
		int len;
		try {
			len = adAttachmentMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = adAttachmentMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(AdAttachment model) throws ServiceException {
		int len;
		try {
			len = adAttachmentMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public AdAttachment selectByPrimaryKey(int id) {
		return adAttachmentMapper.selectByPrimaryKey(id);
	}
	
	public Pager<AdAttachment> getAll(Map<String, Object> paramMap) {
		List<AdAttachment> listObj = adAttachmentMapper.getAll(paramMap);
		Pager<AdAttachment> pager = new Pager<AdAttachment>();
		pager.setDatas(listObj);
		pager.setTotalCount(adAttachmentMapper.getAllNum(paramMap));
		return pager;
	}

	public Pager<AdAttachment> getPreview(Map<String, Object> paramMap) {
		List<AdAttachment> listObj = adAttachmentMapper.getPreview(paramMap);
		Pager<AdAttachment> pager = new Pager<AdAttachment>();
		pager.setDatas(listObj);
		pager.setTotalCount(listObj.size());
		return pager;
	}
}
