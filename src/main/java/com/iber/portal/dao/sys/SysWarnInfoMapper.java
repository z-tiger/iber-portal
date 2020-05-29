package com.iber.portal.dao.sys;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.sys.SysWarnInfo;

public interface SysWarnInfoMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysWarnInfo record);

	int insertSelective(SysWarnInfo record);

	SysWarnInfo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysWarnInfo record);

	int updateByPrimaryKey(SysWarnInfo record);

	/**
	 * 根据orderId和warn_item_code查最新一条预警记录
	 * @return
	 * @author ouxx
	 * @date 2017-5-12 上午9:50:46
	 */
	SysWarnInfo queryByOrderIdAndItemCode(@Param("orderId") String orderId,
			@Param("code") String code);
}