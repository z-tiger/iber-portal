package com.iber.portal.service.evaluate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.evaluate.OrderEvaluateLabelMapper;
import com.iber.portal.dao.evaluate.OrderEvaluateMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.enterprise.EnterpriseLevel;
import com.iber.portal.model.evaluate.OrderEvaluate;
import com.iber.portal.model.evaluate.OrderEvaluateLabel;




/**
 * @describe 评价标签配置
 * 
 * @author yangguiwu
 * @date 2016年03月30日
 */
@Service
public class OrderEvaluateLabelService {

	@Autowired
	private OrderEvaluateLabelMapper orderEvaluateLabelMapper;
	
	


	public List<OrderEvaluateLabel> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return orderEvaluateLabelMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return orderEvaluateLabelMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = orderEvaluateLabelMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int insertSelective(OrderEvaluateLabel model) throws ServiceException {
		int len;
		try {
			len = orderEvaluateLabelMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(OrderEvaluateLabel model) throws ServiceException {
		int len;
		try {
			len = orderEvaluateLabelMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public OrderEvaluateLabel selectByPrimaryId(int id) {
		return orderEvaluateLabelMapper.selectByPrimaryId(id);
	}


}
