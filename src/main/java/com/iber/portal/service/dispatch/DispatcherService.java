package com.iber.portal.service.dispatch;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dispatch.DispatcherMapper;
import com.iber.portal.dao.dispatch.DispatcherMemberMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dispatch.Dispatcher;
import com.iber.portal.model.dispatch.DispatcherMember;
import com.iber.portal.model.dispatch.DispatcherTask;
import com.iber.portal.model.dispatcher.Employee;



@Service
public class DispatcherService {

	@Autowired
	private DispatcherMapper dispatcherMapper;


	@Autowired
	private DispatcherMemberMapper dispatcherMemberMapper;	
	
	public List<Dispatcher> selectByDispatcherPageList(Map<String, Object> map)throws ServiceException{
	       try{
	    	   return dispatcherMapper.selectByDispatcherPageList(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}	
	
	
	
	public int selectByDispatcherPageListRecords(Map<String, Object> map)throws ServiceException{
	       try{
				return dispatcherMapper.selectByDispatcherPageListRecords(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}
	
	
	
	public List<Dispatcher> selectByDispatcherType(Map<String, Object> map)throws ServiceException{
	       try{
	    	   return dispatcherMapper.selectByDispatcherType(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}


	public List<DispatcherMember> selectByDispatcherMemberType(Map<String, Object> map)throws ServiceException{
	       try{
	    	   return dispatcherMemberMapper.selectByDispatcherMemberType(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}


	  public int insertSelective(Dispatcher model) throws ServiceException {
			int len;
			try {
				len = dispatcherMapper.insertSelective(model);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
			return len;
		}

		public int updateByPrimaryKeySelective(Dispatcher model) throws ServiceException {
			int len;
			try {
				len = dispatcherMapper.updateByPrimaryKeySelective(model);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
			return len;
		}

		public Dispatcher selectByPrimaryId(int id) {
			return dispatcherMapper.selectByPrimaryId(id);
		}



		public Pager<Employee> queryPageList(Map<String, Object> paramMap) {
			// TODO Auto-generated method stub
			return null;
		}


}
