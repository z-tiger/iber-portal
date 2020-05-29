package com.iber.portal.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.ShareContentMapper;
import com.iber.portal.model.base.ShareContent;

@Service
public class ShareContentService {

	@Autowired
	private ShareContentMapper shareContentMapper;

	public int deleteByPrimaryKey(Integer id) {
		return shareContentMapper.deleteByPrimaryKey(id);
	}

	public int insertSelective(ShareContent record) {
		return shareContentMapper.insertSelective(record);
	}

	public ShareContent selectByPrimaryKey(Integer id) {
		return shareContentMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(ShareContent record) {
		return shareContentMapper.updateByPrimaryKeySelective(record);
	}

	public List<ShareContent> selectAllShareContent(){
		return shareContentMapper.selectAllShareContent();
	}
}
