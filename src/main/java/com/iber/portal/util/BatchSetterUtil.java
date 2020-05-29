package com.iber.portal.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class BatchSetterUtil<T> implements BatchPreparedStatementSetter {

	final List<T> batchList;
	
	public BatchSetterUtil(List<T> batchList){
		this.batchList = batchList;
    }

	@Override
	public void setValues(PreparedStatement ps, int i) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getBatchSize() {
		// TODO Auto-generated method stub
		return batchList.size();
	}

}
