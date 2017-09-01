package com.haoting.mvc.pagination;

public class PageParam {

    public static final int DEFAULT_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE  = 10;
    private Integer         pageSize           = Integer.valueOf(10);
    private Integer         pageNo             = Integer.valueOf(1);

    private PageParameter   page;

    public int getOffset() {
        return (this.pageNo.intValue() - 1) * this.pageSize.intValue();
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = Integer.valueOf(pageSize != null
                                        && pageSize.intValue() > 0 ? pageSize.intValue() : DEFAULT_PAGE_SIZE);
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = Integer.valueOf(null != pageNo
                                      && pageNo.intValue() >= 1 ? pageNo.intValue() : DEFAULT_PAGE_INDEX);
    }

    public PageParameter getPage() {
        return page;
    }

    public void setPage(PageParameter page) {
        this.page = page;
    }
}
