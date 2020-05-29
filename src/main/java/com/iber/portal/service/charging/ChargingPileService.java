package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingPileMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingPile;

@Service
public class ChargingPileService {

	@Autowired
	private ChargingPileMapper chargingPileMapper;
	
	public int insertSelective(ChargingPile model) throws ServiceException {
		int len;
		try {
			len = chargingPileMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingPileMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingPile model) throws ServiceException {
		int len;
		try {
			len = chargingPileMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingPile selectByPrimaryKey(int id) {
		return chargingPileMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingPile> getAll(Map<String, Object> paramMap) {
		List<ChargingPile> listObj = chargingPileMapper.getAll(paramMap);
		Pager<ChargingPile> pager = new Pager<ChargingPile>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingPileMapper.getAllNum(paramMap));
		return pager;
	}
	
	public List<ChargingPile> queryPileByParkId(Map<String, Object> paramMap) {
		return chargingPileMapper.queryPileByParkId(paramMap);
	}
	
	public List<ChargingPile> queryPileByAreaCode(Map<String, Object> paramMap) {
		return chargingPileMapper.queryPileByAreaCode(paramMap);
	}
	
	public int queryParkPileByAreaCode(String areaCode){
		return chargingPileMapper.queryParkPileByAreaCode(areaCode) ;
	}
	
	public List<ChargingPile> queryPileStatusByAreaCode(Map<String, Object> paramMap){
		return chargingPileMapper.queryPileStatusByAreaCode(paramMap) ;
	}
}
