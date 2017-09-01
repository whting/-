package com.haoting.mvc.pagination;

import java.util.List;

/**
 * 分页类<br/>
 * Created by maojianting on 16/9/16.
 */
public class Pagination<T> {

    /**
     * 默认分页页码
     */
    public static final Integer DEFAULT_PAGE_INDEX = 1;
    /**
     * 默认每页数量
     */
    public static final int     DEFAULT_PAGE_SIZE  = 10;
    /**
     * 页码
     */
    private int                 pageIndex          = DEFAULT_PAGE_INDEX;
    /**
     * 每页数量
     */
    private int                 pageSize           = DEFAULT_PAGE_SIZE;
    /**
     * 总记录条数
     */
    private int                 totalCount         = 0;
    /**
     * 查询结果
     */

    private int                 totalPage          = 0;
    private List<T>             list;
    /**
     * query结果
     */
    private boolean             success;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = (null == pageIndex || pageIndex < DEFAULT_PAGE_INDEX ? DEFAULT_PAGE_INDEX : pageIndex);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (null == pageSize ? DEFAULT_PAGE_SIZE : pageSize);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = (totalCount < 0 ? 0 : totalCount);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = (totalPage < 0 ? 0 : totalPage);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 获得当前分页对象的总页数
     *
     * @return
     */
    public int getPageCount() {
        if (totalCount <= 0) {
            return 0;
        }
        int pageNum = totalCount / pageSize;
        // 如果不能整除，页数加1
        if (totalCount % pageSize != 0) {
            pageNum++;
        }
        return pageNum;
    }

    /**
     * 获取记录offset
     * 
     * @return offset
     */
    public int getOffset() {
        int offset = (getPageIndex() - 1) * getPageSize();
        return offset < 0 ? 0 : offset;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
