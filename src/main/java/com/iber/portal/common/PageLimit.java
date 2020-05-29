package com.iber.portal.common;

public class PageLimit {
	
	private int offset;
	
	private int rows;
	
	public PageLimit(int currOffset, int currRows) {
		this.offset = currOffset;
		this.rows = currRows;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
