package com.iber.portal.service.dispatch;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.dispatch.DispatcherTaskMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dispatch.DispatcherTask;
import com.iber.portal.model.evaluate.OrderEvaluateLabel;

@Service
public class DispatchService {

	@Autowired
	private DispatcherTaskMapper dispatcherTaskMapper;
	

	public List<DispatcherTask> selectByDispatcherTaskList(Map<String, Object> map)throws ServiceException{
	       try{
	    	   return dispatcherTaskMapper.selectByDispatcherTaskList(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}	
	
	
	
	public int selectByWarnInfoListRecords(Map<String, Object> map)throws ServiceException{
	       try{
				return dispatcherTaskMapper.selectByDispatcherTaskRecords(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

		public List<DispatcherTask> selectByPrimaryKeyExcel(Map<String, String> map) {

			return dispatcherTaskMapper.selectByPrimaryKeyExcel(map);	
		}


	  public int insertSelective(DispatcherTask model) throws ServiceException {
			int len;
			try {
				len = dispatcherTaskMapper.insertSelective(model);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
			return len;
		}

		public int updateByPrimaryKeySelective(DispatcherTask model) throws ServiceException {
			int len;
			try {
				len = dispatcherTaskMapper.updateByPrimaryKeySelective(model);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
			return len;
		}

		public DispatcherTask selectByPrimaryId(int id) {
			return dispatcherTaskMapper.selectByPrimaryId(id);
		}


		public DispatcherTask selectBySrcAlertId(int id) {
			return dispatcherTaskMapper.selectBySrcAlertId(id);
		}

}
