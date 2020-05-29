package com.iber.portal.dao.insurance;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.insurance.Insurance;

public interface InsuranceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Insurance record);

    int insertSelective(Insurance record);

    Insurance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Insurance record);

    int updateByPrimaryKey(Insurance record);

	List<Insurance> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	List<Insurance> selectByNextMonthDate(Map<String, Object> paramMap);

	List<Insurance> selectByLpnAndType(Map<String, Object> paramMap);

	List<Insurance> selectByLpn(Map<String, Object> paramMap);

	List<Insurance> selectLpn();
}