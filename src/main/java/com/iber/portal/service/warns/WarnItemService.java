package com.iber.portal.service.warns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.warns.WarnItemMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.warns.WarnItem;

@Service
public class WarnItemService {

	@Autowired
	private WarnItemMapper warnItemMapper;
	
	public List<WarnItem> selectAll()throws ServiceException{
       try{
			return warnItemMapper.selectAll() ;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
