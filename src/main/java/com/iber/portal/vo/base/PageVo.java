package com.iber.portal.vo.base;

import java.io.Serializable;

/**
 * 分页vo
 * lf
 * 2018年1月4日
 */
public class PageVo implements Serializable{

    private static final long serialVersionUID = -14042516664729903L;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private Integer page = DEFAULT_PAGE;
    private Integer rows = DEFAULT_PAGE_SIZE;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page == null || page < 1) page = DEFAULT_PAGE;
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows > 100 ? 100 : rows <= 0 ? DEFAULT_PAGE_SIZE : rows ;
    }

    public int getStart() {
        return (page-1) * rows;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "page=" + page +
                ", rows=" + rows +
                ", start=" + getStart() +
                '}';
    }
}
