package com.iber.portal.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.PageLimit;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.sys.SysButtonMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.sys.SysButton;

@Service
public class SysButtonService {

	@Autowired
	private SysButtonMapper sysButtonMapper;
	
	public int insertSelective(SysButton model) throws ServiceException {
		int len;
		try {
			len = sysButtonMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = sysButtonMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(SysButton model) throws ServiceException {
		int len;
		try {
			len = sysButtonMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public SysButton selectByPrimaryKey(int id) {
		return sysButtonMapper.selectByPrimaryKey(id);
	}
	
	public List<SysButton> getAllButton() {
		return sysButtonMapper.getAllButton();
	}
	
	public List<SysButton> getAll(PageLimit pLimit) {
		return sysButtonMapper.getAll(pLimit);
	}
	
	public int getAllNum(){
    	return sysButtonMapper.getAllNum();
    }
	
	public Pager<SysButton> getAll(int first_page, int page_size) {
		List<SysButton> listObj = getAll(new PageLimit(first_page, page_size));
		Pager<SysButton> pager = new Pager<SysButton>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum());
		return pager;
	}
}
