package com.iber.portal.dao.longOrderParam;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.longOrderParam.LongOrderParam;
import com.iber.portal.vo.longOrderParam.LongOrderParamVo;

public interface LongOrderParamMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(LongOrderParam record);

	int insertSelective(LongOrderParam record);

	LongOrderParam selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(LongOrderParam record);

	int updateByPrimaryKey(LongOrderParam record);

	List<LongOrderParamVo> selectVoPager(Map<String, Object> paramMap);

	int getVoAllNum(Map<String, Object> paramMap);

	int selectLongOrderParamRecords(@Param("levelCode") Integer levelCode, @Param("carTypeId") Integer carTypeId,
			@Param("cityCode") String cityCode, @Param("id")String id);
}