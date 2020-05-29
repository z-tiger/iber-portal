package com.iber.portal.service.longOrder;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberLevelMapper;
import com.iber.portal.dao.car.CarTypeMapper;
import com.iber.portal.dao.longOrderParam.LongOrderParamMapper;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.car.CarType;
import com.iber.portal.model.longOrderParam.LongOrderParam;
import com.iber.portal.vo.longOrderParam.LongOrderParamVo;

@Service
public class LongOrderService {

	@Autowired
	private MemberLevelMapper memberLevelMapper;
	
	@Autowired
	private CarTypeMapper carTypeMapper;
	
	@Autowired
	private LongOrderParamMapper longOrderParamMapper;
	
	public List<MemberLevel> selectAllMemberLevel(){
		return memberLevelMapper.selectAllMemberLevel();
	}

	public List<CarType> selectAllCarType() {
		return carTypeMapper.selectAllNotPage();
	}

	public Pager<LongOrderParamVo> queryPageList(Map<String, Object> paramMap) {
		List<LongOrderParamVo> listObj = longOrderParamMapper.selectVoPager(paramMap);
		Pager<LongOrderParamVo> pager = new Pager<LongOrderParamVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	private Integer getAllNum(Map<String, Object> paramMap) {
		return longOrderParamMapper.getVoAllNum(paramMap);
	}

	public LongOrderParam selectById(Integer id) {
		return longOrderParamMapper.selectByPrimaryKey(id);
	}

	public int updateById(LongOrderParam longOrderParam) {
		return longOrderParamMapper.updateByPrimaryKeySelective(longOrderParam);
	}

	public int selectLongOrderParamRecords(Integer levelCode, Integer carTypeId, String cityCode,String id) {
		return longOrderParamMapper.selectLongOrderParamRecords(levelCode,carTypeId,cityCode,id);
	}

	public int saveLongOrderParam(LongOrderParam longOrderParam) {
		return longOrderParamMapper.insertSelective(longOrderParam);
	}
}
