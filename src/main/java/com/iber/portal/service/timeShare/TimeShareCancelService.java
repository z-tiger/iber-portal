package com.iber.portal.service.timeShare;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.timeShare.TimeShareCancelMapper;
import com.iber.portal.vo.timeShare.TimeShareCancelVo;

@Service
public class TimeShareCancelService {

	@Autowired
	private TimeShareCancelMapper timeShareCancelMapper;
	 
	public List<TimeShareCancelVo> getAll(Map<String,Object> map) {
		return timeShareCancelMapper.getAll(map);
	}
	
	public int getAllNum(Map<String,Object> map){
    	return timeShareCancelMapper.getAllNum(map);
    }
	
	public Pager< TimeShareCancelVo> getPagerAll(Map<String,Object> map) {
		List< TimeShareCancelVo> listObj = getAll(map);
		Pager< TimeShareCancelVo> pager = new Pager< TimeShareCancelVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(map));
		return pager;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return timeShareCancelMapper.deleteByPrimaryKey(id) ;
	}

	/**
	 * 重置约车次数
	 * @param id
	 * @return
	 */
	public int resetMemberCancelCarOrderCount(Integer id) {
		return timeShareCancelMapper.resetMemberCancelCarOrderCount(id);
		
	}

	/**
	 * 重置预约充电次数 
	 * @param id
	 */
	public int resetMemberCancelCharingOrderCount(Integer id) {
		return timeShareCancelMapper.resetMemberCancelCharingOrderCount(id);
	}
}
