package com.iber.portal.service.evaluate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.evaluate.OrderEvaluateMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.evaluate.OrderEvaluate;




/**
 * @describe 用车订单评价
 * 
 * @author yangguiwu
 * @date 2016年03月30日
 */
@Service
public class OrderEvaluateService {

	@Autowired
	private OrderEvaluateMapper orderEvaluateMapper;


	public List<OrderEvaluate> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return orderEvaluateMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return orderEvaluateMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int updateByPrimaryKey(OrderEvaluate record){
		return orderEvaluateMapper.updateByPrimaryKey(record);
	}
	
	public OrderEvaluate selectByPrimaryId(Integer id){
		return orderEvaluateMapper.selectByPrimaryId(id) ;
	}
}
