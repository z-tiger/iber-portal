package com.iber.portal.dao.insurance;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.insurance.InsuranceRecord;

public interface InsuranceRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InsuranceRecord record);

    int insertSelective(InsuranceRecord record);

    InsuranceRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InsuranceRecord record);

    int updateByPrimaryKey(InsuranceRecord record);

	List<InsuranceRecord> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}