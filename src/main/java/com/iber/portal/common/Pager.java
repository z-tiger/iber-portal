package com.iber.portal.common;

import java.util.Collections;
import java.util.List;

/**
 * @Description: 分页类 @Create：
 * @Version:
 */
public class Pager<T> {

	private List<T> datas;

	private int pageSize = getDefaultPageSize();

	private int pageNumber = 1;

	private int totalCount;

	public static int getDefaultPageSize() {
		return 20;
	}

	@SuppressWarnings("unchecked")
	public List<T> getDatas() {
		return datas == null ? Collections.EMPTY_LIST : datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize < 1 ? getDefaultPageSize() : pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber < 1 ? 1 : pageNumber;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFirstResult() {
		return (pageNumber - 1) * pageSize;
	}

}
